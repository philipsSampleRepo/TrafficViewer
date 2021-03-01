package com.zuhlke.assignment.viewmodels;

import android.app.Application;
import android.util.Log;


import com.zuhlke.assignment.application.TrafficPhotoViewerApplication;
import com.zuhlke.assignment.di.app.AppComponent;
import com.zuhlke.assignment.model.CameraItem;
import com.zuhlke.assignment.network.APIService;
import com.zuhlke.assignment.repository.Repository;
import com.zuhlke.assignment.utils.AppExecutors;
import com.zuhlke.assignment.utils.Resource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;


public class CameraLocationsViewModel extends AndroidViewModel {

    private static final String TAG = CameraLocationsViewModel.class.getSimpleName();

    public static final String QUERY_EXHAUSTED = "No more results.";

    public enum ViewState {LOCATIONS}

    private MutableLiveData<ViewState> viewState;
    private MediatorLiveData<Resource<List<CameraItem>>> locations = new MediatorLiveData<>();
    private Repository camLocationRepository;

    private boolean cancelRequest;

    public CameraLocationsViewModel(@NonNull Application application) {
        super(application);
        camLocationRepository = ((TrafficPhotoViewerApplication) application)
                .getAppComponent().getRepository();
        init();
    }

    private void init() {
        if (viewState == null) {
            viewState = new MutableLiveData<>();
            viewState.setValue(ViewState.LOCATIONS);
        }
    }

    public LiveData<Resource<List<CameraItem>>> getLocations() {
        return locations;
    }


    public void getCamLocationsFromNetwork(String dateAndTime, APIService apiService,
                                           AppExecutors executors) {
        cancelRequest = false;
        viewState.setValue(ViewState.LOCATIONS);

        final LiveData<Resource<List<CameraItem>>> repositorySource = camLocationRepository
                .getCameraLocations(dateAndTime, apiService, executors);
        locations.addSource(repositorySource, listResource -> {
            if (!cancelRequest) {
                if (listResource != null) {
                    if (listResource.status == Resource.Status.SUCCESS) {
                        Log.d(TAG, "onChanged: " + listResource.data);

                        if (listResource.data != null) {
                            if (listResource.data.size() == 0) {
                                Log.d(TAG, "onChanged: query is exhausted...");
                                locations.setValue(
                                        new Resource<>(
                                                Resource.Status.ERROR,
                                                listResource.data,
                                                QUERY_EXHAUSTED
                                        )
                                );
                            }
                        }
                        locations.removeSource(repositorySource);
                    } else if (listResource.status == Resource.Status.ERROR) {
                        locations.removeSource(repositorySource);
                    }
                    locations.setValue(listResource);
                } else {
                    locations.removeSource(repositorySource);
                }
            } else {
                locations.removeSource(repositorySource);
            }
        });
    }
}
