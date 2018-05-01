package com.example.ramezelbaroudy.gratefulreminder.gratefulPointsHome;

import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;

import java.util.List;

public interface GratefulPointsView {

    void displayConfirmation();

    void showMissingInput();

    void sendRandomGratefulPointNotification(List<GratefulPoint> gratefulPoint);
}
