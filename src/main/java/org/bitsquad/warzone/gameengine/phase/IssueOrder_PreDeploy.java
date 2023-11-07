package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.order.DeployOrder;
import org.bitsquad.warzone.order.Order;
import org.bitsquad.warzone.player.Player;

public class IssueOrder_PreDeploy extends IssueOrder{
    public IssueOrder_PreDeploy(GameEngine p_gameEngine){
        super(p_gameEngine);
    }

    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits) throws Exception{
        Player l_currentPlayer = this.d_gameEngine.getCurrentPlayer();

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

        if (l_currentPlayer.getAvailableArmyUnits() == 0) {
             this.d_gameEngine.setPhase(new IssueOrder_PostDeploy(this.d_gameEngine));
        }
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
}
