package com.dsetanzania.dse.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResponseModel extends BaseResponseModel {

    @SerializedName("data")
    @Expose
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

}
