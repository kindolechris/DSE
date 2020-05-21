package com.dsetanzania.dse.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("tradername")
    @Expose
    private String tradername;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;
    @SerializedName("university")
    @Expose
    private String university;
    @SerializedName("yearOfStudy")
    @Expose
    private String yearOfStudy;
    @SerializedName("coursename")
    @Expose
    private String coursename;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("bonds")
    @Expose
    private Integer bonds;
    @SerializedName("shares")
    @Expose
    private Integer stock;
    @SerializedName("virtualmoney")
    @Expose
    private Double virtualmoney;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("firebaseToken")
    @Expose
    private String firebaseToken;
    @Expose
    @SerializedName("token")
    private String token;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTradername() {
        return tradername;
    }

    public void setTradername(String tradername) {
        this.tradername = tradername;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBonds() {
        return bonds;
    }

    public void setBonds(Integer bonds) {
        this.bonds = bonds;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getVirtualmoney() {
        return virtualmoney;
    }

    public void setVirtualmoney(Double virtualmoney) {
        this.virtualmoney = virtualmoney;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
