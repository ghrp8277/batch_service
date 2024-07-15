package com.example.batchservice.entity.TechnicalIndicators;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Embeddable
@Data
public class MovingAverage implements TechnicalIndicator {
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sma_12", joinColumns = @JoinColumn(name = "stock_data_id"))
    @Column(name = "value")
    private List<Double> sma12;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sma_20", joinColumns = @JoinColumn(name = "stock_data_id"))
    @Column(name = "value")
    private List<Double> sma20;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sma_26", joinColumns = @JoinColumn(name = "stock_data_id"))
    @Column(name = "value")
    private List<Double> sma26;

    @Override
    public void calculate(List<Double> prices) {
        sma12 = calculateSMA(prices, 12);
        sma20 = calculateSMA(prices, 20);
        sma26 = calculateSMA(prices, 26);
    }

    private List<Double> calculateSMA(List<Double> prices, int period) {
        double[] sma = new double[prices.size()];
        List<Double> result = new ArrayList<>(prices.size());

        if (prices.size() < period) {
            return result;
        }

        double sum = prices.stream().limit(period).mapToDouble(Double::doubleValue).sum();
        sma[period - 1] = sum / period;
        result.add(sma[period - 1]);

        for (int i = period; i < prices.size(); i++) {
            sum += prices.get(i) - prices.get(i - period);
            sma[i] = sum / period;
            result.add(sma[i]);
        }

        return result;
    }

    @Override
    public Map<String, List<Double>> getResults() {
        Map<String, List<Double>> results = new HashMap<>();
        results.put("SMA12", sma12);
        results.put("SMA20", sma20);
        results.put("SMA26", sma26);
        return results;
    }
}
