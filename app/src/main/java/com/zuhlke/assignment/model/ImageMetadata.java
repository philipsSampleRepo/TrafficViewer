package com.zuhlke.assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageMetadata implements Parcelable {

	@SerializedName("width")
	@Expose
	private int width;

	@SerializedName("height")
	@Expose
	private int height;

	@SerializedName("md5")
	@Expose
	private String md5;

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public String getMd5(){
		return md5;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.md5);
		dest.writeInt(this.height);
		dest.writeInt(this.width);
	}

	public void readFromParcel(Parcel source) {
		this.md5 = source.readString();
		this.height = source.readInt();
		this.width = source.readInt();
	}

	public ImageMetadata() {
	}

	protected ImageMetadata(Parcel in) {
		this.md5 = in.readString();
		this.height = in.readInt();
		this.width = in.readInt();
	}

	public static final Parcelable.Creator<ImageMetadata> CREATOR = new Parcelable.Creator<ImageMetadata>() {
		@Override
		public ImageMetadata createFromParcel(Parcel source) {
			return new ImageMetadata(source);
		}

		@Override
		public ImageMetadata[] newArray(int size) {
			return new ImageMetadata[size];
		}
	};
}