package com.dsetanzania.dse.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PortfolioActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtusername;
    TextView txtbonds;
    TextView txtshares;
    TextView txtuniversity;
    TextView txtvirtualmoney;
    LinearLayout cardshares;
    LinearLayout cardbonds;
    //TextView txtshares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        //user = new User();
        txtusername  = (TextView) findViewById(R.id.txtusername);
        txtvirtualmoney  = (TextView) findViewById(R.id.txtvirtualmoney);
        txtbonds  = (TextView) findViewById(R.id.bondtxt);
        txtshares  = (TextView) findViewById(R.id.sharestxt);
        txtuniversity  = (TextView) findViewById(R.id.txtuniversity);
        cardshares = (LinearLayout) findViewById(R.id.cardshares);
        cardbonds = (LinearLayout) findViewById(R.id.cardbonds);

        txtusername.setSelected(true);
        txtuniversity.setSelected(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(0);

        cardshares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PortfolioActivity.this, TotalSharesHoldingsActivity.class);
                PortfolioActivity.this.startActivity(myIntent);
            }
        });

        cardbonds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PortfolioActivity.this, TotalBondsHoldingsActivity.class);
                PortfolioActivity.this.startActivity(myIntent);
            }
        });

        readFromLocalDb();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Portfolio");
        }


    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    public void readFromLocalDb(){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readUserFromLocalDatabase(database);
        String tradername="";
        String university="";
        String virtualmoney="";
        int stock=0;
        int id=-1;
        int bonds=0;
        int sync_status;
        while(cursor.moveToNext()){
            tradername = cursor.getString(cursor.getColumnIndex(DbContract.tradername));
            virtualmoney = cursor.getString(cursor.getColumnIndex(DbContract.virtualmoney));
            university = cursor.getString(cursor.getColumnIndex(DbContract.university));
            stock = cursor.getInt(cursor.getColumnIndex(DbContract.stock));
            id = cursor.getInt(cursor.getColumnIndex("id"));
            bonds = cursor.getInt(cursor.getColumnIndex(DbContract.bonds));

        }
        updateFieldsOnChange(tradername,String.valueOf(stock),String.valueOf(bonds),university,virtualmoney);
        cursor.close();
        dbHelper.close();
    }


    public void updateFieldsOnChange(String fullname,String stock,String bonds,String university,String virtualmoney){
        NumberFormat formatter = new DecimalFormat("#,###");
        txtusername.setText(fullname);
        txtshares.setText(stock);
        txtbonds.setText(bonds);
        txtuniversity.setText(university);
        txtvirtualmoney.setText(formatter.format((Double.parseDouble(virtualmoney))));
    }
}
