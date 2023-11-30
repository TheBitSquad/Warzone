package org.bitsquad.warzone.player;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.gameengine.phase.IssueOrderPostDeploy;
import org.bitsquad.warzone.order.AdvanceOrder;
import org.bitsquad.warzone.order.DeployOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Represents the Random Player strategy
 */
public class RandomPlayer extends BasePlayer {

    /**
     * Parameterized constructor
     * @param p_name
     */
    public RandomPlayer(String p_name) {
        super(p_name);
    }

    /**
     * Implementation of issue order
     */
    @Override
    public void issueOrder() {
        if(this.d_countriesOwned == null || this.d_countriesOwned.isEmpty()) return;
        // Randomly choose a country
        Collections.shuffle(this.d_countriesOwned);
        Country l_deployTargetCountry = this.d_countriesOwned.get(0) ;

        // Deploy all army units
        this.d_orderList.add(new DeployOrder(
                this,
                l_deployTargetCountry.getCountryId(),
               this.d_availableArmyUnits
        ));

        // Advance army units randomly
        Random l_random = new Random();
        int l_randomIndex = l_random.nextInt(this.d_countriesOwned.size() );
        List<Country> l_sourceAdvanceCountries = this.d_countriesOwned.subList(0, l_randomIndex);
        for (Country l_source : l_sourceAdvanceCountries) {
            ArrayList<Integer> l_neighbors = l_source.getNeighbors();
            int l_targetCountryID = l_neighbors.get(l_random.nextInt(l_neighbors.size() ));
            if(l_source.getArmyValue() == 0){
                continue;
            }
            int l_numberOfAdvanceUnits = l_random.nextInt(l_source.getArmyValue());
            if (l_numberOfAdvanceUnits == 0) {
                continue;
            }

            this.d_orderList.add(new AdvanceOrder(
                    this,
                    l_source.getCountryId(),
                    l_targetCountryID,
                    l_numberOfAdvanceUnits
            ));
        }
    }
}
