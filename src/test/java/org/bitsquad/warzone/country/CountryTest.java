package org.bitsquad.warzone.country;
import java.util.ArrayList;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.Test;

/**
 * This tests the functionality of Country class.
 */
class CountryTest {
	static ArrayList<Integer> ns;
	static private Country countryObject;

	@BeforeAll
	public static void init(){
		ns= new ArrayList<Integer>();
		countryObject = new Country(1,1,"India",5,4,ns);
	}

	@AfterAll
	public static void destroy(){
		ns = null;
		countryObject = null;
	}
	/**
	 * This method will testing that neighbor country id is only be added if it is not added.
	 * if id is already added in neighbor list then the test case is fail
	 */
	
	@BeforeEach
	public void initEach(){
	     //test setup code
		ArrayList<Integer> ns1= new ArrayList<Integer>();
		ns1.add(4);
		ns1.add(5);
		ns1.add(6);
		countryObject.setNeighbors(ns1);
		
	}


	/**
	 * Testing addNeighbor method
	 */
	@Test
	void testAddNeighbor() {

		assertFalse(countryObject.addNeighbor(1));
		assertTrue(countryObject.addNeighbor(2));
		assertTrue(countryObject.addNeighbor(3));
		assertFalse(countryObject.addNeighbor(3));
	}

	/**
	 * Testing removeNeighbor method
	 */
	@Test
	void testRemoveNeighbor() {

		assertFalse(countryObject.removeNeighbor(2));
		assertTrue(countryObject.removeNeighbor(4));
	}

}
