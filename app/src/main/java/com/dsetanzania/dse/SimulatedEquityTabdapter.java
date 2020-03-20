package com.dsetanzania.dse;


import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dsetanzania.dse.activities.BuySharesFragment;
import com.dsetanzania.dse.activities.SellSharesFragment;

public class SimulatedEquityTabdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    private String tabTitles[] = new String[] { "Buy", "Sale"};

    public SimulatedEquityTabdapter(FragmentManager fragmentManager) {
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
                return BuySharesFragment.newInstance(0, "Buy");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return SellSharesFragment.newInstance(1, "Sale");
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