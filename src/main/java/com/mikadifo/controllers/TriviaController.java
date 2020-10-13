package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.QuestionDB;
import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.WindowFactories.*;
import com.mikadifo.models.function_calls.RandomTrivia;
import com.mikadifo.models.table_statements.User_PlaceDB;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class TriviaController implements Initializable, Window {

    private UserDB currentUser;
    private Alert alert;
    private RandomTrivia randomTrivia;
    private List<Button> options;
    private boolean estado=true; 

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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	alert = new Alert(Alert.AlertType.INFORMATION);
	addOptionsToList();
        callOption();
    }

    private void addOptionsToList() {
	options = new ArrayList<>();

	options.add(btnOption_1);
	options.add(btnOption_2);
	options.add(btnOption_3);
	options.add(btnOption_4);
    }
    
    public void init(UserDB user) {
        currentUser = user;
	init();
    }

    @Override
    public void init() {
	loadTriviasIfUserHasVisitedPlaces();
	currentScene.getStylesheets().add("/styles/trivia.css");
	currentStage.showAndWait();
    }
    private void callOption(){
        options.forEach(op->{
            op.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
                op.setStyle("-fx-background-color: #A30300");
            });
        });
    }

    private void loadTriviasIfUserHasVisitedPlaces() {
	randomTrivia = new RandomTrivia(currentUser.getId()).select();
        txtQuestion.setText(randomTrivia.getQuestionContent());
	if (randomTrivia == null) {
	    //alert
	} else {
	    showNewTrivia(randomTrivia);
	}
    }
    
    public java.sql.Time HoraActual() {
        java.util.Date utilDate = new java.util.Date();
        long lnMilisegundos = utilDate.getTime();
        java.sql.Time HoraActual = new java.sql.Time(lnMilisegundos);
        return HoraActual;
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
    if(estado){
            
            // cargar otra pregunta aleatoria desde la base de datos   basada en el place id 
        } else  {
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Seleccione una ");
            alert.showAndWait();
        }
    }

   

    private void showNewTrivia(RandomTrivia trivia) {
	
    //setQuestion 
	List<String> allAnswers;
        
        
	allAnswers = trivia.getIncorrectAnswersContents();
	allAnswers.add(trivia.getCorrectAnswerContent());
	
        System.out.println(allAnswers.size());
	Collections.shuffle(allAnswers);
        Iterator<Button> optionIterator = options.iterator();
	allAnswers.forEach(answer->{
            setOptionsbyAnswers(optionIterator.next(), answer);
        });
    }

    private void setOptionsbyAnswers(Button button, String answerContent) {
        button.setText(answerContent);
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

    @FXML
    private void OnMouseBtn_1Clicked(MouseEvent event) {
        estado = true;

    }

    @FXML
    private void OnMouseBtn_2Clicked(MouseEvent event) {
        estado = true;
    }

    @FXML
    private void OnMouseBtn_3Clicked(MouseEvent event) {
        estado = true;
    }

    @FXML
    private void OnMouseBtn_4Clicked(MouseEvent event) {
        estado = true;
    }
}
