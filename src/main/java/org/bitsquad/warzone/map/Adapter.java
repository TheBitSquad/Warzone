package org.bitsquad.warzone.map;

import org.bitsquad.warzone.continent.Continent;

import java.util.HashMap;

/**
 * The Adapter class extends the Map class and adapts the functionality of the ConquestMap class.
 *
 */
public class Adapter extends Map{
    ConquestMap d_conquestMap;

    /**
     * Constructs an Adapter object with a ConquestMap instance.
     *
     * @param p_conquestMap The ConquestMap instance to be adapted.
     */
    public Adapter(ConquestMap p_conquestMap){
        this.d_conquestMap = p_conquestMap;
    }

    /**
     * Gets the continents from the adapted ConquestMap.
     *
     * @return HashMap containing continent IDs and corresponding Continent objects.
     */
    public HashMap<Integer, Continent> getContinents() {
        return d_conquestMap.getContinents();
    }

    /**
     * Adds a continent with the specified ID and bonus value to the adapted ConquestMap.
     *
     * @param p_continentId The ID of the continent.
     * @param p_bonusValue  The bonus value of the continent.
     */
    public void addContinent(int p_continentId, int p_bonusValue){
        this.d_conquestMap.addContinent(p_continentId,p_bonusValue);
    }

    /**
     * Removes the continent with the specified ID from the adapted ConquestMap.
     *
     * @param p_continentId The ID of the continent to be removed.
     * @return True if the continent is successfully removed, false otherwise.
     */
    public boolean removeContinent(int p_continentId){
       return d_conquestMap.removeContinent(p_continentId);
    }

    /**
     * Adds a country with the specified ID to the adapted ConquestMap.
     *
     * @param p_countryId   The ID of the country.
     * @param p_continentId The ID of the continent.
     * @return True if the country is successfully added, false otherwise.
     */
    public boolean addCountry(int p_countryId, int p_continentId){
        return d_conquestMap.addCountry(p_countryId,p_continentId);
    }

    /**
     * Removes the country with the specified ID from the adapted ConquestMap.
     *
     * @param p_countryId The ID of the country to be removed.
     */
    public void removeCountry(int p_countryId){
        d_conquestMap.removeCountry(p_countryId);
    }

    /**
     * Adds a neighbor relationship between two countries in the adapted ConquestMap.
     *
     * @param p_sourceCountryId      The ID of the source country.
     * @param p_destinationCountryId The ID of the destination country.
     * @return True if the neighbor relationship is successfully added, false otherwise.
     */
    public boolean addNeighbor(int p_sourceCountryId, int p_destinationCountryId){
        return d_conquestMap.addNeighbor(p_sourceCountryId,p_destinationCountryId);
    }

    /**
     * Removes a neighbor relationship between two countries in the adapted ConquestMap.
     *
     * @param p_sourceCountryId      The ID of the source country.
     * @param p_destinationCountryId The ID of the destination country.
     */
    public void removeNeighbor(int p_sourceCountryId, int p_destinationCountryId){
        d_conquestMap.removeNeighbor(p_sourceCountryId,p_destinationCountryId);
    }

    /**
     * Loads a map from the specified file using the adapted ConquestMap.
     *
     * @param p_fileName The name of the map file.
     * @return True if the map is successfully loaded, false otherwise.
     */
    public boolean loadMap(String p_fileName){
        return d_conquestMap.loadMap(p_fileName);
    }

    /**
     * Saves the map to a file with the specified name using the adapted ConquestMap.
     *
     * @param p_fileName The name of the map file (without extension).
     * @throws Exception If the map is invalid and cannot be saved.
     */
    public void saveMap(String p_fileName) throws Exception {
        d_conquestMap.saveMap(p_fileName);
    }

    /**
     * Edits the map by loading it from the specified file using the adapted ConquestMap.
     *
     * @param p_fileName The name of the map file.
     */
    public void editMap(String p_fileName) {
        d_conquestMap.editMap(p_fileName);
    }


    /**
     * Visualizes the graph representation of the map using the adapted ConquestMap.
     */
    public void visualizeGraph(){
        d_conquestMap.visualizeGraph();
    }

    /**
     * Validates the current state of the map using the adapted ConquestMap.
     *
     * @return True if the map is valid, false otherwise.
     */
    public boolean validateMap(){
        return d_conquestMap.validateMap();
    }
}
