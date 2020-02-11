package com.dsetanzania.dse.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Transactions  {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    private String transId;
    private String userId;
    private String status;
    private String date;
    private double price;
    private int shareAmount;
    private String board;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

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

        //return date.substring(0,10);
        return date;
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

    public Transactions(String id, String userId, String status,String date,String board,double price,int shareAmount,String type,String transId) {

        this.board = board;
        this.price = price;
        this.shareAmount = shareAmount;
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.status = status;
        this.type = type;
        this.transId = transId;
    }


    public Transactions() {

    }

}
