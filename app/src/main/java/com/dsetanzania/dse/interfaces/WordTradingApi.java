package com.dsetanzania.dse.interfaces;

import com.dsetanzania.dse.models.NewsFeedsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WordTradingApi {

    @GET("/")
    Call<List<NewsFeedsModel>> getNews();
}
