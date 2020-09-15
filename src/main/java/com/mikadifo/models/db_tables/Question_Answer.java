package com.mikadifo.models.db_tables;

public class Question_Answer {

    public final String TABLE = "\"Question_Answers\"";
    public final String COLUMNS = "question_id, answer_id";
    public final String ATTRIBUTES = "?, ?";

    private int id;
    private int questionId;
    private int answerId;

    public Question_Answer() {
    }

    public Question_Answer(int id, int questionId, int answerId) {
        this.id = id;
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
