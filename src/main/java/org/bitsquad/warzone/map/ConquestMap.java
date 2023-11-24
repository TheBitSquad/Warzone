package org.bitsquad.warzone.map;

import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import org.bitsquad.warzone.continent.Continent;
import org.bitsquad.warzone.country.Country;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.jgrapht.Graph;
import org.jgrapht.GraphTests;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 *Adaptee
 *
 */
public class ConquestMap {
    ArrayList<String[]> d_conquestNeighbors;
    Map d_map;
    private HashMap<Integer, Continent> d_continents;
    private Graph<Country, DefaultEdge> d_graph;

    public ConquestMap(){
        d_conquestNeighbors = new ArrayList<>();
        d_map = new Map();
        d_continents = new HashMap<Integer, Continent>();
        d_graph = new SimpleGraph<>(DefaultEdge.class);
    }

    public HashMap<Integer, Continent> getContinents(){
        return d_map.getContinents();
    }

    public void addContinent(int p_continentId, String p_continentName, int p_bonusValue) {
        d_map.addContinent(p_continentId, p_continentName, p_bonusValue);
    }

    public void addTerritory(int p_territoryId, String p_territoryName, int p_continentId){
        d_map.addCountry(p_territoryId, p_territoryName, p_continentId);
    }
    public boolean addNeighbor(int p_sourceTerritoryId, int p_destinationTerritoryId){
        return d_map.addNeighbor(p_sourceTerritoryId,p_destinationTerritoryId);
    }
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

    private void processNeighborsData(ArrayList<String[]> conquestNeighbors){
        for(String[] conquestNeighbor : conquestNeighbors){
            int sourceCountryId = getCountryIdByName(conquestNeighbor[0]);
            String[] neighbors = Arrays.copyOfRange(conquestNeighbor, 4, conquestNeighbor.length);
            for(String neighbor: neighbors){
                int targetCountryId = getCountryIdByName(neighbor);
                addNeighbor(sourceCountryId,targetCountryId);
            }
        }
    }


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

    private int getContinentIdByName(String p_continentName){
        for(Continent l_continent: d_map.getContinents().values()){
            if(l_continent.getName().equals(p_continentName))
                return l_continent.getId();
        }
        return -1;
    }

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

    private boolean validateMap(){
        return d_map.validateMap();
    }

    public void visualizeGraph(){
        d_map.visualizeGraph();
    }
}

