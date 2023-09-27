package org.bitsquad.warzone.country;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

/**
 * This tests the functionality of Country class.
 */
class CountryTest {

	ArrayList<Integer> ns= new ArrayList<Integer>();
	private final Country countryObject = new Country(1,"India",4,5,4,ns);

	/**
	 * This method will testing that neighbor country id is only be added if it is not added.
	 * if id is already added in neighbor list then the test case is fail
	 */
	@Test
	void testAddNeighbor() {
		if(countryObject.getNeighbors().contains(1) == false) {
			System.out.println(countryObject.addNeighbor(1));
		}
		else {
			fail("test should have been aborted because country is already added in neighbors list");
		}
	}

	@Test
	void testRemoveNeighbor() {
		if(countryObject.getNeighbors().contains(2)== true) {
			fail("test should have been aborted becuase country you want to reomve is not present in neighbor list");
		}
		else {
			System.out.println(countryObject.removeNeighbor(1));
		}
	}

}
