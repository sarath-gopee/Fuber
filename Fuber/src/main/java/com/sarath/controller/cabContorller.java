package com.sarath.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sarath.DAO.cabDAO;
import com.sarath.exception.cabNotFoundException;
import com.sarath.model.cabData;
import com.sarath.services.cabFunctionServices;

@RestController
public class cabContorller {
	Logger logger = LogManager.getLogger(); 
	@Autowired
	private cabFunctionServices servicesObj;
	
	// to start the trip and set attributes accordingly
	@PostMapping(path="/start-trip")
	private ResponseEntity<Object> startRide(@RequestHeader(name = "cabid", required = true) Integer cabid) 
	throws cabNotFoundException{
		logger.info("entering startRide");
		
		String returnMsg = "Started the trip";
		cabData bookedCab = cabDAO.allCabs.get(cabid);
		
		servicesObj.startTheTrip(bookedCab);
		
		logger.info("exiting startRide");
		return new ResponseEntity<>(returnMsg,HttpStatus.OK);
		
	}
	
	
	//to end trip, set attributes accordingly and calculte total fare and distance
	@PostMapping(path="/end-trip")
	private ResponseEntity<Object> endRide(@RequestHeader(name = "cabid", required = true) Integer cabid)
	throws cabNotFoundException{
		logger.info("inside endRide");
		cabData releaseCar = cabDAO.allCabs.get(cabid);

		releaseCar = servicesObj.endTheTrip(releaseCar);
		servicesObj.releaseCarBooking(releaseCar);
		
		logger.info("exiting endRide");
		return new ResponseEntity<>(releaseCar,HttpStatus.OK);
	} 
	

	
	
}
