package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.Role_Operation;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class Role_OperationDB extends Role_Operation implements SQL_Statement{
    
    private DB_Connection dbConnection = new DB_Connection();

    public Role_OperationDB() { }

    public Role_OperationDB(int id, short roleId, int operationModuleId) {
        super(id, roleId, operationModuleId);
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
    
    public List<Role_OperationDB> getResults() {
        List<Role_OperationDB> role_operation = new ArrayList<>();
        ResultSet results;
        
        try {
            results = dbConnection.executeQuery();
            
            while(results.next()) {
                role_operation.add(getRole_Operation(results));
            }
            
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {            
            return null;
        }
        
        return role_operation;
    }
    
    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "role_operation_id");
            
            setRole_OperationIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");
            
            return false;
        }
        
        return true;
    }
    
    public Role_OperationDB getRole_Operation() {
        return getResults().get(0);
    }
    
    private Role_OperationDB getRole_Operation(ResultSet resultSet) {        
        try {
            return new Role_OperationDB (
                resultSet.getInt("role_operation_id"),
                resultSet.getShort("role_id"),
                resultSet.getInt("operation_module_id")
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
            System.err.print("ERROR INSERTING ROLE_OPERATION");
            
            return false;
        }

        return true;
    }
    
    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "role_operation_id");

            setValues();
            setRole_OperationIdColumnValue(3);
            
            dbConnection.executeAndClose();
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }
        
        return false;
    }
    
    private String build_UPDATE_SET() {
        return "role_id = ?, " +
               "operation_module_id = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setShort(1, getRoleId());
        statement.setInt(2, getOperationModuleId());
        
        dbConnection.setStatement(statement);
    }
    
    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "role_operation_id");
            
            setRole_OperationIdColumnValue(1);
            
            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.println("ERROR DELETING");
            
            return false;
        }
        
        return true;
    }
    
    private void setRole_OperationIdColumnValue(int index) throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setInt(index, getId());
        
        dbConnection.setStatement(statement);
    }
    
    
    
}
