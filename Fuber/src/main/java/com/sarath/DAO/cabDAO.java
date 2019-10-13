package com.sarath.DAO;
import com.sarath.model.Location;
import com.sarath.model.cabData;
import com.sarath.model.customer;
import com.sarath.model.tripData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class cabDAO {
	// to initialise the data - cab locations.
	
	public static HashMap <Integer,cabData> allCabs = new HashMap<>(); 
	
	static {
		cabData cab1 = new cabData(101, new Location(12.882105,77.647504),new customer(),
				false,true, false);
		
		cabData cab2 = new cabData(102, new Location(12.984105,77.657504),new customer(),
				true,true, false);
		
		cabData cab3 = new cabData(103, new Location(12.884105,77.687504),new customer(),
				true,true, false);
		
		cabData cab4 = new cabData(104, new Location(12.885105,77.642504),new customer(),
				false,true, false);
		
		cabData cab5 = new cabData(105, new Location(12.784105,77.647504),new customer(),
				true,true, false);
		
		cabData cab6 = new cabData(106, new Location(12.284105,77.638504),new customer(),
				false,true, false);
		
		cabData cab7 = new cabData(107, new Location(12.484105,77.667504),new customer(),
				false,true, false);
		
		cabData cab8 = new cabData(108, new Location(12.384105,77.601504),new customer(),
				false,true, false);
		
		cabData cab9 = new cabData(109, new Location(12.804105,77.647504),new customer(),
				false,true, false);
		
		cabData cab10 = new cabData(110, new Location(12.884110,77.647600),new customer(),
				true,true, false);
		
		allCabs.put(cab1.getCabID(), cab1);
		allCabs.put(cab2.getCabID(), cab2);
		allCabs.put(cab3.getCabID(), cab3);
		allCabs.put(cab4.getCabID(), cab4);
		allCabs.put(cab5.getCabID(), cab5);
		allCabs.put(cab6.getCabID(), cab6);
		allCabs.put(cab7.getCabID(), cab7);
		allCabs.put(cab8.getCabID(), cab8);
		allCabs.put(cab9.getCabID(), cab9);
		allCabs.put(cab10.getCabID(), cab10);
	}
	
	//returns the master list of all cabs.
	  public HashMap<Integer, cabData> getAllCabs() 
	    {
	        return allCabs;
	    }
	
}
