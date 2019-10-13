package com.sarath.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.util.List;
import com.sarath.DAO.cabDAO;
import com.sarath.exception.cabNotFoundException;
import com.sarath.model.Location;
import com.sarath.model.cabData;
import com.sarath.model.customer;

@Component
public class userFunctionServices {
	Logger logger = LogManager.getLogger();
	cabData nearestCar; Double minDist = 0.0, leftLat, rightLat,upLong, downLong, cabLat, cabLong;
	int testLatVal1,testLatVal2,testLongVal1,testLongVal2;
	
	// to find the cabs for the customer within his sphere of reach and intimate nearest cab
	public cabData findAvailableCabs(boolean pinkcolor, Location custLocation) throws ClassNotFoundException {
		logger.info("inside findAvailableCabs");
		
		List<cabData> availableCabs = new ArrayList<>();
		List<cabData> tempCabList = new ArrayList<>(cabDAO.allCabs.values());
		
		tempCabList.forEach( e -> 
			{
				if(checkWithinCustRange(e.getCabCurrLocation(),custLocation))	
					if(e.isCarAvailability()) {
						if(pinkcolor) { // if user  has pink preference
							if(e.isPinkColor()) { // checking whether pinkCab or not
								availableCabs.add(e);
							}
						}
						else availableCabs.add(e);
					}
			
			});
		
		logger.info("exiting findAvailableCabs");
			return findNearestCar(availableCabs, custLocation);
		}
	
	// to check whether the cars in the pool are within customer's sphere of reach
	private boolean checkWithinCustRange(Location cabLoc, Location custLoc) {
		logger.info("inside checkWithinCustRange");
		// to find cabs that are within sphere of reach of customer - 3 kms 
	boolean retVal = false;
		
	//setting the perimeter for lookup
		leftLat = custLoc.getLatitude() - 0.030000; // approx. 3 kms distance
		rightLat = custLoc.getLatitude() + 0.030000;
		upLong = custLoc.getLongitude() - 0.030000;
		downLong = custLoc.getLongitude() + 0.030000;
		
		cabLat = cabLoc.getLatitude();
		cabLong = cabLoc.getLongitude();
		
		//checking the presence of cab within perimeter
		testLatVal1 = cabLat.compareTo(leftLat);
		testLatVal2 = cabLat.compareTo(rightLat); 
		testLongVal1 = cabLong.compareTo(upLong);
		testLongVal2 = cabLong.compareTo(downLong);
		
		if(testLatVal1 > 0 && testLatVal2 < 0)
			if(testLongVal1 > 0 && testLongVal2 < 0)
				retVal = true;
		
		logger.info("exiting checkWithinCustRange");
		return retVal;
	}
	
	
	//to find the nearest cab inside the customer sphere of reach
	private cabData findNearestCar(List<cabData> listOfCars, Location userLocation) throws ClassNotFoundException {
		nearestCar = null;
		logger.info("inside findNearestCar");
		
		if(!listOfCars.isEmpty()) {
		listOfCars.forEach(l -> {

		Double distanceToUser = this.calculateDistance(userLocation, l.getCabCurrLocation());

		this.updateNearestCar(distanceToUser,l);
	});
	}
		minDist = 0.0; // resetting for the next request
	
		if(nearestCar == null) { throw new cabNotFoundException("Cab is not available");}
		
		logger.info("exiting findNearestCar");
		return nearestCar;
			
	
	}
	
	// to find distance between two gps coordinates
	public Double calculateDistance(Location l1, Location l2) {
		Double distanceCalculated, x, y;
		
		x = l1.getLatitude() - l2.getLatitude();
		y = l1.getLongitude() - l2.getLongitude();
		distanceCalculated = Math.hypot(x, y) * 108;// *108 to convert to kms
		
		return distanceCalculated;
		
	}

	// finds the nearest cab among nearby cabs
	public void updateNearestCar(Double dist, cabData car) {
		logger.info("inside updateNearestCar");
		
		if(minDist == 0 || minDist > dist) {
			minDist = dist;
			nearestCar = car;
	
		}
		logger.info("exiting updateNearestCar");
	}
	
	//to book the cab and update the attributes 
	public Double bookCab(customer cust, Integer cabId) {
		Double distancetocust; BigDecimal bd;
		logger.info("entering bookCab");
		
		cabData selectedCab = cabDAO.allCabs.get(cabId);
		if(selectedCab.isCarAvailability()) { // to ensure that the cab is not booked by another customer
		selectedCab.setCarAvailability(false);
		selectedCab.setBookingStatus(true);
		selectedCab.getUserData().setCustID(cust.getCustID());
		Location startingPoint = new Location(cust.getTripDetails().getStartPoint().getLatitude(),
												cust.getTripDetails().getStartPoint().getLongitude());
		Location endingPoint = new Location(cust.getTripDetails().getEndPoint().getLatitude(),
				cust.getTripDetails().getEndPoint().getLongitude());

		selectedCab.getUserData().getTripDetails().setStartPoint(startingPoint);
		selectedCab.getUserData().getTripDetails().setEndPoint(endingPoint);
		
		
		// as to reach the cab to the customer initial location
		selectedCab.setCustLocation(cust.getCustCurrLocation()); 
		

		distancetocust = this.calculateDistance(cust.getCustCurrLocation(),selectedCab.getCabCurrLocation());
		bd = new BigDecimal(distancetocust).setScale(2, RoundingMode.HALF_UP);
		
		this.updateCarData(selectedCab);
		
		logger.info("entering bookCab");
		return bd.doubleValue();
		
		
		}
		else throw new cabNotFoundException("Cab " + cabId);
	}
	
	
	//update the car data settings into the model.
	public void updateCarData(cabData car) throws cabNotFoundException{
		cabDAO.allCabs.put(car.getCabID(), car);
		
	}
	
	}
	
