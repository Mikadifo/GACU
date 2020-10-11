
package com.mikadifo.models.function_calls;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.table_statements.UserDB;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.postgresql.jdbc.PgArray;

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
    private Array incorrectAnswersContents;

    public RandomTrivia(int userId) {
        this.userId = userId;
    }

    public RandomTrivia(int questionId, String questionContent, int answerId, String correctAnswerContent, Array incorrectAnswersContents) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.answerId = answerId;
        this.correctAnswerContent = correctAnswerContent;
        this.incorrectAnswersContents = incorrectAnswersContents;
    }

    public RandomTrivia() {
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
            
            return new RandomTrivia(
                    results.getInt("question_id"),
                    results.getString("question_content"),
                    results.getInt("answer_id"),
                    results.getString("correct_answer_content"),
                    results.getArray("incorrect_answers_contents")
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

    public Array getIncorrectAnswersContents() {
        return incorrectAnswersContents;
    }
    
    

    @Override
    public String toString() {
        return "RandomTrivia{" + "questionId=" + questionId + ", questionContent=" + questionContent + ", answerId=" + answerId + ", correctAnswerContent=" + correctAnswerContent + ", incorrectAnswersContents=" + incorrectAnswersContents + '}';
    }  
    
//    public static void main(String[] args){
//        
//        new RandomTrivia(1).selectAll().forEach(System.out::println);
//    }
   
}
