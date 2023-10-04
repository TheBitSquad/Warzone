package org.bitsquad.warzone.order;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.Player;

import java.util.HashMap;

/**
 * Represents an order placed by a player
 *
 * This class defines the order placed with the ID of the player, the source and
 * target countries, the number of army units involved in the order
 * and the type of the action as per game-play actions.
 * 
 */
public class Order {
    private int d_playerId;
    private int d_sourceCountryId;
    private int d_targetCountryId;
    private int d_noOfArmyUnits;
    private TYPEOFACTION d_action;

    public enum TYPEOFACTION {
        DEPLOY, ADVANCE, BOMB, BLOCKADE, AIRLIFT, NEGOTIATE
    };

    /**
     * Parametrized Constructor Order
     * 
     * @param p_playerId        Player ID
     * @param p_sourceCountryId Source Country ID
     * @param p_targetCountryId Target Country ID
     * @param p_noOfArmyUnits   No of Army Units
     * @param p_action          Type of Actions
     */
    public Order(int p_playerId, int p_sourceCountryId, int p_targetCountryId, int p_noOfArmyUnits,
            TYPEOFACTION p_action) {
        this.d_playerId = p_playerId;
        this.d_sourceCountryId = p_sourceCountryId;
        this.d_targetCountryId = p_targetCountryId;
        this.d_noOfArmyUnits = p_noOfArmyUnits;
        this.d_action = p_action;
    }

    /**
     * Setter for Player Id
     * 
     * @param p_playerId Setting the player ID
     */
    public void setPlayerId(int p_playerId) {
        d_playerId = p_playerId;
    }

    /**
     * Getter for Player Id
     * 
     * @return Returns the player Id
     */
    public int getPlayerId() {
        return d_playerId;
    }

    /**
     * Setter for source country Id
     * 
     * @param p_sourceCountryId Setting the source country Id
     */
    public void setSourceCountryId(int p_sourceCountryId) {
        d_sourceCountryId = p_sourceCountryId;
    }

    /**
     * Getter for source country Id
     * 
     * @return Returns the source country Id
     */
    public int getSourceCountryId() {
        return d_sourceCountryId;
    }

    /**
     * Setter method for target country Id
     * 
     * @param p_targetCountryId Setting the target country Id
     */
    public void setTargetCountryId(int p_targetCountryId) {
        d_targetCountryId = p_targetCountryId;
    }

    /**
     * Getter method for target country Id
     * 
     * @return Returns the target country Id
     */
    public int getTargetCountryId() {
        return d_targetCountryId;
    }

    /**
     * Setter method for number of army units
     * 
     * @param p_noOfArmyUnits Setting the number of army units
     */
    public void setNoOfArmyUnits(int p_noOfArmyUnits) {
        d_noOfArmyUnits = p_noOfArmyUnits;
    }

    /**
     * Getter method for number of army units involved
     * 
     * @return Returns the number of army units involved
     */
    public int getNoOfArmyUnits() {
        return d_noOfArmyUnits;
    }

    /**
     * Getter for Type of Actions
     * 
     * @return Returns the type of actions
     */
    public TYPEOFACTION getAction() {
        return d_action;
    }

    /**
     * Setter for Type of Actions
     * 
     * @param p_action Setts the type of actions
     */
    public void setAction(TYPEOFACTION p_action) {
        d_action = p_action;
    }

    /**
     * Executes an order
     */
    public void execute() {
        if(d_action == TYPEOFACTION.DEPLOY){
            Map l_gameMap; Player l_player;
            l_gameMap = GameEngine.get_instance().getGameMap();

            // Get all countries
            HashMap<Integer, Country> l_allCountries = new HashMap<>();
            for (Continent l_continent : l_gameMap.getContinents().values()) {
                HashMap<Integer, Country> l_countries = l_continent.getCountries();
                l_allCountries.putAll(l_countries);
            }

            // Make changes to the map
            Country l_country = l_allCountries.get(this.d_targetCountryId);
            l_country.setArmyValue(l_country.getArmyValue() + this.d_noOfArmyUnits);
        }
    }
}