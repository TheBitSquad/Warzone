package org.bitsquad.warzone.continent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This tests the functionality of Continent class.
 */
class ContinentTest {

	private Continent d_continent;

	@BeforeEach
	void setUp() {
		d_continent = new Continent(1, 5);
	}

	@Test
	void testGetId() {
		assertEquals(1, d_continent.getId());
	}

	/**
	 * Tests set value
	 */
	@Test
	void testSetValue() {
		d_continent.setValue(10);
		assertEquals(10, d_continent.getValue());
	}

	/**
	 * Tests add country
	 */
	@Test
	void testAddCountry() {
		d_continent.addCountry(101, "Country1");
		assertTrue(d_continent.getCountries().containsKey(101));
	}

	/**
	 * Tests remove country
	 */
	@Test
	void testRemoveCountry() {
		d_continent.addCountry(101, "Country1");
		d_continent.removeCountry(101);
		assertFalse(d_continent.getCountries().containsKey(101));
	}
}
