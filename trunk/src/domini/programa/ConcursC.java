/**
 * La classe ConcursC es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Concurs i format Continu.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini.programa;
import java.util.Calendar;

public class ConcursC extends Continu {

    public ConcursC(String nom, Calendar dataCaducitat, String descripcio, float preuBase) {
        super(nom, dataCaducitat, descripcio, preuBase);
    }
}
