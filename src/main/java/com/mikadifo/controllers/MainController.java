package com.mikadifo.controllers;

import static javafx.application.Application.launch;
import java.io.IOException;
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
        launch(args);
    }
    
}
