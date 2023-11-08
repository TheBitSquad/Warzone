package org.bitsquad.warzone.order;


import org.bitsquad.warzone.player.Player;

/**
 * A new interface for Order, to implement the Command Pattern
 */
interface GameOrder{
    public void execute();
}


/**
 * Represents an order placed by a player
 *
 * This class defines the order placed with the ID of the player, the source and
 * target countries, the number of army units involved in the order
 * and the type of the action as per game-play actions.
 *
 * It acts as an intermediate to the GameOrder Interface by adding necessary functionality.
 * All other Orders extend this class, it acts as the Command Interface for the Command Pattern
 */
public abstract class Order implements GameOrder{
    protected Player d_playerInstance;
    private int d_sourceCountryId;
    private int d_targetCountryId;
    private int d_noOfArmyUnits;

    /**
     * Parametrized Constructor Order
     * 
     * @param p_player  Instance of the player
     * @param p_sourceCountryId Source Country ID
     * @param p_targetCountryId Target Country ID
     * @param p_noOfArmyUnits   No of Army Units
     */
    public Order(Player p_player, int p_sourceCountryId, int p_targetCountryId, int p_noOfArmyUnits) {
        this.d_playerInstance = p_player;
        this.d_sourceCountryId = p_sourceCountryId;
        this.d_targetCountryId = p_targetCountryId;
        this.d_noOfArmyUnits = p_noOfArmyUnits;
    }

    /**
     * toString method
     * @return string representation of class
     */
    @Override
    public String toString() {
        return "Order: " +
                "d_playerId=" + d_playerInstance.getId() +
                ", d_targetCountryId=" + d_targetCountryId +
                ", d_noOfArmyUnits=" + d_noOfArmyUnits;
    }

    /**
     * Getter for Player
     * 
     * @return Returns the player instance
     */
    public Player getPlayer() {
        return d_playerInstance;
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
     * Checks if a particular method is valid or not
     * @return boolean
     */
    abstract public boolean isValid();
}