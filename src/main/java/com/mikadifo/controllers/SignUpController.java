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
 
    private  Validations validar = new Validations();
    private boolean checkedUser;
    
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
  

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
    private ComboBox<?> comboCity;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
    }   
    
    public void init(Scene scene) {
        btnCreate.getScene().getStylesheets().add("/styles/account.css");
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
	Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onCreateAction(ActionEvent event) {
     //obtener los datos de el dialog y guardarlo en la base 
        String cedula = txtLogin.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if (validar.validateLogIn(cedula)) {
            
            if (validar.validateUsername(username)) {
                if (validar.validatePassword(password)) {
                    //Combo box validacion 
                    checkUser(cedula);
                    if (isCheckedUser()) {                        
                        UserDB user = new UserDB();
                        user.setLogin(cedula);
                        user.setPassword(password);
                        user.setUsername(username);
                        user.setCityId(1);
                        user.setRoleId((short)1);
                        user.insert();
                        
                        alert.setHeaderText(null);
                        alert.setTitle("Confirmación");
                        alert.setContentText("Usuario registrado con exito");
                    } else {
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("La cedula ya está registrada");
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("La contraseña no es correcta");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("El username es incorrecta");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("La cedula es incorrecta");
            alert.showAndWait();
        }

    }

    @FXML
    private void onUsernameKeyReleased(KeyEvent event) { //cambiar a onUsernameKeyTyped
        //solo caracteres alfenumericos
    }

    @FXML
    private void onLoginKeyPressed(KeyEvent event) { //cambiar a onLoginKeyTyped
        //same that login
    }

    @FXML
    private void onPasswordKeyReleased(KeyEvent event) { //cambiar a onPasswordKeyTyped
        //same that login
    }

    @FXML
    private void onCityKeyRekeased(KeyEvent event) { //cambiar a onCityKeyTyped
        //filtrar las ciudades segun se este escribiendo (No es prioridad)
    }
  
    public void checkUser(String login){
       UserDB user = new UserDB();

        user.setLogin(login);
        user.selectById();
        checkedUser = user.getUser() == (null);
    }
     
    public boolean isCheckedUser() {
	return checkedUser;
    }

    @FXML
    private void onUsernameKeyTyped(KeyEvent event) {
	
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
