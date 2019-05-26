package com.example.ramezelbaroudy.gratefulreminder.utils;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@Entity(tableName = "GratefulPoint")
public class GratefulPoint implements Parcelable {

    @ColumnInfo(name = "gratefulString")
    private String gratfulSentence;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "gratefulID")
    private int ID;

    public GratefulPoint(Parcel in) {
        this.ID = in.readInt();
        this.gratfulSentence = in.readString();
    }

    public GratefulPoint(String gratfulSentence) {
        this.gratfulSentence = gratfulSentence;
    }

    public static final Creator<GratefulPoint> CREATOR = new Creator<GratefulPoint>() {
        @Override
        public GratefulPoint createFromParcel(Parcel in) {
            return new GratefulPoint(in);
        }

        @Override
        public GratefulPoint[] newArray(int size) {
            return new GratefulPoint[size];
        }
    };

    public String getGratfulSentence() {
        return gratfulSentence;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(gratfulSentence);
    }
}
