package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.Images;
import com.mikadifo.models.SQL_Statement;
import java.awt.Image;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ImageDB extends Images implements SQL_Statement{
    
    private DB_Connection dbConnection = new DB_Connection();

    public ImageDB() { }

    public ImageDB(int id, Image image, String author, String description, int placeId) {
        super(id, image, author, description, placeId);
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
    
    public List<Images> getResults() {
        List<Images> images = new ArrayList<>();
        ResultSet results;
        
        try {
            results = dbConnection.executeQuery();
            
            while(results.next()) {
                images.add(getImages(results));
            }
            
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {            
            return null;
        }
        
        return images;
    }
    
    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "image_id");
            
            setImageIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");
            
            return false;
        }
        
        return true;
    }
    
    public Images getImages() {
        return getResults().get(0);
    }
    
    private Images getImages(ResultSet resultSet) {        
        try {
            return new Images (
                resultSet.getInt("image_id"),
                resultSet.getBytes("image"),
                resultSet.getString("image_author"),
                resultSet.getString("image_description"),
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
            System.err.print("ERROR INSERTING IMAGE");
            
            return false;
        }

        return true;
    }
    
    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "image_id");

            setValues();
            setImageIdColumnValue(5);
            
            dbConnection.executeAndClose();
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }
        
        return false;
    }
    
    private String build_UPDATE_SET() {
        return "place_id = ?, " +
               "image = ?, " +
               "image_author = ?, " +
               "image_description = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(1, getPlaceId());
        statement.setBytes(2, getImage());
        statement.setString(3, getAuthor());
        statement.setString(4, getDescription());
        
        dbConnection.setStatement(statement);
    }
    
    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "image_id");
            
            setImageIdColumnValue(1);
            
            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.println("ERROR DELETING");
            
            return false;
        }
        
        return true;
    }
    
    private void setImageIdColumnValue(int index) throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(index, getId());
        
        dbConnection.setStatement(statement);
    }
    
    
    
}
