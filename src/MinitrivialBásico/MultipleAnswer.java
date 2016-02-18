/*
 * Copyright (C) 2016 Jorge Maldonado Ventura 
 *
 * Este programa es software libre: usted puede redistruirlo y/o modificarlo
 * bajo los t�rminos de la Licencia P�blica General GNU, tal y como est� publicada por
 * la Free Software Foundation; ya sea la versi�n 3 de la Licencia, o
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
     * @param raf contiene informaci�n sobre el fichero del que se va a leer
     * el objeto y la posici�n donde comenzar� la lectura.
     * @return true si la operaci�n de lectura se ha realizado con �xito; false si no.
     */
    @Override
    public boolean answerReader(RandomAccessFile raf) {
        try{
            code = raf.readInt();
            if(raf.readByte() != TYPE_OF_ANSWER){
                raf.seek(raf.getFilePointer() - 5); //Vuelve a la posici�n anterior a la ejecuci�n del m�todo.
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
     * @param raf contiene informaci�n sobre el fichero en que se va a guardar
     * el objeto y la posici�n del fichero en la que se va a guardar.
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
        return "RESPUESTA M�LTIPLE\n"
                + "\n"
                + "C�digo: " + code + ".\n"
                + "Categor�a: " + StringFormat.formatCategory(category) + ".\n"
//                + "Borrada: " + deleted + ".\n"
                + "Respuesta 1: " + answer + ".\n"
                + "Respuesta 2: " + answer2 + ".\n"
                + "Respuesta 3: " + answer3 + ".\n"
                + "Respuesta 4: " + answer4 + ".\n"
                + "Respuesta 5: " + answer5 + ".\n"
                + "Respuesta correcta: Respuesta " + correctAnswer + ".\n";  
    }
}
