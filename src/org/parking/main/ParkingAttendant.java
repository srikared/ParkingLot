package org.parking.main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.parking.main.exception.ParkingLotIsFullException;

public class ParkingAttendant {
	
	public ParkingAttendant(Set<ParkingArea> parkingAreas) {
		super();
		this.parkingAreas = parkingAreas;
	}


	private Set<ParkingArea> parkingAreas =  new HashSet<>(); 
	
	public void addParkingArea(ParkingArea parkingArea){
		parkingAreas.add(parkingArea);
	}
	
	public ParkingArea getParkingAreaNotFull( Set<ParkingArea> parkingAreas) throws AllParkingAreasFullException{
		for(ParkingArea parkingArea : parkingAreas){
			if(parkingArea.getEmptySpace() != 0){
				return parkingArea;
			}
		}
			throw new AllParkingAreasFullException();
	}
	
	
	public ParkingToken parkCar(String carNo) throws FileNotFoundException, UnsupportedEncodingException, ParkingLotIsFullException, AllParkingAreasFullException{ 
		return(getParkingAreaNotFull(parkingAreas).parkCar(carNo));
	}
	
}
