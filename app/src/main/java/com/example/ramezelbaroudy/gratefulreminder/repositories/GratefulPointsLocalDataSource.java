package com.example.ramezelbaroudy.gratefulreminder.repositories;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;

import java.util.List;

import io.reactivex.Single;

public class GratefulPointsLocalDataSource implements GratefulPointsRepository {

    private final GratefulPointDatabase gratefulPointDatabase;

    public GratefulPointsLocalDataSource(Context context) {
        gratefulPointDatabase = Room.databaseBuilder(context, GratefulPointDatabase.class, "MyDatabase").build();
    }

    @Override
    public Single<List<GratefulPoint>> getGratefulPoints() {
        return Single.fromCallable(() -> gratefulPointDatabase.getGratefulPointDao().getAllGratefulPoints());
    }

    @Override
    public Single addGratefulPoint(String gratefulSentence) {
        return Single.fromCallable(() -> {
            gratefulPointDatabase.getGratefulPointDao().insert(new GratefulPoint(gratefulSentence));
            return "voala";
        });
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Single deleteGratefulPoint(String gratefulSentence) {
        return Single.fromCallable(() ->{
            gratefulPointDatabase.getGratefulPointDao().deleteGratefulPoint(gratefulSentence);
            return "Succed";
        });
    }
}
