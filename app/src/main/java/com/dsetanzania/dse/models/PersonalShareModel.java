package com.dsetanzania.dse.models;

public class PersonalShareModel {

    private String personname;
    private String companyname;
    private String quantity;
    private String price;

    public PersonalShareModel(String personname, String companyname, String quantity, String price) {
        this.personname = personname;
        this.companyname = companyname;
        this.quantity = quantity;
        this.price = price;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
