 package com.mikadifo.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Connection {

    private Connection connection;
    private PreparedStatement statement;

    private String url = "jdbc:postgresql://localhost:5432/GacuBD";
    private String login = "postgres"; 
    private String password = "computer147";
    
    public DB_Connection() {
	loadDriver();
    }

    private void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver Error");
        }
    }
    
    public void buildAndPrepareInsert(String table, String columns,
            String attributes) throws SQLException {
        setStatement(getConnection().prepareStatement(
            build_INSERT(table, columns, attributes))                
        );
    }
    
    private String build_INSERT(String table, String columns,
            String attributes){
        return SQL_Sintax.INSERT
                    .getSintax()
                    .replaceFirst("\\?", table)
                    .replaceFirst("\\?", columns)
                    .replace("?", attributes);
    }
    
    public void buildAndPrepareSelect(String table)
            throws SQLException {
        setStatement(getConnection().prepareStatement(
                build_SELECT_ALL(table))
        );
    }
    
    private String build_SELECT_ALL(String table) {
        return SQL_Sintax.SELECT_ALL
                    .getSintax()
                    .replace("?", table);
                
    }
    
    public void buildAndPrepareSelect(String table, String columnName) 
            throws SQLException {
        setStatement(getConnection().prepareStatement(
            build_SELECT_BY_COLUMN(table, columnName))
        );
    }
    
    private String build_SELECT_BY_COLUMN(String table, String columnName) {
        return SQL_Sintax.SELECT_BY_COLUMN
                    .getSintax()
                    .replaceFirst("\\?", table)
                    .replace("?", build_WHERE(columnName));
    }
    
    public void buildAndPrepareUpdate(String table, String UPDATE_SET,
        String columnName) throws SQLException {
        setStatement(getConnection().prepareStatement(
            build_UPDATE(table, UPDATE_SET, columnName))
        );
    }
    
    private String build_UPDATE(String table, String UPDATE_SET,
            String columnName) {
        return SQL_Sintax.UPDATE
                    .getSintax()
                    .replaceFirst("\\?", table)
                    .replaceFirst("(?s)(.*)\\?", "$1"
                            + build_WHERE(columnName))
                    .replaceFirst("\\?", UPDATE_SET);
                    
    }
    
    public void buildAndPrepareDelete(String table, String columnName)
            throws SQLException {
        setStatement(getConnection().prepareStatement(
            build_DELETE(table, columnName))      
        );
    }
    
    private String build_DELETE(String table, String columnName) {
        return SQL_Sintax.DELETE
                    .getSintax()
                    .replaceFirst("\\?", table)
                    .replace("?", build_WHERE(columnName));
    }
    
    private String build_WHERE(String column) {
        return "(" + column + " = ?)";
    }
    
    public void executeAndClose() throws SQLException {        
        statement.execute();
        closeStatement();
        disconnect();
    }
    
    public void closeStatement() throws SQLException {
        statement.close();
    }
    
    public ResultSet executeQuery() throws SQLException {
        ResultSet results = statement.executeQuery();        
        return results;
    }
    
    public Connection getConnection() {     
        connect();
        
	return connection;
    }
    
    public void setStatement(PreparedStatement statement) {
        this.statement = statement;
    }
    
    private void connect() {
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException ex) {
            System.err.println("DB Connection Error");
        }
    }

    public void setConnection(Connection dbConnection) {
	this.connection = dbConnection;
    }
    
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.print("Error while closing connection");
        }
    }
    
    public PreparedStatement getStatement() {
        return statement;
    }

}
