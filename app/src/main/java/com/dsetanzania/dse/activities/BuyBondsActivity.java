package com.dsetanzania.dse.activities;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.BondTransaction;
import com.dsetanzania.dse.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BuyBondsActivity extends AppCompatActivity {

    Toolbar toolbar;
    String bondnumber,rate,duration;
    TextInputEditText txtquantity;
    TextInputEditText txtprice;
    Button buybondsbtn;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser fuser;
    User user;
    TextView txtholdings;
    TextView txtAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_bonds);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        auth = FirebaseAuth.getInstance();
        fuser = auth.getCurrentUser();
        txtprice = (TextInputEditText) findViewById(R.id.txtprice);
        txtquantity = (TextInputEditText) findViewById(R.id.txtquantity);
        buybondsbtn = (Button) findViewById(R.id.btnbuybonds);


        txtholdings = (TextView) findViewById(R.id.txtbondholdings);
        txtAmount = (TextView) findViewById(R.id.txtvirtualbalance);

        user = new User();

        getUserInfo();
        buybondsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtquantity.getText().toString().trim()) || TextUtils.isEmpty(txtprice.getText().toString().trim())){
                    Toast.makeText(BuyBondsActivity.this,"Enter price and quantity to purchase",Toast.LENGTH_SHORT).show();
                    return;
                }


                double consideration = (Integer.parseInt(txtprice.getText().toString().trim()) / 100.00) * Integer.parseInt(txtquantity.getText().toString().trim());
                checkuserfunds(Integer.parseInt(txtquantity.getText().toString().trim()),Integer.parseInt(txtprice.getText().toString().trim()),consideration);
                double value = consideration;
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bondnumber = extras.getString("Bondnumber");
            rate = extras.getString("Rate");
            duration = extras.getString("Duration");
            //Toast.makeText(this,"price is" + openingprice,Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(BuyBondsActivity.this,"empty values",Toast.LENGTH_SHORT).show();
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Buy bond #" + bondnumber);
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
            ///TODO for compatibility
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

    public void buybonds(int units,int price,double consideration){
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("BondsTransaction");
        String id = reference.push().getKey();
        BondTransaction bondTransaction = new BondTransaction(id,fuser.getUid(),bondnumber,"Successfully",consideration,"Purchase",units,price,getCurrentTimeStamp());
        reference.child(id).setValue(bondTransaction).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Bond was purchased successfully.",Toast.LENGTH_LONG).show();
                updateUserInfo(units,consideration);
            }
        });
    }

    public void getUserInfo(){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                NumberFormat formatter = new DecimalFormat("#,###");
                txtholdings.setText(String.valueOf(user.getBonds()));
                txtAmount.setText(formatter.format(user.getVirtualmoney()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateUserInfo(int bonds,Double consideration){

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int _b = dataSnapshot.child("bonds").getValue(Integer.class);
                double _virtualmoney = dataSnapshot.child("virtualmoney").getValue(Integer.class);
                Map<String, Object> map = new HashMap<>();
                map.put("bonds", _b + bonds);
                map.put("virtualmoney", _virtualmoney - consideration);
                reference.updateChildren(map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void checkuserfunds(int units,int price,double consideration){

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                double _virtualmoney = dataSnapshot.child("virtualmoney").getValue(Integer.class);

                if(_virtualmoney < consideration) {
                    Toast.makeText(BuyBondsActivity.this, "You have less amount to buy this bond", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    buybonds(units,price,consideration);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
