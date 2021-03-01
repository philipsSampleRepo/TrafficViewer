package com.zuhlke.assignment.di.app;

import android.content.Context;

import com.zuhlke.assignment.di.CameraLocationActivityComponent;
import com.zuhlke.assignment.network.APIService;
import com.zuhlke.assignment.network.di.NetworkModule;
import com.zuhlke.assignment.persistence.CameraInfoDatabase;
import com.zuhlke.assignment.repository.Repository;
import com.zuhlke.assignment.utils.AppExecutors;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    APIService getAPIService();

    String getFormattedDateString();

    AppExecutors getAppExecutors();

    CameraInfoDatabase getCameraInfoDatabase();

    Repository getRepository();

    CameraLocationActivityComponent.Factory getActivityComponentFactory();

    @Component.Factory
    interface Factory {
        AppComponent create(AppModule module);
    }
}

