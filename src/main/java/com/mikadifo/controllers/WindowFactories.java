package com.mikadifo.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    public static final ObservableList<Stage> openStages = FXCollections.observableArrayList();
    private static final EventHandler<WindowEvent> onHiddenWindow = event -> openStages.remove(currentStage);
    private static final EventHandler<WindowEvent> onShownWindow = event -> openStages.add(currentStage);

    private static void loadView(String viewName) {
        try {
            loader = new WindowLoader();
            loader.load(viewName);
            currentScene = loader.getScene();
            currentStage = loader.getStage();
            registerStage();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void registerStage() {
        currentStage.addEventHandler(WindowEvent.WINDOW_SHOWN, onShownWindow);
        currentStage.addEventHandler(WindowEvent.WINDOW_HIDDEN, onHiddenWindow);
    }

    public static void closeAllStages() {
        openStages.forEach(stage -> {
            stage.removeEventHandler(WindowEvent.WINDOW_HIDDEN, onHiddenWindow);
            stage.close();
        });

        openStages.removeAll(openStages);
    }
}
