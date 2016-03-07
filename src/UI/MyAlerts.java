/*
 * Copyright (C) 2016 Jorge Maldonado Ventura 
 *
 * Este programa es software libre: usted puede redistruirlo y/o modificarlo
 * bajo los t�rminos de la Licencia P�blica General GNU, tal y como est� publicada por
 * la Free Software Foundation; ya sea la versi�n 2 de la Licencia, o
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

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Se encarga de crear cuadros de di�logo personalizados.
 * @author Jorge Maldonado Ventura 
 */
public class MyAlerts {
    /**
     * Muestra un mensaje de confirmaci�n que oregunta al usuario si quiere cerrar la ventana y
     * adem�s difumina el contenido de la ventana contenedora.
     * @param title el t�tulo que aparece en la ventana.
     * @param message el mensaje de confirmaci�n
     * @param aPane el panel sobre el que se aplica el difuminado.
     */
    public static void exitAlert(String title, String message, Pane aPane){ 
        Stage window = new Stage();
        window.setOnCloseRequest(e -> 
            {   
                window.close();
                aPane.setEffect(null);
            }
        );
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        
        Button closeButton = new Button("S�");
        closeButton.setMinWidth(125);
        Button stayButton = new Button("No");
        stayButton.setMinWidth(125);
        
        HBox buttons = new HBox();
        buttons.setSpacing(5);
        buttons.getChildren().addAll(closeButton, stayButton);
        
        
        
                    
        Label label = new Label(message);
        
        
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        
        grid.add(label, 0, 0);
        grid.setHalignment(label, HPos.CENTER);
        
        grid.add(buttons, 0, 1);
        
        stayButton.setOnAction(e -> 
            {
                window.close();
                aPane.setEffect(null);
                
            }
        );
        
        closeButton.setOnAction(e -> System.exit(0));
        
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
    }
    /**
     * Muestra una ventana informativa simple con el mensaje que queramos
     * @param message el mensaje que muestra la ventana de alerta.
     */
    public static void infoAlert(String message){
        Alert warning = new Alert(AlertType.INFORMATION, message);
        warning.showAndWait();
    }
    /*
    public static void exitAlert(String title, String message){
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        
        
                    
        Label label = new Label(message);
        
        Button closeButton = new Button("S�");
        closeButton.setMinWidth(100);
        
        Button stayButton = new Button("No");
        stayButton.setMinWidth(100);
        
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(2);
        grid.setVgap(7);
        
        grid.add(label, 0, 0, 3, 1);
        grid.setHalignment(label, HPos.CENTER);
        
        grid.add(closeButton, 1, 1);
        
        grid.add(stayButton, 2, 1);
        
        stayButton.setOnAction(e -> 
            {
                window.close();     
            }
        );
        
        closeButton.setOnAction(e -> System.exit(0));
        
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
    }
    */
}
