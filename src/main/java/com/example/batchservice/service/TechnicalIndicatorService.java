package com.example.batchservice.service;

import com.example.batchservice.entity.StockData;
import com.example.batchservice.entity.TechnicalIndicators.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TechnicalIndicatorService {
    public void calculateIndicators(StockData stockData, List<Double> prices) {
        List<Runnable> tasks = new ArrayList<>();

        tasks.add(() -> {
            MovingAverage ma = new MovingAverage();
            ma.calculate(prices);
            stockData.setMovingAverage12(ma.getResults().get("SMA12"));
            stockData.setMovingAverage20(ma.getResults().get("SMA20"));
            stockData.setMovingAverage26(ma.getResults().get("SMA26"));
        });

        tasks.add(() -> {
            BollingerBands bb = new BollingerBands();
            bb.calculate(prices);
            stockData.setBollingerBands(bb);
        });

        tasks.add(() -> {
            MACD macd = new MACD();
            macd.calculate(prices);
            stockData.setMacd(macd);
        });

        tasks.add(() -> {
            RSI rsi = new RSI();
            rsi.calculate(prices);
            List<Double> rsiValues = rsi.getResults().get("RSI");
            stockData.setRsi((rsiValues != null && !rsiValues.isEmpty()) ? rsiValues.get(rsiValues.size() - 1) : null);
        });

        for (Runnable task : tasks) {
            task.run();
        }
    }
}
