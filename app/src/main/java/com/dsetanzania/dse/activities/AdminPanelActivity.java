package com.dsetanzania.dse.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Build;
import android.os.Bundle;
import com.dsetanzania.dse.adapters.tabs.AdminTabdapter;
import com.dsetanzania.dse.R;
import com.google.android.material.tabs.TabLayout;

public class AdminPanelActivity extends AppCompatActivity {

    Toolbar toolbar;
    FragmentPagerAdapter adapterViewPager;
    ViewPager vpPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__simulate_equity__market);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle("Admin");
        }

        vpPager = (ViewPager) findViewById(R.id.adminpageviewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        adapterViewPager = new AdminTabdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        tabLayout.setupWithViewPager(vpPager);
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }

}
