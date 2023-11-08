package org.bitsquad.warzone.gameengine.policy;

import org.bitsquad.warzone.order.Order;
import org.bitsquad.warzone.player.Player;

/**
 * Implements the Negotiate Policy
 */
public class NegotiatePolicy implements Policy{
    private Player d_playerA;
    private Player d_playerB;

    /**
     * Parameterized constructor
     * @param p_playerA Player
     * @param p_playerB Player
     */
    public NegotiatePolicy(Player p_playerA, Player p_playerB){
        this.d_playerA = p_playerA;
        this.d_playerB = p_playerB;
    }

    /**
     * Checks if the Order obeys the policy
     * @param p_order Order
     * @return boolean
     */
    @Override
    public boolean check(Order p_order){
        String l_orderType = p_order.getClass().getSimpleName();
        if (l_orderType.equals("BombOrder") || l_orderType.equals("AdvanceOrder")) {
            int l_playerID = p_order.getPlayer().getId();
            int l_targetCountryID = p_order.getTargetCountryId();
            if (l_playerID == d_playerA.getId()){
                return !d_playerB.hasCountryWithID(l_targetCountryID);
            } else if (l_playerID == d_playerB.getId()){
                return !d_playerA.hasCountryWithID(l_targetCountryID);
            }
        }

        return true;
    }
}
