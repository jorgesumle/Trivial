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

package MinitrivialBásico;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
public class Input {
    public static String answerInput(String message){
        String answer;
        do{
            answer = Input.input(message);
            if(answer.length() > Answer.ANSWER_LENGTH)
                System.out.println("No se ha admitido la respuesta. Las respuestas no pueden tener más de " + Answer.ANSWER_LENGTH + " caracteres.");
            if(StringFormat.removeSpacesAtTheBeggining(answer).length() < 1){
                System.out.println("No se ha admitido la respuesta. Debe haber una respuesta.");
        }
        } while(answer.length() > Answer.ANSWER_LENGTH || StringFormat.removeSpacesAtTheBeggining(answer).length() < 1);
        return answer;
    }
    public static byte byteInput(String message){
        System.out.print(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean validAnswer = false;
        byte value = 0;
        while (!validAnswer){
            try{
                value = Byte.parseByte(br.readLine()); validAnswer = true;
            }
            catch(Exception e){
                System.out.print("Por favor introduce un valor adecuado. \n>>>");
            }
        }
        return value;
    }
    public static String input(String message){
        System.out.print(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean validAnswer = false;
        String value = "";
        while (!validAnswer){
            try{
                value = br.readLine(); validAnswer = true;
            }
            catch(Exception e){
                System.out.print("Por favor introduce un valor adecuado. \n>>>");
            }
        }
        return value;
    }
    public static int intInput(String message){
        System.out.print(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean validAnswer = false;
        int value = 0;
        while (!validAnswer){
            try{
                value = Integer.parseInt(br.readLine()); validAnswer = true;
            }
            catch(Exception e){
                System.out.print("Por favor introduce un valor adecuado. \n>>>");
            }
        }
        return value;
    }
    public static String questionInput(){
        String question;
        do{
            question = String.format("%" + Question.QUESTION_LENGTH + "s" , Input.input("Escribe la pregunta\n>>> "));
            if(question.length() > Question.QUESTION_LENGTH)
                System.out.println("No se ha admitido la pregunta. Las preguntas no pueden tener más de " + Question.QUESTION_LENGTH + " caracteres.");
            if(StringFormat.removeSpacesAtTheBeggining(question).length() < 1){
                System.out.println("No se ha admitido la pregunta. Las preguntas no pueden tener menos de " + 1 + " carácter.");
            }
        } while(question.length() > Question.QUESTION_LENGTH || StringFormat.removeSpacesAtTheBeggining(question).length() < 1);
        return question;
    }
    public static byte selectCategory() {
        byte option = 0;
        final String categories = "    1) Geografía.\n"
                + "    2) Espectáculos.\n"
                + "    3) Historia.\n"
                + "    4) Arte y Literatura.\n"
                + "    5) Ciencias y Naturaleza.\n"
                + "    6) Deportes.\n";
        do{
            option = Input.byteInput("Elige una de las siguientes categorías: \n" + categories + ">>> ");
        } while(option < 1 || option > 6);
        return option;
    }
}
