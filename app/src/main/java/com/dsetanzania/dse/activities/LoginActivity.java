package com.dsetanzania.dse.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.helperClasses.Sms;
import com.dsetanzania.dse.models.AuthResponseModel;
import com.dsetanzania.dse.models.UserModel;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final  String sharedPrefrences = "sharedpref";
    public static final  String emailAddress = "Email";
    public  static final String token = "token";
    Button createNewAccounttxt;
    Button loginBtn;
    EditText emailtxt;
    TextInputEditText passswordtxt;
    private FirebaseAuth mAuth;
    ProgressBar SignInLoader;
    private FirebaseUser firebaseUser;
    Sms sms;
    private String userEmail;
    private List<UserModel> user;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        String _token = sharedPreferences.getString(token, "");
        if(!_token.isEmpty()){
            Intent NextActivity = new Intent(LoginActivity.this, HomeActivity.class);
            LoginActivity.this.startActivity(NextActivity);
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

                    Call<AuthResponseModel> call = RetrofitClient
                            .getInstance().getApi().login(emailtxt.getText().toString().trim(),passswordtxt.getText().toString().trim());
                    call.enqueue(new Callback<AuthResponseModel>() {
                        @Override
                        public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {
                            AuthResponseModel loginresponse = response.body();
                            if (loginresponse.isSuccess()){
                                //checkRole("Admin");
                                saveToLocalDb(loginresponse.getUser().getStock(),loginresponse.getUser().getBonds(),
                                        loginresponse.getUser().getFirstname(),loginresponse.getUser().getLastname(),
                                        loginresponse.getUser().getTradername(),loginresponse.getUser().getEmail(),
                                        loginresponse.getUser().getYearOfStudy(),
                                        loginresponse.getUser().getUniversity(),loginresponse.getUser().getCoursename(),
                                        loginresponse.getUser().getPhonenumber(),loginresponse.getUser().getRole(),
                                        loginresponse.getUser().getVirtualmoney(),loginresponse.getUser().getGender());
                                saveLoginData(loginresponse.getUser().getToken());
                                Intent NextActivity = new Intent(LoginActivity.this, HomeActivity.class);
                                LoginActivity.this.startActivity(NextActivity);
                                finish();
                                Toast.makeText(LoginActivity.this,loginresponse.getMessage(),Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(LoginActivity.this,loginresponse.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthResponseModel> call, Throwable t) {

                        }
                    });
                    //SignIn();
                  /*  SignInLoader.setVisibility(View.VISIBLE);
                    SignInLoader.setVisibility(View.INVISIBLE);
                    new AlertDialog.Builder(LoginActivity.this,R.style.Mydialogtheme)
                            .setTitle("Problem!")
                            .setMessage("Authentication failed")
                            .setPositiveButton("Ok",null).show();
                    checkRole();*/
                    //emailtxt.getText().toString().trim(),passswordtxt.getText().toString().trim()
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void saveLoginData(String accsesstoken){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(emailAddress, emailtxt.getText().toString().trim());
        editor.putString(token, accsesstoken);
        editor.apply();
    }

    public  void loadData(){
        userEmail = sharedPreferences.getString(emailAddress,"");
        emailtxt.setText(userEmail);
    }

    public  void checkRole(String role){
        final FirebaseUser fuser = mAuth.getCurrentUser();

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


    public void saveToLocalDb(int stock,int bond,String firstname,String lastname,String tradername,String email,String yearOfStrudy,String university,String coursename,String phonenumber,String role,Double virtualmoney,String gender){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveTolocalDatabase(stock,bond,firstname,lastname,tradername,email,yearOfStrudy,university,coursename,phonenumber,role,virtualmoney,gender, DbContract.SYNC_STATUS_FAILED,database);
        dbHelper.close();
    }

}
