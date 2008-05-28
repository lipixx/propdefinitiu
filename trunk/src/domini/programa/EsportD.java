/**
 * La classe EsportD es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Esport i format Directe.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini.programa;

import java.util.Calendar;

public class EsportD extends Directe {

    private String nomEsport;

    public EsportD(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase, Calendar NiniciEmissio, int Nduracio, String NnomEsport) {
        super(Nnom, NdataCaducitat, Ndescripcio, NpreuBase, NiniciEmissio, Nduracio);
        nomEsport = NnomEsport;
    }
    
    public EsportD(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase, Calendar NiniciEmissio, int Nduracio) {
        super(Nnom, NdataCaducitat, Ndescripcio, NpreuBase, NiniciEmissio, Nduracio);
        nomEsport = "";
    }

    /**
     *  Consultora de l'atribut nomEsport.
     *  @return El nom de l'esport del que tracta el programa.
     */
    public String getNomEsport() {
        return nomEsport;
    }

    /**
     *  Modificadora de l'atribut nomEsport.
     *  @pre    -
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setNomEsport(String nNomEsport) {
        nomEsport = nNomEsport;
    }
}
