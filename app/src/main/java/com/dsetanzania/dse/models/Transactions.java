package com.dsetanzania.dse.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transactions  {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String userId;
    private String status;
    private String date;
    private double price;
    private int shareAmount;
    private String board;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(int shareAmount) {
        this.shareAmount = shareAmount;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getDate() {

        return date.substring(0,10);
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Transactions(String id, String userId, String status,String date,String board,double price,int shareAmount) {
        this.board = board;
        this.price = price;
        this.shareAmount = shareAmount;
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.status = status;
    }

    public Transactions() {


    }
}
