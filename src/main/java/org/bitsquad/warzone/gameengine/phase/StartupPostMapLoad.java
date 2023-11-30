package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

/**
 * Startup- After Loadmap Implementation
 */
class StartupPostMapLoad extends Startup{
    /**
     * Parameterized constructor
     * @param p_gameEngine
     */
    StartupPostMapLoad(GameEngine p_gameEngine){
        super(p_gameEngine);
    }

    /**
     * Handler for loadmap command
     * @param p_filename String filename
     */
    public void handleLoadMap(String p_filename){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler for editmap command
     * @param p_filename String filename
     */
    public void handleEditMap(String p_filename){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler for savemap command
     * @param p_filename
     */
    public void handleSaveMap(String p_filename, boolean p_saveAsConquestMap){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler for editcontinent command
     * @param p_addArray Ids and values to add
     * @param p_removeIds ids to remove
     */
    public void handleEditContinent(int[] p_addArray, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler for editcountry command
     * @param p_addIds
     * @param p_removeIds
     */
    public void handleEditCountry(int[] p_addIds, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler for editneighbor command
     * @param p_addIds
     * @param p_removeIds
     */
    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }
}
