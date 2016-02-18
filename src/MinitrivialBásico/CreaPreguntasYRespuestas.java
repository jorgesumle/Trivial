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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Este programa crea las preguntas y respuestas del Trivial. Las preguntas tienen
 * una longitud m�xima de 120 car�cteres; las respuestas tienen una longitud m�xima
 * de 30. El programa utiliza dos ficheros de datos: preguntas.dat y respuestas.dat.
 * @author Jorge Maldonado Ventura 
 */
public class CreaPreguntasYRespuestas extends Application{
    GridPane grid;
    public void start(Stage stage){                
        grid = new GridPane();
        grid = GameWindow.game(grid);
        grid.setVisible(true);
        
        Scene scene = new Scene(grid, 300, 300);
        
        stage.setTitle("Trivial");
        stage.setScene(scene);
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
                        + "    5. Borrar una pregunta y su respuesta.\n"
                        + "    6. Borrar todas las preguntas\n"
                        + "    7. Responde a una pregunta elegida al azar.\n"
                        + "    8. Salir del programa.\n";
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
                                    EditMode.addQuestion();
                                }
                                switch(type){
                                    case 1:
                                        EditMode.addSimpleAnswer();
                                        break;
                                    case 2:
                                        EditMode.addYesOrNoAnswer();
                                        break;
                                    case 3:
                                        EditMode.addMultipleAnswer();
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
                            do{
                                query = Input.byteInput("Elige una opci�n:\n"
                                        + "    1) Ver todas las preguntas.\n"
                                        + "    2) Ver todas las respuestas.\n"
                                        + "    3) Ver todas las preguntas y respuestas simples.\n"
                                        + "    4) Ver todas las preguntas y respuestas de s� o no.\n"
                                        + "    5) Ver todas las preguntas y respuestas m�ltiples.\n"
                                        + "    6) Ver todas las preguntas y respuestas.\n"
                                        + "    7) Volver al men� anterior.\n>>> ");
                                switch(query){ //En desarrollo.
                                    case 1: Query.showQuestions(); break;
                                    case 2: Query.showAnswers(); break;
                                    case 3: Query.showSimpleQuestionsAndAnswers(); break;
                                    case 4: break;
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
                                    EditMode.modifyQuestionAndAnswer(Input.intInput("Introduce el c�digo de la pregunta y la respuesta que quieres modificar\n>>> "));
                                    break;
                                case 2:
                                    EditMode.modifyQuestion(Input.intInput("Introduce el c�digo de la pregunta que quieres modificar\n>>> "));
                                    break;
                                case 3:
                                    EditMode.modifyQuestion(Input.intInput("Introduce el c�digo de la respuesta que quieres modificar\n>>> "));
                                    break;                                    
                            }
                            break;
                        case 5:
                            EditMode.removeQuestionAndAnswer(Input.intInput("Introduce el c�digo de la pregunta y la respuesta que quieres borrar\n>>> "));
                            break;
                        case 6:
                            EditMode.removeAll();
                            break;
                        case 7:
                            EditMode.testGame();
                            break;
                        case 8:
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Por favor, introduce una de las opciones disponibles.");
                            break;
                    }
                } while(option != 8);
            }
        } else{
            launch(args);
        }
    }
}
