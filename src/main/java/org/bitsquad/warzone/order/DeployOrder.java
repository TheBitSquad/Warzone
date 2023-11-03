package org.bitsquad.warzone.order;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.Player;

import java.util.HashMap;

/**
 * This class represents the Deploy Order
 */
public class DeployOrder extends Order{

    /**
     * Parameterized constructor
     * @param p_player player instance
     * @param p_sourceCountryId source country ID
     * @param p_targetCountryID target country ID
     * @param p_armyUnits number of army units
     */
    public DeployOrder(Player p_player, int p_sourceCountryId, int p_targetCountryID, int p_armyUnits){
        super(p_player, p_sourceCountryId, p_targetCountryID, p_armyUnits);
    }

    @Override
    public boolean isValid(){
        // Check if the destination country belongs to the player
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : GameEngine.get_instance().getGameMap().getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }
        Country l_sourceCountry = l_allCountries.get(this.getSourceCountryId());
        if(l_sourceCountry.getOwnedByPlayerId() != this.getPlayer().getId()){
            return false;
        }
        return true;
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute(){
        Map l_gameMap;
        l_gameMap = GameEngine.get_instance().getGameMap();

        // Get all countries
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : l_gameMap.getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }

        // Make changes to the map
        Country l_country = l_allCountries.get(getTargetCountryId());
        l_country.setArmyValue(l_country.getArmyValue() + getNoOfArmyUnits());
    }
}
