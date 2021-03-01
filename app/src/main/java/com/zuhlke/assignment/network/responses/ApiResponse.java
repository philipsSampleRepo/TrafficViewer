package com.zuhlke.assignment.network.responses;

import android.util.Log;


import com.zuhlke.assignment.model.CameraLocationsResponse;

import java.io.IOException;

import retrofit2.Response;


public class ApiResponse<T> {

    private static final String TAG = "ApiResponse";

    public ApiResponse<T> create(Throwable error) {
        if (error != null) {
            Log.e(TAG, error.getMessage());
        }
        return new ApiErrorResponse<>(error.getMessage().equals("") ? "Unknown error\nCheck network connection" : error.getMessage());
    }

    public ApiResponse<T> create(Response<T> response) {

        if (response.isSuccessful()) {
            T body = response.body();

            if (body instanceof CameraLocationsResponse) {
                if (!((CameraLocationsResponse) body).getApiInfo().getStatus().equals("healthy")) {
                    String errorMsg = "Api key is invalid or expired.";
                    return new ApiErrorResponse<>(errorMsg);
                }
            }
            // 204 is empty response
            if (body == null || response.code() == 204) {
                return new ApiEmptyResponse<>();
            } else {
                return new ApiSuccessResponse<>(body);
            }
        } else {
            String errorMsg = "";
            try {
                errorMsg = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
                errorMsg = response.message();
            }
            return new ApiErrorResponse<>(errorMsg);
        }
    }

    /**
     * Generic success response from api
     *
     * @param <T>
     */
    public class ApiSuccessResponse<T> extends ApiResponse<T> {

        private T body;

        ApiSuccessResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }

    }

    /**
     * Generic Error response from API
     *
     * @param <T>
     */
    public class ApiErrorResponse<T> extends ApiResponse<T> {

        private String errorMessage;

        ApiErrorResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

    }


    /**
     * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
     */
    public class ApiEmptyResponse<T> extends ApiResponse<T> {
    }

}


