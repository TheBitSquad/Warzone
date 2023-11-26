package org.bitsquad.warzone.gameengine.phase;

import static org.junit.jupiter.api.Assertions.*;

import org.bitsquad.warzone.card.Card;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.order.*;
import org.bitsquad.warzone.order.Order;
import org.bitsquad.warzone.player.Player;
import org.junit.jupiter.api.*;


/**
 * This tests the functionality of Issue Order Post Deploy class.
 */
public class IssueOrderPostDeployTest {
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
            Player player1 = GameEngine.getInstance().getGamePlayers().get(0);
            Player player2 = GameEngine.getInstance().getGamePlayers().get(1);

            Country l_sourceCountry= l_map.getContinents().get(1).getCountries().get(1);
            Country l_targetCountry = l_map.getContinents().get(1).getCountries().get(2);
            Country l_Country3 = l_map.getContinents().get(1).getCountries().get(3);

            player1.addCountryOwned(l_sourceCountry);
            player1.addCountryOwned(l_Country3);
            l_sourceCountry.setOwnedByPlayerId(player1.getId());
            l_Country3.setOwnedByPlayerId(player1.getId());

            player2.addCountryOwned(l_targetCountry);
            l_targetCountry.setOwnedByPlayerId(player2.getId());



            //set army values
            l_sourceCountry.setArmyValue(3);
            l_targetCountry.setArmyValue(3);

            // Set the current player to player1
            GameEngine.getInstance().setCurrentPlayerIndex(0);

        });
    }

    /**
     * Tests the handling of a valid 'Advance' order.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testHandleAdvanceValidOrder() throws Exception {
        IssueOrderPostDeploy l_issueOrderPostDeploy = new IssueOrderPostDeploy(GameEngine.getInstance());
        l_issueOrderPostDeploy.handleAdvance("SourceCountryName", "TargetCountryName", 3);
        assertNotNull(GameEngine.getInstance().getCurrentPlayer().getOrderList());
        assertTrue(GameEngine.getInstance().getCurrentPlayer().getOrderList().get(0) instanceof AdvanceOrder);
    }

    /**
     * Tests the handling of a valid 'Bomb' order.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testHandleBombValidOrder() throws Exception {
        IssueOrderPostDeploy l_issueOrderPostDeploy = new IssueOrderPostDeploy(GameEngine.getInstance());
        GameEngine.getInstance().getCurrentPlayer().getCurrentCards().put(Card.BombCard,1);
        l_issueOrderPostDeploy.handleBomb(2);

        Exception exception = assertThrows(Exception.class, () -> l_issueOrderPostDeploy.handleBomb(1));
        assertEquals("Player trying to bomb own country. Not allowed.", exception.getMessage());

        exception = assertThrows(Exception.class, () -> l_issueOrderPostDeploy.handleBomb(4));
        assertEquals("Target Country does not exist!", exception.getMessage());

        boolean l_found = false;

        for (Order l_order : GameEngine.getInstance().getCurrentPlayer().getOrderList()) {
            if (l_order instanceof BombOrder) {
                l_found = true;
                break;
            }
        }

        assertTrue(l_found, "BombOrder not found in OrderList");
    }

    /**
     * Tests the handling of a valid 'Blockade' order.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testHandleBlockadeValidOrder() throws Exception {
        IssueOrderPostDeploy l_issueOrderPostDeploy = new IssueOrderPostDeploy(GameEngine.getInstance());
        Player l_currentPlayer = GameEngine.getInstance().getCurrentPlayer();
        l_currentPlayer.getCurrentCards().put(Card.BlockadeCard,1);
        l_issueOrderPostDeploy.handleBlockade(GameEngine.getInstance().getGameMap().getContinents().get(1).getCountries().get(1).getCountryId());
        assertNotNull(l_currentPlayer.getOrderList());
        boolean l_found = false;
        for (Order l_order : l_currentPlayer.getOrderList()) {
            if (l_order instanceof BlockadeOrder) {
                l_found = true;
                break;
            }
        }
        assertTrue(l_found, "BlockadeOrder not found in OrderList");
    }

    /**
     * Tests the handling of a valid 'Airlift' order.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testHandleAirliftValidOrder() throws Exception {
        IssueOrderPostDeploy l_issueOrderPostDeploy = new IssueOrderPostDeploy(GameEngine.getInstance());
        Player l_currentPlayer = GameEngine.getInstance().getCurrentPlayer();
        l_currentPlayer.getCurrentCards().put(Card.AirliftCard,1);
        int numArmies = 3;
        l_issueOrderPostDeploy.handleAirlift(GameEngine.getInstance().getGameMap().getContinents().get(1).getCountries().get(1).getCountryId(),GameEngine.getInstance().getGameMap().getContinents().get(1).getCountries().get(3).getCountryId(), numArmies);
        assertNotNull(l_currentPlayer.getOrderList());

        boolean l_found = false;
        for (Order l_order : l_currentPlayer.getOrderList()) {
            if (l_order instanceof AirliftOrder) {
                l_found = true;
                break;
            }
        }
        assertTrue(l_found, "AirliftOrder not found in OrderList");
    }

    /**
     * Tests the handling of a valid 'Negotiate' order.
     */
    @Test
    public void testHandleNegotiate() {
        IssueOrderPostDeploy l_issueOrderPostDeploy = new IssueOrderPostDeploy(GameEngine.getInstance());
        Player l_player1 = GameEngine.getInstance().getGamePlayers().get(0);
        Player l_player2 =GameEngine.getInstance().getGamePlayers().get(1);

        l_player1.getCurrentCards().put(Card.DiplomacyCard,1);
        assertDoesNotThrow(() -> l_issueOrderPostDeploy.handleNegotiate(l_player2.getId()));
        Exception exception1 = assertThrows(Exception.class, () -> l_issueOrderPostDeploy.handleNegotiate(999));
        assertEquals("The target player ID invalid, no such player found.", exception1.getMessage());

        Exception exception2 = assertThrows(Exception.class, () -> l_issueOrderPostDeploy.handleNegotiate(l_player1.getId()));
        assertEquals("The target player can not be the player itself", exception2.getMessage());

        assertNotNull(l_player1.getOrderList());
        assertEquals(1, l_player1.getOrderList().size());
        boolean l_found = false;
        for (Order l_order : l_player1.getOrderList()) {
            if (l_order instanceof NegotiateOrder) {
                l_found = true;
                break;
            }
        }
        assertTrue(l_found, "NegotiateOrder not found in OrderList");
    }
    /**
     * Tests the handling of the 'Commit' action.
     */
    @Test
    public void testHandleCommit() {
        IssueOrderPostDeploy l_issueOrderPostDeploy = new IssueOrderPostDeploy(GameEngine.getInstance());
        Player l_currentPlayer = GameEngine.getInstance().getCurrentPlayer();

        GameEngine.getInstance().setCurrentPlayerIndex(GameEngine.getInstance().getGamePlayers().size()-1);
        assertDoesNotThrow(() -> l_issueOrderPostDeploy.handleCommit());
        GameEngine.getInstance().setCurrentPlayerIndex(0);
        assertDoesNotThrow(() -> l_issueOrderPostDeploy.handleCommit());
        assertEquals(1, GameEngine.getInstance().getCurrentPlayerIndex());
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