package org.bitsquad.warzone.country;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

/**
 * This tests the functionality of Country class.
 * it tests the addNeighbor and removeNeighbor methods of the Country class.
 */
class CountryTest {
	
	
	private final Country countryObject = new Country();

	
	/**
	 * This method will testing that neighbor country id is only be added if it is not added.
	 * if id is already added in neighbor list then the test case is fail 
	 */
	@Test
	void testAddNeighbor() {
		if(countryObject.getNeighbors().contains(2) == false) {
			assumeTrue(countryObject.addNeighbor(2));
		}
		else {
			fail("test should have been aborted because country is already added in neighbors list");
		}
	}
	
	@Test
	void testRemoveNeighbor() {
		
		if(countryObject.getNeighbors().contains(1)) {
			assumeTrue(countryObject.removeNeighbor(1));
		}
		else {
			fail("test should have been aborted becuase country you want to reomve is not present in neighbor list");
		}
		
	}
}