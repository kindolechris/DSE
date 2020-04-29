package com.dsetanzania.dse.models;

import com.google.gson.annotations.SerializedName;

public class NewsFeedsModel {

    @SerializedName("symbol")
    private String symbol;
    @SerializedName("name")
    private String name;
    @SerializedName("currency")
    private String currency;
    @SerializedName("price")
    private String price;
    @SerializedName("price_open")
    private String priceOpen;
    @SerializedName("day_high")
    private String dayHigh;
    @SerializedName("day_low")
    private String dayLow;
    @SerializedName("52_week_high")
    private String _52WeekHigh;
    @SerializedName("52_week_low")
    private String _52WeekLow;
    @SerializedName("day_change")
    private String dayChange;
    @SerializedName("change_pct")
    private String changePct;
    @SerializedName("close_yesterday")
    private String closeYesterday;
    @SerializedName("market_cap")
    private String marketCap;
    @SerializedName("volume")
    private String volume;
    @SerializedName("volume_avg")
    private String volumeAvg;
    @SerializedName("shares")
    private String shares;
    @SerializedName("stock_exchange_long")
    private String stockExchangeLong;
    @SerializedName("stock_exchange_short")
    private String stockExchangeShort;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("timezone_name")
    private String timezoneName;
    @SerializedName("gmt_offset")
    private String gmtOffset;
    @SerializedName("last_trade_time")
    private String lastTradeTime;
    @SerializedName("pe")
    private String pe;
    @SerializedName("eps")
    private String eps;

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceOpen() {
        return priceOpen;
    }

    public String getDayHigh() {
        return dayHigh;
    }

    public String getDayLow() {
        return dayLow;
    }

    public String get_52WeekHigh() {
        return _52WeekHigh;
    }

    public String get_52WeekLow() {
        return _52WeekLow;
    }

    public String getDayChange() {
        return dayChange;
    }

    public String getChangePct() {
        return changePct;
    }

    public String getCloseYesterday() {
        return closeYesterday;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public String getVolume() {
        return volume;
    }

    public String getVolumeAvg() {
        return volumeAvg;
    }

    public String getShares() {
        return shares;
    }

    public String getStockExchangeLong() {
        return stockExchangeLong;
    }

    public String getStockExchangeShort() {
        return stockExchangeShort;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getTimezoneName() {
        return timezoneName;
    }

    public String getGmtOffset() {
        return gmtOffset;
    }

    public String getLastTradeTime() {
        return lastTradeTime;
    }

    public String getPe() {
        return pe;
    }

    public String getEps() {
        return eps;
    }
}
