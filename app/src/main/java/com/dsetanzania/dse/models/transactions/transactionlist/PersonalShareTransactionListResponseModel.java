package com.dsetanzania.dse.models.transactions.transactionlist;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.dsetanzania.dse.models.PersonalsharesTransactionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonalShareTransactionListResponseModel extends BaseResponseModel {
    @SerializedName("data")
    @Expose
    private List<PersonalsharesTransactionModel> personalsharesTransactionModel = null;

    public List<PersonalsharesTransactionModel> getPersonalsharesTransactionModel() {
        return personalsharesTransactionModel;
    }

    public void setPersonalsharesTransactionModel(List<PersonalsharesTransactionModel> personalsharesTransactionModel) {
        this.personalsharesTransactionModel = personalsharesTransactionModel;
    }
}
