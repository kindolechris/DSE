package com.dsetanzania.dse.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.activities.ActiveShareHoldersActivity;
import com.dsetanzania.dse.activities.PortfolioActivity;
import com.dsetanzania.dse.activities.TheRoleOfInvestmentActivity;
import com.dsetanzania.dse.activities.TipsForInvestmentActivity;
import com.dsetanzania.dse.activities.TotalSharesHoldingsActivity;
import com.dsetanzania.dse.activities.ValueVsPriceActivity;
import com.dsetanzania.dse.adapters.FaqsAdapter;
import com.dsetanzania.dse.helperClasses.FaqsChildLayout;
import com.dsetanzania.dse.helperClasses.FaqsParentLayout;
import com.dsetanzania.dse.helperClasses.FaqsTitleCreator;
import com.dsetanzania.dse.models.BondTransactionModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticlesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticlesFragment extends Fragment {


    private String title;
    private int page;
    View view;
    LinearLayout cardtipsforinvestment;
    LinearLayout cardactiveshareholders;
    LinearLayout cardtheroleforinvestment;
    LinearLayout cardunderstandvalue;

    // newInstance constructor for creating fragment with arguments
    public static ArticlesFragment newInstance(int page, String title) {
        ArticlesFragment articlesFragment = new ArticlesFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        articlesFragment.setArguments(args);
        return articlesFragment;
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

        if(view == null){
            view = inflater.inflate(R.layout.fragment_articles_, container, false);
            cardtipsforinvestment = view.findViewById(R.id.card_tips_for_invest);
            cardactiveshareholders = view.findViewById(R.id.card_active_share_holders);
            cardtheroleforinvestment = view.findViewById(R.id.card_role_for_investment);
            cardunderstandvalue = view.findViewById(R.id.card_price_vs_share);
        }

        cardtipsforinvestment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), TipsForInvestmentActivity.class);
                getActivity().startActivity(myIntent);

            }
        });

        cardactiveshareholders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), ActiveShareHoldersActivity.class);
                getActivity().startActivity(myIntent);

            }
        });

        cardtheroleforinvestment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), TheRoleOfInvestmentActivity.class);
                getActivity().startActivity(myIntent);

            }
        });

        cardunderstandvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), ValueVsPriceActivity.class);
                getActivity().startActivity(myIntent);

            }
        });

        return view;
    }

}
