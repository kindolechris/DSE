package com.example.dse;

public class model {

    private String Fullname;
    private String Lastname;
    private String Phone;

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public model(String fullname, String lastname, String phone) {
        Fullname = fullname;
        Lastname = lastname;
        Phone = phone;
    }

    public model() {

    }
}

