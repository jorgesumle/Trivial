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

package Console;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Respuesta de tipo simple. La respuesta tiene una longitud máxima de 30
 * caracteres.
 * @author Jorge Maldonado Ventura 
 */
public class SimpleAnswer extends Answer{
    /**
     * Escribe una respuesta simple al final del fichero de respuestas.
     */
    public static void addSimpleAnswer() {
        String answer = String.format("%" + Answer.ANSWER_LENGTH + "s", Input.answerInput("Escribe la respuesta.\n>>> "));
        answerObj = new SimpleAnswer(Question.questionObj.getCode(), false, Question.questionObj.getCategory(), answer);
        try (final RandomAccessFile raf = new RandomAccessFile(Answer.answersFile, "rw")) {
            long sizeOfAnswersFile = raf.length();
            raf.seek(sizeOfAnswersFile);
            answerObj.answerWriter(raf);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Crea una respuesta simple predeterminada.
     */
    public SimpleAnswer() {
        code = 0;
        deleted = true;
        category = 0;
        answer = "";
        TYPE_OF_ANSWER = 1;
    }
    /**
     * Crea una respuesta simple
     * @param code el código de la respuesta.
     * @param deleted el estado de la respuesta.
     * @param category la categoría de la respuesta.
     * @param answer la respuesta correcta.
     */
    public SimpleAnswer(int code, boolean deleted, byte category, String answer) {
        this.code = code;
        this.deleted = deleted;
        this.category = category;
        this.answer = answer;
        TYPE_OF_ANSWER = 1;
    }
    /**
     * Escribe lee instancia de este objeto mediante el acceso directo.
     * @param raf contiene información sobre el fichero del que se va a leer
     * el objeto y la posición donde comenzará la lectura.
     */
    @Override
    public void answerReader(RandomAccessFile raf) {
        try{
            code = raf.readInt();
            raf.readByte();
            deleted = raf.readBoolean();
            category = raf.readByte();
            answer = raf.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(SimpleAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        } catch (IOException ex) {
            Logger.getLogger(SimpleAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * La representación del objeto del tipo respuesta simple.
     * @return la representación del objeto del tipo respuesta simple con cada
     * atributo en una línea y una cabecera que pone el tipo de respuesta.
     */
    @Override
    public String toString() {
        return "RESPUESTA SIMPLE\n"
                + "\n"
                + "Código: " + code + ".\n"
                + "Categoría: " + StringFormat.formatCategory(category) + ".\n"
//                + "Borrada: " + deleted + ".\n"
                + "Respuesta: " + answer + ".\n";        
    }

    //Por acceso secuencial
    public static ArrayList<SimpleAnswer> getSimpleAnswers(ArrayList<Answer> answers){
        ArrayList<SimpleAnswer> simpleAnswers = new ArrayList<>();
        for(int i = 0; i < answers.size(); i++){
            if(answers.get(i).TYPE_OF_ANSWER == 1){
                simpleAnswers.add((SimpleAnswer)answers.get(i));
            }
        }
        return simpleAnswers;
    }
    
    /*
    Por acceso directo
    */
    /*
    public static ArrayList<SimpleAnswer> getSimpleAnswers(ArrayList<Answer> answers){
        ArrayList<SimpleAnswer> simpleAnswers = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(answersFile, "r")){
            byte type;
            while(raf.getFilePointer() < raf.length()){
                type = Answer.readType(raf);
                if(type == 1){
                    answerObj.answerReader(raf);
                    simpleAnswers.add((SimpleAnswer)answerObj);
                } else if(type == 2){
                    raf.seek();
                } else{
                    
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleAnswer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    */
}
