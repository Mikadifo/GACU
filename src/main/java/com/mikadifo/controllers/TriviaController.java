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
    }

    @FXML
    private void onContinueAction(ActionEvent event) {
    }


}
