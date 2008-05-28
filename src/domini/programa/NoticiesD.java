/**
 * La classe NoticiesC es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Noticies i format Directe.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */

package domini.programa;

import java.util.Calendar;

public class NoticiesD extends Directe {

    public NoticiesD(String nom, Calendar dataCaducitat, String descripcio, float preuBase, Calendar iniciEmissio, int duracio) {
        super(nom, dataCaducitat, descripcio, preuBase, iniciEmissio, duracio);
    }
}
