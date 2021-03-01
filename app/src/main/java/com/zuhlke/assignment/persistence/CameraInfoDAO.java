package com.zuhlke.assignment.persistence;


import com.zuhlke.assignment.model.CameraItem;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CameraInfoDAO {

    @Insert(onConflict = REPLACE)
    void insertCameraItem(CameraItem cameraItem);

    @Query("SELECT * FROM cam_info")
    LiveData<List<CameraItem>> getCameras();
}
