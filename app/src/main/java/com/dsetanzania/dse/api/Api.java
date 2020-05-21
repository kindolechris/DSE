package com.dsetanzania.dse.api;

import com.dsetanzania.dse.models.AuthResponseModel;
import com.dsetanzania.dse.models.BaseResponseModel;
import com.dsetanzania.dse.models.bond_holdings.PersonalBondHoldingResponseModal;
import com.dsetanzania.dse.models.bonds.BondResponseModel;
import com.dsetanzania.dse.models.shares.BoardShareResponseModel;
import com.dsetanzania.dse.models.shares.PersonalShareResponseModel;
import com.dsetanzania.dse.models.transactions.buy.transaction.bonds.BuyBondResponseModel;
import com.dsetanzania.dse.models.transactions.buy.transaction.shares.market.BuyFromPersonResponseModel;
import com.dsetanzania.dse.models.UserDataResponseModel;
import com.dsetanzania.dse.models.share_holdings.PersonalShareHoldingsResponseModal;
import com.dsetanzania.dse.models.transactions.buy.transaction.shares.board.BuyFromBoardResponseModel;
import com.dsetanzania.dse.models.transactions.sell.PersonalShareSalesResponseModel;
import com.dsetanzania.dse.models.transactions.transactionlist.PersonalBondTransactionListResponseModel;
import com.dsetanzania.dse.models.transactions.transactionlist.PersonalShareTransactionListResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
            @Field("phonenumber") String phonenumber,
            @Field("firebaseToken") String firebaseToken
    );

    @FormUrlEncoded
    @POST("pushfirebasetoken")
    Call<BaseResponseModel> pushFirebaseToken(
            @Field("id") int id,
            @Field("firebaseToken") String firebaseToken,
            @Header("Authorization") String token
    );


    @FormUrlEncoded
    @POST("get/user")
    Call<UserDataResponseModel> fetchUserdata(
            @Field("id") int id,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("get/personalsharetransaction")
    Call<PersonalShareTransactionListResponseModel> fetchUserShareTransaction(
            @Field("userid") int id,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("get/userbond/transaction")
    Call<PersonalBondTransactionListResponseModel> fetchUserBondTransaction(
            @Field("user_id") int id,
            @Header("Authorization") String token
    );

    @GET("get/salesshareslist/board")
    Call<BoardShareResponseModel> fetchBoardSharesdata(
            @Header("Authorization") String token
    );

    @GET("get/bond/all")
    Call<BondResponseModel> fetchBondsList(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("buybond")
    Call<BuyBondResponseModel> buyBonds(
            @Field("board_bond_id") String board_bond_id,
            @Field("bond_amount") String bond_amount,
            @Header("Authorization") String token
    );

    @GET("get/salesshareslist/market")
    Call<PersonalShareResponseModel> fetchPersonalSharedata(
            @Header("Authorization") String token
    );

    @POST("sells/entry")
    Call<PersonalShareHoldingsResponseModal> fetchPersonalShareHoldings(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("get/userbond/holdings")
    Call<PersonalBondHoldingResponseModal> fetchPersonalBondeHoldings(
            @Field("user_id") String id,
            @Header("Authorization") String token
    );


    @FormUrlEncoded
    @POST("buyshare")
    Call<BuyFromBoardResponseModel> buyShareFromBoard(
            @Field("transactiontype") String transactiontype,
            @Field("sharesamount") String sharesamount,
            @Field("price") String price,
            @Field("transactionfrom") String transactionfrom,
            @Field("boardShareId") String boardShareId,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("buyshare")
    Call<BuyFromPersonResponseModel> buyShareFromPerson(
            @Field("transactionfrom") String transactionfrom,
            @Field("peerTransactionId") String peerTransactionId,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("sellshare")
    Call<PersonalShareSalesResponseModel> sellSharesTMarket(
            @Field("transactiontype") String transactiontype,
            @Field("sharesamount") String sharesamount,
            @Field("price") String price,
            @Field("transactionfrom") String transactionfrom,
            @Field("boardShareId") String boardShareId,
            @Header("Authorization") String token
    );


}
