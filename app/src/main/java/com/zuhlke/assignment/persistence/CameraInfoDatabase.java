package com.zuhlke.assignment.persistence;


import android.content.Context;


import com.zuhlke.assignment.model.CameraItem;

import javax.inject.Inject;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {CameraItem.class}, version = 1,exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class CameraInfoDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "cam_locations_db";

    private static CameraInfoDatabase instance;

    public static CameraInfoDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    CameraInfoDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract CameraInfoDAO getCamLocationDAO();

}
