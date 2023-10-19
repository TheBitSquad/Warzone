package org.bitsquad.warzone.order;

public class NegotiateOrder extends Order{
    int d_targetPlayerId;
    public NegotiateOrder(int p_sourcePlayerId, int p_targetPlayerId){
        super(p_sourcePlayerId, -1, -1, 0);
        d_targetPlayerId = p_targetPlayerId;
    }

    @Override
    public void execute(){

    }
}
