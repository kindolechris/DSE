package com.dsetanzania.dse.helperClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.dsetanzania.dse.activities.HomeActivity;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.models.UserDataResponseModel;
import com.dsetanzania.dse.models.UserModel;
import com.dsetanzania.dse.storage.SharedPrefManager;
import java.util.Timer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SychronizeLiveDataTimer {
    private int HOME_ACTIVITY = 100;
    private int BOARD_SHARE_ACTIVITY = 101;
    private int PERSON_SHARE_ACTIVITY = 102;
    private int BOARD_SHARE_BUY_OR_SALE_ACTIVITY = 103;
    private int PERSON_SHARE_BUY_OR_SALE_ACTIVITY = 104;
    private int PORTFOLIO_ACTIVITY = 105;
    private int TRANSACTION_ACTIVITY = 106;
    public UserDataResponseModel userdata;
    private Timer timer;
    public static final int TIME_INTERVAL = 500;
    Context context;
    UserModel user;
    SharedPrefManager sharedPrefManager;

    public SychronizeLiveDataTimer(Context context) {
        this.context = context;
        timer = new Timer();
        user = SharedPrefManager.getInstance(context).getUser();
    }

    public void startTimer(int which_activity) {

        Log.d("Constants", "Timer Started");
        timer.scheduleAtFixedRate(new java.util.TimerTask() {

            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                switch (which_activity){
                    case 100:
                        syncHomeActivity();
                        Log.d("Fetching data mesage", "Syncing in Home activity..");
                        break;
                    case 101:
                        break;
                    case 102:
                        break;
                    case 103:
                        break;
                    case 104:
                        break;
                    case 105:
                        break;
                    case 106:
                        break;
                    default:
                        Log.d("Fetching data mesage", "No fetching data for this activity");
                }
            }
        }, 0, TIME_INTERVAL);

    }

    public void stopTimer() {
        timer.cancel();
        Log.d("Fetching data mesage", "Syncing has stoped..");
    }

    public void syncHomeActivity(){

        Call<UserDataResponseModel> call = RetrofitClient
                .getInstance().getApi().fetchUserdata(user.getId(),"Bearer " +  user.getToken());

        call.enqueue(new Callback<UserDataResponseModel>() {
            @Override
            public void onResponse(Call<UserDataResponseModel> call, Response<UserDataResponseModel> response) {
                userdata = response.body();
                if ( userdata != null){
                    sharedPrefManager = new SharedPrefManager(context);
                    sharedPrefManager.SavaUser(userdata.getUsers());
                    //txtvirtualshare.setText(formatter.format(userdate.getUsers().getVirtualmoney()));
                    //checkRole("Admin");
                    //Toast.makeText(HomeActivity.this,userdate.getUsers().getFirstname(),Toast.LENGTH_SHORT).show();
                    //Log.i("Check this ","workingggggggggggggggg");
                    Log.i("Virtual moneyyyyyy::: ",String.valueOf(user.getVirtualmoney()));
                }
                else{
                    //Toast.makeText(HomeActivity.this,"Nothing",Toast.LENGTH_LONG).show();
                    Log.i("Message::: ","No dataaa");
                }
            }

            @Override
            public void onFailure(Call<UserDataResponseModel> call, Throwable t) {

            }
        });
    }
}
