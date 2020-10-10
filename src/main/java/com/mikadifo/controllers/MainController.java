 package com.mikadifo.controllers;

import java.io.File;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import com.mikadifo.models.function_calls.AllImagesByPlace;
import com.mikadifo.models.function_calls.FunctionDB;
import com.mikadifo.models.function_calls.RandomImgForPlaceByCategory;
import com.mikadifo.models.function_calls.RandomImgForCategory;
import com.mikadifo.models.table_statements.ImageDB;

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
	    MainMenuController.scene = scene;
	    menu.init();

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.err.println("Error Loading FMXL = " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
	launch(args);
	//new RandomImgForPlaceByCategory(1).selectAll().forEach(System.out::println);
	//new AllImagesByPlace(2).selectAll().forEach(System.out::println);;
    }

    private static byte[] gettttt() {
	File file = new File("/Users/MIKADIFO/Documents/ISTA/Ciclo3/ProyectoFinal/GACU_IMGS/Museos/Pumapungo/Pumapungo4jpg.jpg");

	try (FileInputStream imageInFile = new FileInputStream(file)) {
	    byte imageData[] = new byte[(int) file.length()];
	    imageInFile.read(imageData);

	    return Base64.getEncoder().encode(imageData);
	} catch (FileNotFoundException e) {
	    System.out.println("Image not found" + e);
	} catch (IOException ioe) {
	    System.out.println("Exception while reading the Image " + ioe);
	}

	return null;
    }
    
}
