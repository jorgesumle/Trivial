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
public class EditMode {
    protected static String answersFile = "respuestas.dat";
    protected static String codesFile = "cod.dat";
    protected static String questionsFile = "preguntas.dat";
    
    private final static byte QUESTION_LENGTH = 120;
    protected final static short QUESTION_OBJECT_LENGTH = 128;
    private final static short MULTIPLE_ANSWER_OBJECT_LENGTH = 160;
    private final static byte SIMPLE_ANSWER_OBJECT_LENGTH = 39;
    private final static byte YES_OR_NO_ANSWER_OBJECT_LENGTH = 10;
    private static Question questionObj;
    private static Answer answerObj;
    public static void addQuestion(){
        String question;
        do{
            question = String.format("%" + QUESTION_LENGTH + "s" , Input.input("Escribe la pregunta\n>>> "));
            if(question.length() > QUESTION_LENGTH)
                System.out.println("No se ha admitido la pregunta. Las preguntas no pueden tener m�s de " + QUESTION_LENGTH + " caracteres.");
            if(StringFormat.removeSpacesAtTheBeggining(question).length() < 1){
                System.out.println("No se ha admitido la pregunta. Las preguntas no pueden tener menos de " + 1 + " car�cter.");
            }
        } while(question.length() > QUESTION_LENGTH || StringFormat.removeSpacesAtTheBeggining(question).length() < 1);
        int code = 0;
        RandomAccessFile codeRAF = null;
        RandomAccessFile questionRAF = null;
        ArrayList<Integer> codesUsed = new ArrayList<>();
        questionObj = new Question();
        try{
            //Crea y escribe el c�digo.
            codeRAF = new RandomAccessFile(codesFile, "rw");
            for(int i = 0; i < codeRAF.length() / 4; i++){
                codesUsed.add(codeRAF.readInt());
            }
            
            if(!(codesUsed.size() == 0))
                code = (codesUsed.get(codesUsed.size() - 1)) + 1;
            
            while(codesUsed.contains(code)){
                code++;
            }
            codeRAF.seek(codeRAF.length());
            codeRAF.writeInt(code);
            //\\
            //Crea y escribe las preguntas
            questionRAF = new RandomAccessFile(questionsFile, "rw");
            long sizeOfQuestionsFile = questionRAF.length();
            questionRAF.seek(sizeOfQuestionsFile);
            questionObj = new Question(code, question, selectCategory());
            questionObj.writeQuestion(questionRAF);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                codeRAF.close();
                questionRAF.close();
            } catch (IOException ex) {
                System.out.println("No existe ninguna pregunta.");
                Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static byte selectCategory() {
        byte option = 0;
        final String categories = "    1) Geograf�a.\n"
                + "    2) Espect�culos.\n"
                + "    3) Historia.\n"
                + "    4) Arte y Literatura.\n"
                + "    5) Ciencias y Naturaleza.\n"
                + "    6) Deportes.\n";
        do{
            option = Input.byteInput("Elige una de las siguientes categor�as: \n" + categories + ">>> ");
        } while(option < 1 || option > 6);
        return option;
    }
    
    public static ArrayList<Answer> getAnswers(){
        Answer answer;
        ArrayList<Answer> answers = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(answersFile, "r")){           
            int i = 0;
            while(i < raf.length()){
                if(!(i == raf.getFilePointer())){
                    i += raf.getFilePointer() -i;
                    if(i >= raf.length())
                        break;
                }
                answer = new SimpleAnswer();
                if(answer.answerReader(raf)){
                    i += SIMPLE_ANSWER_OBJECT_LENGTH;
                    if(!answer.deleted)
                        answers.add(answer);
                } else{
                    answer = new YesOrNoAnswer();
                    if(answer.answerReader(raf)){                      
                        i += YES_OR_NO_ANSWER_OBJECT_LENGTH;
                        if(!answer.deleted)
                            answers.add(answer);
                    } else{
                        answer = new MultipleAnswer();
                        if(answer.answerReader(raf)){
                            i += MULTIPLE_ANSWER_OBJECT_LENGTH;
                            if(!answer.deleted)
                                answers.add(answer);
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No existe ninguna respuesta");
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answers;
    }
    
    public static ArrayList<Question> getQuestions() {
        Question question = new Question();
        ArrayList<Question> questions = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(questionsFile, "r")){
            for(int i = 2; i < raf.length(); i = i + QUESTION_OBJECT_LENGTH){
                if(!(i == raf.getFilePointer() + 2)){
                    i += raf.getFilePointer() - i; //Cuando escribes una tilde se almacenan dos bytes m�s.
                    if(i >= raf.length())
                        break;
                }
                question.readQuestion(raf);
                if(!question.deleted)
                    questions.add(question);
                question = new Question();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    public static void addSimpleAnswer() {
        String answer = String.format("%" + Answer.ANSWER_LENGTH +  "s", Input.answerInput("Escribe la respuesta.\n>>> "));    
        //El questionObj es la pregunta que se acaba de crear.
        answerObj = new SimpleAnswer(questionObj.getCode(), false, questionObj.getCategory(), answer);
        try(RandomAccessFile raf = new RandomAccessFile(answersFile, "rw")){
            long sizeOfAnswersFile = raf.length();                                          
            raf.seek(sizeOfAnswersFile);
            answerObj.answerWriter(raf);
        } catch (IOException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addYesOrNoAnswer() {
        String answer;
         do{
            answer = Input.input("�La respuesta es 's�' o 'no' ('s' o 'n')?\n>>> ");
            if(!(answer.equals("s�") || answer.equals("no") || answer.equals("si") || answer.equals("s") || answer.equals("n"))){
                System.out.println("Las respuestas permitidas son 's�', 'no', 'si', 's' y 'n'. Prueba de  nuevo.");
            }
        } while(!(answer.equals("s�") || answer.equals("no") || answer.equals("si") || answer.equals("s") || answer.equals("n")));
        
        if(answer.equals("s�") || answer.equals("si") || answer.equals("s")){
            answer = "s";
        } else{
            answer = "n";
        }
        answerObj = new YesOrNoAnswer(questionObj.getCode(), false, questionObj.getCategory(), answer);
        try(RandomAccessFile raf = new RandomAccessFile(answersFile, "rw")){
            long sizeOfAnswersFile = raf.length();                                          
            raf.seek(sizeOfAnswersFile);
            answerObj.answerWriter(raf);
        } catch (IOException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addMultipleAnswer() {
        boolean validData = false;
        String answer, answer2, answer3, answer4, answer5;
        byte correctAnswer = 0;
        do{
            System.out.println("Primero debes introducir todas las posibles respuestas. "
                    + "Despu�s tendr�s que especificar cu�l es la correcta");
            answer = String.format("%" + Answer.ANSWER_LENGTH +  "s", Input.answerInput("Escribe la primera respuesta.\n>>> "));
            answer2 = String.format("%" + Answer.ANSWER_LENGTH +  "s", Input.answerInput("Escribe la segunda respuesta.\n>>> "));
            answer3 = String.format("%" + Answer.ANSWER_LENGTH +  "s", Input.answerInput("Escribe la tercera respuesta.\n>>> "));
            answer4 = String.format("%" + Answer.ANSWER_LENGTH +  "s", Input.answerInput("Escribe la cuarta respuesta.\n>>> "));
            answer5 = String.format("%" + Answer.ANSWER_LENGTH +  "s", Input.answerInput("Escribe la quinta respuesta.\n>>> "));
                        
            String again = Input.input(String.format("Estas son las preguntas que has introducido:%n"
                    + "    1)%s%n    2)%s%n    3)%s%n    4)%s%n    5)%s%nPara continuar pulsa 'Enter'. Escribe 'n' si has cometido alg�n error y "
                    + "quieres introducir de nuevo las respuestas. "
                    + "%n>>> ", answer, answer2, answer3, answer4, answer5));
            if(!again.equals("n")){
                correctAnswer = Input.byteInput(String.format("�Cu�l de las respuestas que has introducido es la correcta?%n"
                        + "    1)%s%n    2)%s%n    3)%s%n    4)%s%n    5)%s%n>>> ", answer, answer2, answer3, answer4, answer5));
                validData = true;
            }
        } while(!validData);    
        answerObj = new MultipleAnswer(
                questionObj.getCode(), 
                false, 
                questionObj.getCategory(), 
                answer,
                answer2,
                answer3,
                answer4,
                answer5,
                correctAnswer
        );
        try(RandomAccessFile raf = new RandomAccessFile(answersFile, "rw")){
            long sizeOfQuestionsFile = raf.length();
            raf.seek(sizeOfQuestionsFile);
            answerObj.answerWriter(raf);
        } catch (IOException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Elimina todas las preguntas y las respuestas de forma definitiva. El m�todo
     * sobreescribe los archivos de c�digos, preguntas y respuestas con una cadena de 
     * texto vac�a, es decir, sustituye los bytes que hab�a en el fichero por 0 bytes.
     */
    public static void removeAll() {
        //Mejoralo un poco.
        try{
            BufferedWriter answers = new BufferedWriter(new FileWriter(answersFile));
            answers.write("");
            answers.close();
            
            BufferedWriter questions = new BufferedWriter(new FileWriter(questionsFile));
            questions.write("");
            questions.close();
            
            BufferedWriter codes = new BufferedWriter(new FileWriter(codesFile));
            codes.write("");
            codes.close();
            
        } catch (IOException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Se han borrado con �xito todas las preguntas y respuestas.");
    }

    public static void removeQuestionAndAnswer(int code) {
        removeQuestion(code);
        removeAnswer(code);
    }

    private static void removeQuestion(int code) {
        int readCode = -20; //No se asignan c�digos negativos.
        boolean deleted = false;
        long positionToDelete = -1; //La posici�n donde tiene que cambiar el boolean.
        try(RandomAccessFile raf = new RandomAccessFile(questionsFile, "rw")){  
            for(int i = 2; i < raf.length(); i = i + QUESTION_OBJECT_LENGTH){
                if(!(i == raf.getFilePointer() + 2)){
                    i += raf.getFilePointer() - i; //Cuando escribes una tilde se almacenan dos bytes m�s.
                    if(i >= raf.length())
                        break;
                }
                readCode = raf.readInt();
                positionToDelete = raf.getFilePointer();
                deleted = raf.readBoolean();
                raf.readByte();
                raf.readUTF();
                
                if(readCode == code){
                    if(deleted){
                        System.out.println("Esta pregunta ya ha sido borrada.");
                        return;
                    }
                    raf.seek(positionToDelete);
                    raf.writeBoolean(true); //La borra.
                    System.out.println("Pegunta " + code + " borrada con �xito.");
                    return;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.printf("La pregunta con el c�digo %d no existe.%n", code);
    }

    private static void removeAnswer(int code) {
        try(RandomAccessFile raf = new RandomAccessFile(answersFile, "rw")){           
            int i = 0;
            int readCode = -20; //No se asignan c�digos negativos.
            byte answerType = 0;
            boolean deleted = false;
            long positionToDelete = -1; //La posici�n donde tiene que cambiar el boolean.
            while(i < raf.length()){ 
                if(!(i == raf.getFilePointer())){
                    i += raf.getFilePointer() - i;
                    if(i >= raf.length())
                        break;
                }
                readCode = raf.readInt();
                answerType = raf.readByte();
                positionToDelete = raf.getFilePointer();
                deleted = raf.readBoolean();
                raf.readByte();
                raf.readUTF();
                if(answerType == 3){
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                    raf.readByte();
                }
                
                if(readCode == code){
                    if(deleted){
                        System.out.println("Esta respuesta ya ha sido borrada.");
                        return;
                    }
                    raf.seek(positionToDelete);
                    raf.writeBoolean(true);
                    System.out.println("Respuesta " + code + " borrada con �xito.");
                    return;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No existe ninguna respuesta");
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.printf("La respuesta con el c�digo %d no existe.%n", code);
    }

    public static void modifyQuestionAndAnswer(int code) {
        modifyQuestion(code);
        modifyAnswer(code);
    }
    public static void modifyQuestion(int code){
        Query.printAnswerAndQuestionByCode(code);
        String modify= "";
        do{
            modify = Input.input("Est�s seguro de que quieres modificar esta pregunta. Si"
                + "dices que si se borrar� y deber�s introducir todos los datos"
                + "de la pregunta de nuevo.");
        } while(modify.charAt(0) != 'n' && modify.charAt(0) != 's');
        if(modify.charAt(0) == 'n')
            return;
        
        removeQuestion(code);
        addQuestion();
    }

    public static void modifyAnswer(int code) {
        Query.printAnswerAndQuestionByCode(code);
        String modify= "";
        do{
            modify = Input.input("Est�s seguro de que quieres modificar esta respuesta. Si"
                + "dices que si se borrar� y deber�s introducir todos los datos"
                + "de la respuesta de nuevo.");
        } while(modify.charAt(0) != 'n' && modify.charAt(0) != 's');
        if(modify.charAt(0) == 'n')
            return;
        
        removeAnswer(code);
        

        byte type = Input.byteInput("Cuando modificas una respuesta, puedes cambiar tambi�n su tipo.\n"
                + "Elige un tipo de respuesta:\n"
                + "    1) Simple.\n"
                + "    2) Del tipo si/no.\n"
                + "    3) De respuesta m�ltiple.\n"
                + ">>> "
        );
        switch(type){
            case 1:
                EditMode.addSimpleAnswer();
                break;
            case 2:
                EditMode.addYesOrNoAnswer();
                break;
            case 3:
                EditMode.addMultipleAnswer();
        }
    }

    public static void testGame() {
        ArrayList<Question> questions = EditMode.getQuestions();
        ArrayList<Answer> answers = EditMode.getAnswers();
            
        int randomQuestion = (int)(Math.random()* questions.size());
        
        
        
        Query.showQuestion(questions.get(randomQuestion));
        
        switch (answers.get(randomQuestion).TYPE_OF_ANSWER) {
            case 1:
                {
                    String answer = StringFormat.removeSpacesAtTheBeggining(answers.get(randomQuestion).getAnswer());
                    String answerInput = Input.input("");
                    if(answer.toLowerCase().equals(answerInput.toLowerCase())){
                        System.out.println("Correcto.");
                    } else{
                        System.out.println("Error. La respuesta correecta es" + answers.get(randomQuestion).getAnswer());
                    }       break;
                }
            case 2:
                {
                    String answer = answers.get(randomQuestion).getAnswer();
                    byte answerInput;
                    String answerStr;
                    do{
                        answerInput = Input.byteInput("    1) S�\n    2) No\n");
                    } while(answerInput != 1 && answerInput != 2);
                    if(answerInput == 1){
                        answerStr = "s";
                    } else{
                        answerStr = "n";
                    }       if(answerStr.equals(answer)){
                        System.out.println("Has acertado.");
                    } else{
                        System.out.println("Fallaste.");
                    }       break;
                }
            case 3:
                {
                    MultipleAnswer answerObj = (MultipleAnswer)answers.get(randomQuestion);
                    byte answerInput;
                    do{
                        //Lista las preguntas, pide un n�mero por teclado y lo asigna a answerInput.
                        answerInput = Input.byteInput(String.format("    1) %s%n    2) %s%n    3) %s%n    4) %s%n    5) %s%n", answerObj.answer, answerObj.answer2, answerObj.answer3, answerObj.answer4, answerObj.answer5));
                    } while(answerInput < 1 || answerInput > 5);
                    if(answerInput == answerObj.getCorrectAnswer()){
                        System.out.println("Acertaste.");
                    }
                    else{
                        System.out.println("Fallaste");
                    }
                }
                break;
            default:
                break;
        }
    }
}
