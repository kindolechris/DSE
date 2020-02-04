package com.dsetanzania.dse.models;

import android.content.DialogInterface;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.activities.RegistrationActivity;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUSecurityLivePrice;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class User{
    private String userId;
    private int stock;
    private String firstname;
    private String lastname;
    private  String tradername;
    private String email;
    private String yearOfStudy;
    private  String university;
    private String coursename;
    private String passoword;
    private String phoneNumber;
    private double virtualmoney;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    double amountCalculated;
    double updatedbalance;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTradername() {
        return tradername;
    }

    public void setTradername(String tradername) {
        this.tradername = tradername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getPassoword() {
        return passoword;
    }

    public void setPassoword(String passoword) {
        this.passoword = passoword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getVirtualmoney() {
        return virtualmoney;
    }

    public void setVirtualmoney(double virtualmoney) {
        this.virtualmoney = virtualmoney;
    }

    public User(String userId,String firstname, String lastname, String tradername, String email, String yearOfStudy, String university, String coursename, String phoneNumber, double virtualshare,int stock) {
        this.userId = userId;
        this.virtualmoney = virtualshare;
        this.firstname = firstname;
        this.lastname = lastname;
        this.tradername = tradername;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.university = university;
        this.coursename = coursename;
        this.passoword = passoword;
        this.stock = stock;
        this.phoneNumber = phoneNumber;
    }


    public  User(){

    }



    public boolean sellshares(double amountToSell,double openingprice){

        if(getStock()<0){
            System.out.println("You have no stock");
            return false;
        }

        System.out.println("All gooood");
        double amountTodeduct = stock * openingprice;
        virtualmoney = virtualmoney + amountTodeduct;

        return true;
    }


    public String buyshares(double openingprice,double userBid,int amountOfshares) {

        int updatedStock;
        amountCalculated = openingprice * amountOfshares;
        double fifteenPercentcountPlus = 0;
        double fifteenPercentcountMinus = 0;
        double percent = (15.0 /100 * openingprice);
        Log.v("TAAAAG :", String.valueOf(percent));

        fifteenPercentcountMinus = openingprice - percent;
        fifteenPercentcountPlus =  openingprice + percent;

        if (getVirtualmoney() <= 0) {
            Log.v("TAAAAG :", " You have no balance");
            return "zero";
        }

        if (userBid >= fifteenPercentcountMinus && userBid < openingprice) {
            Log.v("TAAAAG :", "Your bid has been queued");
            return "queued";
        }
        if (userBid < fifteenPercentcountMinus) {
            Log.v("TAAAAG :", "Your are less than 15% gap");
            return "notsatisify";
        } else if (userBid > fifteenPercentcountPlus) {
            Log.v("TAAAAG :", "Your are greater than 15% gap");
            return "greaterthan";
        }

        if (userBid >= openingprice && userBid <= fifteenPercentcountPlus)

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser fuser = mAuth.getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
            updatedbalance = getVirtualmoney() - amountCalculated;
            updatedStock = getStock() + amountOfshares;
            Map<String, Object> map = new HashMap<>();
            map.put("virtualmoney", updatedbalance);
            map.put("stock", updatedStock);
            reference.updateChildren(map);
            Log.v("TAAAAG :", "Transaction successfully");
            return "successfully";
    }
}
