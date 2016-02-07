/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
