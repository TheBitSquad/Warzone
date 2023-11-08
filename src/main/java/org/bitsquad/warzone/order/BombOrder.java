package org.bitsquad.warzone.order;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.Player;

import java.util.HashMap;

/**
 * This class represents the Bomb Card Order
 */
public class BombOrder extends Order {

    /**
     * Parameterized Constructor
     *
     * @param p_player          Player instance
     * @param p_targetCountryID Target Country ID
     */
    public BombOrder(Player p_player, int p_targetCountryID) {
        super(p_player, -1, p_targetCountryID, 0);
    }

    /**
     * Checks if the order is valid
     * @return boolean
     */
    @Override
    public boolean isValid() {
        // Check that the player isn't bombing it's own country
        Country l_targetCountry = this.getPlayer().getCountryByID(this.getTargetCountryId());
        return l_targetCountry == null;
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute() {
        Map l_gameMap;
        l_gameMap = GameEngine.getInstance().getGameMap();

        // Get all countries
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : l_gameMap.getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }

        // Make changes to the map
        Country l_targetCountry = l_allCountries.get(this.getTargetCountryId());
        l_targetCountry.setArmyValue((int) Math.floor(1.0 * l_targetCountry.getArmyValue() / 2));

        LogEntryBuffer.getInstance().log("Bombed country " + this.getTargetCountryId());
    }
}
