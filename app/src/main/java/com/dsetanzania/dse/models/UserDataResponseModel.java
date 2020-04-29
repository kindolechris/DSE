package com.dsetanzania.dse.models;

import com.google.gson.annotations.SerializedName;

public class UserDataResponseModel {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private UserModel users;

    public UserDataResponseModel(String message, UserModel users) {
        this.message = message;
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUsers() {
        return users;
    }

    public void setUsers(UserModel users) {
        this.users = users;
    }
}
