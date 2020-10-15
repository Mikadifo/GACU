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
//	ImageDB img = new ImageDB();
//	img.setPlaceId(                       26  );
//        img.setAuthor(  "Raul Silva"   );
//	img.setDescription(   "Interior del Instituto Tecnológico del Azuay."    );
//        img.setImage(gettttt()); //remeber
//        
//	img.insert();
//        System.exit(0);
    }

//    private static byte[] gettttt() {
//	File file = new File("/Users/MIKADIFO/Documents/ISTA/Ciclo3/ProyectoFinal/GACU_IMGS"
//                + "/EducacionSuperior"
//                + "/ISTA"
//                + "/2.jpg"); 
//            //PNG  png  jpg JPG jpeg
//
//	try (FileInputStream imageInFile = new FileInputStream(file)) {
//	    byte imageData[] = new byte[(int) file.length()];
//	    imageInFile.read(imageData);
//
//	    return Base64.getEncoder().encode(imageData);
//	} catch (FileNotFoundException e) {
//	    System.out.println("Image not found" + e);
//	} catch (IOException ioe) {
//	    System.out.println("Exception while reading the Image " + ioe);
//	}
//
//	return null;
//    }
    
}
