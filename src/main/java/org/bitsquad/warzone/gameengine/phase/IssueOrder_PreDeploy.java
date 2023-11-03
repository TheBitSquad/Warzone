package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

class IssueOrder_PreDeploy extends IssueOrder{
    IssueOrder_PreDeploy(GameEngine p_gameEngine){
        super(p_gameEngine);
    }

    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits){
        // TODO: Implement handle Deploy Army
        // Change state here
        // this.d_gameEngine.setPhase(new IssueOrder_PostDeploy(this.d_gameEngine));
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
