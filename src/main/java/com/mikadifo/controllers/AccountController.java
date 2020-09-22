package com.mikadifo.controllers;

import com.mikadifo.models.db_tables.City;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class AccountController implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnDeleteAccount;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtLogin;
    @FXML
    private ComboBox<City> comboCity;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO //
    }    

    @FXML
    private void OnCancelClick(ActionEvent event) {
    }

    @FXML
    private void OnDeleteAccountClick(ActionEvent event) {
    }

    @FXML
    private void OnUptadeClick(ActionEvent event) {
    }

    @FXML
    private void onUsernameKeyReleased(KeyEvent event) {
    }

    @FXML
    private void onPasswordKeyReleased(KeyEvent event) {
    }

    @FXML
    private void onLoginKeyReleased(KeyEvent event) {
    }
    
}
