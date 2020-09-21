package com.dsetanzania.dse.models.live_data;

import java.util.List;

import com.dsetanzania.dse.models.BoardSharesModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveMarketPricesResult {

    @SerializedName("SecurityLivePrice")
    @Expose
    private List<SecurityLivePrice> securityLivePrice = null;

    public List<SecurityLivePrice> getSecurityLivePrice() {
        return securityLivePrice;
    }

    public void setSecurityLivePrice(List<SecurityLivePrice> securityLivePrice) {
        this.securityLivePrice = securityLivePrice;
    }

}
