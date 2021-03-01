package com.zuhlke.assignment.utils.network;

import android.util.Log;

import com.zuhlke.assignment.model.ApiInfo;
import com.zuhlke.assignment.model.CameraLocationsResponse;
import com.zuhlke.assignment.network.responses.ApiResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Response.class, Log.class})
public class APIResponseTest {
    @Before
    public void setup() {
        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void apiResponseErrorTest() {

        String error = "test error";
        ApiResponse response = new ApiResponse();

        ApiResponse.ApiErrorResponse apiResponse =
                (ApiResponse.ApiErrorResponse) response.create(new Throwable(error));
        assertEquals(apiResponse.getErrorMessage(), error);
    }

    @Test
    public void apiResponseSuccessErrorResponse() {
        String errorMsg = "Api key is invalid or expired.";
        Response retroResponse = PowerMockito.mock(Response.class);

        CameraLocationsResponse locationsResponse = new CameraLocationsResponse();
        ApiInfo info = new ApiInfo();
        CameraLocationsResponse spyResponse = spy(locationsResponse);
        ApiInfo spyApiInfo = spy(info);

        doReturn("not healthy").when(spyApiInfo).getStatus();
        doReturn(spyApiInfo).when(spyResponse).getApiInfo();
        doReturn(true).when(retroResponse).isSuccessful();
        doReturn(spyResponse).when(retroResponse).body();

        ApiResponse response = new ApiResponse();
        ApiResponse.ApiErrorResponse apiResponse =
                (ApiResponse.ApiErrorResponse) response.create(retroResponse);

        assertEquals(apiResponse.getErrorMessage(), errorMsg);
    }

    @Test
    public void apiResponseSuccessResponse() {
        String successfulMsg = "healthy";
        Response retroResponse = PowerMockito.mock(Response.class);

        CameraLocationsResponse locationsResponse = new CameraLocationsResponse();
        ApiInfo info = new ApiInfo();
        CameraLocationsResponse spyResponse = spy(locationsResponse);
        ApiInfo spyApiInfo = spy(info);

        doReturn(successfulMsg).when(spyApiInfo).getStatus();
        doReturn(spyApiInfo).when(spyResponse).getApiInfo();
        doReturn(true).when(retroResponse).isSuccessful();
        doReturn(spyResponse).when(retroResponse).body();

        ApiResponse response = new ApiResponse();
        Object apiResponse = response.create(retroResponse);
        assertTrue(apiResponse instanceof ApiResponse.ApiSuccessResponse);
    }

    @Test
    public void apiResponseEmptyResponse() {
        String successfulMsg = "healthy";
        Response retroResponse = PowerMockito.mock(Response.class);

        CameraLocationsResponse locationsResponse = new CameraLocationsResponse();
        ApiInfo info = new ApiInfo();
        CameraLocationsResponse spyResponse = spy(locationsResponse);
        ApiInfo spyApiInfo = spy(info);

        doReturn(successfulMsg).when(spyApiInfo).getStatus();
        doReturn(spyApiInfo).when(spyResponse).getApiInfo();
        doReturn(true).when(retroResponse).isSuccessful();
        doReturn(null).when(retroResponse).body();

        ApiResponse response = new ApiResponse();
        Object apiResponse = response.create(retroResponse);
        assertTrue(apiResponse instanceof ApiResponse.ApiEmptyResponse);
    }
}
