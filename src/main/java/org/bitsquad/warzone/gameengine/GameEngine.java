package org.bitsquad.warzone.gameengine;

import org.bitsquad.warzone.card.Card;
import org.bitsquad.warzone.card.CardGenerator;
import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.policy.PolicyManager;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.order.*;
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

    PolicyManager d_policyManager;

    GameEngine() {
        d_gameMap = new Map();
        d_gamePlayers = new ArrayList<>();
        d_currentPhase = PHASE.MAP;
        d_policyManager = new PolicyManager();
    }

    /**
     * Sets the currentPlayer
     * @param p_currentPlayerIndex is the current player index
     */
    private void setCurrentPlayerIndex(int p_currentPlayerIndex) {
        System.out.println("Current player: " + d_gamePlayers.get(p_currentPlayerIndex).getName() +
                "Player id: " + d_gamePlayers.get(p_currentPlayerIndex).getId());
        this.d_currentPlayerIndex = p_currentPlayerIndex;
    }

    /**
     * Getter method for game map
     * @return gamemap the map of the current game engine
     */
    public Map getGameMap() {
        return d_gameMap;
    }

    /**
     * Setter for game map
     * @param p_gameMap the map
     */
    public void setGameMap(Map p_gameMap) {
        this.d_gameMap = p_gameMap;
    }

    /**
     * Getter for game players list
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
     * Returns the current player object
     * @return Player
     */
    public Player getCurrentPlayer(){
        return this.d_gamePlayers.get(this.d_currentPlayerIndex);
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
     * Executes orders in Round-Robin fashion
     */
    public void executeOrders() {
        // TODO: ExecuteOrders-Modify so that all deploy orders are executed first then the rest
        // Completed: ExecuteOrders-Modify so that policies are first checked then order is discarded or executed.
        // Completed: ExecuteOrders-Modify to check if an order is valid, before executing (Since the game changes at runtime)
        System.out.println("Executing Orders");
        Order l_orderToExecute = null;

        boolean l_isAllOrderSetsEmpty = false;
        boolean l_allDeployCommandsCompleted = false;

        while(!l_allDeployCommandsCompleted){
            l_allDeployCommandsCompleted = false;
            for (Player l_player : this.d_gamePlayers) {
                l_orderToExecute = null;
                if(l_player.isNextDeploy()){
                    l_allDeployCommandsCompleted = false;
                    l_orderToExecute = l_player.nextOrder();
                }
                if (l_orderToExecute != null) {
                    System.out.println("Executing: " + l_orderToExecute);
                    if(this.d_policyManager.checkPolicies(l_orderToExecute) && l_orderToExecute.isValid()){
                        l_orderToExecute.execute();
                    }
                }
            }
        }

        while (!l_isAllOrderSetsEmpty) {
            l_isAllOrderSetsEmpty = true;
            for (Player l_player : this.d_gamePlayers) {
                l_orderToExecute = l_player.nextOrder();
                if (l_orderToExecute != null) {
                    l_isAllOrderSetsEmpty = false;
                    System.out.println("Executing: " + l_orderToExecute);
                    if(this.d_policyManager.checkPolicies(l_orderToExecute) && l_orderToExecute.isValid()){
                        l_orderToExecute.execute();
                    }
                }
            }
        }
    }

    /**
     * Sets up the start of a new round
     */
    private void nextRound() {
        System.out.println("New Round!");

        for (Player l_player : this.d_gamePlayers) {
            // Assign random cards
            if (l_player.hasNewTerritory()) {
                Card l_generatedCard = CardGenerator.generateRandomCard();
                int l_numberOfCard = l_player.getCurrentCards().get(l_generatedCard);
                l_player.getCurrentCards().put(l_generatedCard, l_numberOfCard + 1);
            }

            // Assign reinforcement units
            // Calculate the reinforcement
            int l_numberReinforcement = getNumberOfReinforcementUnits(l_player);
            // Add the army
            l_player.setAvailableArmyUnits(l_player.getAvailableArmyUnits() + l_numberReinforcement);
            System.out.println(l_player.getName() + ": Reinforcements: " + l_numberReinforcement +
                    ". Total Units available to deploy: " + l_player.getAvailableArmyUnits());

            // Clear the state of players
            l_player.clearState();
        }
        setCurrentPlayerIndex(0);
    }

    /**
     * Calculates number of reinforcement units for a player
     * @param p_player Player Objec
     * @return int number of reinforcement units
     */
    public int getNumberOfReinforcementUnits(Player p_player) {
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
        Order l_deployOrder = new DeployOrder(
                l_currentPlayer,
                -1,
                p_targetCountryID,
                p_armyUnits
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
     * Handler method for the editcontinent command
     * @param p_addArray
     * @param p_removeIds
     */
    public void handleEditContinent(int p_addArray[], int p_removeIds[]){
        if (p_addArray != null) {
            for (int i = 0; i < p_addArray.length; i += 2) {
                int l_continent_id = p_addArray[i];
                int l_continent_value = p_addArray[i + 1];
                GameEngine.get_instance().getGameMap().addContinent(l_continent_id, l_continent_value);
            }
        }
        if (p_removeIds != null) {
            for (int i = 0; i < p_removeIds.length; i++) {
                GameEngine.get_instance().getGameMap().removeContinent(p_removeIds[i]);
            }
        }
    }

    /**
     * Handler method for editcountry command
     * @param p_addIds
     * @param p_removeIds
     */
    public void handleEditCountry(int p_addIds[], int p_removeIds[]){
        if (p_addIds != null) {
            for (int i = 0; i < p_addIds.length; i += 2) {
                int l_countryId = p_addIds[i];
                int l_continentId = p_addIds[i + 1];
                boolean resp = GameEngine.get_instance().getGameMap().addCountry(l_countryId, l_continentId);
                if(!resp){
                    System.out.printf("Cannot add country %d to continent %d\n", l_countryId, l_continentId);
                } else {
                    System.out.printf("Added country: %d to %d\n", l_countryId, l_continentId);
                }
            }
        }
        if (p_removeIds != null) {
            for (int i = 0; i < p_removeIds.length; i++) {
                GameEngine.get_instance().getGameMap().removeCountry(p_removeIds[i]);
            }
        }
    }

    /**
     * Handler method for editneighbor command
     * @param p_addIds
     * @param p_removeIds
     */
    public void handleEditNeighbor(int p_addIds[], int p_removeIds[]){
        if (p_addIds != null) {
            for (int i = 0; i < p_addIds.length; i += 2) {
                GameEngine.get_instance().getGameMap().addNeighbor(p_addIds[i], p_addIds[i+1]);
            }
        }
        if (p_removeIds != null) {
            for (int i = 0; i < p_removeIds.length; i += 2) {
                GameEngine.get_instance().getGameMap().removeNeighbor(p_removeIds[i], p_removeIds[i+1]);
            }
        }
    }

    /**
     * Handler method for savemap command
     * @param p_filename
     * @throws Exception
     */
    public void handleSaveMap(String p_filename) throws Exception{
        this.d_gameMap.saveMap(p_filename);
    }

    /**
     * Handler method for editmap command
     * @param p_filename
     */
    public void handleEditMap(String p_filename){
        this.d_gameMap.editMap(p_filename);
    }

    /**
     * Handler method for the validatemap command
     */
    public void handleValidateMap(){
        if(this.d_gameMap.validateMap()){
            System.out.println("Valid map");
        } else {
            System.out.println("Invalid map");
        }
    }

    /**
     * Handler method for the gameplayer command
     * @param p_addNames
     * @param p_removeNames
     */
    public void handleGamePlayer(String p_addNames[], String p_removeNames[]){
        if (p_addNames != null) {
            for (String l_addName : p_addNames) {
                try {
                    this.handleAddPlayer(l_addName);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        if (p_removeNames != null) {
            for (String l_removeName : p_removeNames) {
                try {
                    this.handleRemovePlayer(l_removeName);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
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

    /**
     * Handler method for advance command
     * @param p_countryNameFrom
     * @param p_targetCountryName
     * @param p_armyUnits
     * @throws Exception
     */
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) throws Exception{
        Player l_currentPlayer = this.getCurrentPlayer();

        // Check if valid source country
        Country l_sourceCountry = null, l_targetCountry = null;
        for (Country l_country : l_currentPlayer.getCountriesOwned()) {
            if (l_country.getCountryName() == p_countryNameFrom) {
                l_sourceCountry = l_country;
                break;
            }
        }
        if(l_sourceCountry == null){
            throw new Exception("Player does not own the source country");
        }

        // Check if valid target country
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : this.d_gameMap.getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }
        Iterator<Country> l_it = l_allCountries.values().iterator();
        while(l_it.hasNext()){
            Country l_tempCountry = l_it.next();
            if(l_tempCountry.getCountryName() == p_targetCountryName){
                l_targetCountry = l_tempCountry;
                break;
            }
        }
        if(l_targetCountry == null){
            throw new Exception("Target country doesn't exist");
        }

        // Check for valid army units
        if(l_sourceCountry.getArmyValue() < p_armyUnits){
            throw new Exception("Insufficient army units in the country");
        }

        l_currentPlayer.setCurrentOrder(
                new AdvanceOrder(l_currentPlayer,
                        l_sourceCountry.getCountryId(),
                        l_targetCountry.getCountryId(),
                        p_armyUnits
                )
        );
        l_currentPlayer.issueOrder();
    }

    /**
     * Handler method for bomb command
     * @param p_countryId
     * @throws Exception
     */
    public void handleBomb(int p_countryId) throws Exception{
        Player l_currentPlayer = d_gamePlayers.get(d_currentPlayerIndex);
        // Check if player has bomb card
        // TODO: HandleBomb-Implement check for card

        // Check if the target country is owned by player
        Country l_targetCountry = null;
        for (Country l_country : l_currentPlayer.getCountriesOwned()) {
            if (l_country.getCountryId() == p_countryId) {
                l_targetCountry = l_country;
                break;
            }
        }
        if (l_targetCountry == null) {
            String l_errStr = "";
            if(l_targetCountry == null){
                l_errStr += "Player is bombing own country: " + p_countryId + ". ";
            }
            throw new Exception(l_errStr);
        }

        // Check if target country exists
        HashMap<Integer, Country> l_allCountries = new HashMap<>();
        for (Continent l_continent : this.d_gameMap.getContinents().values()) {
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
        }
        if(!l_allCountries.containsKey(p_countryId)){
            throw new Exception("Target Country does not exist!");
        }

        // Issue the bomb order
        l_currentPlayer.setCurrentOrder(new BombOrder(l_currentPlayer, p_countryId));
        l_currentPlayer.issueOrder();
    }

    /**
     * Handler method for blockade command
     * @param p_targetCountryId
     * @throws Exception
     */
    public void handleBlockade(int p_targetCountryId) throws Exception{
        // TODO: Implement handle blockade
    }

    /**
     * Handler method for airlift command
     * @param p_sourceCountryId
     * @param p_targetCountryId
     * @param p_numArmies
     * @throws Exception
     */
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) throws Exception{
        Player l_currentPlayer = d_gamePlayers.get(d_currentPlayerIndex);
        // Check if player has airlift card
        // TODO: handleAirlift: Implement check for card

        // Check if the source and target country is owned by player
        Country l_sourceCountry = null, l_targetCountry = null;
        for (Country l_country : l_currentPlayer.getCountriesOwned()) {
            if(l_sourceCountry != null && l_targetCountry != null) break;

            if (l_country.getCountryId() == p_targetCountryId) {
                l_targetCountry = l_country;
            } else if (l_country.getCountryId() == p_sourceCountryId){
                l_sourceCountry = l_country;
            }
        }
        if (l_sourceCountry == null || l_targetCountry == null) {
            String l_errStr = "";
            if(l_sourceCountry == null){
                l_errStr += "Player doesn't own source country: " + p_sourceCountryId + ". ";
            }
            if(l_targetCountry == null){
                l_errStr += "Player doesn't own target country: " + p_targetCountryId + ". ";
            }
            throw new Exception(l_errStr);
        }

        // Check whether the player has the sufficient army units or not
        int l_newAvailableArmyUnits = l_sourceCountry.getArmyValue() - p_numArmies;
        if (l_newAvailableArmyUnits < 0) {
            throw new Exception("Insufficient army units in country: " + p_sourceCountryId);
        }

        // Issue the airlift order
        l_currentPlayer.setCurrentOrder(new AirliftOrder(l_currentPlayer, p_sourceCountryId, p_targetCountryId, p_numArmies));
        l_currentPlayer.issueOrder();
    }

    /**
     * Handler method for the negotiate command
     * @param p_targetPlayerId
     * @throws Exception
     */
    public void handleNegotiate(int p_targetPlayerId) throws Exception{
        // TODO: Implement handle negotiate
    }

    /**
     * Handler method for commit command
     */
    public void handleCommit(){
        // TODO: Implement handle commit
        this.setCurrentPlayerIndex(this.d_currentPlayerIndex + 1);
        if(this.d_currentPlayerIndex == 0){
            // We've taken orders from all players
            // Change state to Order Execution
        }
    }
}
