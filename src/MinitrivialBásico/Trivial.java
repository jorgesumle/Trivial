/*
 * Copyright (C) 2016 Jorge Maldonado Ventura 
 *
 * Este programa es software libre: usted puede redistruirlo y/o modificarlo
 * bajo los t�rminos de la Licencia P�blica General GNU, tal y como est� publicada por
 * la Free Software Foundation; ya sea la versi�n 3 de la Licencia, o
 * (a su elecci�n) cualquier versi�n posterior.
 *
 * Este programa se distribuye con la intenci�n de ser �til,
 * pero SIN NINGUNA GARANT�A; incluso sin la garant�a impl�cita de
 * USABILIDAD O UTILIDAD PARA UN FIN PARTICULAR. Vea la
 * Licencia P�blica General GNU para m�s detalles.
 *
 * Usted deber�a haber recibido una copia de la Licencia P�blica General GNU
 * junto a este programa.  Si no es as�, vea <http://www.gnu.org/licenses/>.
 */

package MinitrivialB�sico;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Trivial (videojuego). Las preguntas tienen
 * una longitud m�xima de 120 car�cteres; las respuestas tienen una longitud m�xima
 * de 30. El programa utiliza dos ficheros de datos: preguntas.dat y respuestas.dat.
 * @author Jorge Maldonado Ventura 
 */
public class Trivial extends Application{
    public static GridPane grid;
    public static String player1Name;
    public static String player2Name;
    
    public void start(Stage stage){
        GridPane mainScreen = new GridPane();
        mainScreen.setVgap(25);
        mainScreen.setHgap(5);

        Scene enterNames = new Scene(mainScreen, 300, 300);
        
        enterNames.getStylesheets().add(UI.GameWindow.class.getResource("namesSc.css").toExternalForm());

        Text title = new Text("TRIVIAL");
        title.setId("title");
        
        Label playerLabel1 = new Label("Jugador 1");
        TextField player1 = new TextField();
        Label playerLabel2 = new Label("Jugador 2");
        TextField player2 = new TextField();
        Button play = new Button("Jugar");
        play.setId("playButton");
        Button exit = new Button("Salir");
        exit.setOnAction(e -> System.exit(0));
        
        mainScreen.add(title, 1, 1, 2, 1);
        
        mainScreen.add(playerLabel1, 1, 2);
        mainScreen.add(player1, 2, 2);
        mainScreen.add(playerLabel2, 1, 3);
        mainScreen.add(player2, 2, 3);
        
        mainScreen.add(play, 2, 4);
        mainScreen.add(exit, 2, 5);
        
        
        play.setOnAction(e -> 
            {
                player1Name = player1.getText(); 
                player2Name = player2.getText();
                
                grid = new GridPane();
                UI.GameWindow.game();
                Scene game = new Scene(grid, 700, 500);
                stage.setScene(game);
            }
        );
        
        
        stage.setTitle("Trivial");
        stage.getIcons().add(new Image(UI.GameWindow.class.getResource("icon.png").toExternalForm()));
        stage.setScene(enterNames);
        stage.show();
    }
    /**
     * @param args los par�metros de la l�nea de comandos
     */
    public static void main(String[] args) {
        if(args.length > 0){
            if(args[0].equals("editar")){ //Creaci�n y edici�n de preguntas y respuestas para el trivial.
                final String menu = "    1. A�adir una pregunta.\n"
                        + "    2. Consultar datos.\n"
                        + "    3. Buscar una pregunta y/o su respuesta.\n"
                        + "    4. Modificar una pregunta y/o su respuesta.\n" //Falta el o
                        + "    5. Borrado de preguntas y respuestas.\n"
                        + "    6. Responde a una pregunta elegida al azar.\n"
                        + "    7. Salir del programa.\n";
                byte option;
                do{
                    option = Input.byteInput("�Qu� quieres hacer?\n" + menu + ">>> ");
                    switch(option){
                        case 1: 
                            byte type = 0;
                            do{
                                type = Input.byteInput("�Qu� tipo de pregunta quieres crear?\n"
                                    + "    1) Simple.\n"
                                    + "    2) Del tipo si/no.\n"
                                    + "    3) De respuesta m�ltiple.\n"
                                    + "    4) Ver un ejemplo de cada pregunta\n"
                                    + "    5) Volver al men� anterior\n"
                                    + ">>> "
                                );
                                if(type == 1 || type == 2 || type == 3){
                                    Question.addQuestion();
                                }
                                switch(type){
                                    case 1:
                                        SimpleAnswer.addSimpleAnswer();
                                        break;
                                    case 2:
                                        YesOrNoAnswer.addYesOrNoAnswer();
                                        break;
                                    case 3:
                                        MultipleAnswer.addMultipleAnswer();
                                        break;
                                    case 4:
                                        String simpleQuestionExample = "�En qu� a�o naci� Francisco Ib��ez? [pregunta] 1936 [respuesta].";
                                        String yesOrNoQuestionExample = "�Francisco Ib��ez naci� en 1936? [pregunta] 'S�' o 'No' [posibles respuestas].";
                                        String multipleAnswerQuestionExample = "�En qu� a�o naci� Francisco Ib��ez? [pregunta]"
                                                + "'1936', '1939' o '1942' [posibles respuestas. La respuesta correcta debe ser una de las opciones mostradas].";
                                        System.out.printf("EJEMPLO DE RESPUESTA SIMPLE%n%s%nEJEMPLO DE RESPUESTA DEL TIPO SI/NO%n%s%nEJEMPLO DE RESPUESTA"
                                                + "M�LTIPLE%n%s%n", simpleQuestionExample, yesOrNoQuestionExample, multipleAnswerQuestionExample);
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Introduce una de las opciones disponibles.");
                                        break;
                                }
                            } while(type != 5); 
                            break;
                        case 2:
                            byte query = 0;
                            ArrayList<Question> questions = Question.getQuestions();
                            ArrayList<Answer> answers = Answer.getAnswers();
                            String[] category = new String[questions.size()];
                            for(int i = 0; i < category.length; i++){
                                category[i] = (StringFormat.formatCategory(questions.get(i).getCategory()));
                            }
                            
                            do{
                                query = Input.byteInput("Elige una opci�n:\n"
                                        + "    1) Ver todas las preguntas.\n"
                                        + "    2) Ver todas las respuestas.\n"
                                        + "    3) Ver todas las preguntas y respuestas simples.\n"
                                        + "    4) Ver todas las preguntas y respuestas de s� o no.\n"
                                        + "    5) Ver todas las preguntas y respuestas m�ltiples.\n"
                                        + "    6) Ver todas las preguntas y respuestas.\n"
                                        + "    7) Volver al men� anterior.\n>>> ");
                                
                                final String QUESTIONS_HEADER = "Pregunta";
                                final String ANSWERS_HEADER = "Respuesta";
                                String[][] fields;
                                
                                final byte MIN_LENGTH_OF_ANSWER_FIELD = (byte)ANSWERS_HEADER.length();
                                final byte MIN_LENGTH_OF_CODE_FIELD = 10;
                                final byte MIN_LENGTH_OF_CATEGORY_FIELD = 21;
                                final byte MIN_LENGTH_OF_QUESTION_FIELD = (byte)QUESTIONS_HEADER.length();
                                //final String headers[]; Usalo cuando descubras por qu� falla lo otro.
                                    //Tambi�n para minFieldLength
                                switch(query){ //En desarrollo.                             
                                    case 1: //    1) Ver todas las preguntas.
                                        final String headers[] = {"C�digo", "Categor�a", "Pregunta"};
                                        byte minFieldLength[] = {MIN_LENGTH_OF_CODE_FIELD, MIN_LENGTH_OF_CATEGORY_FIELD, MIN_LENGTH_OF_QUESTION_FIELD};
                                        fields = new String[questions.size()][minFieldLength.length];
                                        for(int i = 0; i < questions.size(); i++){
                                            for(int j = 0; j < minFieldLength.length; j++){
                                                switch(j){
                                                    case 0: 
                                                        fields[i][j] = Integer.toString(questions.get(i).getCode()); 
                                                        break;
                                                    case 1: 
                                                        fields[i][j] = StringFormat.formatCategory(questions.get(i).getCategory()); 
                                                        break;
                                                    case 2: 
                                                        fields[i][j] = StringFormat.removeSpacesAtTheBeggining(questions.get(i).getQuestion()); 
                                                        break;
                                                }
                                            }
                                        }
                                        Query.showTable(headers, minFieldLength, fields);
                                        break;
                                    case 2: Query.showAnswers(); break;
                                    case 3:/*
                                        final String[] headers2 = {"C�digo", "Categor�a", "Pregunta", "Respuesta"};
                                        final ArrayList<SimpleAnswer> simpleAnswers = SimpleAnswer.getSimpleAnswers(answers);
                                        final ArrayList<Question> simpleQuestions;
                                        byte minFieldLength2[] = {MIN_LENGTH_OF_CODE_FIELD, MIN_LENGTH_OF_CATEGORY_FIELD, MIN_LENGTH_OF_QUESTION_FIELD, MIN_LENGTH_OF_ANSWER_FIELD};
                                        fields = new String[questions.size()][minFieldLength2.length];
                                        for(int i = 0; i < simpleAnswers.size(); i++){
                                            for(int j = 0; j < minFieldLength2.length; j++){
                                                switch(j){
                                                    case 0: 
                                                        fields[i][j] = Integer.toString(questions.get(i).getCode()); 
                                                        break;
                                                    case 1: 
                                                        fields[i][j] = StringFormat.formatCategory(questions.get(i).getCategory()); 
                                                        break;
                                                    case 2: 
                                                        fields[i][j] = StringFormat.removeSpacesAtTheBeggining(questions.get(i).getQuestion()); 
                                                        break;
                                                    case 3:
                                                        fields[i][j] = StringFormat.removeSpacesAtTheBeggining(simpleAnswers.get(i).getAnswer());
                                                        break;
                                                }
                                            }
                                        }*/
                                        //Query.showTable(headers2, minFieldLength2, fields);
                                        Query.showSimpleQuestionsAndAnswers(); 
                                        break;
                                    case 4: Query.showYesOrNoQuestions(); break;
                                    case 5: break;
                                    case 6: break;
                                    case 7: break;
                                    default: System.out.println("No has introducido una opci�n v�lida.");
                                }     
                            } while(query != 7);

                            break;
                        case 3:
                            byte search = 0;
                            do{
                                search = Input.byteInput("Elige una opci�n:\n"
                                        + "    1) Buscar una pregunta y su respuesta por su c�digo.\n"
                                        + "    2) Buscar una pregunta por su c�digo.\n"
                                        + "    3) Buscar una respuesta por su c�digo.\n"
                                        + "    4) Volver al men� anterior.\n>>> ");
                                switch(search){
                                    case 1:
                                        Query.printAnswerAndQuestionByCode(Input.intInput("Introduce el c�digo de la pregunta y la respuesta que quieres buscar\n>>> "));
                                        break;
                                    case 2:
                                        Query.printQuestionByCode(Input.intInput("Introduce el c�digo de la pregunta que quieres buscar\n>>> "));
                                        break;
                                    case 3:
                                        Query.printAnswerByCode(Input.intInput("Introduce el c�digo de la respuesta que quieres buscar\n>>> "));
                                        break;
                                }
                            } while(search != 4);
                            
                            break;
                        case 4:
                            byte toModify = 0;
                            do{
                                toModify = Input.byteInput("Elige una opci�n:\n    1) Modificar una pregunta y su respuesta.\n"
                                        + "    2) Modificar una pregunta.\n"
                                        + "    3) Modificar una respuesta.\n");
                            } while(toModify > 3 || toModify < 1);
                            switch(toModify){
                                case 1:
                                    int code = Input.intInput("Introduce el c�digo de la pregunta y la respuesta que quieres modificar\n>>> ");
                                    Question.modifyQuestion(code);
                                    Answer.modifyAnswer(code);
                                    break;
                                case 2:
                                    Question.modifyQuestion(Input.intInput("Introduce el c�digo de la pregunta que quieres modificar\n>>> "));
                                    break;
                                case 3:
                                    Answer.modifyAnswer(Input.intInput("Introduce el c�digo de la respuesta que quieres modificar\n>>> "));
                                    break;                                    
                            }
                            break;
                        case 5:
                            byte deleteOption = 0;
                            do{
                                deleteOption = Input.byteInput("Elige una opci�n:\n"
                                        + "    1) Borrar una pregunta.\n"
                                        + "    2) Borrar todas las preguntas.\n"
                                        + "    3) Eliminar definitivamente las preguntas que ya han sido borradas.\n"
                                        + "    4) Volver al men� anterior.\n>>> ");
                            } while(deleteOption < 1 || deleteOption > 4);
                            switch(deleteOption){
                                case 1: 
                                    int code = Input.intInput("Introduce el c�digo de la pregunta y la respuesta que quieres borrar\n>>> ");
                                    Question.removeQuestion(code);
                                    Answer.removeAnswer(code);
                                    break;
                                case 2:
                                    //Confirmaci�n
                                    String confirmation;
                                    do{
                                        confirmation = Input.input("�Est�s seguro de que quieres eliminar todas las preguntas y respuestas? ('s' o 'n')\n>>> ").toLowerCase();
                                    } while(confirmation.charAt(0) != 's' && confirmation.charAt(0) != 'n');
                                    if(confirmation.charAt(0) == 'n'){
                                        break;
                                    }
                                    //\\
                                    Question.removeQuestionsPermanently();
                                    Answer.removeAnswersPermanently();
                                    System.out.println("Se han borrado con �xito todas las preguntas y respuestas.");
                                    break;
                                case 3:
                                    Question.removeDeletedQuestions();
                                    Answer.removeDeletedAnswers();
                                    System.out.println("Se han borrado definitivamente las preguntas borradas con �xito.");
                                    break;
                                case 4: 
                                    break;
                            }
                            break;
                        case 6:
                            ConsoleGame.testGame();
                            break;
                        case 7:
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Por favor, introduce una de las opciones disponibles.");
                            break;
                    }
                } while(option != 7);
            }
        } else{
            launch(args);
        }
    }
}
