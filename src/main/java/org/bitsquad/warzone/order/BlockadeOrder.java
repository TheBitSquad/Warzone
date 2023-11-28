package org.bitsquad.warzone.order;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.gameengine.policy.BlockadePolicy;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.bitsquad.warzone.player.BasePlayer;
import org.bitsquad.warzone.player.Player;

public class BlockadeOrder extends Order {

    /**
     * Parameterized Constructor
     *
     * @param p_player          Player
     * @param p_targetCountryId Target Country ID
     */
    public BlockadeOrder(BasePlayer p_player, int p_targetCountryId) {
        super(p_player, -1, p_targetCountryId, 0);
    }

    /**
     * Checks if the order is valid
     * @return boolean
     */
    @Override
    public boolean isValid() {
        return this.getPlayer().hasCountryWithID(this.getTargetCountryId());
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute() {
        // Double the army units in the target country.
        Country l_targetCountry = this.getPlayer().getCountryByID(this.getTargetCountryId());
        l_targetCountry.setArmyValue(l_targetCountry.getArmyValue() * 3);

        // Add the blockade policy to the policy manager.
        GameEngine.getInstance().getPolicyManager()
                .addPolicy(new BlockadePolicy(this.getPlayer(), l_targetCountry));

        LogEntryBuffer.getInstance().log("Country " + l_targetCountry.getCountryId() + " blockaded");
    }
}
