package org.parking.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.parking.main.constants.WhenToNotifySubscribers;
import org.parking.main.exception.CarNotPresentException;
import org.parking.main.exception.ParkingLotIsFullException;
import org.parking.main.exception.TokenNullException;

public class ParkingArea {

	private int totalCapacity;
	private int filledCapacity;
	private Slot[] slots;
	private int freeCarSlot;
	private Set<String> setOfCarNos = new HashSet<String>();
	private ParkingOwner parkingOwner;
	private HashMap<WhenToNotifySubscribers, List<Notifier>> subscribersForFullParking = new HashMap<>();
	private HashMap<WhenToNotifySubscribers, List<Notifier>> subscribersForEmptyParking = new HashMap<>();

	public ParkingArea(int totalCapacity, ParkingOwner parkingOwner) {
		this.totalCapacity = totalCapacity;
		this.filledCapacity = 0;
		this.parkingOwner = parkingOwner;
		slots = new Slot[totalCapacity];
		for (int currentIndex = 0; currentIndex < totalCapacity; currentIndex++) {
			slots[currentIndex] = new Slot(currentIndex, true);
		}
		List<Notifier> subscribersForFullParking = new ArrayList<>();
		subscribersForFullParking.add(parkingOwner);
		this.subscribersForFullParking.put(WhenToNotifySubscribers.HUNDRED_PERCENT,subscribersForFullParking );
		
		List<Notifier> subscribersForEmptyParking = new ArrayList<>();
		subscribersForEmptyParking.add(parkingOwner);
		this.subscribersForFullParking.put(WhenToNotifySubscribers.HUNDRED_PERCENT,subscribersForFullParking );
		freeCarSlot = 0;
	}

	public ParkingToken parkCar(String carNo) throws ParkingLotIsFullException,
			FileNotFoundException, UnsupportedEncodingException {
		if (!isPossibleToParkCar()) {
			throw new ParkingLotIsFullException();
		}
		if (isPossibleToParkCar() && !setOfCarNos.contains(carNo)) {
			filledCapacity++;
			slots[freeCarSlot].setOccupied();
			setNewFreeSlot();
			setOfCarNos.add(carNo);
			notifyListenersForFull((setOfCarNos.size() * 100) / totalCapacity);

			return new ParkingToken(slots[freeCarSlot], carNo,this);
		}

		return null;
	}

	private void notifyListenersForFull(int percentFull) {
		for (WhenToNotifySubscribers whenToNotifySubscribers : subscribersForFullParking
				.keySet()) {
			if (whenToNotifySubscribers.getValue() == percentFull) {
				for (Notifier listener : subscribersForFullParking
						.get(whenToNotifySubscribers)) {

					listener.notifyAgent();
				}
			}
		}

	}
	
	private void notifyListenersForEmpty(int percentEmpty) {
		for (WhenToNotifySubscribers whenToNotifySubscribers : subscribersForFullParking
				.keySet()) {
			if (whenToNotifySubscribers.getValue() == percentEmpty) {
				for (Notifier listener : subscribersForFullParking
						.get(whenToNotifySubscribers)) {

					listener.notifyAgent();
				}
			}
		}

	}

	private boolean isParkingFull() {
		if (setOfCarNos.size() == totalCapacity)
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
		if (!setOfCarNos.contains(token.getCarNo())) {
			throw new CarNotPresentException();
		}
		if (isParkingFull()) {
			parkingOwner.notifyAgent();
		}
		token.getSlot().setFree();
		setOfCarNos.remove(token.getCarNo());
		filledCapacity--;
		return token.getCarNo();

	}

	public void addSubscriberOnFull(Notifier subscriber,
			WhenToNotifySubscribers whenToNotifySubscribers) {
		if (!this.subscribersForFullParking
				.containsKey(whenToNotifySubscribers)) {
			List<Notifier> notifiers = new ArrayList<Notifier>();
			notifiers.add(subscriber);
			this.subscribersForFullParking.put(whenToNotifySubscribers,
					notifiers);
		} else {
			this.subscribersForFullParking.get(whenToNotifySubscribers).add(
					subscriber);
		}
	}

	public void addSubscriberOnEmpty(Notifier subscriber,
			WhenToNotifySubscribers whenToNotifySubscribers) {
		if (!this.subscribersForEmptyParking
				.containsKey(whenToNotifySubscribers)) {
			List<Notifier> notifiers = new ArrayList<Notifier>();
			notifiers.add(subscriber);
			this.subscribersForEmptyParking.put(whenToNotifySubscribers,
					notifiers);
		} else {
			this.subscribersForEmptyParking.get(whenToNotifySubscribers).add(
					subscriber);
		}
	}
	
	public int getEmptySpace(){
		return filledCapacity-setOfCarNos.size();
	}

}
