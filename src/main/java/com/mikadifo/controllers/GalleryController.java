package com.mikadifo.controllers;

import com.mikadifo.models.Roles;
import com.mikadifo.models.table_statements.UserDB;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
	//currentUser = new UserDB();
	//currentUser.setCityId(7);
	//currentUser.setLogin("0104640982");
	//currentUser.setUsername("hello_123");
	//currentUser.setPassword("kkck1");
	//currentUser.setRoleId((short) 1);
    }

    private void loadByRole(Roles role) {
	//if user is null so is guest
        switch(role) {
            case ADMIN:
                break;
            case GUEST:
                btnTrivia.setDisable(true);
                guestPane.setVisible(false);
                rootPane.setTop(logedPane);
                logedPane.setVisible(true);

                break;
            case USER:
                break;
            default:
        }
        
    }

    @FXML
    private void onExitAction(ActionEvent event) {
//        boolean isOk = showAlert(AlertType.CONFIRMATION, null, "Estas seguro?");

//        if (isOk) System.exit(0);
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
       try {
            loader = new WindowLoader();
            loader.load("MainMenu");
            MainMenuController menu = loader.getController();
            menu.init(loader.getScene(), currentUser);
            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onAccountAction(ActionEvent event) {
	try {
            loader.load("Account");
            AccountController account = loader.getController();
            account.init(loader.getScene(), currentUser);
            loader.showAndWait(true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onLoginAction(ActionEvent event) {

    }

    @FXML
    private void onSignupAction(ActionEvent event) {
    }

}
