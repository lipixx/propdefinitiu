/**
 * La classe NoticiesC es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Noticies i format Continu.
 * 
 * @author  Felip Moll 41743858P
 * @version 1.0, 7 Juny 2008 
 * 
 *
 */
package domini.programa;

import java.util.Calendar;

public class AltresD extends Directe {

    public AltresD(String nom, Calendar dataCaducitat, String descripcio, float preuBase, Calendar iniciEmissio, int duracio) {
        super(nom, dataCaducitat, descripcio, preuBase, iniciEmissio, duracio);
    }
}
