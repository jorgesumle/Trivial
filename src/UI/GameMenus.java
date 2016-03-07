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

package UI;

import Console.Quaestiones;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * Todo lo relativo a los menús del juego.
 * @author Jorge Maldonado Ventura 
 */
public class GameMenus {
    //Quizás se podría hacer con herencia para todas las ventanas de forma más sencilla.
    /**
     * Anchura de todas las ventanas del juego.
     */
    public static short WIDTH = 320;
    /**
     * Altura de todas las ventanas del juego.
     */
    public static short HEIGHT = 480;
    
    /**
     * El panel que está siendo usado actualmente.
     */
    public static Pane currentPane;
    /**
     * Crea el menú principal del juego. Desde aquí se puede acceder al juego y a 
     * otros submenús.
     */
    public static void createMenu(){
         /*Si hay un error porque no existe aún el archivo de configuración, no importa; se usarán los valores predeterminados y el error
        no lo verá el usuario. En cuanto se cambie una vez la configuración esto siempre cargará la configuración, aunque sea la misma que haya ya 
        establecida.*/
        loadConfig();
        
        GridPane menu = new GridPane();
        currentPane = menu;
        menu.setAlignment(Pos.CENTER);
        menu.setVgap(20);
        //menu.setHgap(5);
        
        Scene menuScene = new Scene(menu, WIDTH, HEIGHT);
        menuScene.getStylesheets().add(UI.GameWindow.class.getResource("menu.css").toExternalForm());
        
        BackgroundStyle.setResizableBackground(menu, "questionMarksBackground.jpg");
        
        Text title = new Text("QUAESTIONES");
        title.setId("title");
        //menu.setHalignment(title, HPos.CENTER);
        
        Button play = new Button("Jugar");
        play.setId("playButton");
        play.setOnAction(e -> EnterNames.enterNames());
        
        Button instructions = new Button("Instrucciones");
        instructions.setOnAction(e -> instructions());
        
        Button config = new Button("Configuración");
        config.setOnAction(e -> config());
        Button credits = new Button("Créditos");
        credits.setOnAction(e -> credits());
        Button exit = new Button("Salir");
        exit.setOnAction(e -> 
            {   
                if(askBeforeClose){
                    BoxBlur blur = new BoxBlur(GameMenus.WIDTH / 64, GameMenus.HEIGHT / 64, 1);
                    currentPane.setEffect(blur);
                    MyAlerts.exitAlert("Quaestiones - ¿salir?", "¿Estás seguro de que quieres cerrar el programa?", currentPane);
                } else{
                    System.exit(0);
                }
            }
        );
        
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

        Quaestiones.stage.setScene(menuScene);
        
        Quaestiones.stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
            if(e.getCode().equals(KeyCode.ESCAPE)){
                if(askBeforeClose){
                    BoxBlur blur = new BoxBlur(GameMenus.WIDTH / 64, GameMenus.HEIGHT / 64, 1);
                    currentPane.setEffect(blur);
                    MyAlerts.exitAlert("Quaestiones - ¿salir?", "¿Estás seguro de que quieres cerrar el programa?", currentPane);
                } else{
                    System.exit(0);
                }
            }
        });
    }
    /**
     * El estado de los sonidos del juego. Con <i>true</i> están activados; con <i>false</i> desactivados.
     */
    protected static boolean isSoundOn = true;
    /**
     * El requerimiento de confirmación. Si es <i>true</i>, se pedirá una confirmación al usuario
     * antes de cerrar el programa. En caso contrario, se cerrará directamente.
     */
    protected static boolean askBeforeClose = false;
    /**
     * La lista desplegable con las distintas resoluciones.
     */
    private static ChoiceBox<String> resolution;
    final static String R1366x768 = "1366 x 768";
    final static String R320x480 = "320 x 480";
    /**
     * Carga el menú de configuración del juego, desde donde se pueden activar y
     * desactivar los sonidos
     */
    private static void config(){
        GridPane configMenu = new GridPane();
        currentPane = configMenu;
        
        configMenu.setAlignment(Pos.CENTER);
        configMenu.setVgap(20);
        
        Scene configScene = new Scene(configMenu, WIDTH, HEIGHT);
        configScene.getStylesheets().add(UI.GameWindow.class.getResource("menu.css").toExternalForm());
        BackgroundStyle.setResizableBackground(configMenu, "questionMarksBackground2.jpg");
        
        Text title = new Text("Configuración");
        title.setId("smallTitle");
        final Label SOUND_LABEL = new Label("Sonidos"); 
        Button sound;
        final String SOUND_ON = "Sí";
        final String SOUND_OFF = "No";
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
        Label ASK_TO_CLOSE = new Label("Preguntar antes de cerrar el programa");
        final String ASK_BEFORE_CLOSE = "Sí";
        final String DO_NOT_ASK_BEFORE_CLOSE = "No";
        Button askToClose;
        if(askBeforeClose){
            askToClose = new Button(ASK_BEFORE_CLOSE);
        } else{
            askToClose = new Button(DO_NOT_ASK_BEFORE_CLOSE);
        }
        askToClose.setOnAction(e ->
            {
                if(askBeforeClose){
                    askToClose.setText(DO_NOT_ASK_BEFORE_CLOSE);
                    askBeforeClose = false;
                } else{
                    askToClose.setText(ASK_BEFORE_CLOSE);
                    askBeforeClose = true;
                }
            }
        );
        
        
        final Label RESOLUTION_STR = new Label("Resolución predeterminada"); 
        resolution = new ChoiceBox<>();
        
        //Opciones de la lista desplegable
        resolution.getItems().add(R320x480);
        resolution.getItems().add(R1366x768);
        
        if(WIDTH == 320 && HEIGHT == 480){
            resolution.setValue(R320x480);
        } else if(WIDTH == 1366 && HEIGHT == 768){
            resolution.setValue(R1366x768);
        }
        
        
        Button back = new Button("Volver atrás");
        back.setOnAction(e -> 
            {
                saveConfig();
                GameMenus.createMenu();
            }
        );
        
        
        configMenu.add(title, 0, 0, 2, 1);
        configMenu.setHalignment(title, HPos.CENTER);
        
        configMenu.add(SOUND_LABEL, 0, 2);
        configMenu.setHalignment(SOUND_LABEL, HPos.CENTER);
        configMenu.add(sound, 1, 2);
        configMenu.setHalignment(sound, HPos.CENTER);
        configMenu.add(ASK_TO_CLOSE, 0, 3);
        configMenu.setHalignment(ASK_TO_CLOSE, HPos.CENTER);
        configMenu.add(askToClose, 1, 3);
        configMenu.setHalignment(askToClose, HPos.CENTER);
        configMenu.add(RESOLUTION_STR, 0, 4);
        configMenu.setHalignment(RESOLUTION_STR, HPos.CENTER);
        configMenu.add(resolution, 1, 4);
        configMenu.add(back, 0, 5, 2, 1);
        configMenu.setHalignment(back, HPos.CENTER);
        
        
        Quaestiones.stage.setScene(configScene);
    }
    /**
     * Crea una ventana con las instrucciones del juego.
     */
    private static void instructions() {
        VBox instructionsMenu = new VBox();
        currentPane = instructionsMenu;
        
        instructionsMenu.setPrefWidth(WIDTH);
        instructionsMenu.setPrefHeight(HEIGHT);
        instructionsMenu.setAlignment(Pos.CENTER);
        instructionsMenu.setSpacing(10);
        
        
        Text title = new Text("Instrucciones");
        title.setId("smallTitle");
        
        Text gameTitle = new Text("Quaestiones");
        gameTitle.setStyle("-fx-font-style: italic;");
        Text instructionsText = new Text(" es un videojuego para dos jugadores. "
                + "Cada jugador tiene que responder a diez preguntas "
                + "en turnos alternos y suma un punto por cada pregunta "
                + "acertada. El ganador es el jugador que consiga más "
                + "puntos al término de la ronda de diez preguntas. "
                + "Si se produce un empate en el turno 20, se "
                + "seguirán haciendo preguntas hasta que algún jugador falle."
        );
        TextFlow instructions = new TextFlow(gameTitle, instructionsText);
        instructions.getStyleClass().add("textFlow");
        instructions.setTextAlignment(TextAlignment.CENTER);
        
        instructionsMenu.setMargin(instructions, new Insets(6));
        
        Button back = new Button("Volver atrás");
        back.setOnAction(e -> GameMenus.createMenu());
        
        instructionsMenu.getChildren().addAll(title, instructions, back);
        BackgroundStyle.setResizableBackground(instructionsMenu, "questionMarksBackground2.jpg");
        
        
        Scene instructionsScene = new Scene(instructionsMenu, WIDTH, HEIGHT);
        
        instructionsScene.getStylesheets().add(UI.GameWindow.class.getResource("menu.css").toExternalForm());
        Quaestiones.stage.setScene(instructionsScene);
    }
    /**
     * Crea una ventana con los créditos.
     */
    public static void credits(){
        VBox creditsMenu = new VBox();
        currentPane = creditsMenu;
        
        creditsMenu.setAlignment(Pos.CENTER);
        creditsMenu.setSpacing(10);
        
        Text title = new Text("Créditos");
        title.setId("smallTitle");
        
        creditsMenu.setMargin(creditsMenu, new Insets(6));
        BackgroundStyle.setResizableBackground(creditsMenu, "questionMarksBackground2.jpg");
        
        Text jorge = new Text("Jorge Maldonado Ventura (programación y diseño).\n\n");
        
        
        Text hand = new Text("http://freebie.photography/ (imagen de la mano).");
        
        TextFlow credits = new TextFlow(jorge, hand);
        credits.setTextAlignment(TextAlignment.CENTER);
        credits.getStyleClass().add("textFlow");
        
        ImageView GPL = new ImageView(new Image("/GPL.jpg", 100, 50, true, false));
        
        Text copyrightFooter = new Text("Quaestiones © 2016 Jorge Maldonado Ventura.\n"
            + "Este programa es software libre: usted puede redistruirlo y/o modificarlo "+
            "bajo los términos de la Licencia Pública General GNU, tal y como está publicada por" +
            " la Free Software Foundation; ya sea la versión 3 de la Licencia, o" +
            " (a su elección) cualquier versión posterior."
        );
        TextFlow copyrightFter = new TextFlow(copyrightFooter);
        copyrightFter.setTextAlignment(TextAlignment.CENTER);
        copyrightFter.setId("copyright");
                
        Button back = new Button("Volver atrás");
        back.setOnAction(e -> GameMenus.createMenu());
        
        creditsMenu.getChildren().addAll(title, credits, GPL, copyrightFter, back);
        
        Scene creditsScene = new Scene(creditsMenu, WIDTH, HEIGHT);
        creditsScene.getStylesheets().add(UI.GameWindow.class.getResource("menu.css").toExternalForm());
        Quaestiones.stage.setScene(creditsScene);
        
    }
    final static String CONFIG_FILE = "src/config.ajustes";
    private static void saveConfig() {
        String resolutionStr = resolution.getValue();
        byte resolutionCode = 0;
        if(resolutionStr.equals(R320x480)){
            resolutionCode = 1;
        } else if(resolutionStr.equals(R1366x768)){
            resolutionCode = 2;
        }
        try(RandomAccessFile raf = new RandomAccessFile(CONFIG_FILE, "rw")){
            raf.writeBoolean(isSoundOn);
            raf.writeBoolean(askBeforeClose);
            raf.writeByte(resolutionCode);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(GameMenus.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            Logger.getLogger(GameMenus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void loadConfig(){
        try(RandomAccessFile raf = new RandomAccessFile(CONFIG_FILE, "rw")){
            isSoundOn = raf.readBoolean();
            askBeforeClose = raf.readBoolean();
            switch(raf.readByte()){ //Código de resolución - resolutionCode
                case 1: 
                    WIDTH = 320;
                    HEIGHT = 480;
                    break;
                case 2:
                    WIDTH = 1366;
                    HEIGHT = 768;
                    break;
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(GameMenus.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            Logger.getLogger(GameMenus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
