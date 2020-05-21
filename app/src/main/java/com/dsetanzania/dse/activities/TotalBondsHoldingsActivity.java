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
import com.dsetanzania.dse.adapters.ListOfBondsHoldingsAdapter;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.models.PersonalBondHoldingsModel;
import com.dsetanzania.dse.models.PersonalBondTransactionModel;
import com.dsetanzania.dse.models.bond_holdings.PersonalBondHoldingResponseModal;
import com.dsetanzania.dse.models.share_holdings.PersonalShareHoldingsResponseModal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;
import static com.dsetanzania.dse.activities.LoginActivity.userId;

public class TotalBondsHoldingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListOfBondsHoldingsAdapter listOfBondsHoldingsAdapter;
    private PersonalBondHoldingResponseModal personalShareHoldingsResponseModel;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar sharesholdingLoader;
    private SharedPreferences sharedPreferences;
    private String _token;
    private Toolbar toolbar;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_bonds_holdings);
        recyclerView = (RecyclerView) findViewById(R.id.bongholdingsrecycler);
        sharesholdingLoader = (ProgressBar) findViewById(R.id.bondholdingLoader);
        layoutManager = new LinearLayoutManager(TotalBondsHoldingsActivity.this);
        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        userId = sharedPreferences.getInt("userid", -1);
        _token = sharedPreferences.getString("token", "");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Total bonds holdings");
        }


        try {
            getbondHoldings();
        } catch (Exception e) {
            sharesholdingLoader.setVisibility(View.INVISIBLE);
            Toast.makeText(TotalBondsHoldingsActivity.this,"System error",Toast.LENGTH_SHORT).show();
        }
    }


    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }

    private void getbondHoldings() {

        Call<PersonalBondHoldingResponseModal> call = RetrofitClient
                .getInstance().getApi().fetchPersonalBondeHoldings(String.valueOf(userId),"Bearer " +  _token);

        call.enqueue(new Callback<PersonalBondHoldingResponseModal>() {
            @Override
            public void onResponse(Call<PersonalBondHoldingResponseModal> call, Response<PersonalBondHoldingResponseModal> response) {
                personalShareHoldingsResponseModel = response.body();
                if ( personalShareHoldingsResponseModel.isSuccess()){
                    listOfBondsHoldingsAdapter = new ListOfBondsHoldingsAdapter(TotalBondsHoldingsActivity.this, personalShareHoldingsResponseModel.getPersonalBondHoldingsModel());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(listOfBondsHoldingsAdapter);
                    Log.i("Check this ","bond holdings found");
                    sharesholdingLoader.setVisibility(View.INVISIBLE);
                }
                else{
                    //Toast.makeText(HomeActivity.this,"Nothing",Toast.LENGTH_LONG).show();
                    Log.i("Check this ","No bond holdings found");
                }
            }

            @Override
            public void onFailure(Call<PersonalBondHoldingResponseModal> call, Throwable t) {

            }
        });
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
