package com.mikadifo.models.db_tables;

import java.util.Date;

public class Answer {

    public final String TABLE = "\"Answers\"";
    public final String COLUMNS = "place_id, answer_content, answer_is_correct, created_date, type_id";
    public final String ATTRIBUTES = "?, ?, ?, ?, ?";

    private int id;
    private String content;
    private boolean correct;
    private Date createdDate;
    private int placeId;
    private int typeId;

    public Answer() {
    }

    public Answer(int id, String content, boolean correct, Date createdDate, int placeId, int typeId) {
        this.id = id;
        this.content = content;
        this.correct = correct;
        this.createdDate = createdDate;
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

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
