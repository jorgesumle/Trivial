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

import java.util.ArrayList;

public class Query {
    final static String QUESTIONS_HEADER = "Preguntas";
    final static String CATEGORY_HEADER = "Categorías";
    final static String ANSWERS_HEADER = "Respuestas";

    public static void showQuestions(){
        ArrayList<Question> questions = EditMode.getQuestions();
        ArrayList<String> category = new ArrayList<>();
        int sizeOfLongestQuestion = 0;
        for(int i = 0; i < questions.size(); i++){
            questions.get(i).setQuestion(StringFormat.removeSpacesAtTheBeggining(questions.get(i).question));
            category.add(StringFormat.formatCategory(questions.get(i).getCategory()));
            if(questions.get(i).question.length() > sizeOfLongestQuestion)
                sizeOfLongestQuestion = questions.get(i).question.length();
        }
        //Revísalo, no funciona del todo bien.
        int divisor = 2;
        if(sizeOfLongestQuestion == 1){
            divisor = 1;
        }
        
        System.out.format("+%" + (sizeOfLongestQuestion) +"s+%n", StringFormat.repeatChar('-', sizeOfLongestQuestion + 21));
        System.out.printf("|%" + (sizeOfLongestQuestion / divisor) + "s%s|%-21s|%n", QUESTIONS_HEADER, StringFormat.repeatChar(' ', sizeOfLongestQuestion / divisor), CATEGORY_HEADER);
        System.out.printf("+%" + (sizeOfLongestQuestion) +"s+%n", StringFormat.repeatChar('-', sizeOfLongestQuestion + 21));
        for(int i = 0; i < questions.size(); i++){
            System.out.printf("|%-" + sizeOfLongestQuestion + "s|%-21s|%n", questions.get(i).question, category.get(i));
        }
        System.out.printf("+%-" + (sizeOfLongestQuestion) +"s+%n", StringFormat.repeatChar('-', sizeOfLongestQuestion + 21));
    }
    public static void showAnswers(){
        ArrayList<Answer> answers = EditMode.getAnswers();
        int sizeOfLongestAnswer = 0;
        for(int i = 0; i < answers.size(); i++){
            if(((MultipleAnswer)(answers.get(i))).TYPE_OF_ANSWER == 3){ //Está bien, pero no funciona.
                byte correctAnswer = ((MultipleAnswer)(answers.get(i))).correctAnswer;
                switch(correctAnswer){
                    case 1: 
                        break;
                    case 2: 
                        ((MultipleAnswer)(answers.get(i))).setAnswer(((MultipleAnswer)(answers.get(i))).answer2);
                        break;
                    case 3:
                        ((MultipleAnswer)(answers.get(i))).setAnswer(((MultipleAnswer)(answers.get(i))).answer3);
                        break;
                    case 4:
                        ((MultipleAnswer)(answers.get(i))).setAnswer(((MultipleAnswer)(answers.get(i))).answer4);
                        break;
                    case 5:
                        ((MultipleAnswer)(answers.get(i))).setAnswer(((MultipleAnswer)(answers.get(i))).answer5);
                        break;
                }
            }
            answers.get(i).setAnswer(StringFormat.removeSpacesAtTheBeggining(answers.get(i).answer));
            if(answers.get(i).answer.length() > sizeOfLongestAnswer)
                sizeOfLongestAnswer = answers.get(i).answer.length();
        }
        //Revísalo, no funciona del todo bien.
        int divisor = 2;
        if(sizeOfLongestAnswer == 1){
            divisor = 1;
        }
        System.out.format("+%" + (sizeOfLongestAnswer) +"s+%n", StringFormat.repeatChar('-', sizeOfLongestAnswer));
        System.out.printf("|%" + (sizeOfLongestAnswer / divisor) + "s%s|%n", ANSWERS_HEADER, StringFormat.repeatChar(' ', sizeOfLongestAnswer / divisor));
        System.out.printf("+%" + (sizeOfLongestAnswer) +"s+%n", StringFormat.repeatChar('-', sizeOfLongestAnswer));
        for(Answer answer: answers){
            System.out.printf("|%-" + sizeOfLongestAnswer + "s|%n", answer.answer);
        }
        System.out.printf("+%-" + (sizeOfLongestAnswer) +"s+%n", StringFormat.repeatChar('-', sizeOfLongestAnswer));
    }

    static void showSimpleQuestionsAndAnswers() {
        ArrayList<Question> questions = EditMode.getQuestions();
        ArrayList<String> category = new ArrayList<>();
        ArrayList<Answer> answers = EditMode.getAnswers();
        
        ArrayList<SimpleAnswer> simpleAnswers = new ArrayList<>();
        ArrayList<Question> simpleQuestions = new ArrayList<>();
        
        int sizeOfLongestQuestion = 0;
        int sizeOfLongestAnswer = 0;
        
        for(int i = 0; i < questions.size(); i++){
            if(((SimpleAnswer)(answers.get(i))).TYPE_OF_ANSWER == 1){ //Peta en el casting
                answers.get(i).setAnswer(StringFormat.removeSpacesAtTheBeggining(answers.get(i).answer));
                simpleAnswers.add((SimpleAnswer)answers.get(i));
                if(answers.get(i).answer.length() > sizeOfLongestAnswer)
                    sizeOfLongestAnswer = answers.get(i).answer.length();
                
                //Añade las preguntas simples y su categoría.
                questions.get(i).setQuestion(StringFormat.removeSpacesAtTheBeggining(questions.get(i).question));
                category.add(StringFormat.formatCategory(questions.get(i).getCategory()));
                simpleQuestions.add(questions.get(i));
                
                if(questions.get(i).question.length() > sizeOfLongestQuestion)
                    sizeOfLongestQuestion = questions.get(i).question.length();
                }
        }
        int questionDivisor = 2;
        if(sizeOfLongestQuestion == 1){
            questionDivisor = 1;
        }
        
        int answerDivisor = 2;
        if(sizeOfLongestAnswer == 1){
            answerDivisor = 1;
        }
        //Muestra la tabla
        System.out.format("+%" + (sizeOfLongestQuestion) +"s%" + (sizeOfLongestAnswer) +"s+%n", StringFormat.repeatChar('-', sizeOfLongestQuestion + 21 + sizeOfLongestAnswer));
        System.out.printf("|%" + (sizeOfLongestQuestion / questionDivisor) + "s%s|%-21s|%" + (sizeOfLongestAnswer / answerDivisor) + "s%s|%n", QUESTIONS_HEADER, StringFormat.repeatChar(' ', sizeOfLongestQuestion / questionDivisor), CATEGORY_HEADER, ANSWERS_HEADER, StringFormat.repeatChar(' ', sizeOfLongestAnswer / answerDivisor));
        System.out.format("+%" + (sizeOfLongestQuestion) +"s%" + (sizeOfLongestAnswer) +"s+%n", StringFormat.repeatChar('-', sizeOfLongestQuestion + 21 + sizeOfLongestAnswer));
        for(int i = 0; i < questions.size(); i++){
            System.out.printf("|%-" + sizeOfLongestQuestion + "s|%-21s|%-" + sizeOfLongestAnswer + "s|%n", simpleQuestions.get(i).question, category.get(i), simpleAnswers.get(i).answer);
        }
        System.out.format("+%" + (sizeOfLongestQuestion) +"s%" + (sizeOfLongestAnswer) +"s+%n", StringFormat.repeatChar('-', sizeOfLongestQuestion + 21 + sizeOfLongestAnswer));
    }
}
