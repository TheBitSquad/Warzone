package org.bitsquad.warzone.gameengine;

import org.bitsquad.warzone.cli.CliParser;
import org.bitsquad.warzone.cli.CliResponse;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the Game engine.
 * 
 * This class defines a game with a map and a list of players, along with execution details and methods.
 */
public class GameEngine {
    private static GameEngine d_instance;

    Map d_gameMap;
    List<Player> d_gamePlayers;
    GameEngine(){
        d_gameMap = new Map();
        d_gamePlayers = new ArrayList<>();
    }

    public static GameEngine get_instance(){
        if(d_instance == null) {
            d_instance = new GameEngine();
        }
        return d_instance;
    }

    public void printNumberOfPlayers() {
        System.out.println(this.d_gamePlayers.size());
    }
    public static void main(String[] args) throws ClassNotFoundException {
        CliParser parser = new CliParser();
        Scanner scanner = new Scanner(System.in);

        String ip;
        while(true){
            ip = scanner.nextLine();
            try{
                CliResponse resp = parser.parseCommandString(ip);
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
    }

}
