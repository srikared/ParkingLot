package org.parking.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	private ParkingOwner parkingOwner ;
	private File file = new File("C:\\Users\\SAI SRIKAR\\Desktop\\prakingPushNotification.txt");

	public ParkingArea(int totalCapacity, ParkingOwner parkingOwner) {
		this.totalCapacity = totalCapacity;
		this.filledCapacity = 0;
		this.parkingOwner = parkingOwner;
		slots = new Slot[totalCapacity];
		for (int currentIndex = 0; currentIndex < totalCapacity; currentIndex++) {
			slots[currentIndex] = new Slot(currentIndex, true);
		}
		freeCarSlot = 0;
	}

	public ParkingToken parkCar(String carNo) throws ParkingLotIsFullException, FileNotFoundException, UnsupportedEncodingException {
		if(!isPossibleToParkCar()) {
			throw new ParkingLotIsFullException(); 
		}
		if (isPossibleToParkCar() && !setOfCarNos.contains(carNo)) {
			filledCapacity++;
			slots[freeCarSlot].setOccupied();
			setNewFreeSlot();
			setOfCarNos.add(carNo);
			if(isParkingFull()) {
				parkingOwner.notifyWhenParkingIsfull();
			}
			return new ParkingToken(slots[freeCarSlot], carNo);
		}
		
		return null;
	}

	private boolean isParkingFull() throws FileNotFoundException, UnsupportedEncodingException {
		if(setOfCarNos.size()== totalCapacity)
			return true;
		return false;
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
