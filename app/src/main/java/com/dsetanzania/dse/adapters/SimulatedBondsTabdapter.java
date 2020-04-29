package com.dsetanzania.dse.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dsetanzania.dse.fragments.BuyBondsFragment;
import com.dsetanzania.dse.fragments.SellBondsFragment;

public class SimulatedBondsTabdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    private String tabTitles[] = new String[] { "Buy", "Sale"};

    public SimulatedBondsTabdapter(FragmentManager fragmentManager) {
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
                return BuyBondsFragment.newInstance(0, "Buy");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return SellBondsFragment.newInstance(1, "Sale");
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