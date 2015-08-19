package org.parking.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.parking.main.exception.CarNotPresentException;
import org.parking.main.exception.TokenNullException;

public class ParkingArea {

	private int totalCapacity;
	private int filledCapacity;
	private Slot[] slots;
	private int freeCarSlot;
	private Set<String> setOfCarNos = new HashSet<String>();

	public ParkingArea(int totalCapacity) {
		this.totalCapacity = totalCapacity;
		this.filledCapacity = 0;
		slots = new Slot[totalCapacity];
		for (int currentIndex = 0; currentIndex < totalCapacity; currentIndex++) {
			slots[currentIndex] = new Slot(currentIndex, true);
		}
		freeCarSlot = 0;
	}

	public ParkingToken parkCar(String carNo) throws ParkingLotIsFullException {
		if(!isPossibleToParkCar()) {
			throw new ParkingLotIsFullException(); 
		}
		if (isPossibleToParkCar() && !setOfCarNos.contains(carNo)) {
			filledCapacity++;
			slots[freeCarSlot].setOccupied();
			setNewFreeSlot();
			setOfCarNos.add(carNo);
			return new ParkingToken(slots[freeCarSlot], carNo);
		}
		
		return null;
	}

	private void setNewFreeSlot() {
		for (int currentIndex = 0; currentIndex < totalCapacity; currentIndex++) {
			if (!slots[currentIndex].isOccupied()) {
				freeCarSlot = currentIndex;
			}
		}

	}

	private boolean isPossibleToParkCar() {
		return totalCapacity > filledCapacity;
	}

	public String unParkCar(ParkingToken token) throws CarNotPresentException {
		if(!setOfCarNos.contains(token.getCarNo())) {
			throw new CarNotPresentException();
		}
		token.getSlot().setFree();
		setOfCarNos.remove(token.getCarNo());
		return token.getCarNo();
		
	}

}
