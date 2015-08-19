package org.parking.main;

public class ParkingOwner {

	private boolean isFull;
	
	public void notifyWhenParkingIsfull() {
		isFull = true;
	}

	public boolean isFull() {
		return isFull;
	}
	
	public boolean getIsFull () {
		return isFull;
	}

	public void setIsFull(boolean isFull) {
		this.isFull = isFull; 
	}
}
