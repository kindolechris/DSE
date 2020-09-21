package com.dsetanzania.dse.activities;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import com.dsetanzania.dse.helperClasses.CustomMarker;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.interfaces.InternetcheckInterface;
import com.dsetanzania.dse.models.graphdata.GraphDataResponseModel;
import com.dsetanzania.dse.models.transactions.sell.PersonalShareSalesResponseModel;
import com.dsetanzania.dse.storage.DbHelper;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class SellShareActivity extends AppCompatActivity {

    int availableSharesAmount;
    String companyname;
    String boardshareid;
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
    final ArrayList<String> xAxisLabel = new ArrayList<>();
    private LineChart linechart;
    Call<GraphDataResponseModel> base_call_back;
    final List<Entry> entries = new ArrayList<>();
    CustomMarker marker;

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
        marker = new CustomMarker (getApplicationContext(), R.layout.graph_pointer);
        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            companyname = extras.getString("companyname");
            availableSharesAmount = extras.getInt("availableSharesAmount");
            boardshareid = extras.getString("boardshareid");
        }
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
                            showSnackbar("No internet access");
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

        try {
            initGraph("d");
        } catch (Exception e) {
            Toast.makeText(SellShareActivity.this,"Exception thrown",Toast.LENGTH_SHORT).show();
        }
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
        entries.clear();
        linechart = findViewById(R.id.chart1);
        linechart.getDescription().setEnabled(false);
        linechart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        linechart.setPinchZoom(false);
        linechart.setMarker(marker);
        linechart.setDrawGridBackground(false);

        XAxis xAxis = linechart.getXAxis();
        xAxis.setLabelCount(5,true);
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
        linechart.animateX(2500, Easing.Linear);
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
        xAxisLabel.clear();
        base_call_back = RetrofitClient
                .getInstance().getApi().fetchDailyBoardGraphData(boardshareid,"Bearer " + _token);
        base_call_back.enqueue(new Callback<GraphDataResponseModel>() {
            @Override
            public void onResponse(Call<GraphDataResponseModel> call, Response<GraphDataResponseModel> response) {
                GraphDataResponseModel dailygraphdata = response.body();
                if(response.isSuccessful()){
                    if (dailygraphdata.isSuccess()){
                        String[] xdata = dailygraphdata.getData().getX().split(",");
                        String[] ydata = dailygraphdata.getData().getY().split(",");
                        for(int i=0; i<xdata.length; i++) {
                            xAxisLabel.add(xdata[i]);
                            entries.add(new Entry(Float.valueOf(i), Float.valueOf(ydata[i]),xdata[i]));

                        }


                        LineDataSet set = new LineDataSet(entries, "Market Price");
                        set.setColors(getResources().getColor(R.color.colorPrimary));
                        set.setDrawFilled(true);
                        set.setDrawVerticalHighlightIndicator(true);
                        set.setFillDrawable(getResources().getDrawable(R.drawable.gradient_graph));
                        set.setDrawValues(false);
                        set.setDrawCircles(false);
                        LineData data = new LineData(set);
                        linechart.setData(data);
                        linechart.invalidate();
                        graphviewLoader.setVisibility(View.INVISIBLE);
                        linechart.setVisibility(View.VISIBLE);
                    }
                    else{
                        //Toast.makeText(BuyPersonShareActivity.this,"No data fiund",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SellShareActivity.this,"Server error",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GraphDataResponseModel> call, Throwable t) {
            }
        });

    }

    public void setWeekly(){
        xAxisLabel.clear();
        base_call_back = RetrofitClient
                .getInstance().getApi().fetchWeeklyBoardGraphData(boardshareid,"Bearer " + _token);
        base_call_back.enqueue(new Callback<GraphDataResponseModel>() {
            @Override
            public void onResponse(Call<GraphDataResponseModel> call, Response<GraphDataResponseModel> response) {
                GraphDataResponseModel dailygraphdata = response.body();
                if(response.isSuccessful()){
                    if (dailygraphdata.isSuccess()){
                        String[] xdata = dailygraphdata.getData().getX().split(",");
                        String[] ydata = dailygraphdata.getData().getY().split(",");
                        for(int i=0; i<xdata.length; i++) {
                            xAxisLabel.add(xdata[i]);
                            entries.add(new Entry(Float.valueOf(i), Float.valueOf(ydata[i]),xdata[i]));

                        }

                        LineDataSet set = new LineDataSet(entries, "Market Price");
                        set.setColors(getResources().getColor(R.color.colorPrimary));
                        set.setDrawFilled(true);
                        set.setFillDrawable(getResources().getDrawable(R.drawable.gradient_graph));
                        set.setDrawValues(false);
                        set.setDrawCircles(false);
                        LineData data = new LineData(set);
                        linechart.setData(data);
                        linechart.invalidate();
                        graphviewLoader.setVisibility(View.INVISIBLE);
                        linechart.setVisibility(View.VISIBLE);
                    }
                    else{
                        //Toast.makeText(BuyBoardShareActivity.this,"No data fiund",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SellShareActivity.this,"Server error",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GraphDataResponseModel> call, Throwable t) {
            }
        });
    }

    public void setMonthly(){
        xAxisLabel.clear();
        base_call_back = RetrofitClient
                .getInstance().getApi().fetchMonthlyBoardGraphData(boardshareid,"Bearer " + _token);
        base_call_back.enqueue(new Callback<GraphDataResponseModel>() {
            @Override
            public void onResponse(Call<GraphDataResponseModel> call, Response<GraphDataResponseModel> response) {
                GraphDataResponseModel dailygraphdata = response.body();
                if(response.isSuccessful()){
                    if (dailygraphdata.isSuccess()){
                        String[] xdata = dailygraphdata.getData().getX().split(",");
                        String[] ydata = dailygraphdata.getData().getY().split(",");
                        for(int i=0; i<xdata.length; i++) {
                            xAxisLabel.add(xdata[i]);
                            entries.add(new Entry(Float.valueOf(i), Float.valueOf(ydata[i]),xdata[i]));
                        }


                        LineDataSet set = new LineDataSet(entries, "Market Price");
                        set.setColors(getResources().getColor(R.color.colorPrimary));
                        set.setDrawFilled(true);
                        set.setFillDrawable(getResources().getDrawable(R.drawable.gradient_box));
                        set.setDrawValues(false);
                        set.setDrawCircles(false);
                        LineData data = new LineData(set);
                        linechart.setData(data);
                        linechart.invalidate();
                        graphviewLoader.setVisibility(View.INVISIBLE);
                        linechart.setVisibility(View.VISIBLE);
                    }
                    else{
                        //Toast.makeText(BuyPersonShareActivity.this,"No data fiund",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SellShareActivity.this,"Server error",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GraphDataResponseModel> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Handler handler = new Handler();
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        else if (item.getItemId() == R.id.sortbyday) {
            linechart.setVisibility(View.INVISIBLE);
            graphviewLoader.setVisibility(View.VISIBLE);
            initGraph("d");
        }
        else if (item.getItemId() == R.id.sortbyweek) {
            linechart.setVisibility(View.INVISIBLE);
            graphviewLoader.setVisibility(View.VISIBLE);
            initGraph("w");
        }
        else if (item.getItemId() == R.id.sortbymonth) {
            linechart.setVisibility(View.INVISIBLE);
            graphviewLoader.setVisibility(View.VISIBLE);
            initGraph("m");

        }

        return super.onOptionsItemSelected(item);
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

