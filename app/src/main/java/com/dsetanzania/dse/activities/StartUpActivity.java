package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsetanzania.dse.R;
public class StartUpActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    ImageView sponsor;
    TextView poweredtxt, companynametxt;
    private SharedPreferences sharedPreferences;
    public static final  String sharedPrefrences = "sharedpref";
    public  static final String token = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_up);


        image = findViewById(R.id.imageView);
        sponsor = findViewById(R.id.sponsor);
        poweredtxt = findViewById(R.id.textView);

        //AnimationsS
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Set animation to elements
        image.setAnimation(topAnim);
        poweredtxt.setAnimation(bottomAnim);
        sponsor.setAnimation(bottomAnim);

        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        String _token = sharedPreferences.getString(token, "");
        //Calling New Activity after SPLASH_SCREEN seconds 1s = 1000
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          String role = "Student";
                                          if(!_token.isEmpty()){
                                            checkRole(role);
                                          }
                                          else {
                                              Intent NextActivity = new Intent(StartUpActivity.this, LoginActivity.class);
                                              StartUpActivity.this.startActivity(NextActivity);
                                              finish();
                                          }
                                      }
                                  },
                SPLASH_SCREEN);

    }

    public  void checkRole(String role){

                if(role.equals("Admin")){
                    Intent verifycodeintent = new Intent(StartUpActivity.this, AdminPanelActivity.class);
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
    public void onResume(){
        super.onResume();
        SPLASH_SCREEN = 2500;
    }
}
