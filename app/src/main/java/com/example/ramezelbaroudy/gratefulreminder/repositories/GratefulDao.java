package com.example.ramezelbaroudy.gratefulreminder.repositories;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;

import java.util.List;

@Dao
public interface GratefulDao {

    @Insert
    void insert(GratefulPoint gp);

    // not sure the list thing will work
    @Query("select * from GratefulPoint")
    List<GratefulPoint> getAllGratefulPoints();

    @Query("select * from GratefulPoint where gratefulID = :id")
    GratefulPoint getGratefulPointByID(int id);

    @Query("SELECT COUNT(*) from GratefulPoint")
    int getNumberOfRecords();

    @Query("DELETE from GratefulPoint where gratefulString = :gratefulString")
    void deleteGratefulPoint(String gratefulString);

}
