package com.dsetanzania.dse.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.dsetanzania.dse.R;

public class AboutUsActivity extends AppCompatActivity {

    LinearLayout txtwebsite;
    LinearLayout txtemail;
    LinearLayout txtterms;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolBarElevation(7);

        txtwebsite = (LinearLayout) findViewById(R.id.btnWebsiteLink);
        txtemail = (LinearLayout) findViewById(R.id.btnEmailLink);
        txtterms = (LinearLayout) findViewById(R.id.btnTermsAndConditions);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("About");
        }


        txtwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dse.co.tz/"));
                startActivity(browserIntent);

            }
        });

        txtterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dse.co.tz/content/dse-rules-regulations"));
                startActivity(browserIntent);
            }
        });

        txtemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String _email = "info@dse.co.tz";
                String msg = "message body";
                Intent sendEmail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+ _email)); // enter an email id here
                sendEmail.putExtra(Intent.EXTRA_SUBJECT, "subject"); //subject of the email
                sendEmail.putExtra(Intent.EXTRA_TEXT, msg); //body of the email
                startActivity(Intent.createChooser(sendEmail, "Choose an email client from..."));
        }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void toolBarElevation(int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(size);
        }else{
            ///TODO for compatiability
        }
    }
}
