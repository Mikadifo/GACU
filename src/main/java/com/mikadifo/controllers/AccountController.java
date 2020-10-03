package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.CityDB;
import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.UserValidator.*;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class AccountController implements Initializable {

    private UserDB currentUser;
    private CityDB userCity;
    private List<CityDB> citiesFromDB;
    private ObservableList<CityDB> cities;
    private FilteredList<CityDB> filteredCities;

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
	setConverterComboBox();
	comboCity.setItems(cities);
        filteredCities = new FilteredList<>(cities);
   }
    
    public void init(Scene scene, UserDB user) {
	scene.getStylesheets().add("/styles/account.css");
        currentUser = user;
	userCity.setId(currentUser.getCityId());
	userCity.selectById();
	userCity = userCity.getCity();
	setUserInView();
    }

    private void setConverterComboBox() {
	comboCity.setConverter(new StringConverter<CityDB>(){

		@Override
		public String toString(CityDB city) {
		    if (city == null) return "Seleccione";

		    return city.toString();		
		}

		@Override
		public CityDB fromString(String string) {
		    return citiesFromDB
			.stream()
			.filter(city -> city.toString().equals(string))
			.findFirst().orElse(null);
		}
	});
    }

    private void setUserInView() {
	txtLogin.setText(currentUser.getLogin());
	txtUsername.setText(currentUser.getUsername());
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
        UserDB userInView = getUserFromView();

	Optional<String> result = isUsernameValid()
		.and(isCitySelected())
		.apply(userInView);

	if (result.isPresent()) {
	    System.out.println("RRR = " + result.get());
	    //Data of user in view is incorrect, so need to showAlert with result
	} else {
	    boolean isNewData = false;

	    if (isUsernameDifferent(userInView)) {
		isNewData = true;
		currentUser.setUsername(userInView.getUsername());
	    }
	    if (isCityDifferent(userInView)) {
		isNewData = true;
		currentUser.setCityId(userInView.getCityId());
	    }

	    if (isNewData) currentUser.update();
	}

	
    }

    private UserDB getUserFromView() {
        UserDB user = new UserDB();
        
	user.setLogin(txtLogin.getText());
        user.setUsername(txtUsername.getText());
	user.setCityId((comboCity.getValue() == null) ? 0: comboCity.getValue().getId());

        return user;
    }

    private boolean isUsernameDifferent(UserDB newUser) {
	return ! currentUser.getUsername().equals(newUser.getUsername());
    }

    private boolean isCityDifferent(UserDB newUser) {
	return currentUser.getCityId() != newUser.getCityId();
    }

    @FXML
    private void onCityKeyReleased(KeyEvent event) {
	String filter = comboCity.getEditor().getText().toUpperCase();

	filteredCities.setPredicate(item -> item.getName().contains(filter));
	comboCity.setItems(filteredCities);
    }
}
