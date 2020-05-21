package com.dsetanzania.dse.models.transactions.buy.transaction.shares.market;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.dsetanzania.dse.models.transactions.buy.transaction.shares.board.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyFromPersonResponseModel extends BaseResponseModel {

    @SerializedName("data")
    @Expose
    private com.dsetanzania.dse.models.transactions.buy.transaction.shares.board.Data data;

    public com.dsetanzania.dse.models.transactions.buy.transaction.shares.board.Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}

