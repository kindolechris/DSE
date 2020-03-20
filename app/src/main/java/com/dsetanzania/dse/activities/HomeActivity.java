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
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsetanzania.dse.LiveMarketAdapter;
import com.dsetanzania.dse.helperClasses.InternetcheckInterface;
import com.dsetanzania.dse.helperClasses.UserImageUpdloads;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUArrayOfSecurityLivePrice;
import com.dsetanzania.dse.helperClasses.livedata_classes.OOUdefault_AtsWebFeedService;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.NewsFeeds;
import com.dsetanzania.dse.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    RelativeLayout notificationCount1;
    LinearLayout livemarketlinearlayout;
    LinearLayout portfoliolinearlayout;
    LinearLayout tellafriendlinearlayout;
    LinearLayout faqslinearlayout;
    LinearLayout transactionlinearlayout;
    LinearLayout layoutcloseChoice;
    LinearLayout aboutUstlinearlayout;
    TextView txttradername;
    TextView txtvirtualbalance;
    TextView txtstockbalance;
    TextView txtbondbalance;
    TextView txttrandingstats;
    TextView txtdate;
    TextView queuestext;
    ProgressBar prgs;
    TextView txtvirtualshare;
    EditText editText;
    private FirebaseAuth mAuth;
    String response;
    RecyclerView livemarketpricerecyclerview;
    private Bundle bundleResult=new Bundle();
    private JSONObject JSONObj;
    private JSONArray JSONArr;
    private SoapObject resultSOAP;
    ArrayAdapter<String> adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayout mEmptyViewContainer;
    View parentLayout;
    TextView QueuesCountTxt;
    TextView margeetxt;
    DatabaseReference dbReference;
    StorageReference storegaReference;
    FirebaseUser fuser;
    Uri selectedImagURL;
    private StorageTask mUploadTask;
    private TextView emptyView;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    Dialog dialog,dialogChoice;
    LinearLayout closeLayout;
    Button equitybtn;
    Button bondbtn;
    String realtimedata;

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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshLayout);

        dialog = new Dialog(HomeActivity.this,R.style.Mydialogtheme);
        dialog.setContentView(R.layout.custom_pop_up_for_queued_orders);

        queuestext = (TextView) dialog.findViewById(R.id.quedtransactiontxt);

        closeLayout  =(LinearLayout) dialog.findViewById(R.id.layoutclose);

        mAuth = FirebaseAuth.getInstance();

        fuser = mAuth.getCurrentUser();

        txttradername = (TextView) findViewById(R.id.txttradername);
        //txtvirtualbalance = (TextView) findViewById(R.id.virtualbalance);
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

        updatefields();

        getProfileImage();

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
            mAuth.signOut();
            Intent homeintent = new Intent(HomeActivity.this, LoginActivity.class);
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

    private void  getProfileImage(){

        final FirebaseUser fuser = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserImageUpdloads");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserImageUpdloads _image = snapshot.getValue(UserImageUpdloads.class);
                    if(_image.getUserId().equals(fuser.getUid())){

                        //imageView = (CircleImageView) findViewById(R.id.profile_image);
                        //Glide.with(getApplicationContext()).load(_image.getImageUrl()).into(imageView);
                        //Picasso.get().load(_image.getImageUrl()).placeholder(R.drawable.ic_account).resize(100,100).centerCrop().into(imageView);
                        //imageView.setBorderWidth(2);
                        //imageView.setBorderColor(getResources().getColor(R.color.colorWhite));
                        Log.v("ImageURL","Url is" + _image.getImageUrl());
                    }
                    else {
                        Log.v("ImageURL","Is not available" + _image.getImageUrl());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void updatefields(){

        dbReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference usersRef = dbReference.child("Users").child(fuser.getUid());

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User _user = dataSnapshot.getValue(User.class);
                    NumberFormat formatter = new DecimalFormat("#,###");
                    String vsformat = formatter.format( _user.getVirtualmoney());
                    txttradername.setText(_user.getTradername());
                    txtvirtualshare.setText("Tshs. " + vsformat);
                    //txtvirtualbalance.setText("Tshs. " + vsformat);
                    txtstockbalance.setText(String.valueOf(_user.getStock()));
                    txtbondbalance.setText(String.valueOf(_user.getBonds()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        dbReference = FirebaseDatabase.getInstance().getReference("UserImageUpdloads");
        storegaReference = FirebaseStorage.getInstance().getReference("UserImageUpdloads");
        if (selectedImagURL != null) {
            StorageReference fileReference = storegaReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(selectedImagURL));

            mUploadTask = fileReference.putFile(selectedImagURL)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(HomeActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            UserImageUpdloads upload = new UserImageUpdloads(fuser.getUid(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = dbReference.push().getKey();
                            dbReference.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }

   /* private void  getTransactionsQueues(){

        final FirebaseUser fuser = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Transactions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Transactions _transaction = snapshot.getValue(Transactions.class);
                    if(_transaction.getUserId().equals(fuser.getUid()) && _transaction.getStatus().equals("Queued")){
                        int pendingQueueCount = 0;
                        pendingQueueCount = pendingQueueCount + 1;
                        QueuesCountTxt.setText(String.valueOf(pendingQueueCount));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    public void closeModal(View view){
        dialog.dismiss();
    }
}

