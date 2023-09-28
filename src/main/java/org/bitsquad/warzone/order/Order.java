package org.bitsquad.warzone.order;

/**
 * Represents an order placed by a player
 *
 * This class defines the order placed with the ID of the player, the source and
 * target countries, the number of army units involved in the order
 * and the type of the action as per game-play actions.
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

    public Order(int p_playerID, int p_sourceCountryID, int p_targetCountryID, int p_noOfArmyUnits,
            TYPEOFACTION p_typeOfAction) {
        this.d_playerID = p_playerID;
        this.d_sourceCountryID = p_sourceCountryID;
        this.d_targetCountryID = p_targetCountryID;
        this.d_noOfArmyUnits = p_noOfArmyUnits;
        this.d_typeOfAction = p_typeOfAction;
    }

    // Setters and getters
    public void setPlayerID(int p_playerID) {
        d_playerID = p_playerID;
    }

    public int getPlayerID() {
        return d_playerID;
    }

    public void setSourceCountryID(int p_sourceCountryID) {
        d_sourceCountryID = p_sourceCountryID;
    }

    public int getSourceCountryID(int p_sourceCountryID) {
        return d_sourceCountryID;
    }

    public void setTargetCountryID(int p_targetCountryID) {
        d_targetCountryID = p_targetCountryID;
    }

    public int setTargetCountryID() {
        return d_targetCountryID;
    }

    public void setNoOfArmyUnits(int p_noOfArmyUnits) {
        d_noOfArmyUnits = p_noOfArmyUnits;
    }

    public int getNoOfArmyUnits() {
        return d_noOfArmyUnits;
    }

    // TODO: Need to implement
    public void execute() {

    }
}
