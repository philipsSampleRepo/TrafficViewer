package com.zuhlke.assignment.network;


import com.zuhlke.assignment.model.CameraLocationsResponse;
import com.zuhlke.assignment.network.responses.ApiResponse;

import androidx.lifecycle.LiveData;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APIService {
    @GET("traffic-images")
    LiveData<ApiResponse<CameraLocationsResponse>> getAllLocations(@Query("date_time") String date_and_time);
}
