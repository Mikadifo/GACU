package com.mikadifo.models.db_tables;

public class Type {

    public final String TABLE = "\"Types\"";
    public final String COLUMNS = "type_name, type_description";
    public final String ATTRIBUTES = "?, ?";

    private int id;
    private String name;
    private String description;

    public Type() {
    }

    public Type(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
