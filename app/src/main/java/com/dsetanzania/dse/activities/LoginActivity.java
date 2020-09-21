package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.helperClasses.Sms;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.interfaces.InternetcheckInterface;
import com.dsetanzania.dse.models.AuthResponseModel;
import com.dsetanzania.dse.models.BaseResponseModel;
import com.dsetanzania.dse.models.UserModel;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final  String sharedPrefrences = "sharedpref";
    public static final  String emailAddress = "Email";
    public static final  String userId = "userid";
    public  static final String token = "token";
    public  static final String firebasetoken = "firebasetoken";
    Button createNewAccounttxt;
    Button loginBtn;
    View parentLayout;
    EditText emailtxt;
    TextInputEditText passswordtxt;
    ProgressBar SignInLoader;
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
        parentLayout = findViewById(android.R.id.content);
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

                checkInternet task = new checkInternet(getApplicationContext(), new InternetcheckInterface() {
                    @Override
                    public void checkMethod(String result) {

                        if(result == "Access"){
                            if(TextUtils.isEmpty(emailtxt.getText().toString()) || TextUtils.isEmpty(passswordtxt.getText().toString())){
                                new AlertDialog.Builder(LoginActivity.this,R.style.Mydialogtheme)
                                        .setTitle("Problem!")
                                        .setMessage("All fields are required")
                                        .setPositiveButton("Ok",null).show();
                            }

                            else{
                                try {

                                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            String token = task.getResult().getToken();
                                            loginBtn.setEnabled(false);
                                            SignInLoader.setVisibility(View.VISIBLE);
                                            Call<AuthResponseModel> call = RetrofitClient
                                                    .getInstance().getApi().login(emailtxt.getText().toString().trim(),passswordtxt.getText().toString().trim(),token);
                                            call.enqueue(new Callback<AuthResponseModel>() {
                                                @Override
                                                public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {
                                                    if(response.isSuccessful()){
                                                        AuthResponseModel loginresponse = response.body();
                                                        if (loginresponse.isSuccess()){
                                                            //checkRole("Admin");
                                                            saveToLocalDb(loginresponse.getUser().getId(),loginresponse.getUser().getStock(),loginresponse.getUser().getBonds(),
                                                                    loginresponse.getUser().getFirstname(),loginresponse.getUser().getLastname(),
                                                                    loginresponse.getUser().getTradername(),loginresponse.getUser().getEmail(),
                                                                    loginresponse.getUser().getYearOfStudy(),
                                                                    loginresponse.getUser().getUniversity(),loginresponse.getUser().getCoursename(),
                                                                    loginresponse.getUser().getPhonenumber(),loginresponse.getUser().getRole(),
                                                                    loginresponse.getUser().getVirtualmoney(),loginresponse.getUser().getPortfolioValue(),loginresponse.getUser().getGender());

                                                            saveLoginData(loginresponse.getUser().getToken(),loginresponse.getUser().getId(),token);
                                                            //Log.i("Valuuuuuuues", token + "\n" + loginresponse.getUser().getToken() + "\n" + loginresponse.getUser().getId());
                                                            SignInLoader.setVisibility(View.INVISIBLE);
                                                            loginBtn.setEnabled(true);

                                                            Intent NextActivity = new Intent(LoginActivity.this, HomeActivity.class);
                                                            LoginActivity.this.startActivity(NextActivity);
                                                            finish();
                                                            Toast.makeText(LoginActivity.this,loginresponse.getMessage(),Toast.LENGTH_LONG).show();
                                                        }
                                                        else{
                                                            SignInLoader.setVisibility(View.INVISIBLE);
                                                            loginBtn.setEnabled(true);
                                                            Toast.makeText(LoginActivity.this,loginresponse.getMessage(),Toast.LENGTH_LONG).show();
                                                        }
                                                    }else {
                                                        loginBtn.setEnabled(true);
                                                        Toast.makeText(LoginActivity.this,"Server error",Toast.LENGTH_LONG).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<AuthResponseModel> call, Throwable t) {

                                                }
                                            });
                                        }
                                    });

                                } catch (Exception e) {
                                    loginBtn.setEnabled(true);
                                    Toast.makeText(LoginActivity.this,"Exception thrown",Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        else if(result == "NoAccess"){
                            showSnackbar("No internet access");
                        }
                        else{

                        }
                    }
                });

                task.execute();
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

    public void saveLoginData(String accsesstoken, String id, String _firebasetoken){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(emailAddress, emailtxt.getText().toString().trim());
        editor.putString(token, accsesstoken);
        editor.putString(firebasetoken, _firebasetoken);
        editor.putString(userId,id);
        editor.apply();
    }

    public  void loadData(){
        userEmail = sharedPreferences.getString(emailAddress,"");
        emailtxt.setText(userEmail);
    }

    public  void checkRole(String role){

        if(role.equals("Admin")){
                    SignInLoader.setVisibility(View.INVISIBLE);
                    Intent verifycodeintent = new Intent(LoginActivity.this, AdminPanelActivity.class);
                    LoginActivity.this.startActivity(verifycodeintent);
                    finish();
                    return;
                }
                else {

                    SignInLoader.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    LoginActivity.this.startActivity(intent);
                    finish();
                }
    }


    public void saveToLocalDb(String id, int stock, int bond, String firstname, String lastname, String tradername, String email, String yearOfStrudy, String university, String coursename, String phonenumber, String role, Double virtualmoney,Integer portfoliovalue, String gender){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveUserTolocalDatabase(id,stock,bond,firstname,lastname,tradername,email,yearOfStrudy,university,coursename,phonenumber,role,virtualmoney,gender, DbContract.SYNC_STATUS_FAILED,portfoliovalue,database);
        dbHelper.close();
    }

    public void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED);
        View snackView = snackbar.getView();
        TextView textView = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

}
