package com.dsetanzania.dse.models.bond_holdings;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.dsetanzania.dse.models.PersonalBondHoldingsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonalBondHoldingResponseModal extends BaseResponseModel {

    @SerializedName("data")
    @Expose
    private List<PersonalBondHoldingsModel> personalBondHoldingsModel = null;

    public List<PersonalBondHoldingsModel> getPersonalBondHoldingsModel() {
        return personalBondHoldingsModel;
    }

    public void setPersonalBondHoldingsModel(List<PersonalBondHoldingsModel> personalBondHoldingsModel) {
        this.personalBondHoldingsModel = personalBondHoldingsModel;
    }
}
