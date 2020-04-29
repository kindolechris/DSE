package com.dsetanzania.dse.api;

import com.dsetanzania.dse.models.AuthResponseModel;
import com.dsetanzania.dse.models.BoardShareResponseModel;
import com.dsetanzania.dse.models.UserDataResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    @FormUrlEncoded
    @POST("login")
    Call<AuthResponseModel> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<AuthResponseModel> register(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("gender") String gender,
            @Field("tradername") String tradername,
            @Field("email") String email,
            @Field("yearOfStudy") String yearOfStudy,
            @Field("university") String university,
            @Field("coursename") String coursename,
            @Field("password") String password,
            @Field("c_password") String c_password,
            @Field("phonenumber") String phonenumber
    );

    @GET("user/{id}")
    Call<UserDataResponseModel> fetchUserdata(
            @Path("id") int id,
            @Header("Authorization") String token
    );

    @GET("boardshares")
    Call<BoardShareResponseModel> fetchBoardSharesdata(
            @Header("Authorization") String token
    );
}
