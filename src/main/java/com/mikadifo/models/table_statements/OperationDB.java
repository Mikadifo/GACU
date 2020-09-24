package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.Operation;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class OperationDB extends Operation implements SQL_Statement {

    private DB_Connection dbConnection = new DB_Connection();

    public OperationDB(int id, String name) {
        super(id, name);
    }

    public OperationDB() { }

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

    public List<OperationDB> getResults() {
        List<OperationDB> operations = new ArrayList<>();
        ResultSet results;

        try {
            results = dbConnection.executeQuery();

            while (results.next()) {
                operations.add(getOperation(results));
            }

            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {
            
            return null;
        }

        return operations;
    }

    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "operation_id");

            setOperationIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");

            return false;
        }

        return true;
    }

    public OperationDB getOperation() {
        return getResults().get(0);
    }

    private OperationDB getOperation(ResultSet resultSet) {
        try {
            
            return new OperationDB (
                    resultSet.getInt("operation_id"),
                    resultSet.getString("operation_name")
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
            System.err.print("ERROR INSERTING OPERATION");
            
            return false;
        }

        return true;
    }
    
    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "operation_id");

            setValues();
            setOperationIdColumnValue(2);
            
            dbConnection.executeAndClose();
            
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }
        
        return false;
    }
    
    private String build_UPDATE_SET() {
        
        return "operation_name = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setString(1, getName());
        dbConnection.setStatement(statement);
    }
    
    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "operation_id");
            
            setOperationIdColumnValue(1);
            
            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.println("ERROR DELETING");
            
            return false;
        }
        
        return true;
    }
    
    private void setOperationIdColumnValue(int index) throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(index, getId());
        
        dbConnection.setStatement(statement);
    }

}
