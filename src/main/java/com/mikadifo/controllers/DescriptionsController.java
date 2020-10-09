/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.ImageDB;
import com.mikadifo.models.table_statements.PlaceDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class DescriptionsController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private Label title;
    @FXML
    private AnchorPane imageBottomPane;
    @FXML
    private AnchorPane imageCenterPane;
    @FXML
    private AnchorPane placeBottomPane;
    @FXML
    private Button btnImages;
    @FXML
    private AnchorPane placeCenterPane;
    @FXML
    private TextFlow placeInfoText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    public void init(Scene scene, PlaceDB place) {
       scene.getStylesheets().add("/styles/account.css");      
    }
    public void init(Scene scene, ImageDB image) {
        scene.getStylesheets().add("/styles/account.css");   
    }
        
    @FXML
    private void onImagesAction(ActionEvent event) {
        
    }
    
}
