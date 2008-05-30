/**
 * La classe ControladorDominiPlanificacio representa el Controlador de la capa
 * de domini, exclusivament per a la generacio de la planificacio.
 * 
 * @author  Josep Marti
 * @version 0.1, 30 Maig 2008 
 * 
 * Versió preliminar.
 */
package domini;

import dades.RepositoriFranges;
import dades.RepositoriProgrames;
import domini.programa.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Vector;

public class ControladorPlanificacio {

    private RepositoriFranges RepoFranges;
    private Cliente cActual;
    private ControladorProgrames CProgrames;
    private RepositoriProgrames RepoProgs;
    /**/
    private LinkedList<Programa> llistaProgrames;
    private LinkedList<Planificacio> llistaPlanificacions;
    private LinkedList<FranjaHoraria> llistaFrangesPreferides;
    private LinkedList<FranjaHoraria> llistaFrangesProhibides;
    private LinkedList<FranjaHoraria> llistaFranges;
    private LinkedList<Programa> llistaProgs;
    private int[] prioritats = new int[5];
    private Generador generador;
    private boolean criteris = false;
    private float preuMax;

    /**/
    public ControladorPlanificacio(RepositoriFranges nRepoFranges, ControladorProgrames CProgs, RepositoriProgrames<String, Programa> repoProgs) {
        CProgrames = CProgs;
        RepoFranges = nRepoFranges;
        RepoProgs = repoProgs;
        llistaFrangesPreferides = new LinkedList<FranjaHoraria>();
        llistaFrangesProhibides = new LinkedList<FranjaHoraria>();
        llistaPlanificacions = new LinkedList<Planificacio>();
        llistaProgrames = new LinkedList<Programa>();
    }

    public String[] gene(Vector<String> programesSeleccionats, tuplaCriteris nousCriteris) {
        String[] planificacions = new String[llistaPlanificacions.size()];

        if (nousCriteris.autoGen) {

            Vector<String> programa = new Vector<String>();
            boolean[] filtres = {nousCriteris.adults, nousCriteris.concurs, nousCriteris.documental, nousCriteris.esport, nousCriteris.infantil, nousCriteris.musica, nousCriteris.noticies, nousCriteris.pelicula, nousCriteris.series, nousCriteris.tertulies};
            boolean hies = false;

            for (int k = 1; k <= 10; k++) {

                filtres[k] = nousCriteris.adults;
                if (filtres[k]) {

                    //  programa = CProgrames.getllistaFiltrada(k, null);

                    for (int j = 0; j < programa.size(); j++) {
                        hies = false;
                        for (int i = 0; i < llistaProgrames.size() && !hies; i++) {
                            if (llistaProgrames.get(i).getNom().equals("hool")) {
                                hies = true;
                            }
                        }
                        if (!hies) {
                        // llistaProgrames.add(programa.get(j));

                        }
                    }
                }
            }

            String[] aux = new String[programesSeleccionats.size()];
        //llistaProgrames = CPG.getllistaFiltrada(tipusFiltre, null);

        } else {
            String[] aux = new String[programesSeleccionats.size()];
            llistaProgrames = CProgrames.llistarProgramesNom(programesSeleccionats.toArray(aux));


        }

        FranjaHoraria franja;

        if (nousCriteris.pre1Ini != null) {
            franja = new FranjaHoraria(nousCriteris.pre1Ini, nousCriteris.pre1Fi, (float) 0.10);
            llistaFrangesPreferides.add(franja);
        }
        if (nousCriteris.pre2Ini != null) {
            franja = new FranjaHoraria(nousCriteris.pre2Ini, nousCriteris.pre2Fi, (float) 0.10);
            llistaFrangesPreferides.add(franja);
        }
        if (nousCriteris.pre3Ini != null) {
            franja = new FranjaHoraria(nousCriteris.pre3Ini, nousCriteris.pre3Fi, (float) 0.10);
            llistaFrangesPreferides.add(franja);
        }
        if (nousCriteris.pre4Ini != null) {
            franja = new FranjaHoraria(nousCriteris.pre4Ini, nousCriteris.pre4Fi, (float) 0.10);
            llistaFrangesPreferides.add(franja);
        }
        if (nousCriteris.proh1Ini != null) {
            franja = new FranjaHoraria(nousCriteris.proh1Ini, nousCriteris.proh1Fi, (float) 0.10);
            llistaFrangesProhibides.add(franja);
        }
        if (nousCriteris.proh2Ini != null) {
            franja = new FranjaHoraria(nousCriteris.proh2Ini, nousCriteris.proh2Fi, (float) 0.10);
            llistaFrangesProhibides.add(franja);
        }
        if (nousCriteris.proh3Ini != null) {
            franja = new FranjaHoraria(nousCriteris.proh3Ini, nousCriteris.proh3Fi, (float) 0.10);
            llistaFrangesProhibides.add(franja);
        }
        if (nousCriteris.proh4Ini != null) {
            franja = new FranjaHoraria(nousCriteris.proh4Ini, nousCriteris.proh4Fi, (float) 0.10);
            llistaFrangesProhibides.add(franja);
        }


        prioritats[nousCriteris.primer] = 1;
        prioritats[nousCriteris.segon] = 2;
        prioritats[nousCriteris.tercer] = 3;
        prioritats[nousCriteris.quart] = 4;
        prioritats[nousCriteris.cinque] = 5;

        //     llistaPlanificacions = generador.generar(llistaProgrames, nousCriteris.preuMaxim, prioritats, llistaFrangesPreferides, llistaFrangesProhibides, nousCriteris.dataIni, nousCriteris.dataFi, nousCriteris.nombrePlanis, (LinkedList<FranjaHoraria>) RepoFranges.listaObject(), nousCriteris.separades);

        return planificacions;
    }

    /**S'ha canviat el client actual del domini, per aixo
     * se li avisa n'aquest controlador.
     * 
     * @param nouClientActual El nou client del que farem les gestions a partir d'ara
     * @pre nouClientActual no es buit
     * @post S'ha canviat la variable global de clientActual
     */
    public void setClient(Cliente nom) {
        //S'han de "resetejar" ses llistes internes d'aquest controlador
        cActual = nom;
        llistaPlanificacions = (LinkedList<Planificacio>) cActual.getLlistaPlan().clone();
    }

    public void anularEmissio(String nomPrograma, Calendar dIni, Calendar dFi, boolean temporal) {
        //Transformar idPlanificacio a Calendari....
        // idPLanificacio son 2 calendars dd/mm/yyyy - dd/mm/yyyy 
        // true implica que es TEMPORAL
        boolean trobat = false;
        if (temporal == true) {
            for (int i = 0; i < llistaPlanificacions.size() && !trobat; i++) {
                if (llistaPlanificacions.get(i).getDataInici().equals(dIni) && llistaPlanificacions.get(i).getDataFi().equals(dFi)) {
                    for (int j = 0; j < llistaPlanificacions.get(i).getLlistaEmissions().size() && !trobat; j++) {
                        if (((Emissio) llistaPlanificacions.get(i).getLlistaEmissions().get(j)).getPrograma().equals(nomPrograma)) {
                            llistaPlanificacions.get(i).getLlistaEmissions().remove(llistaPlanificacions.get(i).getLlistaEmissions().get(j));
                            //  llistaPlanificacions.get(i).getLlistaEmissions().remove(j);
                            trobat = true;

                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < cActual.getLlistaPlan().size() && !trobat; i++) {
                if (cActual.getLlistaPlan().get(i).getDataInici().equals(dIni) && cActual.getLlistaPlan().get(i).getDataFi().equals(dFi)) {
                    for (int j = 0; j < cActual.getLlistaPlan().get(i).getLlistaEmissions().size() && !trobat; j++) {
                        if (((Emissio)cActual.getLlistaPlan().get(i).getLlistaEmissions().get(j)).getPrograma().getNom().equals(nomPrograma)) {
                            cActual.getLlistaPlan().get(i).getLlistaEmissions().remove(cActual.getLlistaPlan().get(i).getLlistaEmissions().get(j));
                            trobat = true;
                        }
                    }
                }
            }
        }

    }

    public void contractar(Calendar dIni, Calendar dFi) {
        // identificam la planificacio per la seva data ini i data fin 
        boolean trobat = false;
        for (int i = 0; i < llistaPlanificacions.size() && !trobat; i++) {
            if (llistaPlanificacions.get(i).getDataInici().equals(dIni) && llistaPlanificacions.get(i).getDataFi().equals(dFi)) {
                cActual.listPlan.add(llistaPlanificacions.get(i));
                trobat = true;
            }
        }

    }

    public String[] getLlistaPlanificacions() {
        String[] llista;
        ArrayList llistaPClient = cActual.getLlistaPlan();
        if (llistaPClient == null) {
            return llista = new String[0];
        }
        llista = new String[llistaPClient.size()];
        String diaIni = "";
        String mesIni = "";
        String diaFi = "";
        String mesFi = "";
        for (int i = 0; i < llistaPClient.size(); i++) {

            if ((((Planificacio) llistaPClient.get(i)).getDataInici()).get(Calendar.MONTH) < 10) {
                mesIni = "0" + (((Planificacio) llistaPClient.get(i)).getDataInici()).get(Calendar.MONTH);
            } else {
                mesIni = "" + (((Planificacio) llistaPClient.get(i)).getDataInici()).get(Calendar.MONTH);
            }
            if ((((Planificacio) llistaPClient.get(i)).getDataFi()).get(Calendar.MONTH) < 10) {
                mesFi = "0" + (((Planificacio) llistaPClient.get(i)).getDataFi()).get(Calendar.MONTH);
            } else {
                mesFi = "" + (((Planificacio) llistaPClient.get(i)).getDataFi()).get(Calendar.MONTH);
            }
            if ((((Planificacio) llistaPClient.get(i)).getDataInici()).get(Calendar.DAY_OF_MONTH) < 10) {
                diaIni = "0" + (((Planificacio) llistaPClient.get(i)).getDataInici()).get(Calendar.DAY_OF_MONTH);
            } else {
                diaIni = "" + (((Planificacio) llistaPClient.get(i)).getDataInici()).get(Calendar.DAY_OF_MONTH);
            }
            if ((((Planificacio) llistaPClient.get(i)).getDataFi()).get(Calendar.DAY_OF_MONTH) < 10) {
                diaFi = "0" + (((Planificacio) llistaPClient.get(i)).getDataFi()).get(Calendar.DAY_OF_MONTH);
            } else {
                diaFi = "" + (((Planificacio) llistaPClient.get(i)).getDataFi()).get(Calendar.DAY_OF_MONTH);
            }

            llista[i] = "" + diaIni + "/" + mesIni + "/" + (((Planificacio) llistaPClient.get(i)).getDataInici()).get(Calendar.YEAR) + " - " + diaFi + "/" + mesFi + "/" + (((Planificacio) llistaPClient.get(i)).getDataFi()).get(Calendar.YEAR);
        }
        return llista;
    }
}
