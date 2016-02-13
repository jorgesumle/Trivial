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

import java.io.RandomAccessFile;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
abstract class Answer{
    public int code;
    public String answer;
    public boolean deleted;
    
    public abstract void answerReader(RandomAccessFile raf);
    public abstract void answerWriter(RandomAccessFile raf);
    @Override
    public abstract String toString();
    
    public int getCode() {
        return code;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isDeleted() {
        return deleted;
    }
    
    abstract void check(int code);
}
