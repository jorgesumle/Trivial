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

package UI;

import MinitrivialB�sico.Trivial;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
public class GameMenus {
    public static void createMenu(){
        GridPane menu = new GridPane();
        menu.setAlignment(Pos.CENTER);
        menu.setVgap(20);
        //menu.setHgap(5);
        
        Scene menuScene = new Scene(menu, 300, 300);
        menuScene.getStylesheets().add(UI.GameWindow.class.getResource("menu.css").toExternalForm());
        
        Text title = new Text("QUAESTIONES");
        title.setId("title");
        //menu.setHalignment(title, HPos.CENTER);
        
        Button play = new Button("Jugar");
        play.setId("playButton");
        play.setOnAction(e -> EnterNames.enterNames());
        
        Button instructions = new Button("Instrucciones");
        instructions.setOnAction(e -> instructions());
        
        Button config = new Button("Configuraci�n");
        config.setOnAction(e -> config());
        Button credits = new Button("Cr�ditos");
        credits.setOnAction(e -> credits());
        Button exit = new Button("Salir");
        exit.setOnAction(e -> System.exit(0));
        
        menu.add(title, 0, 0);
        
        menu.add(play, 0, 2);
        menu.setHalignment(play, HPos.CENTER);
        menu.add(instructions, 0, 3);
        menu.setHalignment(instructions, HPos.CENTER);
        menu.add(config, 0, 4);
        menu.setHalignment(config, HPos.CENTER);
        menu.add(credits, 0, 5);
        menu.setHalignment(credits, HPos.CENTER);
        menu.add(exit, 0, 6);
        menu.setHalignment(exit, HPos.CENTER);
        
        
        Trivial.stage.setScene(menuScene);
    }
    protected static boolean isSoundOn = true;
    private static void config(){
        GridPane configMenu = new GridPane();
        configMenu.setAlignment(Pos.CENTER);
        configMenu.setVgap(20);
        
        Scene configScene = new Scene(configMenu, 300, 300);
        configScene.getStylesheets().add(UI.GameWindow.class.getResource("menu.css").toExternalForm());

        
        Text title = new Text("Configuraci�n");
        title.setId("smallTitle");
        Label soundLabel = new Label("Efectos"); 
        Button sound;
        final String SOUND_ON = "Activados";
        final String SOUND_OFF = "Desactivados";
        if(isSoundOn){
            sound = new Button(SOUND_ON);
        } else{
            sound = new Button(SOUND_OFF);
        }
        sound.setOnAction(e -> 
            {
                if(isSoundOn){
                    sound.setText(SOUND_OFF);
                    isSoundOn = false;
                }
                else{
                    sound.setText(SOUND_ON);
                    isSoundOn = true;
                }
            }
        );
        
        Button back = new Button("Volver atr�s");
        back.setOnAction(e -> GameMenus.createMenu());
        
        
        configMenu.add(title, 0, 0, 2, 1);
        
        configMenu.add(soundLabel, 0, 2);
        configMenu.setHalignment(soundLabel, HPos.CENTER);
        configMenu.add(sound, 1, 2);
        configMenu.setHalignment(sound, HPos.CENTER);
        configMenu.add(back, 0, 3, 2, 1);
        configMenu.setHalignment(back, HPos.CENTER);
        
        
        Trivial.stage.setScene(configScene);
    }

    private static void instructions() {
        VBox instructionsMenu = new VBox();
        instructionsMenu.setAlignment(Pos.CENTER);
        instructionsMenu.setSpacing(10);
        
        
        Text title = new Text("Instrucciones");
        title.setId("smallTitle");
        
        Text gameTitle = new Text("Quaestiones");
        gameTitle.setStyle("-fx-font-style: italic;");
        Text instructionsText = new Text(" es un videojuego para dos jugadores."
                + "Cada jugador tiene que responder a diez preguntas "
                + "en turnos alternos y suma un punto por cada pregunta "
                + "acertada. El ganador es el jugador que consiga m�s "
                + "puntos al t�rmino de la ronda de diez preguntas. "
                + "Si se produce un empate, en el turno 20, se "
                + "seguir�s haciendo preguntas hasta que alg�n jugador falle."
        );
        TextFlow instructions = new TextFlow(gameTitle, instructionsText);
        instructions.setTextAlignment(TextAlignment.CENTER);
        
        instructionsMenu.setMargin(instructions, new Insets(6));
        
        Button back = new Button("Volver atr�s");
        back.setOnAction(e -> GameMenus.createMenu());
        
        instructionsMenu.getChildren().addAll(title, instructions, back);
        
        
        Scene instructionsScene = new Scene(instructionsMenu, 300, 300);
        instructionsScene.getStylesheets().add(UI.GameWindow.class.getResource("menu.css").toExternalForm());
        Trivial.stage.setScene(instructionsScene);
    }
    public static void credits(){
        VBox creditsMenu = new VBox();
        creditsMenu.setAlignment(Pos.CENTER);
        creditsMenu.setSpacing(10);
        
        Text title = new Text("Cr�ditos");
        title.setId("smallTitle");
        
        creditsMenu.setMargin(creditsMenu, new Insets(6));
        
        Text jorge = new Text("Jorge Maldonado Ventura (programaci�n y dise�o).\n\n");
        
        Text pai = new Text("'Pay' Pablo (dise�o).\n\n");
        
        Text hand = new Text("http://freebie.photography/ (imagen de la mano).");
        
        TextFlow credits = new TextFlow(jorge, pai, hand);
        credits.setTextAlignment(TextAlignment.CENTER);
        
        ImageView GPL = new ImageView(new Image(UI.GameWindow.class.getResource("GPL.jpg").toExternalForm()));
        
        Text copyrightFooter = new Text("Quaestiones � 2016 Jorge Maldonado Ventura.\n"
            + "Este programa es software libre: usted puede redistruirlo y/o modificarlo "+
            "bajo los t�rminos de la Licencia P�blica General GNU, tal y como est� publicada por" +
            " la Free Software Foundation; ya sea la versi�n 3 de la Licencia, o" +
            " (a su elecci�n) cualquier versi�n posterior."
        );
        TextFlow copyrightFter = new TextFlow(copyrightFooter);
        copyrightFter.setTextAlignment(TextAlignment.CENTER);
                
        Button back = new Button("Volver atr�s");
        back.setOnAction(e -> GameMenus.createMenu());
        
        creditsMenu.getChildren().addAll(title, credits, GPL, copyrightFter, back);
        
        Scene creditsScene = new Scene(creditsMenu, 640, 520);
        creditsScene.getStylesheets().add(UI.GameWindow.class.getResource("menu.css").toExternalForm());
        Trivial.stage.setScene(creditsScene);
        
    }
}
