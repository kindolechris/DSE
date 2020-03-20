package com.dsetanzania.dse.activities;

import com.dsetanzania.dse.models.NewsFeeds;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface WordTradingApi {

    @GET("/")
    Call<List<NewsFeeds>> getNews();
}
