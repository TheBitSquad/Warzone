package org.bitsquad.warzone.map;
import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;

import java.util.HashMap;
import java.util.Set;

/**
 * Represents a game map.
 *
 * This class defines a map with a list of continents and its Directed Graph as well all related functionality.
 */
public class Map {
    HashMap<Integer, Continent> d_continents;

    /**
     * Default Constructor
     */
    Map(){
        d_continents = new HashMap<Integer, Continent>();
    }

    /**
     * Adds a continent to the map
     * @param p_continentId continentId
     * @param p_bonusValue bonus value of the continent
     */
    void addContinent(int p_continentId, int p_bonusValue){
        d_continents.putIfAbsent(p_continentId, new Continent(p_continentId, p_bonusValue));
    }

    /**
     * Removes a continent from the map
     * @param p_continentId ID of the continent to be removed
     * @return boolean true if an existing continent is being removed, else false
     */
    boolean removeContinent(int p_continentId){
        // Get the continent to be removed
        Continent l_continentToRemove = d_continents.get(p_continentId);
        if(l_continentToRemove == null) return false;
        // Get the list of countries present in the continent
        Set<Integer> countriesToRemove = l_continentToRemove.getCountries().keySet();
        // Remove the continent
        d_continents.remove(p_continentId);
        // Remove the countries as neighbors from the other continents
        for(Continent l_continent: d_continents.values()){
            for(Country l_country: l_continent.getCountries().values()){
                for(int l_countryIdToRemove: countriesToRemove){
                    l_country.removeNeighbor(l_countryIdToRemove);
                }
            }
        }
        return true;
    }

    /**
     * Adding a country to the map
     * @param p_countryId countryId
     * @param p_continentId ID of the continent to which the country needs to be added to
     * @return boolean if adding a country to an existing continent, else false
     */
    boolean addCountry(int p_countryId, int p_continentId){

        // Check if the country is present in any continent
        for(Continent l_continent: d_continents.values()){
            if(l_continent.getCountries().containsKey(p_countryId)){
                return false;
            }
        }

        // Check if the continent exists
        if(d_continents.containsKey(p_continentId)){
            Continent l_continent = d_continents.get(p_continentId);
            l_continent.addCountry(p_countryId, "");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a country from the map
     * @param p_countryId
     */
    void removeCountry(int p_countryId){
        for(Continent l_continent: d_continents.values()){
            if(l_continent.getCountries().containsKey(p_countryId)){
                l_continent.removeCountry(p_countryId);
                break;
            }
        }
    }

    /**
     * Adds a neighbor to a country
     * @param p_sourceCountryId sourceCountryId
     * @param p_destinationCountryId destinationCountryId
     */
    void addNeighbor(int p_sourceCountryId, int p_destinationCountryId){
        for(Continent l_continent: d_continents.values()){
            if(l_continent.getCountries().containsKey(p_sourceCountryId)){
                Country l_sourceCountry = l_continent.getCountries().get(p_sourceCountryId);
                l_sourceCountry.addNeighbor(p_destinationCountryId);
            } else if (l_continent.getCountries().containsKey(p_destinationCountryId)){
                Country l_destinationCountry = l_continent.getCountries().get(p_destinationCountryId);
                l_destinationCountry.addNeighbor(p_sourceCountryId);
            }
        }
    }

    /**
     * Remove countries from being neighbors
     * @param p_sourceCountryId sourceCountryId
     * @param p_destinationCountryId destinationCountryId
     */
    void removeNeighbor(int p_sourceCountryId, int p_destinationCountryId){
        for(Continent l_continent: d_continents.values()){
            if(l_continent.getCountries().containsKey(p_sourceCountryId)){
                Country l_sourceCountry = l_continent.getCountries().get(p_sourceCountryId);
                l_sourceCountry.removeNeighbor(p_destinationCountryId);
            } else if (l_continent.getCountries().containsKey(p_destinationCountryId)){
                Country l_destinationCountry = l_continent.getCountries().get(p_destinationCountryId);
                l_destinationCountry.removeNeighbor(p_sourceCountryId);
            }
        }
    }

}
