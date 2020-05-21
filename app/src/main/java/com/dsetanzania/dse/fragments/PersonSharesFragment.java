package com.dsetanzania.dse.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.PersonalSharesAdapter;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.models.PersonalsharesTransactionModel;
import com.dsetanzania.dse.models.shares.PersonalShareResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class PersonSharesFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    View view;
    private PersonalShareResponseModel personalShareResponseModel;
    private String _token;
    ArrayList<PersonalsharesTransactionModel> personalShareData;
    RecyclerView personalSharesRecyclerView;
    PersonalSharesAdapter personalSharesAdapter;
    RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    ProgressBar personalSharesLoader;

    // newInstance constructor for creating fragment with arguments
    public static PersonSharesFragment newInstance(int page, String title) {
        PersonSharesFragment faqsFragment = new PersonSharesFragment();
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
        setHasOptionsMenu(true);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person_shares, container, false);
        personalSharesLoader = (ProgressBar) view.findViewById(R.id.personalSharesLoader);
        layoutManager = new LinearLayoutManager(getActivity());
        personalSharesRecyclerView = (RecyclerView) view.findViewById(R.id.personsharesrecyclerview);
        sharedPreferences = getActivity().getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        _token = sharedPreferences.getString("token", "");
        try {
            getPersonalShares();
        } catch (Exception e) {
            Toast.makeText(getActivity(),"System error",Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void getPersonalShares(){
        personalShareData = new ArrayList<>();
        personalShareData.clear();
        Call<PersonalShareResponseModel> call = RetrofitClient
                .getInstance().getApi().fetchPersonalSharedata("Bearer " +  _token);

        String s = call.toString();
        call.enqueue(new Callback<PersonalShareResponseModel>() {
            @Override
            public void onResponse(Call<PersonalShareResponseModel> call, Response<PersonalShareResponseModel> response) {
                personalShareResponseModel = response.body();
                if ( personalShareResponseModel.isSuccess()){
                    personalShareData.clear();
                    personalSharesAdapter = new PersonalSharesAdapter(getContext(), personalShareResponseModel.getData());
                    personalSharesRecyclerView.setHasFixedSize(true);
                    personalSharesRecyclerView.setLayoutManager(layoutManager);
                    personalSharesRecyclerView.setAdapter(personalSharesAdapter);
                    personalSharesLoader.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast.makeText(getContext(),"Nothing..",Toast.LENGTH_LONG).show();
                    //Log.i("Check this ","not workinnnnnnnng");
                }
            }

            @Override
            public void onFailure(Call<PersonalShareResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        personalSharesLoader.setVisibility(View.VISIBLE);
        try {
            getPersonalShares();
        } catch (Exception e) {
            Toast.makeText(getActivity(),"System error",Toast.LENGTH_SHORT).show();
        }
    }
}

