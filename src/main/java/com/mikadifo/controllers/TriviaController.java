/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikadifo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class TriviaController implements Initializable {

    private WindowLoader loader;
    private Stage currentStage;

    @FXML
    private Button bntHome;
    @FXML
    private Button btnContinue;
    @FXML
    private TextArea txtQuestion;
    @FXML
    private Button btnOption_1;
    @FXML
    private Button btnOption_2;
    @FXML
    private Button btnOption_3;
    @FXML
    private Button btnOption_4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void init(Scene scene) {
        btnContinue.getScene().getStylesheets().add("/styles/trivia.css");
    }

    @FXML
    private void onHomeAction(ActionEvent event) {
        try {
            loader = new WindowLoader();
            loader.load("Gallery");
            GalleryController gallery = loader.getController();
            gallery.init(loader.getScene(), Roles.GUEST); //Role must be the logged user role

            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onContinueAction(ActionEvent event) {
        if (btnOption_1.isFocused() || btnOption_2.isFocused() || btnOption_3.isFocused() || btnOption_4.isFocused()) {
            ffs
        }else {
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Seleccione la respuesta");
        }

    }
}
        