package org.bitsquad.warzone.order;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.BasePlayer;
import org.bitsquad.warzone.player.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Bomb Order
 */
class BombOrderTest {

    /**
     * Setup
     */
    @BeforeAll
    static void init(){
        assertDoesNotThrow(()->{
            GameEngine.getInstance().handleAddPlayer("aman");
            GameEngine.getInstance().handleAddPlayer("nisarg");
            BasePlayer l_p1 = GameEngine.getInstance().getGamePlayers().get(0);
            BasePlayer l_p2 = GameEngine.getInstance().getGamePlayers().get(1);

            Map l_map = new Map();
            l_map.addContinent(1, 3);
            l_map.addContinent(2, 10);
            l_map.addCountry(1, 1);
            l_map.addCountry(2, 1);
            l_map.addCountry(3, 2);
            l_map.addCountry(4, 2);
            l_map.addNeighbor(1,2);
            l_map.addNeighbor(2,3);
            l_map.addNeighbor(3,4);
            l_map.addNeighbor(1, 4);
            GameEngine.getInstance().setGameMap(l_map);

            HashMap<Integer, Country> l_allCountries = new HashMap<>();
            for (Continent l_continent : l_map.getContinents().values()) {
                HashMap<Integer, Country> l_countries = l_continent.getCountries();
                l_allCountries.putAll(l_countries);
            }

            l_p1.addCountryOwned(l_allCountries.get(1));
            l_allCountries.get(1).setOwnedByPlayerId(l_p1.getId());
            l_p1.addCountryOwned(l_allCountries.get(2));
            l_allCountries.get(2).setOwnedByPlayerId(l_p1.getId());

            l_p2.addCountryOwned(l_allCountries.get(3));
            l_allCountries.get(3).setOwnedByPlayerId(l_p2.getId());
            l_p2.addCountryOwned(l_allCountries.get(4));
            l_allCountries.get(4).setOwnedByPlayerId(l_p2.getId());

            l_allCountries.get(1).setArmyValue(8);
            l_allCountries.get(2).setArmyValue(1);
            l_allCountries.get(3).setArmyValue(2);
            l_allCountries.get(4).setArmyValue(10);
        });
    }

    /**
     * Teardown
     */
    @AfterAll
    static void destroy(){
        assertDoesNotThrow(()->{
            GameEngine.getInstance().handleRemovePlayer("aman");
            GameEngine.getInstance().handleRemovePlayer("nisarg");
            GameEngine.getInstance().setGameMap(new Map());
        });
    }

    /**
     * Checks isValid
     */
    @Test
    void isValid() {
        BasePlayer l_p1 = GameEngine.getInstance().getGamePlayers().get(0);
        BasePlayer l_p2 = GameEngine.getInstance().getGamePlayers().get(1);

        Order order1 = new BombOrder(l_p1, 2);
        Order order2 = new BombOrder(l_p1, 3);

        assertFalse(order1.isValid());
        assertTrue(order2.isValid());
    }

    /**
     * Checks execute()
     */
    @Test
    void execute() {
        BasePlayer l_p1 = GameEngine.getInstance().getGamePlayers().get(0);
        BasePlayer l_p2 = GameEngine.getInstance().getGamePlayers().get(1);

        Order order1 = new BombOrder(l_p1, 4);
        order1.execute();

        assertEquals(5, l_p2.getCountryByID(4).getArmyValue());
    }
}