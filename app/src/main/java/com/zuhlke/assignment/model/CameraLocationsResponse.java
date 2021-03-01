package com.zuhlke.assignment.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CameraLocationsResponse{

	@SerializedName("api_info")
	private ApiInfo apiInfo;

	@SerializedName("items")
	private List<ItemsItem> items;

	public ApiInfo getApiInfo(){
		return apiInfo;
	}

	public List<ItemsItem> getItems(){
		return items;
	}
}