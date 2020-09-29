/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.UserDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ChangePasswordController implements Initializable {

    Validations validation = new Validations();
    UserAuthentication userValidation = new UserAuthentication();
    private boolean checkedUser;
    
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
    
    public void init(Scene scene) {
        btnChange.getScene().getStylesheets().add("/styles/account.css");
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onChangeAction(ActionEvent event) {
        String login = txtLogin.getText();
        String password = txtOldPassword.getText();
        String newPasword = txtNewPassword.getText();

        if (validation.validateLogIn(login)) {
            checkUser(login);
            if (isCheckedUser()) {
                userValidation.checkPassword(login, password);
                if (userValidation.isChecked()) {

                    UserDB user = new UserDB();
                    user.setLogin(login);
                    user.selectById();
                    user = user.getUser();
                    user.setPassword(newPasword);
                    user.update();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Datos: ");
                    alert.setTitle("Error");
                    alert.setContentText("La contraseña no coincide con la cedula");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Datos: ");
                alert.setTitle("Error");
                alert.setContentText("La cedula ingresada no existe en la Base de Datos");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Datos: ");
            alert.setTitle("Error");
            alert.setContentText("La cedula ingresada no es valida");
            alert.showAndWait();
        }
    }

    @FXML
    private void onLoginKeyTyped(KeyEvent event) {
        char val = event.getCharacter().charAt(0);
        if (!Character.isDigit(val) || txtLogin.getText().length() > 9) {
            event.consume();
        }
    }

    public void checkUser(String login) {
        UserDB user = new UserDB();

        user.setLogin(login);
        user.selectById();

        checkedUser = user.getUser() != (null);

    }

    public boolean isCheckedUser() {
        return checkedUser;
    }

}
