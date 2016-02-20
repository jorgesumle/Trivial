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

package MinitrivialBásico;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
public abstract class Answer{
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

    /**
     * Le el tipo de respuesta de un fichero e informa de su tipo.
     * @param raf contiene información sobre el fichero del que se va a leer
     * @return 1, si se trata de un objeto SimpleAnswer; 2, si es un objeto YesOrNoAnswer;
     * y 3, para MultipleAnswer.
     */
    public static byte readType(RandomAccessFile raf){
        byte type = 0;
        try{
            long initialPos = raf.getFilePointer();
            raf.seek(initialPos + 4);
            type = raf.readByte();
            raf.seek(initialPos);
        } catch (IOException ex) {
            Logger.getLogger(Answer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return type;
    }
}
