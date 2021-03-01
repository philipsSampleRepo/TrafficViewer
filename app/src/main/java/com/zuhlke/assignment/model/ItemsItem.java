package com.zuhlke.assignment.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ItemsItem{

	@SerializedName("cameras")
	private List<CameraItem> cameras;

	@SerializedName("timestamp")
	private String timestamp;

	public List<CameraItem> getCameras(){
		return cameras;
	}

	public String getTimestamp(){
		return timestamp;
	}
}