package com.mikadifo.controllers;

import com.mikadifo.models.Roles;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class MainMenuController implements Initializable {

    private WindowLoader loader;
//    private Stage currentStage;
    
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loader = new WindowLoader();
    }
    
    public void init(Scene scene) {
        btnExit.getScene().getStylesheets().add("/styles/menu.css");
        Scene ss = btnExit.getScene();
        if (ss == null) {
            System.out.println("1111y");
        }
        WindowLoader.currentStage = (Stage) btnExit.getScene().getWindow();
        if (WindowLoader.currentStage == null) {
            System.out.println("YES");
        }
    }

    @FXML
    private void onExitAction(ActionEvent event) {
        WindowLoader.closeCurrent();
    }

    @FXML
    private void onGuestAction(ActionEvent event) {
        try {
            loader = new WindowLoader();
            loader.load("Gallery");
            GalleryController gallery = loader.getController();
            gallery.init(loader.getScene(), Roles.GUEST);
            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onLoginAction(ActionEvent event) {
          try {
            loader = new WindowLoader();
            loader.load("LogIn");
            LogInController login = loader.getController();
            login.init(loader.getScene());
            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onSigninAction(ActionEvent event) {
        try {
            loader = new WindowLoader();
            loader.load("SignUp");
            SignUpController signup = loader.getController();
            signup.init(loader.getScene());
            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
