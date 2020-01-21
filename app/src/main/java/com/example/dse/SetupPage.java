package com.example.dse;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SetupPage extends AppCompatActivity {

    ProgressBar pgrsb;
    LinearLayout linearLayout;
    private String mVerificationId;
    TextInputEditText phonetxt;
    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText verificationtxt;
    LinearLayout verificationLayout;
    private String phone;
    private FirebaseAuth mAuth;
    boolean isVerified;
    String codeFromtxtField;
    String subphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_page);

        isVerified =false;
        mAuth = FirebaseAuth.getInstance();
        linearLayout = (LinearLayout) findViewById(R.id.fields_layout);
        pgrsb = (ProgressBar) findViewById(R.id.SignUpLoader);
        phonetxt = (TextInputEditText) findViewById(R.id.phoneNumber);
        firstname = (TextInputEditText) findViewById(R.id.firstname);
        lastname = (TextInputEditText) findViewById(R.id.lastname);
        verificationLayout = (LinearLayout) findViewById(R.id.verificationLayout);
        verificationtxt =(TextInputEditText) findViewById(R.id.verificationtxtfield);
        final FloatingActionButton SignUp = (FloatingActionButton) findViewById(R.id.SignUp);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                if(isVerified == false){
                    subphone = phonetxt.getText().toString().trim();
                    subphone = subphone.substring(1);
                    sendVerificationCode(subphone);
                    linearLayout.setVisibility(View.INVISIBLE);
                    pgrsb.setVisibility(View.VISIBLE);
                    //Toast.makeText(SetupPage.this,"255"+subphone,Toast.LENGTH_LONG).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isVerified = true;
                            pgrsb.setVisibility(View.INVISIBLE);
                            verificationLayout.setVisibility(View.VISIBLE);
                        }
                    },1000);
                }
                else{
                    codeFromtxtField = verificationtxt.getText().toString().trim();
                    if(codeFromtxtField.isEmpty() || codeFromtxtField.length() < 6){
                        verificationtxt.setError("Enter code..");
                        verificationtxt.requestFocus();
                        return;
                    }
                    verificationLayout.setVisibility(View.INVISIBLE);
                    pgrsb.setVisibility(View.VISIBLE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pgrsb.setVisibility(View.INVISIBLE);
                            verifyVerificationCode(codeFromtxtField);
                        }
                    },1000);
                }
            }
        });

    }
    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(SetupPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            registerUser("+255" + subphone);
                            SharedPreferences settings = getSharedPreferences("prefs", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("firstRun", false);
                            editor.apply();
                            finish();


                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
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
                verificationtxt.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(SetupPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    public  void  registerUser(String phone){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        model _model = new model(firstname.getText().toString().trim(),lastname.getText().toString().trim(),phone);
        String id = mAuth.getUid();
        reference.child(id).setValue(_model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(SetupPage.this,"Added to database.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}

