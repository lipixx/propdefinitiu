package domini;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author Felip Moll Marquès
 * 
 */
public class ServeiPendent implements Serializable
{
    
    private boolean facturat;
    private double preu;
    private String identificador;
    private Calendar dataRealitzacio;
    
    public ServeiPendent(boolean nFacturat, double nPreu, String id, Calendar nDataR)
    {
        if (nPreu >=0) preu = nPreu;
        else preu = 0;
        
        identificador = new String(id);
        facturat = nFacturat;
        dataRealitzacio = (Calendar) nDataR.clone();
    }
    
    public Calendar getDataReal()
    {
        return dataRealitzacio;
    }
    
    public void setDataReal(Calendar dataReal)
    {
        dataRealitzacio = (Calendar) dataReal.clone();
    }
    
    public double getPreu()
    {
        return preu;
    }
    
    public void setPreu(double nPreu)
    {
        if (nPreu >= 0) preu = nPreu;
    }
    
    
    
    /**
     *  Consultora de l'atribut facturat.
     *  @return Un boolea que es cert si l'emissio ja ha estat facturada,
     *  en cas contrari retorna fals.
     */
    public boolean getFacturat()
    {
        return facturat;
    }
    
    
    /**
     *  Modificadora de l'atribut facturat.
     *  @pre    -
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setFacturat(boolean valor)
    {
        facturat = valor;
    }
    
    public String getIdentificador()
    {
        return identificador;
    }
    
    public void setIdentificador(String id)
    {
        identificador = new String(id);
    }
    
    
}
