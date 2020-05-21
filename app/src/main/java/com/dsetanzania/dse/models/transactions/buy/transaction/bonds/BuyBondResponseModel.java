package com.dsetanzania.dse.models.transactions.buy.transaction.bonds;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyBondResponseModel extends BaseResponseModel {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
