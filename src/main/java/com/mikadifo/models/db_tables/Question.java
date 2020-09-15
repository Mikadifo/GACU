package com.mikadifo.models.db_tables;

import java.util.Date;

public class Question {

    public final String TABLE = "\"Questions\"";
    public final String COLUMNS = "place_id, question_content, question_content, difficulty, type_id";
    public final String ATTRIBUTES = "?, ?, ?, ?, ?";

    private int id;
    private String content;
    private Date createdDate;
    private short difficulty;
    private int placeId;
    private int typeId;

    public Question() {
    }

    public Question(int id, String content, Date createdDate, short difficulty, int placeId, int typeId) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.difficulty = difficulty;
        this.placeId = placeId;
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public short getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(short difficulty) {
        this.difficulty = difficulty;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

}
