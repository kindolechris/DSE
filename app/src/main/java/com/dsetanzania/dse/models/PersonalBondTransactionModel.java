package com.dsetanzania.dse.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalBondTransactionModel {
    @SerializedName("boardbond_id")
    @Expose
    private String boardbondId;
    @SerializedName("personalbond_id")
    @Expose
    private String personalbondId;
    @SerializedName("price")
    @Expose
    private String units;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("auction_date")
    @Expose
    private String bondnumber;

    @SerializedName("bond_tenure")
    @Expose
    private String bond_tenure;

    @SerializedName("coupon_rate")
    @Expose
    private String coupon_rate;
    @SerializedName("timeago")
    @Expose
    private String timeago;

    public PersonalBondTransactionModel(String units, String createdAt,String timeago, String id, String bondnumber, String status) {
        this.units = units;
        this.createdAt = createdAt;
        this.timeago = timeago;
        this.id = id;
        this.bondnumber = bondnumber;
        this.status = status;
    }

    public String getBoardbondId() {
        return boardbondId;
    }

    public void setBoardbondId(String boardbondId) {
        this.boardbondId = boardbondId;
    }

    public String getPersonalbondId() {
        return personalbondId;
    }

    public void setPersonalbondId(String personalbondId) {
        this.personalbondId = personalbondId;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBondnumber() {
        return bondnumber;
    }

    public void setBondnumber(String bondnumber) {
        this.bondnumber = bondnumber;
    }

    public String getTimeago() {
        return timeago;
    }

    public void setTimeago(String timeago) {
        this.timeago = timeago;
    }

    public String getBond_tenure() {
        return bond_tenure;
    }

    public void setBond_tenure(String bond_tenure) {
        this.bond_tenure = bond_tenure;
    }

    public String getCoupon_rate() {
        return coupon_rate;
    }

    public void setCoupon_rate(String coupon_rate) {
        this.coupon_rate = coupon_rate;
    }
}

