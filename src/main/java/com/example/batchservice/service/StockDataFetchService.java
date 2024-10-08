package com.example.batchservice.service;

import com.example.common.Stock;
import com.example.common.StockData;
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
            stockDataPage = fetchStockDataPage(stock, indicator, pageNumber);
            allStockData.addAll(stockDataPage.getContent());
            pageNumber++;
        } while (stockDataPage.hasNext());
    }

    private Page<StockData> fetchStockDataPage(Stock stock, String indicator, int pageNumber) {
        return switch (indicator) {
            case "movingAverage12" ->
                    stockDataRepository.findByStockWithMovingAverage12(stock, PageRequest.of(pageNumber, PAGE_SIZE));
            case "movingAverage20" ->
                    stockDataRepository.findByStockWithMovingAverage20(stock, PageRequest.of(pageNumber, PAGE_SIZE));
            case "movingAverage26" ->
                    stockDataRepository.findByStockWithMovingAverage26(stock, PageRequest.of(pageNumber, PAGE_SIZE));
            case "bollingerBands" ->
                    stockDataRepository.findByStockWithBollingerBands(stock, PageRequest.of(pageNumber, PAGE_SIZE));
            case "macd" -> stockDataRepository.findByStockWithMacd(stock, PageRequest.of(pageNumber, PAGE_SIZE));
            default -> throw new IllegalArgumentException("Unknown indicator: " + indicator);
        };
    }
}
