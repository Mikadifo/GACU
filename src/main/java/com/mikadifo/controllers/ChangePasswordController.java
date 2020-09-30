package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.UserDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ChangePasswordController implements Initializable {

    private Validations validation;
    private UserAuthentication userValidation;
    private UserDB currentUser;
    
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	validation = new Validations();
	userValidation = new UserAuthentication();
    }
    
    public void init(Scene scene, UserDB user) {
        btnChange.getScene().getStylesheets().add("/styles/account.css");
        currentUser = user; 
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
            if (userValidation.userExists(login)) {
                userValidation.checkPassword(login, password);
                if (userValidation.isAuthenticated()) {

                    UserDB user = new UserDB();
                    user.setLogin(login);
                    user.selectById();
                    user = user.getUser();
                    user.setPassword(newPasword);
                    user.update();

                    showAlert(AlertType.INFORMATION, "Datos:", "La contraseña se actualizó con exito");

                } else {
                    showAlert(AlertType.ERROR, "Datos:", "La contraseña no coincide con la cedula");
                }
            } else {
                showAlert(AlertType.ERROR, "Datos:", "La cedula ingresada no existe en la Base de Datos");
            }
        } else {
            showAlert(AlertType.ERROR, "Datos:", "La cedula ingresada no es valida");
        }
    }

    @FXML
    private void onLoginKeyTyped(KeyEvent event) {
        char val = event.getCharacter().charAt(0);
        if (!Character.isDigit(val) || txtLogin.getText().length() > 9) {
            event.consume();
        }
    }

    private boolean showAlert(AlertType alertType, String header, String message) {
	Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	return alert.showAndWait().get() == ButtonType.OK;
    }

}
