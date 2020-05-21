package com.dsetanzania.dse.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.tabs.NewsTabdapter;
import com.google.android.material.tabs.TabLayout;

public class NewsActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    FragmentPagerAdapter adapterViewPager;
    ViewPager vpPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("News");
        }

        vpPager = (ViewPager) findViewById(R.id.news_view_Pager);
        tabLayout = (TabLayout) findViewById(R.id.news_tab_layout);

        adapterViewPager = new NewsTabdapter(getSupportFragmentManager());

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

}
