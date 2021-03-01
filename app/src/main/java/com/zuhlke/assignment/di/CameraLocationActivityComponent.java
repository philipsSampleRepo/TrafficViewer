package com.zuhlke.assignment.di;

import com.google.android.gms.maps.SupportMapFragment;
import com.zuhlke.assignment.espresso.LocationIdlingResource;
import com.zuhlke.assignment.ui.CameraLocationsActivity;
import com.zuhlke.assignment.viewmodels.CameraLocationsViewModel;

import dagger.Subcomponent;

@PerActivityScope
@Subcomponent(modules = {CameraLocationsActivityModule.class, FragmentViewModule.class})
public interface CameraLocationActivityComponent {

    CameraLocationsViewModel getViewModel();

    SupportMapFragment getSupportMapFragment();

    LocationIdlingResource getLocationIdlingResource();

    void injectActivity(CameraLocationsActivity cameraLocationsActivity);

    @Subcomponent.Factory
    interface Factory {
        CameraLocationActivityComponent create(CameraLocationsActivityModule activityModule,
                                               FragmentViewModule module);
    }
}
