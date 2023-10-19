package org.bitsquad.warzone.order;

/**
 * This class represents the Bomb Card Order
 */
public class BombOrder extends Order{

    public BombOrder(int p_playerId, int p_targetCountryID){
        super(p_playerId, -1, p_targetCountryID, 0);
    }
    @Override
    public void execute(){
        //TODO: Add functionality of Bomb
    }
}
