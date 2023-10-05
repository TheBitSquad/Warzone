package org.bitsquad.warzone.country;
import java.util.ArrayList;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This tests the functionality of Country class.
 */
class CountryTest {
	static ArrayList<Integer> Ns;
	static private Country CountryObject;

	@BeforeAll
	public static void init(){
		Ns = new ArrayList<Integer>();
		CountryObject = new Country(1,1,"India",5,4, Ns);
	}

	@AfterAll
	public static void destroy(){
		Ns = null;
		CountryObject = null;
	}
	/**
	 * This method will testing that neighbor country id is only be added if it is not added.
	 * if id is already added in neighbor list then the test case is fail
	 */
	
	@BeforeEach
	public void initEach(){
	     //test setup code
		ArrayList<Integer> l_ns1= new ArrayList<Integer>();
		l_ns1.add(4);
		l_ns1.add(5);
		l_ns1.add(6);
		CountryObject.setNeighbors(l_ns1);
	}


	/**
	 * Testing addNeighbor method
	 */
	@Test
	void testAddNeighbor() {

		assertFalse(CountryObject.addNeighbor(1));
		assertTrue(CountryObject.addNeighbor(2));
		assertTrue(CountryObject.addNeighbor(3));
		assertFalse(CountryObject.addNeighbor(3));
	}

	/**
	 * Testing removeNeighbor method
	 */
	@Test
	void testRemoveNeighbor() {

		assertFalse(CountryObject.removeNeighbor(2));
		assertTrue(CountryObject.removeNeighbor(4));
	}

}
