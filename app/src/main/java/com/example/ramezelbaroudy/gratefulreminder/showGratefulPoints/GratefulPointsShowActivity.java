package com.example.ramezelbaroudy.gratefulreminder.showGratefulPoints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.example.ramezelbaroudy.gratefulreminder.GratefulApplication;
import com.example.ramezelbaroudy.gratefulreminder.R;
import com.example.ramezelbaroudy.gratefulreminder.repositories.GratefulPointsRepository;
import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class GratefulPointsShowActivity extends AppCompatActivity implements GratefulPointShowView {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    GratefulPointShowPresenter gratefulPointShowPresenter;
    @Inject
    GratefulPointsRepository gratefulPointsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grateful_points_show);


        mRecyclerView = (RecyclerView) findViewById(R.id.gratefulSentenceRecycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ((GratefulApplication)getApplication()).getAppComponent().inject(this);

        gratefulPointShowPresenter = new GratefulPointShowPresenter(this, gratefulPointsRepository, AndroidSchedulers.mainThread());

        gratefulPointShowPresenter.loadGratefulPoints();


    }

    @Override
    public void displayGratefulPoints(List<GratefulPoint> gratefulPointsList) {
        ArrayList<String> dataString = new ArrayList<>();

        for(int i = 0; i < gratefulPointsList.size(); i++){
            dataString.add(gratefulPointsList.get(i).getGratfulSentence());
        }

        mAdapter = new GratefulPointsAdapter(this, dataString, gratefulPointsRepository, AndroidSchedulers.mainThread());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void displayNoBooks() {
        Intent notFoundIntent = new Intent(getApplicationContext(), NoGratefulPointsFound.class);
        startActivity(notFoundIntent);
    }

    @Override
    public void displayError() {
    }

    @Override
    public void refreshView() {
        Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gratefulPointShowPresenter.unsubscribe();
    }
}
