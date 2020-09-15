package com.mikadifo.models.db_tables;

import java.awt.Image;

public class Images {

    public final String TABLE = "\"Images\"";
    public final String COLUMNS = "place_id, image, image_author, image_description";
    public final String ATTRIBUTES = "?, ?, ?, ?";

    private int id;
    private Image image;
    private String author;
    private String description;
    private int placeId;

    public Images() {
    }

    public Images(int id, Image image, String author, String description, int placeId) {
        this.id = id;
        this.image = image;
        this.author = author;
        this.description = description;
        this.placeId = placeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

}
