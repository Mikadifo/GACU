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

}

