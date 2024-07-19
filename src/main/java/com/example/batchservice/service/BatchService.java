package com.example.batchservice.service;

import com.example.batchservice.constants.NaverSymbolConstants;
import com.example.batchservice.constants.ThreadPoolConstants;
import com.example.batchservice.dto.StockDto;
import com.example.batchservice.entity.Stock;
import com.example.batchservice.entity.StockData;
import com.example.batchservice.repository.StockDataRepository;
import com.example.batchservice.repository.StockRepository;
import com.example.batchservice.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BatchService {
    private static final Logger logger = LoggerFactory.getLogger(BatchService.class);

    public static final int MAX_DAYS_DATA_COUNT = 1250;
    public static final int DAILY_DATA_COUNT = 1;

    @Autowired
    private ThreadPoolExecutor executorService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private IndicatorCalculationService indicatorCalculationService;

    private double averageProcessingTimePerStock = 0;

    private int calculateOptimalThreadCount() {
        int numCores = Runtime.getRuntime().availableProcessors();
        long maxMemory = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
        if (usedMemory <= 0) {
            usedMemory = maxMemory / 10;
        }
        int memoryPerThread = (int) usedMemory / (numCores * 2);

        double averageArrivalRate = (double) numCores / averageProcessingTimePerStock;
        int optimalThreadCount = (int) Math.min(averageArrivalRate, maxMemory / memoryPerThread);
        return Math.min(optimalThreadCount, numCores * 2);
    }

    public void adjustThreadPoolSize() {
        try {
            int optimalThreadCount = calculateOptimalThreadCount();
            executorService.setCorePoolSize(optimalThreadCount);
            executorService.setMaximumPoolSize(optimalThreadCount);
        } catch (Exception e) {
            executorService.setCorePoolSize(ThreadPoolConstants.THREAD_POOL_CORE_SIZE);
            executorService.setMaximumPoolSize(ThreadPoolConstants.THREAD_POOL_MAX_SIZE);
        }
    }

    private long measureSingleTaskTime(String symbol) {
        long startTime = System.currentTimeMillis();
        try {
            List<StockDto> stockDataList = getStockDataWithRetry(symbol, NaverSymbolConstants.TimeFrame.DAY, MAX_DAYS_DATA_COUNT, ThreadPoolConstants.RETRY_COUNT);
            saveStockData(stockDataList, symbol);
        } catch (Exception e) {
            logger.error("Error processing symbol: " + symbol, e);
        }
        return System.currentTimeMillis() - startTime;
    }

    public void measureSingleTaskTime() {
        Optional<Stock> firstStockOptional = stockRepository.findAll().stream().findFirst();
        if (firstStockOptional.isEmpty()) return;

        Stock firstStock = firstStockOptional.get();
        String symbol = firstStock.getCode();
        long singleTaskTime = measureSingleTaskTime(symbol);
        averageProcessingTimePerStock = singleTaskTime / 1000.0;

        logger.info("Measured single task time: {} ms", singleTaskTime);
    }

    public void collectAndSaveInitialData() {
        List<Stock> stocks = stockRepository.findAll();
        if (stocks.isEmpty()) return;
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 1; i < stocks.size(); i++) {
            String symbol = stocks.get(i).getCode();
            Future<?> future = executorService.submit(() -> {
                try {
                    List<StockDto> stockDataList = getStockDataWithRetry(symbol, NaverSymbolConstants.TimeFrame.DAY, MAX_DAYS_DATA_COUNT, ThreadPoolConstants.RETRY_COUNT);
                    saveStockData(stockDataList, symbol);
                } catch (Exception e) {
                    logger.error("Error processing symbol: " + symbol, e);
                }
            });
            futures.add(future);
        }

        waitForCompletion(futures);
        shutdownExecutorService();
    }

    public void collectAndSaveDailyData() {
        List<Stock> stocks = stockRepository.findAll();
        if (stocks.isEmpty()) return;
        List<Future<?>> futures = new ArrayList<>();

        for (Stock stock : stocks) {
            String symbol = stock.getCode();
            Future<?> future = executorService.submit(() -> {
                try {
                    List<StockDto> stockDataList = getStockDataWithRetry(symbol, NaverSymbolConstants.TimeFrame.DAY, DAILY_DATA_COUNT, ThreadPoolConstants.RETRY_COUNT);
                    saveStockData(stockDataList, symbol);
                } catch (Exception e) {
                    logger.error("Error processing symbol: " + symbol, e);
                }
            });
            futures.add(future);
        }

        waitForCompletion(futures);
        shutdownExecutorService();

        logger.info("일간 주가 데이터를 수집하고 저장합니다.");
    }

     public void calculateIndicatorsForAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        ExecutorService executorService = createExecutorService();
        submitStockTasks(executorService, stocks);
        awaitTermination(executorService);
    }

    private ExecutorService createExecutorService() {
        int numCores = Runtime.getRuntime().availableProcessors();
        long maxMemory = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
        int memoryPerThread = (int) usedMemory / (numCores * 2);
        int optimalThreadCount = calculateOptimalThreadCount(numCores, maxMemory, memoryPerThread);
        return Executors.newFixedThreadPool(optimalThreadCount);
    }

    private int calculateOptimalThreadCount(int numCores, long maxMemory, int memoryPerThread) {
//        if (memoryPerThread == 0) {
//            logger.warn("Memory per thread is zero, defaulting to number of CPU cores");
//            return numCores * 2;
//        }

        int memoryBasedThreadCount = (int) (maxMemory / memoryPerThread);
        int cpuBasedThreadCount = numCores * 2;

        return Math.min(cpuBasedThreadCount, memoryBasedThreadCount);
    }

    private void submitStockTasks(ExecutorService executorService, List<Stock> stocks) {
        for (Stock stock : stocks) {
            executorService.submit(() -> {
                try {
                    indicatorCalculationService.calculateIndicatorsForStockWithRetry(stock);
                } catch (Exception e) {
                    logger.error("Error calculating indicators for stock: " + stock.getCode(), e);
                }
            });
        }
    }

    private void awaitTermination(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.MINUTES)) { // 적절한 시간으로 조정
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.error("ExecutorService did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void waitForCompletion(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Error waiting for task completion", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void shutdownExecutorService() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(ThreadPoolConstants.EXECUTOR_SHUTDOWN_WAIT_TIME, TimeUnit.HOURS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(ThreadPoolConstants.EXECUTOR_SHUTDOWN_NOW_WAIT_TIME, TimeUnit.MINUTES)) {
                    logger.error("ExecutorService did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public List<StockDto> getStockDataWithRetry(String symbol, String timeframe, int count, int maxRetries) throws Exception {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                return getStockData(symbol, timeframe, count);
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw e;
                }
                logger.warn("Retrying getStockData for symbol: " + symbol + ", attempt: " + attempt);
                Thread.sleep(ThreadPoolConstants.RETRY_SLEEP_TIME);
            }
        }
        return Collections.emptyList();
    }

    public List<StockDto> getStockData(String symbol, String timeframe, int count) throws Exception {
        String url = String.format("%s?symbol=%s&timeframe=%s&count=%d&requestType=0",
                NaverSymbolConstants.BASE_URL, symbol, timeframe, count);

        String response = HttpUtil.sendGetRequest(url);
        return parseXml(response);
    }

    private List<StockDto> parseXml(String xml) throws Exception {
        List<StockDto> stockDataList = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        Document document = builder.parse(is);

        NodeList items = document.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);
            String data = item.getAttribute("data");
            String[] values = data.split("\\|");

            StockDto stockData = new StockDto(
                    values[0],
                    Integer.parseInt(values[1]),
                    Integer.parseInt(values[2]),
                    Integer.parseInt(values[3]),
                    Integer.parseInt(values[4]),
                    Integer.parseInt(values[5])
            );
            stockDataList.add(stockData);
        }

        return stockDataList;
    }

    public void saveStockData(List<StockDto> stockDataList, String symbol) {
        Optional<Stock> optionalStock = stockRepository.findByCode(symbol);

        if (optionalStock.isEmpty()) {
            System.err.println("Stock with code " + symbol + " not found in the database.");
            return;
        }

        Stock stock = optionalStock.get();

        List<StockData> existingStockDataList = stockDataRepository.findByStock(stock);
        Map<String, StockData> existingStockDataMap = new HashMap<>();
        for (StockData existingStockData : existingStockDataList) {
            existingStockDataMap.put(existingStockData.getDate(), existingStockData);
        }

        List<StockData> stockDataEntities = new ArrayList<>();

        for (StockDto stockDto : stockDataList) {
            StockData stockData = existingStockDataMap.get(stockDto.getDate());
            if (stockData == null) {
                stockData = new StockData();
                stockData.setDate(stockDto.getDate());
                stockData.setOpenPrice(stockDto.getOpenPrice());
                stockData.setHighPrice(stockDto.getHighPrice());
                stockData.setLowPrice(stockDto.getLowPrice());
                stockData.setClosePrice(stockDto.getClosePrice());
                stockData.setVolume(stockDto.getVolume());
                stockData.setStock(stock);
                stockDataEntities.add(stockData);
            } else {
                boolean updated = false;
                if (stockData.getOpenPrice() == null) {
                    stockData.setOpenPrice(stockDto.getOpenPrice());
                    updated = true;
                }
                if (stockData.getHighPrice() == null) {
                    stockData.setHighPrice(stockDto.getHighPrice());
                    updated = true;
                }
                if (stockData.getLowPrice() == null) {
                    stockData.setLowPrice(stockDto.getLowPrice());
                    updated = true;
                }
                if (stockData.getClosePrice() == null) {
                    stockData.setClosePrice(stockDto.getClosePrice());
                    updated = true;
                }
                if (stockData.getVolume() == null) {
                    stockData.setVolume(stockDto.getVolume());
                    updated = true;
                }
                if (updated) {
                    stockDataEntities.add(stockData);
                }
            }
        }

        if (!stockDataEntities.isEmpty()) {
            stockDataRepository.saveAll(stockDataEntities);
        }
    }
}
