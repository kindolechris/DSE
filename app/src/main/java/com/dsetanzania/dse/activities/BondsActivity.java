package com.dsetanzania.dse.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.ListOfBondsAdapter;
import com.dsetanzania.dse.adapters.SimulatedBondsAdapter;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.models.BondsModel;
import com.dsetanzania.dse.models.bonds.BondResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class BondsActivity extends AppCompatActivity {

    Toolbar toolbar;
    FragmentPagerAdapter adapterViewPager;
    RecyclerView bondlistrecyclerviewrecyclerview;
    ArrayList<BondsModel> bonds;
    ListOfBondsAdapter listofbondsadapter;
    private SharedPreferences sharedPreferences;
    private String _token;
    ProgressBar bondsLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity__simulate_bond__market);
        bondlistrecyclerviewrecyclerview = (RecyclerView) findViewById(R.id.bondlistrecycler);
        bondsLoader = (ProgressBar) findViewById(R.id.bondsLoader);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        _token = sharedPreferences.getString("token", "");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Simulated Bonds Trading");
        }

        try {
            getBondList();
        } catch (Exception e) {
            Toast.makeText(BondsActivity.this,"System error",Toast.LENGTH_SHORT).show();
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

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }

    private void getBondList(){

        Call<BondResponseModel> call = RetrofitClient
                .getInstance().getApi().fetchBondsList("Bearer " +  _token);

        call.enqueue(new Callback<BondResponseModel>() {
            @Override
            public void onResponse(Call<BondResponseModel> call, Response<BondResponseModel> response) {
                BondResponseModel bondResponseModel = response.body();
                if ( bondResponseModel.isSuccess()){
                    bondsLoader.setVisibility(View.INVISIBLE);
                    listofbondsadapter = new ListOfBondsAdapter(BondsActivity.this,bondResponseModel.getBondModel());
                    bondlistrecyclerviewrecyclerview.setHasFixedSize(true);
                    bondlistrecyclerviewrecyclerview.setLayoutManager(new LinearLayoutManager(BondsActivity.this, LinearLayoutManager.VERTICAL, false));
                    bondlistrecyclerviewrecyclerview.setAdapter(listofbondsadapter);
                }
                else{
                    bondsLoader.setVisibility(View.INVISIBLE);
                    //Toast.makeText(HomeActivity.this,"Nothing",Toast.LENGTH_LONG).show();
                    Log.i("Check this ","No bonds found");
                }
            }

            @Override
            public void onFailure(Call<BondResponseModel> call, Throwable t) {

            }
        });

    }
}
