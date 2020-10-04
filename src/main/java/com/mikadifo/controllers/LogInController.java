package com.mikadifo.controllers;

import com.mikadifo.models.Roles;
import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.UserValidator.*;

import java.util.Optional;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
public class LogInController implements Initializable {

    private UserDB currentUser;
    private WindowLoader loader;

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
    
    public void init(Scene scene, UserDB user) {
        scene.getStylesheets().add("/styles/account.css");
	if (user != null) setUserInView(user);
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
		//.and(isPasswordValid())
		.and(userExists())
		.and(isUserAuthenticated())
		.apply(user);

	if (result.isPresent()) {
	    showAlert(AlertType.INFORMATION, null, result.get());
	} else {
            try {
                loader = new WindowLoader();
                loader.load("Gallery");
                GalleryController gallery = loader.getController();
                gallery.init(loader.getScene(), Roles.USER, currentUser);
                loader.showAndWait(false);
            } catch (IOException ex) {
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Node currentStag = (Node) event.getSource();
            Stage stage = (Stage) currentStag.getScene().getWindow();
        
            stage.close(); //test if when login close correctly
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
        Node currentStag = (Node) event.getSource();
        Stage stage = (Stage) currentStag.getScene().getWindow();
        
        stage.close();
    }

    @FXML
    private void onLoginKeyTyped(KeyEvent event) {
        String characterTyped = event.getCharacter();
        
        if (!characterTyped.isEmpty()) {
            char val = characterTyped.charAt(0);
            
            if (!Character.isDigit(val) || txtLogin.getText().length() > 9)
                event.consume();
        }
    }

}
