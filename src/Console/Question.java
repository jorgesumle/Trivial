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

package Console;

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
 * La clase Question representa las respuestas del videojuego.
 * @author Jorge Maldonado Ventura 
 */
public class Question {
    /**
     * C�digo de la respuesta.
     */
    public int code;
    /**
     * Categor�a de la respuesta.
     */
    public byte category;
    /**
     * El estado de la pregunta. Su valor es <i>true</i> si est� borrada; <i>false</i> si no.
     */
    public boolean deleted;
    /**
     * La pregunta. Tiene una longitud m�xima de 150 caracteres.
     */
    public String question;
    /**
     * La longitud m�xima de la pregunta.
     */
    public final static short QUESTION_LENGTH = 150;
    /**
     * La ruta en la que se guardan las preguntas cuando as� se indica.
     */
    protected static String questionsFile = "src/preguntas.dat";
    /**
     * Se utiliza para guardar Question creado o le�do de un fichero.
     */
    protected static Question questionObj;
    protected final static short QUESTION_OBJECT_LENGTH = 158; //arr�glalo para el acceso directo.
    /**
     * Elimina las preguntas que ya hab�an sido marcadas como borradas.
     */
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
/**
 * Modifica una pregunta de un fichero.
 * @param code el c�digo de la pregunta que se quiere modificar.
 */
    public static void modifyQuestion(int code) {
        Query.printAnswerAndQuestionByCode(code);
        String modify = "";
        do {
            modify = Input.input("Est�s seguro de que quieres modificar esta pregunta. Si dices que s�, se borrar� y deber�s introducir todos los datos de la pregunta de nuevo.\n>>> ");
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
    /**
     * Marca una pregunta como borrada.
     * @param code el c�digo de la pregunta que se quiere borrar.
     */
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
                    System.out.println("Pegunta " + code + " borrada con �xito.");
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
    /**
     * Obtiene todas las preguntas que no est�n marcadas como borradas.
     * @return las preguntas no marcadas como borradas.
     */
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
    
    
    /**
     * Crea una pregunta predeterminada.
     */
    public Question() {
        code = 0;
        question = "";
        deleted = true;
        category = 0;
    }
    /**
     * Crea una pregunta con los datos indicados por el usuario.
     * @param code el c�digo de la pregunta.
     * @param question la pregunta.
     * @param category la categor�a.
     */
    public Question(int code, String question, byte category) {
        this.code = code;
        this.question = question;
        this.deleted = false; //No puedes crear una pregunta ya borrada.
        this.category = category;
    }
    /**
     * Obtiene el c�digo de la pregunta.
     * @return el c�digo de la pregunta.
     */
    public int getCode() {
        return code;
    }
    /**
     * Obtiene la categor�a de la pregunta
     * @return la categor�a de la pregunta.
     */
    public byte getCategory() {
        return category;
    }
    /**
     * Obtiene la pregunta
     * @return la pregunta.
     */
    public String getQuestion() {
        return question;
    }
    /**
     * Asigna una pregunta al objeto.
     * @param question la pregunta que se quiere asignar.
     */
    public void setQuestion(String question) {
        this.question = question;
    }
    /**
     * Devuelve el estado de la pregunta. Su valor es <i>true</i> si est� borrada; <i>false</i> si no.
     * @return 
     */
    public boolean isDeleted() {
        return deleted;
    }
    /**
     * Marca una pregunta como borrada o no borrada.
     * @param deleted 
     */
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
    /**
     * La representaci�n del objeto de tipo pregunta.
     * @return la representaci�n del objeto de tipo pregunta.
     */
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
    
    /*Usando acceso directo, sin implementar a�n. Futura versi�n.
    public static void addQuestion(){
        String question = Input.questionInput();
        int code = 0;
        
        ArrayList<Integer> codesUsed = getCodes();
        ArrayList<Question> questions = getQuestions();

        while(codesUsed.contains(code)){
            if(!(code >= Integer.MAX_VALUE)) //== tambi�n funcionar�a.
                code++;
            else{
                code = Integer.MIN_VALUE;
            }
        }
        questionObj = new Question(code, question, Input.selectCategory());
    }*/
    
    /*Usando acceso directo, sin implementar a�n. Futura versi�n.
    public static ArrayList<Integer> getCodes(){
        ArrayList<Integer> codesUsed = new ArrayList<>();
        try(RandomAccessFile codesRAF = new RandomAccessFile(questionsFile, "rw")){
            long sizeOfQuestionsFile = codesRAF.length();
            while(codesRAF.getFilePointer() < sizeOfQuestionsFile){
                codesUsed.add(codesRAF.readInt());
                codesRAF.seek(QUESTION_OBJECT_LENGTH);
            }
            for(int i = 0; i < codesUsed.size(); i++){
                System.out.println(codesUsed.get(i));
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codesUsed;
    }*/
    /**
     * A�ade una pregunta al final del fichero de preguntas.
     */
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
    /**
     * Obtiene el objeto pregunta conociendo su c�digo por acceso secuencial.
     * @param code c�digo que se quiere obtener.
     * @return el objeto Question solicitado si existe, si no existe devuelve <i>null</i>.
     */
    public static Question getQuestionByCode(int code){
        Question question = null;
        ArrayList<Question> questions = getQuestions();
        for(int i = 0; i < questions.size(); i++){
            if(questions.get(i).getCode() == code){
                question = questions.get(i);
                break;
            }
        }
        return question;
    }
}
