package com.dsetanzania.dse.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.helperClasses.CustomMarker;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.interfaces.InternetcheckInterface;
import com.dsetanzania.dse.models.graphdata.GraphDataResponseModel;
import com.dsetanzania.dse.models.transactions.buy.transaction.shares.board.BuyFromBoardResponseModel;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class BuyBoardShareActivity extends AppCompatActivity {

    String openingprice;
    String companyname;
    String boardid;
    Button buybtn;
    RadioButton radiobtn;
    TextView txtshares;
    TextView txtvirtualmoney;
    TextInputEditText txtprice;
    TextInputEditText txtAmountofshares;
    Toolbar toolbar;
    View parentLayout;
    private String _token;
    DbHelper dbHelper;
    LinearLayout graphviewLoader;
    SQLiteDatabase database;
    RadioGroup radiotransactiontype;
    private SharedPreferences sharedPreferences;
    private ProgressBar buyBoardSharesLoader;
    private LinearLayout buyBoardSharesLayout;
    private LineChart linechart;
    final ArrayList<String> xAxisLabel = new ArrayList<>();
    Call<GraphDataResponseModel> base_call_back;
    final List<Entry> entries = new ArrayList<>();
    CustomMarker marker;
    String transactiontype = "Instant";
    RadioButton pendingradio;
    RadioButton instantbtn;
    final String[] time = new String[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_or_sale_board_share);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarElevation(7);
        //titletext = (TextView) findViewById(R.id.titlebuyshares);
        txtprice = (TextInputEditText) findViewById(R.id.txtprice);
        txtAmountofshares = (TextInputEditText) findViewById(R.id.txtquantity);
        txtshares = (TextView) findViewById(R.id.txshares);
        radiotransactiontype = (RadioGroup) findViewById(R.id.radiotransactiontype);
        //txtvirtualmoney = (TextView) findViewById(R.id.txtvirtualmoney);
        buyBoardSharesLoader = (ProgressBar) findViewById(R.id.buyBoardSharesLoader);
        buyBoardSharesLayout = (LinearLayout) findViewById(R.id.buyBoardSharesLayout);
        graphviewLoader = (LinearLayout) findViewById(R.id.graphviewLayoutLoader);
        dbHelper = new DbHelper(this);
        parentLayout = findViewById(android.R.id.content);
        database = dbHelper.getWritableDatabase();
        marker = new CustomMarker (getApplicationContext(), R.layout.graph_pointer);
        pendingradio = (RadioButton) findViewById(R.id.btnpending);
        instantbtn = (RadioButton) findViewById(R.id.btninstant);
        txtprice.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            openingprice = extras.getString("OpeningPrice");
            companyname = extras.getString("Companyname");
            boardid = extras.getString("boardid");
            //Toast.makeText(this,"price is" + openingprice,Toast.LENGTH_LONG).show();
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Buying from " + companyname + "  "+ "( " + openingprice +" )");
        }

        sharedPreferences = getSharedPreferences(sharedPrefrences,MODE_PRIVATE);
        _token = sharedPreferences.getString("token", "");
        buybtn = (Button) findViewById(R.id.btnBuyshares);
        readFromLocalDb();
        instantbtn.setChecked(true);
        pendingradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAmountofshares.setVisibility(View.VISIBLE);
                txtprice.setVisibility(View.VISIBLE);
                transactiontype = "Pending";
            }
        });

        instantbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAmountofshares.setVisibility(View.VISIBLE);
                txtprice.setVisibility(View.GONE);
                transactiontype = "Instant";
            }
        });
        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtAmountofshares.getText().toString().trim()) && instantbtn.isChecked()){
                    Toast.makeText(getApplicationContext(),"Please place your quantity to buy",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(txtAmountofshares.getText().toString().trim()) && TextUtils.isEmpty(txtprice.getText().toString().trim()) && pendingradio.isChecked()){
                    Toast.makeText(getApplicationContext(),"Please place your quantity and amount to buy",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(txtAmountofshares.getText().toString().trim()) || TextUtils.isEmpty(txtprice.getText().toString().trim()) && pendingradio.isChecked()){
                    Toast.makeText(getApplicationContext(),"Please place your quantity or amount to buy",Toast.LENGTH_SHORT).show();
                    return;
                }


                checkInternet task = new checkInternet(getApplicationContext(), new InternetcheckInterface() {
                    @Override
                    public void checkMethod(String result) {

                        if(result == "Access"){
                            try {

                                buyBoardSharesLoader.setVisibility(View.VISIBLE);
                                buyBoardSharesLayout.setVisibility(View.INVISIBLE);
                                buyShares("buy",txtAmountofshares.getText().toString().trim(),txtprice.getText().toString().trim(),transactiontype,"board",boardid);
                            } catch (Exception e) {
                                Toast.makeText(BuyBoardShareActivity.this,"System error",Toast.LENGTH_SHORT).show();
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

        try {
            initGraph("d");
        } catch (Exception e) {
            Toast.makeText(BuyBoardShareActivity.this,"Exception thrown",Toast.LENGTH_SHORT).show();
        }


    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }



    public void buyShares(String transactiontype,String shareAmount,String price,String buy_type,String transactionFrom,String boardId){
        Call<BuyFromBoardResponseModel> call = RetrofitClient
                .getInstance().getApi().buyShareFromBoard(transactiontype,shareAmount,price,buy_type,transactionFrom,boardId,"Bearer " + _token);
        call.enqueue(new Callback<BuyFromBoardResponseModel>() {
            @Override
            public void onResponse(Call<BuyFromBoardResponseModel> call, Response<BuyFromBoardResponseModel> response) {
                if(response.isSuccessful()){
                    BuyFromBoardResponseModel buyboardshareresponse = response.body();
                    if (buyboardshareresponse.isSuccess()){
                        buyBoardSharesLoader.setVisibility(View.INVISIBLE);
                        buyBoardSharesLayout.setVisibility(View.VISIBLE);
                        //dbHelper.updateUserLocalDatabase(String.valueOf(buyboardshareresponse.getData().getUser().getId()),buyboardshareresponse.getData().getUser().getStock(),buyboardshareresponse.getData().getUser().getBonds(),buyboardshareresponse.getData().getUser().getFirstname(),buyboardshareresponse.getData().getUser().getLastname(),buyboardshareresponse.getData().getUser().getTradername(),buyboardshareresponse.getData().getUser().getEmail(),buyboardshareresponse.getData().getUser().getYearOfStudy(),buyboardshareresponse.getData().getUser().getUniversity(),buyboardshareresponse.getData().getUser().getCoursename(),buyboardshareresponse.getData().getUser().getPhonenumber(),buyboardshareresponse.getData().getUser().getRole(),buyboardshareresponse.getData().getUser().getVirtualmoney(),buyboardshareresponse.getData().getUser().getGender(),DbContract.SYNC_STATUS_FAILED,database);
                        dbHelper.saveShareTransactionTolocalDatabase(buyboardshareresponse.getData().getPersonalsharesTransaction().getId(),String.valueOf(buyboardshareresponse.getData().getPersonalsharesTransaction().getSharesamount()),buyboardshareresponse.getData().getPersonalsharesTransaction().getCreatedAt(),buyboardshareresponse.getData().getPersonalsharesTransaction().getTimeago(),buyboardshareresponse.getData().getPersonalsharesTransaction().getCompanyname(),buyboardshareresponse.getData().getPersonalsharesTransaction().getPrice(),buyboardshareresponse.getData().getPersonalsharesTransaction().getTransactiontype(),buyboardshareresponse.getData().getPersonalsharesTransaction().getStatus(),database);
                        readFromLocalDb();
                        Toast.makeText(BuyBoardShareActivity.this, buyboardshareresponse.getMessage(),Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        buyBoardSharesLoader.setVisibility(View.INVISIBLE);
                        buyBoardSharesLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(BuyBoardShareActivity.this, buyboardshareresponse.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    buyBoardSharesLoader.setVisibility(View.INVISIBLE);
                    buyBoardSharesLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<BuyFromBoardResponseModel> call, Throwable t) {
                buyBoardSharesLoader.setVisibility(View.INVISIBLE);
                buyBoardSharesLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void updateFieldsOnChange(String stock,Double virtualmoney){
        NumberFormat formatter = new DecimalFormat("#,###");
        txtshares.setText(stock);
        //txtvirtualmoney.setText(String.valueOf(formatter.format(virtualmoney)));
    }

    public void readFromLocalDb(){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readUserFromLocalDatabase(database);
        double virtualmoney=0;
        int stock=0;
        while(cursor.moveToNext()){

            virtualmoney = cursor.getDouble(cursor.getColumnIndex(DbContract.virtualmoney));
            stock = cursor.getInt(cursor.getColumnIndex(DbContract.stock));
        }
        updateFieldsOnChange(String.valueOf(stock),virtualmoney);
        cursor.close();
        dbHelper.close();
    }

    public void initGraph(String duration){
        entries.clear();
        linechart = findViewById(R.id.chart1);
        linechart.getDescription().setEnabled(false);
        linechart.setMaxVisibleValueCount(5);
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

    public void setMonthly(){
        xAxisLabel.clear();
        base_call_back = RetrofitClient
                .getInstance().getApi().fetchMonthlyBoardGraphData(boardid,"Bearer " + _token);
        base_call_back.enqueue(new Callback<GraphDataResponseModel>() {
            @Override
            public void onResponse(Call<GraphDataResponseModel> call, Response<GraphDataResponseModel> response) {
                if(response.isSuccessful()){
                    GraphDataResponseModel dailygraphdata = response.body();
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
                    Toast.makeText(BuyBoardShareActivity.this,"Server error",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GraphDataResponseModel> call, Throwable t) {
            }
        });
    }

    public void setDaily(){
        xAxisLabel.clear();
        base_call_back = RetrofitClient
                .getInstance().getApi().fetchDailyBoardGraphData(boardid,"Bearer " + _token);
        base_call_back.enqueue(new Callback<GraphDataResponseModel>() {
            @Override
            public void onResponse(Call<GraphDataResponseModel> call, Response<GraphDataResponseModel> response) {
                if(response.isSuccessful()){
                    GraphDataResponseModel dailygraphdata = response.body();
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
                    Toast.makeText(BuyBoardShareActivity.this,"Server error",Toast.LENGTH_SHORT).show();
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
                .getInstance().getApi().fetchWeeklyBoardGraphData(boardid,"Bearer " + _token);
        base_call_back.enqueue(new Callback<GraphDataResponseModel>() {
            @Override
            public void onResponse(Call<GraphDataResponseModel> call, Response<GraphDataResponseModel> response) {
                if(response.isSuccessful()){
                    GraphDataResponseModel dailygraphdata = response.body();
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
                    Toast.makeText(BuyBoardShareActivity.this,"Server error",Toast.LENGTH_SHORT).show();
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

    public  void checktransactionType(View view){
        int radioId = radiotransactiontype.getCheckedRadioButtonId();
        radiobtn = findViewById(radioId);
        //Toast.makeText(RegistrationActivity.this,"Selected : " + radiobtn.getText().toString(),Toast.LENGTH_SHORT).show();
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
