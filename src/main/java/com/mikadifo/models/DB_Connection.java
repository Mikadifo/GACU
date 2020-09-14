package com.mikadifo.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Connection {

    private Connection dbConnection;
    private Statement statement;
    private ResultSet resultSet;

    private String server = "jdbc:postgresql://localhost:5432/gacu_db";
    private String user = "postgres"; 
    private String password = "admin";
    
    public DB_Connection() {
	loadDriver();
	dbConnect();
    }

    private void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver Error");
        }
    }
    
    private void dbConnect() {
        try {
            dbConnection = DriverManager.getConnection(server, user, password);
        } catch (SQLException ex) {
            System.err.println("DB Connection Error");
        }
    }

    public ResultSet query(String sql) {
	try{
	    statement = dbConnection.createStatement();
	    resultSet = statement.executeQuery(sql);

	    return resultSet;
	} catch (SQLException ex) {
	    System.err.println("Error executing the query");
	}
    }

    public SQLException statement(String sql) {
	try {
	    statement = dbConnection.createStatement();
	    statement.execute(sql);
	    statement.close();
	} catch (SQLException ex) { return ex; }

	return null;
    }

    public Connection getDbConnection() {
	return dbConnection;
    }

    public void setDbConnection(Connection dbConnection) {
	this.dbConnection = dbConnection;
    }

}
