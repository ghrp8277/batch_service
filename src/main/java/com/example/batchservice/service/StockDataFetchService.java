package com.example.batchservice.service;

import com.example.batchservice.entity.Stock;
import com.example.batchservice.entity.StockData;
import com.example.batchservice.repository.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockDataFetchService {

    @Autowired
    private StockDataRepository stockDataRepository;

    private static final int PAGE_SIZE = 100;

    @Cacheable("stockData")
    public void fetchStockDataWithIndicator(Stock stock, List<StockData> allStockData, String indicator) {
        int pageNumber = 0;
        Page<StockData> stockDataPage;

        do {
            switch (indicator) {
                case "movingAverage12":
                    stockDataPage = stockDataRepository.findByStockWithMovingAverage12(stock, PageRequest.of(pageNumber, PAGE_SIZE));
                    break;
                case "movingAverage20":
                    stockDataPage = stockDataRepository.findByStockWithMovingAverage20(stock, PageRequest.of(pageNumber, PAGE_SIZE));
                    break;
                case "movingAverage26":
                    stockDataPage = stockDataRepository.findByStockWithMovingAverage26(stock, PageRequest.of(pageNumber, PAGE_SIZE));
                    break;
                case "bollingerBands":
                    stockDataPage = stockDataRepository.findByStockWithBollingerBands(stock, PageRequest.of(pageNumber, PAGE_SIZE));
                    break;
                case "macd":
                    stockDataPage = stockDataRepository.findByStockWithMacd(stock, PageRequest.of(pageNumber, PAGE_SIZE));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown indicator: " + indicator);
            }
            allStockData.addAll(stockDataPage.getContent());
            pageNumber++;
        } while (stockDataPage.hasNext());
    }
}
