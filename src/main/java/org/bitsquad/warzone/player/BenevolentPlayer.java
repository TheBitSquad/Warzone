package org.bitsquad.warzone.player;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.order.AdvanceOrder;
import org.bitsquad.warzone.order.DeployOrder;

import java.util.HashMap;

public class BenevolentPlayer extends BasePlayer {
    public BenevolentPlayer(String p_name) {
        super(p_name);
    }

    @Override
    public void issueOrder() {
        // Find the weakest country, and deploy to it
        int minValue = Integer.MAX_VALUE;
        Country l_weakestCountry = null;
        for(Country l_country: this.d_countriesOwned){
            if(l_country.getArmyValue() <= minValue){
                minValue = l_country.getArmyValue();
                l_weakestCountry = l_country;
            }
            if(l_weakestCountry.getArmyValue() == 0){
                break;
            }
        }
        this.d_orderList.add(new DeployOrder(this, l_weakestCountry.getCountryId(), this.getAvailableArmyUnits()));

        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : GameEngine.getInstance().getGameMap().getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }

        for(Country l_country: this.d_countriesOwned){
            if(l_country != l_weakestCountry && l_country.getNeighbors().contains(l_weakestCountry.getCountryId())){
                this.d_orderList.add(new AdvanceOrder(this,
                        l_country.getCountryId(), l_weakestCountry.getCountryId(),
                        l_country.getArmyValue()
                ));
            }
        }
    }
}
