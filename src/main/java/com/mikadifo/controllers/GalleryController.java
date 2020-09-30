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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class GalleryController implements Initializable {

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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void init(Scene scene, Roles role, UserDB user) {
        btnTrivia.getScene().getStylesheets().add("/styles/gallery.css");
        loadByRole(role);
        currentUser = user;
    }

    private void loadByRole(Roles role) {
        switch(role) {
            case ADMIN:
                break;
            case GUEST:
                btnTrivia.setDisable(true);
                logedPane.setVisible(false);
                rootPane.setTop(guestPane);
                guestPane.setVisible(true);

                break;
            case USER:
                break;
            case CONTRIBUTOR:
                break;
            case CREATOR:
                break;
            default:
        }
        
    }

    @FXML
    private void onExitAction(ActionEvent event) {
    }

    @FXML
    private void onTriviaAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(GalleryController.class.getResource("/com/mikadifo/views/Trivia.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            TriviaController trivia = (TriviaController) loader.getController();
            trivia.init(scene, currentUser);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            Stage currentStage = (Stage) btnTrivia.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onMenuAction(ActionEvent event) {

    }

    @FXML
    private void onAccountAction(ActionEvent event) {
    }

    @FXML
    private void onLoginAction(ActionEvent event) {
    }

    @FXML
    private void onSignupAction(ActionEvent event) {
    }

}
