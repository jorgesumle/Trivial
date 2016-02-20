/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import MinitrivialBásico.Answer;
import MinitrivialBásico.EditMode;
import MinitrivialBásico.Question;
import MinitrivialBásico.StringFormat;
import MinitrivialBásico.Trivial;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author DAW15
 */
public class GameWindow {
    private static TextArea input;
    private static int randomQuestion;
    private static String question;
    private static ArrayList<Question> questions;
    private static GridPane grid = Trivial.grid;
    
    private static Text questionText;
    public static void game(){
        grid.setHgap(7);
        grid.setVgap(11);
        
        questions = EditMode.getQuestions();
        ArrayList<Answer> answers = EditMode.getAnswers();
            
        randomQuestion = (int)(Math.random()*questions.size());
        
        question = questions.get(randomQuestion).getQuestion();
        String category = StringFormat.formatCategory(questions.get(randomQuestion).getCategory());
        
        byte p1Points = 0;
        byte p2Points = 0;
        byte numQuestionP1 = 1;
        byte numQuestionP2 = 1;
        
        //Jugadores, puntos, turno
        Text player1 = new Text(Trivial.player1Name);
        Text player1Points = new Text(Byte.toString(p1Points) + " puntos");
        
        Text numQuestion = new Text(Byte.toString(numQuestionP1));
        
        Text player2 = new Text(Trivial.player2Name);
        Text player2Points = new Text(Byte.toString(p2Points) + " puntos");
        
        Button enter = new Button("Aceptar");
        
        
        enter.setOnAction(e -> 
            {
                checkAnswer();
            }
        );
        
        grid.add(player1, 0, 0);
        grid.add(player1Points, 0, 1);
        
        grid.add(numQuestion, 3, 0);
        
        grid.add(player2, 7, 0);
        grid.add(player2Points, 7, 1);
        //\\
        input = new TextArea();
        input.setPrefRowCount(1);
        input.setPrefColumnCount(30); //Será de una sola fila 
        
        
        questionText = new Text(question);
        
        grid.add(new Text(category), 3, 3);
        grid.add(questionText, 3, 4);
        grid.add(input, 3, 5);
        grid.add(enter, 3, 6);
        
        grid.setVisible(true);

    }

    private static void checkAnswer() {
        randomQuestion = (int)(Math.random()*questions.size());
        question = questions.get(randomQuestion).getQuestion();
    }
}
