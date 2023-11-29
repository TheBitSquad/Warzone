package org.bitsquad.warzone.gameengine;

import org.bitsquad.warzone.card.Card;
import org.bitsquad.warzone.card.CardGenerator;
import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.gameengine.phase.GameFinished;
import org.bitsquad.warzone.gameengine.phase.IssueOrderPostDeploy;
import org.bitsquad.warzone.gameengine.phase.Phase;
import org.bitsquad.warzone.gameengine.phase.StartupMapEditing;
import org.bitsquad.warzone.gameengine.policy.PolicyManager;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.bitsquad.warzone.map.Map;
import org.bitsquad.warzone.order.*;
import org.bitsquad.warzone.player.*;

import java.io.IOException;
import java.util.*;

/**
 * Represents the Game engine.
 * <p>
 * This class defines a game with a map and a list of players, along with execution details and methods.
 */
public class GameEngine {
    private static GameEngine d_instance;
    private Phase d_gamePhase;
    private Map d_gameMap;
    private List<BasePlayer> d_gamePlayers;
    private int d_currentPlayerIndex;
    PolicyManager d_policyManager;

    BasePlayer d_winner = null;
    private int d_roundNumber = 0;

    private int d_maxRounds = Integer.MAX_VALUE;

    /**
     * Default Constructor
     */
    private GameEngine() {
        d_gameMap = new Map();
        d_gamePlayers = new ArrayList<>();
        d_policyManager = new PolicyManager();
        d_gamePhase = new StartupMapEditing(this);
    }

    public BasePlayer getWinner() {
        return d_winner;
    }

    public void setWinner(BasePlayer d_winner) {
        LogEntryBuffer.getInstance().log("Winner: " + d_winner.getName() + " , id: " + d_winner.getId());
        this.d_winner = d_winner;
    }

    public void setRoundNumber(int p_value){
        LogEntryBuffer.getInstance().log("Round: " + p_value);
        this.d_roundNumber = p_value;
    }

    public void incrementRoundNumber(){
        setRoundNumber(d_maxRounds + 1);
    }

    public int getRoundNumber(){
        return this.d_roundNumber;
    }

    public void setMaxRounds(int p_value){
        LogEntryBuffer.getInstance().log("Max rounds set to: " + p_value);
        d_maxRounds = p_value;
    }

    /**
     * Sets the phase
     *
     * @param p_newPhase
     */
    public void setPhase(Phase p_newPhase) {
        this.d_gamePhase = p_newPhase;
        LogEntryBuffer.getInstance().log("Phase changed. Current Phase: " + this.d_gamePhase.getClass().getSimpleName());
    }

    public Phase getPhase(){
        return this.d_gamePhase;
    }
    /**
     * Getter for PolicyManager instance
     *
     * @return PolicyManager
     */
    public PolicyManager getPolicyManager() {
        return d_policyManager;
    }

    /**
     * Returns the currentPlayerIndex
     *
     * @return int
     */
    public int getCurrentPlayerIndex() {
        return d_currentPlayerIndex;
    }

    /**
     * Sets the currentPlayer
     *
     * @param p_currentPlayerIndex is the current player index
     */
    public void setCurrentPlayerIndex(int p_currentPlayerIndex) {
        this.d_currentPlayerIndex = p_currentPlayerIndex;
        LogEntryBuffer.getInstance().log("Current turn: Player id: " + this.getCurrentPlayer().getName());

        if (!this.getCurrentPlayer().getClass().getSimpleName().equalsIgnoreCase("Player")) {
            this.getCurrentPlayer().issueOrder();
            this.setPhase(new IssueOrderPostDeploy(GameEngine.getInstance()));
            this.handleCommit();
        }
    }

    /**
     * Increments the player index to the next logical one
     */
    public void setCurrentPlayerIndexToNextPlayer() {
        this.setCurrentPlayerIndex((this.getCurrentPlayerIndex() + 1) % this.d_gamePlayers.size());
    }

    /**
     * Getter method for game map
     *
     * @return gamemap the map of the current game engine
     */
    public Map getGameMap() {
        return d_gameMap;
    }

    /**
     * Setter for game map
     *
     * @param p_gameMap the map
     */
    public void setGameMap(Map p_gameMap) {
        this.d_gameMap = p_gameMap;
    }

    /**
     * Getter for game players list
     *
     * @return list of game players
     */
    public List<BasePlayer> getGamePlayers() {
        return d_gamePlayers;
    }

    /**
     * Setter for game players
     *
     * @param p_gamePlayers list of game players
     */
    public void setGamePlayers(List<BasePlayer> p_gamePlayers) {
        this.d_gamePlayers = p_gamePlayers;
    }

    /**
     * Returns the current player object
     *
     * @return Player
     */
    public BasePlayer getCurrentPlayer() {
        return this.d_gamePlayers.get(this.d_currentPlayerIndex);
    }

    /**
     * Singleton instance getter
     *
     * @return instance of GameEngine
     */
    public static GameEngine getInstance() {
        if (d_instance == null) {
            d_instance = new GameEngine();
        }
        return d_instance;
    }

    public static void resetInstance() {
        d_instance = new GameEngine();
    }


    /**
     * Handler for executing orders
     */
    public void handleExecuteOrders() {
        this.d_gamePhase.handleExecuteOrders();
    }

    /**
     * Executes orders in Round-Robin fashion
     */
    public void executeOrders() {
        LogEntryBuffer.getInstance().log("Executing Orders");
        Order l_orderToExecute = null;

        boolean l_isAllOrderSetsEmpty = false;
        boolean l_allDeployCommandsCompleted = false;

        while (!l_allDeployCommandsCompleted) {
            l_allDeployCommandsCompleted = true;
            for (BasePlayer l_player : this.d_gamePlayers) {
                l_orderToExecute = null;
                if (l_player.isNextDeploy()) {
                    l_allDeployCommandsCompleted = false;
                    l_orderToExecute = l_player.nextOrder();
                }
                if (l_orderToExecute != null) {
                    LogEntryBuffer.getInstance().log("Executing: " + l_orderToExecute);
                    if (this.d_policyManager.checkPolicies(l_orderToExecute) && l_orderToExecute.isValid()) {
                        l_orderToExecute.execute();
                    } else {
                        LogEntryBuffer.getInstance().log("the order is invalid");
                    }
                }
            }
        }

        l_isAllOrderSetsEmpty = false;
        while (!l_isAllOrderSetsEmpty) {
            l_isAllOrderSetsEmpty = true;
            for (BasePlayer l_player : this.d_gamePlayers) {
                l_orderToExecute = l_player.nextOrder();
                if (l_orderToExecute != null) {
                    l_isAllOrderSetsEmpty = false;
                    LogEntryBuffer.getInstance().log("Executing: " + l_orderToExecute);
                    if (this.d_policyManager.checkPolicies(l_orderToExecute) && l_orderToExecute.isValid()) {
                        l_orderToExecute.execute();
                    } else {
                        if (!this.d_policyManager.checkPolicies(l_orderToExecute))
                            LogEntryBuffer.getInstance().log("The order violates current round policies.");
                        else if (!l_orderToExecute.isValid()) {
                            LogEntryBuffer.getInstance().log("The order is invalid");
                        }
                    }
                }
            }
        }
    }

    /**
     * Sets up the start of a new round
     */
    public void nextRound() {
        LogEntryBuffer.getInstance().log("New Round!");

        for (BasePlayer l_player : this.d_gamePlayers) {
            // Assign random cards
            if (l_player.hasNewTerritory()) {
                Card l_generatedCard = CardGenerator.generateRandomCard();
                int l_numberOfCard = l_player.getCurrentCards().getOrDefault(l_generatedCard, 0);
                l_player.getCurrentCards().put(l_generatedCard, l_numberOfCard + 1);
                LogEntryBuffer.getInstance().log("Award " + l_generatedCard + " to the player");
            }

            // Assign reinforcement units
            // Calculate the reinforcement
            int l_numberReinforcement = getNumberOfReinforcementUnits(l_player);
            // Add the army
            l_player.setAvailableArmyUnits(l_player.getAvailableArmyUnits() + l_numberReinforcement);
            LogEntryBuffer.getInstance().log(l_player.getName() + ": Reinforcements: " + l_numberReinforcement +
                    ". Total Units available to deploy: " + l_player.getAvailableArmyUnits());

            // Clear the state of players
            l_player.clearState();
        }
        d_policyManager.clearPolicies();
        incrementRoundNumber();
        if(d_roundNumber >= d_maxRounds){
            setPhase(new GameFinished(this));
            LogEntryBuffer.getInstance().log("Max number of rounds reached.");
            LogEntryBuffer.getInstance().log("Draw!");
            return;
        }
        setCurrentPlayerIndex(0);
    }

    /**
     * Calculates number of reinforcement units for a player
     *
     * @param p_player Player Objec
     * @return int number of reinforcement units
     */
    public int getNumberOfReinforcementUnits(BasePlayer p_player) {
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

    public boolean checkPlayerWinAndRemoveLosers(){
        Iterator<BasePlayer> l_playerIterator = this.d_gamePlayers.iterator();
        while(l_playerIterator.hasNext()){
            BasePlayer l_player = l_playerIterator.next();
            if(l_player.getCountriesOwned().isEmpty()){
                LogEntryBuffer.getInstance().log(l_player.getName() + " owns no countries. Player out!");
                l_playerIterator.remove();
            }
        }
        if(d_gamePlayers.size() == 1){
            this.setWinner(d_gamePlayers.get(0));
            return true;
        }
        return false;
    }
    /**
     * Handler method for loadmap command
     *
     * @param p_filename filename of map file
     * @throws Exception if the map is invalid or IOError
     */
    public void handleLoadMap(String p_filename) throws Exception {
        d_gamePhase.handleLoadMap(p_filename);
    }

    /**
     * Handler method for deploy army command
     *
     * @param p_targetCountryID ID of target country
     * @param p_armyUnits       number of army units
     * @throws Exception
     */
    public void handleDeployArmy(int p_targetCountryID, int p_armyUnits) throws Exception {
        this.d_gamePhase.handleDeployArmy(p_targetCountryID, p_armyUnits);
    }

    /**
     * Handler for assigncountries command
     *
     * @throws Exception
     */
    public void handleAssignCountries() throws Exception {
        this.d_gamePhase.handleAssignCountries();
    }

    /**
     * Handler method for the editcontinent command
     *
     * @param p_addArray
     * @param p_removeIds
     */
    public void handleEditContinent(int p_addArray[], int p_removeIds[]) {
        this.d_gamePhase.handleEditContinent(p_addArray, p_removeIds);
    }

    /**
     * Handler method for editcountry command
     *
     * @param p_addIds
     * @param p_removeIds
     */
    public void handleEditCountry(int p_addIds[], int p_removeIds[]) {
        this.d_gamePhase.handleEditCountry(p_addIds, p_removeIds);
    }

    /**
     * Handler method for editneighbor command
     *
     * @param p_addIds
     * @param p_removeIds
     */
    public void handleEditNeighbor(int p_addIds[], int p_removeIds[]) {
        this.d_gamePhase.handleEditNeighbor(p_addIds, p_removeIds);
    }

    /**
     * Handler method for savemap command
     *
     * @param p_filename
     * @throws Exception
     */
    public void handleSaveMap(String p_filename, boolean c) throws Exception {
        this.d_gamePhase.handleSaveMap(p_filename,c);
    }

    /**
     * Handler method for editmap command
     *
     * @param p_filename
     */
    public void handleEditMap(String p_filename) throws IOException {
        this.d_gamePhase.handleEditMap(p_filename);
    }

    /**
     * Handler method for the validatemap command
     */
    public void handleValidateMap() {
        this.d_gamePhase.handleValidateMap();
    }

    /**
     * Handler method for the gameplayer command
     *
     * @param p_addNames
     * @param p_removeNames
     */
    public void handleGamePlayer(String p_addNames[], String p_removeNames[]) {
        this.d_gamePhase.handleGamePlayer(p_addNames, p_removeNames);
    }

    /**
     * Handler method for gamplayer -add command
     *
     * @param p_playerName name of the player
     * @throws Exception
     */
    public void handleAddPlayer(String p_playerName) throws Exception {
        // IF it is a computer player, just add.
        switch (p_playerName.toLowerCase()){
            case "aggressive": d_gamePlayers.add(new AggressivePlayer(p_playerName)); return;
            case "benevolent": d_gamePlayers.add(new BenevolentPlayer(p_playerName)); return;
            case "cheater": d_gamePlayers.add(new CheaterPlayer(p_playerName)); return;
            case "random": d_gamePlayers.add(new RandomPlayer(p_playerName)); return;
            default: break;
        }

        // Check for existing human players with the same name
        for (BasePlayer l_player : this.d_gamePlayers) {
            if (l_player.getName().equalsIgnoreCase(p_playerName)) {
                throw new Exception("Player already exists!");
            }
        }
        d_gamePlayers.add(new Player(p_playerName));
    }

    /**
     * Handler method of remove player command
     *
     * @param p_playerName Player name
     * @throws Exception
     */
    public void handleRemovePlayer(String p_playerName) throws Exception {
        for (BasePlayer l_player : this.d_gamePlayers) {
            if (l_player.getName().equalsIgnoreCase(p_playerName)) {
                this.d_gamePlayers.remove(l_player);
                return;
            }
        }
        throw new Exception("Player does not exist!");
    }

    /**
     * Handler method for advance command
     *
     * @param p_countryNameFrom
     * @param p_targetCountryName
     * @param p_armyUnits
     * @throws Exception
     */
    public void handleAdvance(String p_countryNameFrom, String p_targetCountryName, int p_armyUnits) throws Exception {
        this.d_gamePhase.handleAdvance(p_countryNameFrom, p_targetCountryName, p_armyUnits);
    }

    /**
     * Handler method for bomb command
     *
     * @param p_countryId
     * @throws Exception
     */
    public void handleBomb(int p_countryId) throws Exception {
        this.d_gamePhase.handleBomb(p_countryId);
    }

    /**
     * Handler method for blockade command
     *
     * @param p_targetCountryId
     * @throws Exception
     */
    public void handleBlockade(int p_targetCountryId) throws Exception {
        this.d_gamePhase.handleBlockade(p_targetCountryId);
    }

    /**
     * Handler method for airlift command
     *
     * @param p_sourceCountryId
     * @param p_targetCountryId
     * @param p_numArmies
     * @throws Exception
     */
    public void handleAirlift(int p_sourceCountryId, int p_targetCountryId, int p_numArmies) throws Exception {
        this.d_gamePhase.handleAirlift(p_sourceCountryId, p_targetCountryId, p_numArmies);
    }

    /**
     * Handler method for the negotiate command
     *
     * @param p_targetPlayerId
     * @throws Exception
     */
    public void handleNegotiate(int p_targetPlayerId) throws Exception {
        this.d_gamePhase.handleNegotiate(p_targetPlayerId);
    }

    /**
     * Handler method for commit command
     */
    public void handleCommit() {
        this.d_gamePhase.handleCommit();
    }

    /**
     * Used to get player instance using the player ID
     *
     * @param p_playerID Player ID
     * @return Player instance
     */
    public BasePlayer getPlayerByID(int p_playerID) {
        for (BasePlayer l_player : d_gamePlayers) {
            if (l_player.getId() == p_playerID) {
                return l_player;
            }
        }
        return null;
    }
}
