package org.parking.main;

public class ParkingToken {
	
	private Slot slot;
	private int carNo;
	public ParkingToken(Slot slot, int carNo) {
		this.slot = slot;
		this.carNo = carNo;
	}
	
	public int getCarNo(){
		return carNo;
	}
	
	public Slot getSlot(){
		return slot;
	}
}
