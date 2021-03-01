package com.zuhlke.assignment.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zuhlke.assignment.model.ImageMetadata;
import com.zuhlke.assignment.model.Location;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;

public class DataConverter {

    @TypeConverter
    public String fromLocation(Location steps) {
        if (steps == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Location>() {
        }.getType();
        String json = gson.toJson(steps, type);
        return json;
    }

    @TypeConverter
    public Location toLocation(String steps) {
        if (steps == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Location>() {
        }.getType();
        Location location = gson.fromJson(steps, type);
        return location;
    }

    @TypeConverter
    public String fromImageMetadata(ImageMetadata imageMetadata) {
        if (imageMetadata == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ImageMetadata>() {
        }.getType();
        String json = gson.toJson(imageMetadata, type);
        return json;
    }

    @TypeConverter
    public ImageMetadata toImageMetadata(String imageMetadata) {
        if (imageMetadata == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ImageMetadata>() {
        }.getType();
        ImageMetadata metadata = gson.fromJson(imageMetadata, type);
        return metadata;
    }

}