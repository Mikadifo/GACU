package com.mikadifo.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.stage.Stage;

public enum WindowFactories implements WindowFactory {
    ACCOUNT {
	@Override
	public Window createWindow() {
	    loadView("Account");

	    return (AccountController) loader.getController();
	}
    },

    CHANGE_PASSWORD {
	@Override
	public Window createWindow() {
	    loadView("ChangePassword");

	    return (ChangePasswordController) loader.getController();
	}
    },

    DESCRIPTIONS {
	@Override
	public Window createWindow() {
	    loadView("Descriptions");

	    return (DescriptionsController) loader.getController();
	}
    },

    GALLERY {
	@Override
	public Window createWindow() {
	    loadView("Gallery");

	    return (GalleryController) loader.getController();
	}
    },

    LOGIN {
	@Override
	public Window createWindow() {
	    loadView("LogIn");

	    return (LogInController) loader.getController();
	}
    },

    MAIN_MENU {
	@Override
	public Window createWindow() {
	    loadView("MainMenu");

	    return (MainMenuController) loader.getController();
	}
    },

    SIGNUP {
	@Override
	public Window createWindow() {
	    loadView("SignUp");

	    return (SignUpController) loader.getController();
	}
    },

    TRIVIA {
	@Override
	public Window createWindow() {
	    loadView("Trivia");

	    return (TriviaController) loader.getController();
	}
    };

    public static Stage currentStage;
    public static Scene currentScene;
    private static WindowLoader loader;

    private static void loadView(String viewName) {
	try {
	    loader = new WindowLoader();
	    loader.load(viewName);
	    currentScene = loader.getScene();
	    currentStage = loader.getStage();
	} catch (IOException ex) {
	    Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}
