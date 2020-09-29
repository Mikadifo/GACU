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
import javafx.scene.control.Button;
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

    private Validations validation = new Validations();
    private UserAuthentication userValidation = new UserAuthentication();
    private boolean checkedUser;

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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void init(Scene scene) {
        btnEnter.getScene().getStylesheets().add("/styles/account.css");
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
            checkUser(login);
            if (isCheckedUser()) {
                userValidation.checkPassword(login, password);
                if (userValidation.isChecked()) {
                    try {
                        try {
                            loader = new WindowLoader();
                            loader.load("Gallery");
                            GalleryController gallery = loader.getController();
                            gallery.init(loader.getScene(), Roles.USER);
                            loader.showAndWait(false);
                        } catch (IOException ex) {
                            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Stage currentStage = (Stage) btnEnter.getScene().getWindow();
                        currentStage.close();
                        Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
    private void onCancelAction(ActionEvent event) {
        Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onLoginKeyTYped(KeyEvent event) { //cambiar a onLoginKeyTyped
    }

}
