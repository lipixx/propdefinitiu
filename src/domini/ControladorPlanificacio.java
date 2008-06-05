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
import domini.programa.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

public class ControladorPlanificacio {

    private RepositoriFranges RepoFranges;
    private Cliente cActual;
    private ControladorProgrames CProgrames;
    private LinkedList<Programa> llistaProgrames;
    private LinkedList<Planificacio> llistaPlanificacions;
    private LinkedList<FranjaHoraria> llistaFrangesPreferides;
    private LinkedList<FranjaHoraria> llistaFrangesProhibides;
    private int[] prioritats = new int[5];
    private Generador generador;
    private Convertir Conv;
    SimpleDateFormat formatCalendar = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatCalendari = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");

    public ControladorPlanificacio(RepositoriFranges nRepoFranges, ControladorProgrames CProgs) {
        CProgrames = CProgs;
        RepoFranges = nRepoFranges;
        generador = new Generador();
        Conv = new Convertir();
        llistaFrangesPreferides = new LinkedList<FranjaHoraria>();
        llistaFrangesProhibides = new LinkedList<FranjaHoraria>();
        llistaPlanificacions = new LinkedList<Planificacio>();
        llistaProgrames = new LinkedList<Programa>();
    }

    public String[][] genSet(String inici, String fin, String plani, boolean temporal) throws ParseException {

        /* Definim els limits de la setmana a generar */
        Calendar iniSetmana = Conv.strToCalendar(inici);
        Calendar fiSetmana = Conv.strToCalendar(fin);
        iniSetmana.set(Calendar.HOUR, 0);
        iniSetmana.set(Calendar.MINUTE, 0);
        iniSetmana.set(Calendar.SECOND, 0);
        fiSetmana.set(Calendar.HOUR, 23);
        fiSetmana.set(Calendar.MINUTE, 59);
        fiSetmana.set(Calendar.SECOND, 59);

        Calendar aux2[] = Conv.idPlanificacio(plani);
        Calendar iniPlaniAux = aux2[0];
        Calendar fiPlaniAux = aux2[1];
        Calendar iniPlani;
        Calendar fiPlani;

        LinkedList<Planificacio> llistaP = llistaPlanificacions;

        if (!temporal) {
            llistaP = cActual.getLlistaPlan();
        }

        Planificacio P = null;
        boolean trobat = false;

        for (int i = 0; i < llistaP.size() && !trobat; i++) {
            /* Obtenim s'identificador de planificacio que estem mirant */

            iniPlani = (Calendar) llistaP.get(i).getDataInici();
            fiPlani = (Calendar) llistaP.get(i).getDataFi();


            if (Conv.sonIgualsData(iniPlaniAux, iniPlani) && Conv.sonIgualsData(fiPlaniAux, fiPlani)) {
                trobat = true;
                P = llistaP.get(i);
            }
        }

        String[][] graella = new String[144][8];
        for (int hmm = 0; hmm < 144; hmm++) {
            for (int l = 0; l < 8; l++) {
                graella[hmm][l] = "-";
            }
        }

        int comptador = 0;
        int dia, horaInici, minutInici, horaFi, minutFi, posIni, posFi;
        boolean diaSeg = false;

        Date iniD = formatHora.parse("00:00");
        Calendar iniDia = Calendar.getInstance();
        iniDia.setTime(iniD);

        Date fiD = formatHora.parse("23:59");
        Calendar fiDia = Calendar.getInstance();
        fiDia.setTime(fiD);

        /* Rellenem sa primera columna amb ses hores: */
        while (iniDia.before(fiDia) && comptador < 144) {
            // graella[comptador][0] = "" + iniDia.get(Calendar.HOUR_OF_DAY) + ":" + iniDia.get(Calendar.MINUTE);
            graella[comptador][0] = Conv.getHora(iniDia);
            iniDia.add(Calendar.MINUTE, +10);
            comptador++;
        }
        if (P != null) {
            for (int j = 0; j < P.getLlistaEmissions().size(); j++) {
                ServeiPendent mirantEmissio = (ServeiPendent) P.getLlistaEmissions().get(j);

                if (!mirantEmissio.getDataReal().before(iniSetmana) && (!mirantEmissio.getDataReal().after(fiSetmana))) {

                    dia = mirantEmissio.getDataReal().get(Calendar.DAY_OF_WEEK);
                    if (dia == 1) {
                        /* Si es diumenge, la pintam a sa columna nombre 8, es a dir, posicio 7 */
                        dia = 7;
                    } else {
                        dia--;
                    }

                    horaInici = ((Emissio) mirantEmissio).getHoraInici().get(Calendar.HOUR_OF_DAY);
                    minutInici = ((Emissio) mirantEmissio).getHoraInici().get(Calendar.MINUTE);
                    posIni = (horaInici * 6) + (minutInici / 10);

                    horaFi = ((Emissio) mirantEmissio).getHoraFi().get(Calendar.HOUR);
                    minutFi = ((Emissio) mirantEmissio).getHoraFi().get(Calendar.MINUTE);
                    posFi = (horaFi * 6) + (minutFi / 10);

                    if (posIni > posFi) {
                        diaSeg = true;
                    } else {
                        diaSeg = false;
                    }

                    String nom = mirantEmissio.getIdentificador();
                    while ((dia < 7 && posIni < posFi) || ((posIni > posFi) && diaSeg) || (diaSeg && (posIni > 143))) {

                        if (diaSeg && (posIni > 143)) {
                            dia++;
                            diaSeg = false;
                            posIni = 0;
                        }
                        graella[posIni][dia] = nom;
                        posIni++;
                    }
                }

            }
        }

        return graella;
    }

    public String[] gene(Vector<String> programesSeleccionats, tuplaCriteris nousCriteris) throws ParseException {
        /* Retorna un vector de String que identifiquen les planificacions generades mitjancant la seva data ini i fi */

        llistaProgrames.clear();
        
        if (nousCriteris.autoGen) {

            Vector<String> programesDefinitius = new Vector<String>();
            boolean[] filtres = nousCriteris.filtres;
            boolean hies = false;

            for (int k = 0; k < 10; k++) {

                if (filtres[k]) {

                    String[] programa = CProgrames.getllistaFiltrada("categoria", "" + (k + 1));

                    for (int j = 0; j < programa.length; j++) {
                        hies = false;
                        for (int i = 0; i < llistaProgrames.size() && !hies; i++) {
                            /* Cercam si ja hi ha el programa dins llistaProgames */
                            if (llistaProgrames.get(i).getNom().compareToIgnoreCase(programa[j]) == 0) {
                                hies = true;
                            }
                        }
                        if (!hies) {
                            programesDefinitius.add(programa[j]);

                        }
                    }
                }
            }

            String[] aux = new String[programesDefinitius.size()];
            llistaProgrames = CProgrames.llistarProgramesNom(programesDefinitius.toArray(aux));

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

        prioritats = nousCriteris.prioritats;

        LinkedList<FranjaHoraria> frangesH = RepoFranges.listaObject();

        llistaPlanificacions = generador.generar(llistaProgrames, nousCriteris.preuMaxim, prioritats, llistaFrangesPreferides, llistaFrangesProhibides, nousCriteris.dataIni, nousCriteris.dataFi, nousCriteris.nombrePlanis, frangesH);


        /* Pasar cada planificacio al seu identificador ( data ini + data fi) */
        String[] planificacions = new String[llistaPlanificacions.size()];

        for (int i = 0; i < llistaPlanificacions.size(); i++) {
            Calendar aux = (Calendar) llistaPlanificacions.get(i).getDataInici().clone();
            Calendar aux3 = (Calendar) llistaPlanificacions.get(i).getDataFi().clone();
            //    planificacions[i] = "" + aux.get(Calendar.DAY_OF_MONTH) + "/" + (aux.get(Calendar.MONTH) + 1) + "/" + aux.get(Calendar.YEAR) + " - " + aux.get(Calendar.DAY_OF_MONTH) + "/" + (aux.get(Calendar.MONTH) + 1) + "/" + aux.get(Calendar.YEAR);
            planificacions[i] = Conv.idPlanificacio(aux, aux3);
        }

        return planificacions;
    }

    public String getDataClient(int i) {

        Calendar aux = cActual.getLlistaPlan().get(i).getDataFi();
        return Conv.dateToStr(aux);
    }

    public int getNumPlanisClient() {
        if (cActual == null) {
            return 0;
        } else {
            return cActual.getLlistaPlan().size();
        }
    }

    /**S'ha canviat el client actual del domini, per aixo
     * se li avisa n'aquest controlador.
     * 
     * @param nom es el nou client del que farem les gestions a partir d'ara
     * @pre nom no es buit
     * @post S'ha canviat la variable global de cActual
     */
    public void setClient(Cliente nom) {
        cActual = nom;
        llistaPlanificacions = cActual.getLlistaPlan();
    }

    public Cliente getClient() {
        return cActual;
    }

    /**
     * Agafa l'identificador d'una planificacio t.q. dIni i dFi, i un nomPrograma. Depenent
     * del boolea temporal cercara la planificacio a la llista temporal de planificacions acabades
     * de generar, o a la llista de planificacions del client. Quan trobi aquesta planificacio,
     * cercara l'emissio que te per indentificador el nomPrograma, i l'esborrara d'aquesta Planificacio.
     * @param nomPrograma Identifica la emissio a esborrar de una planificacio.
     * @param dIni Identificador dataInici de la Planificacio
     * @param dFi Identificador dataFi de la Planificacio
     * @param temporal Indicador de si s'ha de cercar a la llista temporal del generador (true)
     * o a la del client (false).
     * @pre Existeix la planificacio i l'emissio amb nomPrograma com identificador.
     * @post S'ha esborrat de la Planificacio amb clau (dIni,dFi) l'emissio amb clau (nomPrograma)
     * @return Retorna un bolea cert si s'ha anulat el programa.
     */
    public boolean anularEmissio(String nomPrograma, Calendar dIni, Calendar dFi, boolean temporal) {
        // idPLanificacio son 2 calendars dd/mm/yyyy - dd/mm/yyyy 
        // true implica que es TEMPORAL
        Emissio e;
        Planificacio P;
        Calendar pIni, pFi, avui;
        avui = Calendar.getInstance();

        boolean trobat = false;
        LinkedList<ServeiPendent> emissionsTemporal;

        LinkedList<Planificacio> llistaPlanisTemporal = llistaPlanificacions;
        if (!temporal) {
            llistaPlanisTemporal = cActual.getLlistaPlan();
        }

        for (int i = 0; i < llistaPlanisTemporal.size() && !trobat; i++) {
            P = llistaPlanisTemporal.get(i);
            pIni = P.getDataInici();
            pFi = P.getDataFi();

            if (Conv.sonIgualsData(dIni, pIni) && Conv.sonIgualsData(dFi, pFi)) {
                for (int j = 0; j < P.getLlistaEmissions().size() && !trobat; j++) {
                    emissionsTemporal = P.getLlistaEmissions();

                    if (emissionsTemporal.get(j).getIdentificador().equalsIgnoreCase(nomPrograma)) {
                        e = (Emissio) emissionsTemporal.get(j);
                        //Marcam, si procedeix, l'emissio com emesa
                        if (Conv.comparacioData(e.getDataEmissio(), avui) < 0) {
                            e.setEmes(true);
                        } else {
                            if (Conv.comparacioData(e.getDataEmissio(), avui) == 0) {
                                if (Conv.horaMajor(avui, e.getDataEmissio())) {
                                    e.setEmes(true);
                                }
                            }
                        }


                        //Nomes eliminar si no ha estat emes o si es temporal
                        if (!e.getEmes() || temporal) {
                            //Si no queda cap emissio, eliminar la planificacio de l'usuari
                            if (P.delEmissioPlanificacio(e) == 0) {
                                trobat = llistaPlanisTemporal.remove(P);
                            }
                        }
                    }
                }
            }
        }
        return trobat;
    }

    public void contractar(Calendar dIni, Calendar dFi) {
        // identificam la planificacio per la seva data ini i data fin 
        boolean trobat = false;
        for (int i = 0; i <
                llistaPlanificacions.size() && !trobat; i++) {
            if (Conv.sonIgualsData(llistaPlanificacions.get(i).getDataInici(), dIni) && Conv.sonIgualsData(llistaPlanificacions.get(i).getDataFi(), dFi)) {
                cActual.listPlan.add(llistaPlanificacions.get(i));
                trobat = true;
            }

        }

    }

    public String[] getLlistaPlanificacions(boolean temporal) {
        String[] llista;
        Planificacio P;
        LinkedList llistaPClient = cActual.getLlistaPlan();
        if (temporal) {
            llistaPClient = llistaPlanificacions;
        }

        if (llistaPClient == null) {
            return llista = new String[0];
        }

        llista = new String[llistaPClient.size()];
        /* String diaIni = "";
        String mesIni = "";
        String diaFi = "";
        String mesFi = "";
         * */
        for (int i = 0; i < llistaPClient.size(); i++) {

            P = (Planificacio) llistaPClient.get(i);
            llista[i] = Conv.idPlanificacio(P.getDataInici(), P.getDataFi());

        }

        return llista;
    }

    /*Poden ser utils:*/
    /*
     * Donada una dataInici i una dataFi, que seran l'"ID" de la planificacio, i un boolea
     * que ens dira si s'ha de cercar en el client o en la llista generade per el generador,
     * retornara les emissions d'aquella planificacio, en forma de tupla.
     *  
     */
    public tuplaEmissio[] getLlistaEmissionsPlanificacio(Calendar dataIni1, Calendar dataFi1, boolean temporal) {
        Planificacio P = null;
        LinkedList<ServeiPendent> emissionsPlanif;
        tuplaEmissio sortida[] = null;
        Emissio tmp;

        if (temporal) {
            P = this.getPlanificacio(dataIni1, dataFi1);
        } else //Vol dir que hem de mirar les del client
        {
            P = cActual.getPlanificacio(dataIni1, dataFi1);
        }

        if (P != null) {
            emissionsPlanif = P.getLlistaEmissions();
            sortida = new tuplaEmissio[emissionsPlanif.size()];

            for (int i = 0; i < emissionsPlanif.size(); i++) {
                tmp = (Emissio) emissionsPlanif.get(i);
                sortida[i].dataEmissio = tmp.getDataEmissio();
                sortida[i].emes = tmp.getEmes();
                sortida[i].facturat = tmp.getFacturat();
                sortida[i].horaFi = tmp.getHoraFi();
                sortida[i].horaInici = tmp.getHoraInici();
                sortida[i].nomPrograma = emissionsPlanif.get(i).getIdentificador();
                sortida[i].preuEmissio = (float) tmp.getPreu();
            }
        }
        return sortida;
    }

    public double getPreuPlan(String planSelectedID) throws ParseException {

        Calendar idPlan[] = Conv.idPlanificacio(planSelectedID);

        Planificacio P = getPlanificacio(idPlan[0], idPlan[1]);
        if (P == null) {
            return 0;
        } else {
            return P.getPreu();
        }

    }

    private Planificacio getPlanificacio(Calendar dataIni, Calendar dataFi) {
        for (Planificacio p : llistaPlanificacions) {
            if (Conv.sonIgualsData(p.getDataInici(), dataIni) && Conv.sonIgualsData(p.getDataFi(), dataFi)) {
                return p;
            }
        }
        return null;
    }
}
