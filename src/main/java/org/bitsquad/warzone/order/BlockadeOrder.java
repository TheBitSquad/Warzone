package org.bitsquad.warzone.order;
import org.bitsquad.warzone.player.Player;

public class BlockadeOrder extends Order{

    /**
     * Parameterized Constructor
     * @param p_player Player
     * @param p_targetCountryId Target Country ID
     */
    public BlockadeOrder(Player p_player, int p_targetCountryId){
        super(p_player, -1, p_targetCountryId, 0);
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute(){

    }
}
