package com.zuhlke.assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cam_info")
public class CameraItem implements Parcelable {

	@SerializedName("image")
	@Expose
	@ColumnInfo(name = "image_name")
	private String image;

	@SerializedName("image_metadata")
	@Expose
	@ColumnInfo(name = "image_metadata")
	private ImageMetadata imageMetadata;

	@SerializedName("camera_id")
	@Expose
	@PrimaryKey
	@NonNull
	private String cameraId;

	@SerializedName("location")
	@Expose
	@ColumnInfo(name = "location")
	private Location location;

	@SerializedName("timestamp")
	@Expose
	@ColumnInfo(name = "timestamp")
	private String timestamp;

	public String getImage(){
		return image;
	}

	public ImageMetadata getImageMetadata(){
		return imageMetadata;
	}

	public String getCameraId(){
		return cameraId;
	}

	public Location getLocation(){
		return location;
	}

	public String getTimestamp(){
		return timestamp;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setImageMetadata(ImageMetadata imageMetadata) {
		this.imageMetadata = imageMetadata;
	}

	public void setCameraId(@NonNull String cameraId) {
		this.cameraId = cameraId;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.image);
		dest.writeParcelable(this.imageMetadata, flags);
		dest.writeString(this.cameraId);
		dest.writeParcelable(this.location, flags);
		dest.writeString(this.timestamp);
	}

	public void readFromParcel(Parcel source) {
		this.image = source.readString();
		this.imageMetadata = source.readParcelable(ImageMetadata.class.getClassLoader());
		this.cameraId = source.readString();
		this.location = source.readParcelable(Location.class.getClassLoader());
		this.timestamp = source.readString();
	}

	public CameraItem() {
	}

	protected CameraItem(Parcel in) {
		this.image = in.readString();
		this.imageMetadata = in.readParcelable(ImageMetadata.class.getClassLoader());
		this.cameraId = in.readString();
		this.location = in.readParcelable(Location.class.getClassLoader());
		this.timestamp = in.readString();
	}

	public static final Parcelable.Creator<CameraItem> CREATOR = new Parcelable.Creator<CameraItem>() {
		@Override
		public CameraItem createFromParcel(Parcel source) {
			return new CameraItem(source);
		}

		@Override
		public CameraItem[] newArray(int size) {
			return new CameraItem[size];
		}
	};
}