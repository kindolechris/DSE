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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<UserModel> leaderboardusers;
    DatabaseReference reference;
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
        leaderboardrecycler = (RecyclerView) findViewById(R.id.leaderboardrecycler);
        txtusername = (TextView) findViewById(R.id.txtusername);
        txtuniversity = (TextView) findViewById(R.id.txtuniversity);

        getleaderboars();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Leader board");
        }
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }

    private void  getleaderboars(){
        //serverpgsBar.setVisibility(View.VISIBLE);
        leaderboardusers = new ArrayList<UserModel>();
        leaderboardusers.clear();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserModel user = snapshot.getValue(UserModel.class);
                    leaderboardusers.add(user);
                }
                listofLeaderBoardAdapter = new ListofLeaderBoardAdapter(LeaderBoardActivity.this, leaderboardusers);

                leaderboardrecycler.setHasFixedSize(true);
                leaderboardrecycler.setLayoutManager(new LinearLayoutManager(LeaderBoardActivity.this));
                leaderboardrecycler.setAdapter(listofLeaderBoardAdapter);
                leaderboardrecycler.setLayoutManager(new LinearLayoutManager(LeaderBoardActivity.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
