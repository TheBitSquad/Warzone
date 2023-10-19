package org.bitsquad.warzone.player;

import static org.junit.jupiter.api.Assertions.*;

import org.bitsquad.warzone.order.DeployOrder;
import org.bitsquad.warzone.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This tests the functionality of Player class.
 */
class PlayerTest {

    private static Player d_player1;
    private static Player d_player2;
    private static Player d_player3;

    @BeforeEach
    public void initEach() {
        d_player1 = new Player("a");
        d_player2 = new Player("b");
        d_player3 = new Player("c");
    }

    /**
     * Testing the generation of player ID
     */
    @Test
    void testPlayerIDGeneration() {
        assertNotEquals(d_player1.getId(), d_player2.getId());
        assertNotEquals(d_player2.getId(), d_player3.getId());
        assertNotEquals(d_player1.getId(), d_player3.getId());
    }

    /**
     * Testing Issue order method using the current order and order list
     */
    @Test
    void testIssueOrder() {
        Order l_order1 = new DeployOrder(d_player1.getId(), 1, 2, 10);
        d_player1.setCurrentOrder(l_order1);
        d_player1.issueOrder();

        assertEquals(1, d_player1.getOrderList().size());
        assertEquals(0, d_player1.getCurrentOrder().getSourceCountryId());

        Order l_order2 = new DeployOrder(d_player1.getId(), 3, 4, 20);
        d_player1.setCurrentOrder(l_order2);
        d_player1.issueOrder();

        assertEquals(2, d_player1.getOrderList().size());
        assertEquals(0, d_player1.getCurrentOrder().getSourceCountryId());
    }

    /**
     * Testing next order method using issue order and current order
     */
    @Test
    void testNextOrder() {
        Order l_order1 = new DeployOrder(d_player1.getId(), 1, 2, 10);
        Order l_order2 = new DeployOrder(d_player1.getId(), 3, 4, 20);
        d_player1.setCurrentOrder(l_order1);
        d_player1.issueOrder();
        d_player1.setCurrentOrder(l_order2);
        d_player1.issueOrder();

        assertEquals(1, d_player1.nextOrder().getSourceCountryId());
        assertEquals(4, d_player1.nextOrder().getTargetCountryId());
        assertNull(d_player1.nextOrder());
    }

}
