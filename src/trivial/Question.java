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

/**
 *
 * @author Jorge Maldonado Ventura 
 */
abstract class Question {
    public final int code;
    public final String question;
    public final byte type;

    public Question(int code, String question, byte type) {
        this.code = code;
        this.question = question;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getQuestion() {
        return question;
    }

    public byte getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Question{" + "code=" + code + ", question=" + question + ", type=" + type + '}';
    }
    
}
