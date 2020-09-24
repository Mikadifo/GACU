package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.Question;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

public class QuestionDB extends Question implements SQL_Statement {
    
    private DB_Connection dbConnection = new DB_Connection();

    public QuestionDB() { }

    public QuestionDB(int id, String content, java.util.Date createdDate,
            short difficulty, int placeId, int typeId) {
        super(id, content, createdDate, difficulty, placeId, typeId);
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
    
    public List<QuestionDB> getResults() {
        List<QuestionDB> questions = new ArrayList<>();
        ResultSet results;
        
        try {
            results = dbConnection.executeQuery();
            
            while(results.next()) {
                questions.add(getQuestion(results));
            }
            
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {            
            return null;
        }
        
        return questions;
    }
    
    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "question_id");
            
            setIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");
            
            return false;
        }
        
        return true;
    }
    
    public QuestionDB getQuestion() {
        return getResults().get(0);
    }
    
    private QuestionDB getQuestion(ResultSet resultSet) {        
        try {
            return new QuestionDB (
                resultSet.getInt("question_id"),
                resultSet.getString("question_content"),
                resultSet.getDate("created_date"),
                resultSet.getShort("difficulty"),
                resultSet.getInt("place_id"),
                resultSet.getInt("type_id")
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
            System.err.print("ERROR INSERTING QUESTION");
            
            return false;
        }

        return true;
    }

    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "question_id");

            setValues();
            setIdColumnValue(6);
            
            dbConnection.executeAndClose();
            
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }
        
        return false;
    }
    
    private String build_UPDATE_SET() {
        return "place_id = ?, " +
               "question_content = ?, " +
               "created_date = ?, " +
               "difficulty = ?, " +
               "type_id = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(1, getPlaceId());
        statement.setString(2, getContent());
        statement.setDate(3, Date.valueOf(getCreatedDate().toString()));
        statement.setShort(4, getDifficulty());
        statement.setInt(5, getTypeId());
        
        
        dbConnection.setStatement(statement);
    }

    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "question_id");
            
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
