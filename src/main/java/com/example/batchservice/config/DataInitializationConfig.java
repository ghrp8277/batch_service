package com.example.batchservice.config;

import com.example.batchservice.constants.NaverSymbolConstants;
import com.example.batchservice.entity.Market;
import com.example.batchservice.entity.Stock;
import com.example.batchservice.repository.MarketRepository;
import com.example.batchservice.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
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
        initializeStocks();
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

    private void initializeStocks() {
        NaverSymbolConstants.KOSPI_SYMBOLS.forEach(symbol -> createStockIfNotExists(symbol, getKospiStockName(symbol), NaverSymbolConstants.Market.KOSPI));
        NaverSymbolConstants.KOSDAQ_SYMBOLS.forEach(symbol -> createStockIfNotExists(symbol, getKosdaqStockName(symbol), NaverSymbolConstants.Market.KOSDAQ));
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

    private String getKospiStockName(String symbol) {
        switch (symbol) {
            case NaverSymbolConstants.KOSPI.SAMSUNG_ELECTRONIC: return "삼성전자";
            case NaverSymbolConstants.KOSPI.LG_ENERGY_SOLUTION: return "LG에너지솔루션";
            case NaverSymbolConstants.KOSPI.ECOPRO_MATERIALS: return "에코프로머티리얼즈";
            case NaverSymbolConstants.KOSPI.SK_HYNIX: return "SK하이닉스";
            case NaverSymbolConstants.KOSPI.LNF: return "LNF";
            case NaverSymbolConstants.KOSPI.HYUNDAI_MOTOR: return "현대차";
            case NaverSymbolConstants.KOSPI.SAMSUNG_BIOLOGICS: return "삼성바이오로직스";
            case NaverSymbolConstants.KOSPI.SAMSUNG_ELECTRONIC_PREFERRED: return "삼성전자우";
            case NaverSymbolConstants.KOSPI.KIA: return "기아";
            case NaverSymbolConstants.KOSPI.CELLTRION: return "셀트리온";
            case NaverSymbolConstants.KOSPI.KB_FINANCIAL_GROUP: return "KB금융";
            case NaverSymbolConstants.KOSPI.POSCO_HOLDINGS: return "POSCO홀딩스";
            case NaverSymbolConstants.KOSPI.SHINHAN_FINANCIAL_GROUP: return "신한지주";
            case NaverSymbolConstants.KOSPI.NAVER: return "NAVER";
            case NaverSymbolConstants.KOSPI.SAMSUNG_C_T: return "삼성물산";
            case NaverSymbolConstants.KOSPI.SAMSUNG_SDI: return "삼성SDI";
            case NaverSymbolConstants.KOSPI.LG_CHEM: return "LG화학";
            case NaverSymbolConstants.KOSPI.HYUNDAI_MOBIS: return "현대모비스";
            case NaverSymbolConstants.KOSPI.POSCO_FUTURE_M: return "포스코퓨처엠";
            case NaverSymbolConstants.KOSPI.HANA_FINANCIAL_GROUP: return "하나금융지주";
            case NaverSymbolConstants.KOSPI.SAMSUNG_LIFE_INSURANCE: return "삼성생명";
            case NaverSymbolConstants.KOSPI.KAKAO: return "카카오";
            case NaverSymbolConstants.KOSPI.SAMSUNG_FIRE_MARINE_INSURANCE: return "삼성화재";
            case NaverSymbolConstants.KOSPI.LG_ELECTRONICS: return "LG전자";
            case NaverSymbolConstants.KOSPI.MERITZ_FINANCIAL_GROUP: return "메리츠금융지주";
            case NaverSymbolConstants.KOSPI.HANMI_SEMICONDUCTOR: return "한미반도체";
            case NaverSymbolConstants.KOSPI.HMM: return "HMM";
            case NaverSymbolConstants.KOSPI.HD_HYUNDAI_HEAVY_INDUSTRIES: return "HD현대중공업";
            case NaverSymbolConstants.KOSPI.KRAFTON: return "크래프톤";
            case NaverSymbolConstants.KOSPI.LG: return "LG";
            case NaverSymbolConstants.KOSPI.SK_SQUARE: return "SK스퀘어";
            case NaverSymbolConstants.KOSPI.DOOSAN_ENERBILITY: return "두산에너빌리티";
            case NaverSymbolConstants.KOSPI.KOREA_ELECTRIC_POWER_CORPORATION: return "한국전력";
            case NaverSymbolConstants.KOSPI.HANWHA_AEROSPACE: return "한화에어로스페이스";
            case NaverSymbolConstants.KOSPI.SAMSUNG_SDS: return "삼성에스디에스";
            case NaverSymbolConstants.KOSPI.SAMSUNG_ELECTRO_MECHANICS: return "삼성전기";
            case NaverSymbolConstants.KOSPI.KT_G: return "KT&G";
            case NaverSymbolConstants.KOSPI.INDUSTRIAL_BANK_OF_KOREA: return "기업은행";
            case NaverSymbolConstants.KOSPI.HD_KOREA_SHIPBUILDING: return "HD한국조선해양";
            case NaverSymbolConstants.KOSPI.HD_HYUNDAI_ELECTRIC: return "HD현대일렉트릭";
            case NaverSymbolConstants.KOSPI.SK: return "SK";
            case NaverSymbolConstants.KOSPI.KOREA_ZINC: return "고려아연";
            case NaverSymbolConstants.KOSPI.SK_TELECOM: return "SK텔레콤";
            case NaverSymbolConstants.KOSPI.WOORI_FINANCIAL_GROUP: return "우리금융지주";
            case NaverSymbolConstants.KOSPI.SK_INNOVATION: return "SK이노베이션";
            case NaverSymbolConstants.KOSPI.KAKAO_BANK: return "카카오뱅크";
            case NaverSymbolConstants.KOSPI.POSCO_INTERNATIONAL: return "포스코인터내셔널";
            case NaverSymbolConstants.KOSPI.HYUNDAI_GLOVIS: return "현대글로비스";
            case NaverSymbolConstants.KOSPI.HANWHA_OCEAN: return "한화오션";
            case NaverSymbolConstants.KOSPI.KT: return "KT";
            case NaverSymbolConstants.KOSPI.KODEX_CD_INTEREST_RATE_ACTIVE: return "KODEX CD금리액티브(합성)";
            case NaverSymbolConstants.KOSPI.AMOREPACIFIC: return "아모레퍼시픽";
            default: return "UNKNOWN";
        }
    }

    private String getKosdaqStockName(String symbol) {
        switch (symbol) {
            case NaverSymbolConstants.KOSDAQ.ECOPRO_BM: return "에코프로비엠";
            case NaverSymbolConstants.KOSDAQ.ALTEOGEN: return "알테오젠";
            case NaverSymbolConstants.KOSDAQ.ECOPRO: return "에코프로";
            case NaverSymbolConstants.KOSDAQ.HLB: return "HLB";
            case NaverSymbolConstants.KOSDAQ.ENCHEM: return "엔켐";
            case NaverSymbolConstants.KOSDAQ.SAMCHUNDANG_PHARM: return "삼천당제약";
            case NaverSymbolConstants.KOSDAQ.CELLTRION_PHARM: return "셀트리온제약";
            case NaverSymbolConstants.KOSDAQ.LINO_INDUSTRIALS: return "리노공업";
            case NaverSymbolConstants.KOSDAQ.CLASSYS: return "클래시스";
            case NaverSymbolConstants.KOSDAQ.HPSP: return "HPSP";
            case NaverSymbolConstants.KOSDAQ.RAINBOW_ROBOTICS: return "레인보우로보틱스";
            case NaverSymbolConstants.KOSDAQ.PEARL_ABYSS: return "펄어비스";
            case NaverSymbolConstants.KOSDAQ.HUGEL: return "휴젤";
            case NaverSymbolConstants.KOSDAQ.LIG_CHEM_BIO: return "리가켐바이오";
            case NaverSymbolConstants.KOSDAQ.SILICON2: return "실리콘투";
            case NaverSymbolConstants.KOSDAQ.TECHWING: return "테크윙";
            case NaverSymbolConstants.KOSDAQ.EO_TECHNICS: return "이오테크닉스";
            case NaverSymbolConstants.KOSDAQ.SOLBRIEN: return "솔브레인";
            case NaverSymbolConstants.KOSDAQ.DAEJU_ELECTRONIC_MATERIALS: return "대주전자재료";
            case NaverSymbolConstants.KOSDAQ.DONGJIN_SEMI_CHEM: return "동진쎄미켐";
            case NaverSymbolConstants.KOSDAQ.JYP_ENT: return "JYP Ent.";
            case NaverSymbolConstants.KOSDAQ.WONIK_IPS: return "원익IPS";
            case NaverSymbolConstants.KOSDAQ.SEOJIN_SYSTEMS: return "서진시스템";
            case NaverSymbolConstants.KOSDAQ.SM_ENTERTAINMENT: return "에스엠";
            case NaverSymbolConstants.KOSDAQ.JUSUNG_ENGINEERING: return "주성엔지니어링";
            case NaverSymbolConstants.KOSDAQ.JNTC: return "제이앤티씨";
            case NaverSymbolConstants.KOSDAQ.SHINSEONG_DELTA_TECH: return "신성델타테크";
            case NaverSymbolConstants.KOSDAQ.SOLBRIEN_HOLDINGS: return "솔브레인홀딩스";
            case NaverSymbolConstants.KOSDAQ.ST_PHARM: return "에스티팜";
            case NaverSymbolConstants.KOSDAQ.KAKAO_GAMES: return "카카오게임즈";
            case NaverSymbolConstants.KOSDAQ.LS_MATERIALS: return "LS머트리얼즈";
            case NaverSymbolConstants.KOSDAQ.TCK: return "티씨케이";
            case NaverSymbolConstants.KOSDAQ.CJ_ENM: return "CJ ENM";
            case NaverSymbolConstants.KOSDAQ.OSCOTEC: return "오스코텍";
            case NaverSymbolConstants.KOSDAQ.PNT: return "피엔티";
            case NaverSymbolConstants.KOSDAQ.WEMADE: return "위메이드";
            case NaverSymbolConstants.KOSDAQ.YSY: return "와이씨";
            case NaverSymbolConstants.KOSDAQ.NANOSYS: return "나노신소재";
            case NaverSymbolConstants.KOSDAQ.PHARMARESEARCH: return "파마리서치";
            case NaverSymbolConstants.KOSDAQ.SOOP: return "SOOP";
            case NaverSymbolConstants.KOSDAQ.JERYONG_ELECTRIC: return "제룡전기";
            case NaverSymbolConstants.KOSDAQ.PARK_SYSTEMS: return "파크시스템스";
            case NaverSymbolConstants.KOSDAQ.ABL_BIO: return "에이비엘바이오";
            case NaverSymbolConstants.KOSDAQ.PSK_HOLDINGS: return "피에스케이홀딩스";
            case NaverSymbolConstants.KOSDAQ.LUNIT: return "루닛";
            case NaverSymbolConstants.KOSDAQ.ISC: return "ISC";
            case NaverSymbolConstants.KOSDAQ.PEPTONE: return "펩트론";
            case NaverSymbolConstants.KOSDAQ.HANAMATERIALS: return "하나머티리얼즈";
            case NaverSymbolConstants.KOSDAQ.NEXON_GAMES: return "넥슨게임즈";
            case NaverSymbolConstants.KOSDAQ.CNC_INTERNATIONAL: return "씨앤씨인터내셔널";
            default: return "UNKNOWN";
        }
    }
}
