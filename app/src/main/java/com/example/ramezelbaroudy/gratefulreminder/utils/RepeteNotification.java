package com.example.ramezelbaroudy.gratefulreminder.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.ramezelbaroudy.gratefulreminder.R;
import com.example.ramezelbaroudy.gratefulreminder.gratefulPointsHome.GratefulPointsActivity;

import java.util.List;
import java.util.Random;


public class RepeteNotification extends BroadcastReceiver {
    private static final String TAG = "dfas";
    public static String NOTIFICATION_ID = "notification-id";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        List<GratefulPoint> gratefulPoints = (List<GratefulPoint>) intent.getSerializableExtra("gratefulString");

        if(gratefulPoints.size() > 0){
            Random r = new Random();
            int randomNumber = r.nextInt(gratefulPoints.size());
            Notification notification = getNotification(gratefulPoints.get(randomNumber).getGratfulSentence(), context);
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
            nm.notify(id, notification);
        }

    }

    public Notification getNotification(String content, Context context) {

        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, GratefulPointsActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Remember");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentIntent(pi);
        return builder.build();

    }
}
