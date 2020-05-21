package com.dsetanzania.dse.models.transactions.buy.transaction.bonds;

import com.dsetanzania.dse.models.PersonalBondHoldingsModel;
import com.dsetanzania.dse.models.PersonalBondTransactionModel;
import com.dsetanzania.dse.models.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("User")
    @Expose
    private UserModel userModel;
    @SerializedName("PersonalBondHoldings")
    @Expose
    private PersonalBondHoldingsModel personalBondHoldingModel;
    @SerializedName("PersonalBondsTransaction")
    @Expose
    private PersonalBondTransactionModel personalBondTransactionModel;

    public UserModel getUser() {
        return userModel;
    }

    public void setUser(UserModel user) {
        this.userModel = user;
    }

    public PersonalBondHoldingsModel getPersonalBondHoldingModel() {
        return personalBondHoldingModel;
    }

    public void setPersonalBondHoldingModel(PersonalBondHoldingsModel personalBondHoldingModel) {
        this.personalBondHoldingModel = personalBondHoldingModel;
    }

    public PersonalBondTransactionModel getPersonalBondsTransaction() {
        return personalBondTransactionModel;
    }

    public void setPersonalBondsTransaction(PersonalBondTransactionModel personalBondsTransaction) {
        this.personalBondTransactionModel = personalBondsTransaction;
    }

}
