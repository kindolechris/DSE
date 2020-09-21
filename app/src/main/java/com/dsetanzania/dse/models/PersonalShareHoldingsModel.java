package com.dsetanzania.dse.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalShareHoldingsModel {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("boardshare_id")
    @Expose
    private String boardshareId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("sharesamount")
    @Expose
    private Integer sharesamount;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("availableSharesAmount")
    @Expose
    private Integer availableSharesAmount;
    @SerializedName("company")
    @Expose
    private String company;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBoardshareId() {
        return boardshareId;
    }

    public void setBoardshareId(String boardshareId) {
        this.boardshareId = boardshareId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSharesamount() {
        return sharesamount;
    }

    public void setSharesamount(Integer sharesamount) {
        this.sharesamount = sharesamount;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getAvailableSharesAmount() {
        return availableSharesAmount;
    }

    public void setAvailableSharesAmount(Integer availableSharesAmount) {
        this.availableSharesAmount = availableSharesAmount;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

}

