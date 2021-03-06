package com.mikadifo.controllers;

import com.mikadifo.models.Roles;
import com.mikadifo.models.db_tables.City;
import com.mikadifo.models.db_tables.User;
import com.mikadifo.models.table_statements.UserDB;
import static java.lang.Character.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mikadifo.models.table_statements.CityDB;
import static com.mikadifo.controllers.UserValidator.*;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private WindowLoader loader;
    private CityDB userCity;
    private List<CityDB> citiesFromDB;
    private ObservableList<CityDB> cities;
    private FilteredList<CityDB> filteredCities;

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
    private void onUsernameKeyTyped(KeyEvent event) {
	String characterTyped = event.getCharacter();
        if (!characterTyped.isEmpty()) {
            char val = characterTyped.charAt(0);
            
            if (!(isLetterOrDigit(val)|| val == '_' )|| txtUsername.getText().length() > 49) {
                event.consume();
            }
        }
    }

    @FXML
    private void onCityKeyTyped(KeyEvent event) {
	String filter = comboCity.getEditor().getText().toUpperCase();

	filteredCities.setPredicate(item -> item.getName().contains(filter));
	comboCity.setItems(filteredCities);
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        Node currentStag = (Node) event.getSource();
        Stage stage = (Stage) currentStag.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onDeleteAction(ActionEvent event) {
	boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¬øEsta seguro que desea eliminar la cuenta?");

        if (isOk) getUserFromView().delete();
    }

    @FXML
    private void onUpdateAction(ActionEvent event) {
	UserDB userInView = getUserFromView();

	Optional<String> result = isUsernameValid()
		.and(isCitySelected())
		.apply(userInView);

	if (result.isPresent()) {
	    showAlert(Alert.AlertType.CONFIRMATION, null, result.get());
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

	    if (isNewData) {
		boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¬øEsta seguro que desea actualizar su cuenta?");
		if (isOk) currentUser.update();
	    }
	}
    }

    @FXML
    private void onChangePasswordAction(ActionEvent event) {
        try {
            loader = new WindowLoader();
            loader.load("ChangePassword");
            ChangePasswordController changePasswordController = loader.getController();
            changePasswordController.init(loader.getScene(), null);

            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean showAlert(Alert.AlertType alertType, String header, String message) {
	Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	return alert.showAndWait().get() == ButtonType.OK;
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

}
