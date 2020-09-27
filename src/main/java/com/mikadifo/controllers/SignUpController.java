/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
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

    @FXML
    private void onCancelAction(ActionEvent event) {
    }

    @FXML
    private void onCreateAction(ActionEvent event) {
     //obtener los datos de el dialog y guardarlo en la base 
        String cedula = txtLogin.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if (validar.validateLogIn(cedula)) {
            
//            if (validar.validateUsername(username)) {
//                if (validar.validatePassword(password)) {
                    //Combo box validacion 
                    checkUser(cedula);
                    if (isCheckedUser()) {//comprobar en la base de datos
                        
                        UserDB user = new UserDB();
                        user.setLogin(cedula);
                        user.setPassword(password);
                        user.setUsername(username);
                        user.setCityId(1);
                        user.setRoleId((short)1);
                        user.insert();// Insertar en la base de datos
                        
                        alert.setHeaderText(null);
                        alert.setTitle("Confirmación");
                        alert.setContentText("Usuario registrado con exito");
                    } else {
                         alert.setHeaderText(null);
                        alert.setTitle("Confirmación");
                        alert.setContentText("La cedula ya está registrada");
                    }
//
//                } else {
//                    System.out.println("Contraseña incorrecta");
//                }
//            } else {
//                System.out.println("Username incorrecto");
//            }
        } else {
            System.out.println("No existe ");
        }

    }

    @FXML
    private void onUsernameKeyReleased(KeyEvent event) {

    }

    @FXML
    private void onLoginKeyPressed(KeyEvent event) {

    }

    @FXML
    private void onPasswordKeyReleased(KeyEvent event) {

    }

    @FXML
    private void onCityKeyRekeased(KeyEvent event) {
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
}
