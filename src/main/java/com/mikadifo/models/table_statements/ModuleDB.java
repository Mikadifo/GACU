package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.Module;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ModuleDB extends Module implements SQL_Statement {

    private DB_Connection dbConnection = new DB_Connection();

    public ModuleDB(int id, String name) {
        super(id, name);
    }

    public ModuleDB() { }

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

    public List<ModuleDB> getResults() {
        List<ModuleDB> modules = new ArrayList<>();
        ResultSet results;

        try {
            results = dbConnection.executeQuery();

            while (results.next()) {
                modules.add(getModule(results));
            }

            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {
            return null;
        }

        return modules;
    }

    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "module_id");

            setModuleIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");

            return false;
        }

        return true;
    }

    public ModuleDB getModule() {
        return getResults().get(0);
    }

    private ModuleDB getModule(ResultSet resultSet) {
        try {
            return new ModuleDB(
                    resultSet.getInt("module_id"),
                    resultSet.getString("module_name")
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
            System.err.print("ERROR INSERTING MODULE");

            return false;
        }

        return true;
    }

    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "module_id");

            setValues();
            setModuleIdColumnValue(2);

            dbConnection.executeAndClose();
            
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }

        return false;
    }

    private String build_UPDATE_SET() {
        return "module_name = ?";
    }

    private void setValues() throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();

        statement.setString(1, getName());

        dbConnection.setStatement(statement);
    }

    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "module_id");

            setModuleIdColumnValue(1);

            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.println("ERROR DELETING");

            return false;
        }

        return true;
    }

    private void setModuleIdColumnValue(int index) throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();

        statement.setInt(index, getId());

        dbConnection.setStatement(statement);
    }

}
