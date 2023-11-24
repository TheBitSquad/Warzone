package org.bitsquad.warzone.map;

public class Adapter extends Map{
    public static final String mapType ="domination";

    ConquestMap d_conquestMap = new ConquestMap();

    /**
     *
     */
    Adapter(ConquestMap p_conquestMap){
        this.d_conquestMap = p_conquestMap;
    }

    public boolean loadMap(String p_fileName){
        return d_conquestMap.loadMap(p_fileName);
    }

    public void saveMap(String p_fileName) throws Exception {
        d_conquestMap.saveMap(p_fileName);
    }
}
