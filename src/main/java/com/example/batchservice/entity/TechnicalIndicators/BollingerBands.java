package com.example.batchservice.entity.TechnicalIndicators;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Embeddable
@Data
public class BollingerBands implements TechnicalIndicator {
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "bollinger_bands_upper", joinColumns = @JoinColumn(name = "stock_data_id"))
    @Column(name = "value")
    private List<Double> upperBand;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "bollinger_bands_middle", joinColumns = @JoinColumn(name = "stock_data_id"))
    @Column(name = "value")
    private List<Double> middleBand;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "bollinger_bands_lower", joinColumns = @JoinColumn(name = "stock_data_id"))
    @Column(name = "value")
    private List<Double> lowerBand;

    @Override
    public void calculate(List<Double> prices) {
        MovingAverage ma = new MovingAverage();
        ma.calculate(prices);
        List<Double> sma20 = ma.getResults().get("SMA20");
        calculateBollingerBands(prices, sma20);
    }

    private void calculateBollingerBands(List<Double> prices, List<Double> sma20) {
        upperBand = new ArrayList<>(sma20.size());
        lowerBand = new ArrayList<>(sma20.size());

        for (int i = 0; i < sma20.size(); i++) {
            int startIndex = Math.max(i - 19, 0);
            double stddev = calculateStandardDeviation(prices.subList(startIndex, i + 1));
            upperBand.add(sma20.get(i) + 2 * stddev);
            lowerBand.add(sma20.get(i) - 2 * stddev);
        }

        middleBand = sma20;
    }

    private double calculateStandardDeviation(List<Double> values) {
        double mean = values.stream().mapToDouble(val -> val).average().orElse(0.0);
        double variance = values.stream().mapToDouble(val -> Math.pow(val - mean, 2)).average().orElse(0.0);
        return Math.sqrt(variance);
    }

    @Override
    public Map<String, List<Double>> getResults() {
        Map<String, List<Double>> results = new HashMap<>();
        results.put("UpperBand", upperBand);
        results.put("MiddleBand", middleBand);
        results.put("LowerBand", lowerBand);
        return results;
    }
}
