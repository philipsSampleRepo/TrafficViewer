package com.zuhlke.assignment.di;

import android.content.Context;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.zuhlke.assignment.R;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class FragmentViewModule {

    AppCompatActivity context;
    OnMapReadyCallback onMapReadyCallback;

    public FragmentViewModule(AppCompatActivity context, OnMapReadyCallback mapReadyCallback) {
        this.context = context;
        this.onMapReadyCallback = mapReadyCallback;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    SupportMapFragment provideFragmentView() {
        SupportMapFragment mapFragment = (SupportMapFragment) context.getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(onMapReadyCallback);
        return mapFragment;
    }
}
