/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trivial;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * @param args the command line arguments
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
                
                final byte ANSWER_LENGTH = 30;
                final byte QUESTION_LENGTH = 120;
                //Cuidado al modificar OFFSET, podría desbordarse la variable byte.
                final byte OFFSET = QUESTION_LENGTH + 1 + 4; //código pregunta, borrada, pregunta
                String answersFile = "respuestas.dat";
                String questionsFile = "preguntas.dat";
                
                Question questionObj = null;

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
                                    String question = String.format("%" + QUESTION_LENGTH + "s" , Input.input("Escribe la pregunta\n>>> "));
                                    try{
                                        RandomAccessFile raf = new RandomAccessFile(questionsFile, "rw");
                                        long sizeOfQuestionsFile = raf.length();
                                        raf.seek(sizeOfQuestionsFile); //Escribe al final del ficehro.
                                        ArrayList<Integer> indexesUsed = new ArrayList<>();
                                        for (int i = 0; i < sizeOfQuestionsFile; i = i + OFFSET) {
                                            indexesUsed.add(Input.readIntegerByRandomAccess(i, questionsFile));
                                        }
                                        Integer code = 0;
                                        if(!indexesUsed.isEmpty()){
                                            code = indexesUsed.get(indexesUsed.size() - 1);
                                        }
                                        do {
                                            code++;
                                        } while (indexesUsed.contains(code));

                                        questionObj = new Question(code, question);

                                        //#código pregunta, borrada, pregunta.
                                        raf.writeInt(questionObj.getCode());
                                        raf.writeBoolean(questionObj.isDeleted());
                                        raf.writeUTF(questionObj.getQuestion());
                                        //Output.writeIntegerByRandomAccess(raf, questionObj.getCode());
                                        //Output.writeBooleanByRandomAccess(raf, questionObj.isDeleted());
                                        //Output.writeUTFByRandomAccess(raf, questionObj.getQuestion());
                                        raf.close();

                                    } catch (FileNotFoundException ex) {
                                        Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                Answer answerObj = null;
                                switch(type){
                                    case 1:
                                        String answer = String.format("%" + ANSWER_LENGTH + "s", Input.input("Escribe la respuesta.\n>>> "));
                                        try{
                                            RandomAccessFile raf = new RandomAccessFile(answersFile, "rw");
                                            long sizeOfQuestionsFile = raf.length();
                                            raf.seek(sizeOfQuestionsFile);
                                            
                                            answerObj = new SimpleAnswer(questionObj.getCode(), questionObj.isDeleted(), answer);
                                            
                                            raf.writeInt(answerObj.getCode());
                                            raf.writeBoolean(answerObj.isDeleted());
                                            raf.writeUTF(answerObj.getAnswer());
                                            
                                            raf.close();
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (IOException ex) {
                                            Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
                                    case 2: break;
                                    case 3: break;
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
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
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
