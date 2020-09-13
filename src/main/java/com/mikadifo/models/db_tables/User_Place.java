package com.mikadifo.models.db_tables;

public class User_Place {

    private int id;
    private int userId;
    private int placeId;

    public User_Place() { }

    public User_Place(int id, int userId, int placeId) {
        this.id = id;
        this.userId = userId;
        this.placeId = placeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

}

