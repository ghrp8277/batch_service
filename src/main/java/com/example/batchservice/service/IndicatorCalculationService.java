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
}
