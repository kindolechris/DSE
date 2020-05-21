package com.dsetanzania.dse.models.shares;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.dsetanzania.dse.models.PersonalsharesTransactionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonalShareResponseModel extends BaseResponseModel {

    @SerializedName("data")
    @Expose
    private List<PersonalsharesTransactionModel> data = null;

    public List<PersonalsharesTransactionModel> getData() {
        return data;
    }

    public void setData(List<PersonalsharesTransactionModel> data) {
        this.data = data;
    }

}
