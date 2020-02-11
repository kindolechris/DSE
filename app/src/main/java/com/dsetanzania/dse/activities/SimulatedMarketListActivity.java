package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.SimulatedMarketAdapter;
import com.dsetanzania.dse.SimulatedMarketToEditAdapter;
import com.dsetanzania.dse.models.MarketSimulator;
import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseAuth mAuth;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__simulate__market);

        mAuth = FirebaseAuth.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolBarElevation(7);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle("Admin");
        }

        livemarketrecyclerview = (RecyclerView) findViewById(R.id.MarketsPriceListRecyclerView);

        getMarkets();
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
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
                simulatedMarketAdapter = new SimulatedMarketToEditAdapter(SimulatedMarketListActivity.this, simulatedMarket);

                livemarketrecyclerview.setHasFixedSize(true);
                livemarketrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                livemarketrecyclerview.setAdapter(simulatedMarketAdapter);
                livemarketrecyclerview.setLayoutManager(new LinearLayoutManager(SimulatedMarketListActivity.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                simulatedMarketAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signOut) {
            mAuth.signOut();
            Intent homeintent = new Intent(SimulatedMarketListActivity.this, LoginActivity.class);
            SimulatedMarketListActivity.this.startActivity(homeintent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
