package com.dsetanzania.dse.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.SimulatedBondsTabdapter;
import com.google.android.material.tabs.TabLayout;

public class SimulatedBondsActivity extends AppCompatActivity {


    Toolbar toolbar;
    FragmentPagerAdapter adapterViewPager;
    ViewPager vpPager;
    TabLayout tabLayout;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity__simulate_bond__market);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setElevation(0);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Simulated Bonds Trading");
        }

            vpPager = (ViewPager) findViewById(R.id.bondsviewpager);
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        adapterViewPager = new SimulatedBondsTabdapter(getSupportFragmentManager());

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
