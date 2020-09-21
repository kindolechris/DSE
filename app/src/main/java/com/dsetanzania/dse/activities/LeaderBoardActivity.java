package com.dsetanzania.dse.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.BoardSharesAdapter;
import com.dsetanzania.dse.adapters.ListofLeaderBoardAdapter;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.models.AuthResponseModel;
import com.dsetanzania.dse.models.LeaderBoardResponseModel;
import com.dsetanzania.dse.models.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class LeaderBoardActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<UserModel> leaderboardusers;
    ListofLeaderBoardAdapter listofLeaderBoardAdapter;
    RecyclerView leaderboardrecycler;
    TextView txtusername;
    TextView txtuniversity;
    private SharedPreferences sharedPreferences;
    private String _token;
    ProgressBar leaderboardloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        leaderboardrecycler = (RecyclerView) findViewById(R.id.leaderboardrecycler);
        leaderboardloader = (ProgressBar) findViewById(R.id.leaderboardloader);
        txtusername = (TextView) findViewById(R.id.txtusername);
        txtuniversity = (TextView) findViewById(R.id.txtuniversity);
        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        _token = sharedPreferences.getString("token", "");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Top 10 Leaderboard");
        }

        getleaderboards();
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }

    private void  getleaderboards(){
        //serverpgsBar.setVisibility(View.VISIBLE);

        Call<LeaderBoardResponseModel> call = RetrofitClient
                .getInstance().getApi().fetchLeaderBoardsdata("Bearer " +  _token);

        call.enqueue(new Callback<LeaderBoardResponseModel>() {
            @Override
            public void onResponse(Call<LeaderBoardResponseModel> call, Response<LeaderBoardResponseModel> response) {
                if(response.isSuccessful()){
                    LeaderBoardResponseModel leaderboards = response.body();
                    if (leaderboards.isSuccess()){
                        listofLeaderBoardAdapter = new ListofLeaderBoardAdapter(LeaderBoardActivity.this, leaderboards.getUserModel());
                        leaderboardrecycler.setHasFixedSize(true);
                        leaderboardrecycler.setLayoutManager(new LinearLayoutManager(LeaderBoardActivity.this));
                        leaderboardrecycler.setAdapter(listofLeaderBoardAdapter);
                        leaderboardloader.setVisibility(View.INVISIBLE);
                    }
                    else{
                        //Toast.makeText(HomeActivity.this,"leaders boards",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    leaderboardloader.setVisibility(View.INVISIBLE);
                    Toast.makeText(LeaderBoardActivity.this,"Server error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LeaderBoardResponseModel> call, Throwable t) {

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
