package org.bitsquad.warzone.gameengine;

import static org.junit.jupiter.api.Assertions.*;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.phase.IssueOrderPreDeploy;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.BasePlayer;
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
            GameEngine.getInstance().handleAddPlayer("a");
            GameEngine.getInstance().handleAddPlayer("b");
        });
    }

    @AfterEach
    public void destroyEach() {
        // It removes multiple players and the map after each test for checking
        // reinforcement army calculation
        assertDoesNotThrow(() -> {
            GameEngine.getInstance().handleRemovePlayer("a");
            GameEngine.getInstance().handleRemovePlayer("b");
            GameEngine.getInstance().setGameMap(new Map());
        });
    }

    /**
     * It checks the initial bonus for reinforcement army.
     */
    @Test
    void testCalculationOfReinforcementArmyInitialBonus() {
        BasePlayer l_p1 = GameEngine.getInstance().getGamePlayers().get(0);
        BasePlayer l_p2 = GameEngine.getInstance().getGamePlayers().get(1);

        assertEquals(3, GameEngine.getInstance().getNumberOfReinforcementUnits(l_p1));
        assertEquals(3, GameEngine.getInstance().getNumberOfReinforcementUnits(l_p2));
    }

    /**
     * It checks the Country bonus of each player.
     */
    @Test
    void testCalculationOfReinforcementArmyCountryBonus() {
        BasePlayer l_p1 = GameEngine.getInstance().getGamePlayers().get(0);
        BasePlayer l_p2 = GameEngine.getInstance().getGamePlayers().get(1);

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

        assertEquals(6, GameEngine.getInstance().getNumberOfReinforcementUnits(l_p1));
        assertEquals(4, GameEngine.getInstance().getNumberOfReinforcementUnits(l_p2));
    }

    /**
     * It checks the Continent bonus for each player.
     */
    @Test
    void testCalculationOfReinforcementArmyContinentBonus() {
        BasePlayer l_p1 = GameEngine.getInstance().getGamePlayers().get(0);
        BasePlayer l_p2 = GameEngine.getInstance().getGamePlayers().get(1);

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
        GameEngine.getInstance().setGameMap(l_map);

        assertEquals(9, GameEngine.getInstance().getNumberOfReinforcementUnits(l_p1));
        assertEquals(11, GameEngine.getInstance().getNumberOfReinforcementUnits(l_p2));
    }

    /**
     * It checks if the requested number of army is sufficient and whether
     * the deploying on the target country is possible or not
     */
    @Test
    void testDeployArmy() {
        BasePlayer l_p1 = GameEngine.getInstance().getGamePlayers().get(0);
        BasePlayer l_p2 = GameEngine.getInstance().getGamePlayers().get(1);
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
        GameEngine.getInstance().setGameMap(l_map);
        GameEngine.getInstance().setCurrentPlayerIndex(0);
        GameEngine.getInstance().setPhase(new IssueOrderPreDeploy(GameEngine.getInstance()));

        assertThrows(Exception.class, () -> {
            GameEngine.getInstance().handleDeployArmy(1, 5);
        });
        assertThrows(Exception.class, () -> {
            GameEngine.getInstance().handleDeployArmy(7, 1);
        });
        assertDoesNotThrow(() -> {
            GameEngine.getInstance().handleDeployArmy(1, 2);
        });
        assertDoesNotThrow(() -> {
            GameEngine.getInstance().handleDeployArmy(1, 1);
        });
        assertThrows(Exception.class, () -> {
            GameEngine.getInstance().handleDeployArmy(1, 2);
        });
        assertDoesNotThrow(() -> {
            GameEngine.getInstance().handleDeployArmy(1, 1);
        });

        GameEngine.getInstance().handleCommit();

        assertDoesNotThrow(() -> {
            GameEngine.getInstance().handleDeployArmy(7, 6);
        });
        assertThrows(Exception.class, () -> {
            GameEngine.getInstance().handleDeployArmy(7, 2);
        });
        assertThrows(Exception.class, () -> {
            GameEngine.getInstance().handleDeployArmy(1, 1);
        });
    }
}
