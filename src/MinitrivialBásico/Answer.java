/*
 * Copyright (C) 2016 Jorge Maldonado Ventura 
 *
 * Este programa es software libre: usted puede redistruirlo y/o modificarlo
 * bajo los t�rminos de la Licencia P�blica General GNU, tal y como est� publicada por
 * la Free Software Foundation; ya sea la versi�n 2 de la Licencia, o
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Maldonado Ventura 
 */
public abstract class Answer{
    public int code;
    public byte category;
    public boolean deleted;
    public String answer;
    public byte TYPE_OF_ANSWER;
    
    protected final static byte ANSWER_LENGTH = 30;
    
    protected static Answer answerObj;
    protected final static String answersFile = "respuestas.dat";

    public static void modifyAnswer(int code) {
        Query.printAnswerAndQuestionByCode(code);
        String modify = "";
        do {
            modify = Input.input("Est�s seguro de que quieres modificar esta respuesta. Si dices que s� se borrar� y deber�s introducir todos los datos de la respuesta de nuevo.\n>>> ");
        } while (modify.charAt(0) != 'n' && modify.charAt(0) != 's');
        if (modify.charAt(0) == 'n') {
            return;
        }
        removeAnswer(code);
        byte type = Input.byteInput("Cuando modificas una respuesta, puedes cambiar tambi�n su tipo.\n" + "Elige un tipo de respuesta:\n" + "    1) Simple.\n" + "    2) Del tipo si/no.\n" + "    3) De respuesta m\u00faltiple.\n" + ">>> ");
        switch (type) {
            case 1:
                SimpleAnswer.addSimpleAnswer();
                break;
            case 2:
                YesOrNoAnswer.addYesOrNoAnswer();
                break;
            case 3:
                MultipleAnswer.addMultipleAnswer();
                break;
        }
    }

    /**
     * Elimina todas las respuestas de forma definitiva. Sobreescribe el archivo de respuestas
     * con una cadena de texto vac�a, es decir, sustituye los bytes que hab�a en el
     * fichero por 0 bytes.
     */
    protected static void removeAnswersPermanently() {
        try (final BufferedWriter answers = new BufferedWriter(new FileWriter(answersFile))) {
            answers.write("");
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static void removeDeletedAnswers() {
        ArrayList<Answer> answers = getAnswers();
        Answer.removeAnswersPermanently();
        try (final RandomAccessFile raf = new RandomAccessFile(answersFile, "rw")) {
            for (int i = 0; i < answers.size(); i++) {
                answers.get(i).answerWriter(raf);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static void removeAnswer(int code) {
        try (final RandomAccessFile raf = new RandomAccessFile(answersFile, "rw")) {
            int i = 0;
            int readCode = -20; //No se asignan c�digos negativos.
            byte answerType = 0;
            boolean deleted = false;
            long positionToDelete = -1; //La posici�n donde tiene que cambiar el boolean.
            while (i < raf.length()) {
                if (!(i == raf.getFilePointer())) {
                    i += raf.getFilePointer() - i;
                    if (i >= raf.length()) {
                        break;
                    }
                }
                readCode = raf.readInt();
                answerType = raf.readByte();
                positionToDelete = raf.getFilePointer();
                deleted = raf.readBoolean();
                raf.readByte();
                raf.readUTF();
                if (answerType == 3) {
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                    raf.readUTF();
                    raf.readByte();
                }
                if (readCode == code) {
                    if (deleted) {
                        System.out.println("Esta respuesta ya ha sido borrada.");
                        return;
                    }
                    raf.seek(positionToDelete);
                    raf.writeBoolean(true);
                    System.out.println("Respuesta " + code + " borrada con \u00e9xito.");
                    return;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No existe ninguna respuesta");
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.printf("La respuesta con el c\u00f3digo %d no existe.%n", code);
    }

    public static ArrayList getAnswers() {
        Answer answer = null;
        ArrayList<Answer> answers = new ArrayList<>();
        File file = new File(answersFile);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (final RandomAccessFile raf = new RandomAccessFile(answersFile, "r")) {
            int i = 0;
            while (i < raf.length()) {
                if (!(i == raf.getFilePointer())) {
                    i += raf.getFilePointer() - i;
                    if (i >= raf.length()) {
                        break;
                    }
                }
                byte type = Answer.readType(raf);
                switch (type) {
                    case 1:
                        answer = new SimpleAnswer();
                        break;
                    case 2:
                        answer = new YesOrNoAnswer();
                        break;
                    case 3:
                        answer = new MultipleAnswer();
                        break;
                }
                answer.answerReader(raf);
                if (!answer.deleted) {
                    answers.add(answer);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No existe ninguna respuesta");
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsoleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answers;
    }
    
    
    
    
    public abstract boolean answerReader(RandomAccessFile raf);
    public abstract void answerWriter(RandomAccessFile raf);
    @Override
    public abstract String toString();

    public int getCode() {
        return code;
    }

    public byte getCategory() {
        return category;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Le el tipo de respuesta de un fichero e informa de su tipo.
     * @param raf contiene informaci�n sobre el fichero del que se va a leer
     * @return 1, si se trata de un objeto SimpleAnswer; 2, si es un objeto YesOrNoAnswer;
     * y 3, para MultipleAnswer.
     */
    public static byte readType(RandomAccessFile raf){
        byte type = 0;
        try{
            long initialPos = raf.getFilePointer();
            raf.seek(initialPos + 4);
            type = raf.readByte();
            raf.seek(initialPos);
        } catch (IOException ex) {
            Logger.getLogger(Answer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return type;
    }
}
