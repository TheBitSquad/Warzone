package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.bitsquad.warzone.order.DeployOrder;
import org.bitsquad.warzone.player.BasePlayer;
import org.bitsquad.warzone.player.Player;

/**
 * IssueOrder Pre Deploy Phase Implementation
 */
public class IssueOrderPreDeploy extends IssueOrder{
    /**
     * Parameterized construtor
     * @param p_gameEngine
     */
    public IssueOrderPreDeploy(GameEngine p_gameEngine){
        super(p_gameEngine);
    }

    /**
     * Handler method for deploy command
     * @param p_targetCountryId Target Country ID
     * @param p_armyUnits Number of army units
     * @throws Exception
     */
    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits) throws Exception{
        BasePlayer l_currentPlayer = this.d_gameEngine.getCurrentPlayer();

        // Check whether the player has the sufficient army units or not
        int l_newAvailableArmyUnits = l_currentPlayer.getAvailableArmyUnits() - p_armyUnits;
        if (l_newAvailableArmyUnits < 0) {
            throw new Exception("Insufficient army units");
        }

        // Check if the country ID is owned by player
        if (!l_currentPlayer.hasCountryWithID(p_targetCountryId)) {
            throw new Exception("Cannot not deploy to enemy territory");
        }

        // Issue the deployment order
        l_currentPlayer.setCurrentOrder(new DeployOrder(
                l_currentPlayer,
                p_targetCountryId,
                p_armyUnits
        ));
        l_currentPlayer.issueOrder();
        l_currentPlayer.setAvailableArmyUnits(l_newAvailableArmyUnits);
        LogEntryBuffer.getInstance().log("Deploy Order created. Target: " + p_targetCountryId + ", Army units: " + p_armyUnits);

        if (l_currentPlayer.getAvailableArmyUnits() == 0) {
             this.d_gameEngine.setPhase(new IssueOrderPostDeploy(this.d_gameEngine));
        }
    }

    /**
     * Handler method for advance command
     * @param p_countryNameFrom Source Country Name
     * @param p_targetCountryName Target Country Name
     * @param p_armyUnits Number of army units
     */
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for bomb command
     * @param p_countryId Country ID
     */
    public void handleBomb(int p_countryId){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for blockade command
     * @param p_targetCountryId target Country ID
     */
    public void handleBlockade(int p_targetCountryId){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for airlift command
     * @param p_sourceCountryId
     * @param p_targetCountryId
     * @param p_numArmies
     */
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for negotiate command
     * @param p_targetPlayerId Target Player ID
     */
    public void handleNegotiate(int p_targetPlayerId){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for commit command
     */
    public void handleCommit(){
        this.printInvalidCommandMessage();
    }
}
