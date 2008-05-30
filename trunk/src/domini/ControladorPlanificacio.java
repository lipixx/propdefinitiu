/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domini;

import dades.RepositoriFranges;
import dades.RepositoriProgrames;

/**
 *
 * @author Josep Marti
 */


public class ControladorPlanificacio 
{
    private Cliente clientActual;


    public ControladorPlanificacio(RepositoriFranges RepoFranges, ControladorProgrames ControlProg, RepositoriProgrames<String, Programa> repoProgs) 
    {

    }
    
    
    /**S'ha canviat el client actual del domini, per aixo
     * se li avisa n'aquest controlador.
     * 
     * @param nouClientActual El nou client del que farem les gestions a partir d'ara
     * @pre nouClientActual no es buit
     * @post S'ha canviat la variable global de clientActual
     */
    public void setClient(Cliente nouClientActual) {
        //S'han de "resetejar" ses llistes internes d'aquest controlador
        clientActual = nouClientActual;
    }
}

