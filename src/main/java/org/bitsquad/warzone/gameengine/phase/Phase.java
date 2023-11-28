package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;

import java.io.IOException;

/**
 * Phase interface. Represents the state in State Pattern
 */
public abstract class Phase {
    GameEngine d_gameEngine = null;

    /**
     * Parameterized Constructor
     * @param p_gameEngine GameEngine
     */
    protected Phase(GameEngine p_gameEngine){
        d_gameEngine = p_gameEngine;
    }

    /**
     * Handler for loadmap command
     * @param p_filename String filename
     * @throws Exception
     */
    abstract public void handleLoadMap(String p_filename) throws Exception;

    /**
     * Handler for editmap command
     * @param p_filename String filename
     */
    abstract public void handleEditMap(String p_filename) throws IOException;

    /**
     * Handler for savemap command
     * @param p_filename String filename
     * @throws Exception
     */
    abstract public void handleSaveMap(String p_filename, boolean p_saveAsConquestMap) throws Exception;

    /**
     * Handler for validatemap command
     */
    abstract public void handleValidateMap();

    /**
     * Handler for editcontinent command
     * @param p_addArray ids and values to add
     * @param p_removeIds ids to remove
     */
    abstract public void handleEditContinent(int[] p_addArray, int[] p_removeIds);

    /**
     * Handler method for editcountry command
     * @param p_addIds ids to add
     * @param p_removeIds ids to remove
     */
    abstract public void handleEditCountry(int[] p_addIds, int[] p_removeIds);

    /**
     * Handler method for editneighbor command
     * @param p_addIds
     * @param p_removeIds
     */
    abstract public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds);

    /**
     * Handler method for gameplayer command
     * @param p_addNames names to add
     * @param p_removeNames names to remove
     */
    abstract public void handleGamePlayer(String[] p_addNames, String[] p_removeNames);

    /**
     * Handler method for assigncountries command
     * @throws Exception
     */
    abstract public void handleAssignCountries() throws Exception;

    /**
     * Handler method for deploy command
     * @param p_targetCountryId Target Country ID
     * @param p_armyUnits Number of army units
     * @throws Exception
     */
    abstract public void handleDeployArmy(int p_targetCountryId, int p_armyUnits) throws Exception;

    /**
     * Handler method for advance command
     * @param p_countryNameFrom Source Country Name
     * @param p_targetCountryName Target Country Name
     * @param p_armyUnits Number of army units
     * @throws Exception
     */
    abstract public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) throws Exception;

    /**
     * Handler method for bomb command
     * @param p_countryId Country ID
     * @throws Exception
     */
    abstract public void handleBomb(int p_countryId) throws Exception;

    /**
     * Handler method for blockade command
     * @param p_targetCountryId target Country ID
     * @throws Exception
     */
    abstract public void handleBlockade(int p_targetCountryId) throws Exception;

    /**
     * Handler method for airlift command
     * @param p_sourceCountryId
     * @param p_targetCountryId
     * @param p_numArmies
     * @throws Exception
     */
    abstract public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) throws Exception;

    /**
     * Handler method for negotiate command
     * @param p_targetPlayerId Target Player ID
     * @throws Exception
     */
    abstract public void handleNegotiate(int p_targetPlayerId) throws Exception;

    /**
     * Handler method for commit command
     */
    abstract public void handleCommit();

    /**
     * Handler method to execute orders
     */
    abstract public void handleExecuteOrders();

    /**
     * Prints out and invalid command message
     */
    public void printInvalidCommandMessage(){
        LogEntryBuffer.getInstance().log("Invalid command in " + this.getClass().getSimpleName() + " phase.");
    }
}
