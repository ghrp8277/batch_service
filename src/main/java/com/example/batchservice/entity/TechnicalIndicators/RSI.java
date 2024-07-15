package com.example.batchservice.entity.TechnicalIndicators;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Embeddable
@Data
public class RSI implements TechnicalIndicator {
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rsi_values", joinColumns = @JoinColumn(name = "stock_data_id"))
    @Column(name = "value")
    private List<Double> rsi = new ArrayList<>();

    @Override
    public void calculate(List<Double> prices) {
        List<Double> gains = new ArrayList<>(prices.size() - 1);
        List<Double> losses = new ArrayList<>(prices.size() - 1);
        for (int i = 1; i < prices.size(); i++) {
            double change = prices.get(i) - prices.get(i - 1);
            if (change > 0) {
                gains.add(change);
                losses.add(0.0);
            } else {
                gains.add(0.0);
                losses.add(-change);
            }
        }
        double avgGain = gains.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double avgLoss = losses.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        for (int i = 0; i < gains.size(); i++) {
            avgGain = (avgGain * 13 + gains.get(i)) / 14;
            avgLoss = (avgLoss * 13 + losses.get(i)) / 14;
            double rs = avgGain / avgLoss;
            double rsiValue = 100 - (100 / (1 + rs));
            rsi.add(rsiValue);
        }
    }

    @Override
    public Map<String, List<Double>> getResults() {
        Map<String, List<Double>> results = new HashMap<>();
        results.put("RSI", rsi);
        return results;
    }
}
