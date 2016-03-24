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

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * <i>Quaestiones</i> es un videojuego multijugador de preguntas y respuestas. 
 * Cuenta con un modo de edición para consola.
 * <p>En <i>Quaestiones</i> debes responder a diez preguntas en turnos alternos
 * con tu contrincante. Se suma un punto por cada pregunta acertada. El ganador
 * es el jugador que consiga más puntos al término de la ronda de diez preguntas.
 * Si se produce un empate en el turno 20, se seguirán haciendo preguntas hasta
 * que algún jugador falle.
 * @author Jorge Maldonado Ventura 
 */
public class Quaestiones extends Application{
    public static Stage stage;
    public static void start(){
        UI.GameMenus.createMenu();
        
        stage.setTitle("Quaestiones");
        stage.getIcons().add(new Image("icon.png"));
        stage.show();
    }
    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        start();
    }
    /**
     * @param args los parámetros de la línea de comandos
     */
    public static void main(String[] args) {
        if(args.length > 0){
            String parameter = args[0].toLowerCase();
            if(parameter.equals("editar") || parameter.equals("e") || parameter.equals("edit")){ //Creación y edición de preguntas y respuestas para el trivial.
                final String menu = "    1. Añadir una pregunta.\n"
                        + "    2. Consultar datos.\n"
                        + "    3. Buscar una pregunta y/o su respuesta.\n"
                        + "    4. Modificar una pregunta y/o su respuesta.\n" //Falta el o
                        + "    5. Borrado de preguntas y respuestas.\n"
                        + "    6. Responde a una pregunta elegida al azar.\n"
                        + "    7. Salir del programa.\n";
                byte option;
                do{
                    option = Input.byteInput("¿Qué quieres hacer?\n" + menu + ">>> ");
                    switch(option){
                        case 1:
                            addQuestion(); 
                            break;
                        case 2: //Consultar datos
                            query();
                            break;
                        case 3:
                            search();
                            break;
                        case 4:
                            modify();
                            break;
                        case 5:
                            delete();
                            break;
                        case 6:
                            ConsoleGame.testGame();
                            break;
                        case 7:
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Por favor, elige una de las opciones disponibles.");
                            break;
                    }
                } while(option != 7);
            } else if(parameter.equals("help") || parameter.equals("h") || parameter.equals("ayuda") || parameter.equals("a")){
                System.out.println("Sintaxis: Trivial [parámetro].\n"
                        + "A continuación se muestra una lista con todos los parámetros válidos:\n"
                        + "    a: Muestra este mensaje de ayuda.\n"
                        + "    ayuda: Muestra este mensaje de ayuda.\n"
                        + "    e: Inicia el modo de edición de la aplicación.\n"
                        + "    edit: Inicia el modo de edición de la aplicación.\n"
                        + "    editar: Inicia el modo de edición de la aplicación.\n"
                        + "    h: muestra este mensaje de ayuda.\n"
                        + "    help: muestra este mensaje de ayuda.\n"
                );
                System.exit(0);
            } else{
                System.out.println("No existe el parámetro " + args[0] + ". Introduce un parámetro válido,\n"
                        + "como «ayuda», que muestra todos las opciones existentes (Trivial ayuda).");
                System.exit(0);
            }
        } else{
            launch(args);
        }
    }
    private static void addQuestion() {
        byte type = 0;
        do{
            type = Input.byteInput("¿Qué tipo de pregunta quieres crear?\n"
                + "    1) Simple.\n"
                + "    2) Del tipo si/no.\n"
                + "    3) De respuesta múltiple.\n"
                + "    4) Ver un ejemplo de cada pregunta\n"
                + "    5) Volver al menú anterior\n"
                + ">>> "
            );
            if(type == 1 || type == 2 || type == 3){
                Question.addQuestion();
            }
            switch(type){
                case 1:
                    SimpleAnswer.addSimpleAnswer();
                    break;
                case 2:
                    YesOrNoAnswer.addYesOrNoAnswer();
                    break;
                case 3:
                    MultipleAnswer.addMultipleAnswer();
                    break;
                case 4:
                    String simpleQuestionExample = "¿En qué año nació Francisco Ibáñez? [pregunta] 1936 [respuesta].";
                    String yesOrNoQuestionExample = "¿Francisco Ibáñez nació en 1936? [pregunta] 'Sí' o 'No' [posibles respuestas].";
                    String multipleAnswerQuestionExample = "¿En qué año nació Francisco Ibáñez? [pregunta]"
                            + "'1936', '1939' o '1942' [posibles respuestas. La respuesta correcta debe ser una de las opciones mostradas].";
                    System.out.printf("EJEMPLO DE RESPUESTA SIMPLE%n%s%nEJEMPLO DE RESPUESTA DEL TIPO SI/NO%n%s%nEJEMPLO DE RESPUESTA"
                            + "MÚLTIPLE%n%s%n", simpleQuestionExample, yesOrNoQuestionExample, multipleAnswerQuestionExample);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Introduce una de las opciones disponibles.");
                    break;
            }
            if(type == 1 || type == 2 || type == 3){
                Question.appendQuestion();
            }
            /*Justo después de que se escriba la respuesta en el fichero.
            Así hay menos posibilidades de que se pierda información durante la escritura. El usuario no podrá crear
            inconsistencias deliberadamente, sino que tendrá que cerrar abruptamente el programa durante la operación
            de escritura, que sucede en milesimas de segundo. Así si se va la luz probablemente tampoco habrá problemas
            porque tiene que ser mucha coincidencia que se vaya justo en el instante preciso. Aun así, si se diera ese improbable
            caso y se escribiera en el fichero de preguntas y no en el de respuestas, ocasionalmente y dependiendo del tamaño
            de ambos ficheros se daría un fallo durante la partida de probabilidad de (1 / número de preguntas), que haría que no se pudiera
            continuar jugando.*/
        } while(type != 5);
    }
    /**
     * Permite listar todas las preguntas, listar todas las respuestas o volver al menú anterior.
     * Aparecerá una interfaz por consola en la que se debe especificar la acción que se desea realizar.
     */
    private static void query() {
        byte query = 0;
        ArrayList<Question> questions = Question.getQuestions();
        ArrayList<Answer> answers = Answer.getAnswers();
        String[] category = new String[questions.size()];
        for(int i = 0; i < category.length; i++){
            category[i] = (StringFormat.formatCategory(questions.get(i).getCategory()));
        }

        do{
            query = Input.byteInput("Elige una opción:\n"
                    + "    1) Ver todas las preguntas.\n"
                    + "    2) Ver todas las respuestas.\n"
                    + "    3) Volver al menú anterior.\n>>> ");

            final String QUESTIONS_HEADER = "Pregunta";
            final String ANSWERS_HEADER = "Respuesta";
            final String CORRECT_ANSWER_HEADER = "Respuesta correcta";
            String[][] fields;
            String[] headers;
            byte minFieldLength[];

            final byte MIN_LENGTH_OF_ANSWER_FIELD = (byte)ANSWERS_HEADER.length();
            final byte MIN_LENGTH_OF_CODE_FIELD = 10;
            final byte LENGTH_OF_CATEGORY_FIELD = 21;
            final byte MIN_LENGTH_OF_QUESTION_FIELD = (byte)QUESTIONS_HEADER.length();
            final byte LENGTH_OF_TYPE_OF_ANSWER = 17;

            switch(query){                      
                case 1: //    1) Ver todas las preguntas.
                    headers = new String[]{"Código", "Categoría", "Pregunta"};
                    minFieldLength = new byte[]{MIN_LENGTH_OF_CODE_FIELD, LENGTH_OF_CATEGORY_FIELD, MIN_LENGTH_OF_QUESTION_FIELD};
                    fields = new String[questions.size()][minFieldLength.length];
                    for(int i = 0; i < questions.size(); i++){
                        for(int j = 0; j < minFieldLength.length; j++){
                            switch(j){
                                case 0: 
                                    fields[i][j] = Integer.toString(questions.get(i).getCode()); 
                                    break;
                                case 1: 
                                    fields[i][j] = StringFormat.formatCategory(questions.get(i).getCategory()); 
                                    break;
                                case 2: 
                                    fields[i][j] = StringFormat.removeSpacesAtTheBeggining(questions.get(i).getQuestion()); 
                                    break;
                            }
                        }
                    }
                    Query.showTable(headers, minFieldLength, fields);
                    break;
                case 2: 
                    headers = new String[]{"Código", "Categoría", "Tipo de respuesta", "Respuesta 1", "Respuesta 2", "Respuesta 3", "Respuesta 4", "Respuesta 5", "Respuesta correcta"};
                    minFieldLength = new byte[]{MIN_LENGTH_OF_CODE_FIELD, LENGTH_OF_CATEGORY_FIELD, LENGTH_OF_TYPE_OF_ANSWER, (byte)(MIN_LENGTH_OF_ANSWER_FIELD + 2), (byte)(MIN_LENGTH_OF_ANSWER_FIELD + 2), (byte)(MIN_LENGTH_OF_ANSWER_FIELD + 2), (byte)(MIN_LENGTH_OF_ANSWER_FIELD + 2), (byte)(MIN_LENGTH_OF_ANSWER_FIELD + 2), (byte)CORRECT_ANSWER_HEADER.length()};
                    fields = new String[answers.size()][minFieldLength.length];
                    for(int i = 0; i < answers.size(); i++){
                        for(int j = 0; j < minFieldLength.length; j++){
                            try{
                                switch (j) {
                                    case 0:
                                        fields[i][j] = Integer.toString(answers.get(i).getCode());
                                        break;
                                    case 1:
                                        fields[i][j] = StringFormat.formatCategory(answers.get(i).getCategory()); 
                                        break;
                                    case 2:
                                        fields[i][j] = StringFormat.formatTypeOfAnswer(answers.get(i).TYPE_OF_ANSWER);
                                        break;
                                    case 3:
                                        fields[i][j] = StringFormat.removeSpacesAtTheBeggining(answers.get(i).getAnswer()); 
                                        //Mejora la apariencia de las preguntas del tipo sí-o-no.
                                        break;
                                    case 4:
                                        fields[i][j] = StringFormat.removeSpacesAtTheBeggining(((MultipleAnswer)(answers.get(i))).getAnswer2()); 
                                        break;
                                    case 5:
                                        fields[i][j] = StringFormat.removeSpacesAtTheBeggining(((MultipleAnswer)(answers.get(i))).getAnswer3()); 
                                        break;
                                    case 6:
                                        fields[i][j] = StringFormat.removeSpacesAtTheBeggining(((MultipleAnswer)(answers.get(i))).getAnswer4()); 
                                        break;
                                    case 7:
                                        fields[i][j] = StringFormat.removeSpacesAtTheBeggining(((MultipleAnswer)(answers.get(i))).getAnswer5()); 
                                        break;
                                    case 8:
                                        fields[i][j] = StringFormat.removeSpacesAtTheBeggining(MultipleAnswer.getAnswerByCorrectAnswer((MultipleAnswer)answers.get(i))); 
                                        break;
                                }
                            } catch(ClassCastException e){
                                fields[i][j] = "";
                            }
                        }
                    }
                    Query.showTable(headers, minFieldLength, fields);
                    //Query.showAnswers(); 
                    break;
                case 3: break;
                default: System.out.println("No has introducido una opción válida.");
            }     
        } while(query != 3);
    }
    /**
     * Permite buscar una pregunta y su respuesta por su código, buscar una pregunta por su código,
     * buscar una respuesta por su código y volver al menú anterior.
     * Aparecerá una interfaz por consola en la que se debe especificar la acción que se desea realizar.
     */
    private static void search(){
        byte search = 0;
        do{
            search = Input.byteInput("Elige una opción:\n"
                    + "    1) Buscar una pregunta y su respuesta por su código.\n"
                    + "    2) Buscar una pregunta por su código.\n"
                    + "    3) Buscar una respuesta por su código.\n"
                    + "    4) Volver al menú anterior.\n>>> ");
            switch(search){
                case 1:
                    Query.printAnswerAndQuestionByCode(Input.intInput("Introduce el código de la pregunta y la respuesta que quieres buscar\n>>> "));
                    break;
                case 2:
                    Query.printQuestionByCode(Input.intInput("Introduce el código de la pregunta que quieres buscar\n>>> "));
                    break;
                case 3:
                    Query.printAnswerByCode(Input.intInput("Introduce el código de la respuesta que quieres buscar\n>>> "));
                    break;
            }
        } while(search != 4);
    }
    /**
     * Permite modificar una pregunta y su respuesta, modificar una pregunta, modificar una respuesta y volver al menú anterior.
     * Aparecerá una interfaz por consola en la que se debe especificar la acción que se desea realizar.
     */
    private static void modify(){
        byte toModify = 0;
        do{
            toModify = Input.byteInput("Elige una opción:\n    1) Modificar una pregunta y su respuesta.\n"
                    + "    2) Modificar una pregunta.\n"
                    + "    3) Modificar una respuesta.\n"
                    + "    4) Volver al menú anterior.\n>>> ");
        } while(toModify > 4 || toModify < 1);
        switch(toModify){
            case 1:
                int code = Input.intInput("Introduce el código de la pregunta y la respuesta que quieres modificar\n>>> ");
                Question.modifyQuestion(code);
                Answer.modifyAnswer(code);
                break;
            case 2:
                Question.modifyQuestion(Input.intInput("Introduce el código de la pregunta que quieres modificar\n>>> "));
                break;
            case 3:
                Answer.modifyAnswer(Input.intInput("Introduce el código de la respuesta que quieres modificar\n>>> "));
                break;                              
        }
    }
    /**
     * Permite borrar una pregunta y su respuesta, borrar todas las preguntas, compactar los ficheros de preguntas
     * y respuestas y volver al menú anterior.
     * Aparecerá una interfaz por consola en la que se debe especificar la acción que se desea realizar.
     */
    private static void delete(){
        byte deleteOption = 0;
        do{
            deleteOption = Input.byteInput("Elige una opción:\n"
                    + "    1) Borrar una pregunta.\n"
                    + "    2) Borrar todas las preguntas.\n"
                    + "    3) Eliminar definitivamente las preguntas que ya han sido borradas.\n"
                    + "    4) Volver al menú anterior.\n>>> ");
        } while(deleteOption < 1 || deleteOption > 4);
        switch(deleteOption){
            case 1: 
                int code = Input.intInput("Introduce el código de la pregunta y la respuesta que quieres borrar\n>>> ");
                Question.removeQuestion(code);
                Answer.removeAnswer(code);
                break;
            case 2:
                //Confirmación
                String confirmation;
                do{
                    confirmation = Input.input("¿Estás seguro de que quieres eliminar todas las preguntas y respuestas? ('s' o 'n')\n>>> ").toLowerCase();
                } while(confirmation.charAt(0) != 's' && confirmation.charAt(0) != 'n');
                if(confirmation.charAt(0) == 'n'){
                    break;
                }
                //\\
                Question.removeQuestionsPermanently();
                Answer.removeAnswersPermanently();
                System.out.println("Se han borrado con éxito todas las preguntas y respuestas.");
                break;
            case 3:
                Question.removeDeletedQuestions();
                Answer.removeDeletedAnswers();
                System.out.println("Se han borrado definitivamente las preguntas borradas con éxito.");
                break;
            case 4: 
                break;
        }
    }
}
