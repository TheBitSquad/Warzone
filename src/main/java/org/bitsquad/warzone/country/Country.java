package org.bitsquad.warzone.country;
import java.util.ArrayList;

/**
 * Represents a country object.
 * 
 * This class defines a country with its ID, list of neighbors and other relevant details according to system design.
 */
public class Country {
    /**
     * The id of the country.
     */
    private int d_countryId;

    /**
     * The name of the country.
     */
    private String d_countryName;

    /**
     * The number of Armies allocated to the country.
     */
    private int d_armyValue;

    /**
     * The id of the continent to which the country belongs.
     */
    private int d_continentId;

    /**
     * The player id who owns the country.
     */
    private int d_ownedByPlayerId;

    /**
     * The list of neighbor countries' id.
     */
    private ArrayList<Integer> d_neighbors;

    /**
     * Default constructor
     */
    Country(){
        this.d_neighbors = new ArrayList<Integer>();
    }

    /**
     * Parameterized constructor
     * @param p_countryId country ID
     * @param p_continentId continent ID
     */
    public Country(int p_countryId, int p_continentId){
        this.d_countryId = p_countryId;
        this.d_continentId= p_continentId;
        this.d_neighbors = new ArrayList<Integer>();
    }
    /**
     * Parameterized constructor
     * @param p_countryId country ID
     * @param p_continentId continent ID
     * @param p_countryName country name
     * @param p_armyValue the number of army units present in a country
     * @param p_neighbors the neighbors of a country
     * @param p_ownedByPlayerId the playerid of the owner of the country
     */
    public Country(int p_countryId,int p_continentId,String p_countryName,int p_armyValue,int p_ownedByPlayerId,ArrayList<Integer> p_neighbors){
        this.d_countryId = p_countryId;
        this.d_continentId= p_continentId;
        this.d_countryName = p_countryName;
        this.d_armyValue= p_armyValue;
        this.d_ownedByPlayerId = p_ownedByPlayerId;
        this.d_neighbors = p_neighbors;
    }


    /**
     * Getter method for the Country Id.
     * @return the id of the country
     */
    public int getCountryId() {
        return d_countryId;
    }

    /**
     * Setter method for the Country Id.
     * @param p_countryId the id of country.
     */
    public void setCountryId(int p_countryId) {
        this.d_countryId = p_countryId;
    }


    /**
     * Getter method for the Country Name.
     * @return the country name.
     */
    public String getCountryName() {
        return d_countryName;
    }

    /**
     * Setter method for the Country Name
     * @param p_countryName the Country name
     */
    public void setCountryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }


    /**
     * Getter method for Army Value.
     * @return the number of Armies allocated to the country.
     */
    public int getArmyValue() {
        return d_armyValue;
    }

    /**
     * Setter method of Army Value.
     * @param p_armyValue the number of Country's Armies.
     */
    public void setArmyValue(int p_armyValue) {
        this.d_armyValue = p_armyValue;
    }


    /**
     * Getter method of continent Id.
     * @return the continent id.
     */
    public int getContinentId() {
        return d_continentId;
    }

    /**
     * Setter method of continentId which set the continent id.
     * @param p_continentId the continent id.
     */
    public void setContinentId(int p_continentId) {
        this.d_continentId = p_continentId;
    }


    /**
     * This getter method use to get the player id who owns the country.
     * @return the player id.
     */
    public int getOwnedByPlayerId() {
        return d_ownedByPlayerId;
    }

    /**
     * This setter method use to set the Player Id who owns the country.
     * @param p_ownedByPlayerId the Owned player id.
     */
    public void setOwnedByPlayerId(int p_ownedByPlayerId) {
        this.d_ownedByPlayerId = p_ownedByPlayerId;
    }


    /**
     * This getter method use to get the list of neighbor Countries.
     * @return the ArrayList of neighbor Countries
     */
    public ArrayList<Integer> getNeighbors() {
        return d_neighbors;
    }

    /**
     * This method use to set the list of neighbor countries.
     * @param p_neighbors the integer ArrayList of neighbor countries's Id.
     */
    public void setNeighbors(ArrayList<Integer> p_neighbors) {
        this.d_neighbors = p_neighbors;
    }

    /**
     *  addNeighbor method add the id of the neighbor country in the neighbors list.
     * @param p_neighborCountryId the id of the neighbor.
     * @return true if the condition is true otherwise false 
     */
    public boolean addNeighbor(int p_neighborCountryId) {
        if (!d_neighbors.contains(p_neighborCountryId) && p_neighborCountryId!=this.d_countryId) {
            d_neighbors.add(p_neighborCountryId);
            return true;
        } else {
            return false;
        }

    }

    /**
     * removeNeighbor method remove the id of the neighbor country from the neighbors list.
     * @param p_neighborCountryId the id of the neighbor country.
     * @return true if the condition is true otherwise false
     */
    public boolean removeNeighbor(int p_neighborCountryId) {
        if(d_neighbors.contains(p_neighborCountryId)) {
            d_neighbors.remove(Integer.valueOf(p_neighborCountryId));
            return true;
        }
        else {
            return false;
        }
    }
}

