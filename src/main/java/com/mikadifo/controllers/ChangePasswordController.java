/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikadifo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnChange;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtOldPassword;
    @FXML
    private TextField txtNewPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onCancelAction(ActionEvent event) {
    }

    @FXML
    private void onChangeAction(ActionEvent event) {
    }

    @FXML
    private void onLoginKeyReleased(KeyEvent event) {
    }

    @FXML
    private void onOldPassworKeyReleased(KeyEvent event) {
    }

    @FXML
    private void onNewPassworKeyReleased(KeyEvent event) {
    }
    
}
