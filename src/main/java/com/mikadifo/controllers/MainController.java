 package com.mikadifo.controllers;

import java.io.IOException;

import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.UserValidator.*;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController extends Application {
    
    private final String MAIN_MENU = "/com/mikadifo/views/MainMenu.fxml";

    private FXMLLoader loader;
    private Pane window;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        try {            
            loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(MAIN_MENU));
            
            window = loader.load();
            scene = new Scene(window);
            MainMenuController menu = (MainMenuController) loader.getController();
            menu.init(scene);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.err.println("Error Loading FMXL = " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
	UserDB user = new UserDB();
	user.setUsername("");

	Optional<String> result = 
                userNameIsValid()
	    .apply(user);

        if (result.isPresent())
            System.out.println(result.get());
        else
            System.out.println("Now you are logged in!!!");
        //launch(args);
    }
    
}
