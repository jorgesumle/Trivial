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
package trivial;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class Trivial extends Application {
    GridPane grid;
    GameLoop gameLoop;
    
    @Override
    public void start(Stage primaryStage) {
        Square[] board = Board.createBoard();
        
        grid = Board.drawSquares(board, new GridPane());
        grid.setVisible(true);
        
  
        Scene scene = new Scene(grid, Board.WIDTH, Board.HEIGHT);
        
        primaryStage.setTitle("Trivial");
        primaryStage.setScene(scene);
        primaryStage.show();
        
       // createStartGameLoop();
    }
    public void createStartGameLoop(){
        gameLoop.start();
    }

    /**
     * 
     * @param args especifica el modo de juego que se ejecutara.
     * Si no se pasa ningún parámetro se iniciará el juego del Trivial.
     * Si se pasa el parámetro "editar" se lanzará el modo de edición del Trivial,
     * el cual permite administrar las preguntas y respuestas que aparecerán en el juego.
     */
    public static void main(String[] args) {
        if(args.length > 0){
            if(args[0].equals("editar")){ //Creación y edición de preguntas y respuestas para el trivial.
                System.out.println("Estás en el modo de edición del Trivial");
                final String menu = "    1. Añadir una pregunta.\n"
                        + "    2. Listar todas las preguntas y respuestas\n"
                        + "    3. Buscar una pregunta y su respuesta.\n"
                        + "    4  Modificar una pregunta y su respuesta.\n"
                        + "    5. Borrar una pregunta y su respuesta.\n"
                        + "    6. Borrar todas las preguntas y respuestas.\n"
                        + "    7. Salir del programa.\n";
                byte option = 0;
                do{
                    option = Input.byteInput("¿Qué quieres hacer?\n" + menu + ">>> ");
                    switch(option){
                        case 1: 
                            byte type = 0;
                            do{
                                type = Input.byteInput("¿Qué tipo de pregunta quieres crear?\n"
                                    + "    1) Simple.\n"
                                    + "    2) Del tipo si/no.\n"
                                    + "    3) De respuesta múltiple.\n"
                                    + "    4) Ver un ejemplo de cada pregunta\n"
                                    + "    5) Volver al menú anterior\n"
                                    + ">>> "
                                );
                                if(type == 1 || type == 2 || type == 3){
                                    EditMode.addQuestion();
                                }
                                Answer answerObj = null;
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
                                        String simpleQuestionExample = "¿En qué año nació Francisco Ibáñez? [pregunta] 1936 [respuesta]";
                                        String yesOrNoQuestionExample = "¿Francisco Ibáñez nació en 1936? [pregunta] 'Sí' o 'No' [posibles respuestas].";
                                        String multipleAnswerQuestionExample = "¿En qué año nació Francisco Ibáñez? [pregunta]"
                                                + "'1936', '1939' o '1942' [posibles respuestas. La respuesta correcta debe ser una de las opciones mostradas]";
                                        System.out.printf("EJEMPLO DE RESPUESTA SIMPLE%n%s%nEJEMPLO DE RESPUESTA DEL TIPO SI/NO%n%s%nEJEMPLO DE RESPUESTA"
                                                + "MÚLTIPLE%n%s%n", simpleQuestionExample, yesOrNoQuestionExample, multipleAnswerQuestionExample);
                                        break;
                                    case 5: break; 
                                }
                            } while(type != 5);
                            break;
                        case 2: //Listar todas las preguntas y respuestas.
                            EditMode.listQuestionsAndAnswers();
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            EditMode.removeAll();
                            break;
                        case 7:
                            System.exit(0);
                            break;                          
                    }
                } while(option != 7);
            }
        }
        else{
            launch(args);
        }
    }
}
