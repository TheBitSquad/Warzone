package org.bitsquad.warzone.order;
import org.bitsquad.warzone.player.Player;

/**
 * Represents the Negotiate Card Order
 */
public class NegotiateOrder extends Order{
    private int d_targetPlayerId;

    /**
     * Parameterized Constructor
     * @param p_player Source Player instance
     * @param p_targetPlayerId Target Player ID
     */
    public NegotiateOrder(Player p_player, int p_targetPlayerId){
        super(p_player, -1, -1, 0);
        this.d_targetPlayerId = p_targetPlayerId;
    }

    @Override
    public boolean isValid(){
        return true;
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute(){

    }
}
