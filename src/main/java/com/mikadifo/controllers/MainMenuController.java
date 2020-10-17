package com.mikadifo.controllers;

import static com.mikadifo.controllers.WindowFactories.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class MainMenuController implements Initializable, Window {

    public static boolean isLogedIn;
    public static Scene scene;
    
    @FXML
    private ImageView imgLogo;
    @FXML
    private StackPane imageWrapper;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	imgLogo.setFitWidth(imageWrapper.getWidth());
	imgLogo.setFitHeight(imageWrapper.getHeight());
	imgLogo.setImage(new Image(("/imgs/logo.png")));
    }

    @Override
    public void init() {
	currentScene.getStylesheets().add("/styles/menu.css");
    }

    public static void closeIfLogedIn() {
	if (isLogedIn) {
	    Stage stage = (Stage) scene.getWindow();
	    stage.close();
	}
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
        isLogedIn=true;
        closeIfLogedIn();
	GALLERY.createWindow().init();
    }

    @FXML
    private void onLoginAction(ActionEvent event) {
	LOGIN.createWindow().init();
    }

    @FXML
    private void onSigninAction(ActionEvent event) {
	SIGNUP.createWindow().init();
    }
   
    void init(Scene scene, Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
