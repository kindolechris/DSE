package com.dsetanzania.dse.adapters.tabs;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dsetanzania.dse.fragments.AfricaNewsFragment;
import com.dsetanzania.dse.fragments.BussinessNewsFragment;
import com.dsetanzania.dse.fragments.TopHeadlinesNewsFragment;

public class NewsTabdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    private String tabTitles[] = new String[] { "Africa","Top headlines", "Business"};

    public NewsTabdapter(FragmentManager fragmentManager) {
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return AfricaNewsFragment.newInstance(0, "Africa");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return TopHeadlinesNewsFragment.newInstance(1, "Top headlines");
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return BussinessNewsFragment.newInstance(2, "Top Business");
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}