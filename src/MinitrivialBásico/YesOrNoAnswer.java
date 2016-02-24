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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
public class YesOrNoAnswer extends Answer{

    public static void addYesOrNoAnswer() {
        String answer;
        do {
            answer = Input.input("\u00bfLa respuesta es 's\u00ed' o 'no' ('s' o 'n')?\n>>> ");
            if (!(answer.equals("s\u00ed") || answer.equals("no") || answer.equals("si") || answer.equals("s") || answer.equals("n"))) {
                System.out.println("Las respuestas permitidas son 's\u00ed', 'no', 'si', 's' y 'n'. Prueba de  nuevo.");
            }
        } while (!(answer.equals("s\u00ed") || answer.equals("no") || answer.equals("si") || answer.equals("s") || answer.equals("n")));
        if (answer.equals("s\u00ed") || answer.equals("si") || answer.equals("s")) {
            answer = "s";
        } else {
            answer = "n";
        }
        answerObj = new YesOrNoAnswer(Question.questionObj.getCode(), false, Question.questionObj.getCategory(), answer);
        try (final RandomAccessFile raf = new RandomAccessFile(Answer.answersFile, "rw")) {
            long sizeOfAnswersFile = raf.length();
            raf.seek(sizeOfAnswersFile);
            answerObj.answerWriter(raf);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Crea una una respuesta vac�a: 
     *     code = 0;
     *     deleted = true;
     *     category = 0;
     *     answer = "";
     */
    public YesOrNoAnswer() {
        code = 0;
        deleted = true;
        category = 0;
        answer = "";
        TYPE_OF_ANSWER = 2;
    }
    
    public YesOrNoAnswer(int code, boolean deleted, byte category, String answer) {
        this.code = code;
        this.deleted = deleted;
        this.category = category;
        this.answer = answer;
        TYPE_OF_ANSWER = 2;
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
            raf.readByte();
            deleted = raf.readBoolean();
            category = raf.readByte();
            answer = raf.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(SimpleAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
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
        } catch (IOException ex) {
            Logger.getLogger(SimpleAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    @Override
    public String toString() {
        return "RESPUESTA DEL TIPO S�-O-NO\n"
                + "\n"
                + "C�digo: " + code + ".\n"
                + "Categor�a: " + StringFormat.formatCategory(category) + ".\n"
//                + "Borrada: " + deleted + ".\n"
                + "Respuesta: " + answer + ".\n";
    }
}
