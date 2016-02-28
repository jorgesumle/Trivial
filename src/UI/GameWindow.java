/*
 * Copyright (C) 2016 Jorge Maldonado Ventura 
 *
 * Este programa es software libre: usted puede redistruirlo y/o modificarlo
 * bajo los términos de la Licencia Pública General GNU, tal y como está publicada por
 * la Free Software Foundation; ya sea la versión 3 de la Licencia, o
 * (a su elección) cualquier versión posterior.
 *
 * Este programa se distribuye con la intención de ser útil,
 * pero SIN NINGUNA GARANTÍA; incluso sin la garantía implícita de
 * USABILIDAD O UTILIDAD PARA UN FIN PARTICULAR. Vea la
 * Licencia Pública General GNU para más detalles.
 *
 * Usted debería haber recibido una copia de la Licencia Pública General GNU
 * junto a este programa.  Si no es así, vea <http://www.gnu.org/licenses/>.
 */
package UI;


import MinitrivialBásico.Answer;
import MinitrivialBásico.Question;
import MinitrivialBásico.StringFormat;
import MinitrivialBásico.Trivial;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
    private static Answer answer;
    
    private static Text questionText;
    public static void game(){
        grid.setBackground(
                    new Background(
                            new BackgroundImage(
                                    new Image(UI.GameWindow.class.getResource("bubbleBackground3.png").toExternalForm()), 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundPosition.CENTER, 
                                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
        if(turn > 20 && p1Points != p2Points){
            playAgain();
            return;
        }
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(7);
        grid.setVgap(11);
        
        grid.setOnMouseClicked(pass -> {});
        
        
        questions = Question.getQuestions();
        ArrayList<Answer> answers = Answer.getAnswers();

        randomQuestion = (int)(Math.random()*questions.size());

        question = StringFormat.removeSpacesAtTheBeggining(questions.get(randomQuestion).getQuestion());
        String category = StringFormat.formatCategory(questions.get(randomQuestion).getCategory());
        answer = answers.get(randomQuestion);


        //Jugadores, puntos, turno
        Text player1 = new Text(Trivial.player1Name);
        player1.setId("player");
        Text player1Points = new Text(Short.toString(p1Points)/* + " puntos"*/);
        player1Points.setId("points");
        grid.setHalignment(player1Points, HPos.CENTER);

        Text numQuestion = new Text("Turno " + Short.toString(turn));
        numQuestion.setId("points");
        grid.setHalignment(numQuestion, HPos.CENTER);

        Text player2 = new Text(Trivial.player2Name);
        player2.setId("player");
        Text player2Points = new Text(Short.toString(p2Points)/* + " puntos"*/);
        player2Points.setId("points");
        grid.setHalignment(player2Points, HPos.CENTER);
        

        grid.add(player1, 0, 0);
        grid.add(player1Points, 0, 1);

        grid.add(numQuestion, 3, 0);

        grid.add(player2, 7, 0);
        grid.add(player2Points, 7, 1);
        //\\
        input = new TextArea();
        
        
        

        
        Label categoryText = new Label(category);
        grid.setHalignment(categoryText, HPos.CENTER);
        grid.add(categoryText, 3, 2);
        
        questionText = new Text(question);
        questionText.setId("normalText");
        grid.setHalignment(questionText, HPos.CENTER);
        grid.add(questionText, 3, 3);
        
        switch(answer.TYPE_OF_ANSWER){
            case 1: 
                input.setPrefRowCount(1);
                input.setPrefColumnCount(30); //Será de una sola fila
                /*input.setOnKeyPressed((KeyEvent e) -> 
                    {
                        if(e.getCode().equals(KeyCode.ENTER)){
                            checkAnswer(input.getText(), StringFormat.removeSpacesAtTheBeggining(answer.getAnswer()));
                        }
                    }
                );*/
                grid.add(input, 3, 4);
                Button enter = new Button("Aceptar");
                grid.setHalignment(enter, HPos.CENTER);


                enter.setOnAction(e -> 
                    {
                        checkAnswer(input.getText(), StringFormat.removeSpacesAtTheBeggining(answer.getAnswer()));
                    }
                );
                
                grid.add(enter, 3, 5);
                break;
            case 2:
                Button yes = new Button("Sí");
                Button no = new Button("No");
                
                grid.add(yes, 2, 5);
                grid.add(no, 4, 5);
                
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
                grid.setHalignment(option1, HPos.CENTER);
                Button option2 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer2()));
                grid.setHalignment(option2, HPos.CENTER);
                Button option3 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer3()));
                grid.setHalignment(option3, HPos.CENTER);
                Button option4 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer4()));
                grid.setHalignment(option4, HPos.CENTER);
                Button option5 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer5()));
                grid.setHalignment(option5, HPos.CENTER);
                
                grid.add(option1, 3, 4);
                grid.add(option2, 3, 5);
                grid.add(option3, 3, 6);
                grid.add(option4, 3, 7);
                grid.add(option5, 3, 8);
                
                option1.setOnAction(e -> checkAnswer("1", Byte.toString(mAnswer.getCorrectAnswer())));
                option2.setOnAction(e -> checkAnswer("2", Byte.toString(mAnswer.getCorrectAnswer())));
                option3.setOnAction(e -> checkAnswer("3", Byte.toString(mAnswer.getCorrectAnswer())));
                option4.setOnAction(e -> checkAnswer("4", Byte.toString(mAnswer.getCorrectAnswer())));
                option5.setOnAction(e -> checkAnswer("5", Byte.toString(mAnswer.getCorrectAnswer())));
                
                break;
        }
    }
    /**
     * Comprueba cuál es la respuesta correcta y muestra un mensaje por pantalla al usuario
     * informándole de cuál es la respuesta correcta si ha fallado y diciéndole que
     * ha acertado en caso contrario
     * @param userAnswer la respuesta introducida por el usuario
     * @param correctAnswer la respuesta correcta
     */
    private static void checkAnswer(String userAnswer, String correctAnswer) {
        turn++;
        grid.getChildren().clear();
        String message = "";
        if(userAnswer.toLowerCase().equals(correctAnswer.toLowerCase())){
            grid.setBackground(
                    new Background(
                            new BackgroundImage(
                                    new Image(UI.GameWindow.class.getResource("thumbsUpHD.jpg").toExternalForm()), 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundPosition.CENTER, 
                                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
            if(turn % 2 == 1){
                message = "¡Bien hecho, " + Trivial.player2Name + "! Acertaste la pregunta.";
                p2Points++;
            } else{
                message = "¡Bien hecho, " + Trivial.player1Name + "! Acertaste la pregunta.";
                p1Points++;
            }
        } else{
            grid.setBackground(
                    new Background(
                            new BackgroundImage(
                                    new Image(UI.GameWindow.class.getResource("thumbsDownRedHD.jpg").toExternalForm()), 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundPosition.CENTER, 
                                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
            
            if(answer.TYPE_OF_ANSWER == 3){
                //sobreescribo la variable
                correctAnswer = MinitrivialBásico.MultipleAnswer.getAnswerByCorrectAnswer(answer.TYPE_OF_ANSWER, (MinitrivialBásico.MultipleAnswer)(answer));
            } 
            else if(answer.TYPE_OF_ANSWER == 2){
                correctAnswer = "";
            }
            
            if(turn % 2 == 1){
                message = "¡Qué pena, " + Trivial.player2Name + "! Fallaste la pregunta.";
            } else{
                message = "¡Qué pena, " + Trivial.player1Name + "! Fallaste la pregunta.";
            }
            if(!correctAnswer.equals("")){
                message += " La respuesta correcta es «" + StringFormat.removeSpacesAtTheBeggining(correctAnswer) + "».";
            }
        }
        Label result = new Label(message);
        result.setId("normalText");
        grid.setHalignment(result, HPos.CENTER);
        
        Label keepPlaying = new Label("Clica en la ventana o pulsa Enter para continuar jugando.");
        keepPlaying.setId("normalText");
        grid.setHalignment(keepPlaying, HPos.CENTER);


        grid.add(result, 1, 1);
        grid.add(keepPlaying, 1, 3);
        grid.setVisible(true);
        grid.setOnMouseClicked(e -> 
            {
                grid.getChildren().clear();
                game();
            }
        );
        grid.setOnKeyPressed((KeyEvent e) ->
            {
                if(e.getCode().equals(KeyCode.ENTER)){
                    grid.getChildren().clear();
                    game();
                }
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
        //Depurando
        grid.setVisible(true);
        grid.add(playAgain, 1, 0, 2, 1);
        grid.setHalignment(playAgain, HPos.CENTER);
        //Sí
        grid.add(yes, 1, 1);
        grid.setHalignment(yes, HPos.CENTER);
        //\\No
        grid.add(no, 2, 1);
        grid.setHalignment(no, HPos.CENTER);
        //\\
        yes.setOnAction(e -> 
            {
                turn = 1;
                p1Points = 0;
                p2Points = 0;
                Trivial.start(); //No funciona bien del todo.
            }
        );
        no.setOnAction(e -> 
            {
                System.exit(0);
            }
        );
        
    }
}
