package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;

/**
 * Startup Map Editing phase implementation
 */
public class StartupMapEditing extends Startup {
    /**
     * Parameterized constructor
     * @param p_gameEngine GameEngine
     */
    public StartupMapEditing(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Handler method for loadmap command
     * @param p_filename String filename
     * @throws Exception
     */
    public void handleLoadMap(String p_filename) throws Exception {
        boolean resp = this.d_gameEngine.getGameMap().loadMap(p_filename);
        if (resp) {
            LogEntryBuffer.getInstance().log("Map loaded");
            this.d_gameEngine.setPhase(new StartupPostMapLoad(this.d_gameEngine));
        } else {
            throw new Exception("Invalid map or filename");
        }
    }

    /**
     * Handler method for editmap command
     * @param p_filename String filename
     */
    public void handleEditMap(String p_filename) {
        this.d_gameEngine.getGameMap().editMap(p_filename);
        LogEntryBuffer.getInstance().log("Map edited");
    }

    /**
     * Handler method for savemap command
     * @param p_filename String filename
     * @throws Exception
     */
    public void handleSaveMap(String p_filename) throws Exception {
        this.d_gameEngine.getGameMap().saveMap(p_filename);
        LogEntryBuffer.getInstance().log("Map saved");
    }

    /**
     * Handler method for editcontinent command
     * @param p_addArray ids and values to add
     * @param p_removeIds ids to remove
     */
    public void handleEditContinent(int[] p_addArray, int[] p_removeIds) {
        if (p_addArray != null) {
            for (int i = 0; i < p_addArray.length; i += 2) {
                int l_continent_id = p_addArray[i];
                int l_continent_value = p_addArray[i + 1];
                this.d_gameEngine.getGameMap().addContinent(l_continent_id, l_continent_value);
                LogEntryBuffer.getInstance().log("Continent " + l_continent_id + " added");
            }
        }
        if (p_removeIds != null) {
            for (int i = 0; i < p_removeIds.length; i++) {
                this.d_gameEngine.getGameMap().removeContinent(p_removeIds[i]);
                LogEntryBuffer.getInstance().log("Continent " + p_removeIds[i] + " removed");
            }
        }
    }

    /**
     * Handler method for editcountry command
     * @param p_addIds ids to add
     * @param p_removeIds ids to remove
     */
    public void handleEditCountry(int[] p_addIds, int[] p_removeIds) {
        if (p_addIds != null) {
            for (int i = 0; i < p_addIds.length; i += 2) {
                int l_countryId = p_addIds[i];
                int l_continentId = p_addIds[i + 1];
                boolean resp = this.d_gameEngine.getGameMap().addCountry(l_countryId, l_continentId);
                if (!resp) {
                    LogEntryBuffer.getInstance().log("Cannot add country " + l_countryId + " to continent " + l_continentId);
                } else {
                    LogEntryBuffer.getInstance().log("Added country: " + l_countryId + " to " + l_continentId);
                }
            }
        }
        if (p_removeIds != null) {
            for (int i = 0; i < p_removeIds.length; i++) {
                this.d_gameEngine.getGameMap().removeCountry(p_removeIds[i]);
                LogEntryBuffer.getInstance().log("Country " + p_removeIds[i] + " removed");
            }
        }
    }

    /**
     * Handler method for editneighbor command
     * @param p_addIds
     * @param p_removeIds
     */
    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds) {
        if (p_addIds != null) {
            for (int i = 0; i < p_addIds.length; i += 2) {
                this.d_gameEngine.getGameMap().addNeighbor(p_addIds[i], p_addIds[i + 1]);
                LogEntryBuffer.getInstance().log("Neighbor " + p_addIds[i] + " and " + p_addIds[i + 1] + " added");
            }
        }
        if (p_removeIds != null) {
            for (int i = 0; i < p_removeIds.length; i += 2) {
                this.d_gameEngine.getGameMap().removeNeighbor(p_removeIds[i], p_removeIds[i + 1]);
                LogEntryBuffer.getInstance().log("Neighbor " + p_removeIds[i] + " and " + p_removeIds[i + 1] + " removed");
            }
        }
    }
}
