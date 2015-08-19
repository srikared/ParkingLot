package org.parking.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.parking.main.ParkingArea;
import org.parking.main.ParkingToken;
import org.parking.main.exception.CarNotPresentException;
import org.parking.main.exception.TokenNullException;

public class ParkingLotTest {
	
	ParkingArea parkingArea = new ParkingArea(1);

	@Test
	public void shouldAllowToParkACarIfFilledCapcityIsLessThanTotalCapacity(){
		ParkingArea parkingArea = new ParkingArea(1);
		ParkingToken token = parkingArea.parkCar(22);
		assertEquals(true, !token.equals(null));
	}
	
	@Test
	public void shouldNotAllowToParkACarIfFilledCapcityIsEqualTotalCapacity(){
		ParkingArea parkingArea = new ParkingArea(0);
		ParkingToken token = parkingArea.parkCar(21);
		assertNull(token);
	}
	
	@Test
	public void testCarUnPark() throws  CarNotPresentException {
		ParkingArea parkingArea = new ParkingArea(1);
		ParkingToken token = parkingArea.parkCar(21);
		assertEquals(true,parkingArea.unParkCar(token));
	}
	
	@Test(expected = TokenNullException.class)
	public void testCarUnParkOnTokenNull() throws  CarNotPresentException {
		ParkingArea parkingArea = new ParkingArea(1);
		parkingArea.unParkCar(null);
	}
	
	@Test
	public void testParkingSameCar() {
		ParkingArea parkingArea = new ParkingArea(2);
		ParkingToken token = parkingArea.parkCar(21);
		ParkingToken tokenRes = parkingArea.parkCar(21);
		assertNull(tokenRes);
	}
	
	@Test(expected = CarNotPresentException.class)
	public void testUnparkCarTwoTimes() throws CarNotPresentException {
		ParkingArea parkingArea = new ParkingArea(2);
		ParkingToken token = parkingArea.parkCar(21);
		parkingArea.unParkCar(token);
		parkingArea.unParkCar(token);
		
		
	}
	
	
}
