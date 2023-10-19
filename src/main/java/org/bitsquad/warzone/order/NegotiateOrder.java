package org.bitsquad.warzone.order;

/**
 * Represents the Negotiate Card Order
 */
public class NegotiateOrder extends Order{
    private int d_targetPlayerId;

    /**
     * Parameterized Constructor
     * @param p_sourcePlayerId Source Player ID
     * @param p_targetPlayerId Target Player ID
     */
    public NegotiateOrder(int p_sourcePlayerId, int p_targetPlayerId){
        super(p_sourcePlayerId, -1, -1, 0);
        this.d_targetPlayerId = p_targetPlayerId;
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute(){

    }
}
