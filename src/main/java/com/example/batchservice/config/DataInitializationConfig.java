package com.example.batchservice.config;

import com.example.batchservice.constants.NaverSymbolConstants;
import com.example.common.Market;
import com.example.common.Stock;
import com.example.batchservice.repository.MarketRepository;
import com.example.batchservice.repository.StockRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class DataInitializationConfig {

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private StockRepository stockRepository;

    @PostConstruct
    public void initData() {
        initializeMarkets();
        try {
            initializeStocks();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeMarkets() {
        createMarketIfNotExists(NaverSymbolConstants.Market.KOSPI);
        createMarketIfNotExists(NaverSymbolConstants.Market.KOSDAQ);
    }

    private void createMarketIfNotExists(String marketName) {
        Optional<Market> marketOptional = marketRepository.findByName(marketName);
        if (marketOptional.isEmpty()) {
            Market market = new Market();
            market.setName(marketName);
            marketRepository.save(market);
        }
    }

    private void initializeStocks() throws IOException {
        List<String[]> kospiStocks = fetchStockCodes(0);
        List<String[]> kosdaqStocks = fetchStockCodes(1);

        for (String[] stock : kospiStocks) {
            createStockIfNotExists(stock[0], stock[1], NaverSymbolConstants.Market.KOSPI);
        }

        for (String[] stock : kosdaqStocks) {
            createStockIfNotExists(stock[0], stock[1], NaverSymbolConstants.Market.KOSDAQ);
        }
    }

    private void createStockIfNotExists(String code, String name, String marketName) {
        Optional<Stock> stockOptional = stockRepository.findByCode(code);
        if (stockOptional.isEmpty()) {
            Optional<Market> marketOptional = marketRepository.findByName(marketName);
            if (marketOptional.isPresent()) {
                Stock stock = new Stock();
                stock.setCode(code);
                stock.setName(name);
                stock.setMarket(marketOptional.get());
                stockRepository.save(stock);
            } else {
                System.err.println("Market with name " + marketName + " not found in the database.");
            }
        }
    }

    private List<String[]> fetchStockCodes(int market) throws IOException {
        List<String[]> stockCodes = new ArrayList<>();
        int page = 1;

        while (true) {
            String url = String.format(NaverSymbolConstants.STOCK_URL + "?sosok=%d&page=%d", market, page);
            Document doc = Jsoup.connect(url).get();

            Elements rows = doc.select(".type_2 tbody tr");
            boolean pageHasData = false;
            for (Element row : rows) {
                Elements columns = row.select("td");
                if (columns.size() > 1) {
                    Element codeTag = columns.get(1).selectFirst("a");
                    if (codeTag != null) {
                        pageHasData = true;
                        String code = codeTag.attr("href").split("=")[1];
                        String name = codeTag.text().trim();
                        stockCodes.add(new String[]{code, name});
                    }
                }
            }

            if (!pageHasData) {
                break;
            }

            page++;
        }

        return stockCodes;
    }
}
