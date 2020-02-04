package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.dsetanzania.dse.SimulatedMarketToEditAdapter;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUArrayOfSecurityLivePrice;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUSecurityLivePrice;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUdefault_AtsWebFeedService;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.SimulatedMarketAdapter;
import com.dsetanzania.dse.models.MarketSimulator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SimulatedTradeActivity extends AppCompatActivity {

    RecyclerView livemarketpricerecyclerview;
    ArrayList<MarketSimulator> simulatedMarket;
    DatabaseReference reference;
    SimulatedMarketAdapter simulatedMarketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simulated_trade);

        livemarketpricerecyclerview = (RecyclerView) findViewById(R.id.listofmarketrecycler);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Simulated trade");
        }

        getlivedata();
        /*ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, this));
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);*/
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void getlivedata(){
        GetLiveMarketTask gt = new GetLiveMarketTask();
        gt.execute();
    }



    class GetLiveMarketTask extends AsyncTask {

        SimulatedMarketAdapter lvm;

        @Override
        protected Object doInBackground(Object[] objects) {
            //testMedoth();

            try {

           /*     OOUdefault_AtsWebFeedService service = new OOUdefault_AtsWebFeedService("http://ht.ddnss.ch:6080/livefeedCustodian/FeedWebService.svc");
                OOUArrayOfSecurityLivePrice res = service.LiveMarketPrices();
                lvm = new SimulatedMarketAdapter(SimulatedTradeActivity.this,res);*/

                //lvm.pushMarkets();
                getMarkets();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

           /* //Toast.makeText(getApplicationContext(),"Size is : "+lvm.getItemCount(),Toast.LENGTH_LONG).show();
            livemarketpricerecyclerview.setHasFixedSize(true);
            livemarketpricerecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            livemarketpricerecyclerview.setAdapter(lvm);
            livemarketpricerecyclerview.setLayoutManager(new LinearLayoutManager(SimulatedTradeActivity.this, LinearLayoutManager.HORIZONTAL, false));
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(livemarketpricerecyclerview);*/
            //txttrandingstats.setText("Live trending stats");
            //prgs.setVisibility(View.INVISIBLE);
            //getElementsFromSOAP(resultSOAP);
            //parseXML();
        }
    }

    private void  getMarkets(){
        //serverpgsBar.setVisibility(View.VISIBLE);
        simulatedMarket = new ArrayList<MarketSimulator>();
        simulatedMarket.clear();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                simulatedMarket.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    MarketSimulator _market = snapshot.getValue(MarketSimulator.class);
                    simulatedMarket.add(_market);
                }
                simulatedMarketAdapter = new SimulatedMarketAdapter(SimulatedTradeActivity.this, simulatedMarket);

                livemarketpricerecyclerview.setHasFixedSize(true);
                livemarketpricerecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                livemarketpricerecyclerview.setAdapter(simulatedMarketAdapter);
                livemarketpricerecyclerview.setLayoutManager(new LinearLayoutManager(SimulatedTradeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(livemarketpricerecyclerview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
