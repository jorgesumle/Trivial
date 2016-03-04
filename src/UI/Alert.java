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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
public class Alert {
    /*
     * Muestra un mensaje de confirmaci�n de que se quiere cerrar la ventana y
     * adem�s difumina el contenido de la ventana contenedora. No est� correctamente
     * implementado a�n
     * @param title el t�tulo que aparece en la ventana.
     * @param message el mensaje de confirmaci�n
     * @param aPane el panel sobre el que se aplica el difuminado.
     */
    /*
    public static void exitAlert(String title, String message, Pane aPane){ 
        BoxBlur blur = new BoxBlur(aPane.getWidth() / 64, aPane.getHeight() / 64, 1);
        aPane.setEffect(blur);
        
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        
        
                    
        Label label = new Label(message);
        Button closeButton = new Button("S�");
        
        Button stayButton = new Button("No");
        
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        
        grid.add(label, 0, 0, 2, 1);
        grid.setHalignment(label, HPos.CENTER);
        
        grid.add(closeButton, 0, 1);
        grid.setHalignment(closeButton, HPos.CENTER);
        
        grid.add(stayButton, 1, 1);
        grid.setHalignment(stayButton, HPos.CENTER);
        
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
    */
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
    
}
