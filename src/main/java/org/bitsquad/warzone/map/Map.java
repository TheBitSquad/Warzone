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
import java.util.*;

/**
 * Represents a game map.
 * This class defines a map with a list of continents and its Directed Graph as well all related functionality.
 */
public class Map {

    private HashMap<Integer, Continent> d_continents;
    private Graph<Country, DefaultEdge> d_graph;

    /**
     * Constructor for Map class
     */
    public Map(){
        d_continents = new HashMap<Integer, Continent>();
        d_graph = new SimpleGraph<>(DefaultEdge.class);
    }

    /**
     * Copy Constructor for Map
     */
    public Map(Map p_original){
        this.d_continents = new HashMap<>(p_original.getContinents());
        this.d_graph = new SimpleGraph<>(DefaultEdge.class);
    }

    /**
     * Getter method for continents
     * @return d_continents the continents in a map
     */
    public HashMap<Integer, Continent> getContinents() {
        return d_continents;
    }

    /**
     * Used to get a particular continent by ID
     * @param p_continentId Continent id
     * @return Continent object
     */
    public Continent getContinent(int p_continentId){
        return d_continents.get(p_continentId);
    }

    /**
     * Adds a continent to the map
     * @param p_continentId continentId
     * @param p_bonusValue bonus value of the continent
     */
    public void addContinent(int p_continentId, int p_bonusValue){
        this.addContinent(p_continentId, "Continent_" + p_continentId, p_bonusValue);
    }

    public void addContinent(int p_continentId,String p_continentName, int p_bonusValue){
        d_continents.putIfAbsent(p_continentId, new Continent(p_continentId,p_continentName, p_bonusValue));
    }

    /**
     * Removes a continent from the map
     * @param p_continentId ID of the continent to be removed
     * @return boolean true if an existing continent is being removed, else false
     */
    public boolean removeContinent(int p_continentId){
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
    public boolean addCountry(int p_countryId, int p_continentId){
        return this.addCountry(p_countryId, "Country_"+p_countryId, p_continentId);
    }

    /**
     * Adds a country to the map
     * @param p_countryId Country ID
     * @param p_countryName Country Name
     * @param p_continentId ContinentID
     * @return boolean
     */
    public boolean addCountry(int p_countryId, String p_countryName, int p_continentId){
        // Check if the country is present in any continent
        for(Continent l_continent: d_continents.values()){
            if(l_continent.getCountries().containsKey(p_countryId)){
                return false;
            }
        }
        // Check if the continent exists
        if(d_continents.containsKey(p_continentId)){
            Continent l_continent = d_continents.get(p_continentId);
            l_continent.addCountry(p_countryId, p_countryName);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a country from the map
     * @param p_countryId Country Id
     */
    public void removeCountry(int p_countryId){
        Country l_countryToRemove = null;
        for(Continent l_continent: d_continents.values()){
            if(l_continent.getCountries().containsKey(p_countryId)){
                l_countryToRemove = l_continent.getCountries().get(p_countryId);
                // Remove country from continent
                l_continent.removeCountry(p_countryId);
                break;
            }
        }
        if(l_countryToRemove == null) return;
        // Remove country as neighbor from other countries
        for(Continent l_continent: d_continents.values()){
            for(Country l_country: l_continent.getCountries().values()){
                l_country.removeNeighbor(l_countryToRemove.getCountryId());
            }
        }
    }

    /**
     * Adds a neighbor to a country
     * @param p_sourceCountryId sourceCountryId
     * @param p_destinationCountryId destinationCountryId
     * @return boolean if both such countries exist, then true, else false
     */
    public boolean addNeighbor(int p_sourceCountryId, int p_destinationCountryId){
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
    public void removeNeighbor(int p_sourceCountryId, int p_destinationCountryId){
        for(Continent l_continent: d_continents.values()){
            if(l_continent.getCountries().containsKey(p_sourceCountryId)){
                Country l_sourceCountry = l_continent.getCountries().get(p_sourceCountryId);
                l_sourceCountry.removeNeighbor(p_destinationCountryId);
            }
            if (l_continent.getCountries().containsKey(p_destinationCountryId)){
                Country l_destinationCountry = l_continent.getCountries().get(p_destinationCountryId);
                l_destinationCountry.removeNeighbor(p_sourceCountryId);
            }
        }
    }

    /**
     * Load the continent details from the text file to Map
     * @param p_bufferedReader BufferedReader object to read the text file
     */
    private void loadContinents(BufferedReader p_bufferedReader) {
        int l_continentId=1;
        try{
            String l_lines = p_bufferedReader.readLine();
            while (!(l_lines == null) && !(l_lines.isEmpty())) {
                String[] l_data = l_lines.split(" ");
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
     * Load the country details from the text file to Map
     * @param p_bufferedReader BufferedReader object to read the text file
     */
    private void loadCountries(BufferedReader p_bufferedReader) {
        try{
            String l_lines = p_bufferedReader.readLine();
            while (!(l_lines == null) && !(l_lines.isEmpty())) {
                String[] l_data = l_lines.split(" ");
                this.addCountry(Integer.parseInt(l_data[0]), l_data[1], Integer.parseInt(l_data[2]));
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
    private void loadNeighbors(BufferedReader p_bufferedReader) {
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
     * Load contents of the .map text file to Map
     *
     * @param p_fileName Map file name
     * @return true if map loaded successfully
     */
    public boolean loadMap(String p_fileName) {
        File l_file_name = new File(p_fileName);
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
                        case "[neighbors]":
                        case "[borders]": loadNeighbors(l_bufferedReader);
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
     * Write the Map data to a .map text file
     * @param p_fileName FileName
     */
    public void saveMap(String p_fileName) throws Exception {
        if(!validateMap()){
            throw new Exception("Invalid Map, cannot save");
        }

        StringBuilder l_stringBuilder = new StringBuilder("\n[neighbors]\n");
        BufferedWriter l_bufferedWriter = new BufferedWriter(new FileWriter(p_fileName + ".map"));
        //save continents data
        l_bufferedWriter.write("[continents]\n");
        for(Continent l_continents : d_continents.values()){
            String l_continentName= l_continents.getName();
            if(l_continentName==null)
                l_continentName="Continent_"+l_continents.getId();
            l_bufferedWriter.write(l_continentName + " " + l_continents.getValue() + "\n");
        }
        //save countries data and build neighbors list
        l_bufferedWriter.write("\n[countries]\n");
        for(Continent l_continents : d_continents.values()) {
            for(int l_countryId: l_continents.getCountries().keySet()){
                Country l_country = l_continents.getCountries().get(l_countryId);
                String l_countryName= l_country.getCountryName();
                if(l_countryName.equals("Country") || l_countryName.isEmpty() || l_countryName == null)
                    l_countryName="Country_"+l_countryId;
                l_bufferedWriter.write(l_countryId + " " + l_countryName + " " + l_continents.getId() + "\n");
                //building neighbors list
                l_stringBuilder.append(l_countryId);
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

    /**
     * Loads the contents of .map file to edit
     * If file doesn't exist, create a new .map file
     * @param p_fileName Map file name
     */
    public void editMap(String p_fileName) {
        File l_fileName = new File(p_fileName);
        if(l_fileName.exists())
            loadMap(p_fileName);
        else{
            try{
                BufferedWriter l_bufferedWriter = new BufferedWriter(new FileWriter(p_fileName));
                l_bufferedWriter.write("[continents]\n\n");
                l_bufferedWriter.write("[countries]\n\n");
                l_bufferedWriter.write("[neighbors]\n");
                l_bufferedWriter.flush();
                l_bufferedWriter.close();
            }
            catch(IOException e){
                LogEntryBuffer.getInstance().log("Error creating new file" + e.getMessage());
            }
        }
    }

    /**
     * Populates the JgraphtT.graph data structure
     */
    private void populateJGraph(){
        d_graph = new SimpleGraph<>(DefaultEdge.class);
        HashMap<Integer, Country> l_allCountries = new HashMap<>();

        // Add the vertices i.e. Countries
        for(Continent l_continent: d_continents.values()){
            HashMap<Integer, Country> l_countries = l_continent.getCountries();
            l_allCountries.putAll(l_countries);
            for(Country l_country: l_countries.values()){
                d_graph.addVertex(l_country);
            }
        }

        // Add the edges
        for(Country l_country: l_allCountries.values()){
            for(int l_neighborId: l_country.getNeighbors()){
                d_graph.addEdge(l_country, l_allCountries.get(l_neighborId));
            }
        }
    }

    /**
     * Checks if a continent is a subgraph
     * @param p_continentId ContinentId
     * @return boolean true if the continent is a subgraph
     */
    public boolean isContinentSubgraph(int p_continentId){
        populateJGraph();
        // Just checking if the subgraph of all Countries in a continent is a connected graph
        Graph<Country, DefaultEdge> l_subgraph = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);

        Collection<Country> l_subgraphVertices =
                d_continents.get(p_continentId).getCountries().values();

        for(Country l_vertex: l_subgraphVertices){
            l_subgraph.addVertex(l_vertex);
        }

        // Get all edges between those vertices
        for(DefaultEdge l_edge: d_graph.edgeSet()){
            Country l_source = d_graph.getEdgeSource(l_edge);
            Country l_target = d_graph.getEdgeTarget(l_edge);

            if(l_subgraphVertices.contains(l_source) && l_subgraphVertices.contains(l_target)){
                l_subgraph.addEdge(l_source, l_target);
            }
        }
        return GraphTests.isConnected(l_subgraph);
    }

    /**
     * Checks if the graph is valid or not
     * @return boolean true if the graph is valid
     */
    public boolean validateMap(){
        populateJGraph();
        if(GraphTests.isConnected(d_graph)){
            for(int l_continentId: d_continents.keySet()){
                if(!isContinentSubgraph(l_continentId))
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Used to visualise the game map
     */
    public void visualizeGraph(){
        populateJGraph();
        JFrame l_frame = new JFrame("Game Map");
        l_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        l_frame.setSize(800, 600);
        l_frame.getContentPane().setLayout(new BorderLayout());

        JGraphXAdapter<Country, DefaultEdge> l_graphAdapter =
                new JGraphXAdapter<>(d_graph);

        mxGraphComponent l_graphComponent = new mxGraphComponent(l_graphAdapter);
        mxGraphModel l_graphModel = (mxGraphModel) l_graphComponent.getGraph().getModel();
        Collection<Object> l_cells = l_graphModel.getCells().values();
        // Remove arrow from edge
        mxUtils.setCellStyles(l_graphComponent.getGraph().getModel(),
                l_cells.toArray(), mxConstants.STYLE_ENDARROW, mxConstants.NONE);

        l_graphAdapter.setCellsEditable(false);
        l_graphAdapter.setConnectableEdges(false);
        l_graphAdapter.setAllowDanglingEdges(false);
        l_graphAdapter.setCellsBendable(false);
        l_graphAdapter.setCellsMovable(false);
        l_graphAdapter.setCellsLocked(true);
        // Setting edge labels to null
        l_graphAdapter.getEdgeToCellMap().forEach((edge, cell) -> cell.setValue(null));

        mxOrganicLayout l_layout = new mxOrganicLayout(l_graphAdapter);
        l_layout.setOptimizeEdgeLength(false);
        l_layout.setOptimizeNodeDistribution(true);

        l_layout.execute(l_graphAdapter.getDefaultParent());

        l_frame.getContentPane().add(l_graphComponent,BorderLayout.CENTER);
        l_frame.setLocationRelativeTo(null);
        l_frame.setVisible(true);
        l_frame.setEnabled(true);
    }
}

