package org.bitsquad.warzone.gamerunner;

import com.sun.source.tree.WhileLoopTree;
import org.bitsquad.warzone.cli.CliParser;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameRunner {

    CliParser d_commandParser;
    Scanner d_inputScanner;

    public GameRunner(){}

    private static GameRunner d_instance;

    public static GameRunner getInstance(){
        if(d_instance == null){
            d_instance = new GameRunner();
        }
        return d_instance;
    }

    public void handleSingleGameMode(){
        LogEntryBuffer.getInstance().log("Single Game Mode");
        CliParser.setDefaultCommandClassNames();
        String ip;
        while (GameEngine.getInstance().getWinner() == null) {
            System.out.print(">");
            ip = d_inputScanner.nextLine();
            try {
                d_commandParser.parseCommandString(ip);
            } catch (Exception e) {
                LogEntryBuffer.getInstance().log(e.getMessage());
            }
        }
    }

    public void handleTournamentMode(){
        // TODO: Implement handleTournament
        LogEntryBuffer.getInstance().log("Tournament mode");
        CliParser.setCommandClassNames(new ArrayList<>());
    }

    public void runGame(){
        List<String> l_initialCommandList = new ArrayList<String>(List.of("Tournament", "GameMode"));

        d_commandParser = new CliParser();
        d_inputScanner = new Scanner(System.in);

        String ip;
        while(true){
            CliParser.setCommandClassNames(l_initialCommandList);
            LogEntryBuffer.getInstance().log("Welcome to Warzone! Select your game mode");
            System.out.print(">");
            ip = d_inputScanner.nextLine();
            try {
                d_commandParser.parseCommandString(ip);
            } catch (Exception e) {
                LogEntryBuffer.getInstance().log(e.getMessage());
            }
        }
    }
}
