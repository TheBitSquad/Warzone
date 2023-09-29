/**
 *  This file contains all the commands which are represented each in their own class, implemented with the help of picocli. 
 */
package org.bitsquad.warzone.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

import java.util.concurrent.Callable;

@Command(name = "editcontinent", mixinStandardHelpOptions = true, version = "1.0.0")
class EditContinent implements Callable<Integer>{
    @Option(names = "-add", arity = "2", paramLabel = "CONTINENT_ID VALUE",
            description = "Enter continent and value respectively")
    int[] d_add_array;
    @Option(names = "-remove", arity = "1", paramLabel = "CONTINENT_ID",
            description = "Enter the continent ID to remove")
    int[] d_remove_ids;
    @Spec CommandSpec d_spec; // injected by picocli

    public Integer call() {
        validate();
        if(d_add_array != null) {
            for(int i = 0; i< d_add_array.length; i+=2) {
                System.out.println("Adding Continent:" + d_add_array[i] + " value:" + d_add_array[i+1]);
//                CliParser.GameMap.addContinent(add_array[i], add_array[i+1]);
            }
        }
        if(d_remove_ids != null) {
            for(int i = 0; i< d_remove_ids.length; i++){
                System.out.println("Removing contient: " + d_remove_ids[i]);
//                CliParser.GameMap.removeContinent(remove_ids[i]);
            }
        }
        return 0;
    }

    private void validate() {
        if (missing(d_add_array) && missing(d_remove_ids)) {
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
class EditCountry implements Callable<Integer>{
    @Option(names = "-add", arity = "2", required=true,
            description = "Enter country ID and continent ID respectively")
    int[] d_add_ids;
    @Option(names = "-remove", arity = "1",required = true,
            description = "Enter the country ID to remove")
    int[] d_remove_ids;
    @Spec CommandSpec d_spec; // injected by picocli

    public Integer call() {
        validate();
        if(d_add_ids != null) {
            for(int i = 0; i< d_add_ids.length; i+=2) {
                System.out.println("Adding country: Continent:" + d_add_ids[i] + " Country:" + d_add_ids[i+1]);
//                CliParser.GameMap.addCountry(d_add_ids[i], d_add_ids[i+1]);
            }
        }
        if(d_remove_ids != null) {
            for(int i = 0; i< d_remove_ids.length; i++){
                System.out.println("Removing country id:" + d_remove_ids[i]);
//                CliParser.GameMap.removeCountry(d_remove_ids[i]);
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

@Command(name = "editneighbor")
class EditNeighbor implements Callable<Integer>{


    @Option(names = "-add", arity = "2",
            description = "Enter country ID and neigbour country ID respectively",
            required = true)
    int[] d_add_ids;
    @Option(names = "-remove", arity = "2",
            description = "Enter the country ID and neighbour country ID to remove",
            required = true)
    int[] d_remove_ids;
    @Spec CommandSpec d_spec; // injected by picocli

    public Integer call() {
        validate();
        if(d_add_ids != null) {
            for(int i = 0; i< d_add_ids.length; i+=2) {
                System.out.println("Adding neighbor:" + d_add_ids[i] + " Country:" + d_add_ids[i+1]);
//                CliParser.GameMap.addNeighbor(d_add_ids[i], d_add_ids[i+1]);
            }
        }
        if(d_remove_ids != null) {
            for(int i = 0; i< d_remove_ids.length; i+=2){
                System.out.println("Removing neighbor: country:" + d_remove_ids[i] + " Country:" + d_remove_ids[i+1]);
//                CliParser.GameMap.removeNeighbor(d_remove_ids[i], d_remove_ids[i+1]);
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
class ShowMap implements Callable<Integer>{
    public Integer call() {
//        CliParser.GameMap.showMap();
        System.out.println("Showing map");
        return 0;
    }
}

@Command(name = "savemap")
class SaveMap implements Callable<Integer>{
    public Integer call() {
//        CliParser.GameMap.saveMap();
        System.out.println("Saving map");
        return 0;
    }
}

@Command(name = "editmap")
class EditMap implements Callable<Integer>{
    @Parameters(index = "0",
            paramLabel = "Filename",
            description = "Filename of the map")
    String d_filename;

    public Integer call() {
//        CliParser.GameMap.editMap();
        System.out.println("Edit map: " + d_filename);
        return 0;
    }
}


@Command(name = "validatemap")
class ValidateMap implements Callable<Integer>{
    public Integer call() {
//        CliParser.GameMap.validateMap();
        System.out.println("Validate map");
        return 0;
    }
}

@Command(name = "loadmap")
class LoadMap{
    @Parameters(index = "0",
            paramLabel = "Filename of the map",
            description = "Filename of the map")
    String d_filename;

    public Integer call() {
//        CliParser.GameMap.loadMap();
        System.out.println("Load map: " + d_filename);
        return 0;
    }
}

@Command(name = "gameplayer")
class GamePlayer implements Callable<Integer>{
    @Option(names = "-add",
            description = "Enter player ID",
            required = true)
    int d_add_ids[];
    @Option(names = "-remove",
            description = "Enter the player ID",
            required = true)
    int d_remove_ids[];
    @Spec CommandSpec d_spec; // injected by picocli

    public Integer call() {
        validate();
        //TODO: Functionality to add a new player with id
        if(d_add_ids != null) {
            for(int i = 0; i< d_add_ids.length; i++) {
//                CliParser.GamePlayers.add(new Player());
                System.out.println("Adding player id: " + d_add_ids[i]);
            }
        }
        //TODO: Functionality to remove a player using id
        if(d_remove_ids != null) {
            for(int i = 0; i< d_remove_ids.length; i++){
//                CliParser.GamePlayers.remove(d_remove_ids[i]);
                System.out.println("Removing player id: " + d_remove_ids[i]);
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

    private boolean missing(int[] d_list) {
        return d_list == null || d_list.length == 0;
    }

}

//TODO: Decide on how to approach
@Command(name = "assigncountries")
class AssignCountries implements Callable<Integer>{
    public Integer call() {
        return 4;
    }
}

//TODO: Decide on how to implement
@Command(name = "deploy")
class Deploy implements Callable<Integer>{
    @Parameters(index = "0", description = "country ID")
    int d_countryID;
    @Parameters(index = "1", description = "Number of army units")
    int d_num;

    public Integer call() {
        System.out.println("Deploy command");
        return 0;
    }
}