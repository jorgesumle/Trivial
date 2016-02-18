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

package MinitrivialB�sico;
import java.io.RandomAccessFile;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
abstract class Answer{
    public int code;
    public byte category;
    public boolean deleted;
    public String answer;
    public byte TYPE_OF_ANSWER;
    
    protected final static byte ANSWER_LENGTH = 30;
    
    
    
    public abstract boolean answerReader(RandomAccessFile raf);
    public abstract void answerWriter(RandomAccessFile raf);
    @Override
    public abstract String toString();

    public int getCode() {
        return code;
    }

    /*public byte getTypeOfAnswer() {
        return typeOfAnswer;
    }*/

    public byte getCategory() {
        return category;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
}
