package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.UserDB;

public class UserAuthentication {
    
    private boolean authenticated;

    public void checkPassword(String login, String password) {
	UserDB user = new UserDB();

	user.setLogin(login);
	user.selectById();

	user = user.getUser();
	authenticated = user.getPassword().equals(password); //missing hash pass
    }

    public boolean isAuthenticated() {
	return authenticated;
    }
    
    public boolean userExists(String login) {
	UserDB user = new UserDB();

	user.setLogin(login);
        user.selectById();

        return  user.getUser() != (null);
    }
    
}
