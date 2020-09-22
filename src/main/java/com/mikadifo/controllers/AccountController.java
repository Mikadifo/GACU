package com.mikadifo.controllers;

import com.mikadifo.models.db_tables.City;
import com.mikadifo.models.db_tables.User;
import com.mikadifo.models.table_statements.UserDB;
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO //
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

    @FXML
    private void onCancelClick(ActionEvent event) {
    }

    @FXML
    private void onDeleteAccountClick(ActionEvent event) {
        getUserFromView().delete();
    }

    @FXML
    private void onUptadeClick(ActionEvent event) {
        getUserFromView().update();
    }

    private UserDB getUserFromView() {
        User user = new User();
        
        user.setUsername(txtUsername.getText());
        user.setPassword(txtPassword.getText());
        user.setLogin(txtLogin.getText());
        user.setCityId(comboCity.getSelectionModel().getSelectedItem().getId());

        return (UserDB) user;

    }
}
