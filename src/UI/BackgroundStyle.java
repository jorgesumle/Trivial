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
     * Asigna a un panel una imagen que se autoajusta sin perder la relación de aspecto.
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
