package org.bitsquad.warzone.continent;

/**
 * Represents a Continent Object
 * 
 * This class defines a continent with its ID, its constituent Countries and its bonus value.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Continent {
    //declaring variables for continentid
    private int continetId;
    private int value;
    private Map<Integer, String> countries;

    public Continent() {
        countries = new HashMap<>();
    }

    // Getter and Setter methods for 'id'
    public int getId() {
        return continetId;
    }

    public void setId(int continetId) {
        this.continetId = continetId;
    }

    // Getter and Setter methods for 'value'
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // Getter and Setter methods for 'countries'
    public Map<Integer, String> getCountries() {
        return countries;
    }

    public void setCountries(Map<Integer, String> countries) {
        this.countries = countries;
    }
// to check the existing country id 
    // Method to add a country
    public void addCountry(int countryId, String countryName) {
        countries.put(countryId, countryName);
    }
// country not exists
    // Method to remove a country by its code
    public void removeCountry(int countryId) {
        countries.remove(countryId);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Continent continent = new Continent();

        System.out.print("'continentId': ");
        int continentId = scanner.nextInt();
        continent.setId(continentId);

        System.out.print("'value': ");
        int value = scanner.nextInt();
        continent.setValue(value);

        // Add countries
        continent.addCountry(123, "India");
        continent.addCountry(145, "Canada");

        // Display current countries
        System.out.println(continent.getCountries());

        // Remove a country by code
        System.out.print("Enter a country Id to remove: ");
        int countryIdToRemove = scanner.nextInt();
        continent.removeCountry(countryIdToRemove);
        //updated list after removing
        System.out.println("Updated Countries: " + continent.getCountries());
        //add country by name and id
        System.out.print("Enter a country Id and country Name: ");
        int countryIdToAdd = scanner.nextInt();
        String countryNameToAdd = scanner.nextLine();
        continent.addCountry(countryIdToAdd, countryNameToAdd);
        //updated list
        System.out.println("Updated Countries: " + continent.getCountries());

        scanner.close();
    }
}

