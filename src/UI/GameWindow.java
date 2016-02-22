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
import javafx.scene.control.Label;
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
    private static short turn = 1;
    private static short p1Points = 0;
    private static short p2Points = 0;
    
    private static Text questionText;
    public static void game(){
        grid.setHgap(7);
        grid.setVgap(11);
        
        grid.setOnMouseClicked(pass -> {});
        
        
        questions = EditMode.getQuestions();
        ArrayList<Answer> answers = EditMode.getAnswers();

        randomQuestion = (int)(Math.random()*questions.size());

        question = questions.get(randomQuestion).getQuestion();
        String category = StringFormat.formatCategory(questions.get(randomQuestion).getCategory());
        Answer answer = answers.get(randomQuestion);


        //Jugadores, puntos, turno
        Text player1 = new Text(Trivial.player1Name);
        Text player1Points = new Text(Short.toString(p1Points) + " puntos");

        Text numQuestion = new Text(Short.toString(turn));

        Text player2 = new Text(Trivial.player2Name);
        Text player2Points = new Text(Short.toString(p2Points) + " puntos");

        

        grid.add(player1, 0, 0);
        grid.add(player1Points, 0, 1);

        grid.add(numQuestion, 3, 0);

        grid.add(player2, 7, 0);
        grid.add(player2Points, 7, 1);
        //\\
        input = new TextArea();
        
        
        

        questionText = new Text(question);

        grid.add(new Text(category), 3, 3);
        grid.add(questionText, 3, 4);
        grid.setGridLinesVisible(true);
        
        if(turn > 20 && p1Points != p2Points){
            playAgain();
        }
        switch(answer.TYPE_OF_ANSWER){
            case 1: 
                input.setPrefRowCount(1);
                input.setPrefColumnCount(30); //Será de una sola fila 
                grid.add(input, 3, 5);
                Button enter = new Button("Aceptar");


                enter.setOnAction(e -> 
                    {
                        checkAnswer(input.getText(), StringFormat.removeSpacesAtTheBeggining(answer.getAnswer()));
                    }
                );
                
                grid.add(enter, 3, 6);
                break;
            case 2:
                Button yes = new Button("Sí");
                Button no = new Button("No");
                
                grid.add(yes, 2, 6);
                grid.add(no, 4, 6);
                
                yes.setOnAction(e -> 
                    {
                        checkAnswer("s", answer.getAnswer());
                    }
                );
                no.setOnAction(e -> 
                    {
                        checkAnswer("n", answer.getAnswer());
                    }
                );
                break;
            case 3:
                MinitrivialBásico.MultipleAnswer mAnswer = (MinitrivialBásico.MultipleAnswer)(answer);
                Button option1 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer()));
                Button option2 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer2()));
                Button option3 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer3()));
                Button option4 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer4()));
                Button option5 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer5()));
                
                grid.add(option1, 2, 5);
                grid.add(option2, 4, 5);
                grid.add(option3, 1, 6);
                grid.add(option4, 3, 6);
                grid.add(option5, 5, 6);
                
                option1.setOnAction(e -> checkAnswer("1", Byte.toString(mAnswer.getCorrectAnswer())));
                option2.setOnAction(e -> checkAnswer("2", Byte.toString(mAnswer.getCorrectAnswer())));
                option3.setOnAction(e -> checkAnswer("3", Byte.toString(mAnswer.getCorrectAnswer())));
                option4.setOnAction(e -> checkAnswer("4", Byte.toString(mAnswer.getCorrectAnswer())));
                option5.setOnAction(e -> checkAnswer("5", Byte.toString(mAnswer.getCorrectAnswer())));
                
                break;
        }
    }

    private static void checkAnswer(String userAnswer, String correctAnswer) {
        turn++;
        grid.getChildren().clear();
        String message = "";
        if(userAnswer.equals(correctAnswer)){
            message = "Acertaste la pregunta.";
            if(turn % 2 == 0){
                p2Points++;
            } else{
                p1Points++;
            }
        } else{
            message = "Fallaste la pregunta.";
        }
        Text result = new Text(message);
        
        Text keepPlaying = new Text("Clica en la ventana para continuar jugando.");
        
        grid.add(result, 1, 1);
        grid.add(keepPlaying, 1, 3);
        grid.setVisible(true);
        grid.setOnMouseClicked(e -> 
            {
                grid.getChildren().clear();
                game();
            }
        );
    }
    /**
     * Pregunta a los jugadores si quieren jugar otra vez. Si responden que sí,
     * se iniciará una nueva partida y podrán cambiar de nombre en el juego.
     * En el caso contrario se cerrará la aplicación.
     */
    private static void playAgain(){
        grid.getChildren().clear();
        Label playAgain = new Label("¿Queréis jugar una nueva partida?");
        Button yes = new Button("Sí");
        Button no = new Button("No");
        
        grid.add(playAgain, 1, 0, 2, 1);
        grid.add(yes, 1, 1);
        grid.add(no, 2, 1);

        yes.setOnAction(e -> 
            {
                Trivial.main(new String[]{}); //No funciona.
            }
        );
        no.setOnAction(e -> 
            {
                System.exit(0);
            }
        );
        
    }
}
