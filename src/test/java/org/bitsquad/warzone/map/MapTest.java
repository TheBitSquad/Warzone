package org.bitsquad.warzone.map;

import static org.junit.jupiter.api.Assertions.*;

import org.bitsquad.warzone.country.Country;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
/**
 * This tests the functionality of Map class.
 */
class MapTest {
	private Map map;

	@BeforeEach
	public void setUp() {
		map = new Map();
	}

	/**
	 * Test addContinent Method
	 */
	@Test
	public void testAddContinent() {
		map.addContinent(1, 5);
		assertNotNull(map.d_continents.get(1));
		assertNull(map.d_continents.get(2));
		assertEquals(5, map.d_continents.get(1).getValue());
	}

	/**
	 * Test addCountry Method
	 */
	@Ignore
	public void testAddCountry() {
		map.addContinent(1, 5);
		assertTrue(map.addCountry(1, 1));
		assertFalse(map.addCountry(1, 2));
		assertNotNull(map.d_continents.get(1).getCountries().get(1));
		//ToDo: check AddCountry method again
		map.addContinent(2, 4);
		map.addCountry(1,2);
		assertNull(map.d_continents.get(2).getCountries().get(1));
	}

	/**
	 * Test RemoveContinent Method
	 */
	@Test
	public void testRemoveContinent() {
		map.addContinent(1, 5);
		assertTrue(map.removeContinent(1));
		assertNull(map.d_continents.get(1));
	}

	/**
	 * Test AddNeighbor method
	 */
	@Test
	public void testAddNeighbor() {
		map.addContinent(1, 5);
		map.addContinent(2, 5);
		map.addCountry(1, 1);
		map.addCountry(2, 2);
		map.addCountry(3, 1);
		map.addNeighbor(1, 2);
		map.addNeighbor(1, 3);
		assertTrue(map.d_continents.get(1).getCountries().get(1).getNeighbors().contains(2));
		assertTrue(map.d_continents.get(2).getCountries().get(2).getNeighbors().contains(1));
	}


	/**
	 * Test RemoveNeighbor Method
	 */
	@Test
	public void testRemoveNeighbor() {
		map.addContinent(1, 5);
		map.addCountry(1, 1);
		map.addCountry(2, 1);
		map.addNeighbor(1, 2);
		map.removeNeighbor(1, 2);
		assertFalse(map.d_continents.get(1).getCountries().get(1).getNeighbors().contains(2));
	}

	/**
	 * Test loadMap Method
	 * @throws IOException handles IOException
	 */
	@Test
	public void testLoadMap() throws IOException {
		String mapData = "[continents]\n1 5\n2 3\n\n[countries]\n1 1\n2 1\n3 2\n4 2\n\n[neighbors]\n1 2\n2 1\n3 4\n4 3\n";
		File file = new File("testMap.map");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(mapData);
		writer.flush();
		writer.close();
		map.loadMap("testMap");
		assertNotNull(map.d_continents.get(1));
		assertNotNull(map.d_continents.get(2));
		assertNotNull(map.d_continents.get(1).getCountries().get(1));
		assertNotNull(map.d_continents.get(1).getCountries().get(2));
		assertNotNull(map.d_continents.get(2).getCountries().get(3));
		assertNotNull(map.d_continents.get(2).getCountries().get(4));
		assertTrue(map.d_continents.get(1).getCountries().get(1).getNeighbors().contains(2));
		assertTrue(map.d_continents.get(1).getCountries().get(2).getNeighbors().contains(1));
		assertTrue(map.d_continents.get(2).getCountries().get(3).getNeighbors().contains(4));
		assertTrue(map.d_continents.get(2).getCountries().get(4).getNeighbors().contains(3));
	}

	/**
	 * Test saveMap Method
	 * @throws IOException handles IOException
	 */
	@Test
	public void testSaveMap() throws IOException {
		map.addContinent(1, 5);
		map.addContinent(2, 3);
		map.addCountry(1, 1);
		map.addCountry(2, 1);
		map.addCountry(3, 2);
		map.addCountry(4, 2);
		map.addNeighbor(1, 2);
		map.addNeighbor(2, 1);
		map.addNeighbor(2, 4);
		map.addNeighbor(4, 3);
		map.saveMap("savedMap");
		File file = new File("savedMap.map");
		assertTrue(file.exists());
		StringBuilder content = new StringBuilder();
		BufferedReader l_bufferedReader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = l_bufferedReader.readLine()) != null) {
			content.append(line).append("\n");
		}
		String expected = "[continents]\n1 5\n2 3\n\n[countries]\n1 1\n2 1\n3 2\n4 2\n\n[neighbors]\n1 2\n2 1 4\n3 4\n4 2 3\n";
		assertEquals(expected, content.toString());
	}

	/**
	 * Test editMap Method
	 * @throws IOException handles IOException
	 */
	@Test
	public void testEditMap() throws IOException{
		File file = new File("tempMap.map");
		if(file.exists())
			assertTrue(map.loadMap("tempMap"));
		else{
			map.editMap("tempMap");
			assertTrue(file.exists());
			StringBuilder content = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
			String expected = "[continents]\n\n[countries]\n\n[neighbors]\n";
			assertEquals(expected, content.toString());
		}
	}
}
