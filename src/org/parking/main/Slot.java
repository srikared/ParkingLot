package org.parking.main;

public class Slot {

	private int slotNumber;
	private boolean isFree;

	public Slot(int slotNumber, boolean isFree) {
		this.slotNumber = slotNumber;
		this.isFree = isFree;
	}

	public void setOccupied() {
		isFree = false;
	}

	public void setFree() {
		isFree = true;
	}
	public boolean isOccupied() {
		return isFree;
	}
}
