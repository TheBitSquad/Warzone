package org.bitsquad.warzone.gameengine;

import static org.junit.jupiter.api.Assertions.*;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * This tests the functionality of GameEngine class.
 */
class GameEngineTest {

    /**
     * It adds multiple players before each test.
     */
    @BeforeEach
    public void initEach() {
        assertDoesNotThrow(() -> {
            GameEngine.get_instance().handleAddPlayer("a");
            GameEngine.get_instance().handleAddPlayer("b");
        });
    }

    /**
     * It removes players and the map after each test.
     */
    @AfterEach
    public void destroyEach() {
        assertDoesNotThrow(() -> {
            GameEngine.get_instance().handleRemovePlayer("a");
            GameEngine.get_instance().handleRemovePlayer("b");
            GameEngine.get_instance().setGameMap(new Map());
        });
    }

    /**
     * It checks the initial bonus for reinforcement army.
     */
    @Test
    void testCalculationOfReinforcementArmyInitialBonus() {
        Player p1 = GameEngine.get_instance().getGamePlayers().get(0);
        Player p2 = GameEngine.get_instance().getGamePlayers().get(1);

        assertEquals(3, GameEngine.get_instance().getNumberOfReinforcementUnits(p1));
        assertEquals(3, GameEngine.get_instance().getNumberOfReinforcementUnits(p2));
    }

    /**
     * It checks the Country bonus of each player.
     */
    @Test
    void testCalculationOfReinforcementArmyCountryBonus() {
        Player p1 = GameEngine.get_instance().getGamePlayers().get(0);
        Player p2 = GameEngine.get_instance().getGamePlayers().get(1);

        ArrayList<Country> p1Countries = new ArrayList<>();
        p1Countries.add(new Country(1, 1));
        p1Countries.add(new Country(2, 1));
        p1Countries.add(new Country(3, 1));
        p1Countries.add(new Country(4, 2));
        p1Countries.add(new Country(5, 2));
        p1Countries.add(new Country(6, 2));
        p1Countries.add(new Country(7, 4));
        p1Countries.add(new Country(8, 5));
        p1Countries.add(new Country(9, 6));


        ArrayList<Country> p2Countries = new ArrayList<>();
        p2Countries.add(new Country(6, 2));
        p2Countries.add(new Country(7, 3));
        p2Countries.add(new Country(8, 3));
        p2Countries.add(new Country(9, 3));
        p2Countries.add(new Country(10, 3));

        p1.setCountriesOwned(p1Countries);
        p2.setCountriesOwned(p2Countries);

        assertEquals(6, GameEngine.get_instance().getNumberOfReinforcementUnits(p1));
        assertEquals(4, GameEngine.get_instance().getNumberOfReinforcementUnits(p2));
    }

    /**
     * It checks the Continent bonus for each player.
     */
    @Test
    void testCalculationOfReinforcementArmyContinentBonus() {
        Player p1 = GameEngine.get_instance().getGamePlayers().get(0);
        Player p2 = GameEngine.get_instance().getGamePlayers().get(1);

        ArrayList<Country> p1Countries = new ArrayList<>();
        p1Countries.add(new Country(1, 1));
        p1Countries.add(new Country(2, 1));
        p1Countries.add(new Country(3, 1));
        p1Countries.add(new Country(4, 2));
        p1Countries.add(new Country(5, 2));
        p1Countries.add(new Country(11, 2));
        p1Countries.add(new Country(7, 4));
        p1Countries.add(new Country(8, 5));
        p1Countries.add(new Country(9, 6));


        ArrayList<Country> p2Countries = new ArrayList<>();
        p2Countries.add(new Country(6, 2));
        p2Countries.add(new Country(7, 3));
        p2Countries.add(new Country(8, 3));
        p2Countries.add(new Country(9, 3));
        p2Countries.add(new Country(10, 3));

        p1.setCountriesOwned(p1Countries);
        p2.setCountriesOwned(p2Countries);

        Map map = new Map();
        map.addContinent(1, 3);
        map.addContinent(2, 10);
        map.addContinent(3, 7);
        map.addCountry(1, 1);
        map.addCountry(2, 1);
        map.addCountry(3, 1);
        map.addCountry(4, 2);
        map.addCountry(5, 2);
        map.addCountry(6, 2);
        map.addCountry(11, 2);
        map.addCountry(7, 3);
        map.addCountry(8, 3);
        map.addCountry(9, 3);
        map.addCountry(10, 3);
        GameEngine.get_instance().setGameMap(map);

        assertEquals(9, GameEngine.get_instance().getNumberOfReinforcementUnits(p1));
        assertEquals(11, GameEngine.get_instance().getNumberOfReinforcementUnits(p2));
    }

}
