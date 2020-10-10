package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.QuestionDB;
import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.WindowFactories.*;
import com.mikadifo.models.table_statements.User_PlaceDB;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class TriviaController implements Initializable, Window {

    private UserDB currentUser;
    private Alert alert;

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
	alert = new Alert(Alert.AlertType.INFORMATION);
    }
    
    public void init(Scene scene, UserDB user) {
        currentUser = user;
	init();
    }

    @Override
    public void init() {
	currentScene.getStylesheets().add("/styles/trivia.css");
	currentStage.showAndWait();
    }

    @FXML
    private void onHomeAction(ActionEvent event) { //regresar a galleria y avisar que la proxima vez que entre se le generara una pegunta aleatoria(alert d confirmacion)
	boolean isOk = showAlert(AlertType.CONFIRMATION, null, "Se generará una pregunta aleatoria la proxima vez que entre, ¿Esta seguro de hacerlo?");

	if (isOk) currentStage.close();
    }

    private boolean showAlert(AlertType alertType, String header, String message) {
	    Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	    return alert.showAndWait().get() == ButtonType.OK;
    }

    @FXML
    private void onContinueAction(ActionEvent event) {
        if(btnOption_1.isFocused()||btnOption_2.isFocused()||btnOption_3.isFocused()||btnOption_4.isFocused()){
	    txtQuestion.clear();
            btnOption_1.setText(null);
            btnOption_2.setText(null);
            btnOption_3.setText(null);
            btnOption_4.setText(null);
           //Obtener una pregunta aleatoria basada en el place id que ha visitado el usuario

        } else { 
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Seleccione una opción");
            alert.showAndWait();
        }

        // if comprobar que 1 y solo 1 boton esta focused 
            // limpiar todas las opciones y el textflow del enunciado


            // cargar otra aleatoria desde la base de datosc
        // caso contrario avisar con un alert
    }

    private void showNewTrivia() {
        //TABLES: Types, Questions, Answers, Question_Answer
            /////////
            //if place_id exist in table User_Places by userId (Si el usuario ha visitado algun lugar)
            User_PlaceDB visitedPlaces = new User_PlaceDB();
            List<User_PlaceDB> userVisitedPlaces;
            List<QuestionDB> questions;

            visitedPlaces.selectAll();
            userVisitedPlaces = getVisitedPlacesByUserId(currentUser.getId()); //method in visited places todo is get place_id by user_id

            if (userVisitedPlaces != null) { 
                //Obtener un placeid aleatorio de la tabla User_Places;
                int randomPlaceId = getRandomFromArray(userVisitedPlaces);
                QuestionDB questionDB = new QuestionDB();
                questionDB.selectAll();
                questions = questionDB.getResults();
                //Obtener una pregunta aleatoria con el mismo place_id anterior ^
                String question = "";//set the contetn question here
                short typeId = 0;//sets the question type id here
                //Obtener el answer_id que corresponde al question id de la tabla QUESTION_ASNWERS
                String correctAnswer = "";//set the content answer hereA
                //select * from Answer join types using(type_id) where Answer.type_id != typeId and Answer.type_id (3 result only)
                //array con todas las 4 respuestas here
                //[1][2][3][4]
                //mezclar [4][1][2][3]
                //for botones asginar el shuffled array
            //else alert de que aun no ha visitado un lugar
            } else {
                alert.setHeaderText(null);
                alert.setTitle("Aviso");
                alert.setContentText("Aún no ha visitado ningun lugar. No puede acceder a la trivia");
                alert.showAndWait();
            }
    }

    private List<User_PlaceDB> getVisitedPlacesByUserId(int userId) {
        return null;
        //TODO
    }

    private int getRandomFromArray(List<?> array) {
        return 0;
        //TODO
    }

    private int generarNumeroRandom(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1) + min);
    }

}
