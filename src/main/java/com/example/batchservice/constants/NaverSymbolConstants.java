package com.example.batchservice.constants;

import java.util.List;

public class NaverSymbolConstants {
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

    public static class KOSPI {
        public static final String SAMSUNG_ELECTRONIC = "005930";
        public static final String LG_ENERGY_SOLUTION = "373220";
        public static final String ECOPRO_MATERIALS = "450080";
        public static final String SK_HYNIX = "000660";
        public static final String LNF = "066970";
        public static final String HYUNDAI_MOTOR = "005380";
        public static final String SAMSUNG_BIOLOGICS = "207940";
        public static final String SAMSUNG_ELECTRONIC_PREFERRED = "005935";
        public static final String KIA = "000270";
        public static final String CELLTRION = "068270";
        public static final String KB_FINANCIAL_GROUP = "105560";
        public static final String POSCO_HOLDINGS = "005490";
        public static final String SHINHAN_FINANCIAL_GROUP = "055550";
        public static final String NAVER = "035420";
        public static final String SAMSUNG_C_T = "028260";
        public static final String SAMSUNG_SDI = "006400";
        public static final String LG_CHEM = "051910";
        public static final String HYUNDAI_MOBIS = "012330";
        public static final String POSCO_FUTURE_M = "003670";
        public static final String HANA_FINANCIAL_GROUP = "086790";
        public static final String SAMSUNG_LIFE_INSURANCE = "032830";
        public static final String KAKAO = "035720";
        public static final String SAMSUNG_FIRE_MARINE_INSURANCE = "000810";
        public static final String LG_ELECTRONICS = "066570";
        public static final String MERITZ_FINANCIAL_GROUP = "138040";
        public static final String HANMI_SEMICONDUCTOR = "042700";
        public static final String HMM = "011200";
        public static final String HD_HYUNDAI_HEAVY_INDUSTRIES = "329180";
        public static final String KRAFTON = "259960";
        public static final String LG = "003550";
        public static final String SK_SQUARE = "402340";
        public static final String DOOSAN_ENERBILITY = "034020";
        public static final String KOREA_ELECTRIC_POWER_CORPORATION = "015760";
        public static final String HANWHA_AEROSPACE = "012450";
        public static final String SAMSUNG_SDS = "018260";
        public static final String SAMSUNG_ELECTRO_MECHANICS = "009150";
        public static final String KT_G = "033780";
        public static final String INDUSTRIAL_BANK_OF_KOREA = "024110";
        public static final String HD_KOREA_SHIPBUILDING = "009540";
        public static final String HD_HYUNDAI_ELECTRIC = "267260";
        public static final String SK = "034730";
        public static final String KOREA_ZINC = "010130";
        public static final String SK_TELECOM = "017670";
        public static final String WOORI_FINANCIAL_GROUP = "316140";
        public static final String SK_INNOVATION = "096770";
        public static final String KAKAO_BANK = "323410";
        public static final String POSCO_INTERNATIONAL = "047050";
        public static final String HYUNDAI_GLOVIS = "086280";
        public static final String HANWHA_OCEAN = "042660";
        public static final String KT = "030200";
        public static final String KODEX_CD_INTEREST_RATE_ACTIVE = "459580";
        public static final String AMOREPACIFIC = "090430";
    }

    public static class KOSDAQ {
        public static final String ECOPRO_BM = "247540";
        public static final String ALTEOGEN = "196170";
        public static final String ECOPRO = "086520";
        public static final String HLB = "028300";
        public static final String ENCHEM = "348370";
        public static final String SAMCHUNDANG_PHARM = "000250";
        public static final String CELLTRION_PHARM = "068760";
        public static final String LINO_INDUSTRIALS = "058470";
        public static final String CLASSYS = "214150";
        public static final String HPSP = "403870";
        public static final String RAINBOW_ROBOTICS = "277810";
        public static final String PEARL_ABYSS = "263750";
        public static final String HUGEL = "145020";
        public static final String LIG_CHEM_BIO = "141080";
        public static final String SILICON2 = "257720";
        public static final String TECHWING = "089030";
        public static final String EO_TECHNICS = "039030";
        public static final String SOLBRIEN = "357780";
        public static final String DAEJU_ELECTRONIC_MATERIALS = "078600";
        public static final String DONGJIN_SEMI_CHEM = "005290";
        public static final String JYP_ENT = "035900";
        public static final String WONIK_IPS = "240810";
        public static final String SEOJIN_SYSTEMS = "178320";
        public static final String SM_ENTERTAINMENT = "041510";
        public static final String JUSUNG_ENGINEERING = "036930";
        public static final String JNTC = "204270";
        public static final String SHINSEONG_DELTA_TECH = "065350";
        public static final String SOLBRIEN_HOLDINGS = "036830";
        public static final String ST_PHARM = "237690";
        public static final String KAKAO_GAMES = "293490";
        public static final String LS_MATERIALS = "417200";
        public static final String TCK = "064760";
        public static final String CJ_ENM = "035760";
        public static final String OSCOTEC = "039200";
        public static final String PNT = "137400";
        public static final String WEMADE = "112040";
        public static final String YSY = "232140";
        public static final String NANOSYS = "121600";
        public static final String PHARMARESEARCH = "214450";
        public static final String SOOP = "067160";
        public static final String JERYONG_ELECTRIC = "033100";
        public static final String PARK_SYSTEMS = "140860";
        public static final String ABL_BIO = "298380";
        public static final String PSK_HOLDINGS = "031980";
        public static final String LUNIT = "328130";
        public static final String ISC = "095340";
        public static final String PEPTONE = "087010";
        public static final String HANAMATERIALS = "166090";
        public static final String NEXON_GAMES = "225570";
        public static final String CNC_INTERNATIONAL = "352480";
    }

    public static final List<String> KOSPI_SYMBOLS = List.of(
            KOSPI.SAMSUNG_ELECTRONIC,
            KOSPI.LG_ENERGY_SOLUTION,
            KOSPI.ECOPRO_MATERIALS,
            KOSPI.SK_HYNIX,
            KOSPI.LNF,
            KOSPI.HYUNDAI_MOTOR,
            KOSPI.SAMSUNG_BIOLOGICS,
            KOSPI.SAMSUNG_ELECTRONIC_PREFERRED,
            KOSPI.KIA,
            KOSPI.CELLTRION,
            KOSPI.KB_FINANCIAL_GROUP,
            KOSPI.POSCO_HOLDINGS,
            KOSPI.SHINHAN_FINANCIAL_GROUP,
            KOSPI.NAVER,
            KOSPI.SAMSUNG_C_T,
            KOSPI.SAMSUNG_SDI,
            KOSPI.LG_CHEM,
            KOSPI.HYUNDAI_MOBIS,
            KOSPI.POSCO_FUTURE_M,
            KOSPI.HANA_FINANCIAL_GROUP,
            KOSPI.SAMSUNG_LIFE_INSURANCE,
            KOSPI.KAKAO,
            KOSPI.SAMSUNG_FIRE_MARINE_INSURANCE,
            KOSPI.LG_ELECTRONICS,
            KOSPI.MERITZ_FINANCIAL_GROUP,
            KOSPI.HANMI_SEMICONDUCTOR,
            KOSPI.HMM,
            KOSPI.HD_HYUNDAI_HEAVY_INDUSTRIES,
            KOSPI.KRAFTON,
            KOSPI.LG,
            KOSPI.SK_SQUARE,
            KOSPI.DOOSAN_ENERBILITY,
            KOSPI.KOREA_ELECTRIC_POWER_CORPORATION,
            KOSPI.HANWHA_AEROSPACE,
            KOSPI.SAMSUNG_SDS,
            KOSPI.SAMSUNG_ELECTRO_MECHANICS,
            KOSPI.KT_G,
            KOSPI.INDUSTRIAL_BANK_OF_KOREA,
            KOSPI.HD_HYUNDAI_HEAVY_INDUSTRIES,
            KOSPI.HD_HYUNDAI_ELECTRIC,
            KOSPI.SK,
            KOSPI.KOREA_ZINC,
            KOSPI.SK_TELECOM,
            KOSPI.WOORI_FINANCIAL_GROUP,
            KOSPI.SK_INNOVATION,
            KOSPI.KAKAO_BANK,
            KOSPI.POSCO_INTERNATIONAL,
            KOSPI.HYUNDAI_GLOVIS,
            KOSPI.HANWHA_OCEAN,
            KOSPI.KT,
            KOSPI.KODEX_CD_INTEREST_RATE_ACTIVE,
            KOSPI.AMOREPACIFIC
    );

    public static final List<String> KOSDAQ_SYMBOLS = List.of(
            KOSDAQ.ECOPRO_BM,
            KOSDAQ.ALTEOGEN,
            KOSDAQ.ECOPRO,
            KOSDAQ.HLB,
            KOSDAQ.ENCHEM,
            KOSDAQ.SAMCHUNDANG_PHARM,
            KOSDAQ.CELLTRION_PHARM,
            KOSDAQ.LINO_INDUSTRIALS,
            KOSDAQ.CLASSYS,
            KOSDAQ.HPSP,
            KOSDAQ.RAINBOW_ROBOTICS,
            KOSDAQ.PEARL_ABYSS,
            KOSDAQ.HUGEL,
            KOSDAQ.LIG_CHEM_BIO,
            KOSDAQ.SILICON2,
            KOSDAQ.TECHWING,
            KOSDAQ.EO_TECHNICS,
            KOSDAQ.SOLBRIEN,
            KOSDAQ.DAEJU_ELECTRONIC_MATERIALS,
            KOSDAQ.DONGJIN_SEMI_CHEM,
            KOSDAQ.JYP_ENT,
            KOSDAQ.WONIK_IPS,
            KOSDAQ.SEOJIN_SYSTEMS,
            KOSDAQ.SM_ENTERTAINMENT,
            KOSDAQ.JUSUNG_ENGINEERING,
            KOSDAQ.JNTC,
            KOSDAQ.SHINSEONG_DELTA_TECH,
            KOSDAQ.SOLBRIEN_HOLDINGS,
            KOSDAQ.ST_PHARM,
            KOSDAQ.KAKAO_GAMES,
            KOSDAQ.LS_MATERIALS,
            KOSDAQ.TCK,
            KOSDAQ.CJ_ENM,
            KOSDAQ.OSCOTEC,
            KOSDAQ.PNT,
            KOSDAQ.WEMADE,
            KOSDAQ.YSY,
            KOSDAQ.NANOSYS,
            KOSDAQ.PHARMARESEARCH,
            KOSDAQ.SOOP,
            KOSDAQ.JERYONG_ELECTRIC,
            KOSDAQ.PARK_SYSTEMS,
            KOSDAQ.ABL_BIO,
            KOSDAQ.PSK_HOLDINGS,
            KOSDAQ.LUNIT,
            KOSDAQ.ISC,
            KOSDAQ.PEPTONE,
            KOSDAQ.HANAMATERIALS,
            KOSDAQ.NEXON_GAMES,
            KOSDAQ.CNC_INTERNATIONAL
    );

    public static final List<String> ALL_SYMBOLS = List.of(
            KOSPI.SAMSUNG_ELECTRONIC,
            KOSPI.LG_ENERGY_SOLUTION,
            KOSPI.ECOPRO_MATERIALS,
            KOSPI.SK_HYNIX,
            KOSPI.LNF,
            KOSPI.HYUNDAI_MOTOR,
            KOSPI.SAMSUNG_BIOLOGICS,
            KOSPI.SAMSUNG_ELECTRONIC_PREFERRED,
            KOSPI.KIA,
            KOSPI.CELLTRION,
            KOSPI.KB_FINANCIAL_GROUP,
            KOSPI.POSCO_HOLDINGS,
            KOSPI.SHINHAN_FINANCIAL_GROUP,
            KOSPI.NAVER,
            KOSPI.SAMSUNG_C_T,
            KOSPI.SAMSUNG_SDI,
            KOSPI.LG_CHEM,
            KOSPI.HYUNDAI_MOBIS,
            KOSPI.POSCO_FUTURE_M,
            KOSPI.HANA_FINANCIAL_GROUP,
            KOSPI.SAMSUNG_LIFE_INSURANCE,
            KOSPI.KAKAO,
            KOSPI.SAMSUNG_FIRE_MARINE_INSURANCE,
            KOSPI.LG_ELECTRONICS,
            KOSPI.MERITZ_FINANCIAL_GROUP,
            KOSPI.HANMI_SEMICONDUCTOR,
            KOSPI.HMM,
            KOSPI.HD_HYUNDAI_HEAVY_INDUSTRIES,
            KOSPI.KRAFTON,
            KOSPI.LG,
            KOSPI.SK_SQUARE,
            KOSPI.DOOSAN_ENERBILITY,
            KOSPI.KOREA_ELECTRIC_POWER_CORPORATION,
            KOSPI.HANWHA_AEROSPACE,
            KOSPI.SAMSUNG_SDS,
            KOSPI.SAMSUNG_ELECTRO_MECHANICS,
            KOSPI.KT_G,
            KOSPI.INDUSTRIAL_BANK_OF_KOREA,
            KOSPI.HD_KOREA_SHIPBUILDING,
            KOSPI.HD_HYUNDAI_ELECTRIC,
            KOSPI.SK,
            KOSPI.KOREA_ZINC,
            KOSPI.SK_TELECOM,
            KOSPI.WOORI_FINANCIAL_GROUP,
            KOSPI.SK_INNOVATION,
            KOSPI.KAKAO_BANK,
            KOSPI.POSCO_INTERNATIONAL,
            KOSPI.HYUNDAI_GLOVIS,
            KOSPI.HANWHA_OCEAN,
            KOSPI.KT,
            KOSPI.KODEX_CD_INTEREST_RATE_ACTIVE,
            KOSPI.AMOREPACIFIC,
            KOSDAQ.ECOPRO_BM,
            KOSDAQ.ALTEOGEN,
            KOSDAQ.ECOPRO,
            KOSDAQ.HLB,
            KOSDAQ.ENCHEM,
            KOSDAQ.SAMCHUNDANG_PHARM,
            KOSDAQ.CELLTRION_PHARM,
            KOSDAQ.LINO_INDUSTRIALS,
            KOSDAQ.CLASSYS,
            KOSDAQ.HPSP,
            KOSDAQ.RAINBOW_ROBOTICS,
            KOSDAQ.PEARL_ABYSS,
            KOSDAQ.HUGEL,
            KOSDAQ.LIG_CHEM_BIO,
            KOSDAQ.SILICON2,
            KOSDAQ.TECHWING,
            KOSDAQ.EO_TECHNICS,
            KOSDAQ.SOLBRIEN,
            KOSDAQ.DAEJU_ELECTRONIC_MATERIALS,
            KOSDAQ.DONGJIN_SEMI_CHEM,
            KOSDAQ.JYP_ENT,
            KOSDAQ.WONIK_IPS,
            KOSDAQ.SEOJIN_SYSTEMS,
            KOSDAQ.SM_ENTERTAINMENT,
            KOSDAQ.JUSUNG_ENGINEERING,
            KOSDAQ.JNTC,
            KOSDAQ.SHINSEONG_DELTA_TECH,
            KOSDAQ.SOLBRIEN_HOLDINGS,
            KOSDAQ.ST_PHARM,
            KOSDAQ.KAKAO_GAMES,
            KOSDAQ.LS_MATERIALS,
            KOSDAQ.TCK,
            KOSDAQ.CJ_ENM,
            KOSDAQ.OSCOTEC,
            KOSDAQ.PNT,
            KOSDAQ.WEMADE,
            KOSDAQ.YSY,
            KOSDAQ.NANOSYS,
            KOSDAQ.PHARMARESEARCH,
            KOSDAQ.SOOP,
            KOSDAQ.JERYONG_ELECTRIC,
            KOSDAQ.PARK_SYSTEMS,
            KOSDAQ.ABL_BIO,
            KOSDAQ.PSK_HOLDINGS,
            KOSDAQ.LUNIT,
            KOSDAQ.ISC,
            KOSDAQ.PEPTONE,
            KOSDAQ.HANAMATERIALS,
            KOSDAQ.NEXON_GAMES,
            KOSDAQ.CNC_INTERNATIONAL
    );
}
