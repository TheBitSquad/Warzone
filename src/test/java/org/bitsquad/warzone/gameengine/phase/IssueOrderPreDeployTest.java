package org.bitsquad.warzone.gameengine.phase;

import static org.junit.jupiter.api.Assertions.*;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.order.DeployOrder;
import org.bitsquad.warzone.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * This tests the functionality of Issue Order Pre Deploy class.
 */
public class IssueOrderPreDeployTest {
    /**
     * Sets up the test environment before each test case.
     */
    @BeforeEach
    public void setUp() {
        assertDoesNotThrow(() -> {
            // Setup game engine and players
            GameEngine.getInstance().handleAddPlayer("player1");
            GameEngine.getInstance().handleAddPlayer("player2");

            Map l_map = new Map();
            l_map.addContinent(1, 3);
            l_map.addCountry(1,"SourceCountryName", 1);
            l_map.addCountry(2, "TargetCountryName",1);
            l_map.addCountry(3, "Country3",1);
            l_map.addNeighbor(1, 2);
            l_map.addNeighbor(2,3);
            GameEngine.getInstance().setGameMap(l_map);

            // Assign countries to players
            Player l_player1 = GameEngine.getInstance().getGamePlayers().get(0);
            Player l_player2 = GameEngine.getInstance().getGamePlayers().get(1);

            Country l_sourceCountry= l_map.getContinents().get(1).getCountries().get(1);
            Country l_targetCountry = l_map.getContinents().get(1).getCountries().get(2);
            Country l_Country3 = l_map.getContinents().get(1).getCountries().get(3);

            l_player1.addCountryOwned(l_sourceCountry);
            l_player1.addCountryOwned(l_Country3);
            l_sourceCountry.setOwnedByPlayerId(l_player1.getId());
            l_Country3.setOwnedByPlayerId(l_player1.getId());

            l_player2.addCountryOwned(l_targetCountry);
            l_targetCountry.setOwnedByPlayerId(l_player2.getId());



            //set army values
            l_sourceCountry.setArmyValue(3);
            l_targetCountry.setArmyValue(3);

            // Set the current player to player1
            GameEngine.getInstance().setCurrentPlayerIndex(0);

        });
    }
    /**
     * Tests the handling of a valid 'Deploy Army' order.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testHandleDeployArmy() throws Exception {

        IssueOrderPreDeploy l_issueOrderPreDeploy = new IssueOrderPreDeploy(GameEngine.getInstance());
        Player l_currentPlayer = GameEngine.getInstance().getCurrentPlayer();
        l_currentPlayer.setAvailableArmyUnits(5);
        assertDoesNotThrow(() -> l_issueOrderPreDeploy.handleDeployArmy(GameEngine.getInstance().getGameMap().getContinents().get(1).getCountries().get(3).getCountryId(), 3));
        assertNotNull(l_currentPlayer.getOrderList());
        assertEquals(1, l_currentPlayer.getOrderList().size());
        assertTrue(l_currentPlayer.getOrderList().get(0) instanceof DeployOrder);
        assertEquals(2, l_currentPlayer.getAvailableArmyUnits());
    }

    /**
     * Tests the handling of a 'Deploy Army' order with insufficient available army units.
     */
    @Test
    public void testHandleDeployArmyInsufficientArmyUnits() {
        IssueOrderPreDeploy l_issueOrderPreDeploy = new IssueOrderPreDeploy(GameEngine.getInstance());
        Player l_currentPlayer = GameEngine.getInstance().getCurrentPlayer();
        l_currentPlayer.setAvailableArmyUnits(5);
        Exception exception = assertThrows(Exception.class, () -> l_issueOrderPreDeploy.handleDeployArmy(GameEngine.getInstance().getGameMap().getContinents().get(1).getCountries().get(2).getCountryId(), 6));
        assertEquals("Insufficient army units", exception.getMessage());
        assertEquals(0,l_currentPlayer.getOrderList().size());
    }

    /**
     * Tests the handling of a 'Deploy Army' order for an invalid country ownership.
     */
    @Test
    public void testHandleDeployArmyInvalidCountryOwnership() {
        IssueOrderPreDeploy l_issueOrderPreDeploy = new IssueOrderPreDeploy(GameEngine.getInstance());
        Player l_currentPlayer = GameEngine.getInstance().getCurrentPlayer();
        l_currentPlayer.setAvailableArmyUnits(5);
        Exception exception = assertThrows(Exception.class, () -> l_issueOrderPreDeploy.handleDeployArmy(GameEngine.getInstance().getGameMap().getContinents().get(1).getCountries().get(2).getCountryId(), 3));
        assertEquals("Cannot not deploy to enemy territory", exception.getMessage());
        assertEquals(0,l_currentPlayer.getOrderList().size());
    }

    /**
     * Cleans up the test environment after each test case.
     */
    @AfterEach
    public void cleanUp(){
        assertDoesNotThrow(() -> {
            // Cleanup game engine and players
            GameEngine.getInstance().handleRemovePlayer("player1");
            GameEngine.getInstance().handleRemovePlayer("player2");
        });
    }

}
