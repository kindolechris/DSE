package com.dsetanzania.dse.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalBondTransactionModel {
    @SerializedName("boardbond_id")
    @Expose
    private Integer boardbondId;
    @SerializedName("personalbond_id")
    @Expose
    private Integer personalbondId;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("bondnumber")
    @Expose
    private String bondnumber;

    public PersonalBondTransactionModel(String units, String createdAt, Integer id, String bondnumber,String status) {
        this.units = units;
        this.createdAt = createdAt;
        this.id = id;
        this.bondnumber = bondnumber;
        this.status = status;
    }

    public Integer getBoardbondId() {
        return boardbondId;
    }

    public void setBoardbondId(Integer boardbondId) {
        this.boardbondId = boardbondId;
    }

    public Integer getPersonalbondId() {
        return personalbondId;
    }

    public void setPersonalbondId(Integer personalbondId) {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBondnumber() {
        return bondnumber;
    }

    public void setBondnumber(String bondnumber) {
        this.bondnumber = bondnumber;
    }
}

