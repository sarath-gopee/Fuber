package com.sarath.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import com.sarath.DAO.cabDAO;
import com.sarath.model.Location;
import com.sarath.model.cabData;
import com.sarath.model.customer;
import com.sun.tools.sjavac.Log;

@Component
public class userFunctionServices {
	Logger logger = LogManager.getLogger();
	cabData nearestCar; Double minDist = 0.0, leftLat, rightLat,upLong, downLong, cabLat, cabLong;
	int testLatVal1,testLatVal2,testLongVal1,testLongVal2;
	
	
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
			return findNearestCar(availableCabs, custLocation);
		}
	
	private boolean checkWithinCustRange(Location cabLoc, Location custLoc) {
		logger.info("inside checkWithinCustRange");
		// to find cabs that are within sphere of reach of customer - 3 kms 
	boolean retVal = false;
		
	//setting the perimeter for lookup
		leftLat = custLoc.getLatitude() - 0.030000;
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
	
	if(nearestCar == null) { throw new ClassNotFoundException();}
	return nearestCar;
			
	
	}
	
	// to find distance between two gps coordinates
	public Double calculateDistance(Location l1, Location l2) {
		Double distanceCalculated, x, y;
		
		x = l1.getLatitude() - l2.getLatitude();
		y = l1.getLongitude() - l2.getLongitude();
		distanceCalculated = Math.hypot(x, y) * 108;// *108 to convert to kms
		
		logger.info("distanceCalculated" +distanceCalculated);
		
		return distanceCalculated;
		
	}

	// finds the nearest cab among nearby cabs
	public void updateNearestCar(Double dist, cabData car) {
		logger.info("inside updateNearestCar");
		
		if(minDist == 0 || minDist > dist) {
			minDist = dist;
			nearestCar = car;
	
		}
	}
	
	public String bookCab(customer cust, Integer cabId) {
		Double distancetocust;
		cabData selectedCab = cabDAO.allCabs.get(cabId);
		selectedCab.setCarAvailability(false);
		selectedCab.setBookingStatus(true);

		
		// as to reach the cab to the customer initial location
		selectedCab.setCustLocation(cust.getCustCurrLocation()); 
		

		distancetocust = this.calculateDistance(cust.getCustCurrLocation(),selectedCab.getCabCurrLocation());
		DecimalFormat f = new DecimalFormat("##.###");
	     
		return f.format(distancetocust);
	}
	
	}
	
