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
import com.dsetanzania.dse.models.bond_holdings.PersonalBondHoldingResponseModal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class TotalBondsHoldingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListOfBondsHoldingsAdapter listOfBondsHoldingsAdapter;
    private PersonalBondHoldingResponseModal personalShareHoldingsResponseModel;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar bondsholdingLoader;
    private SharedPreferences sharedPreferences;
    private String _token;
    private Toolbar toolbar;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_bonds_holdings);
        recyclerView = (RecyclerView) findViewById(R.id.bongholdingsrecycler);
        bondsholdingLoader = (ProgressBar) findViewById(R.id.bondholdingLoader);
        layoutManager = new LinearLayoutManager(TotalBondsHoldingsActivity.this);
        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        userId = sharedPreferences.getString("userid", "");
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
            bondsholdingLoader.setVisibility(View.INVISIBLE);
            Toast.makeText(TotalBondsHoldingsActivity.this,"Exception thrown",Toast.LENGTH_SHORT).show();
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
                .getInstance().getApi().fetchPersonalBondeHoldings("Bearer " +  _token);

        call.enqueue(new Callback<PersonalBondHoldingResponseModal>() {
            @Override
            public void onResponse(Call<PersonalBondHoldingResponseModal> call, Response<PersonalBondHoldingResponseModal> response) {
                if(response.isSuccessful()){
                    personalShareHoldingsResponseModel = response.body();
                    if ( personalShareHoldingsResponseModel.isSuccess()){
                        listOfBondsHoldingsAdapter = new ListOfBondsHoldingsAdapter(TotalBondsHoldingsActivity.this, personalShareHoldingsResponseModel.getPersonalBondHoldingsModel());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(listOfBondsHoldingsAdapter);
                        bondsholdingLoader.setVisibility(View.INVISIBLE);
                    }
                    else{
                        bondsholdingLoader.setVisibility(View.INVISIBLE);
                        //Toast.makeText(HomeActivity.this,"Nothing",Toast.LENGTH_LONG).show();
                        Log.i("Check this ","No bond holdings found");
                    }
                }
                else{
                    Toast.makeText(TotalBondsHoldingsActivity.this,"Server error",Toast.LENGTH_LONG).show();
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
