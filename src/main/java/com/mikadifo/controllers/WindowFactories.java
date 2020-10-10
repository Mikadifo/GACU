package com.mikadifo.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.Stage;

public enum WindowFactories implements WindowFactory {
    GALLERY(null) {
	@Override
	public Window createWindow() {
	    loadView("Gallery");
	    setStage(loader.getStage());

	    return (GalleryController) loader.getController();
	}
    },

    LOGIN(null) {
	@Override
	public Window createWindow() {
	    // TODO Auto-generated method stub
	    return null;
	}
    };

    private Stage stage;
    private static WindowLoader loader;

    private WindowFactories(Stage stage) { this.stage = stage; }

    public Stage getStage() { return this.stage; }

    public void setStage(Stage stage) { this.stage = stage; }

    private static void loadView(String viewName) {
	try {
	    loader = new WindowLoader();
	    loader.load(viewName);
	} catch (IOException ex) {
	    Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}
