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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
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
        
            boolean isOk = showAlert(AlertType.CONFIRMATION, null, "Estas seguro?");
       if (isOk) System.exit(0);
        }
    
    @FXML
    private void onImagesAction(ActionEvent event) {
        if(btnImages.isPressed()){
            placeBottomPane.setVisible(true);
        }else{
            placeInfoText.setVisible(true);
        }
    }

    private boolean showAlert(AlertType alertType, Object object, String estas_seguro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
