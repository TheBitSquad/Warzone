package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

import java.io.IOException;

public class GameFinished extends Phase{

    /**
     * Parameterized Constructor
     *
     * @param p_gameEngine GameEngine
     */
    public GameFinished(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void handleLoadMap(String p_filename) throws Exception {
        printInvalidCommandMessage();
    }

    @Override
    public void handleEditMap(String p_filename) throws IOException {
        printInvalidCommandMessage();
    }

    @Override
    public void handleSaveMap(String p_filename, boolean p_saveAsConquestMap) throws Exception {
        printInvalidCommandMessage();
    }

    @Override
    public void handleValidateMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void handleEditContinent(int[] p_addArray, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    @Override
    public void handleEditCountry(int[] p_addIds, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    @Override
    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds) {
        printInvalidCommandMessage();
    }

    @Override
    public void handleGamePlayer(String[] p_addNames, String[] p_removeNames) {
        printInvalidCommandMessage();
    }

    @Override
    public void handleAssignCountries() throws Exception {
        printInvalidCommandMessage();
    }

    @Override
    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits) throws Exception {
        printInvalidCommandMessage();
    }

    @Override
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) throws Exception {
        printInvalidCommandMessage();
    }

    @Override
    public void handleBomb(int p_countryId) throws Exception {
        printInvalidCommandMessage();
    }

    @Override
    public void handleBlockade(int p_targetCountryId) throws Exception {
        printInvalidCommandMessage();
    }

    @Override
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) throws Exception {
        printInvalidCommandMessage();
    }

    @Override
    public void handleNegotiate(int p_targetPlayerId) throws Exception {
        printInvalidCommandMessage();
    }

    @Override
    public void handleCommit() {
        printInvalidCommandMessage();
    }

    @Override
    public void handleExecuteOrders() {
        printInvalidCommandMessage();
    }
}
