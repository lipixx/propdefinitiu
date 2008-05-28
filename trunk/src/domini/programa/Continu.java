/**
 * La classe Continu es una subclasse de Programa, i representa aquells tipus
 * de programa que son de format Continu.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini.programa;

import domini.*;

import java.util.Calendar;

public class Continu extends Programa {

    public Continu(String nom, Calendar dataCaducitat, String descripcio, float preuBase) {
        super(nom, dataCaducitat, descripcio, preuBase);
    }
}
