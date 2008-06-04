package domini;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Aquesta Classe dona una classe generica per ser compartida en la que es defineix
 * un servei pendent com un servei que te un preu, es realitza a una data determinada
 * i te un identificador. A mes te un camp per saber si aquest servei ja ha estat cobrat
 * en una factura o no.
 * Qui vulgui fer servir aquest Servei possiblement haura de crear una nova classe que
 * hereti d'aquesta, i a n'aquesta li haura de passar a la constructora "super()" els atributs
 * especificats a continuacio.
 * 
 * @author Felip Moll Marquès
 * @version 1.0, 6 Juny 2008
 * Versio final.
 */
public class ServeiPendent implements Serializable
{
    /**
     *  facturat indica si el servei ja ha estat introduit en una factura.
     *  preu indica el preu que te aquest servei.
     *  identificador es l'identificador del servei.
     *  dataRealitzacio es la data en que es realitza el servei.
     */
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
    
    /**A continuacio les consultores i modificadores dels camps*/
    
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
