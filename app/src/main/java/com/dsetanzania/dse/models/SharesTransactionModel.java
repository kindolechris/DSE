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

public class SharesTransactionModel {

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
    private Integer userId;
    private String status;
    private String date;
    private double price;
    private int shareAmount;
    private String board;

    public String getTransactionParty() {
        return transactionParty;
    }

    public void setTransactionParty(String transactionParty) {
        this.transactionParty = transactionParty;
    }

    private String transactionParty;

    public String getUniversictyfrom() {
        return universictyfrom;
    }

    public void setUniversictyfrom(String universictyfrom) {
        this.universictyfrom = universictyfrom;
    }

    private String universictyfrom;

    public String getTransactionSuccessfulldate() {
        return transactionSuccessfulldate;
    }

    public void setTransactionSuccessfulldate(String transactionSuccessfulldate) {
        this.transactionSuccessfulldate = transactionSuccessfulldate;
    }

    public String getBoughtOrSoldBy() {
        return boughtOrSoldBy;
    }

    public void setBoughtOrSoldBy(String boughtOrSoldBy) {
        this.boughtOrSoldBy = boughtOrSoldBy;
    }

    private String transactionSuccessfulldate;
    private String boughtOrSoldBy;

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


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SharesTransactionModel(String id, Integer userId, String status, String date, String board, double price, int shareAmount, String type, String transId, String boughtOrSoldBy, String transactionSuccessfulldate, String universictyfrom, String transactionParty) {

        this.board = board;
        this.price = price;
        this.shareAmount = shareAmount;
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.status = status;
        this.type = type;
        this.transId = transId;
        this.boughtOrSoldBy = boughtOrSoldBy;
        this.transactionSuccessfulldate = transactionSuccessfulldate;
        this.universictyfrom = universictyfrom;
        this.transactionParty = transactionParty;
    }


    public SharesTransactionModel() {

    }

}
