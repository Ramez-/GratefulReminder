package com.example.ramezelbaroudy.gratefulreminder.repositories;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;

@Database(entities = {GratefulPoint.class}, version = 1)
public abstract class GratefulPointDatabase extends RoomDatabase {
    public abstract GratefulDao getGratefulPointDao(); //GratefulPointModel


}
