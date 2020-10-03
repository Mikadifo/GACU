package com.mikadifo.controllers;

import com.mikadifo.models.Roles;
import com.mikadifo.models.db_tables.City;
import com.mikadifo.models.db_tables.User;
import com.mikadifo.models.table_statements.UserDB;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class AccountController implements Initializable {

    private UserDB currentUser;

    private WindowLoader loader;

    @FXML
    private TextField txtUsername;
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
    }
    
    public void init(Scene scene, UserDB user) {
        btnUpdate.getScene().getStylesheets().add("/styles/account.css");
        currentUser = user;
    }

    private UserDB getUserFromView() {
        User user = new User();
        
        user.setUsername(txtUsername.getText());
        user.setLogin(txtLogin.getText());
        user.setCityId(comboCity.getSelectionModel().getSelectedItem().getId());

        return (UserDB) user;

    }

    @FXML
    private void onCityKeyReleased(KeyEvent event) {
    }

    @FXML
    private void onLoginKeyTyped(KeyEvent event) {
    }

    @FXML
    private void onUsernameKeyTyped(KeyEvent event) {
    }

    @FXML
    private void onCityKeyTyped(KeyEvent event) {
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
	Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onDeleteAction(ActionEvent event) {
	getUserFromView().delete();
    }

    @FXML
    private void onUpdateAction(ActionEvent event) {
        getUserFromView().update();
    }

    @FXML
    private void onChangePasswordAction(ActionEvent event) {
        try {
            loader = new WindowLoader();
            loader.load("ChangePassword");
            ChangePasswordController changePasswordController = loader.getController();
            changePasswordController.init(loader.getScene(), null);

            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
