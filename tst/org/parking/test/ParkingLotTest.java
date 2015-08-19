package org.parking.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.parking.main.ParkingArea;
import org.parking.main.ParkingLotIsFullException;
import org.parking.main.ParkingToken;
import org.parking.main.exception.CarNotPresentException;
import org.parking.main.exception.TokenNullException;

public class ParkingLotTest {
	
	ParkingArea parkingArea = new ParkingArea(1);

	@Test
	public void shouldAllowToParkACarIfFilledCapcityIsLessThanTotalCapacity() throws ParkingLotIsFullException{
		ParkingArea parkingArea = new ParkingArea(1);
		ParkingToken token = parkingArea.parkCar("21");
		assertEquals(true, !token.equals(null));
	}
	
	@Test(expected = ParkingLotIsFullException.class)
	public void shouldNotAllowToParkACarIfFilledCapcityIsEqualTotalCapacity() throws ParkingLotIsFullException{
		ParkingArea parkingArea = new ParkingArea(0);
		ParkingToken token = parkingArea.parkCar("21");
	}
	
	@Test
	public void testCarUnPark() throws  CarNotPresentException, ParkingLotIsFullException {
		ParkingArea parkingArea = new ParkingArea(1);
		ParkingToken token = parkingArea.parkCar("21");
		assertEquals("21",parkingArea.unParkCar(token));
	}
	
	
	@Test
	public void testParkingSameCar() throws ParkingLotIsFullException {
		ParkingArea parkingArea = new ParkingArea(2);
		ParkingToken token = parkingArea.parkCar("21");
		ParkingToken tokenRes = parkingArea.parkCar("21");
		assertNull(tokenRes);
	}
	
	@Test(expected = CarNotPresentException.class)
	public void testUnparkCarTwoTimes() throws CarNotPresentException, ParkingLotIsFullException {
		ParkingArea parkingArea = new ParkingArea(2);
		ParkingToken token = parkingArea.parkCar("21");
		parkingArea.unParkCar(token);
		parkingArea.unParkCar(token);
		
		
	}
	
	
}
