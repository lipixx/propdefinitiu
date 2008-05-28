/**
 * La classe MusicaN es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Musica i format Normal.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini.programa;

import java.util.Calendar;

public class MusicaN extends Normal {

    public MusicaN(String nom, Calendar dataCaducitat, String descripcio, float preuBase, int duracio) {
        super(nom, dataCaducitat, descripcio, preuBase, duracio);
    }
}
