package org.bitsquad.warzone.gameengine;

import org.bitsquad.warzone.cli.CliParser;
import org.bitsquad.warzone.cli.CliResponse;
import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.order.Order;
import org.bitsquad.warzone.player.Player;

import java.util.*;

/**
 * Represents the Game engine.
 * <p>
 * This class defines a game with a map and a list of players, along with execution details and methods.
 */
public class GameEngine {
    private static GameEngine d_instance;

    public enum PHASE {DEFAULT, MAP, STARTUP, PLAY}

    private Map d_gameMap;
    private List<Player> d_gamePlayers;

    private PHASE d_currentPhase;
    private int d_currentPlayerIndex;

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
        if (resp) {
            this.d_currentPhase = PHASE.STARTUP;
        } else {
            throw new Exception("Invalid map or filename");
        }
        this.d_currentPlayerIndex = 0;
    }

    public void assignCountries() {
        // Create a map of whole countries
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : d_gameMap.getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }

        // Split countries between players
        ArrayList<Integer> l_countryIDs = new ArrayList<>(l_allCountries.keySet());
        Collections.shuffle(l_countryIDs);
        int l_intervals = l_countryIDs.size() / this.d_gamePlayers.size();
        for (int i = 0; i < l_countryIDs.size(); i++) {
            int assignee = i % l_intervals;
            d_gamePlayers.get(assignee).addCountryOwned(l_allCountries.get(l_countryIDs.get(i)));
        }

        // Change the current phase
        this.d_currentPhase = PHASE.PLAY;
    }

    public void deployArmy(int p_targetCountryID, int p_armyUnits) throws Exception {
        Player l_currentPlayer = d_gamePlayers.get(d_currentPlayerIndex);

        // Check whether the player has the sufficient army units or not
        int l_newAvailableArmyUnits = l_currentPlayer.getAvailableArmyUnits() - p_armyUnits;
        if (l_newAvailableArmyUnits < 0) {
            throw new Exception("insufficient army units");
        }

        // Check if the country ID is owned by player
        boolean l_hasCountry = false;
        for (Country l_country : l_currentPlayer.getCountriesOwned()) {
            if (l_country.getCountryId() == p_targetCountryID) {
                l_hasCountry = true;
                break;
            }
        }
        if (!l_hasCountry) {
            throw new Exception("can not deploy to enemy territory");
        }

        // Issue the deployment order
        Order l_deployOrder = new Order(
                l_currentPlayer.getId(),
                -1,
                p_targetCountryID,
                p_armyUnits,
                Order.TYPEOFACTION.DEPLOY
        );
        l_currentPlayer.setCurrentOrder(l_deployOrder);
        l_currentPlayer.issueOrder();
        l_currentPlayer.setAvailableArmyUnits(l_newAvailableArmyUnits);

        // Change the turn
        if (l_currentPlayer.getAvailableArmyUnits() == 0) {
            this.d_currentPlayerIndex++;
            if(this.d_currentPlayerIndex == this.d_gamePlayers.size()){
                resetCurrentPlayerIndex();
                executeOrders();
            }
        }
    }

    private void executeOrders() {
        Order l_orderToExecute;

        boolean l_isAllOrderSetsEmpty = false;
        while(!l_isAllOrderSetsEmpty){
            l_isAllOrderSetsEmpty = true;
            for(Player l_player: this.d_gamePlayers){
                l_orderToExecute = l_player.nextOrder();
                if(l_orderToExecute != null){
                    l_isAllOrderSetsEmpty = false;
                    l_orderToExecute.execute();
                }
            }
        }
    }

    private void resetCurrentPlayerIndex() {
        this.d_currentPlayerIndex = 0;
    }

    public void addPlayer(String p_playerName) throws Exception {
        // Check if a player is already present
        for(Player l_player: this.d_gamePlayers){
            if(l_player.getName().equalsIgnoreCase(p_playerName)){
                throw new Exception("Player already exists!");
            }
        }
        d_gamePlayers.add(new Player(p_playerName));
    }

    public void removePlayer(String p_playerName) throws Exception {
        for(Player l_player: this.d_gamePlayers){
            if(l_player.getName().equalsIgnoreCase(p_playerName)){
                this.d_gamePlayers.remove(l_player);
                return;
            }
        }
        throw new Exception("Player does not exist!");
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
