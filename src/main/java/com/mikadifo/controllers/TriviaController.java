package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.WindowFactories.*;
import com.mikadifo.models.function_calls.RandomTrivia;
import com.mikadifo.models.table_statements.User_QuestionDB;
import java.net.URL;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class TriviaController implements Initializable, Window {

    private UserDB currentUser;
    private Alert alert;
    private RandomTrivia randomTrivia;
    private LocalTime started;
    private LocalTime finished;
    private ToggleButton selectedOption;
    private boolean isCorrect;
    @FXML
    private ToggleGroup options;

    @FXML
    private Button bntHome;
    @FXML
    private Button btnContinue;
    @FXML
    private TextArea txtQuestion;
    @FXML
    private ToggleButton toggle1;
    @FXML
    private ToggleButton toggle2;
    @FXML
    private ToggleButton toggle3;
    @FXML
    private ToggleButton toggle4;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert = new Alert(Alert.AlertType.INFORMATION);
    }


    public void init(UserDB user) {
        currentUser = user;
        init();
    }

    @Override
    public void init() {
        loadTriviasIfUserHasVisitedPlaces(); 
    }


    private void loadTriviasIfUserHasVisitedPlaces() {
        randomTrivia = new RandomTrivia(currentUser.getId()).select();
        if (randomTrivia == null){
            showAlert(AlertType.INFORMATION, "Usted no ha visitado ningun lugar");
        }else{
            showNewTrivia(randomTrivia);
            currentScene.getStylesheets().add("/styles/trivia.css");
            currentStage.setOnCloseRequest(event->{
                requestClose();   
                event.consume();
            });
            currentStage.showAndWait(); 
        }  
    }

    @FXML
    private void onHomeAction(ActionEvent event) {
        requestClose(); 
    }
    
    private void requestClose(){
        boolean isOk = showAlert(AlertType.CONFIRMATION,
            "Se generará una pregunta aleatoria la proxima vez que entre, ¿Esta seguro de hacerlo?");
        
            if(isOk) currentStage.close(); 
    }
    
    private boolean showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane(); 
        dialogPane.getStylesheets().add(getClass().getResource("/styles/myDialogs.css").toExternalForm()); 
        
        return alert.showAndWait().orElse(ButtonType.CANCEL)== ButtonType.OK;
    }

    @FXML
    private void onContinueAction(ActionEvent event) {
        if (options.getSelectedToggle() != null) {
            selectedOption = (ToggleButton) options.getSelectedToggle();
            isCorrect=selectedOption.getText().equals(randomTrivia.getCorrectAnswerContent());
            if(isCorrect)      
                showAlert(AlertType.WARNING, "Correcto");
            else
                showAlert(AlertType.ERROR,  
                        "Incorrecto, la respuesta correcta es "
                        + randomTrivia.getCorrectAnswerContent());
            
            finished = LocalTime.now();
            fillData();
            selectedOption.setSelected(false);            
            randomTrivia = new RandomTrivia(currentUser.getId()).select();
            showNewTrivia(randomTrivia);
        } else {
            showAlert(AlertType.INFORMATION, "Debe Seleccionar una opcion");
        }

    }

    private void showNewTrivia(RandomTrivia trivia) {
        txtQuestion.setText(randomTrivia.getQuestionContent());
  
        List<String> allAnswers;

        allAnswers = trivia.getIncorrectAnswersContents();
        allAnswers.add(trivia.getCorrectAnswerContent());

        Collections.shuffle(allAnswers);
        
        Iterator<Toggle> optionIterator = options.getToggles().iterator();
        
	allAnswers.forEach(answer -> {
            setOptionsbyAnswers(optionIterator.next(), answer);
        });
        
        started = LocalTime.now();
    }

    private void setOptionsbyAnswers(Toggle option, String answerContent) {
        ToggleButton op = (ToggleButton) option;
        op.setText(answerContent);
    }
    
    private void fillData() {
        User_QuestionDB quizzResult = new User_QuestionDB();
    
        quizzResult.setUserId(currentUser.getId());
        quizzResult.setQuestionId(randomTrivia.getQuestionId());
        quizzResult.setAnswerId(randomTrivia.getAnswerId());
        quizzResult.setStartedAt(started);
        quizzResult.setFinishedAt(finished);
        quizzResult.setUserAnswer(selectedOption.getText());
        quizzResult.setCorrect(isCorrect);
        
        quizzResult.insert();
    }
    
}
