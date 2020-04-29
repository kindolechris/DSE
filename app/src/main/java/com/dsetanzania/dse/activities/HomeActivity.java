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
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import com.dsetanzania.dse.helperClasses.SychronizeLiveDataTimer;
import com.dsetanzania.dse.interfaces.InternetcheckInterface;
import com.dsetanzania.dse.helperClasses.UserImageUpdloads;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUArrayOfSecurityLivePrice;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUdefault_AtsWebFeedService;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.UserModel;
import com.dsetanzania.dse.models.UserDataResponseModel;
import com.dsetanzania.dse.storage.SharedPrefManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout livemarketlinearlayout;
    LinearLayout portfoliolinearlayout;
    LinearLayout tellafriendlinearlayout;
    LinearLayout faqslinearlayout;
    LinearLayout transactionlinearlayout;
    LinearLayout layoutcloseChoice;
    LinearLayout aboutUstlinearlayout;
    TextView txttradername;
    TextView txtstockbalance;
    TextView txtbondbalance;
    TextView txttrandingstats;
    TextView queuestext;
    ProgressBar prgs;
    TextView txtvirtualshare;
    private String _token;
    RecyclerView livemarketpricerecyclerview;
    SwipeRefreshLayout swipeRefreshLayout;
    View parentLayout;
    TextView QueuesCountTxt;
    TextView margeetxt;
    DatabaseReference dbReference;
    StorageReference storegaReference;
    FirebaseUser fuser;
    Uri selectedImagURL;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    Dialog dialog,dialogChoice;
    LinearLayout closeLayout;
    Button equitybtn;
    Button bondbtn;
    String realtimedata;
    UserModel user;
    NumberFormat formatter;
    SychronizeLiveDataTimer sycnc;
    UserDataResponseModel userdata;
    private SharedPrefManager sharedPrefManager;
    public static Retrofit retrofit = null;
    private static int PICK_IMAGE_REQUEST = 1;
    private static String SOAP_ACTION = "http://tempuri.org/AtsWebFeedService/LiveMarketPrices";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME = "LiveMarketPrices";
    private static String URL = "http://ht.ddnss.ch:6080/livefeedCustodian/FeedWebService.svc?wsdl";

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
        user = SharedPrefManager.getInstance(this).getUser();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshLayout);

        dialog = new Dialog(HomeActivity.this,R.style.Mydialogtheme);
        dialog.setContentView(R.layout.custom_pop_up_for_queued_orders);

        queuestext = (TextView) dialog.findViewById(R.id.quedtransactiontxt);

        closeLayout  =(LinearLayout) dialog.findViewById(R.id.layoutclose);

        txttradername = (TextView) findViewById(R.id.txttradername);
        txtstockbalance = (TextView) findViewById(R.id.stocklbalance);
        txtbondbalance = (TextView) findViewById(R.id.bondsbalance);
        txtvirtualshare = (TextView) findViewById(R.id.sharepricetxt);
        livemarketlinearlayout = (LinearLayout) findViewById(R.id.livemarketLayout);
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

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        final LinearLayout content = (LinearLayout) findViewById(R.id.content);

        dialogChoice = new Dialog(HomeActivity.this,R.style.Mydialogtheme);
        dialogChoice.setContentView(R.layout.custom_pop_up_choice);
        equitybtn = (Button)dialogChoice.findViewById(R.id.btnEquity);
        bondbtn = (Button)dialogChoice.findViewById(R.id.btnBonds);

        equitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, SimulatedEquityActivity.class);
                HomeActivity.this.startActivity(myIntent);
                dialogChoice.dismiss();
            }
        });

        layoutcloseChoice  =(LinearLayout) dialogChoice.findViewById(R.id.layoutcloseChoice);

        layoutcloseChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChoice.dismiss();
            }
        });

        bondbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, SimulatedBondsActivity.class);
                HomeActivity.this.startActivity(myIntent);
                dialogChoice.dismiss();
            }
        });

        margeetxt.setSelected(true);

        getlivedata();


        Log.v("tokensssssssssssssss",user.getToken());

        sycnc = new SychronizeLiveDataTimer(HomeActivity.this);

         new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        updateFieldsOnChange();
                    }
                });
            }
        }, 0, 20);



        checkInternet task = new checkInternet(getApplicationContext(), new InternetcheckInterface() {
            @Override
            public void checkMethod(String result) {

                if(result == "Access"){
                 /*   Snackbar snackbar = Snackbar
                            .make(parentLayout, "Internet is on", Snackbar.LENGTH_LONG);
                    snackbar.show();*/
                }
                else if(result == "NoAccess"){
                    Snackbar snackbar = Snackbar
                            .make(parentLayout, "No internet access", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    Snackbar snackbar = Snackbar
                            .make(parentLayout, "Service unavailable", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        task.execute();

        livemarketlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().show();
                //drawerLayout.closeDrawers();
                dialogChoice.show();
            }
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
                Intent myIntent = new Intent(HomeActivity.this, FaqsActivity.class);
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


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getlivedata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


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
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent NextActivity = new Intent(HomeActivity.this, HomeActivity.class);
            NextActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            HomeActivity.this.startActivity(NextActivity);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sycnc.stopTimer();
    }

    @Override
    public void onResume(){
        super.onResume();
        sycnc = new SychronizeLiveDataTimer(HomeActivity.this);
        sycnc.startTimer(100);
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
            sycnc.stopTimer();
            Intent homeintent = new Intent(HomeActivity.this, LoginActivity.class);
            homeintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            HomeActivity.this.startActivity(homeintent);
            sharedPrefManager = new SharedPrefManager(HomeActivity.this);
            sharedPrefManager.clear();
            finish();
        }
        else if(id== R.id.Userface){
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, PICK_IMAGE_REQUEST);
        }
        else{
            queuestext.setText("You have " + QueuesCountTxt.getText().toString() + " pending \nqueue order(s)");
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

            dbReference = FirebaseDatabase.getInstance().getReference("UserImageUpdloads");
            storegaReference = FirebaseStorage.getInstance().getReference("UserImageUpdloads");
            final StorageReference Imagename = storegaReference.child("image" + selectedImagURL.getLastPathSegment());
            Imagename.putFile(selectedImagURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            UserImageUpdloads upload = new UserImageUpdloads(fuser.getUid(),String.valueOf(uri));
                            String uploadId = dbReference.push().getKey();
                            dbReference.child(uploadId).setValue(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(HomeActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }


    public void getlivedata(){
        GetLiveMarketTask gt = new GetLiveMarketTask();
        gt.execute();
    }

    class GetLiveMarketTask extends AsyncTask {

        LiveMarketAdapter lvm;
        OOUArrayOfSecurityLivePrice res;

        @Override
        protected Object doInBackground(Object[] objects) {

            try {

                OOUdefault_AtsWebFeedService service = new OOUdefault_AtsWebFeedService("http://ht.ddnss.ch:6080/livefeedCustodian/FeedWebService.svc");

             res  = service.LiveMarketPrices();

                lvm = new LiveMarketAdapter(HomeActivity.this, res);



            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            livemarketpricerecyclerview.setHasFixedSize(true);
            livemarketpricerecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            livemarketpricerecyclerview.setAdapter(lvm);
            txttrandingstats.setText("Real-Time Market Prices");
            prgs.setVisibility(View.INVISIBLE);

            try {
                realtimedata = "";

                NumberFormat formatter = new DecimalFormat("#,###");
                for (int i = 0; i < res.getPropertyCount(); i++) {
                    realtimedata = res.get(i).MarketCap.toString();
                    Log.i("Dataaaaaa", realtimedata);
                    margeetxt.append(" " + (res.get(i).Company) + " " + (formatter.format((res.get(i).OpeningPrice)))+ "  ");

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("TAAAAAG","No dataaa");
                return;
            }

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



    public void updateFieldsOnChange(){
        txttradername.setText(sycnc.userdata.getUsers().getTradername());
        txtstockbalance.setText(String.valueOf(sycnc.userdata.getUsers().getStock()));
        txtbondbalance.setText(String.valueOf(sycnc.userdata.getUsers().getBonds()));
        txtvirtualshare.setText(formatter.format(sycnc.userdata.getUsers().getVirtualmoney()));
    }
}

