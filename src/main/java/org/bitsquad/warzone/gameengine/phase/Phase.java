package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;

/**
 * Phase interface. Represents the state in State Pattern
 */
public abstract class Phase {
    GameEngine d_gameEngine = null;
    protected Phase(GameEngine p_gameEngine){
        d_gameEngine = p_gameEngine;
    }
    abstract public void handleLoadMap(String p_filename) throws Exception;
    abstract public void handleEditMap(String p_filename);
    abstract public void handleSaveMap(String p_filename) throws Exception;
    abstract public void handleValidateMap();
    abstract public void handleEditContinent(int[] p_addArray, int[] p_removeIds);
    abstract public void handleEditCountry(int[] p_addIds, int[] p_removeIds);
    abstract public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds);
    abstract public void handleGamePlayer(String[] p_addNames, String[] p_removeNames);
    abstract public void handleAssignCountries() throws Exception;
    abstract public void handleDeployArmy(int p_targetCountryId, int p_armyUnits) throws Exception;
    abstract public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) throws Exception;
    abstract public void handleBomb(int p_countryId) throws Exception;
    abstract public void handleBlockade(int p_targetCountryId) throws Exception;
    abstract public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) throws Exception;
    abstract public void handleNegotiate(int p_targetPlayerId) throws Exception;
    abstract public void handleCommit();
    abstract public void executeOrders();
    public void printInvalidCommandMessage(){
        LogEntryBuffer.getInstance().log("Invalid command in " + this.getClass().getSimpleName() + " phase.");
    }
}
