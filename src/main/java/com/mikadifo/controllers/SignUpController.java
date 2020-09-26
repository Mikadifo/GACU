/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikadifo.controllers;

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
import javafx.scene.control.ComboBox;
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
public class SignUpController implements Initializable {

    public SignUpController(Validations validar, UserDB user) {
        this.validar= validar;
        this.user= user;
    }
    private final UserDB user;
    private final Validations validar;
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
        // TODO
    }    

    @FXML
    private void onCancelAction(ActionEvent event) {
    }

    @FXML
    private void onCreateAction(ActionEvent event) {
    try {
            FXMLLoader loader = new FXMLLoader(SignUpController.class.getResource("/com/mikadifo/views/SignUp.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            Stage currentStage = (Stage) btnCreate.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ValidacionCamposVacios()) {
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Ingrese todos los datos");

            // Si hemos pulsado en aceptar
        } else if (isCheckedUser()) {
             //obtener los datos de el dialog y guardarlo en la base 
            String cedula = txtLogin.getText();
            String username = txtUsername.getText();
            String password = txtPassword.getText();

          
            UserDB user = new UserDB(cedula, username, password);
            user.insert();// Insertar en la base de datos
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Usuario registrado con exito");
            limpiarCampos();            
    }else{ 
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("La cedula ya está registrada");
        }
        }

    @FXML
    private void onUsernameKeyReleased(KeyEvent event) {
         validar.validateUsername(txtUsername.getText());
    }

    @FXML
    private void onLoginKeyPressed(KeyEvent event) {
        validar.validateLogIn(txtLogin.getText());
    }

    @FXML
    private void onPasswordKeyReleased(KeyEvent event) {
        validar.validatePassword(txtPassword.getText());
    }

    @FXML
    private void onCityKeyRekeased(KeyEvent event) {
    }
      private void limpiarCampos() {
        txtLogin.setText("");
        txtPassword.setText("");
        txtUsername.setText("");
       
    }
     private boolean ValidacionCamposVacios() {
        return  txtUsername.getText().isEmpty() || txtLogin.getText().isEmpty()
                || txtPassword.getText().isEmpty();
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
