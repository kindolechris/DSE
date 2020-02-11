package com.dsetanzania.dse.activities;

import android.os.Build;
import android.os.Bundle;

import com.dsetanzania.dse.models.Transactions;
import com.dsetanzania.dse.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.dsetanzania.dse.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.ksoap2.serialization.ValueWriter;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BuyShareActivity extends AppCompatActivity {

    String openingprice;
    private DatabaseReference reference;
    FirebaseAuth mAuth;
    String companyname;
    Button buybtn;
    User user;
    TextView titletext;
    TextView txtholdings;
    TextView txtAmount;
    TextInputEditText txtprice;
    TextInputEditText txtAmountofshares;
    TextView txtreferenceId;
    FirebaseUser fuser;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_share);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();

        titletext = (TextView) findViewById(R.id.titlebuyshares);
        txtprice = (TextInputEditText) findViewById(R.id.txtprice);
        txtAmountofshares = (TextInputEditText) findViewById(R.id.txtquantity);
        txtreferenceId = (TextView) findViewById(R.id.txtreferenceId);
        txtreferenceId.setText("#DSE" + generateguid());

        txtholdings = (TextView) findViewById(R.id.txtholdings);
        txtAmount = (TextView) findViewById(R.id.txtstockbalance);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Buy shares");
        }

        user = new User();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            openingprice = extras.getString("OpeningPrice");
            companyname = extras.getString("Companyname");
            //Toast.makeText(this,"price is" + openingprice,Toast.LENGTH_LONG).show();
        }


        buybtn = (Button) findViewById(R.id.btnBuyshares);
        titletext.setText("Buy from " + companyname);
        getUserInfo();



        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(txtAmountofshares.getText().toString().trim()) || TextUtils.isEmpty(txtprice.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Please place your quantity or price to buy",Toast.LENGTH_SHORT).show();
                    return;
                }
                double doublepurseropeningprice = Double.parseDouble(openingprice);
                double doublepurserUserprice = Double.parseDouble(txtprice.getText().toString().trim());

                String responce = user.buyshares(doublepurseropeningprice,doublepurserUserprice,Integer.parseInt(txtAmountofshares.getText().toString().trim()));
                //String responce = thisUserClass.buyshares(120,doublepurserUserprice,Integer.parseInt(txtAmountofshares.getText().toString().trim()));
                if(responce == "successfully"){
                    pushTransaction("Successfully");
                    txtreferenceId.setText("#DSE" + generateguid());
                    Toast.makeText(getApplicationContext(),"Transaction was successfuly",Toast.LENGTH_SHORT).show();
                }
                else if(responce == "zero") {
                    Toast.makeText(getApplicationContext(),"You have zero balance",Toast.LENGTH_SHORT).show();
                }
                else if(responce == "queued") {
                    pushTransaction("Queued");
                    txtreferenceId.setText("#DSE" + generateguid());
                    Toast.makeText(getApplicationContext(),"Transaction queued",Toast.LENGTH_SHORT).show();
                }
                else if(responce == "notsatisify") {
                    Toast.makeText(getApplicationContext(),"Transaction less than 15% gap",Toast.LENGTH_SHORT).show();
                }
                else if(responce == "greaterthan") {
                    Toast.makeText(getApplicationContext(),"Transaction greater than 15% price gap",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Transaction went wrong",Toast.LENGTH_SHORT).show();
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

    public void getUserInfo(){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                NumberFormat formatter = new DecimalFormat("#,###");
                txtholdings.setText(String.valueOf(user.getStock()));
                txtAmount.setText(formatter.format(user.getVirtualmoney()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void pushTransaction(String status){

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("Transactions");
        String id = reference.push().getKey();
        Transactions tickets = new Transactions("DSE" + generateguid(),fuser.getUid(),status,getCurrentTimeStamp(),companyname,Double.parseDouble(txtprice.getText().toString().trim()),Integer.parseInt(txtAmountofshares.getText().toString().trim()),"Purchase",id);
        reference.child(id).setValue(tickets).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(getApplicationContext(),"Transaction pushed.",Toast.LENGTH_LONG).show();
            }
        });
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

    private String generateguid(){
        String  ticketId = UUID.randomUUID().toString();
        return ticketId.substring(0,8);
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }


}
