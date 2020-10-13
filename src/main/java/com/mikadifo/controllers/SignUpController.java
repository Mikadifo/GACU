package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.CityDB;
import com.mikadifo.models.table_statements.CountryDB;
import com.mikadifo.models.table_statements.RoleDB;
import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.WindowFactories.*;
import static com.mikadifo.controllers.UserValidator.*;
import static java.lang.Character.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.util.StringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class SignUpController implements Initializable, Window {

    private CityDB userCity;
    private CountryDB userCountry;
    private WindowLoader loader;
    private List<CityDB> citiesFromDB;
    private List<CountryDB> countriesFromDB;
    private ObservableList<CityDB> cities;
    private FilteredList<CityDB> filteredCities;

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;
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
	userCountry = new CountryDB();
	userCity.selectAll();
	userCountry.selectAll();
	citiesFromDB = userCity.getResults();
	countriesFromDB = userCountry.getResults();
	cities = FXCollections.observableArrayList(citiesFromDB);
	setConverterComboBox();
	comboCity.setItems(cities);
        filteredCities = new FilteredList<>(cities);
    }

    @Override
    public void init() {
        currentScene.getStylesheets().add("/styles/account.css");
        txtLogin.requestFocus();
	currentStage.showAndWait();
    }

    private void setConverterComboBox() {
	comboCity.setConverter(new StringConverter<CityDB>(){

		@Override
		public String toString(CityDB city) {
		    if (city == null) return "Seleccione";

		    return replaceCountryIdWithName(city);		
		}

		@Override
		public CityDB fromString(String string) {
		    return citiesFromDB
			.stream()
			.filter(city -> replaceCountryIdWithName(city).equals(string))
			.findFirst()
			.orElse(null);
		}
	});
    }

    private String replaceCountryIdWithName(CityDB city) {
	return city.toString()
		.replaceAll("\\(\\d*\\)", getCountryNameById(city.getCountryId()));
    }

    private String getCountryNameById(int id) {
	return countriesFromDB.stream()
		.filter(country -> country.getId() == id)
		.findFirst()
		.map(CountryDB::getName)
		.map(countryName -> "(" + countryName + ")")
		.orElse("N/C");
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        Node currentStag = (Node) event.getSource();
        Stage stage = (Stage) currentStag.getScene().getWindow();

        stage.close();
    }

    @FXML
    private void onCreateAction(ActionEvent event) {
	UserDB userInView = getUserFromView();

	Optional<String> result = isLoginValid() //need to be fixed the login Key typed to accpet '_'
                .and(isUsernameValid())
                .and(isPasswordValid())
		.and(isCitySelected())
                .and(userNotExists()) //need a validation for username already existing
                .apply(userInView);

        if (result.isPresent())
            showAlert(AlertType.INFORMATION, null, result.get());
	else {
	    userInView.insert();
		LogInController login = (LogInController) LOGIN.createWindow();
		login.init(userInView);

	    Node sourceNode = (Node) event.getSource();
	    Stage currentStage = (Stage) sourceNode.getScene().getWindow();
	    currentStage.close();
        }
    }

    private UserDB getUserFromView() {
        UserDB user = new UserDB();
        
	user.setLogin(txtLogin.getText());
        user.setUsername(txtUsername.getText());
	user.setPassword(txtPassword.getText());
	user.setCityId((comboCity.getValue() == null) ? 0: comboCity.getValue().getId());
	user.setRoleId(getUserRoleId());

        return user;
    }

    private short getUserRoleId() {
	RoleDB defaultRole = new RoleDB();
	defaultRole.selectAll();

	return defaultRole.getResults().stream()
		.filter(role -> role.getName().toUpperCase().equals("USER"))
		.map(RoleDB::getId)
		.findFirst()
		.get()
		.shortValue();
    }

    private boolean showAlert(AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

        return alert.showAndWait().get() == ButtonType.OK;
    }

    @FXML
    private void onUsernameKeyTyped(KeyEvent event) {
        String characterTyped = event.getCharacter();

        if (!characterTyped.isEmpty()) {
            char val = characterTyped.charAt(0);

            if (!(isLetterOrDigit(val) || val == '_') || txtUsername.getText().length() > 49) {
                event.consume();

            }
        }
    }

    @FXML
    private void onLoginKeyTyped(KeyEvent event) {
	String characterTyped = event.getCharacter();

        if (!characterTyped.isEmpty()) {
            char val = characterTyped.charAt(0);

            if (!isDigit(val) || txtLogin.getText().length() > 9) {
                event.consume();
            }
        }
    }

    @FXML
    private void onCityKeyReleased(KeyEvent event) {
	String filter = comboCity.getEditor().getText().toUpperCase();
	filteredCities.setPredicate(item -> item.getName().contains(filter));//review
	comboCity.setItems(filteredCities);
    }

    void init(Scene scene, Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
