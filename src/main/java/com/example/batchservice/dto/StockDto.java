package com.example.batchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {
    private String date;
    private int openPrice;
    private int highPrice;
    private int lowPrice;
    private int closePrice;
    private int volume;
}
