/**
 * This file contains all the commands which are represented each in their own class, implemented with the help of picocli.
 */
package org.bitsquad.warzone.cli;

import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * editcontinent command class
 */
@Command(name = "editcontinent", mixinStandardHelpOptions = true, version = "1.0.0")
class EditContinent implements Callable<Integer> {
    @Option(names = "-add", arity = "2", paramLabel = "CONTINENT_ID VALUE",
            description = "Enter continent and value respectively")
    int[] d_addArray;
    @Option(names = "-remove", arity = "1", paramLabel = "CONTINENT_ID",
            description = "Enter the continent ID to remove")
    int[] d_removeIds;
    @Spec
    CommandSpec d_spec; // injected by picocli

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        validate();
        GameEngine.getInstance().handleEditContinent(d_addArray, d_removeIds);
        return 0;
    }

    /**
     * Validation for the command
     */
    private void validate() {
        if (missing(d_addArray) && missing(d_removeIds)) {
            throw new ParameterException(d_spec.commandLine(),
                    "Missing option: at least one of the " +
                            "'-add' or '-remove' options must be specified.");
        }
    }

    /**
     * Helper function to check if given option is present
     * @param p_list list of option inputs
     * @return boolean if the option is missing input
     */
    private boolean missing(int[] p_list) {
        return p_list == null || p_list.length == 0;
    }
}

/**
 * editcountry command class
 */
@Command(name = "editcountry")
class EditCountry implements Callable<Integer> {
    @Option(names = "-add", arity = "2",
            description = "Enter country ID and continent ID respectively")
    int[] d_addIds;
    @Option(names = "-remove", arity = "1",
            description = "Enter the country ID to remove")
    int[] d_removeIds;
    @Spec
    CommandSpec d_spec; // injected by picocli

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        validate();
        GameEngine.getInstance().handleEditCountry(d_addIds, d_removeIds);
        return 0;
    }

    /**
     * Validation for the command
     */
    private void validate() {
        if (missing(d_addIds) && missing(d_removeIds)) {
            throw new ParameterException(d_spec.commandLine(),
                    "Missing option: at least one of the " +
                            "'-add' or '-remove' options must be specified.");
        }
    }

    /**
     * Helper function to check if given option is present
     * @param p_list list of option inputs
     * @return boolean if the option is missing input
     */
    private boolean missing(int[] p_list) {
        return p_list == null || p_list.length == 0;
    }
}

/**
 * editneighbor command class
 */
@Command(name = "editneighbor")
class EditNeighbor implements Callable<Integer> {

    @Option(names = "-add", arity = "2",
            description = "Enter country ID and neigbour country ID respectively"
    )
    int[] d_addIds;
    @Option(names = "-remove", arity = "2",
            description = "Enter the country ID and neighbour country ID to remove"
    )
    int[] d_removeIds;
    @Spec
    CommandSpec d_spec; // injected by picocli

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        validate();
        GameEngine.getInstance().handleEditNeighbor(d_addIds, d_removeIds);
        return 0;
    }

    /**
     * Validation for the command
     */
    private void validate() {
        if (missing(d_addIds) && missing(d_removeIds)) {
            throw new ParameterException(d_spec.commandLine(),
                    "Missing option: at least one of the " +
                            "'-add' or '-remove' options must be specified.");
        }
    }

    /**
     * Helper function to check if given option is present
     * @param p_list list of option inputs
     * @return boolean if the option is missing input
     */
    private boolean missing(int[] p_list) {
        return p_list == null || p_list.length == 0;
    }
}

/**
 * showmap command class
 */
@Command(name = "showmap")
class ShowMap implements Callable<Integer> {
    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        GameEngine.getInstance().getGameMap().visualizeGraph();
        LogEntryBuffer.getInstance().log("Showing Map");
        return 0;
    }
}

/**
 * savemap command class
 */
@Command(name = "savemap")
class SaveMap implements Callable<Integer> {

    @Option(names="-c",
            paramLabel = "SaveAsConquestMap",
            description = "Conquest Map type (Default: false)"
    )
    boolean c;

    @Parameters(index = "0",
            paramLabel = "Filename",
            description = "Filename of the map")
    String d_filename;
    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try {
            GameEngine.getInstance().handleSaveMap(d_filename,c);
        } catch (Exception e) {
            LogEntryBuffer.getInstance().log(e.getMessage());
        }
        return 0;
    }
}

/**
 * editmap command class
 */
@Command(name = "editmap")
class EditMap implements Callable<Integer> {
    @Parameters(index = "0",
            paramLabel = "Filename",
            description = "Filename of the map")
    String d_filename;

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try{
            GameEngine.getInstance().handleEditMap(d_filename);
        } catch (Exception e){
            LogEntryBuffer.getInstance().log(e.getMessage());
            return 1;
        }
        return 0;
    }
}

/**
 * validatemap command class
 */
@Command(name = "validatemap")
class ValidateMap implements Callable<Integer> {
    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        GameEngine.getInstance().handleValidateMap();
        return 0;
    }
}

/**
 * loadmap command class
 */
@Command(name = "loadmap")
class LoadMap implements Callable<Integer> {
    @Parameters(index = "0",
            paramLabel = "Filename of the map",
            description = "Filename of the map")
    String d_filename;

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try {
            GameEngine.getInstance().handleLoadMap(d_filename);
        } catch (Exception e) {
            LogEntryBuffer.getInstance().log(e.getMessage());
            return 1;
        }
        return 0;
    }
}

/**
 * gameplayer command class
 */
@Command(name = "gameplayer")
class GamePlayer implements Callable<Integer> {
    @Option(names = "-add",
            description = "Enter playername/type")
    String[] d_add_names;
    @Option(names = "-remove",
            description = "Enter the player name")
    String[] d_remove_names;
    @Spec
    CommandSpec d_spec; // injected by picocli

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        validate();
        GameEngine.getInstance().handleGamePlayer(d_add_names, d_remove_names);
        return 0;
    }

    /**
     * Validation for the command
     */
    private void validate() {
        if (missing(d_add_names) && missing(d_remove_names)) {
            throw new ParameterException(d_spec.commandLine(),
                    "Missing option: at least one of the " +
                            "'-add' or '-remove' options must be specified.");
        }
    }

    /**
     * Helper function to check if given option is present
     * @param p_list list of option inputs
     * @return boolean if the option is missing input
     */
    private boolean missing(String[] p_list) {
        return p_list == null || p_list.length == 0;
    }

}

/**
 * assigncountries command class
 */
@Command(name = "assigncountries")
class AssignCountries implements Callable<Integer> {
    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try {
            GameEngine.getInstance().handleAssignCountries();
        } catch (Exception e) {
            LogEntryBuffer.getInstance().log(e.getMessage());
        }
        return 0;
    }
}

/**
 * deploy command class
 */
@Command(name = "deploy")
class Deploy implements Callable<Integer> {
    @Parameters(index = "0", description = "country ID")
    int d_countryID;
    @Parameters(index = "1", description = "Number of army units")
    int d_num;

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try {
            GameEngine.getInstance().handleDeployArmy(d_countryID, d_num);
        } catch (Exception e) {
            LogEntryBuffer.getInstance().log(e.getMessage());
            return 1;
        }
        return 0;
    }
}

/**
 * advance command class
 */
@Command(name = "advance")
class Advance implements Callable<Integer> {
    @Parameters(index = "0", description = "Source country name")
    String d_countryNameFrom;

    @Parameters(index = "1", description = "Destination country name")
    String d_countryNameTo;
    @Parameters(index = "2", description = "Number of army units")
    int d_num;

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try {
            GameEngine.getInstance().handleAdvance(d_countryNameFrom, d_countryNameTo, d_num);
        } catch (Exception e) {
            LogEntryBuffer.getInstance().log(e.getMessage());
            return 1;
        }
        return 0;
    }
}

/**
 * bomb command class
 */
@Command(name = "bomb")
class Bomb implements Callable<Integer> {
    @Parameters(index = "0", description = "Target Country ID")
    int d_countryId;

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try {
            GameEngine.getInstance().handleBomb(d_countryId);
        } catch (Exception e) {
            LogEntryBuffer.getInstance().log(e.getMessage());
            return 1;
        }
        return 0;
    }
}

/**
 * blockade command class
 */
@Command(name = "blockade")
class Blockade implements Callable<Integer> {
    @Parameters(index = "0", description = "Target Country ID")
    int d_countryId;

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try {
            GameEngine.getInstance().handleBlockade(d_countryId);
        } catch (Exception e) {
            LogEntryBuffer.getInstance().log(e.getMessage());
            return 1;
        }
        return 0;
    }
}

/**
 * airlift command class
 */
@Command(name = "airlift")
class Airlift implements Callable<Integer> {
    @Parameters(index = "0", description = "Source country ID")
    int d_sourceCountryId;
    @Parameters(index = "1", description = "Destination country Id")
    int d_targetCountryId;
    @Parameters(index = "2", description = "Number of army units")
    int d_num;

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try {
            GameEngine.getInstance().handleAirlift(d_sourceCountryId, d_targetCountryId, d_num);
        } catch (Exception e) {
            LogEntryBuffer.getInstance().log(e.getMessage());
            return 1;
        }
        return 0;
    }
}

/**
 * negotiate command class
 */
@Command(name = "negotiate")
class Negotiate implements Callable<Integer> {
    @Parameters(index = "0", description = "Target Player Id")
    int d_targetPlayerId;

    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try {
            GameEngine.getInstance().handleNegotiate(d_targetPlayerId);
        } catch (Exception e) {
            LogEntryBuffer.getInstance().log(e.getMessage());
            return 1;
        }
        return 0;
    }
}

/**
 * negotiate command class
 */
@Command(name = "commit")
class Commit implements Callable<Integer> {
    /**
     * Implementation of call method
     * @return exit code
     */
    public Integer call() {
        try {
            GameEngine.getInstance().handleCommit();
        } catch (Exception e) {
            LogEntryBuffer.getInstance().log(e.getMessage());
            return 1;
        }
        return 0;
    }
}

// TODO: Add tournament mode and single mode handlers.