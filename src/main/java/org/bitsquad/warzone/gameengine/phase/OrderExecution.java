package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

/**
 * Implementation of OrderExecution phase
 */
public class OrderExecution extends Phase {

    /**
     * Parameterized constructor
     * @param p_engine
     */
    public OrderExecution(GameEngine p_engine) {
        super(p_engine);
    }

    /**
     * Handler method for loadmap command
     * @param p_filename String filename
     */
    public void handleLoadMap(String p_filename) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for editmap command
     * @param p_filename String filename
     */
    public void handleEditMap(String p_filename) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for savemap command
     * @param p_filename String filename
     */
    public void handleSaveMap(String p_filename) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for validatemap command
     */
    public void handleValidateMap() {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for editcontinent command
     * @param p_addArray ids and values to add
     * @param p_removeIds ids to remove
     */
    public void handleEditContinent(int[] p_addArray, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for editcountry command
     * @param p_addIds ids to add
     * @param p_removeIds ids to remove
     */
    public void handleEditCountry(int[] p_addIds, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for editneighbor command
     * @param p_addIds
     * @param p_removeIds
     */
    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for gameplayer command
     * @param p_addNames names to add
     * @param p_removeNames names to remove
     */
    public void handleGamePlayer(String[] p_addNames, String[] p_removeNames) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for assigncountries command
     */
    public void handleAssignCountries() {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for deploy command
     * @param p_targetCountryId Target Country ID
     * @param p_armyUnits Number of army units
     */
    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for advance command
     * @param p_countryNameFrom Source Country Name
     * @param p_targetCountryName Target Country Name
     * @param p_armyUnits Number of army units
     */
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for bomb command
     * @param p_countryId Country ID
     */
    public void handleBomb(int p_countryId) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for blockade command
     * @param p_targetCountryId target Country ID
     */
    public void handleBlockade(int p_targetCountryId) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for airlift command
     * @param p_sourceCountryId
     * @param p_targetCountryId
     * @param p_numArmies
     */
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for negotiate command
     * @param p_targetPlayerId Target Player ID
     */
    public void handleNegotiate(int p_targetPlayerId) {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for commit command
     */
    public void handleCommit() {
        printInvalidCommandMessage();
    }

    /**
     * Handler method for order execution
     */
    public void handleExecuteOrders() {
        this.d_gameEngine.executeOrders();
        this.d_gameEngine.nextRound();
        // FUTURE: Add check for player win condition.
        this.d_gameEngine.setPhase(new IssueOrderPreDeploy(this.d_gameEngine));
    }
}
