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

/**
 * Contiene recursos para darle estilo a cadenas de caracteres.
 * @author Jorge Maldonado Ventura
 */
public class StringFormat {
    /**
     * Centra una cadena. Si se especifiica una longitud menor a la del texto,
     * se perder� informaci�n al devolverlo centrado.
     * @param text el texto que se quiere centrar.
     * @param len el espacio para centrar ese texto
     * @return el texto centrado.
     */
    public static String center(String text, int len){
        String out = String.format("%"+len+"s%s%"+len+"s", "",text,"");
        float mid = (out.length()/2);
        float start = mid - (len/2);
        float end = start + len; 
        return out.substring((int)start, (int)end);
    }
    /**
     * Elimina los espacios en blanco a la izquierda del texto. Por texto se entiende
     * cualquier car�cter excepto el de espacio en blanco.
     * @param text la cadena cuyos car�cteres en blanco a la izquierda del texto
     * se eliminar�n.
     * @return la cadena sin espacios en blanco antes del texto.
     */
    public static String removeSpacesAtTheBeggining(String text){
        String newText = "";
        for(short i = 0; i < text.length(); i++){
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
    /**
     * Asigna a cada n�mero del uno al seis una categor�a. El 1 es para
     * Geograf�a; el 2, para Cine y espect�culos; el 3, para Historia; el 4, 
     * para Arte y literatura; el 5, para Ciencias y naturaleza; y el 6, para
     * Deportes y pasatiempos.
     * @param categoryCode el n�mero que representa una categor�a.
     * @return el nombre de la categor�a seleccionada.
     */
    public static String formatCategory(byte categoryCode){
        String categoryString = "";
        switch(categoryCode){
                case 1:
                    categoryString = "Geograf�a";
                    break;
                case 2: 
                    categoryString = "Cine y espect�culos";
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
    /**
     * Asigna a cada n�mero del uno al tres un tipo de respuesta. El 1 corresponde
     * a una respuesta simple; el 2, a una del tipo s�-o-no; y el 3, a una respuesta
     * m�ltiple.
     * @param type el n�mero que representa a una respuesta.
     * @return el nombre del tipo de respuesta seleccionado.
     */
    public static String formatTypeOfAnswer(byte type){
        String typeStr = "";
        switch(type){
            case 1:
                typeStr = "Simple";
                break;
            case 2: 
                typeStr = "Del tipo s�-o-no";
                break;
            case 3: 
                typeStr = "M�ltiple";
                break;
        }
        return typeStr;
    }
    /*Para cuando implemente el acceso directo
    public static String adjustSpace(String palabra, int space) {
        byte[] bytes = palabra.getBytes();
        int realSpace = bytes.length - palabra.length() ;
        space = space - realSpace;
        String s = "%"+ space +"s";
        return s;
    }*/
}
