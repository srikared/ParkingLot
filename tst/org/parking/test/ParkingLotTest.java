package org.parking.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.parking.main.AllParkingAreasFullException;
import org.parking.main.FBIAgent;
import org.parking.main.Notifier;
import org.parking.main.ParkingArea;
import org.parking.main.ParkingAttendant;
import org.parking.main.ParkingOwner;
import org.parking.main.ParkingToken;
import org.parking.main.constants.WhenToNotifySubscribers;
import org.parking.main.exception.CarNotPresentException;
import org.parking.main.exception.ParkingLotIsFullException;

public class ParkingLotTest {

	@Test
	public void shouldAllowToParkACarIfFilledCapcityIsLessThanTotalCapacity()
			throws ParkingLotIsFullException, FileNotFoundException,
			UnsupportedEncodingException {
		ParkingOwner parkingOwner = mock(ParkingOwner.class);
		ParkingArea parkingArea = new ParkingArea(1, parkingOwner);
		ParkingToken token = parkingArea.parkCar("21");
		assertEquals(true, !token.equals(null));
	}

	@Test(expected = ParkingLotIsFullException.class)
	public void shouldNotAllowToParkACarIfFilledCapcityIsEqualTotalCapacity()
			throws ParkingLotIsFullException, FileNotFoundException,
			UnsupportedEncodingException {
		ParkingOwner parkingOwner = mock(ParkingOwner.class);
		ParkingArea parkingArea = new ParkingArea(0, parkingOwner);
		ParkingToken token = parkingArea.parkCar("21");
	}

	@Test
	public void testCarUnPark() throws CarNotPresentException,
			ParkingLotIsFullException, FileNotFoundException,
			UnsupportedEncodingException {
		ParkingOwner parkingOwner = mock(ParkingOwner.class);
		ParkingArea parkingArea = new ParkingArea(1, parkingOwner);
		ParkingToken token = parkingArea.parkCar("21");
		assertEquals("21", parkingArea.unParkCar(token));
	}

	@Test
	public void testParkingSameCar() throws ParkingLotIsFullException,
			FileNotFoundException, UnsupportedEncodingException {
		ParkingOwner parkingOwner = mock(ParkingOwner.class);
		ParkingArea parkingArea = new ParkingArea(2, parkingOwner);
		ParkingToken token = parkingArea.parkCar("21");
		ParkingToken tokenRes = parkingArea.parkCar("21");
		assertNull(tokenRes);
	}

	@Test(expected = CarNotPresentException.class)
	public void testUnparkCarTwoTimes() throws CarNotPresentException,
			ParkingLotIsFullException, FileNotFoundException,
			UnsupportedEncodingException {
		ParkingOwner parkingOwner = mock(ParkingOwner.class);
		ParkingArea parkingArea = new ParkingArea(2, parkingOwner);
		ParkingToken token = parkingArea.parkCar("21");
		parkingArea.unParkCar(token);
		parkingArea.unParkCar(token);
	}

	@Test
	public void testIfParkingIsFull() throws IOException,
			ParkingLotIsFullException {
		ParkingOwner parkingOwner = mock(ParkingOwner.class);
		ParkingArea parkingArea = new ParkingArea(2, parkingOwner);
		ParkingToken token = parkingArea.parkCar("21");
		ParkingToken tokenRes = parkingArea.parkCar("22");
		verify(parkingOwner, times(1)).notifyAgent();
	}

	@Test
	public void testIfParkingFullNotificationIsNotSentMultipleTimes()
			throws IOException {
		ParkingOwner parkingOwner = mock(ParkingOwner.class);
		ParkingArea parkingArea = new ParkingArea(2, parkingOwner);
		try {
			ParkingToken token = parkingArea.parkCar("21");

			ParkingToken tokenRes = parkingArea.parkCar("22");
			ParkingToken tokenResX = parkingArea.parkCar("23");
		} catch (ParkingLotIsFullException e) {

		} finally {
			verify(parkingOwner, times(1)).notifyAgent();
		}
	}

	@Test
	public void testNotifyOwnerWhenParkingBecomeAvailable()
			throws FileNotFoundException, UnsupportedEncodingException,
			ParkingLotIsFullException, CarNotPresentException {

		ParkingOwner parkingOwner = mock(ParkingOwner.class);
	
		ParkingArea parkingArea = new ParkingArea(2, parkingOwner);
		
		ParkingToken token = parkingArea.parkCar("21");
		ParkingToken tokenRes = parkingArea.parkCar("22");
		parkingArea.unParkCar(tokenRes);
		parkingArea.unParkCar(token);
		verify(parkingOwner, times(2)).notifyAgent();

	}
	
	@Test
	public void testNotifyAllListenersWhenParkingIsFull () throws FileNotFoundException, UnsupportedEncodingException, ParkingLotIsFullException {
		
		ParkingOwner parkingOwner = mock(ParkingOwner.class);
		ParkingArea parkingArea = new ParkingArea(10, parkingOwner);
		FBIAgent fbiAgent = mock(FBIAgent.class);
		
		ArrayList<Notifier> subscribers = new ArrayList<>();
		subscribers.add(fbiAgent);
		parkingArea.addSubscriberOnFull(fbiAgent, WhenToNotifySubscribers.EIGHTY_PERCENT);
		ParkingToken tokenOne = parkingArea.parkCar("21");
		ParkingToken tokenTwo = parkingArea.parkCar("22");
		ParkingToken tokenThree = parkingArea.parkCar("23");
		ParkingToken tokenFour = parkingArea.parkCar("24");
		ParkingToken tokenFive = parkingArea.parkCar("25");
		ParkingToken tokenSix = parkingArea.parkCar("26");
		ParkingToken tokenSeven = parkingArea.parkCar("27");
		ParkingToken tokenEight = parkingArea.parkCar("28");
		ParkingToken tokenNine = parkingArea.parkCar("29");
		ParkingToken tokenTen = parkingArea.parkCar("30");
		
		verify(parkingOwner, times(1)).notifyAgent();
		verify(fbiAgent, times(1)).notifyAgent();
	}

	@Test
	public void testParkingAttendantsCapabilityToParkACar()
			throws FileNotFoundException, UnsupportedEncodingException,
			ParkingLotIsFullException, CarNotPresentException, AllParkingAreasFullException {

		ParkingOwner owner1 = new ParkingOwner(); 
		ParkingOwner owner2 = new ParkingOwner(); 
		
		Set<ParkingArea> parkingAreasSet =  new HashSet<>();
		ParkingArea p1 = new ParkingArea(2, owner1);
		ParkingArea p2 = new ParkingArea(2, owner2);
		ParkingArea p3 = new ParkingArea(2, owner1);
	
		parkingAreasSet.add(p1);
		parkingAreasSet.add(p2);
		parkingAreasSet.add(p3);
	
		ParkingAttendant attendant = new ParkingAttendant(parkingAreasSet);
		
		assertNotNull(attendant.parkCar("1234"));
	
	
	}
	
	
	
}
