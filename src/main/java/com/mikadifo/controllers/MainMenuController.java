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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void init(Scene scene) {
        btnExit.getScene().getStylesheets().add("/styles/menu.css");
    }

    @FXML
    private void onExitAction(ActionEvent event) {

    }

    @FXML
    private void onGuestAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainMenuController.class.getResource("/com/mikadifo/views/Gallery.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            GalleryController gallery = (GalleryController) loader.getController();
            gallery.init(scene);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            Stage currentStage = (Stage) btnGuest.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onLoginAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainMenuController.class.getResource("/com/mikadifo/views/LogIn.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            LogInController login = (LogInController) loader.getController();
            login.init(scene);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            Stage currentStage = (Stage) btnGuest.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onSigninAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainMenuController.class.getResource("/com/mikadifo/views/SignUp.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            SignUpController signup = (SignUpController) loader.getController();
            signup.init(scene);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            Stage currentStage = (Stage) btnGuest.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
