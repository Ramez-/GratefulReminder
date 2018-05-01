package com.example.ramezelbaroudy.gratefulreminder.repositories;

import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;

import java.util.List;

import io.reactivex.Single;

public interface GratefulPointsRepository {


    Single<List<GratefulPoint>> getGratefulPoints();
    Single addGratefulPoint(String gratefulSentence);
    boolean isEmpty();
    Single deleteGratefulPoint(String gratefulPointSentece);

}
