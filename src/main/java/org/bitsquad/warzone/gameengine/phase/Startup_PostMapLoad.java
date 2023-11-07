package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

class Startup_PostMapLoad extends Startup{
    Startup_PostMapLoad(GameEngine p_gameEngine){
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
    public void handleEditContinent(int[] p_addArray, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }
    public void handleEditCountry(int[] p_addIds, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }
    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }
}
