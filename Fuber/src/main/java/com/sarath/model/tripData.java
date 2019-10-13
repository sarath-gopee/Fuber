package com.sarath.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;


public class tripData {

	@Autowired
	private Location startPoint;
	@Autowired
	private Location endPoint;
	private Double tripFare;
	private Double tripDistance; // in kms
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Double totalTime; // in minutes
	
	public tripData(Location startPoint, Location endPoint) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		tripFare = 0.0;
		tripDistance = 0.0;
		totalTime = 0.0;
	}
	
	public Location getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Location startPoint) {
		this.startPoint = startPoint;
	}
	public Location getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Location endPoint) {
		this.endPoint = endPoint;
	}
	public Double getTripFare() {
		return tripFare;
	}
	public void setTripFare(Double tripFare) {
		this.tripFare = tripFare;
	}
	public double getTripDistance() {
		return tripDistance;
	}
	public void setTripDistance(Double tripDistance) {
		this.tripDistance = tripDistance;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Double totalTime) {
		this.totalTime = totalTime;
	}

}
