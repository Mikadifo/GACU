package com.mikadifo.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author MIKADIFO
 */
public class WindowLoader {
    
    private String viewsPath = "/com/mikadifo/views/?.fxml";

    private FXMLLoader loader;
    private Parent root;
    private Scene scene;
    private Stage stage;

    public void load(String viewName) throws IOException {
        loadViewFile(viewName);
        setScene();
    }

    private void loadViewFile(String viewName) throws IOException {
        viewsPath = viewsPath.replace("?", viewName);
        loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource(viewsPath));
        this.root = loader.load();
    }
    
    private void setScene() {
        stage = new Stage();
        this.scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
    }
    
    public <T> T getController() {
        return loader.getController();
    }

    public Scene getScene() {
        return this.scene;
    }

    public Stage getStage() {
	return this.stage;
    }

    public void showAndWait(boolean wait) {
        if (wait) 
            stage.showAndWait();
        else
            stage.show();
    }
}
