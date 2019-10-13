package com.sarath.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sarath.model.cabData;
import com.sarath.model.customer;
import com.sarath.exception.cabNotFoundException;
import com.sarath.model.Location;
import com.sarath.services.userFunctionServices;



@RestController
public class custController {
	Logger logger = LogManager.getLogger();
	@Autowired
	private userFunctionServices cabServicesObj;
	
	// to find cars near by a customer
	@GetMapping("/cabs-available/{pinkCab}") 
	private ResponseEntity<Object> cabsAvailable(@PathVariable boolean pinkCab,
										@RequestParam(value="latitude", required=true) Double locationLat,
										@RequestParam(value="longitude", required=true) Double locationLong)
	throws IllegalArgumentException, ClassNotFoundException, cabNotFoundException{
		logger.info("entering cabsAvailable");
		
		cabData cabSelected = cabServicesObj.findAvailableCabs(pinkCab, new Location(locationLat,locationLong));
		
		logger.info("exiting cabsAvailable");
		return new ResponseEntity<>(cabSelected,HttpStatus.OK);
	}
	
	// to book the nearest cab intimated. Gives how far the nearest cab is
	@PostMapping(path = "/book-cab", consumes = "application/json")
	private ResponseEntity<Object> bookTheCab(@RequestBody customer cust,
			@RequestHeader(name = "cabid", required = true) Integer cabid) 
	throws cabNotFoundException{
		logger.info("entering bookTheCab");
		
		Double distanceToCust =  cabServicesObj.bookCab(cust,cabid);
		String returnMsg = "Cab Id " + cabid + " has been booked and is enroute," + distanceToCust + " kms away";
		
		logger.info("exiting bookTheCab");
		return new ResponseEntity<>(returnMsg,HttpStatus.OK);
	}
	
	
}
