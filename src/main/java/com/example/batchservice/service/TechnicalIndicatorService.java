package com.example.batchservice.service;

import com.example.common.StockData;
import com.example.common.MovingAverage;
import com.example.common.BollingerBands;
import com.example.common.MACD;
import com.example.common.RSI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        if (stockData.getMovingAverage12().isEmpty() && !newMovingAverage.getSma12().isEmpty()) {
            stockData.setMovingAverage12(newMovingAverage.getSma12());
        }
        if (stockData.getMovingAverage20().isEmpty() && !newMovingAverage.getSma20().isEmpty()) {
            stockData.setMovingAverage20(newMovingAverage.getSma20());
        }
        if (stockData.getMovingAverage26().isEmpty() && !newMovingAverage.getSma26().isEmpty()) {
            stockData.setMovingAverage26(newMovingAverage.getSma26());
        }
    }

    private void updateBollingerBands(StockData stockData, BollingerBands newBollingerBands) {
        BollingerBands existingBollingerBands = stockData.getBollingerBands();
        if (existingBollingerBands.getResults().isEmpty()) {
            stockData.setBollingerBands(newBollingerBands);
        } else {
            if (existingBollingerBands.getUpperBand().isEmpty() && !newBollingerBands.getUpperBand().isEmpty()) {
                existingBollingerBands.setUpperBand(newBollingerBands.getUpperBand());
            }
            if (existingBollingerBands.getMiddleBand().isEmpty() && !newBollingerBands.getMiddleBand().isEmpty()) {
                existingBollingerBands.setMiddleBand(newBollingerBands.getMiddleBand());
            }
            if (existingBollingerBands.getLowerBand().isEmpty() && !newBollingerBands.getLowerBand().isEmpty()) {
                existingBollingerBands.setLowerBand(newBollingerBands.getLowerBand());
            }
        }
    }

    private void updateMACD(StockData stockData, MACD newMACD) {
        MACD existingMACD = stockData.getMacd();
        if (existingMACD.getResults().isEmpty()) {
            stockData.setMacd(newMACD);
        } else {
            if (existingMACD.getMacdLine().isEmpty() && !newMACD.getMacdLine().isEmpty()) {
                existingMACD.setMacdLine(newMACD.getMacdLine());
            }
            if (existingMACD.getSignalLine().isEmpty() && !newMACD.getSignalLine().isEmpty()) {
                existingMACD.setSignalLine(newMACD.getSignalLine());
            }
            if (existingMACD.getHistogram().isEmpty() && !newMACD.getHistogram().isEmpty()) {
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
