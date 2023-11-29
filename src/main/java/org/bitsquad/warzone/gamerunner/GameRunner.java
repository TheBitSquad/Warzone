package org.bitsquad.warzone.gamerunner;

import org.bitsquad.warzone.cli.CliParser;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.jgrapht.alg.densesubgraph.GoldbergMaximumDensitySubgraphAlgorithmNodeWeightPerEdgeWeight;

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
        while (!GameEngine.getInstance().getPhase().getClass().getSimpleName().equalsIgnoreCase("GameFinished")) {
            System.out.print(">");
            ip = d_inputScanner.nextLine();
            try {
                d_commandParser.parseCommandString(ip);
            } catch (Exception e) {
                LogEntryBuffer.getInstance().log(e.getMessage());
            }
        }
    }

    public void handleTournamentMode(String[] d_mapFileNames, String[] d_playerStrategies, int d_numGames, int d_maxTurns) throws Exception {
        LogEntryBuffer.getInstance().log("Tournament mode");
        CliParser.setCommandClassNames(new ArrayList<>());

        List<String> l_resultTable = new ArrayList<>();

        String l_header = "Table";
        for(int i = 1; i<=d_numGames; i++){
            l_header += "\tGame" + i;
        }
        l_resultTable.add(l_header);

        for(String l_mapName: d_mapFileNames){
            String l_mapResult = l_mapName;
            for (int l_gameNumber = 1; l_gameNumber <= d_numGames; l_gameNumber ++){
                LogEntryBuffer.getInstance().log("Game " + l_gameNumber);
                // Initialise a game engine instance, its variables and run the game.
                GameEngine.resetInstance();
                // Populate Map
                try{
                    GameEngine.getInstance().handleLoadMap(l_mapName);
                } catch (Exception e){
                    LogEntryBuffer.getInstance().log(e.getMessage());
                    break;
                }

                // Populate players
                // Only the computer players are allowed. no need for exception catch.
                for(String l_playerName: d_playerStrategies){
                    GameEngine.getInstance().handleAddPlayer(l_playerName);
                }

                // Set game conditions
                GameEngine.getInstance().setMaxRounds(d_maxTurns);

                try{
                    GameEngine.getInstance().handleAssignCountries();
                } catch (Exception e){
                    LogEntryBuffer.getInstance().log(e.getMessage());
                    break;
                }

                if(GameEngine.getInstance().getPhase().getClass().getSimpleName().equalsIgnoreCase("GameFinished")){
                    if(GameEngine.getInstance().getWinner() == null){
                        l_mapResult += "\tDraw";
                    } else {
                        l_mapResult += "\t" + GameEngine.getInstance().getWinner().getName();
                    }
                }
            }
            l_resultTable.add(l_mapResult);
        }
        for (String l_str: l_resultTable){
            LogEntryBuffer.getInstance().log(l_str);
        }
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
