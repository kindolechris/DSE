package com.dsetanzania.dse.models.share_holdings;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalShareHoldingsResponseModal extends BaseResponseModel {
    @SerializedName("data")
    @Expose
    private Data PersonalShareHoldingDataModel;

    public Data getPersonalShareHoldingDataModel() {
        return PersonalShareHoldingDataModel;
    }

    public void setPersonalShareHoldingDataModel(Data personalShareHoldingDataModel) {
        PersonalShareHoldingDataModel = personalShareHoldingDataModel;
    }
}
