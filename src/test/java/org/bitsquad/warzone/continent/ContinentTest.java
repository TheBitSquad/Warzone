package org.bitsquad.warzone.continent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This tests the functionality of Continent class.
 */
class ContinentTest {

	private Continent continent;

	@BeforeEach
	void setUp() {
		continent = new Continent(1, 5);
	}

	@Test
	void testGetId() {
		assertEquals(1, continent.getId());
	}

	@Test
	void testSetValue() {
		continent.setValue(10);
		assertEquals(10, continent.getValue());
	}

	@Test
	void testAddCountry() {
		continent.addCountry(101, "Country1");
		assertTrue(continent.getCountries().containsKey(101));
	}

	@Test
	void testRemoveCountry() {
		continent.addCountry(101, "Country1");
		continent.removeCountry(101);
		assertFalse(continent.getCountries().containsKey(101));
	}
}
