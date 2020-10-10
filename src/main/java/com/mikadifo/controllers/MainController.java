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

    /*private static byte[] gettttt() {*/
	//File file = new File("/Users/MIKADIFO/Documents/ISTA/Ciclo3/ProyectoFinal/GACU_IMGS/Museos/Pumapungo/Pumapungo4jpg.jpg");

	//try (FileInputStream imageInFile = new FileInputStream(file)) {
	    //byte imageData[] = new byte[(int) file.length()];
	    //imageInFile.read(imageData);

	    //return Base64.getEncoder().encode(imageData);
	//} catch (FileNotFoundException e) {
	    //System.out.println("Image not found" + e);
	//} catch (IOException ioe) {
	    //System.out.println("Exception while reading the Image " + ioe);
	//}

	//return null;
    /*}*/
    
}
