package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

import java.io.IOException;

/**
 * Represents the GameFinished phase
 */
public class GameFinished extends Phase{

    /**
     * Parameterized Constructor
     *
     * @param p_gameEngine GameEngine
     */
    public GameFinished(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Handler for loadmap
     * @param p_filename String filename
     * @throws Exception
     */
    @Override
    public void handleLoadMap(String p_filename) throws Exception {
        printInvalidCommandMessage();
    }

    /**
     * Handler for handleEditMap
     * @param p_filename String filename
     * @throws IOException
     */
    @Override
    public void handleEditMap(String p_filename) throws IOException {
        printInvalidCommandMessage();
    }

    /**
     * Handler for savemap command
     * @param p_filename String filename
     * @param p_saveAsConquestMap Boolean
     * @throws Exception
     */
    @Override
    public void handleSaveMap(String p_filename, boolean p_saveAsConquestMap) throws Exception {
        printInvalidCommandMessage();
    }

    /**
     * Handler for validatemap command
     */
    @Override
    public void handleValidateMap() {
        printInvalidCommandMessage();
    }

    /**
     * Handler for editcontinent command
     * @param p_addArray ids and values to add
     * @param p_removeIds ids to remove
     */
    @Override
    public void handleEditContinent(int[] p_addArray, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    /**
     * Handler for editcountry command
     * @param p_addIds ids to add
     * @param p_removeIds ids to remove
     */
    @Override
    public void handleEditCountry(int[] p_addIds, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    /**
     * Handler for editneighbor command
     * @param p_addIds
     * @param p_removeIds
     */
    @Override
    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    /**
     * Handler for gameplayer command
     * @param p_addNames names to add
     * @param p_removeNames names to remove
     */
    @Override
    public void handleGamePlayer(String[] p_addNames, String[] p_removeNames) {
        printInvalidCommandMessage();
    }

    /**
     * Handler for assigncountries command
     * @throws Exception
     */
    @Override
    public void handleAssignCountries() throws Exception {
        printInvalidCommandMessage();
    }

    /**
     * Handler for deploy command
     * @param p_targetCountryId Target Country ID
     * @param p_armyUnits Number of army units
     * @throws Exception
     */
    @Override
    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits) throws Exception {
        printInvalidCommandMessage();
    }

    /**
     * Handler for advance command
     * @param p_countryNameFrom Source Country Name
     * @param p_targetCountryName Target Country Name
     * @param p_armyUnits Number of army units
     * @throws Exception
     */
    @Override
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) throws Exception {
        printInvalidCommandMessage();
    }

    /**
     * Handler for bomb command
     * @param p_countryId Country ID
     * @throws Exception
     */
    @Override
    public void handleBomb(int p_countryId) throws Exception {
        printInvalidCommandMessage();
    }

    /**
     * Handler for blockade command
     * @param p_targetCountryId target Country ID
     * @throws Exception
     */
    @Override
    public void handleBlockade(int p_targetCountryId) throws Exception {
        printInvalidCommandMessage();
    }

    /**
     * Handler for airlift command
     * @param p_sourceCountryId Source Country Id
     * @param p_targetCountryId Target Country Id
     * @param p_numArmies Number of armies
     * @throws Exception
     */
    @Override
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) throws Exception {
        printInvalidCommandMessage();
    }

    /**
     * Handler for negotiate command
     * @param p_targetPlayerId Target Player ID
     * @throws Exception
     */
    @Override
    public void handleNegotiate(int p_targetPlayerId) throws Exception {
        printInvalidCommandMessage();
    }

    /**
     * Handler for commit command
     */
    @Override
    public void handleCommit() {
        printInvalidCommandMessage();
    }

    /**
     * Handler for executeorder
     */
    @Override
    public void handleExecuteOrders() {
        printInvalidCommandMessage();
    }
}
