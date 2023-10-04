package org.bitsquad.warzone.gameengine;

import org.bitsquad.warzone.cli.CliParser;
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

    /**
     * Sets the currentPlayer
     * @param p_currentPlayerIndex
     */
    private void setCurrentPlayerIndex(int p_currentPlayerIndex) {
        System.out.println("Current player: " + d_gamePlayers.get(p_currentPlayerIndex).getName());
        this.d_currentPlayerIndex = p_currentPlayerIndex;
    }

    private int d_currentPlayerIndex;

    /**
     * Getter method for game map
     * @return gamemap
     */
    public Map getGameMap() {
        return d_gameMap;
    }

    /**
     * Setter for game map
     * @param p_gameMap gamemap
     */
    public void setGameMap(Map p_gameMap) {
        this.d_gameMap = p_gameMap;
    }

    /**
     * Getter for gameplayers list
     * @return list of game players
     */
    public List<Player> getGamePlayers() {
        return d_gamePlayers;
    }

    /**
     * Setter for game players
     * @param p_gamePlayers list of game players
     */
    public void setGamePlayers(List<Player> p_gamePlayers) {
        this.d_gamePlayers = p_gamePlayers;
    }

    /**
     * Gets the current game phase
     * @return current game phases
     */
    public PHASE getCurrentPhase() {
        return d_currentPhase;
    }

    /**
     * Setter for current game phase
     * @param p_currentPhase game phase
     */
    public void setCurrentPhase(PHASE p_currentPhase) {
        this.d_currentPhase = p_currentPhase;
    }

    /**
     * Default constructor
     */
    GameEngine() {
        d_gameMap = new Map();
        d_gamePlayers = new ArrayList<>();
        d_currentPhase = PHASE.MAP;
    }

    /**
     * Singleton instance getter
     * @return instance of GameEngine
     */
    public static GameEngine get_instance() {
        if (d_instance == null) {
            d_instance = new GameEngine();
        }
        return d_instance;
    }

    /**
     * Handler method for loadmap command
     * @param p_filename filename of map file
     * @throws Exception if the map is invalid or IOError
     */
    public void handleLoadMap(String p_filename) throws Exception {
        boolean resp = this.d_gameMap.loadMap(p_filename);
        if (resp) {
            this.d_currentPhase = PHASE.STARTUP;
        } else {
            throw new Exception("Invalid map or filename");
        }
    }

    /**
     * Handler for assigncountries command
     * @throws Exception
     */
    public void handleAssignCountries() throws Exception{
        if(!this.d_gameMap.validateMap()){
            throw new Exception("Not a valid map cannot begin gameplay");
        }
        if(this.d_gamePlayers.size() == 0){
            throw new Exception("No players have been added yet");
        }

        // Create a map of whole countries
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : d_gameMap.getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }

        // Split countries between players
        ArrayList<Integer> l_countryIDs = new ArrayList<>(l_allCountries.keySet());
        Collections.shuffle(l_countryIDs);

        if(l_countryIDs.size() == 0){
            throw new Exception("No countries have been added");
        }
        int l_intervals = l_countryIDs.size() / this.d_gamePlayers.size();
        for (int i = 0; i < l_countryIDs.size(); i++) {
            int assignee = Math.min(i / l_intervals, d_gamePlayers.size() - 1);
            d_gamePlayers.get(assignee).addCountryOwned(l_allCountries.get(l_countryIDs.get(i)));
            l_allCountries.get(l_countryIDs.get(i)).setOwnedByPlayerId(d_gamePlayers.get(assignee).getId());
        }

        // Change the current phase
        this.d_currentPhase = PHASE.PLAY;
        nextRound();
    }

    /**
     * Handler method for deploy army command
     * @param p_targetCountryID ID of target country
     * @param p_armyUnits number of army units
     * @throws Exception
     */
    public void handleDeployArmy(int p_targetCountryID, int p_armyUnits) throws Exception {
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
            // IF we are on the last player
            if (this.d_currentPlayerIndex + 1 == this.d_gamePlayers.size()) {
                executeOrders();
                nextRound();
            } else {
                setCurrentPlayerIndex(this.d_currentPlayerIndex + 1);
            }
        }
    }

    /**
     * Executes orders in Round-Robin fashion
     */
    private void executeOrders() {
        System.out.println("Executing Orders");
        Order l_orderToExecute;

        boolean l_isAllOrderSetsEmpty = false;
        while (!l_isAllOrderSetsEmpty) {
            l_isAllOrderSetsEmpty = true;
            for (Player l_player : this.d_gamePlayers) {
                l_orderToExecute = l_player.nextOrder();
                if (l_orderToExecute != null) {
                    l_isAllOrderSetsEmpty = false;
                    l_orderToExecute.execute();
                }
            }
        }
    }

    /**
     * Sets up the start of a new round
     */
    private void nextRound() {
        System.out.println("New Round!");
        // Assign reinforcement units
        for (Player l_player : this.d_gamePlayers) {
            // Calculate the reinforcement
            int l_numberReinforcement = getNumberOfReinforcementUnits(l_player);

            // Add the army
            l_player.setAvailableArmyUnits(l_player.getAvailableArmyUnits() + l_numberReinforcement);

            System.out.println(l_player.getName() + ": Reinforcements: " + l_numberReinforcement +
                    ". Total Units available to deploy: " + l_player.getAvailableArmyUnits());
        }
        setCurrentPlayerIndex(0);
    }

    /**
     * Calculates number of reinforcement units for a player
     * @param p_player Player Objec
     * @return int number of reinforcement units
     */
    private int getNumberOfReinforcementUnits(Player p_player) {
        int l_numberReinforcement = 3;
        l_numberReinforcement += p_player.getCountriesOwned().size() / 3;
        for (Continent l_continent : this.d_gameMap.getContinents().values()) {
            boolean l_isAllCountiesOwnedByPlayer = true;
            for (Country l_country : l_continent.getCountries().values()) {
                if (!p_player.getCountriesOwned().contains(l_country)) {
                    l_isAllCountiesOwnedByPlayer = false;
                    break;
                }
            }

            if (l_isAllCountiesOwnedByPlayer) {
                l_numberReinforcement += l_continent.getValue();
            }
        }
        return l_numberReinforcement;
    }

    /**
     * Handler method for gamplayer -add command
     * @param p_playerName name of the player
     * @throws Exception
     */
    public void handleAddPlayer(String p_playerName) throws Exception {
        // Check if a player is already present
        for(Player l_player: this.d_gamePlayers){
            if(l_player.getName().equalsIgnoreCase(p_playerName)){
                throw new Exception("Player already exists!");
            }
        }
        d_gamePlayers.add(new Player(p_playerName));
    }

    /**
     * Handler method of remove player command
     * @param p_playerName Player name
     * @throws Exception
     */
    public void handleRemovePlayer(String p_playerName) throws Exception {
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
            System.out.print(">");
            ip = scanner.nextLine();
            try {
                parser.parseCommandString(ip);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
