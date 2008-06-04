/**
 * La classe MusicaC es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Musica i format Continu.
 * 
 * @author  Felip Moll 41743858P
 * @version 1.0, 7 Juny 2008 
 * 
 *
 */
package domini.programa;
import java.util.Calendar;

public class MusicaC extends Continu {

    public MusicaC(String nom, Calendar dataCaducitat, String descripcio, float preuBase) {
        super(nom, dataCaducitat, descripcio, preuBase);
    }
}
