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
            updateMovingAverage(stockData, ma);
        });

        tasks.add(() -> {
            BollingerBands bb = new BollingerBands();
            bb.calculate(prices);
            updateBollingerBands(stockData, bb);
        });

        tasks.add(() -> {
            MACD macd = new MACD();
            macd.calculate(prices);
            updateMACD(stockData, macd);
        });

        tasks.add(() -> {
            RSI rsi = new RSI();
            rsi.calculate(prices);
            updateRSI(stockData, rsi);
        });

        for (Runnable task : tasks) {
            task.run();
        }
    }

    private void updateMovingAverage(StockData stockData, MovingAverage newMovingAverage) {
        if (stockData.getMovingAverage12() == null && newMovingAverage.getSma12() != null) {
            stockData.setMovingAverage12(newMovingAverage.getSma12());
        }
        if (stockData.getMovingAverage20() == null && newMovingAverage.getSma20() != null) {
            stockData.setMovingAverage20(newMovingAverage.getSma20());
        }
        if (stockData.getMovingAverage26() == null && newMovingAverage.getSma26() != null) {
            stockData.setMovingAverage26(newMovingAverage.getSma26());
        }
    }

    private void updateBollingerBands(StockData stockData, BollingerBands newBollingerBands) {
        BollingerBands existingBollingerBands = stockData.getBollingerBands();
        if (existingBollingerBands == null) {
            stockData.setBollingerBands(newBollingerBands);
        } else {
            if (existingBollingerBands.getUpperBand() == null && newBollingerBands.getUpperBand() != null) {
                existingBollingerBands.setUpperBand(newBollingerBands.getUpperBand());
            }
            if (existingBollingerBands.getMiddleBand() == null && newBollingerBands.getMiddleBand() != null) {
                existingBollingerBands.setMiddleBand(newBollingerBands.getMiddleBand());
            }
            if (existingBollingerBands.getLowerBand() == null && newBollingerBands.getLowerBand() != null) {
                existingBollingerBands.setLowerBand(newBollingerBands.getLowerBand());
            }
        }
    }

    private void updateMACD(StockData stockData, MACD newMACD) {
        MACD existingMACD = stockData.getMacd();
        if (existingMACD == null) {
            stockData.setMacd(newMACD);
        } else {
            if (existingMACD.getMacdLine() == null && newMACD.getMacdLine() != null) {
                existingMACD.setMacdLine(newMACD.getMacdLine());
            }
            if (existingMACD.getSignalLine() == null && newMACD.getSignalLine() != null) {
                existingMACD.setSignalLine(newMACD.getSignalLine());
            }
            if (existingMACD.getHistogram() == null && newMACD.getHistogram() != null) {
                existingMACD.setHistogram(newMACD.getHistogram());
            }
        }
    }

    private void updateRSI(StockData stockData, RSI newRSI) {
        if (stockData.getRsi() == null && newRSI.getRsi() != null && !newRSI.getRsi().isEmpty()) {
            List<Double> rsiValues = newRSI.getRsi();
            stockData.setRsi(rsiValues.getLast());
        }
    }
}
