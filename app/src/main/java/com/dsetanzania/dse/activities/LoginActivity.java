package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.helperClasses.Sms;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // force full screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();


        firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser !=null){
            Intent loginintent = new Intent(LoginActivity.this, HomeActivity.class);
            LoginActivity.this.startActivity(loginintent);
            finish();
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
                                        SignInLoader.setVisibility(View.INVISIBLE);
                                        Intent verifycodeintent = new Intent(LoginActivity.this, HomeActivity.class);
                                        LoginActivity.this.startActivity(verifycodeintent);
                                        finish();
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
}
