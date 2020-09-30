package com.mikadifo.controllers;

import com.mikadifo.models.Roles;
import com.mikadifo.models.table_statements.UserDB;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class LogInController implements Initializable {

    private Validations validation;
    private UserAuthentication userValidation;
    private boolean checkedUser;
    private UserDB currentUser;
    private WindowLoader loader;

    @FXML
    private Button btnEnter;
    @FXML
    private Button btnCancel;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtLogin;

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
        btnEnter.getScene().getStylesheets().add("/styles/account.css");
        currentUser = user;
    }

    private void onUsernameKeyTyped(KeyEvent event) { //falta agregar en scene builder el evento esta como null
        String characterTyped = event.getCharacter();
        
        if (!characterTyped.isEmpty()) {
            char val = characterTyped.charAt(0);
            
            if (!Character.isDigit(val) || txtLogin.getText().length() > 9)
                event.consume();
        }
    }

    @FXML
    private void onEnterAction(ActionEvent event) {
        String login = txtLogin.getText();
        String password = txtPassword.getText();

        if (validation.validateLogIn(login)) {
            if (userValidation.userExists(login)) {
                userValidation.checkPassword(login, password);
                if (userValidation.isAuthenticated()) {
                        try {
                            loader = new WindowLoader();
                            loader.load("Gallery");
                            GalleryController gallery = loader.getController();
                            gallery.init(loader.getScene(), Roles.USER, currentUser);
                            loader.showAndWait(false);
                        } catch (IOException ex) {
                            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Stage currentStage = (Stage) btnEnter.getScene().getWindow();
                        currentStage.close();
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

    private boolean showAlert(AlertType alertType, String header, String message) {
	Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	return alert.showAndWait().get() == ButtonType.OK;
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onLoginKeyTYped(KeyEvent event) { //cambiar a onLoginKeyTyped
        
    }

}
