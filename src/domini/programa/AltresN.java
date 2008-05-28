/**
 * La classe AltresN es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Altres i format Normal.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini.programa;

import java.io.Serializable;
import java.util.Calendar;

public class AltresN extends Normal implements Serializable {

    public AltresN(String nom, Calendar dataCaducitat, String descripcio, float preuBase, int duracio) {
        super(nom, dataCaducitat, descripcio, preuBase, duracio);
    }
}
