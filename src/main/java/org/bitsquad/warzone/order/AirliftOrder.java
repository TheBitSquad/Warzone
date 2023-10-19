package org.bitsquad.warzone.order;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.map.Map;

import java.util.HashMap;

/**
 * Airlift order
 * Used to move army units from a player owned country to another
 * even when they aren't neighbors
 */
public class AirliftOrder extends Order{

    /**
     * Parameterized Constructor
     * @param p_playerId Player ID
     * @param p_sourceCountryId Source Country ID
     * @param p_targetCountryId Target Counry ID
     * @param p_armyUnits Number of army units
     */
    public AirliftOrder(int p_playerId, int p_sourceCountryId, int p_targetCountryId, int p_armyUnits){
        super(p_playerId, p_sourceCountryId, p_targetCountryId, p_armyUnits);
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

        Country l_sourceCountry = l_allCountries.get(getSourceCountryId());
        Country l_targetCountry = l_allCountries.get(getTargetCountryId());

        l_sourceCountry.setArmyValue(l_sourceCountry.getArmyValue() - getNoOfArmyUnits());
        l_targetCountry.setArmyValue(l_targetCountry.getArmyValue() + getNoOfArmyUnits());
    }
}
