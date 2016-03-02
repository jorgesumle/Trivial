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

import Console.Quaestiones;
import static Console.Quaestiones.grid;
import static Console.Quaestiones.player1Name;
import static Console.Quaestiones.player2Name;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;

/**
 * Todo lo relativo a la ventana en la que los jugadores deben introducir sus
 * nombres antes de jugar.
 * @author Jorge Maldonado Ventura
 */
public class EnterNames {
    /**
     * Crea la ventana en la que los jugadores deben introducir sus nombres. Si
     * no lo desean, pueden dejar el �rea de texto en blanco. En este caso, se
     * les asignar� por defecto los nombres "Jugador 1" y "Jugador 2".
     */
    public static void enterNames(){
        GridPane mainScreen = new GridPane();
        mainScreen.setAlignment(Pos.CENTER);
        mainScreen.setVgap(25);
        mainScreen.setHgap(5);

        Scene enterNames = new Scene(mainScreen, 300, 300);
        
        enterNames.getStylesheets().add(UI.GameWindow.class.getResource("namesSc.css").toExternalForm());
        mainScreen.setBackground(
                    new Background(
                            new BackgroundImage(                             
                                    new Image("abstractBlueBackground.png"), 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundRepeat.NO_REPEAT, 
                                    BackgroundPosition.CENTER, 
                                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
                            )
                    )
        );
        
        Label playerLabel1 = new Label("Jugador 1");
        TextField player1 = new TextField();
        Label playerLabel2 = new Label("Jugador 2");
        TextField player2 = new TextField();
        Button play = new Button("Jugar");
        play.setId("playButton");
        Button back = new Button("Volver atr�s");
        back.setOnAction(e -> GameMenus.createMenu());
        
        mainScreen.add(playerLabel1, 1, 2);
        mainScreen.add(player1, 2, 2);
        mainScreen.add(playerLabel2, 1, 3);
        mainScreen.add(player2, 2, 3);
        
        mainScreen.add(play, 2, 4);
        mainScreen.setHalignment(play, HPos.CENTER);
        mainScreen.add(back, 2, 5);
        mainScreen.setHalignment(back, HPos.CENTER);
        
        
        play.setOnAction(e -> 
            {
                if(player1.getText().equals("")){
                    player1Name = "Jugador 1";
                } else{
                    player1Name = player1.getText(); 
                }
                if(player2.getText().equals("")){
                    player2Name = "Jugador 2";
                } else{
                    player2Name = player2.getText();
                }
                Scene game = null;
                if(grid == null){ //La primera vez que se juega
                    grid = new GridPane();
                    GameWindow.game();
                    game = new Scene(grid, 800, 500);
                    game.getStylesheets().add(UI.GameWindow.class.getResource("gameSc.css").toExternalForm());
                    Quaestiones.stage.setScene(game);
                }
                else{ //Cuando ya se ha jugado una vez o m�s.
                    grid.getChildren().clear();
                    GameWindow.game();
                    Quaestiones.stage.setScene(GameWindow.grid.getScene());
                }
            }
        );
        Quaestiones.stage.setScene(enterNames);
    }
}
