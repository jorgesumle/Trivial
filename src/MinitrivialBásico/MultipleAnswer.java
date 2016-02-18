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

package MinitrivialBásico;

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
    byte correctAnswer;
    public MultipleAnswer() {
        code = -1;
        deleted = true;
        category = 0;
        answer = "1"; answer2 = "2"; answer3 = "3"; answer4 = "4"; answer5 = "5";
        correctAnswer = -1;
        TYPE_OF_ANSWER = 3;
    }

    public MultipleAnswer(int code, boolean deleted, byte category, String answer, String answer2, String answer3, String answer4, String answer5, byte correctAnswer) {
        this.code = code;
        this.deleted = deleted;
        this.category = category;
        this.answer = answer;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer5 = answer5;
        this.correctAnswer = correctAnswer;
        TYPE_OF_ANSWER = 3;
    }
    /**
     * Escribe lee instancia de este objeto mediante el acceso directo.
     * @param raf contiene información sobre el fichero del que se va a leer
     * el objeto y la posición donde comenzará la lectura.
     * @return true si la operación de lectura se ha realizado con éxito; false si no.
     */
    @Override
    public boolean answerReader(RandomAccessFile raf) {
        try{
            code = raf.readInt();
            if(raf.readByte() != TYPE_OF_ANSWER){
                raf.seek(raf.getFilePointer() - 5); //Vuelve a la posición anterior a la ejecución del método.
                return false;
            }
            deleted = raf.readBoolean();
            category = raf.readByte();
            answer = raf.readUTF();
            answer2 = raf.readUTF();
            answer3 = raf.readUTF();
            answer4 = raf.readUTF();
            answer5 = raf.readUTF();
            correctAnswer = raf.readByte();        
        } catch (IOException ex) {
            Logger.getLogger(SimpleAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    public byte getCorrectAnswer() {
        return correctAnswer;
    }
    /**
     * Escribe una instancia de este objeto mediante el acceso directo.
     * @param raf contiene información sobre el fichero en que se va a guardar
     * el objeto y la posición del fichero en la que se va a guardar.
     */
    @Override
    public void answerWriter(RandomAccessFile raf) {
        try{
            raf.writeInt(code);
            raf.writeByte(TYPE_OF_ANSWER);
            raf.writeBoolean(deleted);
            raf.writeByte(category);
            raf.writeUTF(answer);
            raf.writeUTF(answer2);
            raf.writeUTF(answer3);
            raf.writeUTF(answer4);
            raf.writeUTF(answer5);
            raf.writeByte(correctAnswer);
        } catch (IOException ex) {
            Logger.getLogger(SimpleAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    @Override
    public String toString() {
        return "RESPUESTA MÚLTIPLE\n"
                + "\n"
                + "Código: " + code + ".\n"
                + "Categoría: " + StringFormat.formatCategory(category) + ".\n"
//                + "Borrada: " + deleted + ".\n"
                + "Respuesta 1: " + answer + ".\n"
                + "Respuesta 2: " + answer2 + ".\n"
                + "Respuesta 3: " + answer3 + ".\n"
                + "Respuesta 4: " + answer4 + ".\n"
                + "Respuesta 5: " + answer5 + ".\n"
                + "Respuesta correcta: Respuesta " + correctAnswer + ".\n";  
    }
}
