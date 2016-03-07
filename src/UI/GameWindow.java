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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;


/**
 * Todo lo relativo a la ventana donde se desarrolla la acción del videojuego.
 * @author DAW15
 */
public class GameWindow {
    private static String question;
    private static ArrayList<Question> questions;
    /**
     * El panel donde se ubican los elementos del juego.
     */
    protected static GridPane grid = EnterNames.grid;
    private static short turn = 1;
    private static short p1Points = 0;
    private static short p2Points = 0;
    private static Answer answer;
    /**
     * Las preguntas que se usarán en durante la partida.
     */
    protected static ArrayList<Answer> answers = Answer.getAnswers();
    /**
     * Las preguntas de la categoría 1 que ya han sido usadas durante la partida. Cuando acaba una partida, se vuelve
     * a dejar vacío este arreglo.
     */
    protected static ArrayList<Answer> answersCat1 = Console.Answer.getCategoryNAnswers((byte)1, answers);
    /**
     * Las preguntas de la categoría 2 que ya han sido usadas durante la partida. Cuando acaba una partida, se vuelve
     * a dejar vacío este arreglo.
     */
    protected static ArrayList<Answer> answersCat2 = Console.Answer.getCategoryNAnswers((byte)2, answers);
    /**
     * Las preguntas de la categoría 3 que ya han sido usadas durante la partida. Cuando acaba una partida, se vuelve
     * a dejar vacío este arreglo.
     */
    protected static ArrayList<Answer> answersCat3 = Console.Answer.getCategoryNAnswers((byte)3, answers);
    /**
     * Las preguntas de la categoría 4 que ya han sido usadas durante la partida. Cuando acaba una partida, se vuelve
     * a dejar vacío este arreglo.
     */
    protected static ArrayList<Answer> answersCat4 = Console.Answer.getCategoryNAnswers((byte)4, answers);
    /**
     * Las preguntas de la categoría 5 que ya han sido usadas durante la partida. Cuando acaba una partida, se vuelve
     * a dejar vacío este arreglo.
     */
    protected static ArrayList<Answer> answersCat5 = Console.Answer.getCategoryNAnswers((byte)5, answers);
    /**
     * Las preguntas de la categoría 6 que ya han sido usadas durante la partida. Cuando acaba una partida, se vuelve
     * a dejar vacío este arreglo.
     */
    protected static ArrayList<Answer> answersCat6 = Console.Answer.getCategoryNAnswers((byte)6, answers);
    
    private static int answersCat1Size = answersCat1.size();
    private static int answersCat2Size = answersCat2.size();
    private static int answersCat3Size = answersCat3.size();
    private static int answersCat4Size = answersCat4.size();
    private static int answersCat5Size = answersCat5.size();
    private static int answersCat6Size = answersCat6.size();
    
    private static ArrayList<Integer> used1Answers = new ArrayList<>();
    private static ArrayList<Integer> used2Answers = new ArrayList<>();
    private static ArrayList<Integer> used3Answers = new ArrayList<>();
    private static ArrayList<Integer> used4Answers = new ArrayList<>();
    private static ArrayList<Integer> used5Answers = new ArrayList<>();
    private static ArrayList<Integer> used6Answers = new ArrayList<>();
    
    
    private static Text questionText;
    /**
     * Es la ventana donde se desarrolla la acción del juego. Aquí aparecen los nombres de los
     * jugadores, el número del turno, los puntos que tienen, las preguntas y sus categorías. Se resalta el
     * nombre del jugador al que le toca contestar a una pregunta. Para contestarla pulsará un
     * botón (en el caso de las preguntas simples deberá hacerlo tras escribir la respuesta
     * en el área de texto).
     */
    public static void game(){
        EnterNames.game.setOnKeyPressed(null);
        grid.setOnMouseClicked(null);
        /* Quizá sirva...
        gameContainer.setCenter(grid);
        
        VBox smallMenu = new VBox();
        Button back = new Button("Volver el menú principal");
        back.setOnAction(e -> GameMenus.createMenu());
        Button exit = new Button("Salir");
        exit.setOnAction(e -> System.exit(0));
        
        smallMenu.getChildren().addAll(back, exit);
        gameContainer.setBottom(smallMenu);*/
        grid.setHgap(2);
        grid.setVgap(5);
        BackgroundStyle.setResizableBackground(grid, "bubbleBackground.png");
        
        if(turn > 20 && p1Points != p2Points){
            playAgain();
            return;
        }
        grid.getStyleClass().add("grid");
        grid.setAlignment(Pos.CENTER);
        
        questions = Question.getQuestions();
        if(questions.isEmpty()){
            Text errorMessage = new Text("No existe ninguna pregunta ni respuesta. Debes crearlas para poder jugar al Trivial.\n"
                    + "Para ello escribe en la consola «Trivial e». Entrarás al modo de edición.\n"
                    + "Entonces selecciona la opción «Añadir una pregunta» (introduce «1»). Con esta opción\n"
                    + "podrás crear preguntas y sus respectivas respuestas.\n");
            grid.add(new TextFlow(errorMessage), 0, 0);
            Scene game = new Scene(grid, GameMenus.WIDTH, GameMenus.HEIGHT);
            Quaestiones.stage.setScene(grid.getScene());
        }
        
        
        
        
        byte randomCategory = (byte)(Math.random() * 6 + 1);
        Question questionObj = null;
        int randomAnswer;
        
        
        int counter;
        switch(randomCategory){ 
            case 1:
                counter = 0;
                do{
                    counter++;
                    randomAnswer = (int) (Math.random() * answersCat1Size);
                    if(counter > 10) break; //Por si ya se han usado todas las respuestas de esta categoría.
                }
                while(used1Answers.contains(randomAnswer));
                used1Answers.add(randomAnswer);
                
                answer = answersCat1.get(randomAnswer);
                questionObj = Question.getQuestionByCode(answer.getCode());
                question = StringFormat.removeSpacesAtTheBeggining(questionObj.getQuestion());
                break;
            case 2: 
                counter = 0;
                do{
                    counter++;
                    randomAnswer = (int) (Math.random() * answersCat2Size);
                    if(counter > 10) break; //Por si ya se han usado todas las respuestas de esta categoría.
                }
                while(used2Answers.contains(randomAnswer));
                used2Answers.add(randomAnswer);
                                
                answer = answersCat2.get(randomAnswer);
                questionObj = Question.getQuestionByCode(answer.getCode());
                question = StringFormat.removeSpacesAtTheBeggining(questionObj.getQuestion());
                break;
            case 3:
                counter = 0;
                do{
                    counter++;
                    randomAnswer = (int) (Math.random() * answersCat3Size);
                    if(counter > 10) break; //Por si ya se han usado todas las respuestas de esta categoría.
                }
                while(used3Answers.contains(randomAnswer));
                used3Answers.add(randomAnswer);
                
                answer = answersCat3.get(randomAnswer);
                questionObj = Question.getQuestionByCode(answer.getCode());
                question = StringFormat.removeSpacesAtTheBeggining(questionObj.getQuestion());
                break;
            case 4:
                counter = 0;
                do{
                    counter++;
                    randomAnswer = (int) (Math.random() * answersCat4Size);
                    if(counter > 10) break; //Por si ya se han usado todas las respuestas de esta categoría.
                }
                while(used4Answers.contains(randomAnswer));
                used4Answers.add(randomAnswer);
                                
                answer = answersCat4.get(randomAnswer);
                questionObj = Question.getQuestionByCode(answer.getCode());
                question = StringFormat.removeSpacesAtTheBeggining(questionObj.getQuestion());
                break;
            case 5: 
                counter = 0;
                do{
                    counter++;
                    randomAnswer = (int) (Math.random() * answersCat5Size);
                    if(counter > 10) break; //Por si ya se han usado todas las respuestas de esta categoría.
                }
                while(used5Answers.contains(randomAnswer));
                used5Answers.add(randomAnswer);
                
                answer = answersCat5.get(randomAnswer);
                questionObj = Question.getQuestionByCode(answer.getCode());
                question = StringFormat.removeSpacesAtTheBeggining(questionObj.getQuestion());
                break;
            case 6: 
                counter = 0;
                do{
                    counter++;
                    randomAnswer = (int) (Math.random() * answersCat6Size);
                    if(counter > 10) break; //Por si ya se han usado todas las respuestas de esta categoría.
                }
                while(used6Answers.contains(randomAnswer));
                used6Answers.add(randomAnswer);
                
                answer = answersCat6.get(randomAnswer);
                questionObj = Question.getQuestionByCode(answer.getCode());
                question = StringFormat.removeSpacesAtTheBeggining(questionObj.getQuestion());
                break;
        }
        
        
        //randomQuestion = (int)(Math.random()*questions.size());

        //question = StringFormat.removeSpacesAtTheBeggining(questions.get(randomQuestion).getQuestion());
        
        byte categoryByte = questionObj.getCategory();
        String category = StringFormat.formatCategory(categoryByte);
        //answer = answers.get(randomQuestion);


        //Jugadores, puntos, turno
        Text player1 = new Text(EnterNames.player1Name);
        player1.setId("player");
        Text player1Points = new Text(Short.toString(p1Points)/* + " puntos"*/);
        player1Points.setId("points");
        grid.setHalignment(player1Points, HPos.CENTER);

        Text numQuestion = new Text("Turno " + Short.toString(turn));
        numQuestion.setId("points");
        grid.setHalignment(numQuestion, HPos.CENTER);

        Text player2 = new Text(EnterNames.player2Name);
        player2.setId("player");
        grid.setHalignment(player2, HPos.RIGHT);
        Text player2Points = new Text(Short.toString(p2Points)/* + " puntos"*/);
        player2Points.setId("points");
        grid.setHalignment(player2Points, HPos.RIGHT);
        
        
        if(turn % 2 == 0){
            player2.setId("playing");
        } else{
            player1.setId("playing");
        }
        
        grid.add(player1, 0, 1);
        grid.add(player1Points, 0, 2);

        grid.add(numQuestion, 0, 0, 3, 1);

        grid.add(player2, 2, 1);
        grid.add(player2Points, 2, 2);
        //\\
        TextArea input = new TextArea();
        
        
        

        
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
        grid.add(categoryText, 0, 4, 3, 1);
        
        questionText = new Text(question);
        questionText.setId("normalText");
        TextFlow questionTextFlow = new TextFlow(questionText);
        questionTextFlow.setTextAlignment(TextAlignment.CENTER);
        
        grid.setHalignment(questionTextFlow, HPos.CENTER);
        grid.add(questionTextFlow, 0, 5, 3, 1);
        
        switch(answer.TYPE_OF_ANSWER){
            case 1: 
                input.setPrefRowCount(1);
                input.setPrefColumnCount(30); //Será de una sola fila
                
                grid.add(input, 0, 6, 3, 1);
                Button enter = new Button("Aceptar");
                grid.setHalignment(enter, HPos.CENTER);
                input.textProperty().addListener((observable, oldValue, newValue) -> 
                    {
                        if(newValue.length() > 30){
                            input.setText(input.getText().substring(0, 30));    
                            MyAlerts.infoAlert("No hay respuestas con más de 30 letras.");
                        }
                    }
                );
                
                input.setOnKeyPressed((KeyEvent e) -> 
                    {
                        if(e.getCode().equals(KeyCode.ENTER)){
                            checkAnswer(input.getText(), StringFormat.removeSpacesAtTheBeggining(answer.getAnswer()));
                        }
                    }
                );

                
                
                grid.add(enter, 0, 7, 3, 1);
                enter.setOnAction(e -> 
                    {
                        checkAnswer(input.getText(), StringFormat.removeSpacesAtTheBeggining(answer.getAnswer()));
                    }
                );
                        
                break;
            case 2:
                VBox yesAndNoButtons = new VBox();
                Button yes = new Button("Sí");
                yes.setMaxWidth(Double.MAX_VALUE);
                Button no = new Button("No");
                no.setMaxWidth(Double.MAX_VALUE);
                
                yesAndNoButtons.setAlignment(Pos.CENTER);
                
                yesAndNoButtons.setSpacing(6);
                yesAndNoButtons.getChildren().addAll(yes, no);
                grid.add(yesAndNoButtons, 0, 6, 3, 1);
              
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
                VBox options = new VBox();
                Console.MultipleAnswer mAnswer = (Console.MultipleAnswer)(answer);
                Button option1 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer()));
                option1.setMaxWidth(Double.MAX_VALUE);
                Button option2 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer2()));
                option2.setMaxWidth(Double.MAX_VALUE);
                Button option3 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer3()));
                option3.setMaxWidth(Double.MAX_VALUE);
                Button option4 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer4()));
                option4.setMaxWidth(Double.MAX_VALUE);
                Button option5 = new Button(StringFormat.removeSpacesAtTheBeggining(mAnswer.getAnswer5()));   
                option5.setMaxWidth(Double.MAX_VALUE);
                
                option1.setOnAction(e -> checkAnswer("1", Byte.toString(mAnswer.getCorrectAnswer())));
                option2.setOnAction(e -> checkAnswer("2", Byte.toString(mAnswer.getCorrectAnswer())));
                option3.setOnAction(e -> checkAnswer("3", Byte.toString(mAnswer.getCorrectAnswer())));
                option4.setOnAction(e -> checkAnswer("4", Byte.toString(mAnswer.getCorrectAnswer())));
                option5.setOnAction(e -> checkAnswer("5", Byte.toString(mAnswer.getCorrectAnswer())));
                options.getChildren().addAll(option1, option2, option3, option4, option5);
                
                options.setAlignment(Pos.CENTER);
                
                options.setSpacing(6);
                options.setPadding(new Insets(0, 20, 10, 20)); 
                grid.add(options, 0, 6, 3, 1);
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
            ImageView victoryImage = new ImageView(new Image("thumbsUpHD.jpg", (UI.GameMenus.WIDTH / 2.5), 0, true, false));
            grid.add(victoryImage, 0, 2);
            grid.setHalignment(victoryImage, HPos.CENTER);

            if(GameMenus.isSoundOn){
                MediaPlayer applause = new MediaPlayer(new Media(Paths.get("src/applause.wav").toUri().toString()));
                applause.play();
            }
            if(turn % 2 == 1){
                message = "¡Bien hecho, " + EnterNames.player2Name + "! Acertaste la pregunta.";
                p2Points++;
            } else{
                message = "¡Bien hecho, " + EnterNames.player1Name + "! Acertaste la pregunta.";
                p1Points++;
            }
        } else{
            ImageView defeatImage = new ImageView(new Image("thumbsDownRedHD.jpg", (UI.GameMenus.WIDTH / 2.5), 0, true, false));
            grid.add(defeatImage, 0, 2);
            grid.setHalignment(defeatImage, HPos.CENTER);
           
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
                message = "¡Qué pena, " + EnterNames.player2Name + "! Fallaste la pregunta.";
            } else{
                message = "¡Qué pena, " + EnterNames.player1Name + "! Fallaste la pregunta.";
            }
            if(!correctAnswer.equals("")){
                message += " La respuesta correcta es «" + StringFormat.removeSpacesAtTheBeggining(correctAnswer) + "».";
            }
        }
        Label result = new Label(message);
        TextFlow resultTextFlow = new TextFlow(result);
        resultTextFlow.setTextAlignment(TextAlignment.CENTER);
        result.setId("normalText");
        
        Label keepPlaying = new Label("Clica en la ventana o pulsa Enter para continuar jugando.");
        keepPlaying.setId("normalText");

        /*VBox vbox = new VBox();
        vbox.setPrefWidth(UI.GameMenus.WIDTH);
        vbox.setPrefHeight(UI.GameMenus.HEIGHT);
        vbox.setSpacing(6);
        vbox.setMargin(keepPlaying, new Insets(6));
        
        vbox.getChildren().addAll(resultTextFlow, keepPlaying);
        vbox.setAlignment(Pos.CENTER);*/
        
        grid.add(resultTextFlow, 0, 0);
        grid.setHalignment(resultTextFlow, HPos.CENTER);
        grid.add(keepPlaying, 0, 1);
        grid.setHalignment(keepPlaying, HPos.CENTER);
        grid.setVisible(true);
        grid.setOnMouseClicked(e -> 
            {
                grid.getChildren().clear();
                game();
            }
        );

        EnterNames.game.setOnKeyPressed((KeyEvent e) ->
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
        HBox hbox = new HBox();
        Button yes = new Button("Sí");
        Button no = new Button("No");
        
        
        hbox.getChildren().addAll(yes, no);
        hbox.setSpacing(6);
        hbox.setAlignment(Pos.CENTER);
        
        grid.add(winner, 0, 0);
        grid.setHalignment(winner, HPos.CENTER);
        
        grid.add(playAgain, 0, 1);
        grid.setHalignment(playAgain, HPos.CENTER);
        //Sí
        grid.add(hbox, 0, 2);
        grid.setHalignment(yes, HPos.CENTER);
        //\\No
        yes.setOnAction(e -> 
            {
                used1Answers = new ArrayList<>();
                used2Answers = new ArrayList<>();
                used3Answers = new ArrayList<>();
                used4Answers = new ArrayList<>();
                used5Answers = new ArrayList<>();
                used6Answers = new ArrayList<>();
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
            winner = EnterNames.player1Name;
        } else if(p2Points > p1Points){
            winner = EnterNames.player2Name;
        } else{ //Nunca debería ocurrir esto.
            winner = "ninguno";
        }
        return winner;
    }
}
