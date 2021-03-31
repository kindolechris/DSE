package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.dsetanzania.dse.adapters.LiveMarketAdapter;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.interfaces.InternetcheckInterface;
import com.dsetanzania.dse.models.UserDataResponseModel;
import com.dsetanzania.dse.models.live_data.LiveDataResponseModel;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout livemarketlinearlayout;
    LinearLayout portfoliolinearlayout;
    LinearLayout tellafriendlinearlayout;
    LinearLayout faqslinearlayout;
    LinearLayout newslinearlayout;
    LinearLayout transactionlinearlayout;
    LinearLayout leaderboardlayout;
    Button btncloseChoice;
    Button azania_link;
    LinearLayout aboutUstlinearlayout;
    TextView txttradername;
    Button avatornametxt;
    TextView txtstockbalance;
    TextView txtbondbalance;
    TextView txttrandingstats;
    TextView queuestext;
    ProgressBar prgs;
    TextView txtvirtualshare;
    private String userId;
    private String _token;
    RecyclerView livemarketpricerecyclerview;
    SwipeRefreshLayout swipeRefreshLayout;
    View parentLayout;
    TextView QueuesCountTxt;
    TextView margeetxt;
    Uri selectedImagURL;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    Dialog dialog,dialogChoice;
    LinearLayout closeLayout;
    Button equitybtn;
    Button bondbtn;
    NumberFormat formatter;
    DbHelper dbHelper;
    SQLiteDatabase database;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter  mIntentFilter;
    private SharedPreferences sharedPreferences;
    private static int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        margeetxt = (TextView) findViewById(R.id.marquutxtbottom);
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        formatter = new DecimalFormat("#,###");
        dialog = new Dialog(HomeActivity.this,R.style.Mydialogtheme);
        dialog.setContentView(R.layout.custom_pop_up_for_queued_orders);
        queuestext = (TextView) dialog.findViewById(R.id.quedtransactiontxt);
        closeLayout  =(LinearLayout) dialog.findViewById(R.id.layoutclose);
        txttradername = (TextView) findViewById(R.id.txttradername);
        txtstockbalance = (TextView) findViewById(R.id.stocklbalance);
        txtbondbalance = (TextView) findViewById(R.id.bondsbalance);
        txtvirtualshare = (TextView) findViewById(R.id.sharepricetxt);
        avatornametxt = (Button) findViewById(R.id.avatornametxt);
        azania_link = (Button) findViewById(R.id.azania_link);
        livemarketlinearlayout = (LinearLayout) findViewById(R.id.livemarketLayout);
        newslinearlayout = (LinearLayout) findViewById(R.id.newsLayout);
        leaderboardlayout = (LinearLayout) findViewById(R.id.leaderboardLayout);
        portfoliolinearlayout = (LinearLayout) findViewById(R.id.portfolioLayout);
        tellafriendlinearlayout = (LinearLayout) findViewById(R.id.tellafriendLayout);
        transactionlinearlayout = (LinearLayout) findViewById(R.id.transactionLayout);
        faqslinearlayout = (LinearLayout) findViewById(R.id.faqsLayout);
        livemarketpricerecyclerview = (RecyclerView) findViewById(R.id.listoflivemarket);
        aboutUstlinearlayout = (LinearLayout) findViewById(R.id.aboutusLayout);
        prgs = (ProgressBar) findViewById(R.id.livemarketLoader);
        txttrandingstats = (TextView) findViewById(R.id.txttrendingTradestats);
        txttrandingstats.setText("Retrieving Real-time data..");
        parentLayout = findViewById(android.R.id.content);
        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        final LinearLayout content = (LinearLayout) findViewById(R.id.content);

        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        userId = sharedPreferences.getString("userid", "");
        _token = sharedPreferences.getString("token", "");
        dialogChoice = new Dialog(HomeActivity.this,R.style.Mydialogtheme);
        dialogChoice.setContentView(R.layout.custom_pop_up_choice);
        equitybtn = (Button)dialogChoice.findViewById(R.id.btnEquity);
        bondbtn = (Button)dialogChoice.findViewById(R.id.btnBonds);
        //Toast.makeText(HomeActivity.this,_token,Toast.LENGTH_SHORT).show();
        equitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, EquityActivity.class);
                HomeActivity.this.startActivity(myIntent);
                dialogChoice.dismiss();
            }
        });

        btncloseChoice  =(Button) dialogChoice.findViewById(R.id.btnclose);

        btncloseChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChoice.dismiss();
            }
        });

        bondbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, BondsActivity.class);
                HomeActivity.this.startActivity(myIntent);
                dialogChoice.dismiss();
            }
        });

        //updatelocaUserInfoTodb();
        //saveToLocalDb();

        margeetxt.setSelected(true);

     /*   new Timer().scheduleAtFixedRate(new TimerTask() {
       @Override
            public void run() {
           runOnUiThread(new TimerTask() {
                  @Override
                    public void run() {
                      updatelocaldb();
                   }
              });
           }
        }, 0, 20);
*/

       checkInternet task = new checkInternet(getApplicationContext(), new InternetcheckInterface() {
            @Override
            public void checkMethod(String result) {

                if(result == "Access"){
                    try {
                        updatelocaUserInfoTodb();
                        getliveData();
                    } catch (Exception e) {
                        Toast.makeText(HomeActivity.this,"Live market feed error",Toast.LENGTH_SHORT).show();
                    }

                }
                else if(result == "NoAccess"){
                    try {
                        readFromLocalDb();
                        //readLivedatafromlocal();
                        showSnackbar("No internet access");
                    } catch (Exception e) {
                        Toast.makeText(HomeActivity.this,"System error (DB)",Toast.LENGTH_SHORT).show();
                    }
                }
                else{

                }
            }
        });

        task.execute();

        livemarketlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().show();
                drawerLayout.closeDrawers();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialogChoice.show();
                    }
                }, 300);
            }
        });


        azania_link.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://azaniabank.co.tz/retail-banking-products-details?product_name=ASPIRE%20Account&what_is_active_product=2"));
            startActivity(browserIntent);

        });

        portfoliolinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().show();
                Intent myIntent = new Intent(HomeActivity.this, PortfolioActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        faqslinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().show();
                Intent myIntent = new Intent(HomeActivity.this, LearningActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        aboutUstlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().show();
                Intent myIntent = new Intent(HomeActivity.this, AboutUsActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        transactionlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().show();
                Intent myIntent = new Intent(HomeActivity.this, TransactionActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        newslinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().show();
                Intent myIntent = new Intent(HomeActivity.this, NewsActivity.class);
                HomeActivity.this.startActivity(myIntent);

            }
        });

        leaderboardlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().show();
                Intent myIntent = new Intent(HomeActivity.this, LeaderBoardActivity.class);
                HomeActivity.this.startActivity(myIntent);

            }
        });


        tellafriendlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Try this app.");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share via"));
            }
        });

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolBarElevation(7);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_sort, null);
                toolbar.setNavigationIcon(d);
            }
        });


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updatelocaUserInfoTodb();
            }
        };

        mIntentFilter = new IntentFilter("OPEN_NEW_ACTIVITY");

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                content.setTranslationX(slideX);
            }
        };


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            updatelocaUserInfoTodb();
            margeetxt.setSelected(true);
        } catch (Exception e) {
            Toast.makeText(HomeActivity.this,"System error",Toast.LENGTH_SHORT).show();
        }
        registerReceiver(broadcastReceiver, mIntentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.avator_menu, menu);

        final MenuItem NotificationItem = menu.findItem(R.id.notification);
        final MenuItem UserAvator = menu.findItem(R.id.Userface);

        View notificationIcon = MenuItemCompat.getActionView(NotificationItem);
        View userAvatorIcon = MenuItemCompat.getActionView(UserAvator);

        QueuesCountTxt = (TextView) notificationIcon.findViewById(R.id.notification_badge);

        QueuesCountTxt.setText(String.valueOf(0));

        userAvatorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(UserAvator);
            }
        });

        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(NotificationItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signOut) {
            //sycnc.stopTimer();
            UserLogOut();
            Intent homeintent = new Intent(HomeActivity.this, LoginActivity.class);
            homeintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            HomeActivity.this.startActivity(homeintent);
            finish();
        }
        else if(id== R.id.Userface){
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, PICK_IMAGE_REQUEST);
        }
        else{
            queuestext.setText("No info currently !");
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            selectedImagURL = data.getData();
            imageView = (CircleImageView) findViewById(R.id.profile_image);
            imageView.setBorderWidth(3);
            imageView.setBorderColor(getResources().getColor(R.color.colorWhite));
            Picasso.get().load(selectedImagURL).into(imageView);
            //uploadFile();
        }
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }


    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    public void closeModal(View view){
        dialog.dismiss();
    }



    public void updateFieldsOnChange(String tradername,String stock,String bonds,Double virtualbalance,String firstname,String lastname){
        NumberFormat formatter = new DecimalFormat("#,###");
        char first = firstname.charAt(0);
        char last = lastname.charAt(0);
        txttradername.setText(tradername);
        txtstockbalance.setText(stock);
        avatornametxt.setText(String.valueOf(first) + String.valueOf(last));
        txtbondbalance.setText(bonds);
        txtvirtualshare.setText(formatter.format(virtualbalance));
    }

    public void readFromLocalDb(){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readUserFromLocalDatabase(database);
        String tradername="";
        String firstname="";
        String lastname="";
        double virtualmoney=0;
        int stock=0;
        int id=-1;
        int bonds=0;
        int sync_status;
        while(cursor.moveToNext()){
             tradername = cursor.getString(cursor.getColumnIndex(DbContract.tradername));
            firstname = cursor.getString(cursor.getColumnIndex(DbContract.firstname));
            lastname = cursor.getString(cursor.getColumnIndex(DbContract.lastname));
             virtualmoney = cursor.getDouble(cursor.getColumnIndex(DbContract.virtualmoney));
             stock = cursor.getInt(cursor.getColumnIndex(DbContract.stock));
             id = cursor.getInt(cursor.getColumnIndex("id"));
             bonds = cursor.getInt(cursor.getColumnIndex(DbContract.bonds));

        }
        updateFieldsOnChange(tradername,String.valueOf(stock),String.valueOf(bonds),virtualmoney,firstname,lastname);
        cursor.close();
        dbHelper.close();
    }


    public void updatelocaUserInfoTodb(){
        Call<JsonObject> call = RetrofitClient
                .getInstance().getApi().fetchUserdata("Bearer " +  _token);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful()){
                    JsonObject responseResult = response.body();
                    if (responseResult.get("success").getAsBoolean()) {
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(String.valueOf(responseResult));
                            JSONObject data = obj.getJSONObject("data");
                            dbHelper.updateUserLocalDatabase(String.valueOf(userId),data.getInt("shares"),data.getInt("bonds"), data.getString("firstname"),data.getString("lastname"),data.getString("tradername"),data.getString("email"),data.getString("yearOfStudy"),data.getString("university"),data.getString("coursename"),data.getString("phonenumber"),data.getString("role"),   data.getDouble("virtualmoney"),data.getString("gender"),DbContract.SYNC_STATUS_FAILED,data.getDouble("portfolio_value"),database);
                            readFromLocalDb();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        responseResult.getAsJsonObject("data");
                    }
                    else{
                        //Toast.makeText(LoginActivity.this,responseResult.get("message").getAsString(),Toast.LENGTH_LONG).show();
                    }
                }else {

                    //Toast.makeText(LoginActivity.this,"Server error",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void UserLogOut(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("sharedpref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("token");
        editor.remove("userid");
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(database,1,2);
        dbHelper.close();
        editor.commit();
        resetInstanceId();
    }

    public static void resetInstanceId() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                    FirebaseInstanceId.getInstance().getInstanceId();
                    Log.i("FirebaseInstance ", "InstanceId removed and regenerated.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void droptableLivedata(){
        String query = "DELETE FROM "+ DbContract.LIVE_MARKET__TABLE;
        database.execSQL(query);
    }

    /*public void readLivedatafromlocal(){
        livePrices = new OOUArrayOfSecurityLivePrice();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readLiveDataFromLocalDatabase(database);
        String Change="";
        String Board="";
        String Closee="";
        String Company="";
        String High="";
        String LastDealPrice="";
        String LastTradedQuantity="";
        String OpeningPrice="";
        String Low="";
        String MarketCap="";
        String Time="";
        String Volume="";
        int i=0;
        while(cursor.moveToNext()){
            Change = cursor.getString(cursor.getColumnIndex(DbContract.Change));
            Board = cursor.getString(cursor.getColumnIndex(DbContract.Board));
            Closee = cursor.getString(cursor.getColumnIndex(DbContract.Closee));
            Company = cursor.getString(cursor.getColumnIndex(DbContract.Company));
            High = cursor.getString(cursor.getColumnIndex(DbContract.High));
            LastDealPrice = cursor.getString(cursor.getColumnIndex(DbContract.LastDealPrice));
            LastTradedQuantity = cursor.getString(cursor.getColumnIndex(DbContract.LastTradedQuantity));
            OpeningPrice = cursor.getString(cursor.getColumnIndex(DbContract.openingPrice));
            Low = cursor.getString(cursor.getColumnIndex(DbContract.Low));
            MarketCap = cursor.getString(cursor.getColumnIndex(DbContract.MarketCap));
            Time = cursor.getString(cursor.getColumnIndex(DbContract.Time));
            Volume = cursor.getString(cursor.getColumnIndex(DbContract.Volume));

            OOUSecurityLivePrice securityLivePrice = new OOUSecurityLivePrice(Board, BigDecimal.valueOf(Double.valueOf(Change).longValue()),BigDecimal.valueOf(Double.valueOf(Closee).longValue()), Company,BigDecimal.valueOf(Double.valueOf(High).longValue()),BigDecimal.valueOf(Double.valueOf(LastDealPrice).longValue()),Double.valueOf(LastTradedQuantity).longValue(),BigDecimal.valueOf(Double.valueOf(Low).longValue()),BigDecimal.valueOf(Double.valueOf(MarketCap).longValue()),BigDecimal.valueOf(Double.valueOf(OpeningPrice)),formatstringtodate(Time),Double.valueOf(Volume).longValue());
            livePrices.add(securityLivePrice);
            margeetxt.append(" " + (livePrices.get(i).Company) + " " + (formatter.format((livePrices.get(i).OpeningPrice)))+ "  ");
            i++;
        }
        prgs.setVisibility(View.INVISIBLE);
        liveMarketAdapter = new LiveMarketAdapter(HomeActivity.this, livePrices);
        livemarketpricerecyclerview.setHasFixedSize(true);
        livemarketpricerecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        livemarketpricerecyclerview.setAdapter(liveMarketAdapter);
        cursor.close();
        dbHelper.close();
    }*/

    public Date formatstringtodate(String date){
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
        Date dt=null;
        try {
            dt = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED);
        View snackView = snackbar.getView();
        TextView textView = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public void getliveData(){
        Call<LiveDataResponseModel> call = RetrofitClient
                .getInstance().getApi().fetchLiveData("Bearer " +  _token);

        String s = call.toString();
        call.enqueue(new Callback<LiveDataResponseModel>() {
            @Override
            public void onResponse(Call<LiveDataResponseModel> call, Response<LiveDataResponseModel> response) {
                if(response.isSuccessful()){
                    LiveDataResponseModel liveDataResponseModel = response.body();
                    if ( liveDataResponseModel.isSuccess()){

                       /*     droptableLivedata();
                            for(int i = 0; i< livePrices.size(); i++){
                                dbHelper.saveLiveDataTolocalDatabase(livePrices.get(i).Board,String.valueOf(livePrices.get(i).Change),String.valueOf(livePrices.get(i).Close), livePrices.get(i).Company,String.valueOf(livePrices.get(i).High),String.valueOf(livePrices.get(i).LastDealPrice),String.valueOf(livePrices.get(i).LastTradedQuantity),String.valueOf(livePrices.get(i).Low),String.valueOf(livePrices.get(i).MarketCap),String.valueOf(livePrices.get(i).OpeningPrice),String.valueOf(livePrices.get(i).Time),String.valueOf(livePrices.get(i).Volume),database);
                            }

                            readLivedatafromlocal();*/

                        LiveMarketAdapter liveMarketAdapter = new LiveMarketAdapter(HomeActivity.this, liveDataResponseModel.getData().getLiveMarketPricesResult().getSecurityLivePrice());
                        livemarketpricerecyclerview.setHasFixedSize(true);
                        livemarketpricerecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        livemarketpricerecyclerview.setAdapter(liveMarketAdapter);
                        txttrandingstats.setText("Real-Time Market Prices");
                        prgs.setVisibility(View.INVISIBLE);
                        DecimalFormat format = new DecimalFormat("0.#");

                        if(liveMarketAdapter!=  null){
                            for (int i = 0; i < liveMarketAdapter.getItemCount(); i++) {
                                margeetxt.append(" " + (liveDataResponseModel.getData().getLiveMarketPricesResult().getSecurityLivePrice().get(i).getCompany()) + " " + (format.format(Double.valueOf(liveDataResponseModel.getData().getLiveMarketPricesResult().getSecurityLivePrice().get(i).getOpeningPrice())))+ "  ");

                            }
                        }
                    }
                    else{
                        Toast.makeText(HomeActivity.this,liveDataResponseModel.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(HomeActivity.this,"Server error no response",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LiveDataResponseModel> call, Throwable t) {

            }
        });
    }
}

