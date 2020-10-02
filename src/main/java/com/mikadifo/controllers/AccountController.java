package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.CityDB;
import com.mikadifo.models.table_statements.UserDB;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class AccountController implements Initializable {

    private UserDB currentUser;
    private CityDB userCity;
    private ObservableList<CityDB> cities;
    private List<CityDB> citiesFromDB;

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnDeleteAccount;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtLogin;
    @FXML
    private ComboBox<CityDB> comboCity;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	userCity = new CityDB();
	userCity.selectAll();
	citiesFromDB = userCity.getResults();
	cities = FXCollections.observableArrayList(citiesFromDB);
    }
    
    public void init(Scene scene, UserDB user) {
	scene.getStylesheets().add("/styles/account.css");
        currentUser = user;
	userCity.setId(currentUser.getCityId());
	userCity.selectById();
	userCity = userCity.getCity();
	setUserInView();
    }

    private void setUserInView() {
	txtLogin.setText(currentUser.getLogin());
	txtUsername.setText(currentUser.getUsername());
	comboCity.setItems(cities);
	comboCity.getSelectionModel().select(userCity);
    }

    @FXML
    private void onUsernameKeyReleased(KeyEvent event) {
    }


    @FXML
    private void onLoginKeyReleased(KeyEvent event) {
    }

    @FXML
    private void onCancelClick(ActionEvent event) {
        Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onDeleteAccountClick(ActionEvent event) {
        getUserFromView().delete();
    }

    @FXML
    private void onUptadeClick(ActionEvent event) {
        UserDB userV = getUserFromView();
	System.out.println("userV = " + userV.getUsername());
    }

    private UserDB getUserFromView() {
        UserDB user = new UserDB();
        
        //user.setLogin(txtLogin.getText());
        user.setUsername(txtUsername.getText());
        //user.setCityId(comboCity.getSelectionModel().getSelectedItem().getId()); //need cities in the database

        return user;
    }

    @FXML
    private void onCityKeyReleased(KeyEvent event) {
    }
}
