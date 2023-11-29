package org.bitsquad.warzone.player;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.gameengine.phase.IssueOrderPostDeploy;
import org.bitsquad.warzone.gamerunner.GameRunner;

import java.util.HashMap;

public class CheaterPlayer extends BasePlayer{
    public CheaterPlayer(String p_name) {
        super(p_name);
    }

    @Override
    public void issueOrder() {
        // Conquer all immediate neighboring enemy countries
        // Double all armies on countries that have enemy neighbors
        // All this directly changes the map, does not issue order.

        // Get all countries
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : GameEngine.getInstance().getGameMap().getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }

        // Conquer all immediate neighboring countries
        for(Country l_ownCountry: this.d_countriesOwned){
            for(int l_neighborCountryId: l_ownCountry.getNeighbors()){
                Country l_targetNeighborCountry = l_allCountries.get(l_neighborCountryId);
                // If player doesn't own the country
                if(l_targetNeighborCountry.getOwnedByPlayerId() != this.getId()){
                    // Change country ownership
                    for(BasePlayer l_player: GameEngine.getInstance().getGamePlayers()){
                        if(l_player.getId() == l_targetNeighborCountry.getOwnedByPlayerId()){
                            // Remove the target country from ownership of enemy
                            l_player.removeCountryOwned(l_targetNeighborCountry);
                        }
                    }
                    this.addCountryOwned(l_targetNeighborCountry);
                    l_targetNeighborCountry.setOwnedByPlayerId(this.getId());
                }
            }
        }

        // Double armies on countries that have neighboring enemies
        for(Country l_ownCountry: this.d_countriesOwned){
            boolean neighborIsEnemy = false;
            for(int l_neighborCountryId: l_ownCountry.getNeighbors()){
                Country l_targetNeighborCountry = l_allCountries.get(l_neighborCountryId);
                // If player doesn't own the country
                if(l_targetNeighborCountry.getOwnedByPlayerId() != this.getId()) {
                    neighborIsEnemy = true;
                    break;
                }
            }
            if(neighborIsEnemy) {
                l_ownCountry.setArmyValue(l_ownCountry.getArmyValue() * 2);
            }
        }
    }
}
