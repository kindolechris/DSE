package com.dsetanzania.dse.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalsharesTransactionModel {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("boardshare_id")
    @Expose
    private Integer boardshareId;
    @SerializedName("personalshare_id")
    @Expose
    private Integer personalshareId;
    @SerializedName("transactiontype")
    @Expose
    private String transactiontype;
    @SerializedName("sharesamount")
    @Expose
    private Integer sharesamount;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("consideration")
    @Expose
    private Integer consideration;
    @SerializedName("transactionfrom")
    @Expose
    private String transactionfrom;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("companyname")
    @Expose
    private String companyname;


    public PersonalsharesTransactionModel(Integer sharesamount , Integer price , String status, String createdAt, Integer id, String companyname, String transactiontype) {
        this.transactiontype = transactiontype;
        this.sharesamount = sharesamount;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.id = id;
        this.companyname = companyname;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBoardshareId() {
        return boardshareId;
    }

    public void setBoardshareId(Integer boardshareId) {
        this.boardshareId = boardshareId;
    }

    public Integer getPersonalshareId() {
        return personalshareId;
    }

    public void setPersonalshareId(Integer personalshareId) {
        this.personalshareId = personalshareId;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public Integer getSharesamount() {
        return sharesamount;
    }

    public void setSharesamount(Integer sharesamount) {
        this.sharesamount = sharesamount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getConsideration() {
        return consideration;
    }

    public void setConsideration(Integer consideration) {
        this.consideration = consideration;
    }

    public String getTransactionfrom() {
        return transactionfrom;
    }

    public void setTransactionfrom(String transactionfrom) {
        this.transactionfrom = transactionfrom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

}

