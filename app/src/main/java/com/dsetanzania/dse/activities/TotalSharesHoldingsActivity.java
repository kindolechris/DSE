package com.dsetanzania.dse.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.ListOfShareHoldingsAdapter;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.models.share_holdings.PersonalShareHoldingsResponseModal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class TotalSharesHoldingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListOfShareHoldingsAdapter listOfShareHoldingsAdapter;
    private PersonalShareHoldingsResponseModal personalShareHoldingsResponseModal;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar sharesholdingLoader;
    private SharedPreferences sharedPreferences;
    private String _token;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_shares_holdings);
        recyclerView = (RecyclerView) findViewById(R.id.shareholdingsrecycler);
        sharesholdingLoader = (ProgressBar) findViewById(R.id.sharesholdingLoader);
        layoutManager = new LinearLayoutManager(TotalSharesHoldingsActivity.this);
        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        _token = sharedPreferences.getString("token", "");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Total shares holdings");
        }

        try {
            getshareHoldings();
        } catch (Exception e) {
            Toast.makeText(TotalSharesHoldingsActivity.this,"System error",Toast.LENGTH_SHORT).show();
        }


    }


    public void getshareHoldings(){

        Call<PersonalShareHoldingsResponseModal> call = RetrofitClient
                .getInstance().getApi().fetchPersonalShareHoldings("Bearer " +  _token);

        call.enqueue(new Callback<PersonalShareHoldingsResponseModal>() {
            @Override
            public void onResponse(Call<PersonalShareHoldingsResponseModal> call, Response<PersonalShareHoldingsResponseModal> response) {
                if(response.isSuccessful()){
                    personalShareHoldingsResponseModal = response.body();
                    if ( personalShareHoldingsResponseModal.isSuccess()){
                        listOfShareHoldingsAdapter = new ListOfShareHoldingsAdapter(TotalSharesHoldingsActivity.this, personalShareHoldingsResponseModal.getPersonalShareHoldingDataModel().getPersonalShares());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(listOfShareHoldingsAdapter);
                        Log.i("Check this ","share holdings found");
                        sharesholdingLoader.setVisibility(View.INVISIBLE);
                    }
                    else{
                        sharesholdingLoader.setVisibility(View.INVISIBLE);
                        //Toast.makeText(HomeActivity.this,"Nothing",Toast.LENGTH_LONG).show();
                        Log.i("Check this ","No share holdings found");
                    }
                }else {
                    Toast.makeText(TotalSharesHoldingsActivity.this,"Server error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PersonalShareHoldingsResponseModal> call, Throwable t) {

            }
        });
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
