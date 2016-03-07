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

package Console;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Respuesta de tipo m�ltiple. Existen cinco opciones y solo una respuesta correcta. Las
 * opciones no pueden tener m�s de 30 caracteres. Esta clase tambi�n contiene m�todos para
 * a�adir una respuesta de tipo m�ltiple al fichero de respuestas, para escritura y para lectura de objetos de tipo
 * MultipleAnswer.
 * @author Jorge Maldonado Ventura 
 */
public class MultipleAnswer extends Answer{
    String answer2;
    String answer3;
    String answer4;
    String answer5;
    byte correctAnswer;
    /**
     * Escribe una respuesta m�ltiple al final del fichero de respuestas.
     */
    public static void addMultipleAnswer() {
        boolean validData = false;
        String answer;
        String answer2;
        String answer3;
        String answer4;
        String answer5;
        byte correctAnswer = 0;
        do {
            System.out.println("Primero debes introducir todas las posibles respuestas. Despu�s tendr�s que especificar cu�l es la correcta");
            answer = String.format("%" + Answer.ANSWER_LENGTH + "s", Input.answerInput("Escribe la primera respuesta.\n>>> "));
            answer2 = String.format("%" + Answer.ANSWER_LENGTH + "s", Input.answerInput("Escribe la segunda respuesta.\n>>> "));
            answer3 = String.format("%" + Answer.ANSWER_LENGTH + "s", Input.answerInput("Escribe la tercera respuesta.\n>>> "));
            answer4 = String.format("%" + Answer.ANSWER_LENGTH + "s", Input.answerInput("Escribe la cuarta respuesta.\n>>> "));
            answer5 = String.format("%" + Answer.ANSWER_LENGTH + "s", Input.answerInput("Escribe la quinta respuesta.\n>>> "));
            String again = Input.input(String.format("Estas son las preguntas que has introducido:%n" + "    1) %s%n    2) %s%n    3) %s%n    4) %s%n    5) %s%nPara continuar pulsa 'Enter'. Escribe 'n' si has cometido alg�n error y quieres introducir de nuevo las respuestas. " + "%n>>> ", StringFormat.removeSpacesAtTheBeggining(answer), StringFormat.removeSpacesAtTheBeggining(answer2), StringFormat.removeSpacesAtTheBeggining(answer3), StringFormat.removeSpacesAtTheBeggining(answer4), StringFormat.removeSpacesAtTheBeggining(answer5)));
            if (!again.equals("n")) {
                correctAnswer = Input.byteInput(String.format("\u00bfCu\u00e1l de las respuestas que has introducido es la correcta?%n" + "    1) %s%n    2) %s%n    3) %s%n    4) %s%n    5) %s%n>>> ", StringFormat.removeSpacesAtTheBeggining(answer), StringFormat.removeSpacesAtTheBeggining(answer2), StringFormat.removeSpacesAtTheBeggining(answer3), StringFormat.removeSpacesAtTheBeggining(answer4), StringFormat.removeSpacesAtTheBeggining(answer5)));
                validData = true;
            }
        } while (!validData);
        answerObj = new MultipleAnswer(Question.questionObj.getCode(), false, Question.questionObj.getCategory(), answer, answer2, answer3, answer4, answer5, correctAnswer);
        try (final RandomAccessFile raf = new RandomAccessFile(Answer.answersFile, "rw")) {
            long sizeOfQuestionsFile = raf.length();
            raf.seek(sizeOfQuestionsFile);
            answerObj.answerWriter(raf);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Crea una respuesta de tipo m�ltiple predeterminada.
     */
    public MultipleAnswer() {
        code = -1;
        deleted = true;
        category = 0;
        answer = "1"; answer2 = "2"; answer3 = "3"; answer4 = "4"; answer5 = "5";
        correctAnswer = -1;
        TYPE_OF_ANSWER = 3;
    }
    /**
     * Crea una respuesta de tipo m�ltiple.
     * @param code el c�digo de respuesta.
     * @param deleted el estado de la respuesta.
     * @param category la categor�a de la respuesta.
     * @param answer la primera opci�n.
     * @param answer2 la segunda opci�n.
     * @param answer3 la tercera opci�n.
     * @param answer4 la cuarta opci�n.
     * @param answer5 la quinta opci�n.
     * @param correctAnswer el n�mero que se refiere a la opci�n correcta.
     */
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
     * Escribe lee instancia de este objeto mediante el acceso secuencial.
     * @param raf contiene informaci�n sobre el fichero del que se va a leer
     * el objeto y la posici�n donde comenzar� la lectura.
     */
    @Override
    public void answerReader(RandomAccessFile raf) {
        try{
            code = raf.readInt();
            raf.readByte();
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
    }
    /**
     * Devuelve el n�mero que representa la respuesta correcta.
     * @return la respuesta correcta.
     */
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
    /**
     * La representaci�n del objeto del tipo de respuesta m�ltiple con
     * cada atributo en una l�nea y una cabecera que pone el tipo de respuesta.
     * @return la representaci�n del objeto MultipleAnswer.
     */
    @Override
    public String toString() {
        return "RESPUESTA M�LTIPLE\n"
                + "\n"
                + "C�digo: " + code + ".\n"
                + "Categor�a: " + StringFormat.formatCategory(category) + ".\n"
//                + "Borrada: " + deleted + ".\n"
                + "Respuesta 1: " + answer + ".\n"
                + "Respuesta 2: " + StringFormat.removeSpacesAtTheBeggining(answer2) + ".\n"
                + "Respuesta 3: " + StringFormat.removeSpacesAtTheBeggining(answer3) + ".\n"
                + "Respuesta 4: " + StringFormat.removeSpacesAtTheBeggining(answer4) + ".\n"
                + "Respuesta 5: " + StringFormat.removeSpacesAtTheBeggining(answer5) + ".\n"
                + "Respuesta correcta: Respuesta " + correctAnswer + ".\n";  
    }
    /**
     * Obtiene la segunda opci�n.
     * @return la segunda opci�n.
     */
    public String getAnswer2() {
        return answer2;
    }
    /**
     * Obtiene la tercera opci�n.
     * @return la tercera opci�n.
     */
    public String getAnswer3() {
        return answer3;
    }
    /**
     * Obtiene la cuarta opci�n.
     * @return la cuarta opci�n.
     */
    public String getAnswer4() {
        return answer4;
    }
    /**
     * Obtiene la quinta opci�n.
     * @return la quinta opci�n.
     */
    public String getAnswer5() {
        return answer5;
    }
    /**
     * Obtiene una representaci�n alfanum�rica del objeto MultipleAnswer pasado
     * como par�metro.
     * @param answerObj el objeto alfanum�rico que se quiere representar.
     * @return la representaci�n alfanum�rica del objeto MultipleAnswer pasado como
     * par�metro.
     */
    public static String getAnswerByCorrectAnswer(MultipleAnswer answerObj){
        String answerStr = "";
        switch(answerObj.getCorrectAnswer()){
            case 1: 
                answerStr = answerObj.getAnswer();
                break;
            case 2:
                answerStr = answerObj.getAnswer2();
                break;
            case 3:
                answerStr = answerObj.getAnswer3();
                break;
            case 4:
                answerStr = answerObj.getAnswer4();
                break;
            case 5:
                answerStr = answerObj.getAnswer5();
                break;
        }
        return answerStr;
    }
}
