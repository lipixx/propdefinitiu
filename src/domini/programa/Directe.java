/**
 * La classe Normal es una subclasse de Programa, i representa aquells tipus
 * de programa que son de format Normal.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini.programa;

import domini.*;
import java.util.Calendar;

public class Directe extends Programa {

    private Calendar iniciEmissio;
    //En minuts!!
    private int duracio;

    public Directe(String nom, Calendar dataCaducitat, String descripcio, float preuBase, Calendar NiniciEmissio, int Nduracio) {
        super(nom, dataCaducitat, descripcio, preuBase);
        
        if (NiniciEmissio.after(dataCaducitat))
        {
            iniciEmissio = (Calendar) dataCaducitat.clone();
        }
        else
        
        //Crida que no entenc.. pero s'ha de fer
        NiniciEmissio.get(Calendar.HOUR_OF_DAY);
        NiniciEmissio.get(Calendar.MINUTE);                     
        iniciEmissio = (Calendar) NiniciEmissio.clone();
                        
        if (Nduracio < 0) 
        {
            duracio = 0;
        } else {
            duracio = Nduracio;
        }
        
    }

    /**
     *  Consultora de l'atribut duracio.
     *  @return La duracio del programa en minuts.
     */
    public int getDuracio() {
        return this.duracio;
    }

    /**
     *  Consultora de l'atribut iniciEmissio.
     *  @return La data d'inici d'emissio del programa en Directe.
     */
    public Calendar getIniciEmissio() {
        return this.iniciEmissio;
    }

    /**
     *  Modificadora de l'atribut duracio.
     *  @pre  nDuracio es mes gran igual que 0.
     *  @post S'ha modificat la duracio del programa.
     */
    public void setDuracio(int nDuracio) {
        this.duracio = nDuracio;
    }

    /**
     *  Modificadora de l'atribut iniciEmissio.
     *  @pre  IniciEmissio es mes petit o igual que DataCaducitat
     *  @post S'ha modificat la data d'inici d'emissio del programa.
     */
    public void setIniciEmissio(Calendar nIniciEmissio) {
        if (!nIniciEmissio.after(this.getDataCaducitat())) {
            this.iniciEmissio = nIniciEmissio;
        }
    }
}
