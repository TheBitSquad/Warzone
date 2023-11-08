package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

abstract class IssueOrder extends Phase{
    IssueOrder(GameEngine p_gameEngine){
        super(p_gameEngine);
    }

    public void handleLoadMap(String p_filename){
        this.printInvalidCommandMessage();
    }
    public void handleEditMap(String p_filename){
        this.printInvalidCommandMessage();
    }
    public void handleSaveMap(String p_filename){
        this.printInvalidCommandMessage();
    }
    public void handleValidateMap(){
        this.printInvalidCommandMessage();
    }
    public void handleEditContinent(int[] p_addArray, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }
    public void handleEditCountry(int[] p_addIds, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }
    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }
    public void handleGamePlayer(String[] p_addNames, String[] p_removeNames){
        this.printInvalidCommandMessage();
    }
    public void handleAssignCountries(){
        this.printInvalidCommandMessage();
    }
    public void handleExecuteOrders(){this.printInvalidCommandMessage();}
}
