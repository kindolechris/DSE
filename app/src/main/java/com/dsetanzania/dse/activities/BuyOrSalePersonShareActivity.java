package com.dsetanzania.dse.activities;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.SharesTransactionModel;
import com.dsetanzania.dse.models.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class BuyOrSalePersonShareActivity extends AppCompatActivity {

    String openingprice;
    private DatabaseReference reference;
    FirebaseAuth mAuth;
    String companyname;
    Button buybtn;
    Button sellbtn;
    UserModel user;
    TextView titletext;
    TextView txtholdings;
    TextView txtAmount;
    TextInputEditText txtprice;
    TextInputEditText txtAmountofshares;
    TextView txtreferenceId;
    FirebaseUser fuser;
    Toolbar toolbar;
    ArrayList<SharesTransactionModel> transactionArray;
    SharesTransactionModel _transaction;
    ArrayList<UserModel> otherUserArray;
    int status = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_or_sale_person_share);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);

        //titletext = (TextView) findViewById(R.id.titlebuyshares);
        txtprice = (TextInputEditText) findViewById(R.id.txtprice);
        txtAmountofshares = (TextInputEditText) findViewById(R.id.txtquantity);
        txtreferenceId = (TextView) findViewById(R.id.txtreferenceId);
        txtreferenceId.setText("#DSE" + generateguid());

        txtholdings = (TextView) findViewById(R.id.txtholdings);
        txtAmount = (TextView) findViewById(R.id.txtstockbalance);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            openingprice = extras.getString("OpeningPrice");
            companyname = extras.getString("tradername");
            //Toast.makeText(this,"price is" + openingprice,Toast.LENGTH_LONG).show();
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Buy or Sell " + companyname);
        }


        buybtn = (Button) findViewById(R.id.btnBuyshares);
        sellbtn = (Button) findViewById(R.id.btnSellshares);

        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtAmountofshares.getText().toString().trim()) || TextUtils.isEmpty(txtprice.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Please place your quantity and price to buy",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        sellbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtAmountofshares.getText().toString().trim()) || TextUtils.isEmpty(txtprice.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Please place your quantity and price to sell",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    private String generateguid(){
        String  ticketId = UUID.randomUUID().toString();
        return ticketId.substring(0,3);
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }


    public static String getdate(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


}
