package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

abstract class Startup extends Phase {
    Startup(GameEngine p_gameEngine){
        super(p_gameEngine);
    }

    public void handleValidateMap(){
        if (this.d_gameEngine.getGameMap().validateMap()) {
            System.out.println("Valid map");
        } else {
            System.out.println("Invalid map");
        }
    }

    public void handleGamePlayer(String[] p_addNames, String[] p_removeNames){
        if (p_addNames != null) {
            for (String l_addName : p_addNames) {
                try {
                    this.d_gameEngine.handleAddPlayer(l_addName);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        if (p_removeNames != null) {
            for (String l_removeName : p_removeNames) {
                try {
                    this.d_gameEngine.handleRemovePlayer(l_removeName);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public void handleAssignCountries() throws Exception{
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

         this.d_gameEngine.setPhase(new IssueOrder_PreDeploy(this.d_gameEngine));
    }

    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits){
        this.printInvalidCommandMessage();
    }
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits){
        this.printInvalidCommandMessage();
    }
    public void handleBomb(int p_countryId){
        this.printInvalidCommandMessage();
    }
    public void handleBlockade(int p_targetCountryId){
        this.printInvalidCommandMessage();
    }
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies){
        this.printInvalidCommandMessage();
    }
    public void handleNegotiate(int p_targetPlayerId){
        this.printInvalidCommandMessage();
    }
    public void handleCommit(){
        this.printInvalidCommandMessage();
    }
    public void executeOrders(){this.printInvalidCommandMessage();}
}
