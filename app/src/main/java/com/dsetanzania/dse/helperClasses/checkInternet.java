package com.dsetanzania.dse.helperClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class checkInternet extends AsyncTask<Void, Void, String> {
    Context mContext;
    private InternetcheckInterface internetcheckInterface;

    public checkInternet(Context mContext, InternetcheckInterface internetcheckInterface) {
        this.mContext = mContext;
        this.internetcheckInterface = internetcheckInterface;
    }


    @Override
    protected String doInBackground(Void... voids) {

            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                Log.v("TAAAAG :", "Internet from Access");
                return "Access";
            } catch (IOException e) {
                Log.e("ERROR : ", "Error checking internet connection", e);
            }
        Log.v("TAAAAG :", "Internet from NoAccess");
        return "NoAccess";
    }

    @Override
    protected void onPostExecute(String result) {
        if (internetcheckInterface != null)
            internetcheckInterface.checkMethod(result);
    }
}
