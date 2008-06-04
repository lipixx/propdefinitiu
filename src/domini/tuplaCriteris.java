/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.Calendar;

/**
 *
 * @author Josep Marti
 */
public class tuplaCriteris {

    /** Categoria ve donat per un INT de 0 a 10:
     * "Altres", "Adults", "Concurs", "Documental", "Esport","Infantil", 
     * "Musica", "Noticies", "Pelicula", "Serie", "Tertulia"*/
    
    /**Setejat a cert per cada Categoria seleccionada, en funcio de la posicio i de 0..10*/
    public boolean filtres[];
    
    /**Nombre maxim de planificacions a generar*/
    public int nombrePlanis;
    
    /**Preu maxim per planificacio*/
    public float preuMaxim;
    
    /**Periode de planificacio*/
    public Calendar dataIni;
    public Calendar dataFi;
    
    /**Franges Preferides (nomes ens interessa la hora)*/
    public Calendar pre1Ini;
    public Calendar pre1Fi;
    
    public Calendar pre2Ini;
    public Calendar pre2Fi;
        
    public Calendar pre3Ini;
    public Calendar pre3Fi;
    
    public Calendar pre4Ini;
    public Calendar pre4Fi;

    /**Franges prohibides (nomes ens interessa la hora)*/
    public Calendar proh1Ini;
    public Calendar proh1Fi;  
    
    public Calendar proh2Ini;
    public Calendar proh2Fi;  
    
    public Calendar proh3Ini;
    public Calendar proh3Fi;
    
    public Calendar proh4Ini;
    public Calendar proh4Fi;

    /**Volem autogeneracio?*/
    public boolean autoGen;
    
    /**Prioritats dels criteris. Per exemple prioritat[0] es la maxima prioritat
     i el criteri associat es el que es troba dins, que es:
     Preu: 1
     Fr preferides: 2
     Fr prohibides: 3
     Respectar els seleccionats : 4
     Respectar el periode de planificacio: 5
     
     * Per tant, prioritat[2] = 4  implica que la 3era prioritat 
     * es la de la Respectar els seleccionats.
     */
    public int prioritats[] = { 1, 2, 3, 4, 5 };
    

    public tuplaCriteris() 
    {    
        /**Inicialitzacions*/
        filtres = new boolean[11];    
        for (boolean b : filtres)
        {
            b = false;
        }
        
        preuMaxim = 0;
        autoGen = false;
        
        dataIni = null;
        dataFi = null;
        
        pre1Ini = null;
        pre2Ini = null;
        pre3Ini = null;
        pre4Ini = null;
        pre1Fi = null;
        pre2Fi = null;
        pre3Fi = null;
        pre4Fi = null;
        
        proh1Ini = null;
        proh2Ini = null;
        proh3Ini = null;
        proh4Ini = null;
        proh1Fi = null;
        proh2Fi = null;
        proh3Fi = null;
        proh4Fi = null;
    }
}
