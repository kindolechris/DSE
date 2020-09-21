package com.dsetanzania.dse.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoardSharesModel {

    @SerializedName("_id")
    @Expose
    private String Id;

    @SerializedName("board")
    @Expose
    private String Board;

    @SerializedName("change")
    @Expose
    private String Change;

    @SerializedName("close")
    @Expose
    private String Close;

    @SerializedName("company")
    @Expose
    private String Company;

    @SerializedName("high")
    @Expose
    private String High;

    @SerializedName("lastDealPrice")
    @Expose
    private String LastDealPrice;

    @SerializedName("lastTradedQuantity")
    @Expose
    private String LastTradedQuantity;

    @SerializedName("low")
    @Expose
    private String Low;

    @SerializedName("marketCap")
    @Expose
    private String MarketCap;

    @SerializedName("openingPrice")
    @Expose
    private String OpeningPrice;
    @SerializedName("Time")
    @Expose
    private String time;

    @SerializedName("volume")
    @Expose
    private String Volume;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public BoardSharesModel() {

    }

}
