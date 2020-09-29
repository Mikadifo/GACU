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

    UserDB currentUser;
    private WindowLoader loader;
    private Stage currentStage;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

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
    
    public void init(Scene scene, UserDB user) {
        btnContinue.getScene().getStylesheets().add("/styles/trivia.css");
        currentUser = user;
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
            List<User_Places> userVisitedPlaces;
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

    private List<User_PlaceDB> getVisitedPlacesByUserId() {
        //TODO
    }

    private int getRandomFromArray(List<?> array) {
        //TODO
    }

    private void mezlcarOpcionesBotones() {
        String[] opcionesRespuesta = copiarArray(respuesta);
        int[] posicionesRadnom = generarNumerosRandom(0, (opcionesRespuesta.length - 1));
        btnOption_1.setText(opcionesRespuesta[posicionesRadnom[0]]);
        btnOption_2.setText(opcionesRespuesta[posicionesRadnom[1]]);
        btnOption_3.setText(opcionesRespuesta[posicionesRadnom[2]]);
        btnOption_4.setText(opcionesRespuesta[posicionesRadnom[3]]);
    }

    private int[] generarNumerosRandom(int min, int max) {
        String nums = String.valueOf(generarNumeroRandom(min, max));
        String[] numsArray = new String[4];
        for (int i = 1; i < numsArray.length; i++) {
            do {
                String num = String.valueOf(generarNumeroRandom(min, max));
                if (!nums.contains(num)) {
                    nums += ("," + num);
                    break;
                }
            } while (true);
        }
        numsArray = nums.split(",");
        return toIntArray(numsArray);
    }

    private int generarNumeroRandom(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1) + min);
    }

}
