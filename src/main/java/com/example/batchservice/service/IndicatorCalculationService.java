package com.example.batchservice.service;

import com.example.batchservice.entity.Stock;
import com.example.batchservice.entity.StockData;
import com.example.batchservice.repository.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

@Service
public class IndicatorCalculationService {
    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private TechnicalIndicatorService technicalIndicatorService;

    @Autowired
    private StockDataFetchService stockDataFetchService;

    private static final int MAX_RETRY_ATTEMPTS = 3;

    @Async
    @Transactional
    public CompletableFuture<Void> calculateIndicatorsForStockAsync(Stock stock) {
        return CompletableFuture.runAsync(() -> {
            calculateIndicatorsForStock(stock);
        });
    }

    @Transactional
    public void calculateIndicatorsForStock(Stock stock) {
        List<StockData> allStockData = new ArrayList<>();

        stockDataFetchService.fetchStockDataWithIndicator(stock, allStockData, "movingAverage12");
        stockDataFetchService.fetchStockDataWithIndicator(stock, allStockData, "movingAverage20");
        stockDataFetchService.fetchStockDataWithIndicator(stock, allStockData, "movingAverage26");
        stockDataFetchService.fetchStockDataWithIndicator(stock, allStockData, "bollingerBands");
        stockDataFetchService.fetchStockDataWithIndicator(stock, allStockData, "macd");

        List<Double> closePrices = allStockData.stream()
                .map(sd -> (double) sd.getClosePrice())
                .collect(Collectors.toList());

        for (StockData stockData : allStockData) {
            technicalIndicatorService.calculateIndicators(stockData, closePrices);
        }

        saveStockData(allStockData);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void calculateIndicatorsForStockWithRetry(Stock stock) {
        int attempt = 0;

        while (attempt < MAX_RETRY_ATTEMPTS) {
            try {
                calculateIndicatorsForStock(stock);
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt >= MAX_RETRY_ATTEMPTS) {
                    throw e;
                }
                // 짧은 대기 시간 후 재시도
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Transactional
    @CacheEvict(value = "stockData", allEntries = true)
    public void saveStockData(List<StockData> stockDataList) {
        stockDataRepository.saveAll(stockDataList);
    }
}
