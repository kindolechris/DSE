package com.dsetanzania.dse.fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dsetanzania.dse.adapters.ListofBondsTransactionAdapter;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.interfaces.InternetcheckInterface;
import com.dsetanzania.dse.models.PersonalBondTransactionModel;
import com.dsetanzania.dse.models.transactions.transactionlist.PersonalBondTransactionListResponseModel;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;


public class BondsTransactionFragment extends Fragment {

    private String title;
    private int page;
    TextView txtnotransaction;
    View view;
    private String userId;
    private String _token;
    DbHelper dbHelper;
    SQLiteDatabase database;
    private SharedPreferences sharedPreferences;
    ArrayList<PersonalBondTransactionModel> transaction;
    RecyclerView recyclerView;
    ListofBondsTransactionAdapter listofBondsTransactionAdapter;
    LinearLayoutManager layoutManager;
    ProgressBar bondstransactionLoader;

    // newInstance constructor for creating fragment with arguments
    public static BondsTransactionFragment newInstance(int page, String title) {
        BondsTransactionFragment bondsTransactionFragment = new BondsTransactionFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        bondsTransactionFragment.setArguments(args);
        return bondsTransactionFragment;
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

        if(view==null){
            view = inflater.inflate(R.layout.fragment_bonds_transaction, container, false);
            layoutManager = new LinearLayoutManager(getActivity());
            sharedPreferences = getActivity().getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
            userId = sharedPreferences.getString("userid", "");
            _token = sharedPreferences.getString("token", "");
            txtnotransaction = (TextView) view.findViewById(R.id.txtnotransaction);
            dbHelper = new DbHelper(getActivity());
            database = dbHelper.getWritableDatabase();
            bondstransactionLoader = (ProgressBar) view.findViewById(R.id.bondstransactionLoader);
            txtnotransaction = (TextView) view.findViewById(R.id.txtnotransaction);
            recyclerView = (RecyclerView) view.findViewById(R.id.bondstransactionrecycler);
            recyclerView.setLayoutManager(layoutManager);
        }
        layoutManager = new LinearLayoutManager(getActivity());

        checkInternet task = new checkInternet(getContext(), new InternetcheckInterface() {
            @Override
            public void checkMethod(String result) {

                if(result == "Access"){
                    try {
                        getlivePersonalbondTransaction();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(),"System error",Toast.LENGTH_SHORT).show();
                    }

                }
                else if(result == "NoAccess"){
                    try {
                        readPersonalTransactionFromLocalDb();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(),"System error (DB)",Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                }
            }
        });

        task.execute();

        return view;
    }

    public void getlivePersonalbondTransaction(){
        Call<PersonalBondTransactionListResponseModel> call = RetrofitClient
                .getInstance().getApi().fetchUserBondTransaction("Bearer " +  _token);

        call.enqueue(new Callback<PersonalBondTransactionListResponseModel>() {
            @Override
            public void onResponse(Call<PersonalBondTransactionListResponseModel> call, Response<PersonalBondTransactionListResponseModel> response) {
                if(response.isSuccessful()){
                    PersonalBondTransactionListResponseModel personalBondTransactionListResponseModel = response.body();
                    if (personalBondTransactionListResponseModel.isSuccess()){
                        if(transactionTableIsEmpty()){
                            for(int i=0; i<personalBondTransactionListResponseModel.getPersonalBondTransactionModel().size(); i++) {
                                dbHelper.saveBondTransactionTolocalDatabase((personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getId()),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getBondnumber(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getStatus(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getBond_tenure(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getCoupon_rate(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getUnits(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getCreatedAt(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getTimeago(),database);
                            }
                        }
                        else{
                            droptabletransaction();
                            for(int i=0; i<personalBondTransactionListResponseModel.getPersonalBondTransactionModel().size(); i++) {
                                dbHelper.saveBondTransactionTolocalDatabase((personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getId()),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getBondnumber(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getStatus(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getBond_tenure(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getCoupon_rate(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getUnits(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getCreatedAt(),personalBondTransactionListResponseModel.getPersonalBondTransactionModel().get(i).getTimeago(),database);
                            }

                        }
                        //Toast.makeText(getActivity(),personalBondTransactionListResponseModel.getMessage(),Toast.LENGTH_SHORT).show();
                        readPersonalTransactionFromLocalDb();
                        bondstransactionLoader.setVisibility(View.INVISIBLE);
                    }
                    else{
                        txtnotransaction.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        bondstransactionLoader.setVisibility(View.INVISIBLE);
                        //Toast.makeText(getActivity(),personalBondTransactionListResponseModel.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Server error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PersonalBondTransactionListResponseModel> call, Throwable t) {
                bondstransactionLoader.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void readPersonalTransactionFromLocalDb(){
        if(transactionTableIsEmpty()){
            txtnotransaction.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            bondstransactionLoader.setVisibility(View.INVISIBLE);
        }
        else{
            txtnotransaction.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            bondstransactionLoader.setVisibility(View.INVISIBLE);
            transaction = new ArrayList<PersonalBondTransactionModel>();
            transaction.clear();
            DbHelper dbHelper = new DbHelper(getActivity());
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            Cursor cursor = dbHelper.readBondTransactionFromLocalDatabase(database);
            String id="";
            Integer units=0;
            String bondtransactiondate="";
            String bondtransactionTimeAgo="";
            String bondnumber="";
            String bondstatus="";
            while(cursor.moveToNext()){
                id = cursor.getString(cursor.getColumnIndex("id"));
                units = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DbContract.bond_price)));
                bondtransactiondate = cursor.getString(cursor.getColumnIndex(DbContract.bondtransactiondate));
                bondtransactionTimeAgo = cursor.getString(cursor.getColumnIndex(DbContract.bondtransactionTimeAgo));
                bondnumber = cursor.getString(cursor.getColumnIndex(DbContract.auction_date));
                bondstatus = cursor.getString(cursor.getColumnIndex(DbContract.bondstatus));
                PersonalBondTransactionModel personalBondTransactionModel = new PersonalBondTransactionModel(String.valueOf(units),bondtransactiondate,bondtransactionTimeAgo,id,bondnumber,bondstatus);
                transaction.add(personalBondTransactionModel);
            }

            listofBondsTransactionAdapter = new ListofBondsTransactionAdapter(getActivity(), transaction);
            recyclerView.setAdapter(listofBondsTransactionAdapter);
            cursor.close();
            dbHelper.close();
        }

    }

    public boolean transactionTableIsEmpty(){
        String count = "SELECT count(*) FROM " + DbContract.BOND_TRANSACTION_TABLE;
        Cursor mcursor = database.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
            return false;
        }
        return true;
    }

    public void droptabletransaction(){
        String query = "DELETE FROM "+ DbContract.BOND_TRANSACTION_TABLE;
        database.execSQL(query);
    }
}
