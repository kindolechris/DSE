package com.dsetanzania.dse.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;
import android.widget.Toast;
import com.dsetanzania.dse.R;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.helperClasses.checkInternet;
import com.dsetanzania.dse.interfaces.InternetcheckInterface;
import com.dsetanzania.dse.models.transactions.buy.transaction.shares.board.BuyFromBoardResponseModel;
import com.dsetanzania.dse.storage.DbContract;
import com.dsetanzania.dse.storage.DbHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class BuyBoardShareActivity extends AppCompatActivity {

    String openingprice;
    String companyname;
    String boardid;
    Button buybtn;
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
    private SharedPreferences sharedPreferences;
    private ProgressBar buyBoardSharesLoader;
    private LinearLayout buyBoardSharesLayout;
    private LineChart linechart;
    final List<String> xAxisLabel = new ArrayList<>();

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
        txtvirtualmoney = (TextView) findViewById(R.id.txtvirtualmoney);
        buyBoardSharesLoader = (ProgressBar) findViewById(R.id.buyBoardSharesLoader);
        buyBoardSharesLayout = (LinearLayout) findViewById(R.id.buyBoardSharesLayout);
        graphviewLoader = (LinearLayout) findViewById(R.id.graphviewLayoutLoader);
        dbHelper = new DbHelper(this);
        parentLayout = findViewById(android.R.id.content);
        database = dbHelper.getWritableDatabase();

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
        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtAmountofshares.getText().toString().trim()) || TextUtils.isEmpty(txtprice.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Please place your quantity and price to buy",Toast.LENGTH_SHORT).show();
                    return;
                }

                checkInternet task = new checkInternet(getApplicationContext(), new InternetcheckInterface() {
                    @Override
                    public void checkMethod(String result) {

                        if(result == "Access"){
                            try {
                                buyBoardSharesLoader.setVisibility(View.VISIBLE);
                                buyBoardSharesLayout.setVisibility(View.INVISIBLE);
                                buyShares("buy",txtAmountofshares.getText().toString().trim(),txtprice.getText().toString().trim(),"board",boardid);
                            } catch (Exception e) {
                                Toast.makeText(BuyBoardShareActivity.this,"System error",Toast.LENGTH_SHORT).show();
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

        initGraph("w");

    }


    private String generateguid(){
        String  ticketId = UUID.randomUUID().toString();
        return ticketId.substring(0,3);
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }


    public static String getdate(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public void buyShares(String transactiontype,String shareAmount,String price,String transactionFrom,String boardId){
        Call<BuyFromBoardResponseModel> call = RetrofitClient
                .getInstance().getApi().buyShareFromBoard(transactiontype,shareAmount,price,transactionFrom,boardId,"Bearer " + _token);
        call.enqueue(new Callback<BuyFromBoardResponseModel>() {
            @Override
            public void onResponse(Call<BuyFromBoardResponseModel> call, Response<BuyFromBoardResponseModel> response) {
                BuyFromBoardResponseModel buyboardshareresponse = response.body();

                if (buyboardshareresponse.isSuccess()){
                    buyBoardSharesLoader.setVisibility(View.INVISIBLE);
                    buyBoardSharesLayout.setVisibility(View.VISIBLE);
                    dbHelper.updateUserLocalDatabase(String.valueOf(buyboardshareresponse.getData().getUser().getId()),buyboardshareresponse.getData().getUser().getStock(),buyboardshareresponse.getData().getUser().getBonds(),buyboardshareresponse.getData().getUser().getFirstname(),buyboardshareresponse.getData().getUser().getLastname(),buyboardshareresponse.getData().getUser().getTradername(),buyboardshareresponse.getData().getUser().getEmail(),buyboardshareresponse.getData().getUser().getYearOfStudy(),buyboardshareresponse.getData().getUser().getUniversity(),buyboardshareresponse.getData().getUser().getCoursename(),buyboardshareresponse.getData().getUser().getPhonenumber(),buyboardshareresponse.getData().getUser().getRole(),buyboardshareresponse.getData().getUser().getVirtualmoney(),buyboardshareresponse.getData().getUser().getGender(),DbContract.SYNC_STATUS_FAILED,database);
                    dbHelper.saveShareTransactionTolocalDatabase(buyboardshareresponse.getData().getPersonalsharesTransaction().getId(),String.valueOf(buyboardshareresponse.getData().getPersonalsharesTransaction().getSharesamount()),buyboardshareresponse.getData().getPersonalsharesTransaction().getCreatedAt(),buyboardshareresponse.getData().getPersonalsharesTransaction().getCompanyname(),buyboardshareresponse.getData().getPersonalsharesTransaction().getPrice(),buyboardshareresponse.getData().getPersonalsharesTransaction().getTransactiontype(),buyboardshareresponse.getData().getPersonalsharesTransaction().getStatus(),database);
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
        txtvirtualmoney.setText(String.valueOf(formatter.format(virtualmoney)));
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


}
