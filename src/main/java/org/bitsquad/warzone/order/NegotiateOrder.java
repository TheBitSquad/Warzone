package org.bitsquad.warzone.order;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.gameengine.policy.NegotiatePolicy;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.bitsquad.warzone.player.Player;

import java.util.Iterator;

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

    /**
     * Checks if the order is valid
     * @return boolean
     */
    @Override
    public boolean isValid(){
        return true;
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute(){
        Iterator<Player> l_gamePlayerIterator = GameEngine.getInstance().getGamePlayers().iterator();
        while(l_gamePlayerIterator.hasNext()){
            Player l_player = l_gamePlayerIterator.next();
            if(l_player.getId() == this.d_targetPlayerId){
                GameEngine.getInstance().getPolicyManager().addPolicy(new NegotiatePolicy(this.getPlayer(), l_player));
                break;
            }
        }
        LogEntryBuffer.getInstance().log("Diplomacy applied between players " + this.getPlayer().getName() + " and " + GameEngine.getInstance().getPlayerByID(this.d_targetPlayerId).getName());
    }
}
