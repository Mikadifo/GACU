package com.mikadifo.models.db_tables;

public class User {
    
    private int id;
    private String login;
    private String password;
    private String username;
    private int cityId;
    private short roleId;

    public User() { }

    public User(int id, String login, String password, String username, int cityId, short roleId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.username = username;
        this.cityId = cityId;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public short getRoleId() {
        return roleId;
    }

    public void setRoleId(short roleId) {
        this.roleId = roleId;
    }

}

