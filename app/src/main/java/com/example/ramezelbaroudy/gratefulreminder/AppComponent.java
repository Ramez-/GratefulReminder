package com.example.ramezelbaroudy.gratefulreminder;


import com.example.ramezelbaroudy.gratefulreminder.gratefulPointsHome.GratefulPointsActivity;
import com.example.ramezelbaroudy.gratefulreminder.showGratefulPoints.GratefulPointsAdapter;
import com.example.ramezelbaroudy.gratefulreminder.showGratefulPoints.GratefulPointsShowActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component( modules = AppModule.class)
public interface AppComponent {

    // I am injecting gratefulApplication because it will provide me with the context that I will need in other injection

    void inject(GratefulApplication gratefulApplication);
    void inject(GratefulPointsShowActivity gratefulPointShowView);
    void inject(GratefulPointsActivity gratefulPointShowView);
    void inject(GratefulPointsAdapter gratefulPointsAdapter);
}
