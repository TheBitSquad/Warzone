package org.bitsquad.warzone.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConquestMapTest {
    private Map d_map;

    private ConquestMap d_conquestMap;
    @BeforeEach
    public void setUp() {
        d_map = new Map();
        d_conquestMap = new ConquestMap();
    }
    @Test
    public void testAddContinent() {
        d_map.addContinent(1,"Asia", 5);
        assertNotNull(d_map.getContinents().get(1));
        assertNull(d_map.getContinents().get(2));
        assertEquals(5, d_map.getContinents().get(1).getValue());
    }

    @Test
    public void testAddCountry() {
        d_map.addContinent(1,"Asia", 5);
        assertTrue(d_map.addCountry(1, "India",1));
        assertFalse(d_map.addCountry(1, "Pakistan",2));
        assertNotNull(d_map.getContinents().get(1).getCountries().get(1));

        d_map.addContinent(2, "SEA",3);
        assertFalse(d_map.addCountry(1,"JAPAN",2));
        assertNull(d_map.getContinents().get(2).getCountries().get(1));
    }

    @Test
    public void testLoadMap() throws IOException {
        d_conquestMap.loadMap("Africa.map");
        assertNotNull(d_map);
    }

    @Test
    public void testSaveMap() throws IOException {
        d_conquestMap.loadMap("Africa.map");
        try {
            d_conquestMap.saveMap("Africa2");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        File l_file = new File("Africa2.map");
        assertTrue(l_file.exists());
    }
}
