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

    private WindowLoader loader;

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtLogin;
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

    private UserDB getUserFromView() {
        User user = new User();
        
        user.setUsername(txtUsername.getText());
        user.setLogin(txtLogin.getText());
        user.setCityId(comboCity.getSelectionModel().getSelectedItem().getId());

        return (UserDB) user;
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
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
	Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onDeleteAction(ActionEvent event) {
	boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¬øEsta seguro que desea eliminar la cuenta?");

        if (isOk) getUserFromView().delete();
    }

    @FXML
    private void onUpdateAction(ActionEvent event) {
	boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¬øEsta seguro que desea actualizar su cuenta?");
        
        if (isOk) getUserFromView().update();
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
}
