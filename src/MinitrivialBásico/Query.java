/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinitrivialB�sico;

import java.util.ArrayList;

public class Query {
    final static String CODE_HEADER = "C�digo";
    final static String QUESTIONS_HEADER = "Pregunta";
    final static String CATEGORY_HEADER = "Categor�a";
    final static String ANSWERS_HEADER = "Respuesta";
    final static String TYPE_OF_ANSWER_HEADER = "Tipo de respuesta";
    
    final static byte LENGTH_OF_CODE_FIELD = 10;
    final static byte LENGTH_OF_CATEGORY_FIELD = 21;
    /**
     * Muestra todas las preguntas existentes en el fichero de preguntas, excepto
     * las que han sido borradas.
     */
    public static void showQuestions(){
        ArrayList<Question> questions = Question.getQuestions();
        ArrayList<String> category = new ArrayList<>();
        int lengthOfLongestQuestion = QUESTIONS_HEADER.length();
        for(int i = 0; i < questions.size(); i++){
            questions.get(i).setQuestion(StringFormat.removeSpacesAtTheBeggining(questions.get(i).question));
            category.add(StringFormat.formatCategory(questions.get(i).getCategory()));
            if(questions.get(i).question.length() > lengthOfLongestQuestion)
                lengthOfLongestQuestion = questions.get(i).question.length();
        }
        
        System.out.format("+%s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + lengthOfLongestQuestion + LENGTH_OF_CATEGORY_FIELD + 8));
        System.out.printf("| %" + LENGTH_OF_CODE_FIELD + "s | %-21s | %-" + lengthOfLongestQuestion + "s |%n", CODE_HEADER, CATEGORY_HEADER, QUESTIONS_HEADER);
        System.out.printf("+%s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + lengthOfLongestQuestion + LENGTH_OF_CATEGORY_FIELD + 8));
        for(int i = 0; i < questions.size(); i++){
            System.out.printf("| %" + LENGTH_OF_CODE_FIELD + "d | %-21s | %-" + lengthOfLongestQuestion + "s |%n", questions.get(i).getCode(), category.get(i), questions.get(i).getQuestion());
        }
        System.out.format("+%s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + lengthOfLongestQuestion + LENGTH_OF_CATEGORY_FIELD + 8));
    }
    public static void showAnswers(){
        ArrayList<Answer> answers = Answer.getAnswers();
        ArrayList<String> category = new ArrayList<>();
        
        int lengthOfLongestAnswer = ANSWERS_HEADER.length();
        for(int i = 0; i < answers.size(); i++){
            category.add(StringFormat.formatCategory(answers.get(i).getCategory()));
            try{
                if(((MultipleAnswer)(answers.get(i))).TYPE_OF_ANSWER == 3){ //Est� bien, pero no funciona.
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
            }
            catch(Exception e){}
            answers.get(i).setAnswer(StringFormat.removeSpacesAtTheBeggining(answers.get(i).answer));
            if(answers.get(i).answer.length() > lengthOfLongestAnswer)
                lengthOfLongestAnswer = answers.get(i).answer.length();
        }
        //Cabecera (columnas)
        //Tienes que a�adir el tipo de respuesta.
        System.out.format("+%" + (lengthOfLongestAnswer) +"s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + LENGTH_OF_CATEGORY_FIELD + TYPE_OF_ANSWER_HEADER.length() + lengthOfLongestAnswer));
        System.out.printf("| %" + LENGTH_OF_CODE_FIELD + "s | %-21s | %" + lengthOfLongestAnswer + "s |%n", CODE_HEADER, CATEGORY_HEADER, ANSWERS_HEADER);
        System.out.format("+%" + (lengthOfLongestAnswer) +"s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + LENGTH_OF_CATEGORY_FIELD+ lengthOfLongestAnswer));
        //Cuerpo (filas)
        for(int i = 0; i < answers.size(); i++){
            System.out.printf("| %" + LENGTH_OF_CODE_FIELD + "s | %-21s | %-" + lengthOfLongestAnswer + "s |%n", answers.get(i).getCode(), category.get(i), answers.get(i).getAnswer());
        }
        System.out.format("+%" + (lengthOfLongestAnswer) +"s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + LENGTH_OF_CATEGORY_FIELD+ lengthOfLongestAnswer));
    }
    public static void showSimpleQuestionsAndAnswers() {
        ArrayList<Question> questions = Question.getQuestions();
        ArrayList<String> category = new ArrayList<>();
        ArrayList<Answer> answers = Answer.getAnswers();
        
        ArrayList<SimpleAnswer> simpleAnswers = new ArrayList<>();
        ArrayList<Question> simpleQuestions = new ArrayList<>();
        
        int lengthOfLongestQuestion = QUESTIONS_HEADER.length();
        int lengthOfLongestAnswer = ANSWERS_HEADER.length();
        
        for(int i = 0; i < questions.size(); i++){
            if(answers.get(i).TYPE_OF_ANSWER == 1){
                answers.get(i).setAnswer(StringFormat.removeSpacesAtTheBeggining(answers.get(i).answer));
                simpleAnswers.add((SimpleAnswer)answers.get(i));
                if(answers.get(i).answer.length() > lengthOfLongestAnswer)
                    lengthOfLongestAnswer = answers.get(i).answer.length();

                //A�ade las preguntas simples y su categor�a.
                questions.get(i).setQuestion(StringFormat.removeSpacesAtTheBeggining(questions.get(i).question));
                category.add(StringFormat.formatCategory(questions.get(i).getCategory()));
                simpleQuestions.add(questions.get(i));

                if(questions.get(i).question.length() > lengthOfLongestQuestion)
                    lengthOfLongestQuestion = questions.get(i).question.length();
            }          
        }
        
        System.out.format("+%s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + lengthOfLongestQuestion + LENGTH_OF_CATEGORY_FIELD + lengthOfLongestAnswer + 11));
        System.out.printf("| %" + LENGTH_OF_CODE_FIELD + "s | %-21s | %-" + lengthOfLongestQuestion + "s | %" + lengthOfLongestAnswer + "s |%n", CODE_HEADER, CATEGORY_HEADER, QUESTIONS_HEADER, ANSWERS_HEADER); 
        System.out.format("+%s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + lengthOfLongestQuestion + LENGTH_OF_CATEGORY_FIELD + lengthOfLongestAnswer + 11));
        for(int i = 0; i < simpleQuestions.size(); i++){
            System.out.printf("| %" + LENGTH_OF_CODE_FIELD + "s | %-21s | %-" + lengthOfLongestQuestion + "s | %" + lengthOfLongestAnswer + "s |%n", questions.get(i).getCode(), category.get(i), questions.get(i).getQuestion(), simpleAnswers.get(i).getAnswer()); 
        }
        System.out.format("+%s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + lengthOfLongestQuestion + LENGTH_OF_CATEGORY_FIELD + lengthOfLongestAnswer + 11));
    }
    
    public static void showYesOrNoQuestions(){
        ArrayList<Question> questions = Question.getQuestions();
        ArrayList<String> category = new ArrayList<>();
        ArrayList<Answer> answers = Answer.getAnswers();
        
        ArrayList<String> yesOrNoAnswers = new ArrayList<>();
        ArrayList<Question> simpleQuestions = new ArrayList<>();
        
        int lengthOfLongestQuestion = QUESTIONS_HEADER.length();
        int lengthOfLongestAnswer = ANSWERS_HEADER.length();
        
        for(int i = 0; i < questions.size(); i++){
            if(answers.get(i).TYPE_OF_ANSWER == 2){
                answers.get(i).setAnswer(StringFormat.removeSpacesAtTheBeggining(answers.get(i).answer));
                if(((YesOrNoAnswer)answers.get(i)).answer.equals("s")){
                    yesOrNoAnswers.add("S�");
                } else{
                    yesOrNoAnswers.add("No");
                }
                if(answers.get(i).answer.length() > lengthOfLongestAnswer)
                    lengthOfLongestAnswer = answers.get(i).answer.length();

                //A�ade las preguntas simples y su categor�a.
                questions.get(i).setQuestion(StringFormat.removeSpacesAtTheBeggining(questions.get(i).question));
                category.add(StringFormat.formatCategory(questions.get(i).getCategory()));
                simpleQuestions.add(questions.get(i));

                if(questions.get(i).question.length() > lengthOfLongestQuestion)
                    lengthOfLongestQuestion = questions.get(i).question.length();
            }          
        }
        
        System.out.format("+%s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + lengthOfLongestQuestion + LENGTH_OF_CATEGORY_FIELD + lengthOfLongestAnswer + 11));
        System.out.printf("| %" + LENGTH_OF_CODE_FIELD + "s | %-21s | %-" + lengthOfLongestQuestion + "s | %" + lengthOfLongestAnswer + "s |%n", CODE_HEADER, CATEGORY_HEADER, QUESTIONS_HEADER, ANSWERS_HEADER); 
        System.out.format("+%s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + lengthOfLongestQuestion + LENGTH_OF_CATEGORY_FIELD + lengthOfLongestAnswer + 11));
        for(int i = 0; i < simpleQuestions.size(); i++){
            System.out.printf("| %" + LENGTH_OF_CODE_FIELD + "s | %-21s | %-" + lengthOfLongestQuestion + "s | %" + lengthOfLongestAnswer + "s |%n", questions.get(i).getCode(), category.get(i), StringFormat.removeSpacesAtTheBeggining(questions.get(i).getQuestion()), yesOrNoAnswers.get(i)); 
        }
        System.out.format("+%s+%n", StringFormat.repeatChar('-', LENGTH_OF_CODE_FIELD + lengthOfLongestQuestion + LENGTH_OF_CATEGORY_FIELD + lengthOfLongestAnswer + 11));
    }
    /**
     * Muestra la pregunta y la respuesta que se le indica. Si no existe el c�digo de respuesta
     * o de pregunta especificado se muestra un mensaje que avisa al usuario de que
     * no existe tal pregunta ni tal respuesta.
     * @param code el c�digo de la respuesta que se quiere mostrar por pantalla.
     */
    public static void printAnswerAndQuestionByCode(int code) {
        printQuestionByCode(code);
        printAnswerByCode(code);
    }
    /**
     * Muestra la respuesta que se le indica. Si no existe el c�digo de respuesta
     * especificado, se muestra el siguiente mensaje: "No existe ese c�digo de respuesta".
     * @param code el c�digo de la respuesta que se quiere mostrar por pantalla.
     */
    public static void printAnswerByCode(int code){
        ArrayList<Answer> answers = Answer.getAnswers();
        int answerPos = -1;
        for(int i = 0; i < answers.size(); i++){
            if(answers.get(i).getCode() == code){
                answerPos = i;
                break;
            }
        }
        if(answerPos == -1){
            System.out.println("No existe ese c�digo de respuesta");
        } else{
            answers.get(answerPos).setAnswer(StringFormat.removeSpacesAtTheBeggining(answers.get(answerPos).answer));
            System.out.println(answers.get(answerPos).toString());
        }
    }
    /**
     * Muestra la pregunta que se le indica. Si no existe el c�digo de pregunta
     * especificado, se muestra el siguiente mensaje: "No existe ese c�digo de pregunta".
     * @param code el c�digo de la pregunta que se quiere mostrar por pantalla.
     */
    public static void printQuestionByCode(int code){
        ArrayList<Question> questions = Question.getQuestions();
        int questionPos = -1;
        for(int i = 0; i < questions.size(); i++){
            if(questions.get(i).getCode() == code){
                questionPos = i;
                break;
            }
        }
        if(questionPos == -1){
            System.out.println("No existe ese c�digo de pregunta");
        } else{
            questions.get(questionPos).setQuestion(StringFormat.removeSpacesAtTheBeggining(questions.get(questionPos).question));
            System.out.println(questions.get(questionPos).toString());
        }
        
    }
    public static void showQuestion(Question question) {
        System.out.printf("%s%n", StringFormat.removeSpacesAtTheBeggining(question.getQuestion()));
    }
    
}
