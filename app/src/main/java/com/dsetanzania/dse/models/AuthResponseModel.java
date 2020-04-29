package com.dsetanzania.dse.models;

import com.google.gson.annotations.SerializedName;

public class AuthResponseModel {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("name")
    private String name;

    @SerializedName("data")
    private UserModel user;

    public AuthResponseModel(boolean success, String message, UserModel user, String name) {
        this.success = success;
        this.message = message;
        this.name = name;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
