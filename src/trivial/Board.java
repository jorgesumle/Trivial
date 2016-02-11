/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trivial;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class Board {
    final static byte SQUARES = 28;
    final static short WIDTH = 640;
    final static short HEIGHT = 640;
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
    public static GridPane drawSquares(Square[] board, GridPane grid){
        grid.setHgap(1);
        grid.setVgap(1);
        
        byte x = 0;
        byte y = 0;
        byte set = 0;
        boolean incrementX = false, 
                decrementX = false,
                incrementY = false,
                decrementY = false;
        Color color = Color.TRANSPARENT;
        Text text = new Text("");
        for(byte i = 0; i < SQUARES; i++){
            if(i % 4 == 0) color = Color.RED;
            else if(i % 4 == 1) color = Color.GAINSBORO;
            else if(i % 4 == 2) color = Color.LIGHTSEAGREEN;
            else if(i % 4 == 3) color = Color.YELLOWGREEN;
            
            text = new Text("");
            if(i % 7 == 0){
                set++;
                text = new Text("QUESITO");
                if(set == 1){
                    incrementX = true;
                }
                else if(set == 2){
                    incrementX = false;
                    incrementY = true;
                }
                else if(set == 3){
                    incrementY = false;
                    decrementX = true;
                }
                else if(set == 4){
                    decrementX = false;
                    decrementY = true;
                }
            }
            
            grid.add(new Rectangle(80, 80, color), x, y);
            grid.add(text, x, y);
            
            if(incrementX) x++;
            else if (incrementY) y++;
            else if(decrementX) x--;
            else if(decrementY) y--;
        }
        return grid;
    }
}
