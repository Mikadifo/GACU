package com.mikadifo.controllers;

import com.mikadifo.models.db_tables.City;
import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.UserValidator.*;
import com.mikadifo.models.Roles;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
public class SignUpController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<City> comboCity;
    
    private WindowLoader loader;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void init(Scene scene) {
        scene.getStylesheets().add("/styles/account.css");
        txtUsername.requestFocus();
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        Node currentStag = (Node) event.getSource();
        Stage stage = (Stage) currentStag.getScene().getWindow();
        
        stage.close();
    }

    @FXML
    private void onCreateAction(ActionEvent event) {
        String login = txtLogin.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        System.out.println(comboCity.getSelectionModel().getSelectedIndex());
        //int cityId = comboCity.getSelectionModel().getSelectedItem().getId(); //nullPointer //TODO put the first item Cuenca

        UserDB user = new UserDB();
        user.setLogin(login);
        user.setUsername(username);
        user.setPassword(password);
        //user.setCityId(cityId);
        user.setRoleId((short) 1); //change

        Optional<String> result = isLoginValid() //first must be login fix in sceneBuilder
                .and(isUsernameValid())
                .and(isPasswordValid())
                .and(isCitySelected())
                .and(userNotExists())
                .apply(user);

        if (result.isPresent()) {
            showAlert(AlertType.INFORMATION, null, result.get());
        } else {
            user.insert();
        }
        try {
            loader = new WindowLoader();
            loader.load("LogIn");
            GalleryController gallery = loader.getController();
            gallery.init(loader.getScene(), Roles.GUEST, null);

            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

            if (!(Character.isLetter(val) || Character.isDigit(val)) || txtLogin.getText().length() > 245) {
                event.consume();
            }
        }

    }


    @FXML
    private void onCityKeTyped(KeyEvent event) {
        //filter citites
    }

    @FXML
    private void onLoginTyped(KeyEvent event) {
        String characterTyped = event.getCharacter();

        if (!characterTyped.isEmpty()) {
            char val = characterTyped.charAt(0);

            if (!Character.isDigit(val) || txtLogin.getText().length() > 9) {
                event.consume();
            }
        }
    }
}
