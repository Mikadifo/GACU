package com.mikadifo.controllers;

import com.mikadifo.models.Roles;
import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.UserValidator.*;

import java.util.Optional;
import static com.mikadifo.controllers.WindowFactories.*;
import static com.mikadifo.controllers.MainMenuController.*;
import static java.lang.Character.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class LogInController implements Initializable, Window {

    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtLogin;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void init(UserDB user) {
	if (user != null) setUserInView(user);
	init();
    }

    @Override
    public void init() {
        currentScene.getStylesheets().add("/styles/account.css");
	txtLogin.requestFocus();
	currentStage.showAndWait();
    }

    private void setUserInView(UserDB user) {
	txtLogin.setText(user.getLogin());
	txtPassword.setText(user.getPassword());
    }

    @FXML
    private void onEnterAction(ActionEvent event) {
        String login = txtLogin.getText();
        String password = txtPassword.getText();

	UserDB user = new UserDB();
	user.setLogin(login);
	user.setPassword(password);

	Optional<String> result = isLoginValid()
		.and(isPasswordValid())
		.and(userExists())
		.and(isUserAuthenticated())
		.apply(user);

	if (result.isPresent()) {
	    showAlert(AlertType.INFORMATION, null, result.get());
	} else {
	    currentStage.close();
            
	    user.selectById();            
            GalleryController gallery = (GalleryController) GALLERY.createWindow();
	    gallery.init(Roles.USER, user.getUser());
            
	    isLogedIn = true;
	    closeIfLogedIn();
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
    private void onCancelAction(ActionEvent event) {
	currentStage.close();
        GALLERY.createWindow().init();
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

}
