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
package Console;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class StringFormat {
    public static String center(String text, int len){
        String out = String.format("%"+len+"s%s%"+len+"s", "",text,"");
        float mid = (out.length()/2);
        float start = mid - (len/2);
        float end = start + len; 
        return out.substring((int)start, (int)end);
    }
    /**
     * Elimina los espacios en blanco a la izquierda del texto. Por texto se entiende
     * cualquier carácter excepto el de espacio en blanco.
     * @param text la cadena cuyos carácteres en blanco a la izquierda del texto
     * se eliminarán.
     * @return la cadena sin espacios en blanco antes del texto.
     */
    public static String removeSpacesAtTheBeggining(String text){
        String newText = "";
        for(byte i = 0; i < text.length(); i++){
            if(!(text.charAt(i) == ' ')){
                newText = text.substring(i);
                break;
            }
        }
        return newText;
    }
    /**
     * Repite un carácter el número de veces que se le indique. Los caracteres repetidos
     * formarán una cadena. Si repetimos el carácter 'x' cinco veces, obtendremos la siguiente
     * cadena: "xxxxx".
     * @param character el carácter a repetir
     * @param times las veces que se repetirá cada carácter
     * @return la cadena resultante de repetir carácteres
     */
    public static String repeatChar(char character, int times){
        String newString = "";
        for(int i = 0; i < times; i++){
            newString += character;
        }
        return newString;
    }
    
    public static String formatCategory(byte categoryCode){
        String categoryString = "";
        switch(categoryCode){
                case 1:
                    categoryString = "Geografía";
                    break;
                case 2: 
                    categoryString = "Cine y espectáculos";
                    break;
                case 3: 
                    categoryString = "Historia";
                    break;
                case 4: 
                    categoryString = "Arte y literatura";
                    break;
                case 5: 
                    categoryString = "Ciencias y naturaleza";
                    break;
                case 6: 
                    categoryString = "Deportes y pasatiempos";
                    break;
        }
        return categoryString;
    }
    public static String formatTypeOfAnswer(byte type){
        String typeStr = "";
        switch(type){
            case 1:
                typeStr = "Simple";
                break;
            case 2: 
                typeStr = "Del tipo sí-o-no";
                break;
            case 3: 
                typeStr = "Múltiple";
                break;
        }
        return typeStr;
    }
}
