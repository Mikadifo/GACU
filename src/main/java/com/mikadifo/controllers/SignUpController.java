package com.mikadifo.controllers;

import com.mikadifo.models.db_tables.City;
import com.mikadifo.models.table_statements.UserDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class SignUpController implements Initializable {
    
    private Validations validar;
    private UserAuthentication authentication;
    private Alert alert;

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnCreate;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<City> comboCity;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	validar = new Validations();
	authentication = new UserAuthentication();
	alert = new Alert(Alert.AlertType.CONFIRMATION);
    }   
    
    public void init(Scene scene) {
        btnCreate.getScene().getStylesheets().add("/styles/account.css");
        txtUsername.requestFocus();
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
	Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onCreateAction(ActionEvent event) {
     //obtener los datos de el dialog y guardarlo en la base 
        String login = txtLogin.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (validar.validateLogIn(login)) {
            if (validar.validateUsername(username)) {
//                if (validar.validatePassword(password)) { //fix
		    if (!comboCity.getSelectionModel().isEmpty()) {
			if (!authentication.userExists(login)) {                        
			    UserDB user = new UserDB();
			    user.setLogin(login);
			    user.setPassword(password);
			    user.setUsername(username);
			    user.setCityId(comboCity.getSelectionModel().getSelectedItem().getId()); 
			    user.setRoleId((short) 1);
			    user.insert();
                        
			    alert.setHeaderText(null);
			    alert.setTitle("Confirmación");
			    alert.setContentText("Usuario registrado con exito");
			    alert.showAndWait();
			} else {
			    alert.setHeaderText(null);
			    alert.setTitle("Error");
			    alert.setContentText("La cedula ya está registrada");
			    alert.showAndWait();
			}
		    } else {
			alert.setTitle("Error");
			alert.setContentText("Debe seleccionar una ciudad");
			alert.showAndWait();
		    }
//                } else {
//                    alert.setTitle("Error");
//                    alert.setContentText("La contraseña no es correcta");
//                    alert.showAndWait();
//                }
            } else {
                alert.setTitle("Error");
                alert.setContentText("El username es incorrecta");
                alert.showAndWait();
            }
        } else {
            alert.setTitle("Error");
            alert.setContentText("La cedula es incorrecta");
            alert.showAndWait();
        }

    }

    @FXML
    private void onUsernameKeyTyped(KeyEvent event) {
    String characterTyped = event.getCharacter();
    if (!characterTyped.isEmpty()) {
        char val = characterTyped.charAt(0);  

        if (!(Character.isLetter(val) || Character.isDigit(val))|| txtLogin.getText().length() > 245) {
        event.consume();
        }
    }    
	
    }

    @FXML
    private void onCedulaTyped(KeyEvent event) { //Rename to onLoginKeyTyped
	String characterTyped = event.getCharacter();
        
        if (!characterTyped.isEmpty()) {
            char val = characterTyped.charAt(0);
            
            if (!Character.isDigit(val) || txtLogin.getText().length() > 9)
                event.consume();
        }
    }

    @FXML
    private void onPasswordKeyTyped(KeyEvent event) {
    }

    @FXML
    private void onCityKeTyped(KeyEvent event) {
    }
}
