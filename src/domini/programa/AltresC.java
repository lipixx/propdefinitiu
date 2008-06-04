/**
 * La classe AltresC es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Altres i format Continu.
 * 
 * @author  Felip Moll 41743858P
 * @version 1.0, 7 Juny 2008
 * 
 *
 */
package domini.programa;

import java.util.Calendar;

public class AltresC extends Continu {

    public AltresC(String nom, Calendar dataCaducitat, String descripcio, float preuBase) {
        super(nom, dataCaducitat, descripcio, preuBase);
    }
}
