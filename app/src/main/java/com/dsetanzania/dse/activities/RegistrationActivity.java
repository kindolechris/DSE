package com.dsetanzania.dse.activities;

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

import com.dsetanzania.dse.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

         spinner = (SearchableSpinner) findViewById(R.id.spinner_search);
            spinner.setTitle("Select university");
            spinner.setPositiveButton("Close");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.university_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                    new MaterialAlertDialogBuilder(RegistrationActivity.this,R.style.Theme_MaterialComponents_DayNight_Dialog_Alert)
                            .setTitle("Problem!")
                            .setMessage("Please select university")
                            .setPositiveButton("Ok",null).show();
                    return;
                }
                if(! passoword.getText().toString().equals(confirmpassword.getText().toString())){
                    new MaterialAlertDialogBuilder(RegistrationActivity.this,R.style.Theme_MaterialComponents_DayNight_Dialog_Alert)
                            .setTitle("Problem!")
                            .setMessage("Password do not match")
                            .setPositiveButton("Ok",null).show();
                    return;
                }

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
                        .show();
                ;
            }
        });
    }

    public void checkInputs(){


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner.getItemAtPosition(position).toString().toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
