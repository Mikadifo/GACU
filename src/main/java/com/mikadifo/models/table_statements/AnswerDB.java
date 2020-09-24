package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.Answer;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

public class AnswerDB extends Answer implements SQL_Statement {
    
    private DB_Connection dbConnection = new DB_Connection();

    public AnswerDB() { }

    public AnswerDB(int id, String content, boolean correct,
            Date createdDate, int placeId, int typeId) {
        super(id, content, correct, createdDate, placeId, typeId);
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
    
    public List<AnswerDB> getResults() {
        List<AnswerDB> answers = new ArrayList<>();
        ResultSet results;
        
        try {
            results = dbConnection.executeQuery();
            
            while(results.next()) {
                answers.add(getAnswer(results));
            }
            
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {            
            return null;
        }
        
        return answers;
    }
    
    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "answer_id");
            
            setIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");
            
            return false;
        }
        
        return true;
    }
    
    public AnswerDB getAnswer() {
        return getResults().get(0);
    }
    
    private AnswerDB getAnswer(ResultSet resultSet) {        
        try {
            return new AnswerDB (
                resultSet.getInt("answer_id"),
                resultSet.getString("answer_content"),
                resultSet.getBoolean("answer_is_correct"),
                Date.valueOf(resultSet.getString("created_date")),
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
            System.err.print("ERROR INSERTING ANSWER");
            
            return false;
        }

        return true;
    }

    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "answer_id");

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
               "answer_content = ?, " +
               "answer_is_correct = ?, " +
               "created_date = ?, " +
               "type_id = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(1, getPlaceId());
        statement.setString(2, getContent());
        statement.setBoolean(3, isCorrect());
        statement.setDate(4, Date.valueOf(getCreatedDate().toString()));
        statement.setInt(5, getTypeId());
        
        
        dbConnection.setStatement(statement);
    }

    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "answer_id");
            
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
