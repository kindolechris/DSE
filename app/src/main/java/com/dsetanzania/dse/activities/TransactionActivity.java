package com.dsetanzania.dse.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.TransactionTabdapter;
import com.dsetanzania.dse.models.SharesTransactionModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {

    ArrayList<SharesTransactionModel> transaction;
    RecyclerView recyclerView;
    RecyclerView.Adapter listOfTransactionAdapter;
    private FirebaseAuth mAuth;
    LinearLayoutManager layoutManager;
    Toolbar toolbar;
    FragmentPagerAdapter adapterViewPager;
    ViewPager vpPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolBarElevation(7);

        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Transactions");
        }


        vpPager = (ViewPager) findViewById(R.id.transactionViewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        adapterViewPager = new TransactionTabdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        tabLayout.setupWithViewPager(vpPager);


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }



}
