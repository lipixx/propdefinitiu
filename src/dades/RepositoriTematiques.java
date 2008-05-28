/**
 * La classe RepositoriTematiques permet utilitzar els metodes del Controlador
 * generic de dades especialment per les tematiques.
 * 
 * 
 * @author  Felip Moll
 * @version 0.1, 30 Maig 2008 
 * 
 * Versió #2a Entrega#
 */

package dades;

public class RepositoriTematiques extends Controlador<String,domini.Tematica>
{
    public RepositoriTematiques()
    {
        super("domini.Tematica","RepositoriTemes.db");
    }

    public boolean exportaTemes(String fitxer) throws GestorDiscException 
    {
        actualizar(fitxer);
        return true;
    }

    public boolean importaTemes(String fitxer) {
        carregarDades(fitxer);
        return true;
    }
    
    
    
/**    
        /**
     *  Retorna la llista amb els noms de les tematiques disponibles
     *  que hi ha al repositori. 
     *  @pre    -
     *  @post   -
     *  @return Una llista amb els noms de les tematiques disponibles
     *          al repositori.
     *
    public String[] getLTematiques() {
        listaObject();
        int nTemes = mida();
        String[] arrayTematiques = new String[nTemes];

        for (int i = 0; i < nTemes; i++) 
        {
            arrayTematiques[i] = listaObject().get(i).getClau();
        }
        return arrayTematiques;
    }
    */
    
  /*
    public void addTematica(Tematica t)
    {
       llistaTemes.add(t);
    }
   
    public int temaExisteix(String nomTema)
    {
       int listSize = llistaTemes.size();
       int index = -1;
       
       for (int i=0; i<listSize; i++)
       {
            if (llistaTemes.get(index).getTema().compareToIgnoreCase(nomTema) == 0)
                return index;
       }
       return index;
    }
    
   public Tematica getTematica(int index)
    {
        return llistaTemes.get(index);
    }
   
    public void delTematica(int index)
    {
        llistaTemes.remove(index);
    }
    
    public int getNTemes()
    {
        return llistaTemes.size();
    }

     public boolean exportaTemes(String nomFitxer) throws GestorDiscException
    {
        GestorDisc<Tematica> gestDisc = new GestorDisc("Tematica");
        gestDisc.guardaTots(llistaTemes, nomFitxer);
        return true;
    }
     
     public boolean importaTemes(String nomFitxer) throws GestorDiscException
    {
        GestorDisc<Tematica> gestDisc = new GestorDisc("Tematica");
        llistaTemes = gestDisc.carregaTots(nomFitxer);
        return true;
    } */
}
