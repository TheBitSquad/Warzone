package org.bitsquad.warzone.order;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.gameengine.policy.BlockadePolicy;
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

    @Override
    public boolean isValid(){
        return this.getPlayer().hasCountryWithID(this.getTargetCountryId());
    }
    /**
     * Executes the Order
     */
    @Override
    public void execute(){
        // Double the army units in the target country.
        Country l_targetCountry = this.getPlayer().getCountryByID(this.getTargetCountryId());
        if(l_targetCountry != null){
            l_targetCountry.setArmyValue(l_targetCountry.getArmyValue() * 2);
        }

        // Add the blockade policy to the policy manager.
        GameEngine.get_instance().getPolicyManager()
                .addPolicy(new BlockadePolicy(this.getPlayer(), l_targetCountry));
    }
}
