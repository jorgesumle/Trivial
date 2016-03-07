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
 * Se encarga de crear cuadros de diálogo personalizados.
 * @author Jorge Maldonado Ventura 
 */
public class MyAlerts {
    /**
     * Muestra un mensaje de confirmación que oregunta al usuario si quiere cerrar la ventana y
     * además difumina el contenido de la ventana contenedora.
     * @param title el título que aparece en la ventana.
     * @param message el mensaje de confirmación
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
        
        Button closeButton = new Button("Sí");
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
    */
}
