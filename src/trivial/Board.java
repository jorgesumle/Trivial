/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trivial;

import javafx.scene.layout.GridPane;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class Board {
    final static byte SQUARES = 28;
    public static Square[] createBoard(){
        Square[] board = new Square[SQUARES];
        
        byte color = 0;
        boolean bonus = false;
        
        for(byte number = 0; number < SQUARES; number++){ //Crea las casillas.
            bonus = number % 7 == 0;
            board[number] = new Square(number, color, false, bonus);
            color++;
            if(color > 3)
                color = 0;
        }
        
        return board;
    }
    public static void drawSquares(Square[] board, GridPane grid){
        for(byte i = 0; i < SQUARES; i++){
            
        }
    }
}
