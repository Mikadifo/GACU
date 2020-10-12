package com.mikadifo.controllers;

import com.mikadifo.models.Roles;
import com.mikadifo.models.db_tables.User_Place;
import com.mikadifo.models.function_calls.RandomTrivia;
import com.mikadifo.models.table_statements.QuestionDB;
import com.mikadifo.models.table_statements.UserDB;
import com.mikadifo.models.table_statements.User_PlaceDB;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class TriviaController implements Initializable {

    private UserDB currentUser;
    private WindowLoader loader;
    private Stage currentStage;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private final RandomTrivia trivia = new RandomTrivia(currentUser.getId());

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
//        txtQuestion.setText(trivia.selectAll().get(2));
    }

    @FXML
    private void onHomeAction(ActionEvent event) { 
	    boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "Se generará una pregunta aleatoria la proxima vez que entre, ¿Esta seguro de hacerlo?");

	    if (isOk) {
            try {
                loader = new WindowLoader();
                loader.load("Gallery");
                GalleryController gallery = loader.getController();
                gallery.init(loader.getScene(), Roles.USER, currentUser);
    
                loader.showAndWait(true);
            } catch (IOException ex) {
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean showAlert(Alert.AlertType alertType, String header, String message) {
	Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	return alert.showAndWait().get() == ButtonType.OK;
    }
 private void borrarCampos(){
    txtQuestion.setText("");
    btnOption_1.setText("");
    btnOption_2.setText("");
    btnOption_3.setText("");
    btnOption_4.setText(""); 
 }
   private void botones() {
           
        Group g = new Group();
        g.getChildren().add(btnOption_1);
        g.getChildren().add(btnOption_2);
        g.getChildren().add(btnOption_3);
        g.getChildren().add(btnOption_4);
    }
   private void onbtnOption_1Action(ActionEvent event) {
   botones();
       btnOption_1.applyCss();
          }
   
 
    @FXML
    private void onContinueAction(ActionEvent event) {
          botones();
        
        if (btnOption_1.isPressed()||btnOption_2.isPressed()||btnOption_3.isPressed()||btnOption_4.isPressed()){
            // cargar otra pregunta aleatoria desde la base de datos   basada en el place id    
        }else{
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Seleccione una opción");
            alert.showAndWait();
        }

       
    }

    private void showNewTrivia() {
        
//        //TABLES: Types, Questions, Answers, Question_Answer
//            /////////
//            //if place_id exist in table User_Places by userId (Si el usuario ha visitado algun lugar)
////            User_PlaceDB visitedPlaces = new User_PlaceDB();
////            List<User_PlaceDB> userVisitedPlaces;
////            List<QuestionDB> questions;
////
////            visitedPlaces.selectAll();
////            userVisitedPlaces = getVisitedPlacesByUserId(currentUser.getId()); //method in visited places todo is get place_id by user_id
//
//            if (userVisitedPlaces != null) { 
//                //Obtener un placeid aleatorio de la tabla User_Places;
//                int randomPlaceId = getRandomFromArray(userVisitedPlaces);
//                QuestionDB questionDB = new QuestionDB();
//                questionDB.selectAll();
//                questions = questionDB.getResults();
//                //Obtener una pregunta aleatoria con el mismo place_id anterior ^
//                String question = "";//set the contetn question here
//                short typeId = 0;//sets the question type id here
//                //Obtener el answer_id que corresponde al question id de la tabla QUESTION_ASNWERS
//                String correctAnswer = "";//set the content answer hereA
//                //select * from Answer join types using(type_id) where Answer.type_id != typeId and Answer.type_id (3 result only)
//                //array con todas las 4 respuestas here
//                //[1][2][3][4]
//                //mezclar [4][1][2][3]
//                //for botones asginar el shuffled array
//            //else alert de que aun no ha visitado un lugar
//            } else {
//                alert.setHeaderText(null);
//                alert.setTitle("Aviso");
//                alert.setContentText("Aún no ha visitado ningun lugar. No puede acceder a la trivia");
//                alert.showAndWait();
//            }
    }
//    private String getRandomIncorrectContentAnswere(){
//            
//    }
    
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