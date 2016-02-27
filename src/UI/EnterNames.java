/*
 * Copyright (C) 2016 Jorge Maldonado Ventura
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package UI;

import MinitrivialBásico.Trivial;
import static MinitrivialBásico.Trivial.grid;
import static MinitrivialBásico.Trivial.player1Name;
import static MinitrivialBásico.Trivial.player2Name;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class EnterNames {
    public static void enterNames(){
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
                game.getStylesheets().add(UI.GameWindow.class.getResource("gameSc.css").toExternalForm());
                Trivial.stage.setScene(game);
            }
        );
        Trivial.stage.setScene(enterNames);
    }
}
