package com.example.ramezelbaroudy.gratefulreminder;

import android.content.Context;

import com.example.ramezelbaroudy.gratefulreminder.repositories.GratefulPointsLocalDataSource;
import com.example.ramezelbaroudy.gratefulreminder.repositories.GratefulPointsRepository;

import org.parceler.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    GratefulApplication appContext;

    public AppModule(GratefulApplication appContext) {
        this.appContext = appContext;
    }

    // need that method so that when dagger do the files have a method to return the context
    @Provides
    @Singleton
    Context getApplicationContext(){
        return appContext;
    }

    @Provides
    @Singleton
    GratefulPointsRepository providesGratefulPointsRepository(Context appContext){
        return new GratefulPointsLocalDataSource(appContext);
    }
}
