package org.bitsquad.warzone.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This tests the functionality of Map class.
 */
class MapTest {
	private Map d_map;

	@BeforeEach
	public void setUp() {
		d_map = new Map();
	}

	@AfterEach
	public void destroy(){ d_map = null; }


	@AfterAll
	public static void destroyAll(){
		// Deleting temp created files
		try {
			// Delete the file
			String l_filenames[] = {"tempMap.map", "savedMap.map", "testMap.map"};

			for(String l_fname: l_filenames){
				Files.deleteIfExists(Paths.get(l_fname));
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
		d_map.addContinent(1, 5);
		assertNotNull(d_map.getContinents().get(1));
		assertNull(d_map.getContinents().get(2));
		assertEquals(5, d_map.getContinents().get(1).getValue());
	}

	/**
	 * Test addCountry Method
	 */
	@Test
	public void testAddCountry() {
		d_map.addContinent(1, 5);
		assertTrue(d_map.addCountry(1, 1));
		assertFalse(d_map.addCountry(1, 2));
		assertNotNull(d_map.getContinents().get(1).getCountries().get(1));

		d_map.addContinent(2, 4);
		assertFalse(d_map.addCountry(1,2));
		assertNull(d_map.getContinents().get(2).getCountries().get(1));
	}

	/**
	 * Test RemoveContinent Method
	 */
	@Test
	public void testRemoveContinent() {
		d_map.addContinent(1, 5);
		assertTrue(d_map.removeContinent(1));
		assertNull(d_map.getContinents().get(1));
	}

	/**
	 * Test AddNeighbor method
	 */
	@Test
	public void testAddNeighbor() {
		d_map.addContinent(1, 5);
		d_map.addContinent(2, 5);
		d_map.addCountry(1, 1);
		d_map.addCountry(2, 2);
		d_map.addCountry(3, 1);
		assertTrue(d_map.addNeighbor(1, 2));
		assertTrue(d_map.addNeighbor(1, 3));
		assertTrue(d_map.addNeighbor(3, 1));
		assertTrue(d_map.getContinents().get(1).getCountries().get(1).getNeighbors().contains(2));
		assertTrue(d_map.getContinents().get(2).getCountries().get(2).getNeighbors().contains(1));
	}


	/**
	 * Test RemoveNeighbor Method
	 */
	@Test
	public void testRemoveNeighbor() {
		d_map.addContinent(1, 5);
		d_map.addCountry(1, 1);
		d_map.addCountry(2, 1);
		d_map.addNeighbor(1, 2);
		d_map.removeNeighbor(1, 2);
		assertFalse(d_map.getContinents().get(1).getCountries().get(1).getNeighbors().contains(2));
	}

	/**
	 * Test loadMap Method
	 * @throws IOException handles IOException
	 */
	@Test
	public void testLoadMap() throws IOException {
		String l_mapData = "[continents]\nEngland 5\nScotland 3\n\n[countries]\n1 Country_1 1\n2 Country_2 1\n3 Country_3 2\n4 Country_4 2\n\n[neighbors]\n1 2\n2 1\n3 4\n4 3\n";
		File l_file = new File("testMap.map");
		BufferedWriter l_writer = new BufferedWriter(new FileWriter(l_file));
		l_writer.write(l_mapData);
		l_writer.flush();
		l_writer.close();
		d_map.loadMap("testMap.map");
		assertNotNull(d_map);

		assertNotNull(d_map.getContinents().get(1));
		assertNotNull(d_map.getContinents().get(2));
		assertNotNull(d_map.getContinents().get(1).getCountries().get(1));
		assertNotNull(d_map.getContinents().get(1).getCountries().get(2));
		assertNotNull(d_map.getContinents().get(2).getCountries().get(3));
		assertNotNull(d_map.getContinents().get(2).getCountries().get(4));
		assertTrue(d_map.getContinents().get(1).getCountries().get(1).getNeighbors().contains(2));
		assertTrue(d_map.getContinents().get(1).getCountries().get(2).getNeighbors().contains(1));
		assertTrue(d_map.getContinents().get(2).getCountries().get(3).getNeighbors().contains(4));
		assertTrue(d_map.getContinents().get(2).getCountries().get(4).getNeighbors().contains(3));
	}

	/**
	 * Test saveMap Method
	 * @throws IOException handles IOException
	 */
	@Test
	public void testSaveMap() throws IOException {
		d_map.addContinent(1, 5);
		d_map.addContinent(2, 3);
		d_map.addCountry(1, 1);
		d_map.addCountry(2, 1);
		d_map.addCountry(3, 2);
		d_map.addCountry(4, 2);
		d_map.addNeighbor(1, 2);
		d_map.addNeighbor(2, 1);
		d_map.addNeighbor(2, 4);
		d_map.addNeighbor(4, 3);
		try {
			d_map.saveMap("savedMap");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		File l_file = new File("savedMap.map");
		assertTrue(l_file.exists());
		StringBuilder l_content = new StringBuilder();
		BufferedReader l_bufferedReader = new BufferedReader(new FileReader(l_file));
		String l_line;
		while ((l_line = l_bufferedReader.readLine()) != null) {
			l_content.append(l_line).append("\n");
		}
		String l_expected = "[continents]\nContinent_1 5\nContinent_2 3\n\n[countries]\n1 Country_1 1\n2 Country_2 1\n3 Country_3 2\n4 Country_4 2\n\n[neighbors]\n1 2\n2 1 4\n3 4\n4 2 3\n";
		assertEquals(l_expected, l_content.toString());
	}

	/**
	 * Test editMap Method
	 * @throws IOException handles IOException
	 */
	@Test
	public void testEditMap() throws IOException{
		File l_file = new File("tempMap.map");
		if(l_file.exists())
			assertTrue(d_map.loadMap("tempMap.map"));
		else{
			d_map.editMap("tempMap.map");
			assertTrue(l_file.exists());
			StringBuilder l_content = new StringBuilder();
			BufferedReader l_reader = new BufferedReader(new FileReader(l_file));
			String l_line;
			while ((l_line = l_reader.readLine()) != null) {
				l_content.append(l_line).append("\n");
			}
			String l_expected = "[continents]\n\n[countries]\n\n[neighbors]\n";
			assertEquals(l_expected, l_content.toString());
		}
	}

	/**
	 * Tests the isContinentSubgraph method
	 */
	@Test
	public void testIsContinentSubgraph(){
		d_map.addContinent(1, 5);
		d_map.addCountry(1, 1);
		d_map.addCountry(2, 1);
		d_map.addCountry(3, 1);
		d_map.addContinent(2, 5);
		d_map.addCountry(4, 2);
		d_map.addCountry(5, 2);
		d_map.addContinent(3, 5);
		d_map.addCountry(6, 3);
		d_map.addCountry(7, 3);

		d_map.addNeighbor(1, 2);
		d_map.addNeighbor(2, 3);
		d_map.addNeighbor(2, 4);
		d_map.addNeighbor(4, 5);
		d_map.addNeighbor(6, 5);

		assertTrue(d_map.isContinentSubgraph(1));
		assertTrue(d_map.isContinentSubgraph(2));
		assertFalse(d_map.isContinentSubgraph(3));
	}

	/**
	 * Tests the validate map method
	 */
	@Test
	public void testValidateMap(){
		d_map.addContinent(1, 5);
		d_map.addCountry(1, 1);
		d_map.addCountry(2, 1);
		d_map.addCountry(3, 1);
		d_map.addContinent(2, 5);
		d_map.addCountry(4, 2);
		d_map.addCountry(5, 2);
		d_map.addContinent(3, 5);
		d_map.addCountry(6, 3);
		d_map.addCountry(7, 3);
		// Graph not connected
		assertFalse(d_map.validateMap());

		d_map.addNeighbor(1, 2);
		d_map.addNeighbor(2, 3);
		d_map.addNeighbor(2, 4);
		d_map.addNeighbor(4, 5);
		d_map.addNeighbor(6, 5);
		d_map.addNeighbor(7, 6);
		assertTrue(d_map.validateMap());

		d_map.removeNeighbor(7, 6);
		//Country 7 not connected
		assertFalse(d_map.validateMap());

		d_map.removeContinent(3);
		d_map.addContinent(3, 5);
		d_map.addCountry(6, 3);
		d_map.addCountry(7, 3);

		// Continent 3 is not a valid subgraph (not connected)
		assertFalse(d_map.validateMap());
	}
}
