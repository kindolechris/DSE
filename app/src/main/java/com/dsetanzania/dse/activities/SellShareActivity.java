package com.dsetanzania.dse.activities;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.interfaces.InternetcheckInterface;
import com.dsetanzania.dse.models.transactions.sell.PersonalShareSalesResponseModel;
import com.dsetanzania.dse.storage.DbHelper;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class SellShareActivity extends AppCompatActivity {

    int availableSharesAmount;
    String companyname;
    int boardshareid;
    TextView txtshareavailabe;
    EditText txtshareprice;
    EditText txtquantity;
    Button sellshabtn;
    private SharedPreferences sharedPreferences;
    private String _token;
    ProgressBar sellShareLoader;
    LinearLayout graphviewLoader;
    LinearLayout sellShareLayout;
    DbHelper dbHelper;
    View parentLayout;
    SQLiteDatabase database;
    private LineChart linechart;
    ArrayList<ILineDataSet> dataSets;
    final List<String> xAxisLabel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_share);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtshareprice = (EditText) findViewById(R.id.txtshareprice);
        txtquantity = (EditText) findViewById(R.id.txtsharequantity);
        txtshareavailabe = (TextView) findViewById(R.id.txtavailableshares);
        sellshabtn = (Button) findViewById(R.id.btnSellShare);
        sellShareLayout = (LinearLayout) findViewById(R.id.sellShareLayout);
        sellShareLoader = (ProgressBar) findViewById(R.id.sellShareLoader);
        parentLayout = findViewById(android.R.id.content);
        graphviewLoader = (LinearLayout) findViewById(R.id.graphviewLayoutLoader);
        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            companyname = extras.getString("companyname");
            availableSharesAmount = extras.getInt("availableSharesAmount");
            boardshareid = extras.getInt("boardshareid");
        }
        dataSets = new ArrayList<>();
        float defaultBarWidth = -1;
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("Jan", "Feb", "March", "April", "May", "June","July", "August", "September", "October", "November", "December"));
        linechart = findViewById(R.id.chart1);
        sellshabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtshareprice.getText().toString().trim()) || TextUtils.isEmpty(txtquantity.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Please place your quantity and price to sell",Toast.LENGTH_SHORT).show();
                    return;
                }

                checkInternet task = new checkInternet(getApplicationContext(), new InternetcheckInterface() {
                    @Override
                    public void checkMethod(String result) {

                        if(result == "Access"){
                            try {
                                sellShareLoader.setVisibility(View.VISIBLE);
                                sellShareLayout.setVisibility(View.INVISIBLE);
                                sellShares("sell",txtquantity.getText().toString().trim(),txtshareprice.getText().toString().trim(),"personal",String.valueOf(boardshareid));
                            } catch (Exception e) {
                                Toast.makeText(SellShareActivity.this,"System error",Toast.LENGTH_SHORT).show();
                            }

                        }
                        else if(result == "NoAccess"){
                            Snackbar snackbar = Snackbar
                                    .make(parentLayout, "No internet access", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });

                task.execute();
            }
        });

        txtshareavailabe.setText(String.valueOf(availableSharesAmount));
        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        _token = sharedPreferences.getString("token", "");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Sell " + companyname +" shares");
        }

        initGraph("m");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Handler handler = new Handler();
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        else if (item.getItemId() == R.id.sortbyday) {
            graphviewLoader.setVisibility(View.VISIBLE);
            linechart.setVisibility(View.INVISIBLE);
            handler.postDelayed(new Runnable() {
                public void run() {
                    initGraph("d");
                    graphviewLoader.setVisibility(View.INVISIBLE);
                    linechart.setVisibility(View.VISIBLE);
                }
            }, 2000);
        }
        else if (item.getItemId() == R.id.sortbyweek) {
            graphviewLoader.setVisibility(View.VISIBLE);
            linechart.setVisibility(View.INVISIBLE);
            handler.postDelayed(new Runnable() {
                public void run() {
                    initGraph("w");
                    graphviewLoader.setVisibility(View.INVISIBLE);
                    linechart.setVisibility(View.VISIBLE);
                }
            }, 2000);
        }
        else if (item.getItemId() == R.id.sortbymonth) {
            graphviewLoader.setVisibility(View.VISIBLE);
            linechart.setVisibility(View.INVISIBLE);
            handler.postDelayed(new Runnable() {
                public void run() {
                    initGraph("m");
                    graphviewLoader.setVisibility(View.INVISIBLE);
                    linechart.setVisibility(View.VISIBLE);
                }
            }, 2000);

        }

        return super.onOptionsItemSelected(item);
    }

    public void sellShares(String transactiontype,String shareAmount,String price,String transactionFrom,String boarsharedId){
        Call<PersonalShareSalesResponseModel> call = RetrofitClient
                .getInstance().getApi().sellSharesTMarket(transactiontype,shareAmount,price,transactionFrom,boarsharedId,"Bearer " + _token);
        call.enqueue(new Callback<PersonalShareSalesResponseModel>() {
            @Override
            public void onResponse(Call<PersonalShareSalesResponseModel> call, Response<PersonalShareSalesResponseModel> response) {
                PersonalShareSalesResponseModel personalShareSalesResponseModel = response.body();

                if (personalShareSalesResponseModel.isSuccess()){
                    sellShareLoader.setVisibility(View.INVISIBLE);
                    sellShareLayout.setVisibility(View.VISIBLE);
                    //dbHelper.updateUserLocalDatabase(String.valueOf(buyboardshareresponse.getData().getUser().getId()),buyboardshareresponse.getData().getUser().getStock(),buyboardshareresponse.getData().getUser().getBonds(),buyboardshareresponse.getData().getUser().getFirstname(),buyboardshareresponse.getData().getUser().getLastname(),buyboardshareresponse.getData().getUser().getTradername(),buyboardshareresponse.getData().getUser().getEmail(),buyboardshareresponse.getData().getUser().getYearOfStudy(),buyboardshareresponse.getData().getUser().getUniversity(),buyboardshareresponse.getData().getUser().getCoursename(),buyboardshareresponse.getData().getUser().getPhonenumber(),buyboardshareresponse.getData().getUser().getRole(),buyboardshareresponse.getData().getUser().getVirtualmoney(),buyboardshareresponse.getData().getUser().getGender(), DbContract.SYNC_STATUS_FAILED,database);
                    dbHelper.saveShareTransactionTolocalDatabase(personalShareSalesResponseModel.getData().getPersonalsharesTransaction().getId(),String.valueOf(personalShareSalesResponseModel.getData().getPersonalsharesTransaction().getSharesamount()),personalShareSalesResponseModel.getData().getPersonalsharesTransaction().getCreatedAt(),personalShareSalesResponseModel.getData().getPersonalsharesTransaction().getTimeago(),personalShareSalesResponseModel.getData().getPersonalsharesTransaction().getCompanyname(),personalShareSalesResponseModel.getData().getPersonalsharesTransaction().getPrice(),personalShareSalesResponseModel.getData().getPersonalsharesTransaction().getTransactiontype(),personalShareSalesResponseModel.getData().getPersonalsharesTransaction().getStatus(),database);
                    //readFromLocalDb();
                    Toast.makeText(SellShareActivity.this, personalShareSalesResponseModel.getMessage(),Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    sellShareLoader.setVisibility(View.INVISIBLE);
                    sellShareLayout.setVisibility(View.VISIBLE);;
                    Toast.makeText(SellShareActivity.this, personalShareSalesResponseModel.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PersonalShareSalesResponseModel> call, Throwable t) {
                sellShareLoader.setVisibility(View.INVISIBLE);
                sellShareLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void initGraph(String duration){
        xAxisLabel.clear();
        linechart = findViewById(R.id.chart1);
        linechart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        linechart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        linechart.setPinchZoom(false);

        linechart.setDrawGridBackground(false);

        XAxis xAxis = linechart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xAxisLabel.get((int) value);
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        linechart.getAxisLeft().setDrawGridLines(false);
        // add a nice and smooth animation
        linechart.animateY(1500);
        linechart.getLegend().setEnabled(false);

        switch(duration) {
            case "d":
                setDaily();
                break;
            case "w":
                setWeekly();
                break;
            case "m":
                setMonthly();
                break;
            default:
                break;
        }
    }

    public void setDaily(){
        xAxisLabel.add("7:00AM");
        xAxisLabel.add("8:00AM");
        xAxisLabel.add("9:00AM");
        xAxisLabel.add("10:00AM");
        xAxisLabel.add("11:00AM");
        xAxisLabel.add("12:00PM");
        xAxisLabel.add("13:00PM");
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 85f));
        entries.add(new Entry(1f, 90f));
        entries.add(new Entry(2f, 106f));
        entries.add(new Entry(3f, 86f));
        entries.add(new Entry(4f, 92f));
        entries.add(new Entry(5f, 110f));
        entries.add(new Entry(6f, 100f));

        LineDataSet set = new LineDataSet(entries, "Market Price");
        set.setColors(getResources().getColor(R.color.colorPrimary));
        set.setDrawFilled(true);
        set.setFillDrawable(getResources().getDrawable(R.drawable.gradient_graph));
        set.setDrawValues(false);
        LineData data = new LineData(set);
        linechart.setData(data);
        linechart.invalidate();
    }

    public void setWeekly(){
        xAxisLabel.add("Mon");
        xAxisLabel.add("Tue");
        xAxisLabel.add("Wed");
        xAxisLabel.add("Thu");
        xAxisLabel.add("Fri");
        xAxisLabel.add("Sat");
        xAxisLabel.add("Sun");

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 95f));
        entries.add(new Entry(1f, 85f));
        entries.add(new Entry(2f, 82f));
        entries.add(new Entry(3f, 90f));
        entries.add(new Entry(4f, 98f));
        entries.add(new Entry(5f, 115f));
        entries.add(new Entry(6f, 102f));

        LineDataSet set = new LineDataSet(entries, "Market Price");
        set.setColors(getResources().getColor(R.color.colorPrimary));
        set.setDrawValues(false);
        set.setDrawFilled(true);
        set.setFillDrawable(getResources().getDrawable(R.drawable.gradient_graph));
        LineData data = new LineData(set);
        linechart.setData(data);
        linechart.invalidate();
    }

    public void setMonthly(){
        xAxisLabel.add("Jan1");
        xAxisLabel.add("Feb1");
        xAxisLabel.add("March1");
        xAxisLabel.add("April1");
        xAxisLabel.add("May1");
        xAxisLabel.add("June1");
        xAxisLabel.add("Jully1");

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 115f));
        entries.add(new Entry(1f, 88f));
        entries.add(new Entry(2f, 85f));
        entries.add(new Entry(3f, 86f));
        entries.add(new Entry(4f, 90f));
        entries.add(new Entry(5f, 90f));
        entries.add(new Entry(6f, 95f));

        LineDataSet set = new LineDataSet(entries, "Market Price");
        set.setColors(getResources().getColor(R.color.colorPrimary));
        set.setDrawValues(false);
        set.setDrawFilled(true);
        set.setFillDrawable(getResources().getDrawable(R.drawable.gradient_graph));
        LineData data = new LineData(set);
        linechart.setData(data);
        linechart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

}

