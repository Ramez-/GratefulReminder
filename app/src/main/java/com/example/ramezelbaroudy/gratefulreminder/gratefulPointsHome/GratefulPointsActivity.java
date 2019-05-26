package com.example.ramezelbaroudy.gratefulreminder.gratefulPointsHome;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.ramezelbaroudy.gratefulreminder.ConfirmationActivity;
import com.example.ramezelbaroudy.gratefulreminder.GratefulApplication;
import com.example.ramezelbaroudy.gratefulreminder.R;
import com.example.ramezelbaroudy.gratefulreminder.repositories.GratefulPointsRepository;
import com.example.ramezelbaroudy.gratefulreminder.showGratefulPoints.GratefulPointsShowActivity;
import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;
import com.example.ramezelbaroudy.gratefulreminder.utils.RepeteNotification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * GratefulPointsActivity is the view
 */
public class GratefulPointsActivity extends AppCompatActivity implements GratefulPointsView {

    private static final String TAG = "GratefulPointsActivity";
    GratefulPointsActivityPresenter presenter;

    private Button remindMe;
    private EditText gratefulSentence;
    private Button showGratefulPoints;
    @Inject
    GratefulPointsRepository gratefulPointsRepository;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    public static final String GRATEFUL_CHANNEL = "grateful";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grateful);

        remindMe = (Button) this.findViewById(R.id.remindMe);
        showGratefulPoints = (Button) this.findViewById(R.id.viewGratefulPoints);
        gratefulSentence = (EditText) this.findViewById(R.id.gratefulSentence);

        // in order for the menu to be displayed in toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        createNotificationChannel();
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        // it will be injected on the variable with the annotation above
        // injecting gratefulPointsLocalDataSource so it can be used as singlton
        ((GratefulApplication) getApplication()).getAppComponent().inject(this);


        presenter = new GratefulPointsActivityPresenter(this, gratefulPointsRepository, AndroidSchedulers.mainThread());

        presenter.remindUserWithGratefulPoint();

        remindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addGratefulPoint(String.valueOf(gratefulSentence.getText()));
                //alarmManager.cancel(pendingIntent);
                //presenter.remindUserWithGratefulPoint();
            }
        });

        showGratefulPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GratefulPointsActivity.this, GratefulPointsShowActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void displayConfirmation() {
        Intent confirmationIntent = new Intent(getApplicationContext(), ConfirmationActivity.class);
        startActivity(confirmationIntent);
    }

    @Override
    public void showMissingInput() {
        gratefulSentence.setError("Grateful sentence is missing");
    }

    @Override
    public void sendRandomGratefulPointNotification(List<GratefulPoint> gratefulPoint) {
        repeteNotification(gratefulPoint);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
//                Intent settings = new Intent(this, SettingsActivity.class);
//                startActivity(settings);
                break;
            case R.id.help:
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }


    // three dots
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void repeteNotification(List<GratefulPoint> gratefulPoints) {
        if(gratefulPoints != null && !gratefulPoints.isEmpty()) {
            Intent intent = new Intent(this, RepeteNotification.class);
            Random random = new Random();
            int randomNumber = random.nextInt(gratefulPoints.size());
            String randomString = gratefulPoints.get(randomNumber).getGratfulSentence();
//            intent.putExtra("gratefulString", randomString);
//            intent.putExtra(RepeteNotification.NOTIFICATION_ID, 1);
            ArrayList<String> gratefulSentences = new ArrayList<>();
            for (GratefulPoint gratefulPoint : gratefulPoints) {
                gratefulSentences.add(gratefulPoint.getGratfulSentence());
            }
            intent.putExtra("gratefulString", gratefulSentences);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, AlarmManager.INTERVAL_HALF_DAY, 1000, pendingIntent);

    }

    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = this.getResources().getString(R.string.channel_name);
            String description = this.getResources().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(GRATEFUL_CHANNEL, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
