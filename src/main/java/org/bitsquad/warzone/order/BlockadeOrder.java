package org.bitsquad.warzone.order;

public class BlockadeOrder extends Order{

    public BlockadeOrder(int p_playerId, int p_targetCountryId){
        super(p_playerId, -1, p_targetCountryId, 0);
    }

    @Override
    public void execute(){

    }
}
