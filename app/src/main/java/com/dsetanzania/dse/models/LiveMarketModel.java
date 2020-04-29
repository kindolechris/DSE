package com.dsetanzania.dse.models;

import java.math.BigDecimal;
import java.util.Date;

public class LiveMarketModel {


    public String Board;

    public BigDecimal Change;

    public BigDecimal Close;

    public String Company;

    public BigDecimal High;

    public BigDecimal LastDealPrice;

    public Long LastTradedQuantity;

    public BigDecimal Low;

    public BigDecimal MarketCap;

    public BigDecimal OpeningPrice;

    public java.util.Date Time;

    public Long Volume;

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }

    public BigDecimal getChange() {
        return Change;
    }

    public void setChange(BigDecimal change) {
        Change = change;
    }

    public BigDecimal getClose() {
        return Close;
    }

    public void setClose(BigDecimal close) {
        Close = close;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public BigDecimal getHigh() {
        return High;
    }

    public void setHigh(BigDecimal high) {
        High = high;
    }

    public BigDecimal getLastDealPrice() {
        return LastDealPrice;
    }

    public void setLastDealPrice(BigDecimal lastDealPrice) {
        LastDealPrice = lastDealPrice;
    }

    public Long getLastTradedQuantity() {
        return LastTradedQuantity;
    }

    public void setLastTradedQuantity(Long lastTradedQuantity) {
        LastTradedQuantity = lastTradedQuantity;
    }

    public BigDecimal getLow() {
        return Low;
    }

    public void setLow(BigDecimal low) {
        Low = low;
    }

    public BigDecimal getMarketCap() {
        return MarketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        MarketCap = marketCap;
    }

    public BigDecimal getOpeningPrice() {
        return OpeningPrice;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        OpeningPrice = openingPrice;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    public Long getVolume() {
        return Volume;
    }

    public void setVolume(Long volume) {
        Volume = volume;
    }

    public LiveMarketModel(String board, BigDecimal change, BigDecimal close, String company, BigDecimal high, BigDecimal lastDealPrice, Long lastTradedQuantity, BigDecimal low, BigDecimal marketCap, BigDecimal openingPrice, Date time, Long volume) {
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
        Time = time;
        Volume = volume;
    }

}
