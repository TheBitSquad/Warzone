package org.bitsquad.warzone.gameengine.phase;

import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;

public class Startup_MapEditing extends Startup {
    public Startup_MapEditing(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void handleLoadMap(String p_filename) throws Exception {
        boolean resp = this.d_gameEngine.getGameMap().loadMap(p_filename);
        if (resp) {
            this.d_gameEngine.setPhase(new Startup_PostMapLoad(this.d_gameEngine));
        } else {
            throw new Exception("Invalid map or filename");
        }
    }

    public void handleEditMap(String p_filename) {
        this.d_gameEngine.getGameMap().editMap(p_filename);
    }

    public void handleSaveMap(String p_filename) throws Exception {
        this.d_gameEngine.getGameMap().saveMap(p_filename);
    }

    public void handleEditContinent(int[] p_addArray, int[] p_removeIds) {
        if (p_addArray != null) {
            for (int i = 0; i < p_addArray.length; i += 2) {
                int l_continent_id = p_addArray[i];
                int l_continent_value = p_addArray[i + 1];
                this.d_gameEngine.getGameMap().addContinent(l_continent_id, l_continent_value);
            }
        }
        if (p_removeIds != null) {
            for (int i = 0; i < p_removeIds.length; i++) {
                this.d_gameEngine.getGameMap().removeContinent(p_removeIds[i]);
            }
        }
    }

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
            }
        }
    }

    public void handleEditNeighbor(int[] p_addIds, int[] p_removeIds) {
        if (p_addIds != null) {
            for (int i = 0; i < p_addIds.length; i += 2) {
                this.d_gameEngine.getGameMap().addNeighbor(p_addIds[i], p_addIds[i + 1]);
            }
        }
        if (p_removeIds != null) {
            for (int i = 0; i < p_removeIds.length; i += 2) {
                this.d_gameEngine.getGameMap().removeNeighbor(p_removeIds[i], p_removeIds[i + 1]);
            }
        }
    }
}
