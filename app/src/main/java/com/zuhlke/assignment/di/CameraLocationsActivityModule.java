package com.zuhlke.assignment.di;

import com.zuhlke.assignment.espresso.LocationIdlingResource;
import com.zuhlke.assignment.viewmodels.CameraLocationsViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;

@Module
public class CameraLocationsActivityModule {

    private AppCompatActivity context;

    public CameraLocationsActivityModule(AppCompatActivity context) {
        this.context = context;
    }

    @Provides
    public AppCompatActivity providesLifeCycleOwner() {
        return context;
    }

    @Provides
    public LocationIdlingResource providesIdlingResource() {
        return new LocationIdlingResource();
    }

    @PerActivityScope
    @Provides
    public CameraLocationsViewModel provideViewModel() {
        return ViewModelProviders.of(context).get(CameraLocationsViewModel.class);
    }
}
