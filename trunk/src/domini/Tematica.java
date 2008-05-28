/**
 * La classe Tematica del Domini proveeix l'objecte amb els atributs i els metodes
 * corresponents.
 * 
 * @author  Felip Moll
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini;

import java.io.Serializable;

public class Tematica implements ClasseAmbClau<String>, Serializable {

    private String nomTema;

    public Tematica(String nouNom) {
        nomTema = new String(nouNom.toLowerCase());
    }

    /**
     *          Consultor del nom de la tematica.
     *  @return Un String que representa el nom d'aquesta tematica.
     */
    public String getTema() {
        return new String(nomTema);
    }

    /**
     *          Modificador del nom de la tematica.
     *   @pre    -
     *   @post  S'ha modificat el nom d'aquest Tema.
     *
     */
    public void setTema(String nouNom) {
        nomTema = nouNom.toLowerCase();
    }

    public String getClau() {
        return nomTema;
    }

    public void setCopia(ClasseAmbClau objecteACopiar) {
        objecteACopiar = new Tematica(nomTema);
    }
}
