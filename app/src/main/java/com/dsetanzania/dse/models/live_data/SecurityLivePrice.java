package com.dsetanzania.dse.models.live_data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecurityLivePrice {

    @SerializedName("Board")
    @Expose
    private String board;
    @SerializedName("Change")
    @Expose
    private String change;
    @SerializedName("Close")
    @Expose
    private String close;
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("High")
    @Expose
    private String high;
    @SerializedName("LastDealPrice")
    @Expose
    private String lastDealPrice;
    @SerializedName("LastTradedQuantity")
    @Expose
    private Integer lastTradedQuantity;
    @SerializedName("Low")
    @Expose
    private String low;
    @SerializedName("MarketCap")
    @Expose
    private String marketCap;
    @SerializedName("OpeningPrice")
    @Expose
    private String openingPrice;
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("Volume")
    @Expose
    private Integer volume;

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLastDealPrice() {
        return lastDealPrice;
    }

    public void setLastDealPrice(String lastDealPrice) {
        this.lastDealPrice = lastDealPrice;
    }

    public Integer getLastTradedQuantity() {
        return lastTradedQuantity;
    }

    public void setLastTradedQuantity(Integer lastTradedQuantity) {
        this.lastTradedQuantity = lastTradedQuantity;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(String openingPrice) {
        this.openingPrice = openingPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

}