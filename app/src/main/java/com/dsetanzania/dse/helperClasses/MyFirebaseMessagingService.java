package com.dsetanzania.dse.helperClasses;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.api.RetrofitClient;
import com.dsetanzania.dse.models.UserDataResponseModel;
import com.dsetanzania.dse.storage.DbContract;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import static android.icu.text.DisplayContext.LENGTH_SHORT;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.dsetanzania.dse.activities.LoginActivity.sharedPrefrences;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


  /* @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Channel_ID","Messages",importance);
            channel.setDescription("SyncMessage");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = new NotificationCompat.Builder(this, "Channel_ID")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setSound(path)
                .setContentText(remoteMessage.getNotification().getBody())
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, notification);

        super.onMessageReceived(remoteMessage);
    }*/

    @Override
    public void onNewToken(String s) {
        Log.e("NEW_TOKEN", s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("MESSAGE RECEIVED", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("MESSAGE RECEIVED", "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
            } else {
                // Handle message within 10 seconds
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                public void run() {
//                    Toast.makeText(getBaseContext(), "Notification received", Toast.LENGTH_LONG).show();
//                }
//            });

            Intent broadcast = new Intent();
            broadcast.setAction("OPEN_NEW_ACTIVITY");
            sendBroadcast(broadcast);

            //Toast.makeText(this,remoteMessage.getNotification().getBody(),Toast.LENGTH_LONG).show();
            Log.d("MESSAGE RECEIVED", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}
