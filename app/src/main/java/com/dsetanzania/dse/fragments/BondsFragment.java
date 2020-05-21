package com.dsetanzania.dse.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsetanzania.dse.activities.LoginActivity;
import com.dsetanzania.dse.adapters.ListOfBondsAdapter;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.adapters.SimulatedMarketToEditAdapter;
import com.dsetanzania.dse.models.BondsModel;
import com.dsetanzania.dse.models.BoardSharesModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BondsFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    View view;
    int status = 1;
    RecyclerView livemarketrecyclerview;
    ArrayList<BoardSharesModel> simulatedMarket;
    SimulatedMarketToEditAdapter simulatedMarketAdapter;
    Toolbar toolbar;
    FloatingActionButton bondsAddbtn;
    Dialog dialog;
    TextInputEditText bondnumbertxt;
    TextInputEditText bondmonthtxt;
    TextInputEditText bondratetxt;
    Button addbondbtn;
    ArrayList <BondsModel> bonds;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    // newInstance constructor for creating fragment with arguments
    public static BondsFragment newInstance(int page, String title) {
        BondsFragment faqsFragment = new BondsFragment();
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
        view = inflater.inflate(R.layout.fragment_bonds, container, false);
        bondsAddbtn = (FloatingActionButton) view.findViewById(R.id.addbondsbtn);
        layoutManager = new LinearLayoutManager(getActivity());


        dialog = new Dialog(getActivity(),R.style.Mydialogtheme);
        dialog.setContentView(R.layout.pop_up_add_bonds);
        recyclerView = (RecyclerView) view.findViewById(R.id.bondslistrecycler);

        bondnumbertxt = (TextInputEditText)dialog.findViewById(R.id.txtbondnumber);
        bondratetxt = (TextInputEditText)dialog.findViewById(R.id.txtbondrate);
        bondmonthtxt = (TextInputEditText) dialog.findViewById(R.id.txtbondmonths);
        addbondbtn = (Button)dialog.findViewById(R.id.Addbondsbutton);

        addbondbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(bondnumbertxt.getText().toString().trim()) || TextUtils.isEmpty(bondratetxt.getText().toString().trim()) || TextUtils.isEmpty(bondmonthtxt.getText().toString().trim())) {
                    Toast.makeText(getContext(),"Empty fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(bondratetxt.getText().toString().trim()) > 100) {
                    Toast.makeText(getContext(),"Purcentage rate cannot be larger than 100",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(bondmonthtxt.getText().toString().trim()) > 12) {
                    Toast.makeText(getContext(),"Bond month can not be longer than 12",Toast.LENGTH_SHORT).show();
                    return;
                }

                addBonds(Integer.parseInt(bondnumbertxt.getText().toString().trim()),Integer.parseInt(bondratetxt.getText().toString().trim()),Integer.parseInt(bondmonthtxt.getText().toString().trim()));
            }
        });

        bondsAddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dialog.show();
            }
        });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.admin_menu, menu);

        menu.findItem(R.id.search_shares).setVisible(false);
        menu.findItem(R.id.search_bonds).setVisible(true);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signOut) {
            Intent homeintent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(homeintent);
            getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addBonds(int bondnumber,int bondrate,int bondyears){

    }

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

}

