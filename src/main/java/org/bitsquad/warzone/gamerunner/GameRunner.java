package org.bitsquad.warzone.gamerunner;

import org.bitsquad.warzone.cli.CliParser;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.jgrapht.alg.densesubgraph.GoldbergMaximumDensitySubgraphAlgorithmNodeWeightPerEdgeWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * GameRunner handles gamemode logic
 */
public class GameRunner {

    CliParser d_commandParser;
    Scanner d_inputScanner;

    /**
     * Default Constructor
     */
    public GameRunner(){}

    private static GameRunner d_instance;

    /**
     * Singleton instance getter
     * @return GameRunner
     */
    public static GameRunner getInstance(){
        if(d_instance == null){
            d_instance = new GameRunner();
        }
        return d_instance;
    }

    /**
     * Handler for Single Game mode
     */
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

    /**
     * Handler for Tournament mode
     * @param d_mapFileNames List of filenames
     * @param d_playerStrategies List of Player Strategies
     * @param d_numGames Number of games
     * @param d_maxTurns Maximum number of rounds in a game
     * @throws Exception
     */
    public void handleTournamentMode(String[] d_mapFileNames, String[] d_playerStrategies, int d_numGames, int d_maxTurns) throws Exception {
        LogEntryBuffer.getInstance().log("Tournament mode");
        CliParser.setCommandClassNames(new ArrayList<>());

        List<String> l_resultTable = new ArrayList<>();
        StringBuilder l_format = new StringBuilder("|");

        List<String> l_tableRow = new ArrayList<>();
        l_tableRow.add("Table");
        l_format.append(" %-20s |");
        for(int i = 1; i<=d_numGames; i++){
            l_format.append(" %-20s |");
            l_tableRow.add("Game" + i);
        }
        l_resultTable.add(String.format(l_format.toString(), l_tableRow.toArray()));

        for(String l_mapName: d_mapFileNames){
            l_tableRow.clear();
            l_tableRow.add(l_mapName.substring(Math.max(0, l_mapName.length() - 20), l_mapName.length()));
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
                        l_tableRow.add("Draw!");
                    } else {
                        l_tableRow.add(GameEngine.getInstance().getWinner().getName());
                    }
                }
            }
            try{
                l_resultTable.add(String.format(l_format.toString(), l_tableRow.toArray()));
            } catch (Exception e){
                // Ignore
            }
        }
        for (String l_str: l_resultTable){
            LogEntryBuffer.getInstance().log(l_str);
        }
    }

    /**
     * Sets up and runs the game
     */
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
