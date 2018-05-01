package com.example.ramezelbaroudy.gratefulreminder.gratefulPointsHome;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
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
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grateful);

        remindMe = (Button) this.findViewById(R.id.remindMe);
        showGratefulPoints = (Button) this.findViewById(R.id.viewGratefulPoints);
        gratefulSentence = (EditText) this.findViewById(R.id.gratefulSentence);

        // in order for the menu to be displayed in toobar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        // it will be injected on the variable with the annotation above
        // injecting gratefulPointsLocalDataSource so it can be used as singlton
        ((GratefulApplication) getApplication()).getAppComponent().inject(this);


        presenter = new GratefulPointsActivityPresenter(this, gratefulPointsRepository, AndroidSchedulers.mainThread());

        presenter.remindUserWithGratefulPoint();

        remindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addGratefulPoint(String.valueOf(gratefulSentence.getText()));
                alarmManager.cancel(pendingIntent);
                presenter.remindUserWithGratefulPoint();
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

    public void repeteNotification(List<GratefulPoint> content) {
        Intent intent = new Intent(this, RepeteNotification.class);
        intent.putExtra("gratefulString", (Serializable) content);
        intent.putExtra(RepeteNotification.NOTIFICATION_ID, 0);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, AlarmManager.INTERVAL_HALF_DAY, 1000, pendingIntent);

    }



}
