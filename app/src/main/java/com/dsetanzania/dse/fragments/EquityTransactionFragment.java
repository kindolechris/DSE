package com.dsetanzania.dse.fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.dsetanzania.dse.adapters.ListofSharesTransactionAdapter;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.interfaces.InternetcheckInterface;
import com.dsetanzania.dse.models.PersonalsharesTransactionModel;
import com.dsetanzania.dse.models.transactions.transactionlist.PersonalShareTransactionListResponseModel;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class EquityTransactionFragment extends Fragment {


    private String title;
    private int page;
    View view;
    TextView txtnotransaction;
    private int userId;
    private String _token;
    DbHelper dbHelper;
    SQLiteDatabase database;
    private SharedPreferences sharedPreferences;
    ArrayList<PersonalsharesTransactionModel> transaction;
    RecyclerView recyclerView;
    RecyclerView.Adapter listOfTransactionAdapter;
    LinearLayoutManager layoutManager;
    ProgressBar sharestransactionLoader;

    // newInstance constructor for creating fragment with arguments
    public static EquityTransactionFragment newInstance(int page, String title) {
        EquityTransactionFragment equityTransactionFragment = new EquityTransactionFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        equityTransactionFragment.setArguments(args);
        return equityTransactionFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_equity_transaction, container, false);
            layoutManager = new LinearLayoutManager(getActivity());
            sharedPreferences = getActivity().getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
            sharestransactionLoader = (ProgressBar)view.findViewById(R.id.sharestransactionLoader);
            txtnotransaction = (TextView) view.findViewById(R.id.txtnotransaction);
            userId = sharedPreferences.getInt("userid", -1);
            _token = sharedPreferences.getString("token", "");
            dbHelper = new DbHelper(getActivity());
            database = dbHelper.getWritableDatabase();

            recyclerView = (RecyclerView) view.findViewById(R.id.transactionlistRecyclerview);
            recyclerView.setLayoutManager(layoutManager);
        }

        checkInternet task = new checkInternet(getContext(), new InternetcheckInterface() {
            @Override
            public void checkMethod(String result) {

                if(result == "Access"){
                    try {
                        getlivePersonalSharesTransaction();
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return view;
    }


    public void readPersonalTransactionFromLocalDb(){
        if(transactionTableIsEmpty()){
            txtnotransaction.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            sharestransactionLoader.setVisibility(View.INVISIBLE);
        }
        else{
            txtnotransaction.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            sharestransactionLoader.setVisibility(View.INVISIBLE);
            transaction = new ArrayList<PersonalsharesTransactionModel>();
            transaction.clear();
            DbHelper dbHelper = new DbHelper(getActivity());
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            Cursor cursor = dbHelper.readShareTransactionFromLocalDatabase(database);
            int id=0;
            Integer sharesAmount=0;
            String transactiondate="";
            String boardShareName="";
            Integer price=0;
            String transactiontype="";
            String transactionstatus="";
            while(cursor.moveToNext()){
                id = cursor.getInt(cursor.getColumnIndex("id"));
                sharesAmount = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DbContract.sharesAmout)));
                transactiondate = cursor.getString(cursor.getColumnIndex(DbContract.sharetransactiondate));
                boardShareName = cursor.getString(cursor.getColumnIndex(DbContract.boardShareName));
                price = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DbContract.sharePrice)));
                transactiontype = cursor.getString(cursor.getColumnIndex(DbContract.transactiontype));
                transactionstatus = cursor.getString(cursor.getColumnIndex(DbContract.transactionstatus));
                PersonalsharesTransactionModel buyerSharesTransaction = new PersonalsharesTransactionModel(sharesAmount,price,transactionstatus,transactiondate,id,boardShareName,transactiontype);
                transaction.add(buyerSharesTransaction);
            }

            listOfTransactionAdapter = new ListofSharesTransactionAdapter(getActivity(), transaction);
            recyclerView.setAdapter(listOfTransactionAdapter);
            ((ListofSharesTransactionAdapter) listOfTransactionAdapter).setMode(Attributes.Mode.Single);
            cursor.close();
            dbHelper.close();
        }

    }

    public void getlivePersonalSharesTransaction(){
        Call<PersonalShareTransactionListResponseModel> call = RetrofitClient
                .getInstance().getApi().fetchUserShareTransaction(userId,"Bearer " +  _token);

        call.enqueue(new Callback<PersonalShareTransactionListResponseModel>() {
            @Override
            public void onResponse(Call<PersonalShareTransactionListResponseModel> call, Response<PersonalShareTransactionListResponseModel> response) {
                PersonalShareTransactionListResponseModel personalTransactionListResponseModel = response.body();
                if (personalTransactionListResponseModel.isSuccess()){


                    if(transactionTableIsEmpty()){
                        for(int i=0; i<personalTransactionListResponseModel.getPersonalsharesTransactionModel().size(); i++) {
                            dbHelper.saveShareTransactionTolocalDatabase((personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getId()),String.valueOf(personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getSharesamount()),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getCreatedAt(),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getCompanyname(),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getPrice(),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getTransactiontype(),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getStatus(),database);
                        }
                    }
                    else{
                        droptabletransaction();
                        for(int i=0; i<personalTransactionListResponseModel.getPersonalsharesTransactionModel().size(); i++) {
                            dbHelper.saveShareTransactionTolocalDatabase((personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getId()),String.valueOf(personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getSharesamount()),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getCreatedAt(),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getCompanyname(),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getPrice(),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getTransactiontype(),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getStatus(),database);
                        }

                       /* for(int i=0; i<personalTransactionListResponseModel.getPersonalsharesTransactionModel().size(); i++) {
                            dbHelper.updateShareTransactionTolocalDatabase(String.valueOf(personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getId()),String.valueOf(personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getSharesamount()),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getCreatedAt(),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getCompanyname(),String.valueOf(personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getPrice()),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getTransactiontype(),personalTransactionListResponseModel.getPersonalsharesTransactionModel().get(i).getStatus(),database);
                        }*/

                    }

                    readPersonalTransactionFromLocalDb();
                    sharestransactionLoader.setVisibility(View.INVISIBLE);
                }
                else{
                    txtnotransaction.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    sharestransactionLoader.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(),personalTransactionListResponseModel.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PersonalShareTransactionListResponseModel> call, Throwable t) {
                sharestransactionLoader.setVisibility(View.INVISIBLE);
            }
        });
    }

    public boolean transactionTableIsEmpty(){
        String count = "SELECT count(*) FROM " + DbContract.SHARE_TRANSACTION_TABLE;
        Cursor mcursor = database.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
        return false;
        }
        return true;
    }

    public void droptabletransaction(){
        String query = "DELETE FROM "+ DbContract.SHARE_TRANSACTION_TABLE;
        database.execSQL(query);
    }
}
