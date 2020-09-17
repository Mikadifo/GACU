package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.Type;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class TypeDB extends Type implements SQL_Statement {
    
    private DB_Connection dbConnection = new DB_Connection();

    public TypeDB() { }

    public TypeDB(int id, String name, String description) {
        super(id, name, description);
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
    
    public List<Type> getResults() {
        List<Type> types = new ArrayList<>();
        ResultSet results;
        
        try {
            results = dbConnection.executeQuery();
            
            while(results.next()) {
                types.add(getType(results));
            }
            
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {            
            return null;
        }
        
        return types;
    }
    
    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "type_id");
            
            setIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");
            
            return false;
        }
        
        return true;
    }
    
    public Type getType() {
        return getResults().get(0);
    }
    
    private Type getType(ResultSet resultSet) {        
        try {
            return new Type (
                resultSet.getInt("type_id"),
                resultSet.getString("type_name"),
                resultSet.getString("type_description")
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
            System.err.print("ERROR INSERTING TYPE");
            
            return false;
        }

        return true;
    }

    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "type_id");

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
        return "type_name = ?, " +
               "type_description = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setString(2, getName());
        statement.setString(3, getDescription());
        
        dbConnection.setStatement(statement);
    }

    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "type_id");
            
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
