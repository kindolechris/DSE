package com.dsetanzania.dse.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.dsetanzania.dse.models.UserModel;

public class SharedPrefManager {
    private static final  String SHARED_PREF_NAME = "my_shared_preff";
    private static SharedPrefManager mInstance;
    private Context mCtx;

    public SharedPrefManager(Context mCtx){
        this.mCtx = mCtx;
    }

    public static  synchronized  SharedPrefManager getInstance(Context mCtx){
        if(mInstance== null){
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void SavaUser(UserModel user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id",user.getId());
        editor.putString("token",user.getFirstname());
        editor.putString("firstname",user.getFirstname());
        editor.putString("lastname",user.getLastname());
        editor.putString("tradername",user.getTradername());
        editor.putString("email",user.getEmail());
        editor.putString("yearOfStudy",user.getYearOfStudy());
        editor.putString("university",user.getUniversity());
        editor.putString("coursename",user.getCoursename());
        editor.putString("password"," ");
        editor.putString("phonenumber",user.getPhonenumber());
        editor.putString("role",user.getLastname());
        editor.putString("gender",user.getLastname());
        editor.putInt("stock",user.getStock());
        editor.putInt("bonds",user.getBonds());
        editor.putString("virtualmoney",String.valueOf(user.getVirtualmoney()));
        editor.putString("token",user.getToken());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString("token", null) != null)
            return true;
        return false;
    }

    public UserModel getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new UserModel(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getInt("stock",-1),
                sharedPreferences.getInt("bonds",-1),
                sharedPreferences.getString("firstname",null),
                sharedPreferences.getString("lastname",null),
                sharedPreferences.getString("tradername",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("yearOfStudy",null),
                sharedPreferences.getString("university",null),
                sharedPreferences.getString("coursename",null),
                sharedPreferences.getString("password",null),
                sharedPreferences.getString("phonenumber",null),
                sharedPreferences.getString("role",null),
                Double.parseDouble(sharedPreferences.getString("virtualmoney",null)),
                sharedPreferences.getString("gender",null),
                sharedPreferences.getString("token",null)
        );
    }
    public  void clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
