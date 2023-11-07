package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.card.Card;
import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.order.*;
import org.bitsquad.warzone.player.Player;

import java.util.HashMap;
import java.util.Iterator;

class IssueOrder_PostDeploy extends IssueOrder{
    IssueOrder_PostDeploy(GameEngine p_gameEngine){
        super(p_gameEngine);
    }
    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits){
        this.printInvalidCommandMessage();
    }
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) throws Exception{
        Player l_currentPlayer = this.d_gameEngine.getCurrentPlayer();

        // Check if valid source country
        Country l_sourceCountry = null, l_targetCountry = null;
        for (Country l_country : l_currentPlayer.getCountriesOwned()) {
            if (l_country.getCountryName().equalsIgnoreCase(p_countryNameFrom)) {
                l_sourceCountry = l_country;
                break;
            }
        }
        if (l_sourceCountry == null) {
            throw new Exception("Player does not own the source country");
        }

        // Check if valid target country
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : this.d_gameEngine.getGameMap().getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }
        for (Country l_tempCountry : l_allCountries.values()) {
            if (l_tempCountry.getCountryName().equalsIgnoreCase(p_targetCountryName)) {
                l_targetCountry = l_tempCountry;
                break;
            }
        }
        if (l_targetCountry == null) {
            throw new Exception("Target country doesn't exist");
        }

        // Check for valid army units
        if (l_sourceCountry.getArmyValue() < p_armyUnits) {
            throw new Exception("Insufficient army units in the country");
        }

        l_currentPlayer.setCurrentOrder(
                new AdvanceOrder(l_currentPlayer,
                        l_sourceCountry.getCountryId(),
                        l_targetCountry.getCountryId(),
                        p_armyUnits
                )
        );
        l_currentPlayer.issueOrder();
    }
    public void handleBomb(int p_targetCountryId) throws Exception{
        Player l_currentPlayer = this.d_gameEngine.getCurrentPlayer();
        // Check if player has bomb card
        if (!l_currentPlayer.hasCard(Card.BombCard)) {
            throw new Exception("The player does not have the card");
        }

        // Check if the target country is owned by player
        Country l_targetCountry = l_currentPlayer.getCountryByID(p_targetCountryId);
        if(l_targetCountry != null){
            throw new Exception("Player trying to bomb own country. Not allowed.");
        }

        // Check if target country exists
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : this.d_gameEngine.getGameMap().getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }
        if (!l_allCountries.containsKey(p_targetCountryId)) {
            throw new Exception("Target Country does not exist!");
        }

        // Issue the bomb order
        l_currentPlayer.setCurrentOrder(new BombOrder(l_currentPlayer, p_targetCountryId));
        l_currentPlayer.issueOrder();
    }
    public void handleBlockade(int p_targetCountryId) throws Exception{
        // Check if player has Blockade card
        Player l_currentPlayer = this.d_gameEngine.getCurrentPlayer();
        if (!l_currentPlayer.hasCard(Card.BlockadeCard)) {
            throw new Exception("The player does not have the card");
        }

        // Check if the player owns the country
        if (!l_currentPlayer.hasCountryWithID(p_targetCountryId)) {
            throw new Exception("The player does not own the country");
        }

        // Issue Blockade order
        l_currentPlayer.setCurrentOrder(new BlockadeOrder(l_currentPlayer, p_targetCountryId));
        l_currentPlayer.issueOrder();
    }
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) throws Exception{
        Player l_currentPlayer = this.d_gameEngine.getCurrentPlayer();
        // Check if player has airlift card
        if (!l_currentPlayer.hasCard(Card.AirliftCard)) {
            throw new Exception("The player does not have the card");
        }

        // Check if the source and target country is owned by player
        Country l_sourceCountry = l_currentPlayer.getCountryByID(p_sourceCountryId);
        Country l_targetCountry = l_currentPlayer.getCountryByID(p_targetCountryId);

        if (l_sourceCountry == null || l_targetCountry == null) {
            String l_errStr = "";
            if (l_sourceCountry == null) {
                l_errStr += "Player doesn't own source country: " + p_sourceCountryId + ". ";
            }
            if (l_targetCountry == null) {
                l_errStr += "Player doesn't own target country: " + p_targetCountryId + ". ";
            }
            throw new Exception(l_errStr);
        }

        // Check whether the player has the sufficient army units or not
        int l_newAvailableArmyUnits = l_sourceCountry.getArmyValue() - p_numArmies;
        if (l_newAvailableArmyUnits < 0) {
            throw new Exception("Insufficient army units in country: " + p_sourceCountryId);
        }

        // Issue the airlift order
        l_currentPlayer.setCurrentOrder(new AirliftOrder(l_currentPlayer, p_sourceCountryId, p_targetCountryId, p_numArmies));
        l_currentPlayer.issueOrder();
    }
    public void handleNegotiate(int p_targetPlayerId) throws Exception{
        // Check if player has Diplomacy card
        Player l_currentPlayer = this.d_gameEngine.getCurrentPlayer();
        if (!l_currentPlayer.hasCard(Card.DiplomacyCard)) {
            throw new Exception("The player does not have the card");
        }

        Player l_targetPlayer = this.d_gameEngine.getPlayerByID(p_targetPlayerId);
        if (l_targetPlayer == null){
            throw new Exception("The target player ID invalid, no such player found.");
        } else if (l_targetPlayer.getId() == l_currentPlayer.getId()) {
            throw new Exception("The target player can not be the player itself");
        }

        l_currentPlayer.setCurrentOrder(new NegotiateOrder(l_currentPlayer, p_targetPlayerId));
        l_currentPlayer.issueOrder();
    }
    public void handleCommit(){
        if (this.d_gameEngine.getCurrentPlayerIndex() == this.d_gameEngine.getGamePlayers().size() - 1) {
            // We've taken orders from all players
            // Change state to Order Execution
            this.d_gameEngine.setPhase(new OrderExecution(this.d_gameEngine));
            // Execute the orders here itself.
            this.d_gameEngine.executeOrders();
        } else {
            // Change the phase to pre deploy for the next player
            this.d_gameEngine.setCurrentPlayerIndexToNextPlayer();
            this.d_gameEngine.setPhase(new IssueOrder_PreDeploy(this.d_gameEngine));
        }
    }
}
