package org.parking.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.parking.main.ParkingArea;
import org.parking.main.ParkingLotIsFullException;
import org.parking.main.ParkingOwner;
import org.parking.main.ParkingToken;
import org.parking.main.exception.CarNotPresentException;

public class ParkingLotTest {

	@Test
	public void shouldAllowToParkACarIfFilledCapcityIsLessThanTotalCapacity()
			throws ParkingLotIsFullException, FileNotFoundException,
			UnsupportedEncodingException {
		ParkingOwner parkingOwner = new ParkingOwner();
		ParkingArea parkingArea = new ParkingArea(1, parkingOwner);
		ParkingToken token = parkingArea.parkCar("21");
		assertEquals(true, !token.equals(null));
	}

	@Test(expected = ParkingLotIsFullException.class)
	public void shouldNotAllowToParkACarIfFilledCapcityIsEqualTotalCapacity()
			throws ParkingLotIsFullException, FileNotFoundException,
			UnsupportedEncodingException {
		ParkingOwner parkingOwner = new ParkingOwner();
		ParkingArea parkingArea = new ParkingArea(0, parkingOwner);
		ParkingToken token = parkingArea.parkCar("21");
	}

	@Test
	public void testCarUnPark() throws CarNotPresentException,
			ParkingLotIsFullException, FileNotFoundException,
			UnsupportedEncodingException {
		ParkingOwner parkingOwner = new ParkingOwner();
		ParkingArea parkingArea = new ParkingArea(1, parkingOwner);
		ParkingToken token = parkingArea.parkCar("21");
		assertEquals("21", parkingArea.unParkCar(token));
	}

	@Test
	public void testParkingSameCar() throws ParkingLotIsFullException,
			FileNotFoundException, UnsupportedEncodingException {
		ParkingOwner parkingOwner = new ParkingOwner();
		ParkingArea parkingArea = new ParkingArea(2, parkingOwner);
		ParkingToken token = parkingArea.parkCar("21");
		ParkingToken tokenRes = parkingArea.parkCar("21");
		assertNull(tokenRes);
	}

	@Test(expected = CarNotPresentException.class)
	public void testUnparkCarTwoTimes() throws CarNotPresentException,
			ParkingLotIsFullException, FileNotFoundException,
			UnsupportedEncodingException {
		ParkingOwner parkingOwner = new ParkingOwner();
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
		verify(parkingOwner , times(1)).notifyWhenParkingIsfull();
	}
	
	@Test
	public void testIfParkingFullNotificationIsNotSentMultipleTimes() throws IOException
			 {
		ParkingOwner parkingOwner = mock(ParkingOwner.class);
		ParkingArea parkingArea = new ParkingArea(2, parkingOwner);
		try {
			ParkingToken token = parkingArea.parkCar("21");

			ParkingToken tokenRes = parkingArea.parkCar("22");
			ParkingToken tokenResX = parkingArea.parkCar("23");
		} catch (ParkingLotIsFullException e) {
			
		}
		finally {
			verify(parkingOwner , times(1)).notifyWhenParkingIsfull();
		}
	}

}
