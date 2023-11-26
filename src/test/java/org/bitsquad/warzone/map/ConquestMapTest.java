package org.bitsquad.warzone.map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class represents a set of JUnit tests for the ConquestMap class.
 * It includes tests for adding continents, adding countries, loading a map from a file, and saving a map to a file.
 */
public class ConquestMapTest {

    private ConquestMap d_conquestMap;

    /**
     * Sets up a new ConquestMap object before each test.
     */
    @BeforeEach
    public void setUp() {
        d_conquestMap = new ConquestMap();
    }

    @AfterEach
    public void destroy(){ d_conquestMap = null; }
    /**
     * Tests the addContinent method of the ConquestMap class.
     * Verifies that a continent is added correctly with the specified ID, name, and value.
     */
    @Test
    public void testAddContinent() {
        d_conquestMap.addContinent(1, "Asia", 5);
        assertNotNull(d_conquestMap.getContinents().get(1));
        assertNull(d_conquestMap.getContinents().get(2));
        assertEquals(5, d_conquestMap.getContinents().get(1).getValue());
    }

    /**
     * Tests the addCountry method of the ConquestMap class.
     * Verifies that a country is added to the specified continent with the given country ID.
     */
    @Test
    public void testAddCountry() {
        d_conquestMap.addContinent(1, "Asia", 5);
        assertTrue(d_conquestMap.addCountry(1, 1));
        assertFalse(d_conquestMap.addCountry(1, 2));
        assertNotNull(d_conquestMap.getContinents().get(1).getCountries().get(1));

        d_conquestMap.addContinent(2, "SEA", 3);
        assertFalse(d_conquestMap.addCountry(1, 2));
        assertNull(d_conquestMap.getContinents().get(2).getCountries().get(1));
    }

    /**
     * Tests the loadMap method of the ConquestMap class.
     * Verifies that a ConquestMap object is successfully loaded from a map file.
     *
     * @throws IOException if an I/O error occurs while reading the map file.
     */
    @Test
    public void testLoadMap() throws IOException {
        String l_mapData = "[continents]\nNorth Asia=3\nAsia Minor=4\n\n[Territories]\nRussia,363,81,North Asia,Japan,Turkey\nJapan,678,141,North Asia,Russia\nTurkey,60,155,Asia Minor,Cyprus,Japan\nCyprus,27,191,Asia Minor,Turkey\n\n";
        File l_file = new File("ConquestLoadTest.map");
        BufferedWriter l_writer = new BufferedWriter(new FileWriter(l_file));
        l_writer.write(l_mapData);
        l_writer.flush();
        l_writer.close();
        d_conquestMap.loadMap("ConquestLoadTest.map");
        assertNotNull(d_conquestMap);

        assertNotNull(d_conquestMap.getContinents().get(1));
        assertNotNull(d_conquestMap.getContinents().get(2));
        assertNotNull(d_conquestMap.getContinents().get(1).getCountries().get(1));
        assertNotNull(d_conquestMap.getContinents().get(1).getCountries().get(2));
        assertNotNull(d_conquestMap.getContinents().get(2).getCountries().get(3));
        assertNotNull(d_conquestMap.getContinents().get(2).getCountries().get(4));
        assertTrue(d_conquestMap.getContinents().get(1).getCountries().get(1).getNeighbors().contains(2));
        assertTrue(d_conquestMap.getContinents().get(1).getCountries().get(2).getNeighbors().contains(1));
        assertTrue(d_conquestMap.getContinents().get(2).getCountries().get(3).getNeighbors().contains(2));
        assertTrue(d_conquestMap.getContinents().get(2).getCountries().get(4).getNeighbors().contains(3));
    }

    /**
     * Tests the saveMap method of the ConquestMap class.
     * Verifies that a ConquestMap object is successfully saved to a map file.
     *
     * @throws IOException if an I/O error occurs while writing the map file.
     */
    @Test
    public void testSaveMap() throws IOException {
        d_conquestMap.addContinent(1, 5);
        d_conquestMap.addContinent(2, 3);
        d_conquestMap.addCountry(1, 1);
        d_conquestMap.addCountry(2, 1);
        d_conquestMap.addCountry(3, 2);
        d_conquestMap.addCountry(4, 2);
        d_conquestMap.addNeighbor(1, 2);
        d_conquestMap.addNeighbor(2, 1);
        d_conquestMap.addNeighbor(2, 4);
        d_conquestMap.addNeighbor(4, 3);
        try {
            d_conquestMap.saveMap("savedMap");
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
        String l_expected = "[Continents]\n" +
                "Continent_1=5\n" +
                "Continent_2=3\n" +
                "\n" +
                "[Territories]\n" +
                "Country_1,x,y,Continent_1,Country_2\n" +
                "Country_2,x,y,Continent_1,Country_1,Country_4\n" +
                "\n" +
                "Country_3,x,y,Continent_2,Country_4\n" +
                "Country_4,x,y,Continent_2,Country_2,Country_3\n\n";
        assertEquals(l_expected, l_content.toString());
    }
}
