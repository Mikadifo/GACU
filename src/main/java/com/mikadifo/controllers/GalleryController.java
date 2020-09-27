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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class GalleryController implements Initializable {

    @FXML
    private Button btnExit;
    @FXML
    private Button btnTrivia;
    @FXML
    private AnchorPane logedPane;
    @FXML
    private AnchorPane guestPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void init(Scene scene) {
        btnTrivia.getScene().getStylesheets().add("/styles/gallery.css");
    }

    @FXML
    private void onExitAction(ActionEvent event) {
    }

    @FXML
    private void onTriviaAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(GalleryController.class.getResource("/com/mikadifo/views/Trivia.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            TriviaController trivia = (TriviaController) loader.getController();
            trivia.init(scene);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            Stage currentStage = (Stage) btnTrivia.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
