package com.dsetanzania.dse.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.BoardSharesAdapter;
import com.dsetanzania.dse.models.BoardSharesModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuySharesFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    //SnappingRecyclerView livemarketpricerecyclerview;
    RecyclerView livemarketpricerecyclerview;
    ArrayList<BoardSharesModel> simulatedMarket;
    DatabaseReference reference;
    BoardSharesAdapter simulatedMarketAdapter;
    View view;

    // newInstance constructor for creating fragment with arguments
    public static BuySharesFragment newInstance(int page, String title) {
        BuySharesFragment faqsFragment = new BuySharesFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        faqsFragment.setArguments(args);
        return faqsFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(view == null){
            view = inflater.inflate(R.layout.fragment_buy, container, false);
            livemarketpricerecyclerview = (RecyclerView) view.findViewById(R.id.listofmarketrecycler);
            getlivedata();

        }

        return view;
    }

    private void  getMarkets(){
        //serverpgsBar.setVisibility(View.VISIBLE);
        simulatedMarket = new ArrayList<BoardSharesModel>();
        simulatedMarket.clear();
        reference = FirebaseDatabase.getInstance().getReference("MarketSimulator");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                simulatedMarket.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    BoardSharesModel _market = snapshot.getValue(BoardSharesModel.class);
                    simulatedMarket.add(_market);
                }
                simulatedMarketAdapter = new BoardSharesAdapter(getContext(), simulatedMarket);
                livemarketpricerecyclerview.setHasFixedSize(true);
                livemarketpricerecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                livemarketpricerecyclerview.setAdapter(simulatedMarketAdapter);
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(livemarketpricerecyclerview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getlivedata(){
        GetLiveMarketTask gt = new GetLiveMarketTask();
        gt.execute();
    }



    class GetLiveMarketTask extends AsyncTask {

        BoardSharesAdapter lvm;

        @Override
        protected Object doInBackground(Object[] objects) {
            //testMedoth();

            try {

/*
               OOUdefault_AtsWebFeedService service = new OOUdefault_AtsWebFeedService("http://ht.ddnss.ch:6080/livefeedCustodian/FeedWebService.svc");
                OOUArrayOfSecurityLivePrice res = service.LiveMarketPrices();
                lvm = new SimulatedMarketAdapter(SimulatedTradeActivity.this,res);
*/

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

            //Toast.makeText(getApplicationContext(),"Size is : "+lvm.getItemCount(),Toast.LENGTH_LONG).show();
   /*         livemarketpricerecyclerview.setHasFixedSize(true);
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


}

