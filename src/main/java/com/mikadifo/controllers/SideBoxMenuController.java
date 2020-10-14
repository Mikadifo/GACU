package com.mikadifo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class SideBoxMenuController implements Initializable {

    @FXML
    private VBox sideBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onUserRolesReportAction(ActionEvent event) {
	System.out.println(GalleryController.currentUser.getLogin());
    }

    @FXML
    private void onCompletedQuizzesAction(ActionEvent event) {
    }

    @FXML
    private void onVisitedPlacesAction(ActionEvent event) {
    }

    
}
