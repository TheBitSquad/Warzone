/**
 * This file contains all the commands which are represented each in their own class, implemented with the help of picocli.
 */
package org.bitsquad.warzone.cli;

import org.bitsquad.warzone.gameengine.GameEngine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

import java.util.concurrent.Callable;

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

    public Integer call() {
        validate();
        if (d_addArray != null) {
            for (int i = 0; i < d_addArray.length; i += 2) {
                int l_continent_id = d_addArray[i];
                int l_continent_value = d_addArray[i + 1];
                GameEngine.get_instance().getGameMap().addContinent(l_continent_id, l_continent_value);
            }
        }
        if (d_removeIds != null) {
            for (int i = 0; i < d_removeIds.length; i++) {
                GameEngine.get_instance().getGameMap().removeContinent(d_removeIds[i]);
            }
        }
        return 0;
    }

    private void validate() {
        if (missing(d_addArray) && missing(d_removeIds)) {
            throw new ParameterException(d_spec.commandLine(),
                    "Missing option: at least one of the " +
                            "'-add' or '-remove' options must be specified.");
        }
    }

    private boolean missing(int[] p_list) {
        return p_list == null || p_list.length == 0;
    }
}

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

    public Integer call() {
        validate();
        if (d_addIds != null) {
            for (int i = 0; i < d_addIds.length; i += 2) {
                int l_countryId = d_addIds[i];
                int l_continentId = d_addIds[i + 1];
                boolean resp = GameEngine.get_instance().getGameMap().addCountry(l_countryId, l_continentId);
                if(!resp){
                    System.out.println("Cannot add country");
                } else {
                    System.out.printf("Added country: %d to %d\n", l_countryId, l_continentId);
                }
            }
        }
        if (d_removeIds != null) {
            for (int i = 0; i < d_removeIds.length; i++) {
                GameEngine.get_instance().getGameMap().removeCountry(d_removeIds[i]);
            }
        }
        return 0;
    }

    private void validate() {
        if (missing(d_addIds) && missing(d_removeIds)) {
            throw new ParameterException(d_spec.commandLine(),
                    "Missing option: at least one of the " +
                            "'-add' or '-remove' options must be specified.");
        }
    }

    private boolean missing(int[] p_list) {
        return p_list == null || p_list.length == 0;
    }
}

@Command(name = "editneighbor")
class EditNeighbor implements Callable<Integer> {

    @Option(names = "-add", arity = "2",
            description = "Enter country ID and neigbour country ID respectively"
    )
    int[] d_add_ids;
    @Option(names = "-remove", arity = "2",
            description = "Enter the country ID and neighbour country ID to remove"
    )
    int[] d_remove_ids;
    @Spec
    CommandSpec d_spec; // injected by picocli

    public Integer call() {
        validate();
        if (d_add_ids != null) {
            for (int i = 0; i < d_add_ids.length; i += 2) {
                GameEngine.get_instance().getGameMap().addNeighbor(d_add_ids[i], d_add_ids[i+1]);
            }
        }
        if (d_remove_ids != null) {
            for (int i = 0; i < d_remove_ids.length; i += 2) {
                GameEngine.get_instance().getGameMap().removeNeighbor(d_remove_ids[i], d_remove_ids[i+1]);
            }
        }
        return 0;
    }

    private void validate() {
        if (missing(d_add_ids) && missing(d_remove_ids)) {
            throw new ParameterException(d_spec.commandLine(),
                    "Missing option: at least one of the " +
                            "'-add' or '-remove' options must be specified.");
        }
    }

    private boolean missing(int[] p_list) {
        return p_list == null || p_list.length == 0;
    }
}

@Command(name = "showmap")
class ShowMap implements Callable<Integer> {
    public Integer call() {
        GameEngine.get_instance().getGameMap().visualizeGraph();
        return 0;
    }
}

@Command(name = "savemap")
class SaveMap implements Callable<Integer> {
    @Parameters(index = "0",
            paramLabel = "Filename",
            description = "Filename of the map")
    String d_filename;
    public Integer call() {
        try {
            GameEngine.get_instance().getGameMap().saveMap(d_filename);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }
}

@Command(name = "editmap")
class EditMap implements Callable<Integer> {
    @Parameters(index = "0",
            paramLabel = "Filename",
            description = "Filename of the map")
    String d_filename;

    public Integer call() {
        GameEngine.get_instance().getGameMap().editMap(d_filename);
        return 0;
    }
}


@Command(name = "validatemap")
class ValidateMap implements Callable<Integer> {
    public Integer call() {
        System.out.println(GameEngine.get_instance().getGameMap().validateMap());
        return 0;
    }
}

@Command(name = "loadmap")
class LoadMap implements Callable<Integer> {
    @Parameters(index = "0",
            paramLabel = "Filename of the map",
            description = "Filename of the map")
    String d_filename;

    public Integer call() {
        try {
            GameEngine.get_instance().loadMap(d_filename);
        } catch (Exception e) {
            System.err.println(e);
            return 1;
        }
        return 0;
    }
}

@Command(name = "gameplayer")
class GamePlayer implements Callable<Integer> {
    @Option(names = "-add",
            description = "Enter player name")
    String[] d_add_names;
    @Option(names = "-remove",
            description = "Enter the player name")
    String[] d_remove_names;
    @Spec
    CommandSpec d_spec; // injected by picocli

    public Integer call() {
        validate();
        if (d_add_names != null) {
            for (String l_addName : d_add_names) {
                try {
                    GameEngine.get_instance().addPlayer(l_addName);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        if (d_remove_names != null) {
            for (String l_removeName : d_remove_names) {
                try {
                    GameEngine.get_instance().removePlayer(l_removeName);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return 0;
    }

    private void validate() {
        if (missing(d_add_names) && missing(d_remove_names)) {
            throw new ParameterException(d_spec.commandLine(),
                    "Missing option: at least one of the " +
                            "'-add' or '-remove' options must be specified.");
        }
    }

    private boolean missing(String[] d_list) {
        return d_list == null || d_list.length == 0;
    }

}

@Command(name = "assigncountries")
class AssignCountries implements Callable<Integer> {
    public Integer call() {
        try {
            GameEngine.get_instance().assignCountries();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }
}

@Command(name = "deploy")
class Deploy implements Callable<Integer> {
    @Parameters(index = "0", description = "country ID")
    int d_countryID;
    @Parameters(index = "1", description = "Number of army units")
    int d_num;

    public Integer call() {
        try {
            GameEngine.get_instance().deployArmy(d_countryID, d_num);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 1;
        }
        return 0;
    }
}