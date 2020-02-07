package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.helperClasses.InternetcheckInterface;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button registerbtn;
    SearchableSpinner spinner;
    AlertDialog _builder;
    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText tradername;
    TextInputEditText email;
    TextInputEditText yearOfStudy;
    TextInputEditText coursename;
    TextInputEditText passoword;
    TextInputEditText confirmpassword;
    TextInputEditText phoneNumber;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    View parentLayout;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        parentLayout = findViewById(android.R.id.content);

        mAuth = FirebaseAuth.getInstance();

         spinner = (SearchableSpinner) findViewById(R.id.spinner_search);
        progressBar = (ProgressBar)  findViewById(R.id.RegistrationLoaderketLoader2);
            spinner.setTitle("Select university");
            spinner.setPositiveButton("Close");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.university_list, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        firstname = (TextInputEditText) findViewById(R.id.txtfirstname);
        lastname = (TextInputEditText) findViewById(R.id.txtlastname);
        tradername = (TextInputEditText) findViewById(R.id.txttradername);
        email = (TextInputEditText) findViewById(R.id.txtemail);
        yearOfStudy = (TextInputEditText) findViewById(R.id.txtyearofstudy);
        coursename = (TextInputEditText) findViewById(R.id.txtcource);
        phoneNumber = (TextInputEditText) findViewById(R.id.txtphone);
        passoword = (TextInputEditText) findViewById(R.id.txtpassword);
        confirmpassword = (TextInputEditText) findViewById(R.id.txtconfirmpass);
        registerbtn = (Button) findViewById(R.id.btnregister);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(firstname.getText().toString().trim()) && TextUtils.isEmpty(lastname.getText().toString().trim()) && TextUtils.isEmpty(email.getText().toString().trim()) && TextUtils.isEmpty(tradername.getText().toString().trim()) && TextUtils.isEmpty(coursename.getText().toString().trim()) && TextUtils.isEmpty(yearOfStudy.getText().toString().trim()) && TextUtils.isEmpty(phoneNumber.getText().toString().trim()) && TextUtils.isEmpty(passoword.getText().toString().trim()) && TextUtils.isEmpty(confirmpassword.getText().toString().trim()) && spinner.getSelectedItem() == null){
                    new AlertDialog.Builder(RegistrationActivity.this,R.style.Mydialogtheme)
                            .setTitle("Problem!")
                            .setMessage("All fields must be entered")
                            .setPositiveButton("Ok",null).show();
                    return;
                }

                if(firstname.getText().toString().trim().isEmpty()){
                    firstname.setError("Required");
                    return;
                }
                if(lastname.getText().toString().trim().isEmpty()){
                    lastname.setError("Required");
                    return;
                } if(email.getText().toString().trim().isEmpty()){
                    email.setError("Required");
                    return;
                } if(tradername.getText().toString().trim().isEmpty()){
                    tradername.setError("Required");
                    return;
                } if(coursename.getText().toString().trim().isEmpty()){
                    coursename.setError("Required");
                    return;
                } if(yearOfStudy.getText().toString().trim().isEmpty()){
                    yearOfStudy.setError("Required");
                    return;
                }
                if(phoneNumber.getText().toString().trim().isEmpty()) {
                    phoneNumber.setError("Required");
                    return;
                }
                if(passoword.getText().toString().trim().isEmpty()) {
                    passoword.setError("Required");
                    return;
                }

                if(passoword.getText().toString().trim().length() < 6 ){
                    passoword.setError("Password must be atleast 6 character long");
                    return;
                }
                if(confirmpassword.getText().toString().trim().isEmpty()) {
                    confirmpassword.setError("Required");
                    return;
                }

                if(spinner.getSelectedItem() == null ){
                    new AlertDialog.Builder(RegistrationActivity.this,R.style.Mydialogtheme)
                            .setTitle("Alert!")
                            .setMessage("Please select university")
                            .setPositiveButton("Ok",null).show();
                    return;
                }
                if(! passoword.getText().toString().equals(confirmpassword.getText().toString())){
                    new AlertDialog.Builder(RegistrationActivity.this,R.style.Mydialogtheme)
                            .setTitle("Alert!")
                            .setMessage("The confirmed Password do not match")
                            .setPositiveButton("Ok",null).show();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                checkInternet task = new checkInternet(getApplicationContext(), new InternetcheckInterface() {
                    @Override
                    public void checkMethod(String result) {

                        if(result == "Access"){
                            checkEmailFirstIfExist();
                            progressBar.setVisibility(View.INVISIBLE);
                 /*   Snackbar snackbar = Snackbar
                            .make(parentLayout, "Internet is on", Snackbar.LENGTH_LONG);
                    snackbar.show();*/
                        }
                        else if(result == "NoAccess"){
                            Snackbar snackbar = Snackbar
                                    .make(parentLayout, "No internet access", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else{
                            Snackbar snackbar = Snackbar
                                    .make(parentLayout, "Service unavailable", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                task.execute();


                //phone verification implementation
/*
                _builder = new MaterialAlertDialogBuilder(RegistrationActivity.this,R.style.Theme_MaterialComponents_DayNight_Dialog_Alert)
                        .setTitle("Phone number verification")
                        .setMessage("Will send a verification code to " + phoneNumber.getText().toString().trim())
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                _builder.dismiss();
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String subphone = phoneNumber.getText().toString().trim();
                                subphone = subphone.substring(1);
                                Intent verifycodeintent = new Intent(RegistrationActivity.this, PhoneVerificationActivity.class);
                                verifycodeintent.putExtra("firstName",firstname.getText().toString().trim() );
                                verifycodeintent.putExtra("lastName", lastname.getText().toString().trim());
                                verifycodeintent.putExtra("email", email.getText().toString().trim());
                                verifycodeintent.putExtra("tradername", tradername.getText().toString().trim());
                                verifycodeintent.putExtra("university", spinner.getSelectedItem().toString().trim());
                                verifycodeintent.putExtra("coursename", coursename.getText().toString().trim());
                                verifycodeintent.putExtra("yearOfStudy", yearOfStudy.getText().toString().trim());
                                verifycodeintent.putExtra("password", passoword.getText().toString().trim());
                                verifycodeintent.putExtra("phonenumber",subphone);
                                RegistrationActivity.this.startActivity(verifycodeintent);
                            }
                        })
                        .show();*/
            }
        });
    }


    public  void registerUser(){

        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),passoword.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userId = mAuth.getUid();
                            DatabaseReference reference;
                            reference = FirebaseDatabase.getInstance().getReference("Users");
                            User _usermodel = new User(userId,firstname.getText().toString().trim(),lastname.getText().toString().trim(),tradername.getText().toString().trim(),email.getText().toString().trim(),yearOfStudy.getText().toString().trim(),spinner.getSelectedItem().toString().trim(),coursename.getText().toString().trim(),phoneNumber.getText().toString().trim(),3000000,0,"Student");
                            reference.child(userId).setValue(_usermodel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    signInwithEmailAndPassword();
                                }
                            });
                        }
                        else{
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegistrationActivity.this,"There was a problem when registering",Toast.LENGTH_SHORT).show();
                            return;

                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner.getItemAtPosition(position).toString().toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void signInwithEmailAndPassword() {
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(),passoword.getText().toString().trim())
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);

                            _builder = new AlertDialog.Builder(RegistrationActivity.this,R.style.Mydialogtheme)
                                    .setTitle("Welcome")
                                    .setMessage("You are successfully registered")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            _builder.dismiss();
                                            Intent verifycodeintent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                            RegistrationActivity.this.startActivity(verifycodeintent);
                                            finish();
                                        }
                                    }).show();

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            //verification unsuccessful.. display an error message
                            //Toast.makeText(RegistrationActivity.this,"Somthing is wrong, we will fix it soon...",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

    public void checkEmailFirstIfExist(){
        mAuth.fetchSignInMethodsForEmail(email.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check = !task.getResult().getSignInMethods().isEmpty();

                if(!check){
                    registerUser();

                }else {
                    new AlertDialog.Builder(RegistrationActivity.this,R.style.Mydialogtheme)
                            .setTitle("Alert!")
                            .setMessage("Email already registered..")
                            .setPositiveButton("Ok",null).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                    //Toast.makeText(RegistrationActivity.this,"Email already registered..",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
