package com.mikadifo.models.db_tables;

public class Country {

    public final String TABLE = "\"Countries\"";
    public final String COLUMNS = "country_name";
    public final String ATTRIBUTES = "?";

    private int id;
    private String name;

    public Country() { }

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
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

}
