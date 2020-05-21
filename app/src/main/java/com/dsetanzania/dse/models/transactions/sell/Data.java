package com.dsetanzania.dse.models.transactions.sell;

import com.dsetanzania.dse.models.PersonalsharesTransactionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("personalsharesTransaction")
    @Expose
    private PersonalsharesTransactionModel personalsharesTransaction;

    public PersonalsharesTransactionModel getPersonalsharesTransaction() {
        return personalsharesTransaction;
    }

    public void setPersonalsharesTransaction(PersonalsharesTransactionModel personalsharesTransaction) {
        this.personalsharesTransaction = personalsharesTransaction;
    }

}