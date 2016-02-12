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
                                int code = 0;
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
                                       
                                        if(!indexesUsed.isEmpty()){
                                            code = indexesUsed.get(indexesUsed.size() - 1);
                                        }
                                        do {
                                            code++;
                                        } while (indexesUsed.contains(code));

                                        questionObj = new Question(code, question);

                                        questionObj.writeQuestion(raf);

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
                                        answerObj = new SimpleAnswer(questionObj.getCode(), questionObj.isDeleted(), answer);
                                        try{
                                            RandomAccessFile raf = new RandomAccessFile(answersFile, "rw");
                                            long sizeOfQuestionsFile = raf.length();                                          
                                            raf.seek(sizeOfQuestionsFile);
                                            answerObj.answerWriter(raf);
                                            raf.close();
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (IOException ex) {
                                            Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
                                    case 2:
                                        do{
                                            answer = Input.input("¿La respuesta es 'sí' o 'no' ('s' o 'n')?\n>>> ");
                                            if(!(answer.equals("sí") || answer.equals("no") || answer.equals("si") || answer.equals("s") || answer.equals("n"))){
                                                System.out.println("Las respuestas permitidas son 'sí', 'no', 'si', 's' y 'n'. Prueba de  nuevo.");
                                            }
                                        } while(!(answer.equals("sí") || answer.equals("no") || answer.equals("si") || answer.equals("s") || answer.equals("n")));
                                       
                                        if(answer.equals("sí") || answer.equals("si") || answer.equals("s")){
                                            answer = "s";
                                        } else{
                                            answer = "n";
                                        }
                                        answerObj = new YesOrNoAnswer(code, false, answer);
                                        try{
                                            RandomAccessFile raf = new RandomAccessFile(answersFile, "rw");
                                            long sizeOfQuestionsFile = raf.length();
                                            raf.seek(sizeOfQuestionsFile);
                                            answerObj.answerWriter(raf);
                                            raf.close();
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (IOException ex) {
                                            Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
                                    case 3:
                                        boolean noMistake = false;
                                        String answer2;
                                        String answer3;
                                        String answer4;
                                        String answer5;
                                        do{
                                            System.out.println("Primero debes introducir todas las posibles respuestas. "
                                                    + "Después tendrás que especificar cuál es la correcta");
                                            answer = Input.input("Introduce la primera respuesta\n>>> ");
                                            answer2 = Input.input("Introduce la segunda respuesta\n>>> ");
                                            answer3 = Input.input("Introduce la tercera respuesta\n>>> ");
                                            answer4 = Input.input("Introduce la cuarta respuesta\n>>> ");
                                            answer5 = Input.input("Introduce la quinta respuesta\n>>> ");
                                            
                                            String again = Input.input(String.format("Estas son las preguntas que has introducido:%n"
                                                    + " 1)%s%n    2)%s%n    3)%s%n    4)%s%n    5)%s%nEscribe 's' si has cometido algún error y "
                                                    + "quieres introducir de nuevo las respuestas.%n>>> ", answer, answer2, answer3, answer4, answer5));
                                            if(again.equals("s")){
                                                continue;
                                            }
                                            byte correctAnswer = Input.byteInput(String.format("¿Cuál de las respuestas que has introducido es la correcta?"
                                                    + "    1)%s%n    2)%s%n    3)%s%n    4)%s%n    5)%s%n>>> ", answer, answer2, answer3, answer4, answer5));
                                        } while(noMistake);
                                        answerObj = new MultipleAnswer(code, false, answer, answer2, answer3, answer4, answer5);
                                        try{
                                            RandomAccessFile raf = new RandomAccessFile(answersFile, "rw");
                                            long sizeOfQuestionsFile = raf.length();
                                            raf.seek(sizeOfQuestionsFile);
                                            answerObj.answerWriter(raf);
                                            raf.close();
                                        } catch (IOException ex) {
                                            Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
                                        }
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
                        case 2:
                            try{
                                RandomAccessFile raf = new RandomAccessFile(questionsFile, "r");
                                
                                raf.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
