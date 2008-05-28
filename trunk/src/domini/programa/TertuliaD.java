/**
 * La classe TertuliaD es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Tertulia i format Directe.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */

package domini.programa;

import java.util.Calendar;

public class TertuliaD extends Directe {

    private String nomTertulia;

    public TertuliaD(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase, Calendar NiniciEmissio, int Nduracio, String NnomTertulia) {
        super(Nnom, NdataCaducitat, Ndescripcio, NpreuBase, NiniciEmissio, Nduracio);
        nomTertulia = new String(NnomTertulia);
    }
    
     public TertuliaD(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase, Calendar NiniciEmissio, int Nduracio) {
        super(Nnom, NdataCaducitat, Ndescripcio, NpreuBase, NiniciEmissio, Nduracio);
        nomTertulia = "";
    }

    /**
     *  Consultora de l'atribut nomTertulia.
     *  @return El nom de l'Tertulia del que tracta el programa.
     */
    public String getNomTertulia() {
        return nomTertulia;
    }

    /**
     *  Modificadora de l'atribut nomTertulia.
     *  @pre    -
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setNomTertulia(String nNomTertulia) {
        nomTertulia = new String(nNomTertulia);
    }
}
