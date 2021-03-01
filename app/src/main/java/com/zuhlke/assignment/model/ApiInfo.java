package com.zuhlke.assignment.model;

import com.google.gson.annotations.SerializedName;

public class ApiInfo{

	@SerializedName("status")
	private String status;

	public String getStatus(){
		return status;
	}
}