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
public class MultipleAnswer extends Answer{
    String answer2;
    String answer3;
    String answer4;
    String answer5;

    public MultipleAnswer() {
        code = -1;
        deleted = true;
        answer = "1"; answer2 = "2"; answer3 = "3"; answer4 = "4"; answer5 = "5";
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public MultipleAnswer(int code, boolean deleted, String answer, String answer2, String answer3, String answer4, String answer5) {
        this.code = code;
        this.deleted = deleted;
        this.answer = answer;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer5 = answer5;
    }
    
    @Override
    public void check(int code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void answerWriter(RandomAccessFile raf) {
        try{
            raf.writeInt(code);
            raf.writeBoolean(deleted);
            raf.writeUTF(answer);
            raf.writeUTF(answer2);
            raf.writeUTF(answer3);
            raf.writeUTF(answer4);
            raf.writeUTF(answer5);
        } catch (IOException ex) {
            Logger.getLogger(MultipleAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void answerReader(RandomAccessFile raf) {
        try{
            raf.readInt();
            raf.readBoolean();
            raf.readUTF(); //1
            raf.readUTF(); //2
            raf.readUTF(); //3
            raf.readUTF(); //4
            raf.readUTF(); //5
        } catch (IOException ex) {
            Logger.getLogger(MultipleAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public String toString(){
        return String.format("Código de respuesta: %d. Borrada: %b. Respuesta1: %s. Respuesta2: %s. Respuesta3: %s. Respuesta4: %s. Respuesta5: %s", code, deleted, answer, answer2, answer3, answer4, answer5);
    }
}
