package com.dsetanzania.dse.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.BoardSharesAdapter;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.models.BoardShareResponseModel;
import com.dsetanzania.dse.models.BoardSharesModel;
import com.dsetanzania.dse.models.UserModel;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class BoardSharesFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    UserModel user;
    BoardShareResponseModel boardShareResponseModel;
    //SnappingRecyclerView livemarketpricerecyclerview;
    RecyclerView livemarketpricerecyclerview;
    ArrayList<BoardSharesModel> simulatedMarket;
    DatabaseReference reference;
    BoardSharesAdapter simulatedMarketAdapter;
    View view;
    private String _token;
    RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;

    // newInstance constructor for creating fragment with arguments
    public static BoardSharesFragment newInstance(int page, String title) {
        BoardSharesFragment faqsFragment = new BoardSharesFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        faqsFragment.setArguments(args);
        return faqsFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(view == null){
            view = inflater.inflate(R.layout.fragment_buy, container, false);
            layoutManager = new LinearLayoutManager(getActivity());
            livemarketpricerecyclerview = (RecyclerView) view.findViewById(R.id.listofmarketrecycler);
            //getlivedata();
            sharedPreferences = getActivity().getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
            _token = sharedPreferences.getString("token", "");
            getboards();
        }

        return view;
    }


    public void getboards(){

        Call<BoardShareResponseModel> call = RetrofitClient
                .getInstance().getApi().fetchBoardSharesdata("Bearer " +  _token);

        String s = call.toString();
        call.enqueue(new Callback<BoardShareResponseModel>() {
            @Override
            public void onResponse(Call<BoardShareResponseModel> call, Response<BoardShareResponseModel> response) {
                boardShareResponseModel = response.body();
                if ( boardShareResponseModel != null){

                    //updateFieldsOnChange();
                    simulatedMarketAdapter = new BoardSharesAdapter(getContext(), boardShareResponseModel.getBoardSharesModel());
                    livemarketpricerecyclerview.setHasFixedSize(true);
                    livemarketpricerecyclerview.setLayoutManager(layoutManager);
                    livemarketpricerecyclerview.setAdapter(simulatedMarketAdapter);
                    Log.i("Check this ","boards found");
                }
                else{
                    //Toast.makeText(HomeActivity.this,"Nothing",Toast.LENGTH_LONG).show();
                    //Log.i("Check this ","not workinnnnnnnng");
                }
            }

            @Override
            public void onFailure(Call<BoardShareResponseModel> call, Throwable t) {

            }
        });
    }

}

