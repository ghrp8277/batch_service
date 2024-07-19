package com.example.batchservice.constants;

import java.util.List;

public class NaverSymbolConstants {
    public static final String STOCK_URL = "https://finance.naver.com/sise/sise_market_sum.nhn";
    public static final String BASE_URL = "https://fchart.stock.naver.com/sise.nhn";

    public static class TimeFrame {
        public static final String DAY = "day";
        public static final String WEEK = "week";
        public static final String MONTH = "month";
        public static final String MINUTE = "minute";
    }

    public static class Market {
        public static final String KOSPI = "KOSPI";
        public static final String KOSDAQ = "KOSDAQ";
    }
}
