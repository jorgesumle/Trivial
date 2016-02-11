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

package trivial;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
public class Output {
    public static void writeUTFByRandomAccess(RandomAccessFile raf, long position, String text, String file){
        try{
            raf.seek(position);
            raf.writeUTF(text);
        } catch (IOException ex) {
            Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void writeBooleanByRandomAccess(RandomAccessFile raf, long position, boolean bool, String file){ 
        try{
            raf.seek(position);
            raf.writeBoolean(bool);
        } catch (IOException ex) {
            Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void writeIntegerByRandomAccess(RandomAccessFile raf, long position, int number, String file){ 
        try{
            raf.seek(position);
            raf.writeInt(number);
        } catch (IOException ex) {
            Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
