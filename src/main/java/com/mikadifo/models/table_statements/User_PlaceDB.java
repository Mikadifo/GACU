package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.User_Place;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class User_PlaceDB extends User_Place implements SQL_Statement{
    
    private DB_Connection dbConnection = new DB_Connection();

    public User_PlaceDB() {
    }

    public User_PlaceDB(int id, int userId, int placeId) {
        super(id, userId, placeId);
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
    
    public List<User_Place> getResults() {
        List<User_Place> user_places = new ArrayList<>();
        ResultSet results;
        
        try {
            results = dbConnection.executeQuery();
            
            while(results.next()) {
                user_places.add(getUser_Place(results));
            }
            
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {            
            return null;
        }
        
        return user_places;
    }
    
    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "user_places_id");
            
            setUser_PlaceIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");
            
            return false;
        }
        
        return true;
    }
    
    public User_Place getUser_Place() {
        return getResults().get(0);
    }
    
    private User_Place getUser_Place(ResultSet resultSet) {        
        try {
            return new User_Place (
                resultSet.getInt("user_places_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("place_id")
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
            System.err.print("ERROR INSERTING User_Place");
            
            return false;
        }

        return true;
    }
    
    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "user_places_id");

            setValues();
            setUser_PlaceIdColumnValue(3);
            
            dbConnection.executeAndClose();
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }
        
        return false;
    }
    
    private String build_UPDATE_SET() {
        return "user_id = ?, " +
               "place_id = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(1, getUserId());
        statement.setInt(2, getPlaceId());
        
        dbConnection.setStatement(statement);
    }
    
    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "user_places_id");
            
            setUser_PlaceIdColumnValue(1);
            
            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.println("ERROR DELETING");
            
            return false;
        }
        
        return true;
    }
    
    private void setUser_PlaceIdColumnValue(int index) throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(index, getId());
        
        dbConnection.setStatement(statement);
    }
    
}
