package com.mikadifo.controllers;

import static com.mikadifo.controllers.WindowFactories.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainController extends Application {
    
    @Override
    public void start(Stage primaryStage) {
    	MAIN_MENU.createWindow().init();
	primaryStage.setScene(currentScene);
	primaryStage.show();
	MainMenuController.scene = currentScene;
    }
    
    public static void main(String[] args) {
	launch(args);
    }
    
}
