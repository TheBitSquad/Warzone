package org.bitsquad.warzone.map;

import org.bitsquad.warzone.continent.Continent;

import java.util.HashMap;

public class Adapter extends Map{
    ConquestMap d_conquestMap;

    public Adapter(ConquestMap p_conquestMap){
        this.d_conquestMap = p_conquestMap;
    }

    public HashMap<Integer, Continent> getContinents() {
        return d_conquestMap.getContinents();
    }

    public void addContinent(int p_continentId, int p_bonusValue){
        this.d_conquestMap.addContinent(p_continentId,p_bonusValue);
    }

    public boolean removeContinent(int p_continentId){
       return this.d_conquestMap.removeContinent(p_continentId);
    }

    public boolean addCountry(int p_countryId, int p_continentId){
        return d_conquestMap.addCountry(p_countryId,p_continentId);
    }

    public void removeCountry(int p_countryId){
        d_conquestMap.removeCountry(p_countryId);
    }

    public boolean addNeighbor(int p_sourceCountryId, int p_destinationCountryId){
        return d_conquestMap.addNeighbor(p_sourceCountryId,p_destinationCountryId);
    }

    public void removeNeighbor(int p_sourceCountryId, int p_destinationCountryId){
        d_conquestMap.removeNeighbor(p_sourceCountryId,p_destinationCountryId);
    }
    public boolean loadMap(String p_fileName){
        return d_conquestMap.loadMap(p_fileName);
    }

    public void saveMap(String p_fileName) throws Exception {
        d_conquestMap.saveMap(p_fileName);
    }
    public void editMap(String p_fileName) {
        d_conquestMap.editMap(p_fileName);
    }

    public void visualizeGraph(){
        d_conquestMap.visualizeGraph();
    }

    public boolean validateMap(){
        return d_conquestMap.validateMap();
    }
}
