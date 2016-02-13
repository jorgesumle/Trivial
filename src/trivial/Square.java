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
package trivial;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class Square {
    public final byte NUMBER;
    public final byte COLOR;
    public boolean focused;
    public final boolean BONUS;
    
    public Square(byte NUMBER, byte COLOR, boolean focused, boolean BONUS) {
        this.NUMBER = NUMBER;
        this.COLOR = COLOR;
        this.focused = focused;
        this.BONUS = BONUS;
    }

    public byte getNUMBER() {
        return NUMBER;
    }
    
    public byte getCOLOR() {
        return COLOR;
    }

    public boolean isFOCUSED() {
        return focused;
    }

    public void setFOCUSED(boolean focused) {
        this.focused = focused;
    }

    public boolean isBONUS() {
        return BONUS;
    }
}
