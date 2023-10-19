package org.bitsquad.warzone.order;

/**
 * Represents the Advance Order
 */
public class AdvanceOrder extends Order{

    /**
     * Parameterized constructor
     * @param p_playerId player ID
     * @param p_sourceCountryId source country ID
     * @param p_targetCountryID target country ID
     * @param p_armyUnits number of army units
     */
    public AdvanceOrder(int p_playerId, int p_sourceCountryId, int p_targetCountryID, int p_armyUnits){
        super(p_playerId, p_sourceCountryId, p_targetCountryID, p_armyUnits);
    }
    @Override
    public void execute(){
        //TODO: Add functionality of advance order
    }
}
