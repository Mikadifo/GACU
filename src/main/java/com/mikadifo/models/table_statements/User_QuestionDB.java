package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.User_Question;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class User_QuestionDB extends User_Question implements SQL_Statement{
    
    private DB_Connection dbConnection = new DB_Connection();

    public User_QuestionDB() {
    }

    public User_QuestionDB(int id, int userId, int questionId, int answerId, LocalTime startedAt, LocalTime finishedAt, String userAnswer, boolean correct) {
        super(id, userId, questionId, answerId, startedAt, finishedAt, userAnswer, correct);
    }
    
    @Override
    public boolean selectAll() {        
        try {
            dbConnection.buildAndPrepareSelect(TABLE);
        } catch (SQLException ex) {
            System.err.println("ERROR SELECT ALL");
            
            return false;
        }
        
        return true;
    }
    
    public List<User_QuestionDB> getResults() {
        List<User_QuestionDB> users_questions = new ArrayList<>();
        ResultSet results;
        
        try {
            results = dbConnection.executeQuery();
            
            while(results.next()) {
                users_questions.add(getUser_Question(results));
            }
            
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {            
            return null;
        }
        
        return users_questions;
    }
    
    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "user_questions_id");
            
            setUser_QuestionIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");
            
            return false;
        }
        
        return true;
    }
    
    public User_QuestionDB getUser_Question() {
        return getResults().get(0);
    }
    
    private User_QuestionDB getUser_Question(ResultSet resultSet) {        
        try {
            return new User_QuestionDB (
                resultSet.getInt("user_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("question_id"),
                resultSet.getInt("answer_id"),
                resultSet.getTime("started_at").toLocalTime(),
                resultSet.getTime("finished_at").toLocalTime(),
                resultSet.getString("user_answer"),
                resultSet.getBoolean("is_correct")
            );
        } catch (SQLException ex) {
            System.err.println("Error");
            
            return null;
        }
    }
    
    @Override
    public boolean insert() {
        try {
            dbConnection.buildAndPrepareInsert(TABLE, COLUMNS, ATTRIBUTES);
            
            setValues();
            
            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.print("ERROR INSERTING USER_QUESTION");
            
            return false;
        }

        return true;
    }
    
    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "user_questions_id");

            setValues();
            setUser_QuestionIdColumnValue(8);
            
            dbConnection.executeAndClose();
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }
        
        return false;
    }
    
    private String build_UPDATE_SET() {
        return "user_id = ?, " +
               "question_id = ?, " +
               "answer_id = ?, " +
               "started_at = ?, " +
               "finished_at = ?, "+
               "user_answer = ?, " +
                "is_correct = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(1, getUserId());
        statement.setInt(2, getQuestionId());
        statement.setInt(3, getAnswerId());
        statement.setTime(4, Time.valueOf(getStartedAt()));
        statement.setTime(5, Time.valueOf(getFinishedAt()));
        statement.setString(6, getUserAnswer());
        statement.setBoolean(7, isCorrect());
        
        dbConnection.setStatement(statement);
    }
    
    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "user_questions_id");
            
            setUser_QuestionIdColumnValue(1);
            
            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.println("ERROR DELETING");
            
            return false;
        }
        
        return true;
    }
    
    private void setUser_QuestionIdColumnValue(int index) throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(index, getId());
        
        dbConnection.setStatement(statement);
    }
    
}
