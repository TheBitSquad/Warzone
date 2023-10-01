package org.bitsquad.warzone.player;

import static org.junit.jupiter.api.Assertions.*;

import org.bitsquad.warzone.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This tests the functionality of Player class.
 */
class PlayerTest {

    private static Player player1;
    private static Player player2;
    private static Player player3;

    @BeforeEach
    public void initEach() {
        player1 = new Player("a");
        player2 = new Player("b");
        player3 = new Player("c");
    }

    /**
     * Testing the generation of player ID
     */
    @Test
    void testPlayerIDGeneration() {
        assertNotEquals(player1.getId(), player2.getId());
        assertNotEquals(player2.getId(), player3.getId());
        assertNotEquals(player1.getId(), player3.getId());
    }

    /**
     * Testing Issue order method using the current order and order list
     */
    @Test
    void testIssueOrder() {
        Order order1 = new Order(player1.getId(), 1, 2, 10, Order.TYPEOFACTION.DEPLOY);
        player1.setCurrentOrder(order1);
        player1.issueOrder();

        assertEquals(1, player1.getOrderList().size());
        assertEquals(0, player1.getCurrentOrder().getSourceCountryId());

        Order order2 = new Order(player1.getId(), 3, 4, 20, Order.TYPEOFACTION.BOMB);
        player1.setCurrentOrder(order2);
        player1.issueOrder();

        assertEquals(2, player1.getOrderList().size());
        assertEquals(0, player1.getCurrentOrder().getSourceCountryId());
    }

    /**
     * Testing next order method using issue order and current order
     */
    @Test
    void testNextOrder() {
        Order order1 = new Order(player1.getId(), 1, 2, 10, Order.TYPEOFACTION.DEPLOY);
        Order order2 = new Order(player1.getId(), 3, 4, 20, Order.TYPEOFACTION.BOMB);
        player1.setCurrentOrder(order1);
        player1.issueOrder();
        player1.setCurrentOrder(order2);
        player1.issueOrder();

        assertEquals(1, player1.nextOrder().getSourceCountryId());
        assertEquals(4, player1.nextOrder().getTargetCountryId());
        assertNull(player1.nextOrder());
    }

}
