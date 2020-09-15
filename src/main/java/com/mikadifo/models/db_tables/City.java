package com.mikadifo.models.db_tables;

public class City {

    public final String TABLE = "\"Cities\"";
    public final String COLUMNS = "city_name, country_id";
    public final String ATTRIBUTES = "?, ?";

    private int id;
    private String name;
    private int countryId;

    public City() {
    }

    public City(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

}
