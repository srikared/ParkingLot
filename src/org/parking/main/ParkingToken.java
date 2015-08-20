package org.parking.main;

public class ParkingToken {
	
	private Slot slot;
	private String carNo;
	private ParkingArea parkingArea;
	
	public ParkingToken(Slot slot, String carNo, ParkingArea parkingArea) {
		this.slot = slot;
		this.carNo = carNo;
		this.parkingArea = parkingArea;
	}
	
	public String getCarNo(){
		return carNo;
	}
	
	public Slot getSlot(){
		return slot;
	}
	
	
	
}
