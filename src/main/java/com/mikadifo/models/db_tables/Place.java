package com.mikadifo.models.db_tables;

public class Place {

    private int id;
    private String name;
    private String info;
    private int categoryId;

    public Place() { }

    public Place(int id, String name, String info, int categoryId) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.categoryId = categoryId;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
