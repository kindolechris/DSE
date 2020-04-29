package com.dsetanzania.dse.fragments;


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
import com.dsetanzania.dse.adapters.SimulatedBondsAdapter;
import com.dsetanzania.dse.models.BondsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuyBondsFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    //SnappingRecyclerView livemarketpricerecyclerview;
    RecyclerView bondlistrecyclerviewrecyclerview;
    ArrayList<BondsModel> bonds;
    DatabaseReference reference;
    SimulatedBondsAdapter simulatedBondsAdapter;
    View view;

    // newInstance constructor for creating fragment with arguments
    public static BuyBondsFragment newInstance(int page, String title) {
        BuyBondsFragment faqsFragment = new BuyBondsFragment();
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
            view = inflater.inflate(R.layout.fragment_buy_bond, container, false);
            bondlistrecyclerviewrecyclerview = (RecyclerView) view.findViewById(R.id.bondlistrecycler);

            getMarkets();

        }

        return view;
    }

    private void  getMarkets(){
        //serverpgsBar.setVisibility(View.VISIBLE);
        bonds = new ArrayList<BondsModel>();
        bonds.clear();
        reference = FirebaseDatabase.getInstance().getReference("Bonds");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bonds.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    BondsModel _market = snapshot.getValue(BondsModel.class);
                    bonds.add(_market);
                }
                simulatedBondsAdapter = new SimulatedBondsAdapter(getContext(), bonds);
                bondlistrecyclerviewrecyclerview.setHasFixedSize(true);
                bondlistrecyclerviewrecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                bondlistrecyclerviewrecyclerview.setAdapter(simulatedBondsAdapter);
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(bondlistrecyclerviewrecyclerview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

