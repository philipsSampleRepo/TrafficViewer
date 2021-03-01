package com.zuhlke.assignment.repository;


import android.content.Context;
import android.util.Log;


import com.zuhlke.assignment.application.TrafficPhotoViewerApplication;
import com.zuhlke.assignment.di.app.AppComponent;
import com.zuhlke.assignment.model.CameraItem;
import com.zuhlke.assignment.model.CameraLocationsResponse;
import com.zuhlke.assignment.model.ItemsItem;
import com.zuhlke.assignment.network.APIService;
import com.zuhlke.assignment.network.responses.ApiResponse;
import com.zuhlke.assignment.persistence.CameraInfoDAO;
import com.zuhlke.assignment.persistence.CameraInfoDatabase;
import com.zuhlke.assignment.utils.AppExecutors;
import com.zuhlke.assignment.utils.NetworkBoundResource;
import com.zuhlke.assignment.utils.Resource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class Repository {

    private static final String TAG = "LocationRepository";

    private static Repository instance;
    private CameraInfoDAO cameraInfoDAO;

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    private Repository(Context context) {
        cameraInfoDAO = ((TrafficPhotoViewerApplication) context.getApplicationContext())
                .getAppComponent().getCameraInfoDatabase().getCamLocationDAO();
    }

    public LiveData<Resource<List<CameraItem>>> getCameraLocations(String dateAndTime,
                                                                   APIService apiService, AppExecutors executors) {
        return new NetworkBoundResource<List<CameraItem>, CameraLocationsResponse>(executors) {

            @Override
            protected void saveCallResult(@NonNull CameraLocationsResponse item) {
                if (item != null && item.getItems() != null && item.getItems().size() > 0) {
                    Log.d(TAG, "saveCallResult: " + item.getItems().size());
                    ItemsItem cameraObj = item.getItems().get(0);
                    List<CameraItem> cameraItemArrayList = cameraObj.getCameras();

                    if (cameraItemArrayList != null && cameraItemArrayList.size() > 0) {
                        for (CameraItem model : cameraItemArrayList) {
                            cameraInfoDAO.insertCameraItem(model);
                        }
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CameraItem> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CameraItem>> loadFromDb() {
                return cameraInfoDAO.getCameras();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CameraLocationsResponse>> createCall() {
                LiveData<ApiResponse<CameraLocationsResponse>> responseLiveData =
                        apiService.getAllLocations(dateAndTime);
                return responseLiveData;
            }
        }.getAsLiveData();
    }
}