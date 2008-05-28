/**
 * La classe InfantilN es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Infantil i format Normal.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini.programa;

import java.util.Calendar;

public class InfantilN extends Normal {

    public InfantilN(String nom, Calendar dataCaducitat, String descripcio, float preuBase, int duracio) {
        super(nom, dataCaducitat, descripcio, preuBase, duracio);
    }
}
