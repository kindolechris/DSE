package com.dsetanzania.dse.models.graphdata;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GraphDataResponseModel extends BaseResponseModel {
    @SerializedName("data")
    @Expose
    private List<GraphDataModel> graphDataModel = null;

    public List<GraphDataModel> getData() {
        return graphDataModel;
    }

    public void setData(List<GraphDataModel> data) {
        this.graphDataModel = data;
    }
}
