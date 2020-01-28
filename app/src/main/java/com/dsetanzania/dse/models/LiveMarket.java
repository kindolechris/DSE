package com.dsetanzania.dse.models;

public class LiveMarket {

    private String Company;
    private String OpeningPrice;
    private String MarketCap;

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        this.Company = company;
    }

    public String getOpeningPrice() {
        return OpeningPrice;
    }

    public void setOpeningPrice(String openingPrice) {
        this.OpeningPrice = openingPrice;
    }

    public String getMarketCap() {
        return MarketCap;
    }

    public void setMarketCap(String marketCap) {
        this.MarketCap = marketCap;
    }

    public LiveMarket() {
    }

}
