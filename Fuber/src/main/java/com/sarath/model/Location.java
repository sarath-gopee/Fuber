package com.sarath.model;

import javax.annotation.Resource;

@Resource
public class Location {
	
	private Double latitude;
	private Double longitude;
	
	public Location() {
		super();
	}
	public Location(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
