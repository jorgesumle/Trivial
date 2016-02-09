/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trivial;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
                byte option = 0;
                final String menu = "    1. Añadir una pregunta.\n"
                        + "    2. Listar todas las preguntas y respuestas\n"
                        + "    3. Buscar una pregunta y su respuesta.\n"
                        + "    4  Modificar una pregunta y su respuesta.\n"
                        + "    5. Borrar una pregunta y su respuesta.\n"
                        + "    6. Borrar todas las preguntas y respuestas.\n"
                        + "    7. Salir del programa.\n";
                while(option != 7){
                    option = Input.byteInput("¿Qué quieres hacer?\n" + menu + "\n>>> ");
                    switch(option){
                        case 1:
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
                }
            }
        }
        else{
            launch(args);
        }
    }
}
