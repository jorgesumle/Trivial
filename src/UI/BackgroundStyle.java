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

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
public class BackgroundStyle {
    /**
     * Asigna a un panel una imagen que se autoajusta sin perder la relaci�n de aspecto.
     */
    public static void setResizableBackground(Pane pane, String imageURL){
        pane.setBackground(
            new javafx.scene.layout.Background(
                    new BackgroundImage(                             
                            new Image(imageURL), 
                            BackgroundRepeat.NO_REPEAT, 
                            BackgroundRepeat.NO_REPEAT, 
                            BackgroundPosition.CENTER, 
                            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
                    )
            )
        );
    }
}
