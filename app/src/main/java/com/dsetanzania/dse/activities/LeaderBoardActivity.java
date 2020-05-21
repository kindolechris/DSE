package com.dsetanzania.dse.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.ListofLeaderBoardAdapter;
import com.dsetanzania.dse.models.UserModel;
import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<UserModel> leaderboardusers;
    ListofLeaderBoardAdapter listofLeaderBoardAdapter;
    RecyclerView leaderboardrecycler;
    TextView txtusername;
    TextView txtuniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        leaderboardrecycler = (RecyclerView) findViewById(R.id.leaderboardrecycler);
        txtusername = (TextView) findViewById(R.id.txtusername);
        txtuniversity = (TextView) findViewById(R.id.txtuniversity);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Leader board");
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

        leaderboardusers = new ArrayList<UserModel>();
        leaderboardusers.clear();

        listofLeaderBoardAdapter = new ListofLeaderBoardAdapter(LeaderBoardActivity.this, leaderboardusers);

        leaderboardrecycler.setHasFixedSize(true);
        leaderboardrecycler.setLayoutManager(new LinearLayoutManager(LeaderBoardActivity.this));
        leaderboardrecycler.setAdapter(listofLeaderBoardAdapter);
        leaderboardrecycler.setLayoutManager(new LinearLayoutManager(LeaderBoardActivity.this));

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
