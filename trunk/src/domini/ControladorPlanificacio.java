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

    public String[][] genSet(String inici, String fin, String plani, int indexPlan, boolean temporal) throws ParseException {

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

        boolean trobat = false;
        Planificacio p = null;

        if (temporal) {
            indexPlan--;
            if (indexPlan > -1) {
                p = llistaP.get(indexPlan);
                if (p != null) {
                    trobat = true;
                }
            }
        } else {

            for (int i = 0; i < llistaP.size() && !trobat; i++) {
                /* Obtenim s'identificador de planificacio que estem mirant */

                iniPlani = (Calendar) llistaP.get(i).getDataInici();
                fiPlani = (Calendar) llistaP.get(i).getDataFi();


                if (Conv.sonIgualsData(iniPlaniAux, iniPlani) && Conv.sonIgualsData(fiPlaniAux, fiPlani)) {
                    trobat = true;
                    p = llistaP.get(i);
                }
            }
        }

        String[][] graella = new String[144][8];
        for (int hmm = 0; hmm < 144; hmm++) {
            for (int wowo = 1; wowo < 8; wowo++) {
                graella[hmm][wowo] = "-";
            }
        }

        //Ara tenim p, que es la planificacio que volem pintar:

        int comptador = 0;
        int dia, horaInici, minutInici, horaFi, minutFi, posIni, posFi;



        Calendar iniDia = Conv.strToCalendar("00:00");
        Calendar fiDia = Conv.strToCalendar("23:59");

        /* Rellenem sa primera columna amb ses hores: */
        while (!Conv.horaMajor(iniDia, fiDia) && comptador < 144) {

            graella[comptador][0] = Conv.getHora(iniDia);
            iniDia.add(Calendar.MINUTE, +10);
            comptador++;
        }
        if (p != null) {
            for (int j = 0; j < p.getLlistaEmissions().size(); j++) {
                ServeiPendent mirantEmissio = (ServeiPendent) p.getLlistaEmissions().get(j);

                if (!mirantEmissio.getDataReal().before(iniSetmana) && (!mirantEmissio.getDataReal().after(fiSetmana))) {

                    dia = mirantEmissio.getDataReal().get(Calendar.DAY_OF_WEEK);

                    /* SUNDAY=1, MONDAY=2, TUESDAY=3, WEDNESDAY=4, THURSDAY=5, FRIDAY=6 and SATURDAY=7 */

                    if (dia == 1) {
                        /* Si es diumenge, la pintam a sa columna nombre 8, es a dir, posicio 7 */
                        dia = 7;
                    } else {
                        dia--;
                    }

                    Calendar aux = ((Emissio) mirantEmissio).getHoraInici();
                    horaInici = aux.get(Calendar.HOUR_OF_DAY);
                    minutInici = aux.get(Calendar.MINUTE);
                    posIni = (horaInici * 6) + (minutInici / 10);

                    aux = ((Emissio) mirantEmissio).getHoraFi();
                    horaFi = aux.get(Calendar.HOUR_OF_DAY);
                    minutFi = aux.get(Calendar.MINUTE);
                    posFi = (horaFi * 6) + (minutFi / 10);
                    if (posFi == 0) {
                        posFi = 144;
                    }
                    String nom = mirantEmissio.getIdentificador();
                    while (posIni < posFi) {
                        graella[posIni][dia] = nom;
                        posIni++;
                    }
                }

            }
        }

        return graella;
    }

    /**
     * Aquesta funcio genera un vector de Strings que identifiquen les planificacions
     * generades mitjançant el format: dd/MM/yyyy - dd/MM/yyyy
     * @param programesSeleccionats Son els programes que s'han seleccionat a les vistes.
     * Si s'ha seleccionat autogeneracio, llavors aquesta llista conte tots els programes,
     * i s'hauran d'eliminar els que corresponguin a categories marcades com a fals.
     * Si no s'ha marcat cap categoria, es podra utilitzar directament aquesta llista, ja
     * que presuposarem que els volem tots.
     * 
     * @param nousCriteris La tupla de criteris que l'usuari ha seleccionat
     * @return Un string[] que identifica les planificacions generades amb el format
     * dd/MM/yyyy - dd/MM/yyyy
     * @throws java.text.ParseException
     */
    public String[] gene(Vector<String> programesSeleccionats, tuplaCriteris nousCriteris) throws ParseException {

        //Netejem la llista global de programes
        llistaProgrames.clear();
        llistaFrangesPreferides.clear();
        llistaFrangesProhibides.clear();
        llistaPlanificacions = new LinkedList<Planificacio>();

        /**Si s'ha seleccionat autogeneracio, mirarem si existeixen filtres marcats.
         * Si existeixen, buidarem la llista programesSeleccionats i  per cada filtre
         * seleccionat, obtindrem la llista de programes corresponents i els afegirem
         * tots al vector programesSeleccionats.
         * Si no existeixen, ens servira la llista programesSeleccionats per saber
         * quins s'han seleccionat.
         */
        if (nousCriteris.autoGen) {

            boolean[] filtres = nousCriteris.filtres;
            String[] programesFiltrats;
            boolean nhia = false;


            /**Per cada filtre de categoria (Adults, Pelicula, Serie, etc.) mirem
             *si n'hi ha algun de marcat, llavors esborrem la llista i passem a
             *omplirla
             */
            for (int k = 0; k < 11 && !nhia; k++) {
                if (filtres[k]) {
                    nhia = true;
                    programesSeleccionats.clear();
                }
            }

            if (nhia) {
                for (int k = 0; k < 11; k++) {

                    //Mirem si el filtre esta seleccionat
                    if (filtres[k]) {
                        programesFiltrats = CProgrames.getllistaFiltrada("categoria", "" + k);

                        for (int j = 0; j < programesFiltrats.length; j++) {
                            programesSeleccionats.add(programesFiltrats[j]);
                        }
                    }
                }
            }
        }

        String[] tomatiga = new String[programesSeleccionats.size()];
        llistaProgrames = CProgrames.llistarProgramesNom(programesSeleccionats.toArray(tomatiga));
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

        prioritats = new int[5];
        prioritats[nousCriteris.prioritats[0] - 1] = 1;
        prioritats[nousCriteris.prioritats[1] - 1] = 2;
        prioritats[nousCriteris.prioritats[2] - 1] = 3;
        prioritats[nousCriteris.prioritats[3] - 1] = 4;
        prioritats[nousCriteris.prioritats[4] - 1] = 5;

        LinkedList<FranjaHoraria> frangesH = RepoFranges.listaObject();

        llistaPlanificacions = generador.generar(llistaProgrames, nousCriteris.preuMaxim, prioritats, llistaFrangesPreferides, llistaFrangesProhibides, nousCriteris.dataIni, nousCriteris.dataFi, nousCriteris.nombrePlanis, frangesH);


        /* Pasar cada planificacio al seu identificador ( data ini + data fi) */
        String[] planificacions = new String[llistaPlanificacions.size()];

        for (int i = 0; i < llistaPlanificacions.size(); i++) {
            Calendar aux = (Calendar) llistaPlanificacions.get(i).getDataInici().clone();
            Calendar aux3 = (Calendar) llistaPlanificacions.get(i).getDataFi().clone();
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

    public String genResum(String idPlanificacio, int indexPlan, boolean temporal) {
        LinkedList<Planificacio> llistaP = llistaPlanificacions;
        String out = new String();

        if (!temporal) {
            llistaP = cActual.getLlistaPlan();
        }

        Calendar aux2[] = Conv.idPlanificacio(idPlanificacio);
        Calendar iniPlaniAux = aux2[0];
        Calendar fiPlaniAux = aux2[1];
        Calendar iniPlani;
        Calendar fiPlani;


        Planificacio p = null;
        boolean trobat = false;
        if (!temporal) {
            for (int i = 0; i < llistaP.size() && !trobat; i++) {
                iniPlani = (Calendar) llistaP.get(i).getDataInici();
                fiPlani = (Calendar) llistaP.get(i).getDataFi();

                if (Conv.sonIgualsData(iniPlaniAux, iniPlani) && Conv.sonIgualsData(fiPlaniAux, fiPlani)) {
                    trobat = true;
                    p = llistaP.get(i);
                }
            }
        } else {
            p = llistaP.get(indexPlan);
        }

        if (p != null) {
            for (int j = 0; j < p.getLlistaEmissions().size(); j++) {
                ServeiPendent mirantEmissio = (ServeiPendent) p.getLlistaEmissions().get(j);
                out = out + "" + "Nom Programa: " + mirantEmissio.getIdentificador() + "\n" + "Data Emissio: " + Conv.dateToStr(mirantEmissio.getDataReal()) + "\nHora Inici emissio: " + Conv.getHora(((Emissio) mirantEmissio).getHoraInici()) + "\nHora Fi emissio:" + Conv.getHora(((Emissio) mirantEmissio).getHoraFi()) + "\nPreu: " + mirantEmissio.getPreu() + "\n-------------------------------------\n\n";
            }

        }
        return out;
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
    public boolean anularEmissio(String nomPrograma, Calendar dIni, Calendar dFi, int indexPlan, boolean temporal) {
        // idPLanificacio son 2 calendars dd/mm/yyyy - dd/mm/yyyy 
        // true implica que es TEMPORAL
        Emissio e = null;
        Planificacio P = null;
        Calendar pIni, pFi, avui;
        avui = Calendar.getInstance();
        boolean resultat = false;
        boolean trobat = false;
        LinkedList<ServeiPendent> emissionsTemporal = null;

        LinkedList<Planificacio> llistaPlanisTemporal = llistaPlanificacions;
        if (!temporal) {
            llistaPlanisTemporal = cActual.getLlistaPlan();
        }

        //Cerquem la Planificacio a la llista:
        if (!temporal) {
            for (int i = 0; i < llistaPlanisTemporal.size() && !trobat; i++) {
                P = llistaPlanisTemporal.get(i);
                pIni = P.getDataInici();
                pFi = P.getDataFi();

                if (Conv.sonIgualsData(dIni, pIni) && Conv.sonIgualsData(dFi, pFi)) {
                    emissionsTemporal = P.getLlistaEmissions();
                    trobat = true;
                }
            }
            trobat = false;
        } else {
            indexPlan--;
            P = llistaPlanisTemporal.get(indexPlan);
            emissionsTemporal = P.getLlistaEmissions();
        }
        //Ara tenim la llista d'emissions de la planificacio trobada, cercarem la emissio ssi
        //hem trobat emissions
        if (emissionsTemporal != null) {
            for (int j = 0; j < emissionsTemporal.size() && !trobat; j++) {
                if (emissionsTemporal.get(j).getIdentificador().equalsIgnoreCase(nomPrograma)) {
                    trobat = true;
                    e = (Emissio) emissionsTemporal.get(j);
                }
            }
        }

        //Si hem trobat l'emissio procedim de formes diferents si son emissions temporals o no:

        if (trobat) {
            if (temporal) {
                if (P.delEmissioPlanificacio(e) == 0) {
                    llistaPlanisTemporal.remove(P);
                }
                resultat = true;
            }

            if (!temporal) {
                //Mirem si la podem eliminar
                //Ja s'ha emes la emissio?, si s'ha emes impossible eliminar.
                if (Conv.comparacioData(e.getDataEmissio(), avui) < 0) {
                    e.setEmes(true);
                    resultat = false;
                } else {
                    if (Conv.comparacioData(e.getDataEmissio(), avui) == 0) {
                        if (Conv.horaMajor(avui, e.getHoraInici())) {
                            e.setEmes(true);
                            resultat = false;
                        }
                    } else {
                        if (!e.getEmes() && !e.getFacturat()) {
                            if (P.delEmissioPlanificacio(e) == 0) {
                                llistaPlanisTemporal.remove(P);
                            }
                            resultat = true;
                        }
                    }
                }
            }
        }

        return resultat;
    }

    public void contractar(Calendar dIni, Calendar dFi, int indexPlan) {
        // identificam la planificacio per la seva data ini i data fin 
        boolean trobat = false;
        /**
        for (int i = 0; i < llistaPlanificacions.size() && !trobat; i++) {
        if (Conv.sonIgualsData(llistaPlanificacions.get(i).getDataInici(), dIni) && Conv.sonIgualsData(llistaPlanificacions.get(i).getDataFi(), dFi)) {
        cActual.addPlanificacio(llistaPlanificacions.get(i));
        trobat = true;
        }
        }
         */
        cActual.addPlanificacio(llistaPlanificacions.get(indexPlan - 1));

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
        /** String diaIni = "";
        String mesIni = "";
        String diaFi = "";
        String mesFi = "";
         */
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
            P = this.getPlanificacio(dataIni1, dataFi1, temporal);
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

    public double getPreuPlan(String planSelectedID, boolean temporal) throws ParseException {

        Calendar idPlan[] = Conv.idPlanificacio(planSelectedID);

        Planificacio P = getPlanificacio(idPlan[0], idPlan[1], temporal);
        if (P == null) {
            return 0;
        } else {
            return P.getPreu();
        }

    }

    public double getPreuPlan(String planSelectedID, int index, boolean temporal) throws ParseException {

        Calendar idPlan[] = Conv.idPlanificacio(planSelectedID);
        Planificacio P;
        if (temporal) {
            P = getPlanificacio(index);
        } else {
            P = getPlanificacio(idPlan[0], idPlan[1], temporal);
        }
        if (P == null) {
            return 0;
        } else {
            return P.getPreu();
        }

    }

    /**
     * Aquesta funcio cerca per data ini i data fi una planificacio
     * @param dataIni
     * @param dataFi
     * @param temporal
     * @return
     */
    private Planificacio getPlanificacio(Calendar dataIni, Calendar dataFi, boolean temporal) {
        LinkedList<Planificacio> lTemp = llistaPlanificacions;
        if (!temporal) {
            lTemp = cActual.getLlistaPlan();
        }

        for (Planificacio p : lTemp) {
            if (Conv.sonIgualsData(p.getDataInici(), dataIni) && Conv.sonIgualsData(p.getDataFi(), dataFi)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Aquesta funcio cerca per index una planificacio.
     * @param dataIni
     * @param dataFi
     * @param index
     * @param temporal
     * @return
     */
    private Planificacio getPlanificacio(int index) {

        LinkedList<Planificacio> lTemp = llistaPlanificacions;

        for (int i = 0; i < lTemp.size(); i++) {
            Planificacio p = lTemp.get(i);
            if (p.getId() == index) {
                return p;
            }
        }
        return null;
    }
}
