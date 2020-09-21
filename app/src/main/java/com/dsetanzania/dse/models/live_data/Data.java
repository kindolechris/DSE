package com.dsetanzania.dse.models.live_data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("LiveMarketPricesResult")
    @Expose
    private LiveMarketPricesResult liveMarketPricesResult;

    public LiveMarketPricesResult getLiveMarketPricesResult() {
        return liveMarketPricesResult;
    }

    public void setLiveMarketPricesResult(LiveMarketPricesResult liveMarketPricesResult) {
        this.liveMarketPricesResult = liveMarketPricesResult;
    }

}