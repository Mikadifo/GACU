package com.mikadifo.controllers;

import com.mikadifo.models.Roles;
import static com.mikadifo.models.Roles.ADMIN;
import static com.mikadifo.models.Roles.GUEST;
import static com.mikadifo.models.Roles.USER;
import com.mikadifo.models.table_statements.UserDB;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class GalleryController implements Initializable {

    private WindowLoader loader;
    private UserDB currentUser;

    @FXML
    private Button btnExit;
    @FXML
    private Button btnTrivia;
    @FXML
    private AnchorPane logedPane;
    @FXML
    private AnchorPane guestPane;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnAccount;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignup;
    @FXML
    private ScrollPane rootScroll;
    @FXML
    private FlowPane imagesFlowPane;
    @FXML
    private Button backButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	loader = new WindowLoader();
    }
   
    public void init(Scene scene, Roles role, UserDB user) {
        btnTrivia.getScene().getStylesheets().add("/styles/gallery.css");
        loadByRole(role);
	currentUser = user;
    }

    private void loadByRole(Roles role) {
	//if user is null so is guest
        if(role.equals(ADMIN)){
           
        }else if(role.equals(GUEST)){
             btnTrivia.setDisable(true);
                guestPane.setVisible(false);
                rootPane.setTop(logedPane);
                logedPane.setVisible(true);
        } else if(role.equals(USER)){
            
        }
        
    }

    @FXML
    private void onExitAction(ActionEvent event) {
	boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¿Estas seguro que desea salir?");

        if (isOk) System.exit(0);
    }

    @FXML
    private void onTriviaAction(ActionEvent event) {
        try {
	    loader.load("Trivia");
            TriviaController trivia = loader.getController();
            trivia.init(loader.getScene(), currentUser);
	    loader.showAndWait(false);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onMenuAction(ActionEvent event) {

    }
    

    @FXML
    private void onAccountAction(ActionEvent event) {
	try {
            loader.load("Account");
            AccountController account = loader.getController();
	    System.out.println(currentUser.getLogin());
            account.init(loader.getScene(), currentUser);
            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onLoginAction(ActionEvent event) {
        try {
            loader.load("LogIn");
            LogInController login = loader.getController();
            login.init(loader.getScene(), currentUser);
            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    }
    
    @FXML
    private void onSignupAction(ActionEvent event) {
<<<<<<< HEAD
        
=======
        boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "Se le dirigirá a crear una cuenta, ¿está seguro?");

        if (isOk){
        
            try {
                loader.load("SignUp");
                SignUpController account = loader.getController();
                account.init(loader.getScene());
                loader.showAndWait(true);
            } catch (IOException ex) {
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    private boolean showAlert(Alert.AlertType alertType, String header, String message) {
	Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	return alert.showAndWait().get() == ButtonType.OK;
    }

    @FXML
    private void onBackAction(ActionEvent event) {
>>>>>>> e64176cdf92b3df9a17a2425ed5b3d60b2b1e0fe
    }

}
