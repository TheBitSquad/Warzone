package org.bitsquad.warzone.continent;

import java.util.HashMap;
import java.util.Scanner;

import org.bitsquad.warzone.country.Country;

/**
 * Represents a Continent Object
 * 
 * This class defines a continent with its ID, its constituent Countries and its bonus value.
 */
public class Continent {
    
    private int d_continentId;
    private int d_value;
    private HashMap<Integer, Country> d_countries;

    public Continent() {
        d_countries = new HashMap<>();
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

    /**
     *  getter method of the bonus value of continent
     * @return the bonus value of continent 
     */
    public int getValue() {
        return d_value;
    }
    /**
     * Setter method of bonus value
     * @param p_value the bonus value of the continent
     * @return the value
     */
    public void setValue(int p_value) {
        this.d_value = p_value;
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
       
        //TODO: Need to implement Country class
        d_countries.putIfAbsent(p_countryId, new Country());
    }

    /**
    * Method to remove a country using country Id
    * @param p_countryId  the country id
    */
    public void removeCountry(int p_countryId) {
        d_countries.remove(p_countryId);
    }
}