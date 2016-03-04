/*
 * Quaestiones Copyright (C) 2016 Jorge Maldonado Ventura 
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


import Console.Answer;
import Console.Question;
import Console.StringFormat;
import Console.Quaestiones;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;


/**
 * Todo lo relativo a la ventana donde se desarrolla la acción del videojuego.
 * @author DAW15
 */
public class GameWindow {
    private static TextArea input;
    private static int randomQuestion;
    private static String question;
    private static ArrayList<Question> questions;
    protected static GridPane grid = Quaestiones.grid;
    private static short turn = 1;
    private static short p1Points = 0;
    private static short p2Points = 0;
    private static Answer answer;
    
    
    private static Text questionText;
    /**
     * Es la ventana donde se desarrolla la acción del juego. Aquí aparecen los nombres de los
     * jugadores, el número del turno, los puntos que tienen, las preguntas y sus categorías. Se resalta el
     * nombre del jugador al que le toca contestar a una pregunta. Para contestarla pulsará un
     * botón (en el caso de las preguntas simples deberá hacerlo tras escribir la respuesta
     * en el área de texto).
     */
    public static void game(){
        /*gameContainer.setCenter(grid);
        
        VBox smallMenu = new VBox();
        Button back = new Button("Volver el menú principal");
        back.setOnAction(e -> GameMenus.createMenu());
        Button exit = new Button("Salir");
        exit.setOnAction(e -> System.exit(0));
        
        smallMenu.getChildren().addAll(back, exit);
        gameContainer.setBottom(smallMenu);*/
        
        grid.setBackground(
                    new Background(
                            new BackgroundImage(
                                    new Image("bubbleBackground.png"), 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundPosition.CENTER, 
                                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
        
        
        if(turn > 20 && p1Points != p2Points){
            playAgain();
            return;
        }
        grid.getStyleClass().add("grid");
        grid.setAlignment(Pos.CENTER);
        
        questions = Question.getQuestions();
        if(questions.isEmpty()){
            String errorMessage = "No existe ninguna pregunta ni respuesta. Debes crearlas para poder jugar al Trivial.\n"
                    + "Para ello escribe en la consola «Trivial e». Entrarás al modo de edición.\n"
                    + "Entonces selecciona la opción «Añadir una pregunta» (introduce «1»). Con esta opción\n"
                    + "podrás crear preguntas y sus respectivas respuestas.\n";
            grid.add(new Text(errorMessage), 0, 0);
            Scene game = new Scene(grid, 700, 500);
            Quaestiones.stage.setScene(grid.getScene());
        }
        ArrayList<Answer> answers = Answer.getAnswers();

        randomQuestion = (int)(Math.random()*questions.size());

        question = StringFormat.removeSpacesAtTheBeggining(questions.get(randomQuestion).getQuestion());
        
        byte categoryByte = questions.get(randomQuestion).getCategory();
        String category = StringFormat.formatCategory(categoryByte);
        answer = answers.get(randomQuestion);


        //Jugadores, puntos, turno
        Text player1 = new Text(Quaestiones.player1Name);
        player1.setId("player");
        Text player1Points = new Text(Short.toString(p1Points)/* + " puntos"*/);
        player1Points.setId("points");
        grid.setHalignment(player1Points, HPos.CENTER);

        Text numQuestion = new Text("Turno " + Short.toString(turn));
        numQuestion.setId("points");
        grid.setHalignment(numQuestion, HPos.CENTER);

        Text player2 = new Text(Quaestiones.player2Name);
        player2.setId("player");
        Text player2Points = new Text(Short.toString(p2Points)/* + " puntos"*/);
        player2Points.setId("points");
        grid.setHalignment(player2Points, HPos.CENTER);
        
        
        if(turn % 2 == 0){
            player2.setId("playing");
        } else{
            player1.setId("playing");
        }
        
        grid.add(player1, 0, 0);
        grid.add(player1Points, 0, 1);

        grid.add(numQuestion, 3, 0);

        grid.add(player2, 7, 0);
        grid.add(player2Points, 7, 1);
        //\\
        input = new TextArea();
        
        
        

        
        Label categoryText = new Label(category);
        switch(categoryByte){
            case 1: 
                categoryText.setId("Geografia");
                break;
            case 2:
                categoryText.setId("Cine");
                break;
            case 3:
                categoryText.setId("Historia");
                break;
            case 4: 
                categoryText.setId("Arte");
                break;
            case 5:
                categoryText.setId("Ciencias");
                break;
            case 6:
                categoryText.setId("Deportes");
                break;
        }
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
                input.setOnKeyPressed((KeyEvent e) -> 
                    {
                        if(e.getCode().equals(KeyCode.ENTER)){
                            checkAnswer(input.getText(), StringFormat.removeSpacesAtTheBeggining(answer.getAnswer()));
                        }
                    }
                );
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
                Console.MultipleAnswer mAnswer = (Console.MultipleAnswer)(answer);
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
     * Comprueba cuál es la respuesta correcta y muestra un mensaje por pantalla (acompañado
     * de sonido e imágenes) al usuario
     * informándole de cuál es la respuesta correcta si ha fallado y diciéndole que
     * ha acertado en caso contrario.
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
                                    new Image("thumbsUpHD.jpg"), 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundPosition.CENTER, 
                                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
            if(GameMenus.isSoundOn){
                MediaPlayer applause = new MediaPlayer(new Media(Paths.get("src/applause.wav").toUri().toString()));
                applause.play();
            }
            if(turn % 2 == 1){
                message = "¡Bien hecho, " + Quaestiones.player2Name + "! Acertaste la pregunta.";
                p2Points++;
            } else{
                message = "¡Bien hecho, " + Quaestiones.player1Name + "! Acertaste la pregunta.";
                p1Points++;
            }
        } else{
            grid.setBackground(
                    new Background(
                            new BackgroundImage(
                                    new Image("thumbsDownRedHD.jpg"), 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundPosition.CENTER, 
                                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
            if(GameMenus.isSoundOn){
                MediaPlayer failSound = new MediaPlayer(new Media(Paths.get("src/fail.mp3").toUri().toString()));
                failSound.play();
            }
            if(answer.TYPE_OF_ANSWER == 3){
                //sobreescribo la variable
                correctAnswer = Console.MultipleAnswer.getAnswerByCorrectAnswer((Console.MultipleAnswer)(answer));
            } 
            else if(answer.TYPE_OF_ANSWER == 2){
                correctAnswer = "";
            }
            
            if(turn % 2 == 1){
                message = "¡Qué pena, " + Quaestiones.player2Name + "! Fallaste la pregunta.";
            } else{
                message = "¡Qué pena, " + Quaestiones.player1Name + "! Fallaste la pregunta.";
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
     * Pregunta a los jugadores si quieren jugar otra vez. Si responden que sí (pulsando el botón «Sí»),
     * se iniciará una nueva partida;
     * en el caso contrario se cerrará el programa.
     */
    private static void playAgain(){
        grid.getChildren().clear();
        Text winner = new Text("El ganador es " + getWinner() + ".");
        winner.setStyle("-fx-font-size: 16px;");

        
        Label playAgain = new Label("¿Queréis jugar una nueva partida?");
        Button yes = new Button("Sí");
        Button no = new Button("No");
        
        grid.add(winner, 0, 0);
        grid.setHalignment(winner, HPos.CENTER);
        
        grid.add(playAgain, 0, 1, 2, 1);
        grid.setHalignment(playAgain, HPos.CENTER);
        //Sí
        grid.add(yes, 0, 2);
        grid.setHalignment(yes, HPos.CENTER);
        //\\No
        grid.add(no, 1, 2);
        grid.setHalignment(no, HPos.CENTER);
        //\\
        yes.setOnAction(e -> 
            {
                turn = 1;
                p1Points = 0;
                p2Points = 0;
                EnterNames.enterNames();
            }
        );
        no.setOnAction(e -> 
            {
                GameMenus.createMenu();
            }
        );
    }
    /**
     * Obtiene el nombre del ganador
     * @return el nombre del jugador que ha conseguido más puntos.
     */
    private static String getWinner(){
        String winner; 
        if(p1Points > p2Points){
            winner = Quaestiones.player1Name;
        } else if(p2Points > p1Points){
            winner = Quaestiones.player2Name;
        } else{ //Nunca debería ocurrir esto.
            winner = "ninguno";
        }
        return winner;
    }
}
