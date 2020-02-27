package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.helperClasses.Sms;
import com.dsetanzania.dse.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static final  String sharedPrefrences = "sharedpref";
    public static final  String emailAddress = "Email";

    Button createNewAccounttxt;
    Button loginBtn;
    EditText emailtxt;
    TextInputEditText passswordtxt;
    private FirebaseAuth mAuth;
    ProgressBar SignInLoader;
    private FirebaseUser firebaseUser;
    Sms sms;
    private String userEmail;
    private List<User> user;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // force full screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        mAuth = FirebaseAuth.getInstance();


        firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser !=null){

            checkRole();
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        createNewAccounttxt = (Button) findViewById(R.id.registerNewAccountTxt);
        emailtxt = (EditText) findViewById(R.id.txtemailAddress);
        passswordtxt = (TextInputEditText) findViewById(R.id.txtpasswordlogin);
        SignInLoader = (ProgressBar) findViewById(R.id.SignInLoader);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loadData();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

      /*          Thread nthread=new Thread(new Runnable() {
                    @Override
                    public void run() {

                        sms = new Sms();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            try {
                                sms.sendMessage();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                nthread.start();*/

                if(TextUtils.isEmpty(emailtxt.getText().toString()) || TextUtils.isEmpty(passswordtxt.getText().toString())){
                    new AlertDialog.Builder(LoginActivity.this,R.style.Mydialogtheme)
                            .setTitle("Problem!")
                            .setMessage("All fields are required")
                            .setPositiveButton("Ok",null).show();
                }

                else{
                    SignInLoader.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(emailtxt.getText().toString().trim(),passswordtxt.getText().toString().trim())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        checkRole();
                                    } else {
                                        SignInLoader.setVisibility(View.INVISIBLE);
                                        new AlertDialog.Builder(LoginActivity.this,R.style.Mydialogtheme)
                                                .setTitle("Problem!")
                                                .setMessage("Authentication failed")
                                                .setPositiveButton("Ok",null).show();
                                    }
                                }
                            });
                }
                
            }
        });

        createNewAccounttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        saveLoginData();
    }

    public void saveLoginData(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(emailAddress, emailtxt.getText().toString().trim());
        editor.apply();
    }

    public  void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        userEmail = sharedPreferences.getString(emailAddress,"");
        emailtxt.setText(userEmail);
    }

    public  void checkRole(){
        final FirebaseUser fuser = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid()).child("role");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String role = dataSnapshot.getValue(String.class);
                if(role.equals("Admin")){
                    SignInLoader.setVisibility(View.INVISIBLE);
                    Intent verifycodeintent = new Intent(LoginActivity.this, SimulatedMarketListActivity.class);
                    LoginActivity.this.startActivity(verifycodeintent);
                    finish();
                    return;
                }
                else {

                    SignInLoader.setVisibility(View.INVISIBLE);
                    Intent verifycodeintent = new Intent(LoginActivity.this, HomeActivity.class);
                    LoginActivity.this.startActivity(verifycodeintent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
