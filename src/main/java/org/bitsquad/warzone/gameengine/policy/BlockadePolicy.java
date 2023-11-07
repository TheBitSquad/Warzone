package org.bitsquad.warzone.gameengine.policy;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.order.Order;
import org.bitsquad.warzone.player.Player;

/**
 * Blockade Policy to implement the blockade functionality
 */
public class BlockadePolicy implements Policy {
    private Country d_blockadeCountry;
    private Player d_playerBlockaded;

    /**
     * Parametrized constructor
     * @param p_player Player
     * @param p_blockadeCountry Country
     */
    public BlockadePolicy(Player p_player, Country p_blockadeCountry) {
        this.d_playerBlockaded = p_player;
        this.d_blockadeCountry = p_blockadeCountry;
    }

    /**
     * Checks if the policy is valid or not
     * @param p_order Order
     * @return boolean
     */
    @Override
    public boolean check(Order p_order) {
        if (p_order.getPlayer() == d_playerBlockaded
                && (p_order.getSourceCountryId() == d_blockadeCountry.getCountryId() || p_order.getTargetCountryId() == d_blockadeCountry.getCountryId())
        )
            return false;
        else
            return true;
    }
}
