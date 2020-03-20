package com.dsetanzania.dse.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsetanzania.dse.ListofSharesTransactionAdapter;
import com.dsetanzania.dse.R;
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
 * Use the {@link EquityTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquityTransactionFragment extends Fragment {


    private String title;
    private int page;;
    View view;
    ArrayList<SharesTransaction> transaction;
    RecyclerView recyclerView;
    RecyclerView.Adapter listOfTransactionAdapter;
    private FirebaseAuth mAuth;
    LinearLayoutManager layoutManager;

    // newInstance constructor for creating fragment with arguments
    public static EquityTransactionFragment newInstance(int page, String title) {
        EquityTransactionFragment equityTransactionFragment = new EquityTransactionFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        equityTransactionFragment.setArguments(args);
        return equityTransactionFragment;
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
        view = inflater.inflate(R.layout.fragment_equity_transaction, container, false);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.transactionlistRecyclerview);

        getTransactions();
        // Inflate the layout for this fragment
        return view;
    }

    private void  getTransactions(){

        transaction = new ArrayList<SharesTransaction>();

        transaction.clear();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fuser = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Transactions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transaction.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SharesTransaction _transaction = snapshot.getValue(SharesTransaction.class);
                    if(_transaction.getUserId().equals(fuser.getUid())){
                        transaction.add(_transaction);
                    }
                }

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setNestedScrollingEnabled(false);
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);

                listOfTransactionAdapter = new ListofSharesTransactionAdapter(getActivity(),transaction);
                recyclerView.setAdapter(listOfTransactionAdapter);;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
