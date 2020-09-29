package com.mikadifo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class TriviaController implements Initializable {

    private WindowLoader loader;
    private Stage currentStage;

    @FXML
    private Button bntHome;
    @FXML
    private Button btnContinue;
    @FXML
    private TextArea txtQuestion;
    @FXML
    private Button btnOption_1;
    @FXML
    private Button btnOption_2;
    @FXML
    private Button btnOption_3;
    @FXML
    private Button btnOption_4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void init(Scene scene) {
        btnContinue.getScene().getStylesheets().add("/styles/trivia.css");
    }

    @FXML
    private void onHomeAction(ActionEvent event) { //regresar a galleria y avisar que la proxima vez que entre se le generara una pegunta aleatoria(alert d confirmacion)
    }

    @FXML
    private void onContinueAction(ActionEvent event) {
        if(btnOption1.isFocused()||btnOption2.isFocused()||btnOption3.isFocused()||btnOption4.isFocused()){
            txtQuestion.setText("");
            btnOption_1.setText("");
            btnOption_2.setText("");
            btnOption_3.setText("");
            btnOption_4.setText("");

        } else{ 
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Seleccione una opción");
        }
        // if comprobar que 1 y solo 1 boton esta focused 
            // limpiar todas las opciones y el textflow del enunciado
            // cargar otra aleatoria desde la base de datosc
        // caso contrario avisar con un alert
    }


}
