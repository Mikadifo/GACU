package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.Question_Answer;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class Question_AnswerDB extends Question_Answer implements SQL_Statement {
    
    private DB_Connection dbConnection = new DB_Connection();

    public Question_AnswerDB() { }

    public Question_AnswerDB(int id, int questionId, int answerId) {
        super(id, questionId, answerId);
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
    
    public List<Question_Answer> getResults() {
        List<Question_Answer> question_answers = new ArrayList<>();
        ResultSet results;
        
        try {
            results = dbConnection.executeQuery();
            
            while(results.next()) {
                question_answers.add(getQuestion_Answer(results));
            }
            
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {            
            return null;
        }
        
        return question_answers;
    }
    
    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "question_answer_id");
            
            setIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");
            
            return false;
        }
        
        return true;
    }
    
    public Question_Answer getQuestion_Answer() {
        return getResults().get(0);
    }
    
    private Question_Answer getQuestion_Answer(ResultSet resultSet) {        
        try {
            return new Question_Answer (
                resultSet.getInt("question_answer_id"),
                resultSet.getInt("question_id"),
                resultSet.getInt("answer_id")
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
            System.err.print("ERROR INSERTING QUESTION_ANSWER");
            
            return false;
        }

        return true;
    }
    
    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "question_answer_id");

            setValues();
            setIdColumnValue(3);
            
            dbConnection.executeAndClose();
            
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }
        
        return false;
    }
    
    private String build_UPDATE_SET() {
        return "question_id = ?, " +
               "answer_id = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(1, getId());
        statement.setInt(2, getQuestionId());
        statement.setInt(3, getAnswerId());
        
        dbConnection.setStatement(statement);
    }
    
    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "question_answer_id");
            
            setIdColumnValue(1);
            
            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.println("ERROR DELETING");
            
            return false;
        }
        
        return true;
    }
    
    private void setIdColumnValue(int index) throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(index, getId());
        
        dbConnection.setStatement(statement);
    }
    
}
