package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

/**
 * Phase interface. Represents the state in State Pattern
 */
public abstract class Phase {
    GameEngine d_gameEngine = null;
    protected Phase(GameEngine p_gameEngine){
        d_gameEngine = p_gameEngine;
    }
    abstract public void handleLoadMap(String p_filename);
    abstract public void handleEditMap(String p_filename);
    abstract public void handleSaveMap(String p_filename);
    abstract public void handleValidateMap();
    abstract public void handleEditContinent(int[] p_addArray, int[] p_removeIds);
    abstract public void handleEditCountry(int[] p_addIds, int[] p_removeIds);
    abstract public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds);
    abstract public void handleGamePlayer(String[] p_addNames, String[] p_removeNames);
    abstract public void handleAssignCountries();
    abstract public void handleDeployArmy(int p_targetCountryId, int p_armyUnits);
    abstract public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits);
    abstract public void handleBomb(int p_countryId);
    abstract public void handleBlockade(int p_targetCountryId);
    abstract public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies);
    abstract public void handleNegotiate(int p_targetPlayerId);
    abstract public void handleCommit();
    public void printInvalidCommandMessage(){
        System.out.println("Invalid command in " + this.getClass().getSimpleName() + " phase.");
    }
}
