package com.dsetanzania.dse.activities;

import android.os.Build;
import android.os.Bundle;

import com.dsetanzania.dse.models.SharesTransaction;
import com.dsetanzania.dse.models.User;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    ArrayList<SharesTransaction> transactionArray;
    User otheruserclass;
    SharesTransaction _transaction;
    ArrayList<User> otherUserArray;
    int status = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_share);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();

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
            companyname = extras.getString("Companyname");
            //Toast.makeText(this,"price is" + openingprice,Toast.LENGTH_LONG).show();
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Buy from " + companyname);
        }

        user = new User();
        otheruserclass = new User();
        _transaction = new SharesTransaction();


        buybtn = (Button) findViewById(R.id.btnBuyshares);
        //titletext.setText("Buy from " + companyname);
        getUserInfo();
        getTransactionsAndOtherUser();
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
                    pushTransaction("Successfully","","","","fromCompany");
                    txtreferenceId.setText("#DSE" + generateguid());
                    Toast.makeText(getApplicationContext(),"Purchase transaction was successfuly",Toast.LENGTH_SHORT).show();
                }
                else if(responce == "zero") {
                    Toast.makeText(getApplicationContext(),"You have zero virtual balance",Toast.LENGTH_SHORT).show();
                }
                else if(responce == "queued") {

                    if(transactionArray.isEmpty()){
                        pushTransaction("Queued","","","","thirdParty");
                        txtreferenceId.setText("#DSE" + generateguid());
                        Toast.makeText(BuyShareActivity.this,"Purchase transaction was queued",Toast.LENGTH_SHORT).show();
                        return;
                    }

                        for(final SharesTransaction trns : transactionArray){
                            for (User usr : otherUserArray){

                                if(usr.getUserId().equals(trns.getUserId())  && trns.getPrice() == Double.parseDouble(txtprice.getText().toString().trim()) && trns.getBoard().equals(companyname) && trns.getType().equals("Sales") && trns.getStatus().equals("Queued")){

                                    if(trns.getUserId().equals(fuser.getUid())){
                                        pushTransaction("Queued","","","","thirdParty");
                                        txtreferenceId.setText("#DSE" + generateguid());
                                        Toast.makeText(BuyShareActivity.this,"Purchase transaction was queued",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    reference = FirebaseDatabase.getInstance().getReference("Users").child(trns.getUserId());
                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            Double vm = dataSnapshot.child("virtualmoney").getValue(Double.class);
                                            int stock = dataSnapshot.child("stock").getValue(Integer.class);
                                            String tradername = dataSnapshot.child("tradername").getValue(String.class);
                                            String university = dataSnapshot.child("university").getValue(String.class);
                                            Map<String, Object> _Umap = new HashMap<>();
                                            _Umap.put("virtualmoney",  vm + (trns.getPrice() * trns.getShareAmount()));
                                            _Umap.put("stock",  stock - trns.getShareAmount());
                                            dataSnapshot.getRef().updateChildren(_Umap);
                                            Toast.makeText(BuyShareActivity.this,"Purchase transaction from third part was Successfully",Toast.LENGTH_SHORT).show();
                                            pushTransaction("Successfully",tradername,getdate(),university,"thirdParty");
                                            txtreferenceId.setText("#DSE" + generateguid());

                                            reference = FirebaseDatabase.getInstance().getReference("Transactions").child(trns.getTransId());
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("status", "Successfully");
                                            map.put("boughtOrSoldBy", user.getTradername());
                                            map.put("transactionSuccessfulldate",getdate());
                                            map.put("universictyfrom",user.getUniversity());
                                            reference.updateChildren(map);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            Double vm = dataSnapshot.child("virtualmoney").getValue(Double.class);
                                            int stock = dataSnapshot.child("stock").getValue(Integer.class);
                                            Map<String, Object> _Umap = new HashMap<>();
                                            _Umap.put("virtualmoney",  vm - (Double.parseDouble(txtprice.getText().toString().trim()) * Integer.parseInt(txtAmountofshares.getText().toString().trim())));
                                            _Umap.put("stock",  stock + Integer.parseInt(txtAmountofshares.getText().toString().trim()));
                                            dataSnapshot.getRef().updateChildren(_Umap);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    status = 3;
                                    break;
                                }
                                else {
                                    status = 2;
                                }

                            }

                            if(status == 3){
                                break;
                            }
                        }

                        if(status == 2){
                            pushTransaction("Queued","","","","thirdParty");
                            txtreferenceId.setText("#DSE" + generateguid());
                            Toast.makeText(BuyShareActivity.this,"Purchase transaction was queued",Toast.LENGTH_SHORT).show();
                            return;
                        }

                }
                else if(responce == "notsatisify") {
                    Toast.makeText(getApplicationContext(),"Purchase transaction less than 15% gap",Toast.LENGTH_SHORT).show();
                }
                else if(responce == "greaterthan") {
                    Toast.makeText(getApplicationContext(),"Purchase transaction greater than 15% price gap",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Purchase transaction went wrong",Toast.LENGTH_SHORT).show();
                }

                getTransactionsAndOtherUser();
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

    public void pushTransaction(String status,String soldby,String date,String university,String transactionParty){

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("Transactions");
        String id = reference.push().getKey();
        SharesTransaction tickets = new SharesTransaction("DSE" + generateguid(),fuser.getUid(),status,getCurrentTimeStamp(),companyname,Double.parseDouble(txtprice.getText().toString().trim()),Integer.parseInt(txtAmountofshares.getText().toString().trim()),"Purchase",id,soldby,date,university,transactionParty);
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
        return ticketId.substring(0,3);
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }

    public void getTransactionsAndOtherUser(){

        transactionArray = new ArrayList<SharesTransaction>();
        reference = FirebaseDatabase.getInstance().getReference("Transactions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    _transaction = snapshot.getValue(SharesTransaction.class);
                    transactionArray.add(_transaction);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        otherUserArray = new ArrayList<User>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    otheruserclass = snapshot.getValue(User.class);
                    otherUserArray.add(otheruserclass);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
