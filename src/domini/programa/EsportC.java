/**
 * La classe EsportC es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Esport i format Continu.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */

package domini.programa;
import java.util.Calendar;

public class EsportC extends Continu{
    
    private String nomEsport;
    
    public EsportC(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase,String NnomEsport)
    {
        super(Nnom,NdataCaducitat,Ndescripcio,NpreuBase);
        nomEsport = NnomEsport;
    }
    
    public EsportC(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase)
    {
        super(Nnom,NdataCaducitat,Ndescripcio,NpreuBase);
        nomEsport = "";
    }
    
    /**
     *  Consultora de l'atribut nomEsport.
     *  @return El nom de l'esport del que tracta el programa.
     */
    public String getNomEsport()
    {
        return  nomEsport;
    }
    
    /**
     *  Modificadora de l'atribut nomEsport.
     *  @pre    -
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setNomEsport(String nNomEsport)
    {
        nomEsport = nNomEsport;
    }
}
