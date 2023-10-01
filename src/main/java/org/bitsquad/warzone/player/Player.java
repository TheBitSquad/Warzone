package org.bitsquad.warzone.player;

import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.order.Order;

import java.util.ArrayList;

/**
 * Represents a player in the game
 * <p>
 * The class defines a player with the aid of an ID, player name, number of army units available for the user in the following turn,
 * list of orders which it places and the list of countries it owns.
 */
public class Player {
    private static int LastPlayerID = 0;

    private int d_id;
    private String d_name;
    private int d_availableArmyUnits;
    private ArrayList<Country> d_countriesOwned;
    private ArrayList<Order> d_orderList;

    // d_currentOrder contains the current order to be issued next
    private Order d_currentOrder;

    /**
     * Constructor
     *
     * @param p_name player name
     */
    public Player(String p_name) {
        this.d_id = Player.LastPlayerID;
        this.d_name = p_name;
        this.d_availableArmyUnits = 0;
        this.d_countriesOwned = new ArrayList<>();
        this.d_orderList = new ArrayList<>();

        // Initialize the current order
        this.d_currentOrder = new Order(
                this.d_id,
                0,
                0,
                0,
                Order.TYPEOFACTION.DEPLOY
        );

        // Add one to the total number of players
        Player.LastPlayerID += 1;
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
}
