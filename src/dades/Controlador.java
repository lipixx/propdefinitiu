package dades;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alvar / Sandra
 */


import domini.ClasseAmbClau;
import java.util.LinkedList;
/* Controlador:
 *              K -> Tipus de la Clau dels objectes de la Classe T.
 *                  (p.ex.: String, Integer, Long, etc.)
 *                  {Han de ser Tipus Objectes; no poden ser tipus basics}
 * 
 *              T -> Classe d'objectes que emmagatzemara el controlador.
 */
public class Controlador <K,T extends ClasseAmbClau<K>>
{
    // La Llista propiament on s'emmagatzemaran els objectes
    protected LinkedList<T> llista;
    private GestorDisc<T> gDisc;
    
    
    public Controlador(String clase, String archivo)
    {
        /* Pre:
         *      clase: indica la clase a partir de la cual se crea el gestor de disco.
         *      archivo: indica el archivo dnd estan los archivos que se quieren cargar
         */
        // Es crea una llista buida.
        try{
            this.gDisc = new GestorDisc<T>(clase);
            this.llista=gDisc.carregaTots(archivo);
        }
        catch(GestorDiscException g)
        {
            llista = new LinkedList<T>();
        }
        /* Post:
         *      se ha inicializado el gestor de disco y se han cargado los datos que hay en "archivo"
         */
    }
    
    /* afegirElement: -Intenta afegir l'objecte a la llista-
     *              objecte -> l'objecte que es preten emmagatzemar a la llista del controlador.
     *              retorna: cert -true- si l'operacio ha tingut exit;
     *                       fals -false- en cas contrari.
     */
    public boolean afegirElement(T objecte)
    {
        /* Pre: 
         *      objecte != null
         */
        // La llista conte ja l'objecte ??
        if(estaObject(objecte.getClau()))
        {
            /* La llista ja contenia l'objecte
             * per tant, no s'afegeix l'objecte a la llista
             * i retornem fals.
             */
            return false;
        }
        /* La llista no contenia l'objecte
         * per tant, afegim l'objecte al final de la llista
         * i retornem cert.
         */
        return llista.add(objecte);
        /* Post:
         *      Retorna cert <=> l'objecte no pertanyia a la llista 
         */
    }
    
    /* obteElement: -Intenta obtenir una copia de l'objecte amb clau "clau"-
     *              clau -> clau de l'objecte a buscar.
     *              objecte -> objecte on es guardara una copia de l'objecte.
     *              retorna: cert -true- si s'ha trobat un objecte amb clau "clau"
     *                                   i s'ha creat una copia de l'objecte a "objecte";
     * 
     *                       false -false- si no s'ha trobat cap objecte amb aquesta clau.
     */
    
    
    public boolean obteElement(K clau, T objecte)
    {
        /* Pre:
         *      clau != null
         */
        // Per a cada objecte dins la llista
        if (estaObject(clau))
        {
            for(T i : llista)
            {
                // L'aobjecte de la llista te com a clau "clau" ??
               if(clau.equals(i.getClau()))
               {
                   /* L'objecte i te com a clau "clau";
                    * per tant, fem que "objecte" tingui una copia de l'objecte i
                * i retornem cert.
                */
                   objecte.setCopia(i);
                   //objecte = i;
                   return true;
                }
            }
        }
        /* No s'ha trobat cap objecte dins de la llista amb clau "clau";
         * per tant, s'ha de retornar fals.
         */
        return false;
        /* Post:
         *      Retorna cert <=> Existeix un objecte a la llista amb clau "clau"
         *              Si retorna cert => "objecte" conte una copia de l'objecta amb clau "clau"
         */
    }
    
    /* modificaElement: -Modifica els atributs de l'objecte a la llista amb la mateixa clau que "objecte"
                         pels valors dels atributs de "objecte"-
     *                  objecte -> L'objecte "nou"
     *                  retorna: cert -true- si s'ha trobat un objecte a la llista amb la mateixa clau que "objecte"
     *                                       i s'ha realitzat la copia correctament;
     *                           fals -flase- en cas contrari.
     */
    public boolean modificaElement(T objecte)
    {
        /* Pre:
         *      objecte != null
         */
        // Guardem una copia de la clau de "objecte" per evitar cridar reiteradament objecte.getClau().
        K ClauObjecte = objecte.getClau();
        // Per cada objecte dins la llista
        for(T i : llista)
        {
            // L'objecte i te la mateixa clau que "objecte" ??
            if(ClauObjecte.equals(i.getClau()))
            {
                /* L'objecte i te com a clau la mateixa que "objecte"
                 * per tant, realitzem la copia i retornem cert.
                 */
                i.setCopia(objecte);
                return true;
            }
        }
        /* No s'ha trobat cap objecte amb la mateixa clau que "objecte";
         * en consequencia, no es pot realitzar la copia i retornem fals.
         */
        return false;
        /* Post:
         *      retorna cert <=> L'objecte existeix a la llista.
         */
    }
    
    /* esborraElement: -Intenta esborrar l'objecte de la llista que tingui la mateixa clau-
     *                  objecte -> l'objecte que es preten esborrar.
     *                  retorna: cert -true- si s'ha trobat un objecte amb la mateixa clau i 
     *                           s'ha pogut esborrar de la llista;
     *                           fals -false-, en cas contrari.
     */
    public boolean esborraElement(T objecte)
    {
        /* Pre:
         *      objecte != null
         */
        // Guardem una copia de la clau de "objecte" per evitar cridar reiteradament objecte.getClau().
        K ClauObjecte = objecte.getClau();
        // Per a cada objecte dins de la llista
        for(T i : llista)
        {
            // L'objecte i te la mateixa clau que "objecte" ??
            if(i.getClau().equals(ClauObjecte))
            {
                // L'objecte i té la mateixa clau que "objecte";
                // per tant, podem esborrar-lo de la llista.
                return llista.remove(i);
            }
        }
        /* L'objecte no es troba a la llista;
         * per tant, com que no podem esborrar-lo
         * retornem fals.
         */
        return false;
        /* Post:
         *      Retorna cert <=> L'objecte es troba a la llista i s'ha pogut esborrar.
         */
    }
    
   public boolean esborraElement(K clau /*T obj*/)
    {//{PRE: el elemento obj se ha tenido que inicializar antes de realizar la llamada}
       //T obj = new T();
       for (int i=0; i<llista.size();i++)
       {
           if (llista.get(i).getClau().equals(clau))
               llista.remove(i);
               return true;
       }
       //boolean b = obteElement(clau, obj);
       //return llista.remove(obj);
       return false;
    }
   
   public int mida()
   {
        return llista.size();
   }
   
    /* listaObject: obte la llista dels objectes T enmagatzemats en el controlador.
     * {pre: -}*/
    public LinkedList<T> listaObject()
    {
        return llista;
    }
    /* {post: retorna una LinkedList<T> on conté els objectes T que s'han enmagatzemat 
     * en el controlador*/
    
    
    /* actualizar: actualitza la llista d'objectes T guardada en el controlador en disc.
     *            archivo: es el arxiu d'objetes guardats en Disc, i que ens permet
     *                   actualitzar el archiu.
     * {pre:-}
     */ 
    public void actualizar(String archivo) throws GestorDiscException
    {   
        try{
        gDisc.esborra(archivo);
        gDisc.guardaTots(llista, archivo);
        }
        catch(GestorDiscException g)
        {
            gDisc.guardaTots(llista, archivo);
        }
    }
     /*{Post: ha guardat la llista d'objectes T en disc}*/
    
  
    //borrarLlista: esborra la llista de objectes que hi ha en el controlador
    public void esborrarLlista()
    {
        llista.clear();
    }
    
    
    // carregar dades: carga los datos que hay en el archivo "archivo" a la lista "llista"
    public boolean carregarDades(String archivo)
    {
        try{
            this.llista=gDisc.carregaTots(archivo);
            return true;
        }
        catch(GestorDiscException g)
        {
            llista = new LinkedList<T>();
            return false;
        }
    }
    
    
    public boolean estaObject(K id)
    {
        for (T c:llista)
        {
            if(id.equals(c.getClau()))
           {
               return true;
           }
        }
        return false;
    }

    
    /**Afegida*/
    public T obteElement(K clau)
    {
        /* Pre:
         *      clau != null
         */
        // Per a cada objecte dins la llista
        if (estaObject(clau))
        {
            for(T i : llista)
            {
                // L'aobjecte de la llista te com a clau "clau" ??
               if(clau.equals(i.getClau()))
               {
                   /* L'objecte i te com a clau "clau";
                    * per tant, fem que "objecte" tingui una copia de l'objecte i
                * i retornem cert.
                */
                   return i;
                }
            }
        }
        /* No s'ha trobat cap objecte dins de la llista amb clau "clau";
         * per tant, s'ha de retornar fals.
         */
        return null;
        /* Post:
         *      Retorna cert <=> Existeix un objecte a la llista amb clau "clau"
         *              Si retorna cert => "objecte" conte una copia de l'objecta amb clau "clau"
         */
    }
}