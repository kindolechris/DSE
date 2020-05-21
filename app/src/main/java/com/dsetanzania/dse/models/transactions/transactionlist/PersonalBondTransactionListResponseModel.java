package com.dsetanzania.dse.models.transactions.transactionlist;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.dsetanzania.dse.models.PersonalBondTransactionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonalBondTransactionListResponseModel extends BaseResponseModel {
    @SerializedName("data")
    @Expose
    private List<PersonalBondTransactionModel> personalBondTransactionModel = null;

    public List<PersonalBondTransactionModel> getPersonalBondTransactionModel() {
        return personalBondTransactionModel;
    }

    public void setPersonalBondTransactionModel(List<PersonalBondTransactionModel> personalBondTransactionModel) {
        this.personalBondTransactionModel = personalBondTransactionModel;
    }
}
