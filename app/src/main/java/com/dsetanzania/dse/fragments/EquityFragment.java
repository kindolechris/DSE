package com.dsetanzania.dse.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.activities.LoginActivity;
import com.dsetanzania.dse.adapters.SimulatedMarketToEditAdapter;
import com.dsetanzania.dse.models.BoardSharesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EquityFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    View view;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    int status = 1;
    RecyclerView livemarketrecyclerview;
    ArrayList<BoardSharesModel> simulatedMarket;
    SimulatedMarketToEditAdapter simulatedMarketAdapter;
    Toolbar toolbar;


    // newInstance constructor for creating fragment with arguments
    public static EquityFragment newInstance(int page, String title) {
        EquityFragment faqsFragment = new EquityFragment();
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
        setHasOptionsMenu(true);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.fragment_shares_equity, container, false);

        livemarketrecyclerview =  view.findViewById(R.id.MarketsPriceListRecyclerView);

        getMarkets();

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
                simulatedMarketAdapter = new SimulatedMarketToEditAdapter(getContext(), simulatedMarket);

                livemarketrecyclerview.setHasFixedSize(true);
                livemarketrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                livemarketrecyclerview.setAdapter(simulatedMarketAdapter);
                livemarketrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {

        inflater.inflate(R.menu.admin_menu, menu);

        menu.findItem(R.id.search_shares).setVisible(true);
        menu.findItem(R.id.search_bonds).setVisible(false);

        MenuItem searchItem = menu.findItem(R.id.search_shares);
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

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signOut) {
            mAuth.signOut();
            Intent homeintent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(homeintent);
            getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

