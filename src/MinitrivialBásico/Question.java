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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
public class Question {
    public int code;
    public byte category;
    public boolean deleted;
    public String question;
    
    public final static byte QUESTION_LENGTH = 120;
    protected static String questionsFile = "preguntas.dat";
    protected static Question questionObj;
    protected final static short QUESTION_OBJECT_LENGTH = 128;
    
    public static void removeDeletedQuestions() {
        ArrayList<Question> questions = getQuestions();
        Question.removeQuestionsPermanently();
        try (final RandomAccessFile raf = new RandomAccessFile(questionsFile, "rw")) {
            for (int i = 0; i < questions.size(); i++) {
                questions.get(i).writeQuestion(raf);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modifyQuestion(int code) {
        Query.printAnswerAndQuestionByCode(code);
        String modify = "";
        do {
            modify = Input.input("Est�s seguro de que quieres modificar esta pregunta. Si " + "dices que s�, se borrar� y deber�s introducir todos los datos" + "de la pregunta de nuevo.\n>>> ");
        } while (modify.charAt(0) != 'n' && modify.charAt(0) != 's');
        if (modify.charAt(0) == 'n') {
            return;
        }
        removeQuestion(code);
        Question.addQuestion();
        Question.appendQuestion();
    }

    /**
     * Elimina todas las preguntas de forma definitiva. Sobreescribe el archivo de preguntas
     * con una cadena de texto vac�a, es decir, sustituye los bytes que hab�a en el
     * fichero por 0 bytes.
     */
    public static void removeQuestionsPermanently() {
        try (final BufferedWriter questions = new BufferedWriter(new FileWriter(questionsFile))) {
            questions.write("");
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void removeQuestion(int code) {
        int readCode = -20; //No se asignan c�digos negativos.
        boolean deleted = false;
        long positionToDelete = -1; //La posici�n donde tiene que cambiar el boolean.
        try (final RandomAccessFile raf = new RandomAccessFile(questionsFile, "rw")) {
            for (int i = 2; i < raf.length(); i = i + QUESTION_OBJECT_LENGTH) {
                if (!(i == raf.getFilePointer() + 2)) {
                    i += raf.getFilePointer() - i;
                    if (i >= raf.length()) {
                        break;
                    }
                }
                readCode = raf.readInt();
                positionToDelete = raf.getFilePointer();
                deleted = raf.readBoolean();
                raf.readByte();
                raf.readUTF();
                if (readCode == code) {
                    if (deleted) {
                        System.out.println("Esta pregunta ya ha sido borrada.");
                        return;
                    }
                    raf.seek(positionToDelete);
                    raf.writeBoolean(true);
                    System.out.println("Pegunta " + code + " borrada con \u00e9xito.");
                    return;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.printf("La pregunta con el c�digo %d no existe.%n", code);
    }

    public static ArrayList getQuestions() {
        Question question = new Question();
        ArrayList<Question> questions = new ArrayList<>();
        File file = new File(questionsFile);
        if (!file.exists()) {
            return new ArrayList<Question>();
        }
        try (final RandomAccessFile raf = new RandomAccessFile(questionsFile, "r")) {
            for (int i = 2; i < raf.length(); i = i + QUESTION_OBJECT_LENGTH) {
                if (!(i == raf.getFilePointer() + 2)) {
                    i += raf.getFilePointer() - i;
                    if (i >= raf.length()) {
                        break;
                    }
                }
                question.readQuestion(raf);
                if (!question.deleted) {
                    questions.add(question);
                }
                question = new Question();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }
    
    
    
    public Question() {
        code = 0;
        question = "";
        deleted = true;
        category = 0;
    }

    public Question(int code, String question, byte category) {
        this.code = code;
        this.question = question;
        this.deleted = false; //No puedes crear una pregunta borrada.
        this.category = category;
    }

    public int getCode() {
        return code;
    }

    public byte getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    /**
     * Escribe lee instancia de este objeto mediante el acceso directo.
     * @param raf contiene informaci�n sobre el fichero del que se va a leer
     * el objeto y la posici�n donde comenzar� la lectura.
     */
    public void readQuestion(RandomAccessFile raf){
        try{
            code = raf.readInt();
            deleted = raf.readBoolean();
            category = raf.readByte();
            question = raf.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Escribe una instancia de este objeto mediante el acceso directo.
     * @param raf contiene informaci�n sobre el fichero en que se va a guardar
     * el objeto y la posici�n del fichero en la que se va a guardar.
     */
    public void writeQuestion(RandomAccessFile raf){
        try{
            raf.writeInt(code);
            raf.writeBoolean(deleted);
            raf.writeByte(category);
            raf.writeUTF(question);
        } catch (IOException ex) {
            Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public String toString() {
        return "PREGUNTA\n"
                + "\n"
                + "C�digo: " + code + ".\n"
                + "Categor�a: " + StringFormat.formatCategory(category) + ".\n"
//                + "Borrada: " + deleted + ".\n"
                + "Pregunta: " + question + ".\n";
    }
    public static void addQuestion(){
        String question = Input.questionInput();
        int code = 0;
        
        ArrayList<Integer> codesUsed = new ArrayList<>();
        ArrayList<Question> questions = getQuestions();
        
         //Crea y escribe el c�digo.
        for(int i = 0; i < questions.size(); i++){
            codesUsed.add(questions.get(i).getCode());
        }
        while(codesUsed.contains(code)){
            if(!(code >= Integer.MAX_VALUE)) //== tambi�n funcionar�a.
                code++;
            else{
                code = Integer.MIN_VALUE;
            }
        }
        
        questionObj = new Question(code, question, Input.selectCategory());
            
    }
    public static void appendQuestion(){
        try(RandomAccessFile questionRAF = new RandomAccessFile(questionsFile, "rw")){
            //Crea y escribe las preguntas
            long sizeOfQuestionsFile = questionRAF.length();
            questionRAF.seek(sizeOfQuestionsFile);
            questionObj.writeQuestion(questionRAF);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("No existe ninguna pregunta"); //rev�salo.
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*public static void getSimpleQuestions(ArrayList<Question> questions){
        ArrayList<Question> simpleQuestions = new ArrayList<>();
        for(int i = 0; i < questions.size(); i++){
            if()
        }
    }*/
}
