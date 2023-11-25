package org.bitsquad.warzone.map;

import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.logger.LogEntryBuffer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The ConquestMap class serves as an adaptee for the Adapter pattern. It adapts the functionality of the Map class
 * to be compatible with the ConquestMap requirements.
 */
public class ConquestMap {
    ArrayList<String[]> d_conquestNeighbors;
    Map d_map;

    /**
     * Constructs a ConquestMap object with an empty list of conquest neighbors and an instance of the Map class.
     */
    public ConquestMap(){
        d_conquestNeighbors = new ArrayList<>();
        d_map = new Map();
    }

    /**
     * Gets the continents from the underlying Map instance.
     * @return HashMap containing continent IDs and corresponding Continent objects.
     */
    public HashMap<Integer, Continent> getContinents(){
        return d_map.getContinents();
    }

    /**
     * Adds a continent with the specified ID, name, and bonus value.
     *
     * @param p_continentId    The ID of the continent.
     * @param p_continentName  The name of the continent.
     * @param p_bonusValue     The bonus value of the continent.
     */
    public void addContinent(int p_continentId, String p_continentName, int p_bonusValue) {
        d_map.addContinent(p_continentId, p_continentName, p_bonusValue);
    }

    /**
     * Adds a continent with the specified ID.
     *
     * @param p_continentId The ID of the continent.
     * @param p_bonusValue  The bonus value of the continent.
     */
    public void addContinent(int p_continentId, int p_bonusValue){
        d_map.addContinent(p_continentId,p_bonusValue);
    }

    /**
     * Removes the continent with the specified ID.
     *
     * @param p_continentId The ID of the continent to be removed.
     * @return True if the continent is successfully removed, false otherwise.
     */
    public boolean removeContinent(int p_continentId){
        return d_map.removeContinent(p_continentId);
    }

    /**
     * Adds a country with the specified ID.
     *
     * @param p_countryId   The ID of the country.
     * @param p_continentId The ID of the continent.
     * @return True if the country is successfully added, false otherwise.
     */
    public boolean addCountry(int p_countryId, int p_continentId){
        return d_map.addCountry(p_countryId,p_continentId);
    }

    /**
     * Adds a territory with the specified ID, name, and continent ID.
     *
     * @param p_territoryId   The ID of the territory.
     * @param p_territoryName The name of the territory.
     * @param p_continentId   The ID of the continent.
     */
    public void addTerritory(int p_territoryId, String p_territoryName, int p_continentId){
        d_map.addCountry(p_territoryId, p_territoryName, p_continentId);
    }

    /**
     * Removes the country with the specified ID.
     *
     * @param p_countryId The ID of the country to be removed.
     */
    public void removeCountry(int p_countryId){
        d_map.removeCountry(p_countryId);
    }

    /**
     * Adds a neighbor relationship between two territories.
     *
     * @param p_sourceTerritoryId      The ID of the source territory.
     * @param p_destinationTerritoryId The ID of the destination territory.
     * @return True if the neighbor relationship is successfully added, false otherwise.
     */
    public boolean addNeighbor(int p_sourceTerritoryId, int p_destinationTerritoryId){
        return d_map.addNeighbor(p_sourceTerritoryId,p_destinationTerritoryId);
    }

    /**
     * Removes a neighbor relationship between two territories.
     *
     * @param p_sourceCountryId      The ID of the source territory.
     * @param p_destinationCountryId The ID of the destination territory.
     */
    public void removeNeighbor(int p_sourceCountryId, int p_destinationCountryId){
        d_map.removeNeighbor(p_sourceCountryId,p_destinationCountryId);
    }

    /**
     * Loads territory data from the specified BufferedReader.
     *
     * @param p_bufferedReader The BufferedReader containing territory data.
     */
    private void loadConquestTerritories(BufferedReader p_bufferedReader) {
        int l_countryId=1;

        try{
            String l_lines ;
            while ((l_lines = p_bufferedReader.readLine()) != null) {
                if(l_lines.isEmpty())
                    continue;
                String[] l_data = l_lines.split(",");
                int l_continentId= getContinentIdByName(l_data[3]);
                addTerritory(l_countryId, l_data[0], l_continentId);
                d_conquestNeighbors.add(l_data);
                l_countryId++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        processNeighborsData(d_conquestNeighbors);
    }

    /**
     * Processes the data of conquest neighbors and establishes neighbor relationships.
     *
     * @param p_conquestNeighbors The list of conquest neighbors data.
     */
    private void processNeighborsData(ArrayList<String[]> p_conquestNeighbors){
        for(String[] l_conquestNeighbor : p_conquestNeighbors){
            int l_sourceCountryId = getCountryIdByName(l_conquestNeighbor[0]);
            String[] l_neighbors = Arrays.copyOfRange(l_conquestNeighbor, 4, l_conquestNeighbor.length);
            for(String l_neighbor: l_neighbors){
                int l_targetCountryId = getCountryIdByName(l_neighbor);
                addNeighbor(l_sourceCountryId,l_targetCountryId);
            }
        }
    }


    /**
     * Reads and loads continent data from the given BufferedReader, adding continents to the map.
     *
     * @param p_bufferedReader The BufferedReader containing continent data to be loaded.
     */
    private void loadConquestContinents(BufferedReader p_bufferedReader) {
        int l_continentId=1;
        try{
            String l_lines = p_bufferedReader.readLine();
            while (!(l_lines == null) && !(l_lines.isEmpty())) {
                String[] l_data = l_lines.split("=");
                addContinent(l_continentId,l_data[0],Integer.parseInt(l_data[1]));
                l_lines = p_bufferedReader.readLine();
                l_continentId++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Loads a map from the specified file name, reading continents and territories from the file.
     *
     * @param p_fileName The name of the map file to be loaded.
     * @return True if the map is successfully loaded and validated, false otherwise.
     */
    public boolean loadMap(String p_fileName){
        File l_file_name = new File(p_fileName);
        try{
            if(l_file_name.exists()){
                BufferedReader l_bufferedReader = new BufferedReader(new FileReader(l_file_name));
                String l_lines;
                while((l_lines = l_bufferedReader.readLine()) != null){
                    switch (l_lines.toLowerCase()) {
                        case "[continents]": loadConquestContinents(l_bufferedReader);
                            break;
                        case "[territories]": loadConquestTerritories(l_bufferedReader);
                            break;
                    }
                }
                l_bufferedReader.close();
            }
            return validateMap();
        }
        catch (IOException e){
            LogEntryBuffer.getInstance().log("Unable to load the map," + e.getMessage());
        }
        return false;
    }

    /**
     * Edits the map by loading it from the specified file name.
     * If the file exists, it loads the map; otherwise, it creates a new map file.
     *
     * @param p_fileName The name of the map file to be edited.
     */
    public void editMap(String p_fileName) {
        File l_fileName = new File(p_fileName);
        if(l_fileName.exists())
            loadMap(p_fileName);
        else{
            try{
                BufferedWriter l_bufferedWriter = new BufferedWriter(new FileWriter(p_fileName));
                l_bufferedWriter.write("[continents]\n\n");
                l_bufferedWriter.write("[territories]\n");
                l_bufferedWriter.flush();
                l_bufferedWriter.close();
            }
            catch(IOException e){
                LogEntryBuffer.getInstance().log("Error creating new file" + e.getMessage());
            }
        }
    }

    /**
     * Saves the map to a file with the specified name.
     * The map is saved with continents and territories, including their neighbors.
     *
     * @param p_fileName The name of the map file (without extension) to be saved.
     * @throws Exception If the map is invalid and cannot be saved.
     */
    public void saveMap(String p_fileName) throws Exception {
        if (!validateMap()) {
            throw new Exception("Invalid Map, cannot save");
        }
        BufferedWriter l_bufferedWriter = new BufferedWriter(new FileWriter(p_fileName + ".map"));
        StringBuilder l_stringBuilder = new StringBuilder("\n[Territories]\n");
        //save continents data
        l_bufferedWriter.write("[Continents]\n");
        for (Continent l_continents : d_map.getContinents().values())
            l_bufferedWriter.write(l_continents.getName() + "=" + l_continents.getValue() + "\n");
        //save countries data and neighbors list
        for (Continent l_continents : d_map.getContinents().values()) {
            for (int l_countryId : l_continents.getCountries().keySet()) {
                Country l_country = l_continents.getCountries().get(l_countryId);
                l_stringBuilder.append(l_country.getCountryName()).append(",");
                l_stringBuilder.append("x,");
                l_stringBuilder.append("y,");
                l_stringBuilder.append(l_continents.getName());
                for (int neighborId : l_country.getNeighbors()) {
                    String l_neighborCountry = getCountryByID(neighborId);
                    if (l_neighborCountry != null) {
                        l_stringBuilder.append(",").append(l_neighborCountry);
                    }
                }
                l_stringBuilder.append("\n");
            }
            l_stringBuilder.append("\n");
        }
        l_bufferedWriter.append(l_stringBuilder);
        l_bufferedWriter.flush();
        l_bufferedWriter.close();
    }

    /**
     * Retrieves the country name associated with the given country ID.
     *
     * @param p_countryId The ID of the country to retrieve.
     * @return The name of the country, or null if not found.
     */
    private String getCountryByID(int p_countryId) {
        for (Continent l_continent : d_map.getContinents().values()) {
            for (Country l_country : l_continent.getCountries().values()) {
                if (l_country.getCountryId()==p_countryId) {
                    return l_country.getCountryName();
                }
            }
        }
        return null;
    }

    /**
     * Retrieves the continent ID associated with the given continent name.
     *
     * @param p_continentName The name of the continent to retrieve.
     * @return The ID of the continent, or -1 if not found.
     */
    private int getContinentIdByName(String p_continentName){
        for(Continent l_continent: d_map.getContinents().values()){
            if(l_continent.getName().equals(p_continentName))
                return l_continent.getId();
        }
        return -1;
    }

    /**
     * Retrieves the country ID associated with the given country name.
     *
     * @param p_countryName The name of the country to retrieve.
     * @return The ID of the country, or -1 if not found.
     */
    private int getCountryIdByName(String p_countryName) {
        for (Continent l_continent : d_map.getContinents().values()) {
            for (Country l_country : l_continent.getCountries().values()) {
                if (l_country.getCountryName().equals(p_countryName)) {
                    return l_country.getCountryId();
                }
            }
        }
        return -1;
    }

    /**
     * Validates the current map using the internal Map instance.
     *
     * @return True if the map is valid, false otherwise.
     */
    public boolean validateMap(){
        return d_map.validateMap();
    }

    /**
     * Visualizes the graph representation of the map using the internal Map instance.
     */
    public void visualizeGraph(){
        d_map.visualizeGraph();
    }
}

