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

        CommandClassPhaseMap.put("LoadMap", GameEngine.PHASE.STARTUP);
        CommandClassPhaseMap.put("GamePlayer", GameEngine.PHASE.STARTUP);
        CommandClassPhaseMap.put("AssignCountries", GameEngine.PHASE.STARTUP);

        CommandClassPhaseMap.put("Deploy", GameEngine.PHASE.PLAY);
        CommandClassPhaseMap.put("Test", GameEngine.PHASE.PLAY);
    }

    private Class getClassName(String p_commandName) throws ClassNotFoundException {
        //TODO: Add a phase in the formal arguments maybe to check only in the valid phases
        String l_packageName = CliParser.class.getPackageName();
        String l_requiredKey = null;
        Set<String> l_keys = CommandClassPhaseMap.keySet();

        for(String key: l_keys) {
            if(key.equalsIgnoreCase(p_commandName)) {
                l_requiredKey = key;
                break;
            }
        }

        if(l_requiredKey == null) {
            return null;
        } else {
            String l_fullyQualifiedClassName = l_packageName + "." + l_requiredKey;
            return Class.forName(l_fullyQualifiedClassName);
        }
    }

    public CliResponse parseCommandString(String p_ip) throws ClassNotFoundException, CommandLine.ParameterException {
        if(p_ip == null) {
            System.err.println("No command was inputted");
            return new CliResponse(true, "No command was inputted", false);
        }
        String[] l_ip_arr = p_ip.split(" ");

        Class l_command = getClassName(l_ip_arr[0]);
        Object l_obj;

        if(l_command == null) {
            System.err.println(l_ip_arr[0] + " is not a valid command");
            return new CliResponse(true, l_ip_arr[0] + " is not a valid command.", false);
        }
        try {
            l_obj = l_command.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            return new CliResponse(true, ex.getLocalizedMessage(), false);
        }


        CommandLine l_cmd = new CommandLine(l_obj);
        String[] l_command_args = Arrays.copyOfRange(l_ip_arr, 1, l_ip_arr.length);


        if(l_cmd.getCommandName().equalsIgnoreCase("assigncountries")) {
            return new CliResponse(false, null, true);
        }

        int l_resp = l_cmd.execute(l_command_args);
        return new CliResponse(false, null, false);
    }
}
