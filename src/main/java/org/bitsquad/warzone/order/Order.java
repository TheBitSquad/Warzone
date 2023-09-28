package org.bitsquad.warzone.order;

/**
 * Represents an order placed by a player
 *
 * This class defines the order placed with the ID of the player, the source and
 * target countries, the number of army units involved in the order
 * and the type of the action as per game-play actions.
 * 
 */
public class Order {
    public int d_playerID;
    public int d_sourceCountryID;
    public int d_targetCountryID;
    public int d_noOfArmyUnits;
    public TYPEOFACTION d_typeOfAction;

    public enum TYPEOFACTION {
        DEPLOY, ADVANCE, BOMB, BLOCKADE, AIRLIFT, NEGOTIATE
    };

    /**
     * 
     * @param p_playerID        Player ID
     * @param p_sourceCountryID Source Country ID
     * @param p_targetCountryID Target Country ID
     * @param p_noOfArmyUnits   No of Army Units
     * @param p_typeOfAction    Type of Actions
     */

    public Order(int p_playerID, int p_sourceCountryID, int p_targetCountryID, int p_noOfArmyUnits,
            TYPEOFACTION p_typeOfAction) {
        this.d_playerID = p_playerID;
        this.d_sourceCountryID = p_sourceCountryID;
        this.d_targetCountryID = p_targetCountryID;
        this.d_noOfArmyUnits = p_noOfArmyUnits;
        this.d_typeOfAction = p_typeOfAction;
    }

    /**
     * 
     * @param p_playerID Setting the player ID
     */
    public void setPlayerID(int p_playerID) {
        d_playerID = p_playerID;
    }

    /**
     * 
     * @return Returns the player ID
     */
    public int getPlayerID() {
        return d_playerID;
    }

    /**
     * 
     * @param p_sourceCountryID Setting the source country ID
     */
    public void setSourceCountryID(int p_sourceCountryID) {
        d_sourceCountryID = p_sourceCountryID;
    }

    /**
     * 
     * @return Returns the source country ID
     */
    public int getSourceCountryID() {
        return d_sourceCountryID;
    }

    /**
     * 
     * @param p_targetCountryID Setting the target country ID
     */
    public void setTargetCountryID(int p_targetCountryID) {
        d_targetCountryID = p_targetCountryID;
    }

    /**
     * 
     * @return Returns the target country ID
     */
    public int getTargetCountryID() {
        return d_targetCountryID;
    }

    /**
     * 
     * @param p_noOfArmyUnits Setting the number of army units
     */
    public void setNoOfArmyUnits(int p_noOfArmyUnits) {
        d_noOfArmyUnits = p_noOfArmyUnits;
    }

    /**
     * 
     * @return Returns the number of army units
     */
    public int getNoOfArmyUnits() {
        return d_noOfArmyUnits;
    }

    // TODO: Need to implement
    public void execute() {

    }
}
