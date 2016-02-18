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
package MinitrivialB�sico;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class StringFormat {
    /**
     * Elimina los espacios en blanco a la izquierda del texto. Por texto se entiende
     * cualquier car�cter excepto el de espacio en blanco.
     * @param text la cadena cuyos car�cteres en blanco a la izquierda del texto
     * se eliminar�n.
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
     * Repite un car�cter el n�mero de veces que se le indique. Los caracteres repetidos
     * formar�n una cadena. Si repetimos el car�cter 'x' cinco veces, obtendremos la siguiente
     * cadena: "xxxxx".
     * @param character el car�cter a repetir
     * @param times las veces que se repetir� cada car�cter
     * @return la cadena resultante de repetir car�cteres
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
                    categoryString = "Geograf�a";
                    break;
                case 2: 
                    categoryString = "Espect�culos";
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
                    categoryString = "Deportes";
                    break;
        }
        return categoryString;
    }
}
