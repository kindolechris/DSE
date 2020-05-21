package com.dsetanzania.dse.models.bonds;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.dsetanzania.dse.models.BondModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BondResponseModel extends BaseResponseModel {

    @SerializedName("data")
    @Expose
    private List<BondModel> bondModel = null;
    
    public List<BondModel> getBondModel() {
        return bondModel;
    }

    public void setBondModel(List<BondModel> bondModel) {
        this.bondModel = bondModel;
    }

}

