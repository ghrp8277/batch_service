package com.example.batchservice.service;

import com.example.batchservice.entity.Stock;
import com.example.batchservice.entity.StockData;
import com.example.batchservice.repository.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndicatorCalculationService {
    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private TechnicalIndicatorService technicalIndicatorService;

    private static final int MAX_RETRY_ATTEMPTS = 3;

    @Transactional
    public void calculateIndicatorsForStock(Stock stock) {
        List<StockData> stockDataList = stockDataRepository.findByStock(stock);
        List<Double> closePrices = new ArrayList<>();
        for (StockData stockData : stockDataList) {
            closePrices.add((double) stockData.getClosePrice());
        }

        for (StockData stockData : stockDataList) {
            technicalIndicatorService.calculateIndicators(stockData, closePrices);
        }

        stockDataRepository.saveAll(stockDataList);
    }

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
}
