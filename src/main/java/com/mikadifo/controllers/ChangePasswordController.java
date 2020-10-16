package com.mikadifo.controllers;

import static com.mikadifo.controllers.GalleryController.currentUser;
import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.UserValidator.*;
import static com.mikadifo.controllers.WindowFactories.*;
import com.mikadifo.models.Roles;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ChangePasswordController implements Initializable, Window {

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
    
    @Override
    public void init() {
	currentScene.getStylesheets().add("/styles/account.css");
	currentStage.showAndWait();
        
    }
    public void init( UserDB user) {
	currentUser = user;
	init();
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
	Node currentStage = (Node) event.getSource();
        Stage stage = (Stage) currentStage.getScene().getWindow();
        stage.close();
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

    private boolean showAlert(AlertType alertType, String header, String message) {
	Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	return alert.showAndWait().get() == ButtonType.OK;
    }

   

}
