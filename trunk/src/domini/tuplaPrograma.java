/**
 * La classe tuplaPrograma ens permet passar facilment de la capa de presentacio
 * a la de domini les dades d'un programa.
 * Els atributs especifics de cada categoria no s'utilitzen en aquesta practica
 * (PROP QP - 2008) ja que han quedat com a opcionals. Igualment es deixen a la tupla
 * per una possible ampliacio.
 * 
 * @author  Felip Moll
 * @version 1.0, 6 Juny 2008 
 *
 * Versio final
 */
package domini;

import java.util.Calendar;

public class tuplaPrograma {

    public String nom;
    public float preu;
    public Calendar dataCad;
    public String descripcio;
    /** Format: Normal=0, Continu=1, Directe=2)*/
    public int format;
    /**
     * Categoria ve donat per un INT de 0 a 10:
     * "Altres", "Adults", "Concurs", "Documental", "Esport","Infantil", 
     * "Musica", "Noticies", "Pelicula", "Serie", "Tertulia";
     */
    public int categoria;
    /**Parametre per el Format, ATTRF1: Duració en minuts*/
    public int duracio;
    /**Parametre per el Format, ATTRF2:iniciEmissio-> H:mm-dd-MM-yyyy*/
    public Calendar iniciEmissio;
    /** attrc1 es un atribut de la categoria, concretament per:
     *  Pelicula: director
     *  Serie: resumCapitol
     *  Tertulia: participants
     *  Esport: nomEsport
     */
    public String attrc1;
    /**attrc2 es un atribut de la categoria, concretament per:
     * Pelicula: any
     * Serie: capitol
     */
    public int attrc2;
    /**attrc3 es un atribut de la categoria, concretament per:
     * Pelicula: VO (True o False)
     * Serie: temporada (int)
     * Esport: infoExtra
     */
    public String attrc3;
    public String[] tematiques;

    public tuplaPrograma() {
        nom = "";
        preu = 0;
        dataCad = null;
        descripcio = "";
        format = -1;
        categoria = -1;
        duracio = -1;
        iniciEmissio = null;
        attrc1 = "";
        attrc2 = -1;
        attrc3 = "";
        tematiques = null;
    }
}
