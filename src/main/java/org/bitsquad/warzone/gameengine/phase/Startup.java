package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Startup Phase implementation
 */
abstract class Startup extends Phase {

    /**
     * Parameterized constructor
     * @param p_gameEngine
     */
    Startup(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Handler method for validatemap command
     */
    public void handleValidateMap() {
        if (this.d_gameEngine.getGameMap().validateMap()) {
            LogEntryBuffer.getInstance().log("Valid map");
        } else {
            LogEntryBuffer.getInstance().log("Invalid map");
        }
    }

    /**
     * Handler method for gameplayer command
     * @param p_addNames names to add
     * @param p_removeNames names to remove
     */
    public void handleGamePlayer(String[] p_addNames, String[] p_removeNames) {
        if (p_addNames != null) {
            for (String l_addName : p_addNames) {
                try {
                    this.d_gameEngine.handleAddPlayer(l_addName);
                    LogEntryBuffer.getInstance().log("new player " + l_addName + " is added");
                } catch (Exception e) {
                    LogEntryBuffer.getInstance().log(e.getMessage());
                }
            }
        }
        if (p_removeNames != null) {
            for (String l_removeName : p_removeNames) {
                try {
                    this.d_gameEngine.handleRemovePlayer(l_removeName);
                    LogEntryBuffer.getInstance().log("removed player " + l_removeName);
                } catch (Exception e) {
                    LogEntryBuffer.getInstance().log(e.getMessage());
                }
            }
        }
    }

    /**
     * Handler method for assigncountries command
     * @throws Exception
     */
    public void handleAssignCountries() throws Exception {
        if (!this.d_gameEngine.getGameMap().validateMap()) {
            throw new Exception("Not a valid map cannot begin gameplay");
        }
        if (this.d_gameEngine.getGamePlayers().isEmpty()) {
            throw new Exception("No players have been added yet");
        }

        // Create a map of whole countries
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : this.d_gameEngine.getGameMap().getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }

        // Split countries between players
        ArrayList<Integer> l_countryIDs = new ArrayList<>(l_allCountries.keySet());
        Collections.shuffle(l_countryIDs);

        if (l_countryIDs.isEmpty()) {
            throw new Exception("No countries have been added");
        }
        int l_intervals = l_countryIDs.size() / this.d_gameEngine.getGamePlayers().size();
        for (int i = 0; i < l_countryIDs.size(); i++) {
            int assignee = Math.min(i / l_intervals, this.d_gameEngine.getGamePlayers().size() - 1);
            this.d_gameEngine.getGamePlayers().get(assignee).addCountryOwned(l_allCountries.get(l_countryIDs.get(i)));
            l_allCountries.get(l_countryIDs.get(i)).setOwnedByPlayerId(this.d_gameEngine.getGamePlayers().get(assignee).getId());
        }

        this.d_gameEngine.nextRound();
        if(!this.d_gameEngine.getPhase().getClass().getSimpleName().equalsIgnoreCase("GameFinished")){
            this.d_gameEngine.setPhase(new IssueOrderPreDeploy(this.d_gameEngine));
        }
    }

    /**
     * Handler method for deploy command
     * @param p_targetCountryId Target Country ID
     * @param p_armyUnits Number of army units
     */
    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits) {
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for advance command
     * @param p_countryNameFrom Source Country Name
     * @param p_targetCountryName Target Country Name
     * @param p_armyUnits Number of army units
     */
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) {
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for bomb command
     * @param p_countryId Country ID
     */
    public void handleBomb(int p_countryId) {
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for blockade command
     * @param p_targetCountryId target Country ID
     */
    public void handleBlockade(int p_targetCountryId) {
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for airlift command
     * @param p_sourceCountryId
     * @param p_targetCountryId
     * @param p_numArmies
     */
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) {
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for negotiate command
     * @param p_targetPlayerId Target Player ID
     */
    public void handleNegotiate(int p_targetPlayerId) {
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for commit command
     */
    public void handleCommit() {
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for execute orders
     */
    public void handleExecuteOrders() {
        this.printInvalidCommandMessage();
    }
}
