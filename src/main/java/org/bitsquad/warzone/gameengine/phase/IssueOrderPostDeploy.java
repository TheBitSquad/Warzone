package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.card.Card;
import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.bitsquad.warzone.order.*;
import org.bitsquad.warzone.player.BasePlayer;
import org.bitsquad.warzone.player.Player;

import java.util.HashMap;

/**
 * IssueOrder Post Deploy Phase implementation
 */
public class IssueOrderPostDeploy extends IssueOrder{
    /**
     * Parameterized constructor
     * @param p_gameEngine
     */
    public IssueOrderPostDeploy(GameEngine p_gameEngine){
        super(p_gameEngine);
    }

    /**
     * Handler method for deploy command
     * @param p_targetCountryId Target Country ID
     * @param p_armyUnits Number of army units
     */
    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for advance command
     * @param p_countryNameFrom Source Country Name
     * @param p_targetCountryName Target Country Name
     * @param p_armyUnits Number of army units
     * @throws Exception
     */
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) throws Exception{
        BasePlayer l_currentPlayer = this.d_gameEngine.getCurrentPlayer();

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
        LogEntryBuffer.getInstance().log("Advance Order created. Source: " + p_countryNameFrom + " Target: " + p_targetCountryName + ", Army units: " + p_armyUnits);
    }

    /**
     * Handler method for bomb command
     * @param p_targetCountryId Country ID
     * @throws Exception
     */
    public void handleBomb(int p_targetCountryId) throws Exception{
        BasePlayer l_currentPlayer = this.d_gameEngine.getCurrentPlayer();
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
        LogEntryBuffer.getInstance().log("Bomb Order created. Target: " + p_targetCountryId);

    }

    /**
     * Handler method for bloackade command
     * @param p_targetCountryId target Country ID
     * @throws Exception
     */
    public void handleBlockade(int p_targetCountryId) throws Exception{
        // Check if player has Blockade card
        BasePlayer l_currentPlayer = this.d_gameEngine.getCurrentPlayer();
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
        LogEntryBuffer.getInstance().log("Blockade Order created. Target: " + p_targetCountryId);

    }

    /**
     * Handler method for airlift command
     * @param p_sourceCountryId Source Country ID
     * @param p_targetCountryId Target Country ID
     * @param p_numArmies
     * @throws Exception
     */
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) throws Exception{
        BasePlayer l_currentPlayer = this.d_gameEngine.getCurrentPlayer();
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
        LogEntryBuffer.getInstance().log("Airlift Order created. Source: " + p_sourceCountryId + " Target: " + p_targetCountryId + ", Army units: " + p_numArmies);

    }

    /**
     * Handler method for negotiate command
     * @param p_targetPlayerId Target Player ID
     * @throws Exception
     */
    public void handleNegotiate(int p_targetPlayerId) throws Exception{
        // Check if player has Diplomacy card
        BasePlayer l_currentPlayer = this.d_gameEngine.getCurrentPlayer();
        if (!l_currentPlayer.hasCard(Card.DiplomacyCard)) {
            throw new Exception("The player does not have the card");
        }

        BasePlayer l_targetPlayer = this.d_gameEngine.getPlayerByID(p_targetPlayerId);
        if (l_targetPlayer == null){
            throw new Exception("The target player ID invalid, no such player found.");
        } else if (l_targetPlayer.getId() == l_currentPlayer.getId()) {
            throw new Exception("The target player can not be the player itself");
        }

        l_currentPlayer.setCurrentOrder(new NegotiateOrder(l_currentPlayer, p_targetPlayerId));
        l_currentPlayer.issueOrder();
        LogEntryBuffer.getInstance().log("Negotiate Order created. Source player: " + l_currentPlayer.getId() + ", Target player:" + l_targetPlayer.getId());

    }

    /**
     * Handler method for commit command
     */
    public void handleCommit(){
        if (this.d_gameEngine.getCurrentPlayerIndex() == this.d_gameEngine.getGamePlayers().size() - 1) {
            LogEntryBuffer.getInstance().log("All Players committed");
            // We've taken orders from all players
            // Change state to Order Execution
            this.d_gameEngine.setPhase(new OrderExecution(this.d_gameEngine));
            // Execute the orders here itself.
            this.d_gameEngine.handleExecuteOrders();
        } else {
            // Change the phase to pre deploy for the next player
            LogEntryBuffer.getInstance().log("Player committed. Next player's turn");
            this.d_gameEngine.setCurrentPlayerIndexToNextPlayer();
            if(!this.d_gameEngine.getPhase().getClass().getSimpleName().equalsIgnoreCase("GameFinished")){
                this.d_gameEngine.setPhase(new IssueOrderPreDeploy(this.d_gameEngine));
            }
        }
    }
}
