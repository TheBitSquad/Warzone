package org.bitsquad.warzone.country;
import java.util.ArrayList;
/**
 * Represents a country object.
 * 
 * This class defines a country with its ID, list of neighbours and other relevant details according to system design.
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
     * noOfArmies stores the number of Armies allocated to the country by the owner.
     */
    private int d_noOfArmies;

    /**
     * The id of the continent to which the country belongs.
     */
    private int d_continentId;

    /**
     * The player id who owns the country.
     */
    private int d_ownedByPlayerId;

    /**
     * d_neighbors stores the list of neighbor countries' id.
     */
    private ArrayList<Integer> d_neighbors;


    Country(){
        this.d_neighbors = new ArrayList<Integer>();
    }
    Country(int p_countryId,int p_continentId,String p_countryName,int p_noOfArmies,int p_ownedByPlayerId,ArrayList<Integer> p_neighbors){
        this.d_countryId = p_countryId;
        this.d_countryName = p_countryName;
        this.d_noOfArmies= p_noOfArmies;
        this.d_continentId= p_continentId;
        this.d_ownedByPlayerId = p_ownedByPlayerId;
        this.d_neighbors = p_neighbors;
    }


    /**
     *  getter method of Country Id.
     * @return the id of the country
     */
    public int getCountryId() {
        return d_countryId;
    }

    /**
     * setId method will set the id of the Country.
     * @param p_id the id of country.
     */
    public void setCountryId(int p_countryId) {
        this.d_countryId = p_countryId;
    }


    /**
     * getter method for Country name
     * @return the country name.
     */
    public String getCountryName() {
        return d_countryName;
    }

    /**
     * setter method for country name.
     * @param p_countryName
     */
    public void setCountryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }


    /**
     * Getter method of NoOfArmies which get number of Country's Armies.
     * @return the number of Armies allocated to the country by the owner.
     */
    public int getNoOfArmies() {
        return d_noOfArmies;
    }

    /**
     * Setter method of noOfArmies.
     * @param p_noOfArmies the number of Country's Armies.
     */
    public void setNoOfArmies(int p_noOfArmies) {
        this.d_noOfArmies = p_noOfArmies;
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
