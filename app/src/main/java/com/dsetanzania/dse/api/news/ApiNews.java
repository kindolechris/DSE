package com.dsetanzania.dse.api.news;

import com.dsetanzania.dse.models.news.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiNews {

    @GET("top-headlines")
    Call<ResponseModel> getTopHeadlinestNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ResponseModel> getAfricaNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ResponseModel> getBusinessNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey);
}
