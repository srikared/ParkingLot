package org.parking.main;

public class ParkingToken {
	
	private Slot slot;
	private String carNo;
	public ParkingToken(Slot slot, String carNo) {
		this.slot = slot;
		this.carNo = carNo;
	}
	
	public String getCarNo(){
		return carNo;
	}
	
	public Slot getSlot(){
		return slot;
	}
}
