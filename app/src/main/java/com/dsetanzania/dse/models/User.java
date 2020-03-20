package com.dsetanzania.dse.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class User{
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;
    private int stock;
    private int bonds;
    private String firstname;
    private String lastname;
    private  String tradername;
    private String email;
    private String yearOfStudy;
    private  String university;
    private String coursename;
    private String passoword;
    private String phoneNumber;

    private String check;
    SharesTransaction _transaction;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String role;
    private double virtualmoney;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    double amountCalculated;
    double updatedbalance;

    public int getBonds() {
        return bonds;
    }

    public void setBonds(int bonds) {
        this.bonds = bonds;
    }

    public void setCheck(String check){
        this.check = check;
    }

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

    public User(String userId, String firstname, String lastname, String gender, String tradername, String email, String yearOfStudy, String university, String coursename, String phoneNumber, double virtualshare, int stock, String role,int bonds) {
        this.userId = userId;
        this.virtualmoney = virtualshare;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.tradername = tradername;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.university = university;
        this.coursename = coursename;
        this.stock = stock;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.bonds = bonds;
    }


    public  User(){


    }

    public void sellshares(final double amountToSell, final int sharesToSell,final String board){

        if(getStock()<0){
            System.out.println("You have no stock");
        }

        if(amountToSell > getVirtualmoney()){
            System.out.println("You have less virtual balance");

        }


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
            Log.v("TAAAAG :", "Purchase transaction successfully");
            return "successfully";
    }

    public void methodToProcess(String firebaseVariable){
        check = firebaseVariable;
    }
}
