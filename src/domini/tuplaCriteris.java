/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.Calendar;

/**
 *
 * @author Josep Marti
 */
public class tuplaCriteris {

    public int primer;
    public int segon;
    public int tercer;
    public int quart;
    public int cinque;
    public int separades;
    public int nombrePlanis;
    public float preuMaxim;
    public Calendar dataIni;
    public Calendar dataFi;
    public Calendar pre1Ini;
    public Calendar pre2Ini;
    public Calendar pre3Ini;
    public Calendar pre4Ini;
    public Calendar pre1Fi;
    public Calendar pre2Fi;
    public Calendar pre3Fi;
    public Calendar pre4Fi;
    public Calendar proh1Ini;
    public Calendar proh2Ini;
    public Calendar proh3Ini;
    public Calendar proh4Ini;
    public Calendar proh1Fi;
    public Calendar proh2Fi;
    public Calendar proh3Fi;
    public Calendar proh4Fi;
    public boolean adults;
    public boolean concurs;
    public boolean documental;
    public boolean esport;
    public boolean infantil;
    public boolean musica;
    public boolean noticies;
    public boolean pelicula;
    public boolean series;
    public boolean tertulies;
    public boolean autoGen;

    public tuplaCriteris() {
        primer = 1;
        segon = 2;
        tercer = 3;
        quart = 4;
        cinque = 5;
        separades = 1;
        preuMaxim = 0;

        autoGen = false;

        dataIni = null;
        dataFi = null;
        pre1Ini = null;
        pre2Ini = null;
        pre3Ini = null;
        pre4Ini = null;
        pre1Fi = null;
        pre2Fi = null;
        pre3Fi = null;
        pre4Fi = null;
        proh1Ini = null;
        proh2Ini = null;
        proh3Ini = null;
        proh4Ini = null;
        proh1Fi = null;
        proh2Fi = null;
        proh3Fi = null;
        proh4Fi = null;

        adults = false;
        concurs = false;
        documental = false;
        esport = false;
        infantil = false;
        musica = false;
        noticies = false;
        pelicula = false;
        series = false;
        tertulies = false;
        autoGen = false;
    }
}
