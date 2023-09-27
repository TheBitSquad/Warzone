package org.bitsquad.warzone.country;
import java.util.ArrayList;

/**
 * Represents a country object.
 * 
 * This class defines a country with its ID, list of neighbor countries, id of the continent it belongs to,no of armies country owns, 
 * and the owner of the country and other relevant details according to system design.
 * This class also defines addNeighbor and removeNeighbor methods which add and remove the neighbor Country Id in the neighbors list.
 * @version 1.0
 * @author nisargadalja 
 */
public class Country {

	/**
	 * The id of the country.
	 */
	private int d_id;
	
	/**
	 * The id of the continent to which the country belongs. 
	 */
	private int d_continentId;
	
	/**
	 * noOfArmies stores the number of Armies allocated to the country by the owner. 
	 */
	private int d_noOfArmies;
	
	/**
	 * The player id who owns the country.
	 */
	private int d_ownedByPlayerId;
	

	/**
	 * d_neighbors stores the list of neighbor countries' id. 
	 */
	private ArrayList<Integer> d_neighbors;
	
	
	
	/**
	 *  getter method of Country Id.
	 * @return the id of the country
	 */
	public int getId() {
		return d_id;
	}
	
	/**
	 * setId method will set the id of the Country.
	 * @param p_id the id of country.
	 */
	public void setId(int p_id) {
		if (p_id!=0) {
			this.d_id = p_id;
		}
	}
	
	/**
	 * Getter method of continent Id.
	 * @return the continent id.
	 */
	public int getContinentId() {
		return d_continentId;
	}
	
	/**
	 * Setter method of continentId which set the continent id. 
	 * @param p_continentId the continent id. 
	 */
	public void setContinentId(int p_continentId) {
		this.d_continentId = p_continentId;
	}
	
	/**
	 * Getter method of NoOfArmies which get number of Country's Armies. 
	 * @return the number of Armies allocated to the country by the owner.
	 */
	public int getNoOfArmies() {
		return d_noOfArmies;
	}
	
	/**
	 * setter method of noOfArmies.
	 * @param p_noOfArmies the number of Country's Armies.
	 */
	public void setNoOfArmies(int p_noOfArmies) {
		this.d_noOfArmies = p_noOfArmies;
	}
	
	/**
	 * This method use to get the player id who owns the country.  
	 * @return the player id.
	 */
	public int getOwnedByPlayerId() {
		return d_ownedByPlayerId;
	}
	
	/**
	 * This method use to set the Player Id who owns the country.
	 * @param p_ownedByPlayerId the Owned player id.
	 */
	public void setOwnedByPlayerId(int p_ownedByPlayerId) {
		this.d_ownedByPlayerId = p_ownedByPlayerId;
	}
	
	/**
	 * This method use to get the list of neighbor Countries.
	 * @return the ArrayList of neighbor Countries
	 */
	public ArrayList<Integer> getNeighbors() {
		return d_neighbors;
	}
	
	/**
	 * This method use to set the list of neighbor countries.
	 * @param p_neighbors the integer ArrayList of neighbor countries's Id.
	 */
	public void setNeighbors(ArrayList<Integer> p_neighbors) {
		this.d_neighbors = p_neighbors;
	} 
	
	/**
	 *  addNeighbor method add the id of the neighbor country in the neighbors list. 
	 * @param p_neighborCountryId the id of the neighbor.
	 */
	public boolean addNeighbor(int p_neighborCountryId) {
		if (d_neighbors.contains(p_neighborCountryId) == false) {
			d_neighbors.add(p_neighborCountryId);
			return true;
		}
		else {
			System.out.println("The Neighbor Country is already is added");
			return false;
		}
		
	}
	
	/**
	 * removeNeighbor method remove the id of the neighbor country from the neighbors list.
	 * @param p_neighborCountryId the id of the neighbor country.
	 */
	public boolean removeNeighbor(int p_neighborCountryId) {
		if(p_neighborCountryId>=1 && d_neighbors.contains(p_neighborCountryId)) {
			d_neighbors.remove(Integer.valueOf(p_neighborCountryId));
			return true;
		}
		return false;
	}
	
	
}
	