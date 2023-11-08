package org.bitsquad.warzone.order;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.Player;

/**
 * This class represents the Deploy Order
 */
public class DeployOrder extends Order{

    /**
     * Parameterized constructor
     * @param p_player player instance
     * @param p_targetCountryID target country ID
     * @param p_armyUnits number of army units
     */
    public DeployOrder(Player p_player, int p_targetCountryID, int p_armyUnits){
        super(p_player, -1, p_targetCountryID, p_armyUnits);
    }

    @Override
    public boolean isValid(){
        // Check if the destination country belongs to the player
        Country l_targetCountry = this.getPlayer().getCountryByID(this.getTargetCountryId());
        return l_targetCountry != null;
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute(){
        Map l_gameMap;
        l_gameMap = GameEngine.getInstance().getGameMap();

        // Make changes to the map
        Country l_country = this.getPlayer().getCountryByID(this.getTargetCountryId());
        l_country.setArmyValue(l_country.getArmyValue() + getNoOfArmyUnits());

        LogEntryBuffer.getInstance().log("Deployed " + this.getNoOfArmyUnits() + " number of armies in country " + this.getTargetCountryId());
    }
}
