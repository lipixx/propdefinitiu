/**
 * La classe SerieN es una sub-subclasse de Programa, i representa aquella categoria
 * de programa que son de categoria Serie i format Normal.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini.programa;

import java.util.Calendar;

public class SerieN extends Normal {

    private int temporada;
    private int capitol;
    private String resumCap;

    public SerieN(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase, int Nduracio, int Ntemporada, int Ncapitol, String NresumCap) {
        super(Nnom, NdataCaducitat, Ndescripcio, NpreuBase, Nduracio);
        temporada = Ntemporada;
        capitol = Ncapitol;
        resumCap = NresumCap;
    }

    public SerieN(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase, int Nduracio) {
        super(Nnom, NdataCaducitat, Ndescripcio, NpreuBase, Nduracio);
        temporada = 1;
        capitol = 0;
        resumCap = "";
    }
    
    /**
     *  Consultora de l'atribut Temporada.
     *  @return L'enter corresponent a la temporada de la serie.
     */
    public int getTemporada() {
        return temporada;
    }

    /**
     *  Consultora de l'atribut capitol.
     *  @return El nombre del capitol.
     */
    public int getCapitol() {
        return capitol;
    }

    /**
     *  Consultora de l'atribut resum del capitol.
     *  @return Un resum del capitol.
     */
    public String getResumCap() {
        return resumCap;
    }

    /**
     *  Modificadora de l'atribut temporada.
     * @param nTemp La temporada nova.
     *  @pre  -
     *  @post S'ha modificat l'atribut.
     */
    public void setTemporada(int nTemp) {
        temporada = nTemp;
    }

    /**
     *  Modificadora de l'atribut Any.
     * @param nCapitol El nou nombre de capitol
     *  @pre  -
     *  @post S'ha modificat l'atribut.
     */
    public void setCapitol(int nCapitol) {
        capitol = nCapitol;
    }

    /**
     *  Modificadora de l'atribut Any.
     * @param nResum El nou resum
     *  @pre  -
     *  @post S'ha modificat l'atribut.
     */
    public void setResumCap(String nResum) {
        resumCap = nResum;
    }
}
