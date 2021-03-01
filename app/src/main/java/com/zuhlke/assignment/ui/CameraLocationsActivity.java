package com.zuhlke.assignment.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.IdlingResource;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.zuhlke.assignment.R;
import com.zuhlke.assignment.application.TrafficPhotoViewerApplication;
import com.zuhlke.assignment.databinding.ActivityMainBinding;
import com.zuhlke.assignment.databinding.BottomSheetViewBinding;
import com.zuhlke.assignment.di.CameraLocationActivityComponent;
import com.zuhlke.assignment.di.CameraLocationsActivityModule;
import com.zuhlke.assignment.di.FragmentViewModule;
import com.zuhlke.assignment.di.app.AppComponent;
import com.zuhlke.assignment.espresso.LocationIdlingResource;
import com.zuhlke.assignment.model.CameraItem;

import com.zuhlke.assignment.viewmodels.CameraLocationsViewModel;

import java.util.List;

public class CameraLocationsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "CameraLocations";

    private CameraLocationsViewModel mCamLocationViewModel;
    private ActivityMainBinding activityMainBinding;
    private List<CameraItem> cameraItemList;
    private GoogleMap googleMap;
    private BottomSheetDialog dialog;
    private BottomSheetViewBinding bottomSheetBinding;
    private LocationIdlingResource idlingResource;
    private CameraLocationActivityComponent activityComponent;
    private AppComponent appComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        injectDependencies();
        loadLocations();
        setupMapUI();
    }

    private void injectDependencies() {
        appComponent = ((TrafficPhotoViewerApplication) getApplication())
                .getAppComponent();
        activityComponent = appComponent.getActivityComponentFactory()
                .create(new CameraLocationsActivityModule(this)
                        , new FragmentViewModule(this, this));
        activityComponent.injectActivity(this);
        mCamLocationViewModel = activityComponent.getViewModel();
        idlingResource = activityComponent.getLocationIdlingResource();
    }

    private void setupMapUI() {
        activityComponent.getSupportMapFragment();
    }

    private void loadLocations() {
        idlingResource.setIdleState(false);
        mCamLocationViewModel.getLocations().observe(this, listResource -> {
            if (listResource != null) {
                Log.d(TAG, "onChanged: status: " + listResource.status);

                if (listResource.data != null) {
                    switch (listResource.status) {
                        case LOADING: {
                            Log.d(TAG, "onChanged: DATA LOADING");
                            activityMainBinding.progressBar.setVisibility(View.VISIBLE);
                            break;
                        }

                        case ERROR: {
                            Log.e(TAG, "onChanged: cannot refresh the cache. ERROR");
                            Log.e(TAG, "onChanged: ERROR message: " + listResource.message);
                            Log.e(TAG, "onChanged: status: ERROR: " +
                                    listResource.data.size());

                            showSnackBar(activityMainBinding.getRoot(), listResource.message);
                            activityMainBinding.progressBar.setVisibility(View.INVISIBLE);
                            break;
                        }

                        case SUCCESS: {
                            Log.d(TAG, "onChanged: cache has been refreshed.");
                            Log.d(TAG, "onChanged: status: SUCCESS: " +
                                    listResource.data.size());
                            cameraItemList = listResource.data;
                            displayMarkers(cameraItemList);
                            activityMainBinding.progressBar.setVisibility(View.INVISIBLE);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        idlingResource.setIdleState(false);
        mCamLocationViewModel.getCamLocationsFromNetwork(appComponent.getFormattedDateString(),
                appComponent.getAPIService(), appComponent.getAppExecutors());
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        setMapUiSettings();
    }

    private void setMapUiSettings() {
        UiSettings uiSettings = this.googleMap.getUiSettings();
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
    }

    private void displayMarkers(List<CameraItem> cameraItemList) {
        idlingResource.setIdleState(false);
        if (googleMap != null) {
            for (CameraItem item : cameraItemList) {
                Marker m = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(item.getLocation().getLatitude(),
                                item.getLocation().getLongitude()))
                        .title(item.getCameraId()));
                m.setTag(item);
            }

            LatLngBounds.Builder bounds = LatLngBounds.builder();
            for (CameraItem item : cameraItemList) {
                bounds.include(new LatLng(item.getLocation().getLatitude(),
                        item.getLocation().getLongitude()));
            }
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20));
            idlingResource.setIdleState(true);
            displayLocationImage();
        }
    }

    private void displayLocationImage() {
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d(TAG, "onMarkerClick: " + marker.getTitle());
                showBottomSheet(marker);
                return false;
            }
        });
    }

    private void showBottomSheet(Marker marker) {
        bottomSheetBinding = BottomSheetViewBinding
                .inflate(getLayoutInflater(), null, false);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(bottomSheetBinding.getRoot());
        CameraItem cameraItem = (CameraItem) marker.getTag();
        bottomSheetBinding.timeTxt.setText(cameraItem.getTimestamp());
        loadImage(cameraItem.getImage(), dialog);
        setLandscapeView(dialog);
        dialog.show();
    }

    private void setLandscapeView(BottomSheetDialog dialog) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FrameLayout bottomSheet = (FrameLayout)
                    dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setPeekHeight(0);
        } else {
        }
    }

    private void loadImage(String image, BottomSheetDialog dialog) {
        idlingResource.setIdleState(false);
        bottomSheetBinding.progressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(image)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model, Target<Drawable> target,
                                                boolean isFirstResource) {
                        bottomSheetBinding.progressBar.setVisibility(View.GONE);
                        bottomSheetBinding.timeTxt
                                .setText(getString(R.string.image_load_error));
                        idlingResource.setIdleState(true);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        bottomSheetBinding.progressBar.setVisibility(View.GONE);
                        idlingResource.setIdleState(true);
                        return false;
                    }
                })
                .error(R.drawable.ic_launcher_foreground)
                .into(bottomSheetBinding.trafficImg);
    }

    private void showSnackBar(View view, String message) {
        Snackbar.make(view, message,
                Snackbar.LENGTH_LONG).show();
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        return idlingResource;
    }
}