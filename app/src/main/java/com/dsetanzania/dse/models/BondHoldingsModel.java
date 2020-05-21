package com.dsetanzania.dse.models;

public class BondHoldingsModel {
    private int id;
    private String user_id;
    private String boardbond_id;
    private String amount;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBoardbond_id() {
        return boardbond_id;
    }

    public void setBoardbond_id(String boardbond_id) {
        this.boardbond_id = boardbond_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

