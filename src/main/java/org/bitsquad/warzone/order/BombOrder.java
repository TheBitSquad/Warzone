package org.bitsquad.warzone.order;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.map.Map;

import java.util.HashMap;

/**
 * This class represents the Bomb Card Order
 */
public class BombOrder extends Order{

    /**
     * Parameterized Constructor
     * @param p_playerId Player ID
     * @param p_targetCountryID Target Country ID
     */
    public BombOrder(int p_playerId, int p_targetCountryID){
        super(p_playerId, -1, p_targetCountryID, 0);
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
        Country l_targetCountry = l_allCountries.get(getTargetCountryId());
        l_targetCountry.setArmyValue((int)Math.floor(l_targetCountry.getArmyValue()/2));
    }
}
