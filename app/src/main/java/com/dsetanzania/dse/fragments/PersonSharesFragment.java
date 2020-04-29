package com.dsetanzania.dse.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dsetanzania.dse.R;

public class PersonSharesFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    View view;

    // newInstance constructor for creating fragment with arguments
    public static PersonSharesFragment newInstance(int page, String title) {
        PersonSharesFragment faqsFragment = new PersonSharesFragment();
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
        view = inflater.inflate(R.layout.fragment_person_shares, container, false);

        return view;
    }
}

