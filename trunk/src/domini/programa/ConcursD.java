/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domini.programa;

import java.util.Calendar;

/**
 *
 * @author lipi
 */
public class ConcursD extends Directe {

    public ConcursD(String nom, Calendar dataCaducitat, String descripcio, float preuBase, Calendar iniciEmissio, int duracio) {
        super(nom, dataCaducitat, descripcio, preuBase, iniciEmissio, duracio);
    }
}
