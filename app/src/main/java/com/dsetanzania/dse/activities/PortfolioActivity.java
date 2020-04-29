package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PortfolioActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtusername;
    TextView txtbonds;
    TextView txtshares;
    TextView txtuniversity;
    //TextView txtshares;
    DatabaseReference reference;
    UserModel user;
    FirebaseUser fuser;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        //user = new User();
        txtusername  = (TextView) findViewById(R.id.txtusername);
        txtbonds  = (TextView) findViewById(R.id.bondtxt);
        txtshares  = (TextView) findViewById(R.id.sharestxt);
        txtuniversity  = (TextView) findViewById(R.id.txtuniversity);

        txtusername.setSelected(true);
        txtuniversity.setSelected(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(0);

        auth = FirebaseAuth.getInstance();
        fuser = auth.getCurrentUser();
        getUserInfo();

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

    public void getUserInfo(){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(UserModel.class);
                txtusername.setText(user.getFirstname() + " " + user.getLastname());
                txtshares.setText(String.valueOf(user.getStock()));
                txtbonds.setText(String.valueOf(user.getBonds()));
                txtuniversity.setText(String.valueOf(user.getUniversity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
