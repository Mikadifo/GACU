package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.QuestionDB;
import com.mikadifo.models.table_statements.UserDB;
import static com.mikadifo.controllers.WindowFactories.*;
import com.mikadifo.models.function_calls.RandomTrivia;
import com.mikadifo.models.table_statements.User_PlaceDB;
import com.mikadifo.models.table_statements.User_QuestionDB;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            showAlert(AlertType.INFORMATION, null, "Usted no ha visitado ningun lugar");
        }else{
            showNewTrivia(randomTrivia);
            currentScene.getStylesheets().add("/styles/trivia.css");
            currentStage.showAndWait();
        }  
    }


    @FXML
    private void onHomeAction(ActionEvent event) { //regresar a galleria y avisar que la proxima vez que entre se le generara una pegunta aleatoria(alert d confirmacion)
        boolean isOk = showAlert(AlertType.CONFIRMATION, null, "Se generará una pregunta aleatoria la proxima vez que entre, ¿Esta seguro de hacerlo?");

        if (isOk) {
            currentStage.close();
        }
    }

    private boolean showAlert(AlertType alertType, String header, String message) {

        Alert alert = new Alert(alertType);
        alert.setGraphic(new ImageView(new Image("/imgs/logo.png")));
        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);
        
        return alert.showAndWait().orElse(ButtonType.CANCEL)== ButtonType.OK;
    }


    @FXML
    private void onContinueAction(ActionEvent event) {

        if (options.getSelectedToggle() != null) {
            selectedOption = (ToggleButton) options.getSelectedToggle();
            isCorrect=selectedOption.getText().equals(randomTrivia.getCorrectAnswerContent());
            if(isCorrect)                
                showAlert(AlertType.INFORMATION, null, "Correcto");
           else
                showAlert(AlertType.INFORMATION, null, "Incorrecto");
            
            finished=LocalTime.now();
            fillData();
            selectedOption.setSelected(false);            
            randomTrivia = new RandomTrivia(currentUser.getId()).select();
            showNewTrivia(randomTrivia);
        } else {
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Seleccione una opcion");
            alert.showAndWait();
        }

    }

    private void showNewTrivia(RandomTrivia trivia) {
        
        txtQuestion.setText(randomTrivia.getQuestionContent());
  
        List<String> allAnswers;

        allAnswers = trivia.getIncorrectAnswersContents();
        allAnswers.add(trivia.getCorrectAnswerContent());

        Collections.shuffle(allAnswers);
        Iterator<Toggle> optionIterator = options.getToggles().iterator();
	allAnswers.forEach(answer->{
            setOptionsbyAnswers(optionIterator.next(), answer);
        });
        started=LocalTime.now();
    }

    private void setOptionsbyAnswers(Toggle option, String answerContent) {
        ToggleButton op = (ToggleButton) option;
        op.setText(answerContent);
    }
    
    private void fillData(){
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
