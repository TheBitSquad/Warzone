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
 * <p>
 * This class defines a game with a map and a list of players, along with execution details and methods.
 */
public class GameEngine {
    private static GameEngine d_instance;

    public enum PHASE {DEFAULT, SETUP, STARTUP, ORDER}

    private Map d_gameMap;
    private List<Player> d_gamePlayers;
    private PHASE d_currentPhase;

    public Map getGameMap() {
        return d_gameMap;
    }

    public void setGameMap(Map p_gameMap) {
        this.d_gameMap = p_gameMap;
    }

    public List<Player> getGamePlayers() {
        return d_gamePlayers;
    }

    public void setGamePlayers(List<Player> p_gamePlayers) {
        this.d_gamePlayers = p_gamePlayers;
    }

    public PHASE getCurrentPhase() {
        return d_currentPhase;
    }

    public void setCurrentPhase(PHASE p_currentPhase) {
        this.d_currentPhase = p_currentPhase;
    }

    GameEngine() {
        d_gameMap = new Map();
        d_gamePlayers = new ArrayList<>();
    }

    public static GameEngine get_instance() {
        if (d_instance == null) {
            d_instance = new GameEngine();
        }
        return d_instance;
    }

    public void loadMap(String p_filename) throws Exception {
        boolean resp = this.d_gameMap.loadMap(p_filename);
        if(resp){
            this.d_currentPhase = PHASE.STARTUP;
        } else {
            throw new Exception("Invalid map or filname");
        }
    }

    public void assignCountries(){
        //TODO: AssignCountries to all the players
    }

    public static void main(String[] args) throws ClassNotFoundException {
        CliParser parser = new CliParser();
        Scanner scanner = new Scanner(System.in);

        String ip;
        while (true) {
            ip = scanner.nextLine();
            try {
                CliResponse resp = parser.parseCommandString(ip);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
