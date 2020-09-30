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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class MainMenuController implements Initializable {

    private WindowLoader loader;
    
    @FXML
    private ImageView imgLogo;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void init(Scene scene) {
        scene.getStylesheets().add("/styles/menu.css");
    }

    @FXML
    private void onExitAction(ActionEvent event) {
        boolean isOk = showAlert(AlertType.CONFIRMATION, null, "Estas seguro?");

        if (isOk) System.exit(0);
    }

    private boolean showAlert(AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

        return alert.showAndWait().get() == ButtonType.OK;
    }
    

    @FXML
    private void onGuestAction(ActionEvent event) {
        try {
            loader = new WindowLoader();
            loader.load("Gallery");
            GalleryController gallery = loader.getController();
            gallery.init(loader.getScene(), Roles.GUEST, null);

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
            login.init(loader.getScene(), null);
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
