package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class PhoneVerificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String mVerificationId;
    TextInputEditText verificationtxtxfield;
    String userId;
    String firstname;
    String lastname;
    String tradername;
    String email;
    String yearOfStudy;
    String university;
    String coursename;
    String passoword;
    String phoneNumber;
    Button verifybtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_phone_verification);
        progressBar = (ProgressBar)  findViewById(R.id.progressLoader);
        verificationtxtxfield = (TextInputEditText) findViewById(R.id.vericationtxt);
        verifybtn = (Button) findViewById(R.id.btnVerifyCode);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            firstname = extras.getString("firstName");
            lastname = extras.getString("lastName");
            email = extras.getString("email");
            tradername = extras.getString("tradername");
            university= extras.getString("university");
            coursename = extras.getString("coursename");
            yearOfStudy = extras.getString("yearOfStudy");
            passoword = extras.getString("password");
            phoneNumber = extras.getString("phonenumber");
            //The key argument here must match that used in the other activity
        }

        sendVerificationCode(phoneNumber);

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyVerificationCode(verificationtxtxfield.getText().toString().trim());
                registerUser();
                Intent verifycodeintent = new Intent(PhoneVerificationActivity.this, LoginActivity.class);
                PhoneVerificationActivity.this.startActivity(verifycodeintent);
                Toast.makeText(PhoneVerificationActivity.this,"You are successfully registerd",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signInwithEmailAndPassword() {
        mAuth.signInWithEmailAndPassword(email,passoword)
                .addOnCompleteListener(PhoneVerificationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                       /*     SharedPreferences settings = getSharedPreferences("prefs", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("firstRun", false);
                            editor.apply();
                            finish();*/

                        } else {

                            //verification unsuccessful.. display an error message
                            Toast.makeText(PhoneVerificationActivity.this,"Somthing is wrong, we will fix it soon...",Toast.LENGTH_SHORT).show();
                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                                //verificationtxtxfield.setError(message);
                            }
                            return;
                        }
                    }
                });
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+255" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                verificationtxtxfield.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
            else{
                return;
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(PhoneVerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    public  void registerUser(){

        mAuth.createUserWithEmailAndPassword(email,passoword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userId = mAuth.getUid();
                            DatabaseReference reference;
                            reference = FirebaseDatabase.getInstance().getReference("Users");
                            User _usermodel = new User(userId,firstname,lastname,tradername,email,yearOfStudy,university,coursename,"0"+ phoneNumber,"1000000");
                            reference.child(userId).setValue(_usermodel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    signInwithEmailAndPassword();
                                }
                            });
                        }
                        else{
                            Toast.makeText(PhoneVerificationActivity.this,"Cant't register with this email.",Toast.LENGTH_SHORT).show();
                            return;

                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        return;
    }

}
