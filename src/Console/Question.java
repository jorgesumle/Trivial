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
     * Código de la respuesta.
     */
    public int code;
    /**
     * Categoría de la respuesta.
     */
    public byte category;
    /**
     * El estado de la pregunta. Su valor es <i>true</i> si está borrada; <i>false</i> si no.
     */
    public boolean deleted;
    /**
     * La pregunta. Tiene una longitud máxima de 150 caracteres.
     */
    public String question;
    /**
     * La longitud máxima de la pregunta.
     */
    public final static short QUESTION_LENGTH = 150;
    /**
     * La ruta en la que se guardan las preguntas cuando así se indica.
     */
    protected static String questionsFile = "src/preguntas.dat";
    /**
     * Se utiliza para guardar Question creado o leído de un fichero.
     */
    protected static Question questionObj;
    protected final static short QUESTION_OBJECT_LENGTH = 158; //arréglalo para el acceso directo.
    /**
     * Elimina las preguntas que ya habían sido marcadas como borradas.
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
 * @param code el código de la pregunta que se quiere modificar.
 */
    public static void modifyQuestion(int code) {
        Query.printAnswerAndQuestionByCode(code);
        String modify = "";
        do {
            modify = Input.input("Estás seguro de que quieres modificar esta pregunta. Si dices que sí, se borrará y deberás introducir todos los datos de la pregunta de nuevo.\n>>> ");
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
     * con una cadena de texto vacía, es decir, sustituye los bytes que había en el
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
     * @param code el código de la pregunta que se quiere borrar.
     */
    public static void removeQuestion(int code) {
        int readCode = -20; //No se asignan códigos negativos.
        boolean deleted = false;
        long positionToDelete = -1; //La posición donde tiene que cambiar el boolean.
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
                    System.out.println("Pegunta " + code + " borrada con éxito.");
                    return;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.printf("La pregunta con el código %d no existe.%n", code);
    }
    /**
     * Obtiene todas las preguntas que no están marcadas como borradas.
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
     * @param code el código de la pregunta.
     * @param question la pregunta.
     * @param category la categoría.
     */
    public Question(int code, String question, byte category) {
        this.code = code;
        this.question = question;
        this.deleted = false; //No puedes crear una pregunta ya borrada.
        this.category = category;
    }
    /**
     * Obtiene el código de la pregunta.
     * @return el código de la pregunta.
     */
    public int getCode() {
        return code;
    }
    /**
     * Obtiene la categoría de la pregunta
     * @return la categoría de la pregunta.
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
     * Devuelve el estado de la pregunta. Su valor es <i>true</i> si está borrada; <i>false</i> si no.
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
     * @param raf contiene información sobre el fichero del que se va a leer
     * el objeto y la posición donde comenzará la lectura.
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
     * @param raf contiene información sobre el fichero en que se va a guardar
     * el objeto y la posición del fichero en la que se va a guardar.
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
     * La representación del objeto de tipo pregunta.
     * @return la representación del objeto de tipo pregunta.
     */
    @Override
    public String toString() {
        return "PREGUNTA\n"
                + "\n"
                + "Código: " + code + ".\n"
                + "Categoría: " + StringFormat.formatCategory(category) + ".\n"
//                + "Borrada: " + deleted + ".\n"
                + "Pregunta: " + question + ".\n";
    }
    public static void addQuestion(){
        String question = Input.questionInput();
        int code = 0;
        
        ArrayList<Integer> codesUsed = new ArrayList<>();
        ArrayList<Question> questions = getQuestions();
        
         //Crea y escribe el código.
        for(int i = 0; i < questions.size(); i++){
            codesUsed.add(questions.get(i).getCode());
        }
        while(codesUsed.contains(code)){
            if(!(code >= Integer.MAX_VALUE)) //== también funcionaría.
                code++;
            else{
                code = Integer.MIN_VALUE;
            }
        }
        questionObj = new Question(code, question, Input.selectCategory());       
    }
    
    /*Usando acceso directo, sin implementar aún. Futura versión.
    public static void addQuestion(){
        String question = Input.questionInput();
        int code = 0;
        
        ArrayList<Integer> codesUsed = getCodes();
        ArrayList<Question> questions = getQuestions();

        while(codesUsed.contains(code)){
            if(!(code >= Integer.MAX_VALUE)) //== también funcionaría.
                code++;
            else{
                code = Integer.MIN_VALUE;
            }
        }
        questionObj = new Question(code, question, Input.selectCategory());
    }*/
    
    /*Usando acceso directo, sin implementar aún. Futura versión.
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
     * Añade una pregunta al final del fichero de preguntas.
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
            System.out.println("No existe ninguna pregunta"); //revísalo.
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Obtiene el objeto pregunta conociendo su código por acceso secuencial.
     * @param code código que se quiere obtener.
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
