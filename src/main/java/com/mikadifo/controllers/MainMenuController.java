package com.mikadifo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button btnExit;
    @FXML
    private Button btnGuest;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignin;
    @FXML
    private ImageView imgLogo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onExitAction(ActionEvent event) {
    }

    @FXML
    private void onGuestAction(ActionEvent event) {
    }

    @FXML
    private void onLoginAction(ActionEvent event) {
    }

    @FXML
    private void onSigninAction(ActionEvent event) {
    }
    
}