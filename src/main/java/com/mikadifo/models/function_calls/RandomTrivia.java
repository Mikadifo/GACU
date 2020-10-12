
package com.mikadifo.models.function_calls;

import com.mikadifo.models.DB_Connection;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RandomTrivia implements FunctionDB{
    
    private DB_Connection dbConnection = new DB_Connection();
    private final String functionName = "random_trivia(?)";
    private ResultSet results;
    private List<RandomTrivia> resultList;

    
    
    private int userId;
    
    private int questionId;
    private String questionContent;
    private int answerId;
    private String correctAnswerContent;
    private List<String> incorrectAnswersContents;

    public RandomTrivia(int userId) { this.userId = userId; }

    private RandomTrivia(int questionId, String questionContent, int answerId, String correctAnswerContent, List<String> incorrectAnswersContents) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.answerId = answerId;
        this.correctAnswerContent = correctAnswerContent;
        this.incorrectAnswersContents = incorrectAnswersContents;
    } 

    public RandomTrivia select() {
	return (selectAll().isEmpty()) ? null : selectAll().get(0);
    }

    @Override
    public List<RandomTrivia> selectAll() {
        resultList = new ArrayList<>();
        
        try {
            dbConnection.buildAndPrepareSelect(functionName);
            dbConnection.getStatement().setInt(1, userId);
            results = dbConnection.executeQuery();
            while (results.next()) {                
                resultList.add(getObjectFromResulSet());
                System.out.println("hola");
            }   
            results.close();
            dbConnection.closeStatement();
        } catch (SQLException e) {
            return null;
        }
        return resultList;
    }

    private RandomTrivia getObjectFromResulSet()
            throws SQLException{
            
            Array z = results.getArray("incorrect_answers_contents");
            String[] zips = (String[])z.getArray();
            return new RandomTrivia(
                    results.getInt("question_id"),
                    results.getString("question_content"),
                    results.getInt("answer_id"),
                    results.getString("correct_answer_content"),
                    new ArrayList(Arrays.asList(zips))
            );
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public int getAnswerId() {
        return answerId;
    }

    public String getCorrectAnswerContent() {
        return correctAnswerContent;
    }

    public List<String> getIncorrectAnswersContents() {
        return incorrectAnswersContents;
    }
    

//    @Override
//    public String toString() {
//        return "RandomTrivia{" + "questionId=" + questionId + ", questionContent=" + questionContent + ", answerId=" + answerId + ", correctAnswerContent=" + correctAnswerContent + ", incorrectAnswersContents=" + incorrectAnswersContents + '}';
//    }
   
}
