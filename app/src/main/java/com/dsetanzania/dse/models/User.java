package com.dsetanzania.dse.models;

public class User {
    private String userId;
    private String firstname;
    private String lastname;
    private  String tradername;
    private String email;
    private String yearOfStudy;
    private  String university;
    private String coursename;
    private String passoword;
    private String phoneNumber;
    private String virtualshare;

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

    public String getVirtualshare() {
        return virtualshare;
    }

    public void setVirtualshare(String virtualshare) {
        this.virtualshare = virtualshare;
    }

    public User(String userId,String firstname, String lastname, String tradername, String email, String yearOfStudy, String university, String coursename, String phoneNumber, String virtualshare) {
        this.userId = userId;
        this.virtualshare = virtualshare;
        this.firstname = firstname;
        this.lastname = lastname;
        this.tradername = tradername;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.university = university;
        this.coursename = coursename;
        this.passoword = passoword;
        this.phoneNumber = phoneNumber;
    }


    public  User(){

    }


}
