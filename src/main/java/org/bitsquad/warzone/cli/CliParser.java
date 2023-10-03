package org.bitsquad.warzone.cli;

import org.bitsquad.warzone.gameengine.GameEngine;
import picocli.CommandLine;

import java.util.*;

/**
 *  This class represents the implementation of the command line interface, which processes user input commands
 */
public class CliParser {
    static HashMap<String, GameEngine.PHASE> CommandClassPhaseMap = new HashMap<>();
    static {
        // Add to the map to return the class/object for a lookup.
        CommandClassPhaseMap.put("EditContinent", GameEngine.PHASE.MAP);
        CommandClassPhaseMap.put("EditCountry", GameEngine.PHASE.MAP);
        CommandClassPhaseMap.put("EditNeighbor", GameEngine.PHASE.MAP);

        CommandClassPhaseMap.put("SaveMap", GameEngine.PHASE.MAP);
        CommandClassPhaseMap.put("EditMap", GameEngine.PHASE.MAP);
        CommandClassPhaseMap.put("ValidateMap", GameEngine.PHASE.MAP);

        CommandClassPhaseMap.put("ShowMap", GameEngine.PHASE.DEFAULT);

        CommandClassPhaseMap.put("LoadMap", GameEngine.PHASE.MAP);
        CommandClassPhaseMap.put("GamePlayer", GameEngine.PHASE.STARTUP);
        CommandClassPhaseMap.put("AssignCountries", GameEngine.PHASE.STARTUP);

        CommandClassPhaseMap.put("Deploy", GameEngine.PHASE.PLAY);
    }

    private String commandClassName(String p_commandName){
        String l_requiredKey = null;
        Set<String> l_keys = CommandClassPhaseMap.keySet();

        for(String key: l_keys) {
            if(key.equalsIgnoreCase(p_commandName)) {
                l_requiredKey = key;
                break;
            }
        }
        return l_requiredKey;
    }
    private Class getFullyQualifiedClassName(String p_className) throws ClassNotFoundException {
        String l_packageName = CliParser.class.getPackageName();
        if(p_className == null) {
            return null;
        } else {
            String l_fullyQualifiedClassName = l_packageName + "." + p_className;
            return Class.forName(l_fullyQualifiedClassName);
        }
    }

    public void parseCommandString(String p_ip) throws ClassNotFoundException, CommandLine.ParameterException {
        if(p_ip == null) {
            System.err.println("No command was inputted");
            return;
        }
        String[] l_ip_arr = p_ip.split(" ");
        String l_commandName = l_ip_arr[0];

        Class l_command = getFullyQualifiedClassName(commandClassName(l_commandName));
        Object l_obj = null;

        if(l_command == null) {
            System.err.println(l_ip_arr[0] + " is not a valid command");
            return;
        }

        if(CommandClassPhaseMap.get(commandClassName(l_commandName)) != GameEngine.PHASE.DEFAULT){
            if(l_commandName.equalsIgnoreCase("assigncountries") || l_commandName.equalsIgnoreCase("gameplayer")){
                // Changes state, hence it should be allowed if the previous state is either Phase.Map or Phase.Startup
                if(GameEngine.get_instance().getCurrentPhase() != GameEngine.PHASE.MAP &&
                        GameEngine.get_instance().getCurrentPhase() != GameEngine.PHASE.STARTUP){
                    System.err.println("Command not valid in current phase!");
                    return;
                }
            }else if(CommandClassPhaseMap.get(commandClassName(l_commandName)) != GameEngine.get_instance().getCurrentPhase()){
                System.err.println("Command not valid in current phase!");
                return;
            }
        }

        try {
            l_obj = l_command.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        CommandLine l_cmd = new CommandLine(l_obj);

        if(l_ip_arr.length > 1){
            String[] l_command_args = Arrays.copyOfRange(l_ip_arr, 1, l_ip_arr.length);
            int l_resp = l_cmd.execute(l_command_args);
        } else {
            int l_resp = l_cmd.execute();
        }

    }
}
