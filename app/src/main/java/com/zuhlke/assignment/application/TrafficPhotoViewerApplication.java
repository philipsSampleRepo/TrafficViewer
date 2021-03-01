package com.zuhlke.assignment.application;

import android.app.Application;

import com.zuhlke.assignment.di.app.AppComponent;
import com.zuhlke.assignment.di.app.AppModule;
import com.zuhlke.assignment.di.app.DaggerAppComponent;

public class TrafficPhotoViewerApplication extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.factory().create(new AppModule(getApplicationContext()));
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
