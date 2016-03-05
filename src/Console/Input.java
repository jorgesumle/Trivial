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

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Se encarga de las operaciones de entrada de datos por teclado.
 * @author Jorge Maldonado Ventura 
 */
public class Input {
    /**
     * Introduce una respuesta por teclado. Las respuestas no pueden tener más de 30 caracteres.
     * @param message el mensaje que aparece al preguntar por la respuesta.
     * @return la respuesta introducida.
     */
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
    /**
     * Introduce un número entre -128 y 127 por teclado.
     * @param message el mensaje que aparece al preguntar por el número.
     * @return el número introducido.
     */
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
    /**
     * Introduce un número entre -2147483648 y 2147483647 por teclado.
     * @param message el mensaje que aparece al preguntar por el número.
     * @return el número introducido.
     */
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
    /**
     * Elige una de las seis categorías disponibles por teclado. Las categorías son las siguientes: 1) Geografía,
     * 2) Cine y espectáculos, 3) Historia, 4) Arte y literatura, 5) Ciencias y naturaleza y 6) Deportes y
     * pasatiempos.
     * @return el número de la categoría elegida.
     */
    public static byte selectCategory() {
        byte option = 0;
        final String categories = "    1) Geografía.\n"
                + "    2) Cine y espectáculos.\n"
                + "    3) Historia.\n"
                + "    4) Arte y Literatura.\n"
                + "    5) Ciencias y Naturaleza.\n"
                + "    6) Deportes y pasatiempos.\n";
        do{
            option = Input.byteInput("Elige una de las siguientes categorías: \n" + categories + ">>> ");
        } while(option < 1 || option > 6);
        return option;
    }
}
