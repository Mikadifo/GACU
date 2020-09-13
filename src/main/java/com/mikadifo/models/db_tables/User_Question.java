package com.mikadifo.models.db_tables;

import java.time.LocalTime;

public class User_Question {

    private int id;
    private int userId;
    private int questionId;
    private int answerId;
    private LocalTime startedAt;
    private LocalTime finishedAt;
    private String userAnswer;
    private boolean correct;

    public User_Question() { }

    public User_Question(int id, int userId, int questionId, int answerId, LocalTime startedAt, LocalTime finishedAt, String userAnswer, boolean correct) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.answerId = answerId;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.userAnswer = userAnswer;
        this.correct = correct;
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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public LocalTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

}

