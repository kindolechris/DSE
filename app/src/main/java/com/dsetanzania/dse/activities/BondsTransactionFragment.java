package com.dsetanzania.dse.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsetanzania.dse.ListofBondsTransactionAdapter;
import com.dsetanzania.dse.ListofSharesTransactionAdapter;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.SimulatedBondsAdapter;
import com.dsetanzania.dse.models.BondTransaction;
import com.dsetanzania.dse.models.Bonds;
import com.dsetanzania.dse.models.SharesTransaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BondsTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BondsTransactionFragment extends Fragment {


    private String title;
    private int page;
    //SnappingRecyclerView livemarketpricerecyclerview;
    RecyclerView bondlistrecyclerviewrecyclerview;
    ArrayList<BondTransaction> bonds;
    DatabaseReference reference;
    SimulatedBondsAdapter simulatedBondsAdapter;
    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter listOfTransactionAdapter;
    private FirebaseAuth mAuth;
    LinearLayoutManager layoutManager;

    // newInstance constructor for creating fragment with arguments
    public static BondsTransactionFragment newInstance(int page, String title) {
        BondsTransactionFragment bondsTransactionFragment = new BondsTransactionFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        bondsTransactionFragment.setArguments(args);
        return bondsTransactionFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bonds_transaction, container, false);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.bondstransactionrecycler);

        getTransactions();
        return view;
    }

    private void  getTransactions(){

        bonds = new ArrayList<BondTransaction>();

        bonds.clear();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fuser = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BondsTransaction");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bonds.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    BondTransaction _transaction = snapshot.getValue(BondTransaction.class);
                    if(_transaction.getUserId().equals(fuser.getUid())){
                        bonds.add(_transaction);
                    }
                }

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setNestedScrollingEnabled(false);
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);

                listOfTransactionAdapter = new ListofBondsTransactionAdapter(getActivity(),bonds);
                recyclerView.setAdapter(listOfTransactionAdapter);;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
