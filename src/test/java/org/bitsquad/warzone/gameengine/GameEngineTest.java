package org.bitsquad.warzone.gameengine;

import static org.junit.jupiter.api.Assertions.*;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.phase.IssueOrder_PreDeploy;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * This tests the functionality of GameEngine class.
 */
class GameEngineTest {

    @BeforeEach
    public void initEach() {
        // It adds multiple players before each test.
        assertDoesNotThrow(() -> {
            GameEngine.get_instance().handleAddPlayer("a");
            GameEngine.get_instance().handleAddPlayer("b");
        });
    }

    @AfterEach
    public void destroyEach() {
        // It removes multiple players and the map after each test for checking
        // reinforcement army calculation
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
        Player l_p1 = GameEngine.get_instance().getGamePlayers().get(0);
        Player l_p2 = GameEngine.get_instance().getGamePlayers().get(1);

        assertEquals(3, GameEngine.get_instance().getNumberOfReinforcementUnits(l_p1));
        assertEquals(3, GameEngine.get_instance().getNumberOfReinforcementUnits(l_p2));
    }

    /**
     * It checks the Country bonus of each player.
     */
    @Test
    void testCalculationOfReinforcementArmyCountryBonus() {
        Player l_p1 = GameEngine.get_instance().getGamePlayers().get(0);
        Player l_p2 = GameEngine.get_instance().getGamePlayers().get(1);

        ArrayList<Country> l_p1Countries = new ArrayList<>();
        l_p1Countries.add(new Country(1, 1));
        l_p1Countries.add(new Country(2, 1));
        l_p1Countries.add(new Country(3, 1));
        l_p1Countries.add(new Country(4, 2));
        l_p1Countries.add(new Country(5, 2));
        l_p1Countries.add(new Country(6, 2));
        l_p1Countries.add(new Country(7, 4));
        l_p1Countries.add(new Country(8, 5));
        l_p1Countries.add(new Country(9, 6));


        ArrayList<Country> l_p2Countries = new ArrayList<>();
        l_p2Countries.add(new Country(6, 2));
        l_p2Countries.add(new Country(7, 3));
        l_p2Countries.add(new Country(8, 3));
        l_p2Countries.add(new Country(9, 3));
        l_p2Countries.add(new Country(10, 3));

        l_p1.setCountriesOwned(l_p1Countries);
        l_p2.setCountriesOwned(l_p2Countries);

        assertEquals(6, GameEngine.get_instance().getNumberOfReinforcementUnits(l_p1));
        assertEquals(4, GameEngine.get_instance().getNumberOfReinforcementUnits(l_p2));
    }

    /**
     * It checks the Continent bonus for each player.
     */
    @Test
    void testCalculationOfReinforcementArmyContinentBonus() {
        Player l_p1 = GameEngine.get_instance().getGamePlayers().get(0);
        Player l_p2 = GameEngine.get_instance().getGamePlayers().get(1);

        ArrayList<Country> l_p1Countries = new ArrayList<>();
        l_p1Countries.add(new Country(1, 1));
        l_p1Countries.add(new Country(2, 1));
        l_p1Countries.add(new Country(3, 1));
        l_p1Countries.add(new Country(4, 2));
        l_p1Countries.add(new Country(5, 2));
        l_p1Countries.add(new Country(11, 2));
        l_p1Countries.add(new Country(7, 4));
        l_p1Countries.add(new Country(8, 5));
        l_p1Countries.add(new Country(9, 6));


        ArrayList<Country> l_p2Countries = new ArrayList<>();
        l_p2Countries.add(new Country(6, 2));
        l_p2Countries.add(new Country(7, 3));
        l_p2Countries.add(new Country(8, 3));
        l_p2Countries.add(new Country(9, 3));
        l_p2Countries.add(new Country(10, 3));

        l_p1.setCountriesOwned(l_p1Countries);
        l_p2.setCountriesOwned(l_p2Countries);

        Map l_map = new Map();
        l_map.addContinent(1, 3);
        l_map.addContinent(2, 10);
        l_map.addContinent(3, 7);
        l_map.addCountry(1, 1);
        l_map.addCountry(2, 1);
        l_map.addCountry(3, 1);
        l_map.addCountry(4, 2);
        l_map.addCountry(5, 2);
        l_map.addCountry(6, 2);
        l_map.addCountry(11, 2);
        l_map.addCountry(7, 3);
        l_map.addCountry(8, 3);
        l_map.addCountry(9, 3);
        l_map.addCountry(10, 3);
        GameEngine.get_instance().setGameMap(l_map);

        assertEquals(6, GameEngine.get_instance().getNumberOfReinforcementUnits(l_p1));
        assertEquals(4, GameEngine.get_instance().getNumberOfReinforcementUnits(l_p2));
    }

    /**
     * It checks if the requested number of army is sufficient and whether
     * the deploying on the target country is possible or not
     */
    @Test
    void testDeployArmy() {
        Player l_p1 = GameEngine.get_instance().getGamePlayers().get(0);
        Player l_p2 = GameEngine.get_instance().getGamePlayers().get(1);
        l_p1.setAvailableArmyUnits(4);
        l_p2.setAvailableArmyUnits(7);


        ArrayList<Country> l_p1Countries = new ArrayList<>();
        l_p1Countries.add(new Country(1, 1));
        l_p1Countries.add(new Country(2, 1));
        l_p1Countries.add(new Country(3, 1));


        ArrayList<Country> l_p2Countries = new ArrayList<>();
        l_p2Countries.add(new Country(7, 3));
        l_p2Countries.add(new Country(8, 3));
        l_p2Countries.add(new Country(9, 3));
        l_p2Countries.add(new Country(10, 3));

        l_p1.setCountriesOwned(l_p1Countries);
        l_p2.setCountriesOwned(l_p2Countries);

        Map l_map = new Map();
        l_map.addContinent(1, 3);
        l_map.addContinent(2, 10);
        l_map.addContinent(3, 7);
        l_map.addCountry(1, 1);
        l_map.addCountry(2, 1);
        l_map.addCountry(3, 1);
        l_map.addCountry(7, 3);
        l_map.addCountry(8, 3);
        l_map.addCountry(9, 3);
        l_map.addCountry(10, 3);
        GameEngine.get_instance().setGameMap(l_map);
        GameEngine.get_instance().setCurrentPlayerIndex(0);
        GameEngine.get_instance().setPhase(new IssueOrder_PreDeploy(GameEngine.get_instance()));

        assertThrows(Exception.class, () -> {
            GameEngine.get_instance().handleDeployArmy(1, 5);
        });
        assertThrows(Exception.class, () -> {
            GameEngine.get_instance().handleDeployArmy(7, 1);
        });
        assertDoesNotThrow(() -> {
            GameEngine.get_instance().handleDeployArmy(1, 2);
        });
        assertDoesNotThrow(() -> {
            GameEngine.get_instance().handleDeployArmy(1, 1);
        });
        assertThrows(Exception.class, () -> {
            GameEngine.get_instance().handleDeployArmy(1, 2);
        });
        assertDoesNotThrow(() -> {
            GameEngine.get_instance().handleDeployArmy(1, 1);
        });

        GameEngine.get_instance().handleCommit();

        assertDoesNotThrow(() -> {
            GameEngine.get_instance().handleDeployArmy(7, 6);
        });
        assertThrows(Exception.class, () -> {
            GameEngine.get_instance().handleDeployArmy(7, 2);
        });
        assertThrows(Exception.class, () -> {
            GameEngine.get_instance().handleDeployArmy(1, 1);
        });
    }
}
