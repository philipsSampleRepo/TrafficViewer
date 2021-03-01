package com.zuhlke.assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location implements Parcelable {

	@SerializedName("latitude")
	@Expose
	private double latitude;

	@SerializedName("longitude")
	@Expose
	private double longitude;

	public double getLatitude(){
		return latitude;
	}

	public double getLongitude(){
		return longitude;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(this.latitude);
		dest.writeDouble(this.longitude);
	}

	public void readFromParcel(Parcel source) {
		this.latitude = source.readDouble();
		this.longitude = source.readDouble();
	}

	public Location() {
	}

	protected Location(Parcel in) {
		this.latitude = in.readDouble();
		this.longitude = in.readDouble();
	}

	public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
		@Override
		public Location createFromParcel(Parcel source) {
			return new Location(source);
		}

		@Override
		public Location[] newArray(int size) {
			return new Location[size];
		}
	};
}