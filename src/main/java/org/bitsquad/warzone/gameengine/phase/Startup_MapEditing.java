package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

// TODO: Implement mapediting handler methods. Copy from gameengine
public class Startup_MapEditing extends Startup {
    public Startup_MapEditing(GameEngine p_gameEngine){
        super(p_gameEngine);
    }
    public void handleLoadMap(String p_filename){
        // Change state here
        // this.d_gameEngine.setPhase(new Startup_PostLoadMap(this.d_gameEngine));
    }
    public void handleEditMap(String p_filename){
        
    }
    public void handleSaveMap(String p_filename){
        
    }
    public void handleValidateMap(){
        
    }
    public void handleEditContinent(int[] p_addArray, int[] p_removeIds){
        
    }
    public void handleEditCountry(int[] p_addIds, int[] p_removeIds){
        
    }
    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds){
        
    }
}
