/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinitrivialBásico;


import java.util.ArrayList;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author DAW15
 */
public class GameWindow {
    public static GridPane game(GridPane grid){
        grid.setHgap(7);
        grid.setVgap(11);
        
        ArrayList<Question> questions = EditMode.getQuestions();
        ArrayList<Answer> answers = EditMode.getAnswers();
            
        int randomQuestion = (int)(Math.random()*questions.size());
        
        String question = questions.get(randomQuestion).getQuestion();
        
        byte p1Points = 0;
        byte p2Points = 0;
        byte numQuestionP1 = 0;
        byte numQuestionP2 = 0;
        
        //Jugadores, puntos, turno
        Text player1 = new Text("Jugador 1");
        Text player1Points = new Text(Byte.toString(p1Points));
        
        Text numQuestion = new Text(Byte.toString(numQuestionP1));
        
        Text player2 = new Text("Jugador 2");
        Text player2Points = new Text(Byte.toString(p2Points));
        
        grid.add(player1, 0, 0);
        grid.add(player1Points, 0, 1);
        
        grid.add(numQuestion, 3, 0);
        
        grid.add(player2, 7, 0);
        grid.add(player2Points, 7, 1);
        //\\
        
        grid.add(new Text(question), 3, 3);
        grid.add(new TextArea(), 3, 4);
        
        
     
        
        
        
        
        update(grid);
        
        return grid;
    }

    private static void update(GridPane grid) {
        
    }
}
