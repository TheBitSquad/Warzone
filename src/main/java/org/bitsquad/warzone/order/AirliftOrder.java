package org.bitsquad.warzone.order;

public class AirliftOrder extends Order{

    public AirliftOrder(int p_playerId, int p_sourceCountryId, int p_targetCountryId, int p_armyUnits){
        super(p_playerId, p_sourceCountryId, p_targetCountryId, p_armyUnits);
    }

    @Override
    public void execute(){

    }
}
