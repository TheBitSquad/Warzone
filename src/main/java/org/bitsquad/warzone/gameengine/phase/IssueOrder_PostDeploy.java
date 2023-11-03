package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

// TODO: Implement PostDeploy
class IssueOrder_PostDeploy extends IssueOrder{
    IssueOrder_PostDeploy(GameEngine p_gameEngine){
        super(p_gameEngine);
    }
    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits){
        this.printInvalidCommandMessage();
    }
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits){
        
    }
    public void handleBomb(int p_countryId){
        
    }
    public void handleBlockade(int p_targetCountryId){
        
    }
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies){
        
    }
    public void handleNegotiate(int p_targetPlayerId){
        
    }
    public void handleCommit(){
        // Change state here
        // this.d_gameEngine.setPhase(new OrderExecution(this.d_gameEngine));
        // this.d_gameEngine.handleExecuteOrders(); or something similar
    }
}
