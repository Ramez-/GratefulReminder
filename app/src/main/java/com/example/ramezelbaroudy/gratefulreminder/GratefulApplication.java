package com.example.ramezelbaroudy.gratefulreminder;

import android.app.Application;

public class GratefulApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();

    }


    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void initDagger() {

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        appComponent.inject(this );

    }
}
