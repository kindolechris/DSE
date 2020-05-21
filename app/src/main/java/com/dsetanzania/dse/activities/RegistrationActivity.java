package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.interfaces.InternetcheckInterface;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.models.AuthResponseModel;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    Button registerbtn;
    Button btnuniversity;
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
    ProgressBar progressBar;
    View parentLayout;
    RadioGroup radiogroupGender;
    RadioButton radiobtn;
    RadioButton maleRadio;
    RadioButton femaleRadio;
    Toolbar toolbar;
    RelativeLayout registrationLayout;
    public static final  String sharedPrefrences = "sharedpref";
    public static final  String emailAddress = "Email";
    public static final  String userId = "userid";
    public  static final String token = "token";
    public  static final String firebasetoken = "firebasetoken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        parentLayout = findViewById(android.R.id.content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        registrationLayout = (RelativeLayout) findViewById(R.id.registrationLayout);
        setSupportActionBar(toolbar);
        toolBarElevation(7);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Registration");
        }
        btnuniversity = (Button) findViewById(R.id.btnuniversity);
        progressBar = (ProgressBar)  findViewById(R.id.RegistrationLoaderketLoader2);

        radiogroupGender = (RadioGroup) findViewById(R.id.radioGender);
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
        maleRadio = (RadioButton) findViewById(R.id.radioButton1);
        femaleRadio = (RadioButton) findViewById(R.id.radioButton2);
        btnuniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RegistrationActivity.this, SearchUniversityActivity.class);
                RegistrationActivity.this.startActivity(myIntent);
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(firstname.getText().toString().trim()) && TextUtils.isEmpty(lastname.getText().toString().trim()) && TextUtils.isEmpty(email.getText().toString().trim()) && TextUtils.isEmpty(tradername.getText().toString().trim()) && TextUtils.isEmpty(coursename.getText().toString().trim()) && TextUtils.isEmpty(yearOfStudy.getText().toString().trim()) && TextUtils.isEmpty(phoneNumber.getText().toString().trim()) && TextUtils.isEmpty(passoword.getText().toString().trim()) && TextUtils.isEmpty(confirmpassword.getText().toString().trim()) && btnuniversity.getText().equals("University")){
                    new AlertDialog.Builder(RegistrationActivity.this,R.style.Mydialogtheme)
                            .setTitle("Problem!")
                            .setMessage("All fields must be entered")
                            .setPositiveButton("Ok",null).show();
                    return;
                }

                if(!maleRadio.isChecked() && !femaleRadio.isChecked()){
                    new AlertDialog.Builder(RegistrationActivity.this,R.style.Mydialogtheme)
                            .setTitle("Problem!")
                            .setMessage("Please select gender")
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

                if(btnuniversity.getText().equals("University")){
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

                registrationLayout.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                checkInternet task = new checkInternet(getApplicationContext(), new InternetcheckInterface() {
                    @Override
                    public void checkMethod(String result) {

                        if(result == "Access"){
                            //checkEmailFirstIfExist();
                            //API call

                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            if (!task.isSuccessful()) {
                                                Log.w("MESSAGE", "getInstanceId failed", task.getException());
                                                return;
                                            }

                                            // Get new Instance ID token
                                            String token = task.getResult().getToken();
                                            Log.d("MESSAGE", token);
                                            try {
                                                registerUser(token);
                                            } catch (Exception e) {
                                                Toast.makeText(RegistrationActivity.this,"System error",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else if(result == "NoAccess"){
                            Snackbar snackbar = Snackbar
                                    .make(parentLayout, "No internet access", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progressBar.setVisibility(View.INVISIBLE);
                            registrationLayout.setVisibility(View.VISIBLE);
                        }
                        else{
                            Snackbar snackbar = Snackbar
                                    .make(parentLayout, "Service unavailable", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progressBar.setVisibility(View.INVISIBLE);
                            registrationLayout.setVisibility(View.VISIBLE);
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


    public  void checkbtn(View view){
        int radioId = radiogroupGender.getCheckedRadioButtonId();
        radiobtn = findViewById(radioId);

        //Toast.makeText(RegistrationActivity.this,"Selected : " + radiobtn.getText().toString(),Toast.LENGTH_SHORT).show();
    }


    public void registerUser(String FirebaseTOken){
        Call<AuthResponseModel> call = RetrofitClient
                .getInstance().getApi().register(firstname.getText().toString().trim(),lastname.getText().toString().trim(),radiobtn.getText().toString(),tradername.getText().toString().trim(),email.getText().toString().trim(),yearOfStudy.getText().toString().trim(),btnuniversity.getText().toString().trim(),coursename.getText().toString().trim(),passoword.getText().toString().trim(),confirmpassword.getText().toString().trim(),phoneNumber.getText().toString().trim(),FirebaseTOken);
        call.enqueue(new Callback<AuthResponseModel>() {
            @Override
            public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {
                AuthResponseModel regsterResponse = response.body();

                if (regsterResponse.isSuccess()){
                    if(regsterResponse.getMessage().equals("User arleady registered.")){
                        Toast.makeText(RegistrationActivity.this,"Email already registered",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        registrationLayout.setVisibility(View.VISIBLE);
                        return;
                    }
                    Toast.makeText(RegistrationActivity.this, regsterResponse.getMessage(),Toast.LENGTH_LONG).show();
                    saveLoginData(regsterResponse.getUser().getToken(),regsterResponse.getUser().getId(),regsterResponse.getUser().getFirebaseToken(),regsterResponse.getUser().getEmail());
                    saveToLocalDb(regsterResponse.getUser().getId(),regsterResponse.getUser().getStock(),regsterResponse.getUser().getBonds(),regsterResponse.getUser().getFirstname(),regsterResponse.getUser().getLastname(),regsterResponse.getUser().getTradername(),regsterResponse.getUser().getEmail(),regsterResponse.getUser().getYearOfStudy(),regsterResponse.getUser().getUniversity(),regsterResponse.getUser().getCoursename(),regsterResponse.getUser().getPhonenumber(),regsterResponse.getUser().getRole(),regsterResponse.getUser().getVirtualmoney(),regsterResponse.getUser().getGender());
                    progressBar.setVisibility(View.INVISIBLE);
                    //registrationLayout.setVisibility(View.VISIBLE);

                    Intent NextActivity = new Intent(RegistrationActivity.this, HomeActivity.class);
                    RegistrationActivity.this.startActivity(NextActivity);
                    Toast.makeText(RegistrationActivity.this,"You are logged in.",Toast.LENGTH_LONG).show();
                    finish();

                }
                else{
                    Toast.makeText(RegistrationActivity.this, regsterResponse.getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<AuthResponseModel> call, Throwable t) {

            }
        });
    }

    public void saveLoginData(String accsesstoken,int id,String _firebasetoken,String email){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(emailAddress,email);
        editor.putString(token, accsesstoken);
        editor.putString(firebasetoken, _firebasetoken);
        editor.putInt(userId,id);
        editor.apply();
    }

    public void saveToLocalDb(int id,int stock,int bond,String firstname,String lastname,String tradername,String email,String yearOfStrudy,String university,String coursename,String phonenumber,String role,Double virtualmoney,String gender){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveUserTolocalDatabase(id,stock,bond,firstname,lastname,tradername,email,yearOfStrudy,university,coursename,phonenumber,role,virtualmoney,gender, DbContract.SYNC_STATUS_FAILED,database);
        dbHelper.close();
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }

    @Override
    public void onResume() {
        SharedPreferences sp = getSharedPreferences("universitypref", 0);
        String  universityname= sp.getString("universityname", "University");
        String newstr = capitalize(universityname.toLowerCase());
        btnuniversity.setText(newstr);
        super.onResume();
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        clearFields();
    }



    @Override
    public void onPause() {
        //clearFields();
        super.onPause();
    }


    @Override
    protected void onStop() {
        //clearFields();
        super.onStop();
    }

    @Override
    protected void onStart() {
        //clearFields();
        super.onStart();
    }



    public void clearFields(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("universitypref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("universityname");
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }


}
