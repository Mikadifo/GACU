/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikadifo.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import com.mikadifo.models.table_statements.ImageDB;
import com.mikadifo.models.table_statements.PlaceDB;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class DescriptionsController implements Initializable {

    public static boolean imagesButtonIsPressed;

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

    private static PlaceDB selectedPlace;
    private ImageDB selectedImg;
    @FXML
    private ImageView imgView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void init(PlaceDB place) {
	selectedPlace = place;
	rootPane.setCenter(placeInfoText);
	rootPane.setBottom(placeBottomPane);
	placeBottomPane.setVisible(true);
	placeInfoText.setVisible(true);
	setPlaceInView();
    }

    public void init(ImageDB img) {
	selectedImg = img;
	rootPane.setCenter(imageCenterPane);
	rootPane.setBottom(imageBottomPane);
	imageBottomPane.setVisible(true);
	imageCenterPane.setVisible(true);
	setImageInView();
    }

    private void setPlaceInView() {
	title.setText(selectedPlace.getName());
	Text placeInfo = new Text(selectedPlace.getInfo());
	placeInfoText.getChildren().add(placeInfo);
    }

    private void setImageInView() {
	title.setText(selectedPlace.getName());
	imgView.setImage(this.getImage());
	imageCenterPane.getChildren().add(imgView);
    }

    private Image getImage() {
	byte[] decodedImg;
	decodedImg = Base64.getDecoder().decode(selectedImg.getImage());
	ByteArrayInputStream array = new ByteArrayInputStream(decodedImg);

	return new Image(array);
    }
    
    @FXML
    private void onImagesAction(ActionEvent event) {
	imagesButtonIsPressed = true;
	Node btn = (Node) event.getSource();
	Stage cse = (Stage) btn.getScene().getWindow();
	cse.close();
	//need other button to set in false the pressed and need to disable the close operation in x window
    }

    
}
