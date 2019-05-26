package com.example.ramezelbaroudy.gratefulreminder.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.ramezelbaroudy.gratefulreminder.R;
import com.example.ramezelbaroudy.gratefulreminder.gratefulPointsHome.GratefulPointsActivity;

import java.util.ArrayList;
import java.util.List;

// broadcast receiver register for system or application level events
public class RepeteNotification extends BroadcastReceiver {
    private static final String TAG = "dfas";
    public static String NOTIFICATION_ID = "notification-id";

    @Override
    public void onReceive(Context context, Intent intent) {
//        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        Bundle bundle = intent.getExtras();
        List<GratefulPoint> gratefulPoints  = bundle.getParcelableArrayList("gratefulString");

//        String selectedGratefulPoint = (String) intent.getSerializableExtra("gratefulString");
//        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        Notification notification = getNotification(gratefulPoints.get(0).getGratfulSentence(), context);
        manager.notify(1, notification);

    }

    public Notification getNotification(String content, Context context) {
      //  PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, GratefulPointsActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(context, GratefulPointsActivity.GRATEFUL_CHANNEL)
                .setContentTitle("Remember")
                .setSmallIcon(R.drawable.logo)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }
}
