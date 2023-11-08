package org.bitsquad.warzone.order;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.Player;

import java.util.HashMap;

/**
 * Represents the Advance Order
 */
public class AdvanceOrder extends Order{

    private static float d_attackerKillsDefenderProbability = 0.6f;
    private static float d_defenderKillsAttackerProbability = 0.7f;

    /**
     * Parameterized constructor
     * @param p_player player instance
     * @param p_sourceCountryId source country ID
     * @param p_targetCountryID target country ID
     * @param p_armyUnits number of army units
     */
    public AdvanceOrder(Player p_player, int p_sourceCountryId, int p_targetCountryID, int p_armyUnits){
        super(p_player, p_sourceCountryId, p_targetCountryID, p_armyUnits);
    }

    @Override
    public boolean isValid(){
        // Completed: Check if countries are neighbors or not, and the source country belongs to the player
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : GameEngine.getInstance().getGameMap().getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }
        Country l_sourceCountry = l_allCountries.get(this.getSourceCountryId());
        Country l_targetCountry = l_allCountries.get(this.getTargetCountryId());

        return l_sourceCountry.getOwnedByPlayerId() == this.getPlayer().getId()
                && l_sourceCountry.getNeighbors().contains(this.getTargetCountryId());
    }
    /**
     * Helper class Pair
     * @param <T>
     */
    private class Pair<T>{
        public T d_first;
        public T d_second;

        public Pair(T p_first, T p_second){
            d_first = p_first;
            d_second = p_second;
        }
    }

    /**
     * Calculates and returns the expected survivors
     * @param p_numAttackers Number of attacking units
     * @param p_numDefenders Number of defending units
     * @return Pair<Integer>
     */
    private Pair<Integer> getExpectedSurvivors(int p_numAttackers, int p_numDefenders){
        int l_attackSurvivors = p_numAttackers;
        int l_defenderSurvivors = p_numDefenders;

        for(int i = 0; i<p_numAttackers; i++){
            if(Math.random() <= d_attackerKillsDefenderProbability) {
                l_defenderSurvivors--;
            }
        }

        for(int i = 0; i<p_numDefenders; i++){
            if(Math.random() <= d_defenderKillsAttackerProbability){
                l_attackSurvivors--;
            }
        }
        return new Pair<>(Math.max(l_attackSurvivors, 0), Math.max(l_defenderSurvivors, 0));
    }

    /**
     * Executes the Order
     */
    @Override
    public void execute(){
        Map l_gameMap; Player l_currentPlayer;
        l_gameMap = GameEngine.getInstance().getGameMap();
        l_currentPlayer = this.getPlayer();

        // Get all countries
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : l_gameMap.getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }

        Country l_sourceCountry = l_allCountries.get(getSourceCountryId());
        Country l_targetCountry = l_allCountries.get(getTargetCountryId());
        this.setNoOfArmyUnits(Math.min(l_sourceCountry.getArmyValue(), this.getNoOfArmyUnits()));

        if(l_currentPlayer.getCountriesOwned().contains(l_targetCountry)){
            // Advance army units from source to target
            l_sourceCountry.setArmyValue(l_sourceCountry.getArmyValue() - this.getNoOfArmyUnits());
            l_targetCountry.setArmyValue(l_targetCountry.getArmyValue() + this.getNoOfArmyUnits());
            LogEntryBuffer.getInstance().log("Moved " + this.getNoOfArmyUnits() + " from " + this.getSourceCountryId() + " to " + this.getTargetCountryId());
        } else {
            // Attack the target country
            int l_attackUnits = this.getNoOfArmyUnits();
            int l_defenderUnits = l_targetCountry.getArmyValue();

            Pair<Integer> l_survivors = getExpectedSurvivors(l_attackUnits, l_defenderUnits);

            int l_attackSurvivors = l_survivors.d_first;
            int l_defenderSurvivors = l_survivors.d_second;

            if(l_defenderSurvivors == 0 && l_attackSurvivors > 0){
                // Conquered a country successfully
                d_playerInstance.setHasNewTerritory(true);

                // Update country army values
                l_sourceCountry.setArmyValue(l_sourceCountry.getArmyValue() - getNoOfArmyUnits());
                l_targetCountry.setArmyValue(l_attackSurvivors);

                // Change country ownership
                for(Player l_player: GameEngine.getInstance().getGamePlayers()){
                    if(l_player.getId() == l_targetCountry.getOwnedByPlayerId()){
                        // Remove the target country from ownership of enemy
                        l_player.removeCountryOwned(l_targetCountry);
                    }
                }
                l_currentPlayer.addCountryOwned(l_targetCountry);
                l_targetCountry.setOwnedByPlayerId(l_currentPlayer.getId());
                LogEntryBuffer.getInstance().log("The player conquered the country " + this.getTargetCountryId() + ", number of survivors " + l_attackSurvivors);
            } else {
                l_sourceCountry.setArmyValue(l_sourceCountry.getArmyValue() - getNoOfArmyUnits() + l_attackSurvivors);
                l_targetCountry.setArmyValue(l_defenderSurvivors);
                LogEntryBuffer.getInstance().log("The player attacked the country " + this.getTargetCountryId() + ", number of survivors " + l_attackSurvivors + ", number of enemy survivors " + l_defenderSurvivors);
            }
        }
    }
}
