package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.WindowFactories.*;
import static java.lang.Character.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import com.mikadifo.models.table_statements.CityDB;
import com.mikadifo.models.table_statements.CountryDB;

import static com.mikadifo.controllers.UserValidator.*;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class AccountController implements Initializable, Window {

    private UserDB currentUser;
    private CityDB userCity;
    private CountryDB userCountry;
    private List<CityDB> citiesFromDB;
    private List<CountryDB> countriesFromDB;
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
        userCountry = new CountryDB();
        userCity.selectAll();
        userCountry.selectAll();
        citiesFromDB = userCity.getResults();
        countriesFromDB = userCountry.getResults();
        cities = FXCollections.observableArrayList(citiesFromDB);
        setConverterComboBox();
        comboCity.setItems(cities);
        comboCity.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                comboCity.show();
            }
        });
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

    public void init(UserDB user) {
        currentUser = user;
        userCity.setId(currentUser.getCityId());
        userCity.selectById();
        userCity = userCity.getCity();
        setUserInView(); //si no vale poner al otro init
        init();
    }

    @Override
    public void init() {
        currentScene.getStylesheets().add("/styles/account.css");
        currentStage.showAndWait();
    }

    private void setConverterComboBox() {
        comboCity.setConverter(new StringConverter<CityDB>() {

            @Override
            public String toString(CityDB city) {
                if (city == null) {
                    return "Seleccione";
                }

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

    private void setUserInView() {
        txtLogin.setText(currentUser.getLogin());
        txtUsername.setText(currentUser.getUsername());
        comboCity.getSelectionModel().select(userCity);
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
    private void onCancelAction(ActionEvent event) {
        currentStage.close();
    }

    @FXML
    private void onDeleteAction(ActionEvent event) {
        boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¬øEsta seguro que desea eliminar la cuenta?");

        if (isOk) {
            getUserFromView().delete();

            closeAllStages();

            MAIN_MENU.createWindow().init();
        }

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
                if (isOk) {
                    currentUser.update();
                }
            }
        }
    }

    @FXML
    private void onChangePasswordAction(ActionEvent event) {
        CHANGE_PASSWORD.createWindow().init();
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
        user.setCityId((comboCity.getValue() == null) ? 0 : comboCity.getValue().getId());

        return user;
    }

    private boolean isUsernameDifferent(UserDB newUser) {
        return !currentUser.getUsername().equals(newUser.getUsername());
    }

    private boolean isCityDifferent(UserDB newUser) {
        return currentUser.getCityId() != newUser.getCityId();
    }

    @FXML
    private void onCityKeyReleased(KeyEvent event) {
        String filter = comboCity.getEditor().getText().toUpperCase();
        filteredCities.setPredicate(item -> item.getName().contains(filter));//review
        comboCity.setItems(filteredCities);
    }

}
