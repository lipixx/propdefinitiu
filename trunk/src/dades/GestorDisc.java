package dades;


import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.LinkedList;

/**
 * Controlador pseudo-generic que implementa la persistencia d'objectes a memoria secundaria.
 * 
 * @author Jordi Cuadrado
 * @param T
 * Es el Tipus de classe que es vol guardar/recuperar d'un determiant fitxer de disc.
 * La classe T haura d'implementar Serializable amb l'import java.io.Serializable;
 * 
 * P.ex. si volem guardar Clients haurem de construir un GestorDisc de la seguent manera:
 * //Sobre una instancia de la classe que volem guardar/llegir de disc anomenada cliente1
 * String clase = cliente1.getClass().getName();
 * GestorDisc<Client> guardador = new GestorDisc(clase);
 */
@SuppressWarnings("unchecked")
public class GestorDisc<T> {

    private String clase;

    /**
     * Constructora de GestorDisc de la clase indicada en el parametro clase
     * @param clase
     * Indica sobre que tipo de objetos trabajara el gestor.
     * 
     * Este atributo es necesario para poder controlar que en un archivo solo se
     * escriban datos del mismo tipo (para evitar que p.ej. se escriban Clientes y
     * Servicios juntos en un mismo fichero) y que luego a la hora de leerlos tambien
     * sean del mismo tipo que los que se requiere. (P.ej. si quiero leer de un fichero
     * Clientes pero indico un archivo que contiene Servicios saltara una excepcion)
     * 
     * Nota: Java NO permite saber cual es parametro T generico en tiempo de ejecucion
     *       por eso tenemos que pasar esta informacion en forma de String
     */
    public GestorDisc(String clase) {
        this.clase = clase;
    }

    /**
     * Guarda al final (concatena) del archivo nomArxiu el elemento de tipo de T
     * @param elem
     * Es el elemento de tipo T a guardar al final del fichero
     * @param nomArxiu
     * Es el nombre del fichero en disco
     * @throws dades.GestorDiscException
     */
    public void guardaUn(T elem, String nomArxiu) throws GestorDiscException {
        try {
            String formato = obteFormatFitxer(nomArxiu);

            if (formato.equals(clase) || formato.equals("fichero_vacio") || formato.equals("no_existe_fichero")) {
                //El formato es el mismo que con lo que vamos a trabajar o tenemos un nuevo archivo
                FileOutputStream arxiuOut = new FileOutputStream(nomArxiu, true);
                //El true significa que es Appendable
                BufferEscripturaDisc output = new BufferEscripturaDisc(arxiuOut);

                output.writeObject(elem);

                output.flush();  //Buidem (fem flush) de les dades a l'arxiu (l'stream)
                output.close();  //Tanquem el buffer d'escriptura 
                arxiuOut.close(); //Tanquem l'escriptura a l'arxiu                
            } else { //El formato del archivo es otro del que con el que trabajamos
                throw new GestorDiscException("Error al guardar. Els elements continguts al fitxer:\n" +
                        nomArxiu + "\n" + "son del tipus " + formato + " enlloc de " + clase);
            }
        } catch (FileNotFoundException e) { //Viene dada por el FileOutputStream
            String causaError = e.getMessage();
            throw new GestorDiscException("No s'ha trobat l'arxiu indicat o no s'ha pogut crear.\n" +
                    "Detall de l'error: " + causaError);
        } catch (IOException e) { //Viene dada por el BufferEscripturaDisc
            String causaError = e.getMessage();
            throw new GestorDiscException("Error d'e/s. No s'ha pogut guardar l'objecte.\n" +
                    "Detall de l'error: " + causaError);
        }
    }

    /**
     * Guarda de uno en uno todos los elementos de la lista que son de la clase T.
     * Es decir, si se contruyo un nuevo GestorDisc<Client> la lista es de Client.
     * Todos estos elementos quedan concatenados al final del fichero nomArxiu.
     * @param llista
     * La lista de elementos de tipo T que contiene los elementos a guardar
     * @param nomArxiu
     * Nombre que tendra el fichero de disco donde se guardan los elementos
     * @throws dades.GestorDiscException
     */
    public void guardaTots(LinkedList<T> llista, String nomArxiu) throws GestorDiscException {
        try {
            String formato = obteFormatFitxer(nomArxiu);

            if (formato.equals(clase) || formato.equals("fichero_vacio") || formato.equals("no_existe_fichero")) {
                FileOutputStream arxiuOut = new FileOutputStream(nomArxiu, true);
                BufferEscripturaDisc output = new BufferEscripturaDisc(arxiuOut);

                int i = 0;
                int tamanyLlista = llista.size(); //La llista pot contenir com a maxim 2.147.483.647 elems

                while (i < tamanyLlista) { //Escribimos todos los elementos de la lista uno a uno
                    output.writeObject(llista.get(i));
                    i++;
                }
                output.flush();  //Buidem (fem flush) de les dades a l'arxiu
                output.close();  //Tanquem el buffer d'escriptura  
                arxiuOut.close(); //Tanquem l'escriptura a l'arxiu   
            } else { //El formato del archivo es otro del que con el que trabajamos
                throw new GestorDiscException("Error al guardar. Els elements continguts al fitxer:\n" +
                        nomArxiu + "\n" + "son del tipus " + formato + " enlloc de " + clase);
            }
        } catch (FileNotFoundException e) {
            String causaError = e.getMessage();
            throw new GestorDiscException("No s'ha trobat l'arxiu indicat o no s'ha pogut crear.\n" +
                    "Detall de l'error: " + causaError);
        } catch (IOException e) {
            String causaError = e.getMessage();
            throw new GestorDiscException("Error d'e/s. No s'ha pogut guardar l'objecte.\n" +
                    "Detall de l'error: " + causaError);
        }
    }

    /**
     * Carga de disco (uno a uno) todos los elementos del fichero nomArxiu i los
     * devuelve en una lista de elementos del mismo tipo que los originalemente guardados
     * 
     * @param nomArxiu
     * Es el nombre del fichero en disco a leer
     * @return
     * La lista de elementos del tipo indicado en el constructor que han sido leidos de nomArxiu
     * @throws dades.GestorDiscException
     */
    public LinkedList<T> carregaTots(String nomArxiu) throws GestorDiscException {

        LinkedList<T> llista = new LinkedList<T>();  //L'objecte que retornarem del fitxer llegit

        try {
            String formato = obteFormatFitxer(nomArxiu); //*** ALERTA EXCEPCIONES O ESTO FUERA DEL TRY

            if (formato.equals(clase)) {//El formato es el mismo que con lo que vamos a trabajar
                FileInputStream arxiuIn = new FileInputStream(nomArxiu);
                BufferLecturaDisc input = new BufferLecturaDisc(arxiuIn);

                while (arxiuIn.available() != 0) { //Leemos mientras no lleguemos al final del archivo
                    llista.add((T) input.readObject());
                }
                input.close(); //Hem acabat de llegir l'objecte, tanquem l'stream
                arxiuIn.close(); //Hem acabat de llegir del fitxer, el tanquem a lectura
            } else { //El formato del archivo es otro del que con el que trabajamos
                throw new GestorDiscException("Error al carregar. Els elements continguts al fitxer:\n" +
                        nomArxiu + "\n" + "son del tipus " + formato + " enlloc de " + clase);
            }
        } catch (FileNotFoundException e) { //Viene dada por la construtora de FileInputStream
            String causaError = e.getMessage(); //getMessage() retorna un String amb el detall de l'excepció
            throw new GestorDiscException("No s'ha trobat l'arxiu indicat o no s'ha pogut obrir.\n" +
                    "Detall de l'error: " + causaError);
        } catch (IOException e) { //Viene dada por el ObjectInputStream y por close de FileInputStream
            String causaError = e.getMessage();
            throw new GestorDiscException("Error d'e/s. No s'ha pogut carregar l'objecte a l'obrir l'arxiu:\n" +
                    nomArxiu + "\n" +
                    "Detall de l'error: " + causaError);
        } catch (ClassNotFoundException e) { //Viene dada por el ObjectInputStream
            String causaError = e.getMessage();
            throw new GestorDiscException("No s'ha pogut carregar l'objecte a l'obrir l'arxiu:\n" +
                    nomArxiu + "\n" +
                    "Detall de l'error: " + causaError);
        }

        return llista; //Retornem tot el que hem llegit de nomArxiu
    }

    /**
     * Esborra l'arxiu nomArxiu de disc si i nomes si es del mateix format
     * que el tipus passat al construir el GestorDisc. Aixo evita que amb un
     * GestorDisc de Client poguem esborrar fitxers que contenen Serveis, p.ex.
     * 
     * Esta operacion es util para borrar un archivo cuando p.ej. queremos regenerar
     * el fichero despues de haber borrado diversos elementos en la capa de dominio.
     * 
     * @return esborrat==true si nomArxiu se ha borrado correctamente
     */
    public boolean esborra(String nomArxiu) throws GestorDiscException {
        /* L'string nomArxiu pot contenir una ruta a subdirectoris, pero qui la de formar be, es qui
         * cridi a aquest esborra. Si no tingues un format correcte salta l'excepcio GestorDiscException.
         */
        boolean esborrat = false;
        String formato = obteFormatFitxer(nomArxiu);

        if (formato.equals(clase)) {
            File arxiu = new File(nomArxiu);

            if (arxiu.exists()) {
                esborrat = arxiu.delete();
            } else { //L'arxiu no existeix, llancem nova excepcio personalitzada
                throw new GestorDiscException("Error a l'esborrar: L'arxiu " + nomArxiu + " no existeix");
            }
        } else if (formato.equals("no_existe_fichero")) {
            throw new GestorDiscException("Error a l'esborrar: L'arxiu " + nomArxiu + " no existeix");
        } else { //El formato del archivo es otro del que con el que trabajamos
            throw new GestorDiscException("Error, no es pot esborrar. Els elements continguts al fitxer:\n" +
                    nomArxiu + "\n" + "son del tipus " + formato + " enlloc de " + clase);
        }

        return esborrat;
    }

    /**
     * Comprueba el formato del fichero. En realidad mira y retorna la clase (el tipo)
     * de los elementos guardados en el fichero nomArxiu
     * 
     * @param nomArxiu
     * Contiene el nombre del fichero que se va acomprobar su formato
     * @return
     * El string con el nombre de la clase (el tipo) de los elementos guardados en nomArxiu
     * @throws dades.GestorDiscException
     */
    private String obteFormatFitxer(String nomArxiu) throws GestorDiscException {

        String tipo = "fichero_vacio"; //Si el fichero esta vacio o es nuevo
        File arxiu = new File(nomArxiu);

        try {
            if (arxiu.exists()) {
                FileInputStream arxiuIn = new FileInputStream(nomArxiu);
                BufferLecturaDisc input = new BufferLecturaDisc(arxiuIn);

                if (arxiuIn.available() != 0) { //Si el fichero NO esta vacio comprobamos
                    Object primer = input.readObject();
                    tipo = primer.getClass().getName();
//                  //Info de debug:      
//                  System.out.println("La clase del objeto en obteFormatFitxer es " + tipo);
//                  System.out.println("La clase pasada al construir es " + clase);
                }
                input.close();
                arxiuIn.close();
            } else {
                tipo = "no_existe_fichero";
            }
        } catch (FileNotFoundException e) {
            String causaError = e.getMessage();
            throw new GestorDiscException("No s'ha trobat l'arxiu indicat o no s'ha pogut obrir.\n" +
                    "Detall de l'error: " + causaError);
        } catch (IOException e) {
            String causaError = e.getMessage();
            throw new GestorDiscException("Error d'e/s. No s'ha pogut carregar l'objecte a l'obrir l'arxiu:\n" +
                    nomArxiu + "\n" +
                    "Detall de l'error: " + causaError);
        } catch (ClassNotFoundException e) {
            String causaError = e.getMessage();
            throw new GestorDiscException("No s'ha pogut carregar l'objecte a l'obrir l'arxiu:\n" +
                    nomArxiu + "\n" +
                    "Detall de l'error: " + causaError);
        }

        return tipo;
    }   
}
