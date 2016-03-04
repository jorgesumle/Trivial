/*
 * Copyright (C) 2016 Jorge Maldonado Ventura 
 *
 * Este programa es software libre: usted puede redistruirlo y/o modificarlo
 * bajo los términos de la Licencia Pública General GNU, tal y como está publicada por
 * la Free Software Foundation; ya sea la versión 2 de la Licencia, o
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
     * Muestra un mensaje de confirmación de que se quiere cerrar la ventana y
     * además difumina el contenido de la ventana contenedora. No está correctamente
     * implementado aún
     * @param title el título que aparece en la ventana.
     * @param message el mensaje de confirmación
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
        Button closeButton = new Button("Sí");
        
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
        
        Button closeButton = new Button("Sí");
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
