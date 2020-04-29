package com.dsetanzania.dse.models;

public class BondsModel {
    private int bondnumber;
    private int rate;
    private int month;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    private String id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public int getBondnumber() {
        return bondnumber;
    }

    public void setBondnumber(int bondnumber) {
        this.bondnumber = bondnumber;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }


    public BondsModel(String id, int bondnumber, int rate, int month, String date) {
        this.id = id;
        this.bondnumber = bondnumber;
        this.rate = rate;
        this.month = month;
        this.date =date;
    }


    public BondsModel() {

    }
}
