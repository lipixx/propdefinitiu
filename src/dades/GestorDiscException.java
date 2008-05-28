package dades;

/**
 * Classe especialitzada d'Exception que serveix per propagar excepcions des de 
 * GestorDisc (a la capa de dades) cap a la capa de presentacio. Aixi es poden
 * comunicar missatges d'error a l'usari que s'han generat a capes inferiors tenint 
 * una nomenclatura clara per identificar d'on venen les excepcions.
 * 
 * @author Jordi Cuadrado
 */
public class GestorDiscException extends Exception {

    /**
     * Constructora, rep el parametre s amb la informacio de l'error
     * @param s
     * Es el missatge a comunicar amb l'error produit
     */
    public GestorDiscException(String s){
        
        super(s);
    }
    
    @Override
    /**
     * Redefinicion -a no hacer nada- de la operacion fillInStackTrace para que
     * NO rellene la informacion de la pila de ejecucion, puesto que no querremos
     * depurar una excepcion relacionada con esta clase porque ya sabremos cual
     * es el error que se ha producido al estar este en el mensaje s.
     * 
     * Con esto conseguimos que la creacion de un nuevo objeto de este clase
     * -que al fin y al cabo es una Exception- sea hasta 10 veces mas rapida.
     * 
     * Extraido de: http://www.matfys.kvl.dk/~sestoft/papers/performance.pdf
     * Java performance. Reducing time and space consumption: 1.7 Exceptions 
     * de Peter Sestoft, IT University of Copenhagen
     */
    public Throwable fillInStackTrace() {
        return this;
    }
}