package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

abstract class Startup extends Phase {
    Startup(GameEngine p_gameEngine){
        super(p_gameEngine);
    }

    public void handleGamePlayer(String[] p_addNames, String[] p_removeNames){
        // TODO: Add handle game player code
    }

    public void handleAssignCountries(){
        // TODO: Add handle assign countries code
        // Change state here

        // this.d_gameEngine.setPhase(new IssueOrder_PreDeploy(this.d_gameEngine));
    }

    public void handleDeployArmy(int p_targetCountryId, int p_armyUnits){
        this.printInvalidCommandMessage();
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
