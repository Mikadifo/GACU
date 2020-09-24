package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.UserDB;

public class UserAuthentication {
    
    private UserDB authenticatedUser;
    private boolean checked;

    public void checkPassword(String login, String password) {
	UserDB user = new UserDB();

	user.setLogin(login);
	user.selectById();

	user = user.getUser();

	checked = user.getPassword().equals(password); //missing hash pass and roles may
    }

    public boolean isChecked() {
	return checked;
    }
}
