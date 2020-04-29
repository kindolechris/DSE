package com.dsetanzania.dse.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BoardShareResponseModel {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<BoardSharesModel> boardSharesModel;

    public BoardShareResponseModel(String message, ArrayList<BoardSharesModel> boardSharesModel) {
        this.message = message;
        this.boardSharesModel = boardSharesModel;
    }

    public ArrayList<BoardSharesModel> getBoardSharesModel() {
        return boardSharesModel;
    }

    public void setBoardSharesModel(ArrayList<BoardSharesModel> boardSharesModel) {
        this.boardSharesModel = boardSharesModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
