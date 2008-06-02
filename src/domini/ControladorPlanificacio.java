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
        generador = new Generador();
        llistaFrangesPreferides = new LinkedList<FranjaHoraria>();
        llistaFrangesProhibides = new LinkedList<FranjaHoraria>();
        llistaPlanificacions = new LinkedList<Planificacio>();
        llistaProgrames = new LinkedList<Programa>();
    }

    public String[][] genSet(String inici, String fin, String plani, boolean temporal) throws ParseException {

        SimpleDateFormat formatCalendar = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatCalendar2 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatHora = new SimpleDateFormat("H:mm");

        Date ini = formatCalendar.parse(inici);
        Calendar iniSetmana = Calendar.getInstance();
        iniSetmana.setTime(ini);
        iniSetmana.set(Calendar.HOUR, 0);
        iniSetmana.set(Calendar.MINUTE, 0);
        iniSetmana.set(Calendar.SECOND,0);
        
        
        Date fi = formatCalendar.parse(fin);
        Calendar fiSetmana = Calendar.getInstance();
        fiSetmana.setTime(fi);
        fiSetmana.set(Calendar.HOUR, 23);
        fiSetmana.set(Calendar.MINUTE,59);
        fiSetmana.set(Calendar.SECOND, 59);
        
        Date iniP = formatCalendar2.parse(plani.substring(0, 10));
        Calendar iniPlaniAux = Calendar.getInstance();
        iniPlaniAux.setTime(iniP);
        
        
        //Comprovar que es 23 esta be, que no es surti de rang
        Date fiP = formatCalendar2.parse(plani.substring(13, 23));
        Calendar fiPlaniAux = Calendar.getInstance();
        fiPlaniAux.setTime(fiP);
        
        Calendar iniPlani;
        Calendar fiPlani;
        
        LinkedList<Planificacio> llistaP;
        if (temporal) {
            llistaP = llistaPlanificacions;
        } else {
            llistaP = cActual.getLlistaPlan();
        }
        Planificacio p = null;
        boolean trobat = false;
        for (int i = 0; i < llistaP.size() && !trobat; i++) {
            //Aço seteja es mateixos milisegons, hores, minuts...
            iniPlani = (Calendar) llistaP.get(i).getDataInici().clone();
            iniPlani.set(Calendar.DATE, iniPlaniAux.get(Calendar.DATE));
            iniPlani.set(Calendar.MONTH,iniPlaniAux.get(Calendar.MONTH));
            iniPlani.set(Calendar.YEAR,iniPlaniAux.get(Calendar.YEAR));
            
            fiPlani = (Calendar) llistaP.get(i).getDataFi().clone();
            fiPlani.set(Calendar.DATE, fiPlaniAux.get(Calendar.DATE));
            fiPlani.set(Calendar.MONTH, fiPlaniAux.get(Calendar.MONTH));
            fiPlani.set(Calendar.YEAR, fiPlaniAux.get(Calendar.YEAR));
            
            if (llistaP.get(i).getDataInici().equals(iniPlani) 
                    && llistaP.get(i).getDataFi().equals(fiPlani))   
            {
                trobat = true;
                p = llistaP.get(i);
            }
        }
        String[][] graella = new String[144][8];
        int comptador = 0;
        int dia, horaInici, minutInici, horaFi, minutFi, posIni, posFi;
        boolean diaSeg = false;

        Date iniD = formatHora.parse("00:00");
        Calendar iniDia = Calendar.getInstance();
        iniDia.setTime(iniD);

        Date fiD = formatHora.parse("23:59");
        Calendar fiDia = Calendar.getInstance();
        fiDia.setTime(fiP);

        //Rellenem sa primera columna amb ses hores:
        while (iniDia.before(fiDia) && comptador < 144) {

            graella[comptador][0] = "" + iniDia.get(Calendar.HOUR_OF_DAY) + ":" + iniDia.get(Calendar.MINUTE);
            iniDia.add(Calendar.MINUTE, +10);
            comptador++;
        }

        for (int j = 0; j < p.getLlistaEmissions().size(); j++) {

                Emissio temp = (Emissio) p.getLlistaEmissions().get(j);
            if (!temp.getDataEmissio().before(iniSetmana) && !(temp.getDataEmissio().after(fiSetmana))
                        ||temp.getDataEmissio().equals(iniSetmana) 
                        || temp.getDataEmissio().equals(fiSetmana)) {

                dia = temp.getDataEmissio().get(Calendar.DAY_OF_WEEK);
                if (dia == 1) {
                    dia = 7;
                } else {
                    dia++;
                }
                horaInici = temp.getHoraInici().get(Calendar.HOUR_OF_DAY);
                minutInici = temp.getHoraInici().get(Calendar.MINUTE);
                posIni = (horaInici * 6) + (minutInici / 10);

                horaFi = temp.getHoraFi().get(Calendar.HOUR);
                minutFi =temp.getHoraFi().get(Calendar.MINUTE);
                posFi = (horaFi * 6) + (minutFi / 10);

                if (posIni > posFi) {
                    diaSeg = true;
                } else {
                    diaSeg = false;
                }

                while (posIni < posFi || (posIni > posFi && diaSeg) || (diaSeg && posIni > 144)) {
                    String nom = temp.getPrograma().getNom();
                    if (diaSeg && posIni > 144) {
                        dia++;
                        diaSeg = false;
                        posIni = 0;
                    }
                    graella[posIni][dia] = nom;
                    posIni += 10;
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
            boolean[] filtres = {nousCriteris.adults, nousCriteris.concurs, nousCriteris.documental, nousCriteris.esport, nousCriteris.infantil, nousCriteris.musica, nousCriteris.noticies, nousCriteris.pelicula, nousCriteris.series, nousCriteris.tertulies};
            boolean hies = false;

            for (int k = 1; k <= 10; k++) {

                if (filtres[k]) {

                    String[] programa = CProgrames.getllistaFiltrada("" + k, null);

                    for (int j = 0; j < programa.length; j++) {
                        hies = false;
                        for (int i = 0; i < llistaProgrames.size() && !hies; i++) {
                            /* Cercam si ja hi ha el programa dins llistaProgames */
                            if (llistaProgrames.get(i).getNom().equals("hool")) {
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

        prioritats[nousCriteris.primer - 1] = 1;
        prioritats[nousCriteris.segon - 1] = 2;
        prioritats[nousCriteris.tercer - 1] = 3;
        prioritats[nousCriteris.quart - 1] = 4;
        prioritats[nousCriteris.cinque - 1] = 5;

        /* DETECCIO */
        System.out.println("llistaprogrames = " + llistaProgrames.size());
        System.out.println("nousCriteris.preuMax =" + nousCriteris.preuMaxim);
        System.out.println("prioritats" + prioritats.length);
        System.out.println("llistaFrangesPreferides = " + llistaFrangesPreferides.size());
        System.out.println("llistaFrangesProhibides =" + llistaFrangesProhibides.size());
        System.out.println("data ini =" + nousCriteris.dataIni.get(Calendar.DAY_OF_MONTH));
        System.out.println("DATA FI = " + nousCriteris.dataFi.get(Calendar.DAY_OF_MONTH));
        System.out.println("NOMBRE PLANI =" + nousCriteris.nombrePlanis);
        System.out.println(" REPRO =" + RepoFranges.listaObject().size());
        System.out.println("SEPARADES = " + nousCriteris.separades);
        /**/

        LinkedList<FranjaHoraria> frangesH = RepoFranges.listaObject();
        llistaPlanificacions = generador.generar(llistaProgrames, nousCriteris.preuMaxim, prioritats, llistaFrangesPreferides, llistaFrangesProhibides, nousCriteris.dataIni, nousCriteris.dataFi, nousCriteris.nombrePlanis, frangesH, nousCriteris.separades);


        /* Pasar cada planificacio al seu identificador ( data ini + data fi) */
        String[] planificacions = new String[llistaPlanificacions.size()];
        for (int i = 0; i < llistaPlanificacions.size(); i++) {
            planificacions[i] = "" + llistaPlanificacions.get(i).getDataInici().get(Calendar.DAY_OF_MONTH) + "/" + (llistaPlanificacions.get(i).getDataInici().get(Calendar.MONTH) + 1) + "/" + llistaPlanificacions.get(i).getDataInici().get(Calendar.YEAR) + " - " + llistaPlanificacions.get(i).getDataFi().get(Calendar.DAY_OF_MONTH) + "/" + (llistaPlanificacions.get(i).getDataFi().get(Calendar.MONTH) + 1) + "/" + llistaPlanificacions.get(i).getDataFi().get(Calendar.YEAR);
        }

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
        //Petava perque no es podia fer casting de Array a Linked. Es podia
        //fer una new LinkedList(ArrayList), pero tu lo que vols es fer feina
        //directament amb sa llista de dins es client, aixi no has de preocuparte
        //de guardar cada vegada.
        cActual = nom;
        llistaPlanificacions = cActual.getLlistaPlan();
    }

    public Cliente getClient() {
        return cActual;
    }

    public void anularEmissio(String nomPrograma, Calendar dIni, Calendar dFi, boolean temporal) {
        //Transformar idPlanificacio a Calendari....
        // idPLanificacio son 2 calendars dd/mm/yyyy - dd/mm/yyyy 
        // true implica que es TEMPORAL
        boolean trobat = false;
        if (temporal == true) {
            for (int i = 0; i <
                    llistaPlanificacions.size() && !trobat; i++) {
                if (llistaPlanificacions.get(i).getDataInici().equals(dIni) && llistaPlanificacions.get(i).getDataFi().equals(dFi)) {
                    for (int j = 0; j <
                            llistaPlanificacions.get(i).getLlistaEmissions().size() && !trobat; j++) {
                        if (((Emissio) llistaPlanificacions.get(i).getLlistaEmissions().get(j)).getPrograma().equals(nomPrograma)) {
                            llistaPlanificacions.get(i).getLlistaEmissions().remove(llistaPlanificacions.get(i).getLlistaEmissions().get(j));
                            //  llistaPlanificacions.get(i).getLlistaEmissions().remove(j);
                            trobat =
                                    true;

                        }

                    }
                }
            }
        } else {
            for (int i = 0; i <
                    cActual.getLlistaPlan().size() && !trobat; i++) {
                if (cActual.getLlistaPlan().get(i).getDataInici().equals(dIni) && cActual.getLlistaPlan().get(i).getDataFi().equals(dFi)) {
                    for (int j = 0; j <
                            cActual.getLlistaPlan().get(i).getLlistaEmissions().size() && !trobat; j++) {
                        if (((Emissio) cActual.getLlistaPlan().get(i).getLlistaEmissions().get(j)).getPrograma().getNom().equals(nomPrograma)) {
                            cActual.getLlistaPlan().get(i).getLlistaEmissions().remove(cActual.getLlistaPlan().get(i).getLlistaEmissions().get(j));
                            trobat =
                                    true;
                        }

                    }
                }
            }
        }

    }

    public void contractar(Calendar dIni, Calendar dFi) {
        // identificam la planificacio per la seva data ini i data fin 
        boolean trobat = false;
        for (int i = 0; i <
                llistaPlanificacions.size() && !trobat; i++) {
            if (llistaPlanificacions.get(i).getDataInici().equals(dIni) && llistaPlanificacions.get(i).getDataFi().equals(dFi)) {
                cActual.listPlan.add(llistaPlanificacions.get(i));
                trobat =
                        true;
            }

        }

    }

    public String[] getLlistaPlanificacions() {
        String[] llista;
        LinkedList llistaPClient = cActual.getLlistaPlan();
        if (llistaPClient == null) {
            return llista = new String[0];
        }

        llista = new String[llistaPClient.size()];
        String diaIni = "";
        String mesIni = "";
        String diaFi = "";
        String mesFi = "";
        for (int i = 0; i <
                llistaPClient.size(); i++) {

            if ((((Planificacio) llistaPClient.get(i)).getDataInici()).get(Calendar.MONTH) < 10) {
                mesIni = "0" + ((((Planificacio) llistaPClient.get(i)).getDataInici()).get(Calendar.MONTH)+1);
            } else {
                mesIni = "" + ((((Planificacio) llistaPClient.get(i)).getDataInici()).get(Calendar.MONTH)+1);
            }

            if ((((Planificacio) llistaPClient.get(i)).getDataFi()).get(Calendar.MONTH) < 10) {
                mesFi = "0" + ((((Planificacio) llistaPClient.get(i)).getDataFi()).get(Calendar.MONTH)+1);
            } else {
                mesFi = "" + ((((Planificacio) llistaPClient.get(i)).getDataFi()).get(Calendar.MONTH)+1);
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

    private Planificacio getPlanificacio(Calendar dataIni, Calendar dataFi) {
        for (Planificacio p : llistaPlanificacions) {
            if (p.getDataInici().equals(dataIni) && p.getDataFi().equals(dataFi)) {
                return p;
            }
        }
        return null;
    }
}
