package com.dsetanzania.dse.models;

import java.math.BigDecimal;

public class MarketSimulator {

    private String Board;

    private Double Change;

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }

    public Double getChange() {
        return Change;
    }

    public void setChange(Double change) {
        Change = change;
    }

    public Double getClose() {
        return Close;
    }

    public void setClose(Double close) {
        Close = close;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public Double getHigh() {
        return High;
    }

    public void setHigh(Double high) {
        High = high;
    }

    public Double getLastDealPrice() {
        return LastDealPrice;
    }

    public void setLastDealPrice(Double lastDealPrice) {
        LastDealPrice = lastDealPrice;
    }

    public Long getLastTradedQuantity() {
        return LastTradedQuantity;
    }

    public void setLastTradedQuantity(Long lastTradedQuantity) {
        LastTradedQuantity = lastTradedQuantity;
    }

    public Double getLow() {
        return Low;
    }

    public void setLow(Double low) {
        Low = low;
    }

    public Double getMarketCap() {
        return MarketCap;
    }

    public void setMarketCap(Double marketCap) {
        MarketCap = marketCap;
    }

    public Double getOpeningPrice() {
        return OpeningPrice;
    }

    public void setOpeningPrice(Double openingPrice) {
        OpeningPrice = openingPrice;
    }

    public Long getVolume() {
        return Volume;
    }

    public void setVolume(Long volume) {
        Volume = volume;
    }

    public MarketSimulator(String board, Double change, Double close, String company, Double high, Double lastDealPrice, Long lastTradedQuantity, Double low, Double marketCap, Double openingPrice, Long volume) {
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


    public MarketSimulator() {


    }


    private Double Close;

    private String Company;

    private Double High;

    private Double LastDealPrice;

    private Long LastTradedQuantity;

    private Double Low;

    private Double MarketCap;

    private Double OpeningPrice;

    private Long Volume;

}
