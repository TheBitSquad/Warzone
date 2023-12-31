package org.bitsquad.warzone.player;

import org.bitsquad.warzone.card.Card;
import org.bitsquad.warzone.card.CardGenerator;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.order.Order;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a player in the game
 * <p>
 * The class defines a player with the aid of an ID, player name, number of army units available for the user in the following turn,
 * list of orders which it places and the list of countries it owns.
 */
public abstract class BasePlayer implements PlayerStrategy{
    private static int LastPlayerID = 0;
    private int d_id;
    private String d_name;
    protected int d_availableArmyUnits;
    protected ArrayList<Country> d_countriesOwned;
    protected ArrayList<Order> d_orderList;
    // d_currentOrder contains the current order to be issued next
    protected Order d_currentOrder;
    private HashMap<Card, Integer> d_currentCards;
    // d_hasNewTerritory specifies whether player has captured a new country or not
    private boolean d_hasNewTerritory = false;

    /**
     * Constructor
     *
     * @param p_name player name
     */
    public BasePlayer(String p_name) {
        this.d_id = BasePlayer.LastPlayerID;
        this.d_name = p_name;
        this.d_availableArmyUnits = 0;
        this.d_countriesOwned = new ArrayList<>();
        this.d_orderList = new ArrayList<>();
        this.d_currentCards = new HashMap<>();

        // Initialize the current order
        this.d_currentOrder = null;

        // Add one to the total number of players
        BasePlayer.LastPlayerID += 1;
    }

    /**
     * Getter method for player id
     *
     * @return the player id
     */
    public int getId() {
        return d_id;
    }

    /**
     * Getter method for player name
     *
     * @return the player name
     */
    public String getName() {
        return d_name;
    }

    /**
     * Getter method for player available army units
     *
     * @return the player available army units
     */
    public int getAvailableArmyUnits() {
        return d_availableArmyUnits;
    }

    /**
     * Setter method for player available army units
     *
     * @param p_availableArmyUnits army units
     */
    public void setAvailableArmyUnits(int p_availableArmyUnits) {
        this.d_availableArmyUnits = p_availableArmyUnits;
    }

    /**
     * Getter method for player countries owned
     *
     * @return the player countries owned
     */
    public ArrayList<Country> getCountriesOwned() {
        return d_countriesOwned;
    }

    /**
     * Used to fetch a player owned country using Country ID
     * @param p_countryID
     * @return Country
     */
    public Country getCountryByID(int p_countryID) {
        for (Country l_country : d_countriesOwned) {
            if (l_country.getCountryId() == p_countryID) {
                return l_country;
            }
        }
        return null;
    }

    /**
     * Used to check if player has a particular country
     * @param p_countryID
     * @return boolean
     */
    public boolean hasCountryWithID(int p_countryID) {
        for (Country l_country : d_countriesOwned) {
            if (l_country.getCountryId() == p_countryID) {
                return true;
            }
        }

        return false;
    }

    /**
     * Setter method for player countries owned
     *
     * @param p_countriesOwned countries owned
     */
    public void setCountriesOwned(ArrayList<Country> p_countriesOwned) {
        this.d_countriesOwned = p_countriesOwned;
    }

    /**
     * Getter method for player order list
     *
     * @return the order list
     */
    public ArrayList<Order> getOrderList() {
        return d_orderList;
    }

    /**
     * Getter method for player current order
     *
     * @return player current order
     */
    public Order getCurrentOrder() {
        return d_currentOrder;
    }

    /**
     * Setter method for player current order
     *
     * @param p_currentOrder current order
     */
    public void setCurrentOrder(Order p_currentOrder) {
        this.d_currentOrder = p_currentOrder;
    }

    /**
     * Getter for the cards which a player holds
     * @return HashMap
     */
    public HashMap<Card, Integer> getCurrentCards() {
        return d_currentCards;
    }

    /**
     * Checks if a player has a card
     * @param p_card
     * @return boolean
     */
    public boolean hasCard(Card p_card) {
        return d_currentCards.get(p_card) > 0;
    }

    /**
     * Tells if a player has a new territory
     * @return boolean
     */
    public boolean hasNewTerritory() {
        return d_hasNewTerritory;
    }

    /**
     * Sets if the player has gained a new territory
     * @param p_hasNewTerritory
     */
    public void setHasNewTerritory(boolean p_hasNewTerritory) {
        d_hasNewTerritory = p_hasNewTerritory;
    }

    /**
     * Get the next order in the order list
     *
     * @return the next order
     */
    public Order nextOrder() {
        if (this.d_orderList.isEmpty()) {
            return null;
        }

        return this.d_orderList.remove(0);
    }

    /**
     * Adds a country to the countries owned
     *
     * @param p_country Country object
     */
    public void addCountryOwned(Country p_country) {
        this.d_countriesOwned.add(p_country);
    }

    /**
     * Removes a country from countries owned
     *
     * @param p_country Country object
     */
    public void removeCountryOwned(Country p_country) {
        this.d_countriesOwned.remove(p_country);
    }

    /**
     * Helper method to check if the next order to be executed is a Deploy order
     *
     * @return
     */
    public Boolean isNextDeploy() {
        if (this.d_orderList.isEmpty()) return false;
        return this.d_orderList.get(0).getClass().getSimpleName().equalsIgnoreCase("DeployOrder");
    }

    /**
     * Clears the state of the player for executing the next turn of the game
     */
    public void clearState() {
        d_hasNewTerritory = false;
    }
}
