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

import java.io.BufferedWriter;
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
    protected static String questionsFile = "preguntas.dat";
    final static byte ANSWER_LENGTH = 30;
    final static byte QUESTION_LENGTH = 120;
    //Cuidado al modificar QUESTION_OFFSET, podría desbordarse la variable byte.
    final static byte QUESTION_OFFSET = QUESTION_LENGTH + 1 + 4;
    
    public static void addQuestion(){
        
    }
    public static void listQuestionsAndAnswers(){
        ArrayList<Question> simpleQuestion = new ArrayList<>();
        ArrayList<Question> yesOrNoQuestion = new ArrayList<>();
        ArrayList<Question> multipleQuestion = new ArrayList<>();
        
        ArrayList<SimpleAnswer> simpleAnswer = new ArrayList<>();
        ArrayList<YesOrNoAnswer> yesOrNoAnswer = new ArrayList<>();
        ArrayList<MultipleAnswer> multipleAnswer = new ArrayList<>();
        
        Question questionObj = null;     
        Answer answerObj = null;
        try{
            RandomAccessFile questionsRAF = new RandomAccessFile(questionsFile, "r");
            RandomAccessFile answersRAF = new RandomAccessFile(answersFile, "r");
            for(int i = 0; i < questionsRAF.length(); i = i + QUESTION_OFFSET + 2){
                questionObj.readQuestion(questionsRAF);

                if(questionObj.getCode() == 1){
                    answerObj = new SimpleAnswer();
                    answerObj.answerReader(answersRAF);
                    
                    simpleQuestion.add(questionObj);
                    simpleAnswer.add((SimpleAnswer)answerObj);
                } 
                else if(questionObj.getCode() == 2){
                    answerObj = new YesOrNoAnswer();
                    answerObj.answerReader(answersRAF);
                    
                    yesOrNoQuestion.add(questionObj);
                    yesOrNoAnswer.add((YesOrNoAnswer)answerObj);
                } 
                else if(questionObj.getCode() == 3){
                    answerObj = new MultipleAnswer();
                    answerObj.answerReader(answersRAF);
                    
                    multipleQuestion.add(questionObj);
                    multipleAnswer.add((MultipleAnswer)answerObj);
                }
            }
            questionsRAF.close();
            for(int i = 0; i < questionsRAF.length(); i = i + QUESTION_OFFSET + 2){
                
            }
            answersRAF.close();
            final int averageLengthOfASimpleAnswer = 3;
            
            //Muestra las preguntas simples
            System.out.println("PREGUNTAS SIMPLES\n");
            System.out.printf("%" + QUESTION_OFFSET / 2 + "s|%s" + (QUESTION_OFFSET / 2) + averageLengthOfASimpleAnswer + "%", "Preguntas", "Respuestas");
            for(int i = 0; i < simpleQuestion.size(); i++){
                System.out.printf("%s|s" + (QUESTION_OFFSET / 2) + averageLengthOfASimpleAnswer + "%", simpleQuestion.get(i).question, simpleAnswer.get(i).answer);
            }
            System.out.println();
            
            //Muestra las preguntas de sí o no.
            System.out.println("PREGUNTAS DE SÍ O NO\n");
            System.out.printf("%" + QUESTION_OFFSET / 2 + "s|%s", "Preguntas", "Respuestas");
            for(int i = 0; i < yesOrNoQuestion.size(); i++){
                System.out.printf("%s|s%", yesOrNoQuestion.get(i).question, yesOrNoAnswer.get(i).answer);
            }
            System.out.println();
            
            //Muestra las preguntas de respuesta múltiple.
             System.out.println("PREGUNTAS DE RESPUESTA MÚLTIPLE\n");
             System.out.printf("%" + QUESTION_OFFSET / 2 + "s|%s|%s|%s|%s|%s", "Preguntas", "Respuesta1", "Respuesta2", "Respuesta3", "Respuesta4", "Respuesta5");
            for(int i = 0; i < multipleQuestion.size(); i++){
                System.out.printf("%s|s%", multipleQuestion.get(i).question, multipleAnswer.get(i).answer);
            }
            System.out.println();
        } catch (IOException ex) {
            Logger.getLogger(Trivial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void removeAll() {
        try{
            BufferedWriter answers = new BufferedWriter(new FileWriter(answersFile));
            answers.write("");
            answers.close();
            
            BufferedWriter questions = new BufferedWriter(new FileWriter(questionsFile));
            questions.write("");
            questions.close();
            
        } catch (IOException ex) {
            Logger.getLogger(EditMode.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Se han borrado con éxito todas las preguntas y respuestas.");
    }
}
