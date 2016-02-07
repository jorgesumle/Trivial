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
    
    @Override
    public void start(Stage primaryStage) {
        
        Button btn = new Button();
        
        Square[] board = Board.createBoard();
        
        
        
        GridPane grid = new GridPane();
        
        Scene scene = new Scene(grid, 300, 250);
        
        primaryStage.setTitle("Trivial");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
