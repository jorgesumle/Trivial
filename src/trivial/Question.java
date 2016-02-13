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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
public class Question {
    public final int code;
    public final String question;
    public boolean deleted;

    public Question(int code, String question) {
        this.code = code;
        this.question = question;
        this.deleted = false; //No puedes crear una pregunta borrada.
    }

    public int getCode() {
        return code;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public void readQuestion(RandomAccessFile raf){
        try{
            raf.readInt();
            raf.readBoolean();
            raf.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void writeQuestion(RandomAccessFile raf){
        try{
            raf.writeInt(code);
            raf.writeBoolean(deleted);
            raf.writeUTF(question);
        } catch (IOException ex) {
            Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public String toString() {
        return "Question{" + "code=" + code + ", question=" + question + ", deleted=" + deleted + '}';
    }

    
}
