package com.mikadifo.models.db_tables;

public class Role {
    public final String TABLE = "\"Roles\"";
    public final String COLUMNS = "role_name";
    public final String ATTRIBUTES = "?";
    
    private int id;
    private String name;

    public Role() { }

    public Role(int id, String name) {
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

