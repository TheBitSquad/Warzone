package org.bitsquad.warzone.player;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.order.AdvanceOrder;
import org.bitsquad.warzone.order.DeployOrder;

import java.util.HashMap;

public class AggressivePlayer extends BasePlayer{
    public AggressivePlayer(String p_name) {
        super(p_name);
    }

    @Override
    public void issueOrder() {
        // Deploy to its strongest country
        // Decide the strongest country
        int l_maxUnits = 0;
        Country l_maxCountry = null;
        for(Country l_country: this.d_countriesOwned){
            if(l_country.getArmyValue() >= l_maxUnits){
                l_maxCountry = l_country;
                l_maxUnits = l_country.getArmyValue();
            }
        }

        this.d_orderList.add(new DeployOrder(
                this,
                l_maxCountry.getCountryId(),
                this.getAvailableArmyUnits()
        ));

        // Attacks with that country.
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : GameEngine.getInstance().getGameMap().getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }

        int l_minTargetValue = Integer.MAX_VALUE;
        Country l_targetAttackCountry = null;
        for(int l_targetCountryId: l_maxCountry.getNeighbors()){
            Country l_possibleTarget = l_allCountries.get(l_targetCountryId);
            if(l_minTargetValue == Integer.MAX_VALUE){
                l_minTargetValue = l_possibleTarget.getArmyValue();
                l_targetAttackCountry = l_possibleTarget;
            } else if (l_minTargetValue > l_possibleTarget.getArmyValue()){
                l_targetAttackCountry = l_possibleTarget;
                l_minTargetValue = l_possibleTarget.getArmyValue();
            }
        }
        
        if(l_maxCountry != null && l_targetAttackCountry != null)
            this.d_orderList.add(new AdvanceOrder(this, l_maxCountry.getCountryId(), l_targetAttackCountry.getCountryId(), l_maxCountry.getArmyValue()));

        // Moves armies to the strongest country
        for(Country l_country: this.d_countriesOwned){
            if(l_country != l_maxCountry && l_country.getNeighbors().contains(l_maxCountry.getCountryId())){
                this.d_orderList.add(new AdvanceOrder(this,
                        l_country.getCountryId(), l_maxCountry.getCountryId(),
                        l_country.getArmyValue()
                ));
            }
        }
    }
}
