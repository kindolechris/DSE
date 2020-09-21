package com.dsetanzania.dse.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.models.UserModel;
import com.dsetanzania.dse.models.transactions.buy.transaction.bonds.BuyBondResponseModel;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class BuyBondActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button buybondsbtn;
    UserModel user;
    TextView txtholdings;
    TextView txtAmount;
    TextView bondinfotxt;
    TextInputEditText txtquantity;
    String bondnumber;
    String bondrate;
    String bondduration;
    String bondid;
    String bondprice;
    ProgressBar bondprogressbar;
    LinearLayout buybondLayout;
    DbHelper dbHelper;
    private String _token;
    private SharedPreferences sharedPreferences;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_bonds);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        _token = sharedPreferences.getString("token", "");
        buybondsbtn = (Button) findViewById(R.id.btnbuybonds);
        buybondLayout = (LinearLayout) findViewById(R.id.buybondLayout);
        txtholdings = (TextView) findViewById(R.id.txtbondholdings);
        bondinfotxt = (TextView) findViewById(R.id.bondinfotxt);
        txtAmount = (TextView) findViewById(R.id.txtvirtualbalance);
        txtquantity = (TextInputEditText) findViewById(R.id.txtquantity);
        bondprogressbar = (ProgressBar) findViewById(R.id.bondprogressbar);
        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bondnumber = extras.getString("Bondnumber");
            bondrate = extras.getString("Rate");
            bondduration = extras.getString("Duration");
            bondprice = extras.getString("bondprice");
            bondid = extras.getString("bondid");
        }


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Buying bond " + "( " + bondnumber + " )");
        }

        bondinfotxt.setText("You are about to buy "+ bondnumber + " bond with an interest rate of " + bondrate + "% that you will gain each month.");
        buybondsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(TextUtils.isEmpty(txtquantity.getText().toString().trim())){
                        Toast.makeText(getApplicationContext(),"Place your price to buy",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    bondprogressbar.setVisibility(View.VISIBLE);
                    buybondLayout.setVisibility(View.INVISIBLE);
                    buybond(bondid,txtquantity.getText().toString().trim());
                   } catch (Exception e) {
                    Toast.makeText(BuyBondActivity.this,"System error",Toast.LENGTH_SHORT).show();
               }
            }
        });

        try {
            readFromLocalDb();
        } catch (Exception e) {
            Toast.makeText(BuyBondActivity.this,"System error (DB)",Toast.LENGTH_SHORT).show();
        }

    }

    public void updateFieldsOnChange(String stock,Double virtualmoney){
        NumberFormat formatter = new DecimalFormat("#,###");
        txtholdings.setText(stock);
        txtAmount.setText(String.valueOf(formatter.format(virtualmoney)));
    }

    public void readFromLocalDb(){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readUserFromLocalDatabase(database);
        double virtualmoney=0;
        int bond =0;
        while(cursor.moveToNext()){

            virtualmoney = cursor.getDouble(cursor.getColumnIndex(DbContract.virtualmoney));
            bond = cursor.getInt(cursor.getColumnIndex(DbContract.bonds));
        }
        updateFieldsOnChange(String.valueOf(bond),virtualmoney);
        cursor.close();
        dbHelper.close();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatibility
        }
    }


    public void buybond(String bondid,String price){
        Call<BuyBondResponseModel> call = RetrofitClient
                .getInstance().getApi().buyBonds(bondid,price,"Bearer " + _token);
        call.enqueue(new Callback<BuyBondResponseModel>() {
            @Override
            public void onResponse(Call<BuyBondResponseModel> call, Response<BuyBondResponseModel> response) {
                BuyBondResponseModel buyBondResponseModel = response.body();

                if (buyBondResponseModel.isSuccess()){
                    bondprogressbar.setVisibility(View.INVISIBLE);
                    buybondLayout.setVisibility(View.VISIBLE);
                    //dbHelper.updateUserLocalDatabase(String.valueOf(buyBondResponseModel.getData().getUser().getId()),buyBondResponseModel.getData().getUser().getStock(),buyBondResponseModel.getData().getUser().getBonds(),buyBondResponseModel.getData().getUser().getFirstname(),buyBondResponseModel.getData().getUser().getLastname(),buyBondResponseModel.getData().getUser().getTradername(),buyBondResponseModel.getData().getUser().getEmail(),buyBondResponseModel.getData().getUser().getYearOfStudy(),buyBondResponseModel.getData().getUser().getUniversity(),buyBondResponseModel.getData().getUser().getCoursename(),buyBondResponseModel.getData().getUser().getPhonenumber(),buyBondResponseModel.getData().getUser().getRole(),buyBondResponseModel.getData().getUser().getVirtualmoney(),buyBondResponseModel.getData().getUser().getGender(), DbContract.SYNC_STATUS_FAILED,database);
                    dbHelper.saveBondTransactionTolocalDatabase(buyBondResponseModel.getData().getPersonalBondsTransaction().getId(),bondnumber,buyBondResponseModel.getData().getPersonalBondsTransaction().getStatus(),bondduration,bondrate,txtquantity.getText().toString().trim(),buyBondResponseModel.getData().getPersonalBondsTransaction().getCreatedAt(),buyBondResponseModel.getData().getPersonalBondsTransaction().getTimeago(),database);
                    Toast.makeText(BuyBondActivity.this, buyBondResponseModel.getMessage(),Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    bondprogressbar.setVisibility(View.INVISIBLE);
                    buybondLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(BuyBondActivity.this, buyBondResponseModel.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BuyBondResponseModel> call, Throwable t) {
                bondprogressbar.setVisibility(View.INVISIBLE);
                buybondLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
