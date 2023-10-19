package org.bitsquad.warzone.order;

public class BlockadeOrder extends Order{

    /**
     * Parameterized Constructor
     * @param p_playerId Player ID
     * @param p_targetCountryId Target Country ID
     */
    public BlockadeOrder(int p_playerId, int p_targetCountryId){
        super(p_playerId, -1, p_targetCountryId, 0);
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute(){

    }
}
