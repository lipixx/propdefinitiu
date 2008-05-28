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
    public GestorDiscException(String s) {

        super(s);
    }
}
