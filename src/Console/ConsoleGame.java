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

import java.util.ArrayList;

/**
 * Permite probar si el programa funciona correctamente jugando en la consola.
 * @author Jorge Maldonado Ventura 
 */
public class ConsoleGame {
    /**
     * Le hace una pregunta al usuario y le dice si ha acertado o no. Si no
     * ha acertado, adem�s, le informa de cu�l era la respuesta correcta.
     */
    public static void testGame() {
        ArrayList<Question> questions = Question.getQuestions();
        ArrayList<Answer> answers = Answer.getAnswers();
            
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
                        System.out.println("Error. La respuesta correcta es " + StringFormat.removeSpacesAtTheBeggining(answers.get(randomQuestion).getAnswer()) + ".");
                    }       break;
                }
            case 2:
                {
                    String answer = answers.get(randomQuestion).getAnswer();
                    String answerInput;
                    String answerStr;
                    do{
                        answerInput = Input.input("    1) S�\n    2) No\n").toLowerCase(); //A min�scula para compararlo m�s f�cilmente.
                    } while(!(answerInput.equals("1") || answerInput.equals("2") || answerInput.equals("si") || answerInput.equals("no") || answerInput.equals("s�")));
                    if(answerInput.equals("1") || answerInput.equals("si") || answerInput.equals("s�")){
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
                    
                    String answerStr = StringFormat.removeSpacesAtTheBeggining(MultipleAnswer.getAnswerByCorrectAnswer(answerObj));
                    
                    if(answerInput == answerObj.getCorrectAnswer()){
                        System.out.println("Acertaste.");
                    }
                    else{
                        System.out.println("Fallaste. La respuesta correcta es " + answerStr + ".");
                    }
                }
                break;
            default:
                break;
        }
    }

}
