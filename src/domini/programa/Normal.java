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

public class Normal extends Programa {

    /** En minuts i > 0*/
    private int duracio;

    public Normal(String nom, Calendar dataCaducitat, String descripcio, float preuBase, int Nduracio) {
        super(nom, dataCaducitat, descripcio, preuBase);
        
        if (Nduracio < 0) duracio = 0;
        else duracio = Nduracio;
    }

    /**
     *  Consultora de l'atribut duracio.
     *  @return La duracio del programa en minuts.
     */
    public int getDuracio() {
        return this.duracio;
    }

    /**
     *  Modificadora de l'atribut duracio.
     *  @pre  nDuracio es mes gran igual que 0.
     *  @post S'ha modificat la duracio del programa.
     */
    public void setDuracio(int nDuracio) 
    {
        if (nDuracio < 0) nDuracio = 0;
        this.duracio = nDuracio;
    }
}
