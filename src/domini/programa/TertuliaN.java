/**
 * La classe TertuliaN es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Tertulia i format Normal.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini.programa;

import java.util.Calendar;

public class TertuliaN extends Normal{
    
    private String nomTertulia;
    
    public TertuliaN(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase,int Nduracio, String NnomTertulia)
    {
        super(Nnom,NdataCaducitat,Ndescripcio,NpreuBase,Nduracio);
        nomTertulia = NnomTertulia;
    }
    
    public TertuliaN(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase,int Nduracio)
    {
        super(Nnom,NdataCaducitat,Ndescripcio,NpreuBase,Nduracio);
        nomTertulia = "";
    }
    
    /**
     *  Consultora de l'atribut nomTertulia.
     *  @return El nom de la Tertulia de la que tracta el programa.
     */
    public String getNomTertulia()
    {
        return  nomTertulia;
    }
    
    /**
     *  Modificadora de l'atribut nomTertulia.
     *  @pre    -
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setNomTertulia(String nNomTertulia)
    {
        nomTertulia = nNomTertulia;
    }
}
