package com.dsetanzania.dse.models.shares;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.dsetanzania.dse.models.BoardSharesModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BoardShareResponseModel extends BaseResponseModel {

    @SerializedName("data")
    @Expose
    private List<BoardSharesModel> boardSharesModel = null;

    public List<BoardSharesModel> getBoardSharesModel() {
        return boardSharesModel;
    }

    public void setBoardSharesModel(List<BoardSharesModel> boardSharesModel) {
        this.boardSharesModel = boardSharesModel;
    }

}

