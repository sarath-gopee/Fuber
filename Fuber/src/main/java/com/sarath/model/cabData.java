package com.sarath.model;

import org.springframework.beans.factory.annotation.Autowired;

public class cabData {
	
	private Integer cabID;
	@Autowired
	private Location cabCurrLocation;
	@Autowired
	private customer userData;
	@Autowired
	private Location custLocation;
	private boolean pinkColor;
	private boolean carAvailability;
	private boolean bookingStatus;

	
	
	
	public cabData(Integer cabID, Location cabCurrLocation, customer userData, boolean pinkColor,
			boolean carAvailability, boolean bookingStatus) {
		super();
		this.cabID = cabID;
		this.cabCurrLocation = cabCurrLocation;
		this.userData = userData;
		this.pinkColor = pinkColor;
		this.carAvailability = carAvailability;
		this.bookingStatus = bookingStatus;
	}
	
	public Integer getCabID() {
		return cabID;
	}
	public void setCabID(Integer cabID) {
		this.cabID = cabID;
	}
	public Location getCabCurrLocation() {
		return cabCurrLocation;
	}
	public void setCabCurrLocation(Location cabCurrLocation) {
		this.cabCurrLocation = cabCurrLocation;
	}
	public customer getUserData() {
		return userData;
	}
	public void setUserData(customer userData) {
		this.userData = userData;
	}
	public Location getCustLocation() {
		return custLocation;
	}
	public void setCustLocation(Location custLocation) {
		this.custLocation = custLocation;
	}

	public boolean isPinkColor() {
		return pinkColor;
	}
	public void setPinkColor(boolean pinkColor) {
		this.pinkColor = pinkColor;
	}
	public boolean isCarAvailability() {
		return carAvailability;
	}
	public void setCarAvailability(boolean carAvailability) {
		this.carAvailability = carAvailability;
	}
	public boolean isBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(boolean bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	


}
