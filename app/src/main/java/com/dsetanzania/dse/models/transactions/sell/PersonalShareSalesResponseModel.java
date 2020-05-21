package com.dsetanzania.dse.models.transactions.sell;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalShareSalesResponseModel extends BaseResponseModel {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("instant_sell")
    @Expose
    private Boolean instantSell;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Boolean getInstantSell() {
        return instantSell;
    }

    public void setInstantSell(Boolean instantSell) {
        this.instantSell = instantSell;
    }
}
