package com.dsetanzania.dse.models.graphdata;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GraphDataResponseModel extends BaseResponseModel {

    @SerializedName("data")
    @Expose
    private GraphDataModel data;

    public GraphDataModel getData() {
        return data;
    }

    public void setData(GraphDataModel data) {
        this.data = data;
    }
}
