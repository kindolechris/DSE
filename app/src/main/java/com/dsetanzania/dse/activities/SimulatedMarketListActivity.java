package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.SimulatedMarketAdapter;
import com.dsetanzania.dse.SimulatedMarketToEditAdapter;
import com.dsetanzania.dse.models.MarketSimulator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SimulatedMarketListActivity extends AppCompatActivity {

    RecyclerView livemarketrecyclerview;
    ArrayList<MarketSimulator> simulatedMarket;
    DatabaseReference reference;
    SimulatedMarketToEditAdapter simulatedMarketAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__simulate__market);

        livemarketrecyclerview = (RecyclerView) findViewById(R.id.MarketsPriceListRecyclerView);

        getMarkets();
    }

    private void  getMarkets(){
        //serverpgsBar.setVisibility(View.VISIBLE);
        simulatedMarket = new ArrayList<MarketSimulator>();
        simulatedMarket.clear();
        reference = FirebaseDatabase.getInstance().getReference("MarketSimulator");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                simulatedMarket.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    MarketSimulator _market = snapshot.getValue(MarketSimulator.class);
                    simulatedMarket.add(_market);
                }
                simulatedMarketAdapter = new SimulatedMarketToEditAdapter(getApplicationContext(), simulatedMarket);

                livemarketrecyclerview.setHasFixedSize(true);
                livemarketrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                livemarketrecyclerview.setAdapter(simulatedMarketAdapter);
                livemarketrecyclerview.setLayoutManager(new LinearLayoutManager(SimulatedMarketListActivity.this));
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(livemarketrecyclerview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
