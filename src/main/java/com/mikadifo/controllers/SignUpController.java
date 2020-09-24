/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikadifo.controllers;

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
            FXMLLoader loader= new FXMLLoader(SignUpController.class.getResource("/com/mikadifo/views/SignUp.fxml"));
            Parent root=loader.load();
            
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            Stage currentStage=(Stage)btnCreate.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
