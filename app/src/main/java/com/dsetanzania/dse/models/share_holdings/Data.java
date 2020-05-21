package com.dsetanzania.dse.models.share_holdings;

import com.dsetanzania.dse.models.PersonalShareHoldingsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("personalShares")
    @Expose
    private List<PersonalShareHoldingsModel> personalShares = null;

    public List<PersonalShareHoldingsModel> getPersonalShares() {
        return personalShares;
    }

    public void setPersonalShares(List<PersonalShareHoldingsModel> personalShares) {
        this.personalShares = personalShares;
    }
}
