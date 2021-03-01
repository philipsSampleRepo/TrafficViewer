package com.zuhlke.assignment.di.app;

import android.content.Context;

import com.zuhlke.assignment.persistence.CameraInfoDatabase;
import com.zuhlke.assignment.repository.Repository;
import com.zuhlke.assignment.utils.AppExecutors;
import com.zuhlke.assignment.utils.DateFormatter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    String provideFormattedDateString(DateFormatter formatter) {
        return formatter.getFormatterDate();
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return this.context;
    }

    @Provides
    @Singleton
    CameraInfoDatabase provideCameraInfoDatabase(Context context) {
        return CameraInfoDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    Repository provideRepository() {
        return Repository.getInstance(context);
    }
}
