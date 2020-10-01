package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.UserValidator.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ChangePasswordController implements Initializable {

    private UserDB currentUser;
    
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnChange;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtOldPassword;
    @FXML
    private TextField txtNewPassword;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void init(Scene scene, UserDB user) {
        btnChange.getScene().getStylesheets().add("/styles/account.css");
        currentUser = user; 
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onChangeAction(ActionEvent event) {
        String login = txtLogin.getText();
        String password = txtOldPassword.getText();
        String newPasword = txtNewPassword.getText();

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
	    user.selectById();

	    user = user.getUser();
	    user.setPassword(newPasword);
	    user.update();

	    showAlert(AlertType.INFORMATION, "Datos:", "La contraseña se actualizó con exito");
	}
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

    private boolean showAlert(AlertType alertType, String header, String message) {
	Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	return alert.showAndWait().get() == ButtonType.OK;
    }

}
