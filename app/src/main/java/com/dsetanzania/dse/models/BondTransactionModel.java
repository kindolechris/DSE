package com.dsetanzania.dse.models;

public class BondTransactionModel {

    private String Id;
    private String UserId;
    private String Bondnumber;
    private Double Cosideration;
    private String Type;
    private int Unit;
    private int AtPrice;
    private String date;
    private String Bondstatus;


    public BondTransactionModel() {

    }

    public String getBondstatus() {
        return Bondstatus;
    }

    public void setBondstatus(String bondstatus) {
        Bondstatus = bondstatus;
    }

    public String getBondnumber() {
        return Bondnumber;
    }

    public void setBondnumber(String bondnumber) {
        Bondnumber = bondnumber;
    }

    public Double getCosideration() {
        return Cosideration;
    }

    public void setCosideration(Double cosideration) {
        Cosideration = cosideration;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getUnit() {
        return Unit;
    }

    public void setUnit(int unit) {
        Unit = unit;
    }

    public int getAtPrice() {
        return AtPrice;
    }

    public void setAtPrice(int atPrice) {
        AtPrice = atPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
