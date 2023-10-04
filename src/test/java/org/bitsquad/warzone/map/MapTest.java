package org.bitsquad.warzone.map;

import static org.junit.jupiter.api.Assertions.*;

import org.bitsquad.warzone.country.Country;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This tests the functionality of Map class.
 */
class MapTest {
	private Map map;

	@BeforeEach
	public void setUp() {
		map = new Map();
	}

	@AfterEach
	public void destroy(){ map = null; }


	@AfterAll
	public static void destroyAll(){
		// Deleting temp created files
		try {
			// Delete the file
			String filenames[] = {"tempMap.map", "savedMap.map", "testMap.map"};

			for(String f_name: filenames){
				Files.deleteIfExists(Paths.get(f_name));
			}
		}
		// If the input directory is not empty
		catch (DirectoryNotEmptyException e) {
			System.err.println("Directory is not empty!");
		}
		// If some I/O error occurred
		catch (IOException e) {
			System.err.println("I/O error occurred");
		}
		// Delete access denied issue
		catch (SecurityException e) {
			System.err.println("Delete access denied!");
		}
	}

	/**
	 * Test addContinent Method
	 */
	@Test
	public void testAddContinent() {
		map.addContinent(1, 5);
		assertNotNull(map.getContinents().get(1));
		assertNull(map.getContinents().get(2));
		assertEquals(5, map.getContinents().get(1).getValue());
	}

	/**
	 * Test addCountry Method
	 */
	@Test
	public void testAddCountry() {
		map.addContinent(1, 5);
		assertTrue(map.addCountry(1, 1));
		assertFalse(map.addCountry(1, 2));
		assertNotNull(map.getContinents().get(1).getCountries().get(1));

		map.addContinent(2, 4);
		assertFalse(map.addCountry(1,2));
		assertNull(map.getContinents().get(2).getCountries().get(1));
	}

	/**
	 * Test RemoveContinent Method
	 */
	@Test
	public void testRemoveContinent() {
		map.addContinent(1, 5);
		assertTrue(map.removeContinent(1));
		assertNull(map.getContinents().get(1));
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
		assertTrue(map.addNeighbor(1, 2));
		assertTrue(map.addNeighbor(1, 3));
		assertTrue(map.addNeighbor(3, 1));
		assertTrue(map.getContinents().get(1).getCountries().get(1).getNeighbors().contains(2));
		assertTrue(map.getContinents().get(2).getCountries().get(2).getNeighbors().contains(1));
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
		assertFalse(map.getContinents().get(1).getCountries().get(1).getNeighbors().contains(2));
	}

	/**
	 * Test loadMap Method
	 * @throws IOException handles IOException
	 */
	@Test
	public void testLoadMap() throws IOException {
		String mapData = "[continents]\n1 5\n2 3\n\n[countries]\n1 Country_1 1\n2 Country_2 1\n3 Country_3 2\n4 Country_4 2\n\n[neighbors]\n1 2\n2 1\n3 4\n4 3\n";
		File file = new File("testMap.map");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(mapData);
		writer.flush();
		writer.close();
		map.loadMap("testMap.map");
		assertNotNull(map.getContinents().get(1));
		assertNotNull(map.getContinents().get(2));
		assertNotNull(map.getContinents().get(1).getCountries().get(1));
		assertNotNull(map.getContinents().get(1).getCountries().get(2));
		assertNotNull(map.getContinents().get(2).getCountries().get(3));
		assertNotNull(map.getContinents().get(2).getCountries().get(4));
		assertTrue(map.getContinents().get(1).getCountries().get(1).getNeighbors().contains(2));
		assertTrue(map.getContinents().get(1).getCountries().get(2).getNeighbors().contains(1));
		assertTrue(map.getContinents().get(2).getCountries().get(3).getNeighbors().contains(4));
		assertTrue(map.getContinents().get(2).getCountries().get(4).getNeighbors().contains(3));
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
		try {
			map.saveMap("savedMap");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		File file = new File("savedMap.map");
		assertTrue(file.exists());
		StringBuilder content = new StringBuilder();
		BufferedReader l_bufferedReader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = l_bufferedReader.readLine()) != null) {
			content.append(line).append("\n");
		}
		String expected = "[continents]\n1 5\n2 3\n\n[countries]\n1 Country_1 1\n2 Country_2 1\n3 Country_3 2\n4 Country_4 2\n\n[neighbors]\n1 2\n2 1 4\n3 4\n4 2 3\n";
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
			assertTrue(map.loadMap("tempMap.map"));
		else{
			map.editMap("tempMap.map");
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

	/**
	 * Tests the isContinentSubgraph method
	 */
	@Test
	public void testIsContinentSubgraph(){
		map.addContinent(1, 5);
		map.addCountry(1, 1);
		map.addCountry(2, 1);
		map.addCountry(3, 1);
		map.addContinent(2, 5);
		map.addCountry(4, 2);
		map.addCountry(5, 2);
		map.addContinent(3, 5);
		map.addCountry(6, 3);
		map.addCountry(7, 3);

		map.addNeighbor(1, 2);
		map.addNeighbor(2, 3);
		map.addNeighbor(2, 4);
		map.addNeighbor(4, 5);
		map.addNeighbor(6, 5);

		assertTrue(map.isContinentSubgraph(1));
		assertTrue(map.isContinentSubgraph(2));
		assertFalse(map.isContinentSubgraph(3));
	}

	/**
	 * Tests the validate map method
	 */
	@Test
	public void testValidateMap(){
		map.addContinent(1, 5);
		map.addCountry(1, 1);
		map.addCountry(2, 1);
		map.addCountry(3, 1);
		map.addContinent(2, 5);
		map.addCountry(4, 2);
		map.addCountry(5, 2);
		map.addContinent(3, 5);
		map.addCountry(6, 3);
		map.addCountry(7, 3);
		// Graph not connected
		assertFalse(map.validateMap());

		map.addNeighbor(1, 2);
		map.addNeighbor(2, 3);
		map.addNeighbor(2, 4);
		map.addNeighbor(4, 5);
		map.addNeighbor(6, 5);
		map.addNeighbor(7, 6);
		assertTrue(map.validateMap());

		map.removeNeighbor(7, 6);
		//Country 7 not connected
		assertFalse(map.validateMap());

		map.removeContinent(3);
		map.addContinent(3, 5);
		map.addCountry(6, 3);
		map.addCountry(7, 3);

		// Continent 3 is not a valid subgraph (not connected)
		assertFalse(map.validateMap());
	}
}
