package com.dsetanzania.dse.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class UserModel {

    @SerializedName("id")
    private int id;

    @SerializedName("stock")
    private int stock;

    @SerializedName("bonds")
    private int bonds;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("lastname")
    private String lastname;

    @SerializedName("tradername")
    private  String tradername;

    @SerializedName("email")
    private String email;

    @SerializedName("yearOfStudy")
    private String yearOfStudy;

    @SerializedName("university")
    private  String university;

    @SerializedName("coursename")
    private String coursename;

    @SerializedName("password")
    private String passoword;

    @SerializedName("phonenumber")
    private String phonenumber;

    @SerializedName("role")
    private String role;

    @SerializedName("virtualmoney")
    private double virtualmoney;

    @SerializedName("gender")
    private String gender;

    @SerializedName("token")
    private String token;

    public UserModel(int id, int stock, int bonds, String firstname, String lastname, String tradername, String email, String yearOfStudy, String university, String coursename, String passoword, String phonenumber, String role, double virtualmoney, String gender, String token) {
        this.id = id;
        this.stock = stock;
        this.bonds = bonds;
        this.firstname = firstname;
        this.lastname = lastname;
        this.tradername = tradername;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.university = university;
        this.coursename = coursename;
        this.passoword = passoword;
        this.phonenumber = phonenumber;
        this.role = role;
        this.virtualmoney = virtualmoney;
        this.gender = gender;
        this.token = token;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBonds() {
        return bonds;
    }

    public void setBonds(int bonds) {
        this.bonds = bonds;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getVirtualmoney() {
        return virtualmoney;
    }

    public void setVirtualmoney(double virtualmoney) {
        this.virtualmoney = virtualmoney;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
