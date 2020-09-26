package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.User;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UserDB extends User implements SQL_Statement {

    private DB_Connection dbConnection = new DB_Connection();

    public UserDB() { }

    public UserDB(int id, String login, String password, String username, 
            int cityId, short roleId) {
        super(id, login, password, username, cityId, roleId);
    }
     public UserDB(String login, String password, String username
            ) {
          super( login, password, username);
       
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
    
    public List<UserDB> getResults() {
        List<UserDB> users = new ArrayList<>();
        ResultSet results;
        
        try {
            results = dbConnection.executeQuery();
            
            while(results.next()) {
                users.add(getUser(results));
            }
            
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {            
            return null;
        }
        
        return users;
    }
    
    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "login_user");
            
            setLoginColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");
            
            return false;
        }
        
        return true;
    }
    
    public UserDB getUser() {
        return getResults().get(0);
    }
    
    private UserDB getUser(ResultSet resultSet) {        
        try {
            return new UserDB (
                resultSet.getInt("user_questions_id"),
                resultSet.getString("login_user"),
                resultSet.getString("pass_user"),
                resultSet.getString("username"),
                resultSet.getInt("city_id"),
                resultSet.getShort("role_id")
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
            System.err.print("ERROR INSERTING USER");
            
            return false;
        }

        return true;
    }

    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "login_user");

            setValues();
            setLoginColumnValue(6);
            
            dbConnection.executeAndClose();
            
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }
        
        return false;
    }
    
    private String build_UPDATE_SET() {
        return "role_id = ?, " +
               "login_user = ?, " +
               "pass_user = ?, " +
               "username = ?, " +
               "city_id = ?";
    }
    
    private void setValues() throws SQLException {  
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setShort(1, getRoleId());
        statement.setString(2, getLogin());
        statement.setString(3, getPassword());
        statement.setString(4, getUsername());
        statement.setInt(5, getCityId());
        
        dbConnection.setStatement(statement);
    }

    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "login_user");
            
            setLoginColumnValue(1);
            
            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.println("ERROR DELETING");
            
            return false;
        }
        
        return true;
    }
    
    private void setLoginColumnValue(int index) throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();
        
        statement.setString(index, getLogin());
        
        dbConnection.setStatement(statement);
    }
      
}
