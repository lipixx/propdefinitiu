/**
 * La classe RepositoriTematiques permet utilitzar els metodes del Controlador
 * generic de dades especialment per les tematiques.
 * 
 * 
 * @author  Felip Moll
 * @version 1, 6 Juny 2008 
 * 
 * Versio final
 */

package dades;

public class RepositoriTematiques extends Controlador<String,domini.Tematica>
{
    public RepositoriTematiques()
    {
        super("domini.Tematica","RepositoriTemes.db");
    }

        /**
     * Donat un nom de fitxer, guarda a disc totes les tematiques del repositori
     * a disc.
     * @param nomFitxer On es guardaran les tematiques.
     * @return cert Si s'han guardat correctament
     * @pre El repositori de temes no es buit.
     * @post S'han guardat les tematiques al fitxer de disc.
     * @throws dades.GestorDiscException
     */
    public boolean exportaTemes(String fitxer) throws GestorDiscException 
    {
        actualizar(fitxer);
        return true;
    }

     /**
     * Donat un nom de fitxer, carrega a memoria totes les tematiques del
     * repositori que esta a disc.
     * @param nomFitxer On es troba el repositori de temes
     * @pre El fitxer existeix i es valid
     * @post S'han carregat les tematiques a memoria
     * @return cert si s'ha fet correctament
     */
    public boolean importaTemes(String fitxer) {
        carregarDades(fitxer);
        return true;
    }
    
}
