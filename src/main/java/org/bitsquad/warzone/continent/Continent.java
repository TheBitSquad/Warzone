package org.bitsquad.warzone.continent;

import java.util.HashMap;

import org.bitsquad.warzone.country.Country;

/**
 * Represents a Continent Object
 * 
 * This class defines a continent with its ID, its constituent Countries and its bonus value.
 */
public class Continent {
    
    private int d_continentId;
    private String d_continentName;
    private int d_bonusValue;
    private HashMap<Integer, Country> d_countries;

    /**
     * Default Constructor
     */
    public Continent() {
        this.d_countries = new HashMap<>();
    }

    /**
     * Parameterized Constructor
     * @param p_continentId continent ID
     * @param p_bonusValue continents' bonus value
     */
    public Continent(int p_continentId, int p_bonusValue){
        d_continentId = p_continentId;
        d_bonusValue = p_bonusValue;
        this.d_countries = new HashMap<>();
    }

    public Continent(int p_continentId,String p_continentName, int p_bonusValue){
        d_continentId = p_continentId;
        d_continentName = p_continentName;
        d_bonusValue = p_bonusValue;
        this.d_countries = new HashMap<>();
    }

    /**
     * Getter method of continentId.
     * @return the id of the continent
     */
    public int getId() {
        return d_continentId;
    }

    /**
     *  Setter method of continentId.
     * @param p_continentId the continent id
     */
    public void setId(int p_continentId) {
        this.d_continentId = p_continentId;
    }

    public void setName(String p_continentName){
        this.d_continentName = p_continentName;
    }

    public String getName(){
        return d_continentName;
    }

    /**
     *  getter method of the bonus value of continent
     * @return d_bonusValue the bonus value of continent
     */
    public int getValue() {
        return d_bonusValue;
    }

    /**
     * Setter method of bonus value
     * @param p_bonusValue the bonus value of the continent
     */
    public void setValue(int p_bonusValue) {
        this.d_bonusValue = p_bonusValue;
    }

    /** 
    * Getter  method for Countries hashmap
    * @return the hashmap of countries
    */
    public HashMap<Integer, Country> getCountries() {
        return d_countries;
    }

    /**
     * Setter method of countries hashmap.
     * @param p_countries the hashmap of countries
     */
    public void setCountries(HashMap<Integer, Country> p_countries) {
        this.d_countries = p_countries;
    }

    /** 
    * Method to add a Country
    * @param p_countryId the country id
    * @param p_countryName the name of the country
    */
    public void addCountry(int p_countryId, String p_countryName) {
        d_countries.putIfAbsent(p_countryId, new Country(p_countryId, p_countryName, d_continentId));
    }

    /**
    * Method to remove a country using country Id
    * @param p_countryId  the country id
    */
    public void removeCountry(int p_countryId) {
        d_countries.remove(p_countryId);
    }
}