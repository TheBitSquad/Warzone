package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;

public class OrderExecution extends Phase {

    public OrderExecution(GameEngine p_engine) {
        super(p_engine);
    }

    public void handleLoadMap(String p_filename) {
        printInvalidCommandMessage();
    }

    public void handleEditMap(String p_filename) {
        printInvalidCommandMessage();
    }

    public void handleSaveMap(String p_filename) {
        printInvalidCommandMessage();
    }

    public void handleValidateMap() {
        printInvalidCommandMessage();
    }

    public void handleEditContinent(int[] p_addArray, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    public void handleEditCountry(int[] p_addIds, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    public void handleGamePlayer(String[] p_addNames, String[] p_removeNames) {
        printInvalidCommandMessage();
    }

    public void handleAssignCountries() {
        printInvalidCommandMessage();
    }

    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits) {
        printInvalidCommandMessage();
    }

    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) {
        printInvalidCommandMessage();
    }

    public void handleBomb(int p_countryId) {
        printInvalidCommandMessage();
    }

    public void handleBlockade(int p_targetCountryId) {
        printInvalidCommandMessage();
    }

    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) {
        printInvalidCommandMessage();
    }

    public void handleNegotiate(int p_targetPlayerId) {
        printInvalidCommandMessage();
    }

    public void handleCommit() {
        printInvalidCommandMessage();
    }

    public void handleExecuteOrders() {
        this.d_gameEngine.executeOrders();
        this.d_gameEngine.nextRound();
        // FUTURE: Add check for player win condition.
        this.d_gameEngine.setPhase(new IssueOrder_PreDeploy(this.d_gameEngine));
    }
}
