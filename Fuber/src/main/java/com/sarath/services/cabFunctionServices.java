package com.sarath.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarath.DAO.cabDAO;
import com.sarath.model.Location;
import com.sarath.model.cabData;

@Component
public class cabFunctionServices {
	@Autowired
	private userFunctionServices Obj;
	Logger logger = LogManager.getLogger();
	
	
	//to set cab destination as cust. destination and set current time as start time
	public void startTheTrip(cabData ridingCab) {
		logger.info("entering startTheTrip");
		Double cabLat = ridingCab.getUserData().getTripDetails().getStartPoint().getLatitude();
		Double cabLong = ridingCab.getUserData().getTripDetails().getStartPoint().getLongitude();
		Location cabCurrLoc = new Location(cabLat, cabLong);
		
		ridingCab.setCabCurrLocation(cabCurrLoc);
		ridingCab.getUserData().getTripDetails().setStartTime(LocalDateTime.now()); 
		
		Obj.updateCarData(ridingCab); // updating the model
		logger.info("exiting startTheTrip :");
	}
	
	//set the endtime and calculate total distance and fare
	public cabData endTheTrip(cabData ridingCab) {
		logger.info("entering endTheTrip");
		Double totalDistance, totalFare, pinkValue;Double rideTime; Long totalTime ;
		BigDecimal bd; 
		
		ridingCab.getUserData().getTripDetails().setEndTime(LocalDateTime.now());
		
		Location startloc = ridingCab.getUserData().getTripDetails().getStartPoint();
		Location endloc = ridingCab.getUserData().getTripDetails().getEndPoint();
		
		LocalDateTime startTime = ridingCab.getUserData().getTripDetails().getStartTime();
		LocalDateTime endTime =  LocalDateTime.now();
		totalDistance = Obj.calculateDistance(startloc, endloc);
		bd = new BigDecimal(totalDistance).setScale(2, RoundingMode.HALF_UP);// to round of the value to two decimals
		totalDistance = bd.doubleValue();
		totalTime = Duration.between(startTime, endTime).toMillis();
		rideTime = totalTime.doubleValue();
		rideTime = ((rideTime / 1000.0) / 60.0 ); // to convert from milliseconds to minutes
		bd = new BigDecimal(rideTime).setScale(2, RoundingMode.HALF_UP); // to round off decimals
		rideTime = bd.doubleValue();
		
		if(ridingCab.isPinkColor()) pinkValue = 5.0;
		else pinkValue = 0.0;
		
		totalFare = this.fareCalculator(totalDistance, rideTime.doubleValue(), pinkValue);
		bd = new BigDecimal(totalFare).setScale(2, RoundingMode.HALF_UP);
		totalFare = bd.doubleValue();// to round of to two decimals
		
		ridingCab.getUserData().getTripDetails().setTripDistance(totalDistance);
		ridingCab.getUserData().getTripDetails().setTripFare(totalFare);
		ridingCab.getUserData().getTripDetails().setTotalTime(rideTime);
		ridingCab.setCustLocation(new Location(0.0,0.0)); // clearing the travelled cust location

		logger.info("exiting endTheTrip");
		return ridingCab;
		
		
	}
	
	//to calculate the total fare
	private Double fareCalculator(Double dist, Double time, Double pinkVal) {
		Double fare;
		fare = (dist * 2 ) + ( time * 1 ) + pinkVal;
		
		return fare;		
	}
	
	
	// to release the car from booking settings
	public void releaseCarBooking(cabData releaseThis) {
		logger.info("inside releaseCarBooking : ");
		
		Location endloc = releaseThis.getUserData().getTripDetails().getEndPoint();
		
		//release the cab from booking
		releaseThis.setBookingStatus(false);
		releaseThis.setCarAvailability(true);
		
		//reset cab location
		releaseThis.setCabCurrLocation(endloc);
		
		cabDAO.allCabs.put(releaseThis.getCabID(),releaseThis); // update the settings to the data model
		logger.info("exiting releaseCarBooking  ");	
	}
	
}
