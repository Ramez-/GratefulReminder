package com.example.ramezelbaroudy.gratefulreminder.showGratefulPoints;

import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;

import java.util.List;

public interface GratefulPointShowView {

    void displayGratefulPoints(List<GratefulPoint> gratefulPointsList);
    void displayNoBooks();
    void displayError();
    void refreshView();
}
