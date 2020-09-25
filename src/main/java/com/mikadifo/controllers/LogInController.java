package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.UserDB;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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

    Validations validation = new Validations();
    UserAuthentication userValidation = new UserAuthentication();
    private boolean checkedUser;

    @FXML
    private Button btnEnter;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OnEnterAuto(ActionEvent event) {
//        validation.validateUsername(txtUsername.getText());
        String login = txtUsername.getText();
        String password = txtPassword.getText();

        userValidation.checkPassword(login, password);

        if (isCheckedUser()) {
            if (userValidation.isChecked()) {

                try {
                    FXMLLoader loader = new FXMLLoader(LogInController.class.getResource("/com/mikadifo/views/Gallery.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    Stage currentStage = (Stage) btnEnter.getScene().getWindow();
                    currentStage.close();
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("El usuario y la contraseña no existen");
                Optional<ButtonType> action = alert.showAndWait();
                System.out.println("Error");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("El usuario ingresado no existe");
            Optional<ButtonType> acept = alert.showAndWait();
            System.out.println("Error");

        }

    }

    @FXML
    private void OnCancelAction(ActionEvent event) {
    }

    @FXML
    private void onUsernameKeyReleased(KeyEvent event) {
        char val=event.getCharacter().charAt(0);
        if (!Character.isLetter(val)) {
            event.consume();
        }
    }

    @FXML
    private void onPasswordKeyReleased(KeyEvent event) {
        
    }

    public void checkUser(String login){
        UserDB user = new UserDB();
        
        user.setLogin(login);
        user.selectById();
        
        user = user.getUser();
        
        checkedUser = user.getLogin()==(null);
            
    }
    
    public boolean isCheckedUser() {
	return checkedUser;
    }
    
}
