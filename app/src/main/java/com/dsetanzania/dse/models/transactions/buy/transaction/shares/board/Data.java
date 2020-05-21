package com.dsetanzania.dse.models.transactions.buy.transaction.shares.board;
import com.dsetanzania.dse.models.PersonalShareHoldingsModel;
import com.dsetanzania.dse.models.UserModel;
import com.dsetanzania.dse.models.PersonalsharesTransactionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

        @SerializedName("user")
        @Expose
        private UserModel userModel;
        @SerializedName("personalsharesTransaction")
        @Expose
        private PersonalsharesTransactionModel  personalsharesTransaction;
        @SerializedName("personalShares")
        @Expose
        private PersonalShareHoldingsModel personalShares;

        public UserModel getUser() {
            return userModel;
        }

        public void setUser(UserModel user) {
            this.userModel = user;
        }

    public PersonalsharesTransactionModel getPersonalsharesTransaction() {
        return personalsharesTransaction;
    }

    public void setPersonalsharesTransaction(PersonalsharesTransactionModel personalsharesTransaction) {
        this.personalsharesTransaction = personalsharesTransaction;
    }

    public PersonalShareHoldingsModel getPersonalShares() {
            return personalShares;
        }

        public void setPersonalShares(PersonalShareHoldingsModel personalShares) {
            this.personalShares = personalShares;
        }

    }
