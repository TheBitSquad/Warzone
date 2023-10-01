package org.bitsquad.warzone.map;
import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.player.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents a game map.
 * This class defines a map with a list of continents and its Directed Graph as well all related functionality.
 */
public class Map {

    private HashMap<Integer, Continent> d_continents;

    /**
     * Default Constructor
     */
    Map(){
        d_continents = new HashMap<Integer, Continent>();
    }

    /**
     * Getter method for continents
     * @return d_continents the continents in a map
     */
    public HashMap<Integer, Continent> getContinents() {
        return d_continents;
    }

    public Continent getContinent(int p_continentId){
        return d_continents.get(p_continentId);
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
     * @param p_countryId Country Id
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
     * @return boolean if both such countries exist, then true, else false
     */
    boolean addNeighbor(int p_sourceCountryId, int p_destinationCountryId){
        Country l_sourceCountry = null, l_destinationCountry = null;

        for(Continent l_continent: d_continents.values()){
            if(l_continent.getCountries().containsKey(p_sourceCountryId)){
                l_sourceCountry = l_continent.getCountries().get(p_sourceCountryId);
            }
            if (l_continent.getCountries().containsKey(p_destinationCountryId)){
                l_destinationCountry = l_continent.getCountries().get(p_destinationCountryId);
            }
        }

        // Add the edge, only if both countries exist
        if(l_sourceCountry != null && l_destinationCountry != null){
            l_sourceCountry.addNeighbor(p_destinationCountryId);
            l_destinationCountry.addNeighbor(p_sourceCountryId);
            return true;
        } else {
            return false;
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

    /**
     * Load the continent details from the text file to Map
     * @param p_bufferedReader BufferedReader object to read the text file
     */
    void loadContinents(BufferedReader p_bufferedReader) {
        try{
            String l_lines = p_bufferedReader.readLine();
            while (!(l_lines == null) && !(l_lines.isEmpty())) {
                String[] l_data = l_lines.split(" ");
                addContinent(Integer.parseInt(l_data[0]),Integer.parseInt(l_data[1]));
                l_lines = p_bufferedReader.readLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Load the country details from the text file to Map
     * @param p_bufferedReader BufferedReader object to read the text file
     */
    void loadCountries(BufferedReader p_bufferedReader) {
        try{
            String l_lines = p_bufferedReader.readLine();
            while (!(l_lines == null) && !(l_lines.isEmpty())) {
                String[] l_data = l_lines.split(" ");
                addCountry(Integer.parseInt(l_data[0]),Integer.parseInt(l_data[1]));
                l_lines = p_bufferedReader.readLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Load the Neighbors details from the text file to Map
     * @param p_bufferedReader BufferedReader object to read the text file
     */
    void loadNeighbors(BufferedReader p_bufferedReader) {
        try{
            String l_lines = p_bufferedReader.readLine();
            while (!(l_lines == null) && !(l_lines.isEmpty())) {
                String[] l_data = l_lines.split(" ");
                int l_sourceCountry = Integer.parseInt(l_data[0]);
                for(int i = 1 ; i < l_data.length ; i++){
                    int l_destinationCountry = Integer.parseInt(l_data[i]);
                    addNeighbor(l_sourceCountry, l_destinationCountry);
                }
                l_lines = p_bufferedReader.readLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Load contents of the text file to Map
     *
     * @param p_fileName Map file name
     * @return true if map loaded successfully
     */
    boolean loadMap(String p_fileName) {
        File l_file_name = new File(p_fileName+".map");
        try{
            if(l_file_name.exists()){
                BufferedReader l_bufferedReader = new BufferedReader(new FileReader(l_file_name));
                String l_lines = null;
                while((l_lines = l_bufferedReader.readLine()) != null){
                    switch (l_lines) {
                        case "[continents]": loadContinents(l_bufferedReader);
                            break;
                        case "[countries]": loadCountries(l_bufferedReader);
                            break;
                        case "[neighbors]": loadNeighbors(l_bufferedReader);
                            break;
                    }
                }
                l_bufferedReader.close();
            }
            return true;
        }
        catch (IOException e){
            System.err.println("Unable to load the map," + e.getMessage());
        }
        return false;
    }

    /**
     * Write the Map data to a text file
     * @param p_fileName FileName
     */
    void saveMap(String p_fileName) {
        try{
            StringBuilder l_stringBuilder = new StringBuilder("\n[neighbors]\n");
            BufferedWriter l_bufferedWriter = new BufferedWriter(new FileWriter(p_fileName + ".map"));
            //save continents data
            l_bufferedWriter.write("[continents]\n");
            for(Continent l_continents : d_continents.values())
                l_bufferedWriter.write(l_continents.getId() + " " + l_continents.getValue() + "\n");
            //save countries data and build neighbors list
            l_bufferedWriter.write("\n[countries]\n");
            for(Continent l_continents : d_continents.values()) {
                for(int l_countryId: l_continents.getCountries().keySet()){
                    l_bufferedWriter.write(l_countryId + " " + l_continents.getId() + "\n");
                    //building neighbors list
                    l_stringBuilder.append(l_countryId);
                    Country l_country = l_continents.getCountries().get(l_countryId);
                    for (int neighborId : l_country.getNeighbors()) {
                        l_stringBuilder.append(" ").append(neighborId);
                    }
                    l_stringBuilder.append("\n");
                }
            }
            l_bufferedWriter.append(l_stringBuilder);
            l_bufferedWriter.flush();
            l_bufferedWriter.close();
        }
        catch (IOException e){
            System.err.println("Error saving the map" + e.getMessage());
        }
    }

    /**
     * Loads the contents of map file to edit
     * If file doesn't exist, create a new map file
     * @param p_fileName Map file name
     */
    void editMap(String p_fileName) {
        File l_fileName = new File(p_fileName+".map");
        if(l_fileName.exists())
            loadMap(p_fileName);
        else{
            try{
                BufferedWriter l_bufferedWriter = new BufferedWriter(new FileWriter(p_fileName + ".map"));
                l_bufferedWriter.write("[continents]\n\n");
                l_bufferedWriter.write("[countries]\n\n");
                l_bufferedWriter.write("[neighbors]\n");
                l_bufferedWriter.flush();
                l_bufferedWriter.close();
            }
            catch(IOException e){
                System.err.println("Error creating new file" + e.getMessage());
            }
        }
    }
}

