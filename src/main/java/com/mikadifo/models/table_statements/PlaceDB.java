package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.Place;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class PlaceDB extends Place implements SQL_Statement{
    
    private DB_Connection dbConnection = new DB_Connection();

    public PlaceDB() { }

    public PlaceDB(int id, String name, String info, int categoryId) {
        super(id, name, info, categoryId);
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
    
    public List<PlaceDB> getResults() {
        List<PlaceDB> places = new ArrayList<>();
        ResultSet results;
        
        try {
            results = dbConnection.executeQuery();
            
            while(results.next()) {
                places.add(getPlace(results));
            }
            
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {            
            return null;
        }
        
        return places;
    }
    
    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "place_id");
            
            setPlaceIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");
            
            return false;
        }
        
        return true;
    }
    
    public PlaceDB getPlace() {
        return getResults().get(0);
    }
    
    private PlaceDB getPlace(ResultSet resultSet) {        
        try {
            return new PlaceDB (
                resultSet.getInt("place_id"),
                resultSet.getString("place_name"),
                resultSet.getString("place_info"),
                resultSet.getInt("category_id")
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
            System.err.print("ERROR INSERTING PLACE");
            
            return false;
        }

        return true;
    }
    
    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "place_id");

            setValues();
            setPlaceIdColumnValue(4);
            
            dbConnection.executeAndClose();
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }
        
        return false;
    }
    
    private String build_UPDATE_SET() {
        return "place_name = ?, " +
               "place_info = ?, " +
               "category_id = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setString(1, getName());
        statement.setString(2, getInfo());
        statement.setInt(3, getCategoryId());
        
        dbConnection.setStatement(statement);
    }
    
    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "place_id");
            
            setPlaceIdColumnValue(1);
            
            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.println("ERROR DELETING");
            
            return false;
        }
        
        return true;
    }
    
    private void setPlaceIdColumnValue(int index) throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(index, getId());
        
        dbConnection.setStatement(statement);
    }

    
    
}
