package com.dsetanzania.dse.models;

import com.google.gson.annotations.SerializedName;

public class UserDataResponseModel extends BaseResponseModel {

    @SerializedName("data")
    private UserModel users;

    public UserModel getUsers() {
        return users;
    }

    public void setUsers(UserModel users) {
        this.users = users;
    }
}
