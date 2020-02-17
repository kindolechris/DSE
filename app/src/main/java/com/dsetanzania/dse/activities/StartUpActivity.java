package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsetanzania.dse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartUpActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView poweredtxt, companynametxt;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_up);

        image = findViewById(R.id.imageView);
        poweredtxt = findViewById(R.id.textView);
        companynametxt = findViewById(R.id.textView2);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Set animation to elements
        image.setAnimation(topAnim);
        poweredtxt.setAnimation(bottomAnim);
        companynametxt.setAnimation(bottomAnim);


        mAuth = FirebaseAuth.getInstance();


        firebaseUser = mAuth.getCurrentUser();

        firebaseUser = mAuth.getCurrentUser();



        //Calling New Activity after SPLASH_SCREEN seconds 1s = 1000
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          if(firebaseUser !=null){

                                              checkRole();
                                          }
                                          else{
                                              Intent verifycodeintent = new Intent(StartUpActivity.this, LoginActivity.class);
                                              StartUpActivity.this.startActivity(verifycodeintent);
                                              finish();
                                          }


                                      }
                                  },
                SPLASH_SCREEN);

    }

    public  void checkRole(){
        final FirebaseUser fuser = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).child("role");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String role = dataSnapshot.getValue(String.class);
                if(role.equals("Admin")){
                    Intent verifycodeintent = new Intent(StartUpActivity.this, SimulatedMarketListActivity.class);
                    StartUpActivity.this.startActivity(verifycodeintent);
                    finish();
                }
                else {

                    Intent verifycodeintent = new Intent(StartUpActivity.this, HomeActivity.class);
                    StartUpActivity.this.startActivity(verifycodeintent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        SPLASH_SCREEN = 2500;
    }
}
