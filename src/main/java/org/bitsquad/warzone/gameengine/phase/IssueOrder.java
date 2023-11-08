package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;

/**
 * IssueOrder Phase implementation
 */
abstract class IssueOrder extends Phase{
    /**
     * Parameterized constructor
     * @param p_gameEngine GameEngine
     */
    IssueOrder(GameEngine p_gameEngine){
        super(p_gameEngine);
    }

    /**
     * Handler method for loadmap command
     * @param p_filename String filename
     */
    public void handleLoadMap(String p_filename){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for loadmap command
     * @param p_filename String filename
     */
    public void handleEditMap(String p_filename){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for savemap command
     * @param p_filename String filename
     */
    public void handleSaveMap(String p_filename){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for validate command
     */
    public void handleValidateMap(){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for editcontinent command
     * @param p_addArray ids and values to add
     * @param p_removeIds ids to remove
     */
    public void handleEditContinent(int[] p_addArray, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for editcountry command
     * @param p_addIds ids to add
     * @param p_removeIds ids to remove
     */
    public void handleEditCountry(int[] p_addIds, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for editneighbor command
     * @param p_addIds
     * @param p_removeIds
     */
    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for gameplayer command
     * @param p_addNames names to add
     * @param p_removeNames names to remove
     */
    public void handleGamePlayer(String[] p_addNames, String[] p_removeNames){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for assigncountries command
     */
    public void handleAssignCountries(){
        this.printInvalidCommandMessage();
    }

    /**
     * Handler method for execute order
     */
    public void handleExecuteOrders(){this.printInvalidCommandMessage();}
}
