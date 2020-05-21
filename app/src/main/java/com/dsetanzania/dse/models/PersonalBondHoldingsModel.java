package com.dsetanzania.dse.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalBondHoldingsModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("boardbond_id")
    @Expose
    private Integer boardbondId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("bondnumber")
    @Expose
    private String bondnumber;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("interest_rate")
    @Expose
    private Integer interestRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBoardbondId() {
        return boardbondId;
    }

    public void setBoardbondId(Integer boardbondId) {
        this.boardbondId = boardbondId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getBondnumber() {
        return bondnumber;
    }

    public void setBondnumber(String bondnumber) {
        this.bondnumber = bondnumber;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Integer interestRate) {
        this.interestRate = interestRate;
    }

}