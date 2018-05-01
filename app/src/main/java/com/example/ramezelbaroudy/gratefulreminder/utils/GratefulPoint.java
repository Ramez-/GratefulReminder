package com.example.ramezelbaroudy.gratefulreminder.utils;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "GratefulPoint")
public class GratefulPoint implements Serializable {

    @ColumnInfo(name = "gratefulString")
    private String gratfulSentence;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "gratefulID")
    private int ID;


    public GratefulPoint(String gratfulSentence) {
        this.gratfulSentence = gratfulSentence;
    }

    public String getGratfulSentence() {
        return gratfulSentence;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
