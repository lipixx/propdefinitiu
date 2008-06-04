/**
 * La classe AdultsN es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Adults i format Normal.
 * 
 * @author  Felip Moll 41743858P
 * @version 1.0, 7 Juny 2008
 * 
 *
 */
package domini.programa;

import java.util.Calendar;

public class AdultsN extends Normal {

    public AdultsN(String nom, Calendar dataCaducitat, String descripcio, float preuBase, int duracio) {
        super(nom, dataCaducitat, descripcio, preuBase, duracio);
    }
}
