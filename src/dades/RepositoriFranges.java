/**
 * La classe RepositoriFranges permet utilitzar els metodes del Controlador
 * generic de dades especialment per les FrangesHoraries.
 * 
 * @author  Felip Moll
 * @version 0.1, 30 Maig 2008 
 * 
 * Versió #2a Entrega#
 */

package dades;

public class RepositoriFranges extends Controlador<Float,domini.FranjaHoraria> {

        public RepositoriFranges()
        {
            super("domini.FranjaHoraria","RepositoriFranges.db");
        }
      
     /**
      *  Obté la franja a que es troba en la posició especificada, si és possible. 
      *  @param index La posició de la franja a obtenir. 
      *  @pre    index >= 0 i menor que el nombre total de franges.
      *  @post   -
      *  @return El Programa que es troba a l'index, null si no hi és.
      */
        public Object getFranja(int index)
        {
            return listaObject().get(index);
        }

        
     public boolean exportaFranges(String nomFitxer) throws GestorDiscException 
     {
            actualizar(nomFitxer);
            return true;
     }
     
     public boolean importaFranges(String nomFitxer) 
    {
        return carregarDades(nomFitxer);
    }
        
}