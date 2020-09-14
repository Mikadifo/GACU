/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikadifo.models.table_statements;

import com.mikadifo.models.db_tables.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MIKADIFO
 */
public class UserDB extends User{
    //attributes
    ConnectPG conecta = new ConnectPG();
    VistaPersonas vista = new VistaPersonas();
    
    //Constructor
    public UserDB() {
    }

    public UserDB(int id, String login, String password, String username, int cityId, short roleId) {
        super(id, login, password, username, cityId, roleId);
    }
    
    //Methods
    public boolean insertUser(){
    
    String nsql;
        nsql = "INSERT INTO Users";
        nsql += "(user_id, role_id, login_user, pass_user, username, city_id)";
        nsql += "VALUES('" + getId()+ "','" + getRoleId() + "','"
                + getLogin()+ "','" + getPassword()+ "','" + getUsername()
                + "','" + getCityId()+ "');";
        if (conecta.noQuery(nsql) == null) {
            return true;
        } else {
            System.out.println("ERROR");
            return false;
        } 
    
}
    public List<User> ShowDataUser() {
        try {
            String sql = "SELECT * FROM Users";
            ResultSet resultSet = conecta.query(sql);
            List<User> listUser = new ArrayList<User>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(Integer.valueOf(resultSet.getString("user_id")));
                user.setRoleId(Short.valueOf(resultSet.getString("role_id")));
                user.setLogin(resultSet.getString("login_user"));
                user.setPassword(resultSet.getString("pass_user"));
                user.setUsername(resultSet.getString("username"));
                user.setCityId(Integer.valueOf(resultSet.getString("city_id")));
                
                listUser.add(user);

            }
            resultSet.close();
            return listUser;
        } catch (SQLException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public UserDB GetUser(String IdUser){
        UserDB user = new UserDB();
        String sql = "SELECT * FROM Users where user_id='" + IdUser + "'";
            try (ResultSet resultSet = conecta.query(sql)) {
                resultSet.next();
                user.setId(Integer.valueOf(resultSet.getString("user_id")));
                user.setRoleId(Short.valueOf(resultSet.getString("role_id")));
                user.setLogin(resultSet.getString("login_user"));
                user.setPassword(resultSet.getString("pass_user"));
                user.setUsername(resultSet.getString("username"));
                user.setCityId(Integer.valueOf(resultSet.getString("city_id")));
                return user;
            }catch(SQLException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
            
            }
            return null;
    }
    public boolean DeleteDataUser(String data) {

        String nsql;
        nsql = "DELETE FROM Users WHERE user_id = ('" + data + "')"; 

        if (conecta.noQuery(nsql) == null) {
            return true;
        } else {
            System.out.println("ERROR");
            return false;
        }
    }
    
    public boolean editDataUser() {

        String nsql;
        nsql = "UPDATE Users SET user_id='" + getId()+ "',role_id='" + getRoleId()+ "',login_user='"
                + getLogin()+ "',pass_user='" + getPassword()+ "',username='" + 
                getUsername()+ "',city_id='" + getCityId()+ "'";
        if (conecta.noQuery(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }
    
}
