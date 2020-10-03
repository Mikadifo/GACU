package com.mikadifo.controllers;

import com.mikadifo.models.db_tables.City;
import com.mikadifo.models.db_tables.User;
import com.mikadifo.models.table_statements.UserDB;
import static java.lang.Character.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnDeleteAccount;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField txtUsername;
    private TextField txtPassword;
    @FXML
    private TextField txtLogin;
    @FXML
    private ComboBox<City> comboCity;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void init(Scene scene, UserDB user) {
        btnUpdate.getScene().getStylesheets().add("/styles/account.css");
        currentUser = user;
        
    }

    @FXML
    private void onUsernameKeyReleased(KeyEvent event) {
        String characterTyped = event.getCharacter();
        if (!characterTyped.isEmpty()) {
            char val = characterTyped.charAt(0);
            
            if (!(isLetterOrDigit(val)|| val == '_' )|| txtUsername.getText().length() > 49) {
                event.consume();
            }
        }
        
    }


    @FXML
    private void onLoginKeyReleased(KeyEvent event) {
        String characterTyped = event.getCharacter();

        if (!characterTyped.isEmpty()) {
            char val = characterTyped.charAt(0);

            if (!isDigit(val) || txtLogin.getText().length() > 9) {
                event.consume();
            }
        }
    }

    @FXML
    private void onCancelClick(ActionEvent event) {
        Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onDeleteAccountClick(ActionEvent event) {
        boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¿Esta seguro que desea eliminar la cuenta?");

        if (isOk) getUserFromView().delete();
        
    }

    @FXML
    private void onUptadeClick(ActionEvent event) {
        boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¿Esta seguro que desea actualizar su cuenta?");
        
        if (isOk) getUserFromView().update();
        
    }

    private UserDB getUserFromView() {
        User user = new User();
        
        user.setUsername(txtUsername.getText());
        user.setPassword(txtPassword.getText());
        user.setLogin(txtLogin.getText());
        user.setCityId(comboCity.getSelectionModel().getSelectedItem().getId());

        return (UserDB) user;

    }

    @FXML
    private void onCityKeyReleased(KeyEvent event) {
    }
    
    private boolean showAlert(Alert.AlertType alertType, String header, String message) {
	Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	return alert.showAndWait().get() == ButtonType.OK;
    }
}
