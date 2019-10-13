package com.sarath.controller;


import java.awt.PageAttributes.MediaType;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sarath.model.cabData;
import com.sarath.model.customer;
import com.sarath.model.Location;
import com.sarath.services.userFunctionServices;

import jdk.internal.org.jline.utils.Log;

@RestController
public class custController {
	Logger logger = LogManager.getLogger();
	@Autowired
	private userFunctionServices cabServicesObj;
	
	@GetMapping("/cabs-available/{pinkCab}") 
	private cabData cabsAvailable(@PathVariable boolean pinkCab,
										@RequestParam(value="latitude", required=true) Double locationLat,
										@RequestParam(value="longitude", required=true) Double locationLong)
	throws IllegalArgumentException, ClassNotFoundException{
		
		 
		cabData cabSelected = cabServicesObj.findAvailableCabs(pinkCab, new Location(locationLat,locationLong));
		
		return cabSelected;
	}
	
	@PostMapping(path = "/book-cab", consumes = "application/json")
	private ResponseEntity bookTheCab(@RequestBody customer cust,
			@RequestHeader(name = "cabid", required = true) Integer cabid) {
		
		String distanceToCust =  cabServicesObj.bookCab(cust,cabid);
		String returnMsg = "Cab Id " + cabid + " has been booked and is enroute," + distanceToCust + " kms away";
		return new ResponseEntity<>(returnMsg,HttpStatus.OK);
	}
	
	
}
