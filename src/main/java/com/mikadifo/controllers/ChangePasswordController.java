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

    Validations validation = new Validations();
    UserAuthentication userValidation = new UserAuthentication();
    private boolean checkedUser;
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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
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

    public void checkUser(String login) {
        UserDB user = new UserDB();

        user.setLogin(login);
        user.selectById();

        checkedUser = user.getUser() != (null);

    }

    public boolean isCheckedUser() {
        return checkedUser;
    }

    private boolean showAlert(AlertType alertType, String header, String message) {
	    Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	    return alert.showAndWait().get() == ButtonType.OK;
    }

}
