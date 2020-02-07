package com.dsetanzania.dse.models;

import java.math.BigDecimal;

public class MarketSimulator {

    private String Board;

    private String Change;

    private String Close;

    private String Company;

    private String High;

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }

    public String getChange() {
        return Change;
    }

    public void setChange(String change) {
        Change = change;
    }

    public String getClose() {
        return Close;
    }

    public void setClose(String close) {
        Close = close;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getHigh() {
        return High;
    }

    public void setHigh(String high) {
        High = high;
    }

    public String getLastDealPrice() {
        return LastDealPrice;
    }

    public void setLastDealPrice(String lastDealPrice) {
        LastDealPrice = lastDealPrice;
    }

    public String getLastTradedQuantity() {
        return LastTradedQuantity;
    }

    public void setLastTradedQuantity(String lastTradedQuantity) {
        LastTradedQuantity = lastTradedQuantity;
    }

    public String getLow() {
        return Low;
    }

    public void setLow(String low) {
        Low = low;
    }

    public String getMarketCap() {
        return MarketCap;
    }

    public void setMarketCap(String marketCap) {
        MarketCap = marketCap;
    }

    public String getOpeningPrice() {
        return OpeningPrice;
    }

    public void setOpeningPrice(String openingPrice) {
        OpeningPrice = openingPrice;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public MarketSimulator(String board, String change, String close, String company, String high, String lastDealPrice, String lastTradedQuantity, String low, String marketCap, String openingPrice, String volume) {
        Board = board;
        Change = change;
        Close = close;
        Company = company;
        High = high;
        LastDealPrice = lastDealPrice;
        LastTradedQuantity = lastTradedQuantity;
        Low = low;
        MarketCap = marketCap;
        OpeningPrice = openingPrice;
        Volume = volume;
    }

    private String LastDealPrice;

    private String LastTradedQuantity;

    private String Low;

    private String MarketCap;

    private String OpeningPrice;

    private String Volume;




    public MarketSimulator() {


    }

}
