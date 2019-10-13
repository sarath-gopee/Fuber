package com.sarath.model;

import org.springframework.beans.factory.annotation.Autowired;

public class customer {

	private Integer custID;
	@Autowired
	private Location custCurrLocation;

	@Autowired
	private tripData tripDetails;
	
	
	public customer() {
		super();
		Location loc = new Location(0.0,0.0);
		this.custCurrLocation = loc;
		this.tripDetails = new tripData(loc,loc);
	}
	public Integer getCustID() {
		return custID;
	}
	public void setCustID(Integer custID) {
		this.custID = custID;
	}
	public Location getCustCurrLocation() {
		return custCurrLocation;
	}
	public void setCustCurrLocation(Location custCurrLocation) {
		this.custCurrLocation = custCurrLocation;
	}
	public tripData getTripDetails() {
		return tripDetails;
	}
	public void setTripDetails(tripData tripDetails) {
		this.tripDetails = tripDetails;
	}
	
}
