package org.bitsquad.warzone.gameengine.policy;

import org.bitsquad.warzone.order.Order;
import org.bitsquad.warzone.player.Player;

/**
 * Implements the Negotiate Policy
 */
public class NegotiatePolicy implements Policy{
    private Player d_PlayerA;
    private Player d_PlayerB;

    /**
     * Parameterized constructor
     * @param p_playerA Player
     * @param p_playerB Player
     */
    public NegotiatePolicy(Player p_playerA, Player p_playerB){
        this.d_PlayerA = p_playerA;
        this.d_PlayerB = p_playerB;
    }

    /**
     * Checks if the Order obeys the policy
     * @param p_order Order
     * @return boolean
     */
    @Override
    public boolean check(Order p_order){
        // TODO: Implement
        return true;
    }
}
