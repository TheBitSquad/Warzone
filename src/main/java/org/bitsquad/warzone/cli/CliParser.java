package org.bitsquad.warzone.cli;

import org.bitsquad.warzone.gameengine.GameEngine;
import picocli.CommandLine;

import java.util.*;

/**
 *  This class represents the implementation of the command line interface, which processes user input commands
 */
public class CliParser {
    static List<String> CommandClassNames = new LinkedList<>();
    static {
        // Add to the map to return the class/object for a lookup.
        CommandClassNames.add("EditContinent");
        CommandClassNames.add("EditCountry");
        CommandClassNames.add("EditNeighbor");

        CommandClassNames.add("SaveMap");
        CommandClassNames.add("EditMap");
        CommandClassNames.add("ValidateMap");
        CommandClassNames.add("ShowMap");
        CommandClassNames.add("LoadMap");
        
        CommandClassNames.add("GamePlayer");
        CommandClassNames.add("AssignCountries");

        CommandClassNames.add("Deploy");
        CommandClassNames.add("Advance");

        CommandClassNames.add("Bomb");
        CommandClassNames.add("Airlift");
        CommandClassNames.add("Negotiate");
        CommandClassNames.add("Blockade");

        CommandClassNames.add("Commit");
    }

    /**
     * Gets corresponding command handler class name for command name
     * @param p_commandName command name
     * @return class name of command handler
     */
    private String commandClassName(String p_commandName){
        String l_requiredKey = null;

        for(String key: CommandClassNames) {
            if(key.equalsIgnoreCase(p_commandName)) {
                l_requiredKey = key;
                break;
            }
        }
        return l_requiredKey;
    }

    /**
     * Generates a fully qualified class name for a given class name
     * @param p_className name of the class
     * @return Class
     * @throws ClassNotFoundException
     */
    private Class getFullyQualifiedClassName(String p_className) throws ClassNotFoundException {
        String l_packageName = CliParser.class.getPackageName();
        if(p_className == null) {
            return null;
        } else {
            String l_fullyQualifiedClassName = l_packageName + "." + p_className;
            return Class.forName(l_fullyQualifiedClassName);
        }
    }

    /**
     * Parses the inputted command string
     * @param p_ip inputted command
     * @throws ClassNotFoundException
     * @throws CommandLine.ParameterException
     */
    public void parseCommandString(String p_ip) throws ClassNotFoundException, CommandLine.ParameterException {
        if (p_ip == null) {
            System.err.println("No command was inputted");
            return;
        }
        String[] l_ip_arr = p_ip.split(" ");
        String l_commandName = l_ip_arr[0];

        Class l_command = getFullyQualifiedClassName(commandClassName(l_commandName));
        Object l_obj = null;

        if (l_command == null) {
            System.err.println(l_ip_arr[0] + " is not a valid command");
            return;
        }

        try {
            l_obj = l_command.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        CommandLine l_cmd = new CommandLine(l_obj);

        if (l_ip_arr.length > 1) {
            String[] l_command_args = Arrays.copyOfRange(l_ip_arr, 1, l_ip_arr.length);
            int l_resp = l_cmd.execute(l_command_args);
        } else {
            int l_resp = l_cmd.execute();
        }
    }
}
