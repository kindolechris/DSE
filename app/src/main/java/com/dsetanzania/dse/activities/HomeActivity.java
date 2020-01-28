package com.dsetanzania.dse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dsetanzania.dse.LiveMarketAdapter;
import com.dsetanzania.dse.OOUArrayOfSecurityLivePrice;
import com.dsetanzania.dse.OOUdefault_AtsWebFeedService;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout livemarketlinearlayout;
    LinearLayout portfoliolinearlayout;
    LinearLayout tellafriendlinearlayout;
    LinearLayout faqslinearlayout;
    LinearLayout aboutUstlinearlayout;
    TextView txttradername;
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

    private static String SOAP_ACTION = "http://tempuri.org/AtsWebFeedService/LiveMarketPrices";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME = "LiveMarketPrices";

    private static String URL = "http://ht.ddnss.ch:6080/livefeedCustodian/FeedWebService.svc?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        txttradername = (TextView) findViewById(R.id.txttradername);
        txtvirtualshare = (TextView) findViewById(R.id.sharepricetxt);
        livemarketlinearlayout = (LinearLayout) findViewById(R.id.livemarketLayout);
        portfoliolinearlayout = (LinearLayout) findViewById(R.id.portfolioLayout);
        tellafriendlinearlayout = (LinearLayout) findViewById(R.id.tellafriendLayout);
        faqslinearlayout = (LinearLayout) findViewById(R.id.faqsLayout);
        livemarketpricerecyclerview = (RecyclerView) findViewById(R.id.listoflivemarket);
        aboutUstlinearlayout = (LinearLayout) findViewById(R.id.aboutusLayout);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        final LinearLayout content = (LinearLayout) findViewById(R.id.content);

        getlivedata();

        updatefields();


        livemarketlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, LiveMarketActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        portfoliolinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, PortfolioActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        faqslinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, FaqsActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        aboutUstlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, AboutUsActivity.class);
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

        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_sort, null);
                toolbar.setNavigationIcon(d);
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
        return super.onOptionsItemSelected(item);
    }

    public void updatefields(){

        FirebaseUser fuser = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User _user = dataSnapshot.getValue(User.class);
                    NumberFormat formatter = new DecimalFormat("#,###");
                    double vs = Double.parseDouble(_user.getVirtualshare());
                    String vsformat = formatter.format(vs);
                    txttradername.setText(_user.getTradername());
                    txtvirtualshare.setText("Tshs. " + vsformat);
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


    public void loadprices() throws JSONException {


        //for linear parameter
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.implicitTypes = true;
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;


        try {

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            androidHttpTransport.call(SOAP_ACTION, envelope);
            String response = androidHttpTransport.responseDump;
            resultSOAP = (SoapObject) envelope.getResponse();

            //ParseJSON(response,"Company");
        } catch (HttpResponseException e) {
            // TODO Auto-generated catch block
            Log.e("HTTPLOG", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("IOLOG", e.getMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            Log.e("XMLLOG", e.getMessage());
            e.printStackTrace();
        } //send request

    }

    class GetLiveMarketTask extends AsyncTask{

        LiveMarketAdapter lvm;

        @Override
        protected Object doInBackground(Object[] objects) {
            //testMedoth();


                try {

                    OOUdefault_AtsWebFeedService service = new OOUdefault_AtsWebFeedService("http://ht.ddnss.ch:6080/livefeedCustodian/FeedWebService.svc");

                    OOUArrayOfSecurityLivePrice res = service.LiveMarketPrices();

                     lvm = new LiveMarketAdapter(getApplicationContext(),res);


                    String val = "";
                    for (int i=0; i<res.getPropertyCount(); i++) {
                        val = res.get(i).MarketCap.toString();
                        Log.i("", val);
                    }
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
             //getElementsFromSOAP(resultSOAP);
            //parseXML();
        }
    }

    public  void testMedoth(){

        //Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


        //Use this to add parameters
        //request.addProperty("Parameter","Value");

        //Declare the version of the SOAP request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        //Needed to make the internet call
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            //this is the actual part that will call the webservice
            androidHttpTransport.call(SOAP_ACTION, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }


        SoapObject obj =(SoapObject) envelope.bodyIn;
        response = obj.getProperty(0).toString();
        if(response != null){

            System.out.println(response);
            //Toast.makeText(getApplicationContext(),"got something...",Toast.LENGTH_LONG);
        }
    }

    public void parseXML(){

    }


    public ArrayList getElementsFromSOAP(SoapObject so){
        final ArrayList<String> resultArray = new ArrayList<>();
        int elementCount = so.getPropertyCount();

        for(int i = 0;i<elementCount;i++){
            PropertyInfo pi = new PropertyInfo();
            SoapObject nestedSO = (SoapObject)so.getProperty(i);

            int nestedElementCount = nestedSO.getPropertyCount();
            Log.i("id", Integer.toString(nestedElementCount));

            for(int ii = 0;ii<nestedElementCount;ii++){
                nestedSO.getPropertyInfo(ii, pi);
                resultArray.add(pi.getValue().toString());

                  Log.i("object",pi.getName() + ": " + pi.getValue());
                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        // Any UI task, example
                        //adapter = new ArrayAdapter<>(HomeActivity.this,android.R.layout.simple_list_item_single_choice,resultArray);
                        //livemarketpricerecyclerview.setAdapter(adapter);
                    }
                };
                handler.sendEmptyMessage(1);
            }
        }

        return resultArray;

    }
}

