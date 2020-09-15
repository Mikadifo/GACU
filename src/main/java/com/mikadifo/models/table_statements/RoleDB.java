package com.mikadifo.models.table_statements;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.db_tables.Role;
import com.mikadifo.models.SQL_Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class RoleDB extends Role implements SQL_Statement {

    private DB_Connection dbConnection = new DB_Connection();

    public RoleDB(int id, String name) {
        super(id, name);
    }

    public RoleDB() {
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

    public List<Role> getResults() {
        List<Role> role = new ArrayList<>();
        ResultSet results;

        try {
            results = dbConnection.executeQuery();

            while (results.next()) {
                role.add(getRole(results));
            }

            results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {
            return null;
        }

        return role;
    }

    @Override
    public boolean selectById() {
        try {
            dbConnection.buildAndPrepareSelect(TABLE, "role_id");

            setRoleIdColumnValue(1);
        } catch (SQLException ex) {
            System.err.println("ERROR");

            return false;
        }

        return true;
    }

    public Role getRole() {
        return getResults().get(0);
    }

    private Role getRole(ResultSet resultSet) {
        try {
            return new Role(
                    resultSet.getInt("role_id"),
                    resultSet.getString("role_name")
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
            System.err.print("ERROR INSERTING ROLE");

            return false;
        }

        return true;
    }

    @Override
    public boolean update() {
        try {
            dbConnection.buildAndPrepareUpdate(TABLE, build_UPDATE_SET(), "role_id");

            setValues();
            setRoleIdColumnValue(2);

            dbConnection.executeAndClose();
            return true;
        } catch (SQLException ex) {
            System.err.println("ERROR UPDATING");
        }

        return false;
    }

    private String build_UPDATE_SET() {
        return "role_name = ?, ";
    }

    private void setValues() throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();

        statement.setString(1, getName());

        dbConnection.setStatement(statement);
    }

    @Override
    public boolean delete() {
        try {
            dbConnection.buildAndPrepareDelete(TABLE, "role_id");

            setRoleIdColumnValue(1);

            dbConnection.executeAndClose();
        } catch (SQLException ex) {
            System.err.println("ERROR DELETING");

            return false;
        }

        return true;
    }

    private void setRoleIdColumnValue(int index) throws SQLException {
        PreparedStatement statement = dbConnection.getStatement();

        statement.setInt(index, getId());

        dbConnection.setStatement(statement);
    }

}
