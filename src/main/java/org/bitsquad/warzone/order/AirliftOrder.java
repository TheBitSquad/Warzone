package org.bitsquad.warzone.order;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.Player;

/**
 * Airlift order
 * Used to move army units from a player owned country to another
 * even when they aren't neighbors
 */
public class AirliftOrder extends Order {

    /**
     * Parameterized Constructor
     *
     * @param p_player          Player ID
     * @param p_sourceCountryId Source Country ID
     * @param p_targetCountryId Target Counry ID
     * @param p_armyUnits       Number of army units
     */
    public AirliftOrder(Player p_player, int p_sourceCountryId, int p_targetCountryId, int p_armyUnits) {
        super(p_player, p_sourceCountryId, p_targetCountryId, p_armyUnits);
    }

    @Override
    public boolean isValid() {
        // Check if both source and final countries belong to the player
        Country l_sourceCountry = this.getPlayer().getCountryByID(this.getSourceCountryId());
        Country l_targetCountry = this.getPlayer().getCountryByID(this.getTargetCountryId());

        if (l_sourceCountry == null || l_targetCountry == null) {
            return false;
        } else if (l_sourceCountry.getArmyValue() < this.getNoOfArmyUnits()) {
            return false;
        }
        return true;
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute() {
        Country l_sourceCountry = this.getPlayer().getCountryByID(this.getSourceCountryId());
        Country l_targetCountry = this.getPlayer().getCountryByID(this.getTargetCountryId());

        l_sourceCountry.setArmyValue(l_sourceCountry.getArmyValue() - this.getNoOfArmyUnits());
        l_targetCountry.setArmyValue(l_targetCountry.getArmyValue() + this.getNoOfArmyUnits());

        LogEntryBuffer.getInstance().log("Airlifted " + this.getNoOfArmyUnits() + " from " + this.getSourceCountryId() + " to " + this.getTargetCountryId());
    }
}
