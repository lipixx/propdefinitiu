package domini;

import domini.programa.Directe;
import domini.programa.Normal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/**
 * La classe Generador proveeix l'objecte amb els atributs i estructures de dades,
 * aixi com les operacions necessaries per accedir i modificar els camps.
 * 
 * Un Generador s'identifica per una llista de Programes llistaProgs.
 * 
 * @author  Josep Marti 41743212Y
 * @version 3.0, 30 Maig 2008 
 * 
 *
 */
public class Generador {

    LinkedList<Planificacio> llistaPlanificacionsGenerades;
    LinkedList<FranjaHoraria> repositoriFranges;
    LinkedList<FranjaHoraria> llistaFranges;
    LinkedList<FranjaHoraria> listFranAux;
    LinkedList<Programa> llistaProgrames;
    LinkedList<Programa> listProgsAux;
    boolean preuSobrepassat = false;
    private Planificacio plani;
    Calendar calendarFi;
    boolean directe = false;
    float ajust, preuTotal = 0;
    int nemis = 0;
    int numCriterisAfluixats = 0;
    int[][] criteris = new int[5][2];

    public Generador() {

        llistaPlanificacionsGenerades = new LinkedList<Planificacio>();
        repositoriFranges = new LinkedList<FranjaHoraria>();
        llistaFranges = new LinkedList<FranjaHoraria>();
        listFranAux = new LinkedList<FranjaHoraria>();
        llistaProgrames = new LinkedList<Programa>();
        listProgsAux = new LinkedList<Programa>();
    }

    @SuppressWarnings("empty-statement")
    public LinkedList<Planificacio> generar(LinkedList<Programa> llistaProgs, float preuMax, int[] prioritats, LinkedList<FranjaHoraria> fPreferides, LinkedList<FranjaHoraria> fProhibides, Calendar superDataIni, Calendar superDataFi, int nPlanis, LinkedList<FranjaHoraria> llistaFranT) throws ParseException {

        int total = 0;
        int countadorIguals = 0;
        int maxIteracions = 500;
        boolean ok = true;
        String valor[] = new String[2];
        int maxEmisDia = 1;
        int numEmisDia = 0;
        boolean planNova = true;
        boolean igual = false;
        Calendar in = superDataIni;
        int dia, mes, any;
        Calendar dataEmissio = null, dataFiEmissio = null;
        Calendar min = null, max = null;
        SimpleDateFormat formatCalendar = new SimpleDateFormat("dd-MM-yyyy");

        for (int k = 0; k < 5; k++) {
            /* INICIALITZAR CRITERIS AMB ELS VALORS PASSAT PER PARAMETRE A PRIORITATS */
            /* Maxima prioritat =1, minima prioritat = 5 */
            criteris[k][0] = prioritats[k];
        }

        /* Inicialitzar llista de programes i franges d'acord amb els criteris esecificats */

        for (int i = 0; i < llistaProgs.size(); i++) {
            llistaProgrames.add(llistaProgs.get(i));
        }


        for (int j = 0; j < llistaFranT.size(); j++) {
            repositoriFranges.add(llistaFranT.get(j));
        }

        if (criteris[0][0] == 1) {

            /* El preu es el criteris mes prioritari*/
            /* Ordenam les franges preferides per el seu preu, si no hi ha cap franja preferida definida es crea */

            for (int i = 0; i < fPreferides.size(); i++) {
                listFranAux.add(fPreferides.get(i));
            }
            if (listFranAux.size() == 0) {

                crearFrangesNoProhibidesNiPreferides(fPreferides, fProhibides);

                for (int k = 0; k < llistaFranges.size(); k++) {
                    listFranAux.add(llistaFranges.get(k));
                }
            }

            llistaFranges = ordenarFrangesPreu(listFranAux);

            for (int i = 0; i < llistaProgs.size(); i++) {
                listProgsAux.add(llistaProgs.get(i));
            }
            llistaProgrames = ordenarProgramesPreu(listProgsAux);

        } else {

            for (int i = 0; i < fPreferides.size(); i++) {
                listFranAux.add(fPreferides.get(i));
            }

            if (listFranAux.size() == 0) {

                crearFrangesNoProhibidesNiPreferides(fPreferides, fProhibides);
            }
        }
        /* Fi inicialitzacio de programes i franges */


        /* Comencem a generar les planificacions */
        while (nPlanis > llistaPlanificacionsGenerades.size() && total < maxIteracions) {

            inicialitzarCriteris(prioritats);

            if (planNova && !igual) {

                plani = new Planificacio(superDataIni, superDataFi);

                /* Setejam les dates dataEmissio i dataFiEmissio a la data superDataIni i superDataFi */
                dia = superDataIni.get(Calendar.DAY_OF_MONTH);
                mes = (superDataIni.get(Calendar.MONTH) + 1);
                any = superDataIni.get(Calendar.YEAR);


                Date dat = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
                dataEmissio = Calendar.getInstance();
                dataEmissio.setTime(dat);

                dia = superDataFi.get(Calendar.DAY_OF_MONTH);
                mes = (superDataFi.get(Calendar.MONTH) + 1);
                any = superDataFi.get(Calendar.YEAR);

                dat = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
                dataFiEmissio = Calendar.getInstance();
                dataFiEmissio.setTime(dat);
            }


            /* Si hem elegit la opcio de generar automatica el numero d'emissions per dia la generarem per cada planificacio */
            Random rnd = new Random();
            maxEmisDia = (int) (rnd.nextDouble() * 123456.0);
            maxEmisDia %= 2;
            maxEmisDia++;


            if (llistaPlanificacionsGenerades.size() > 0) {
                /* Ordenam els programes de forma aleatoria */
                /* En la primera planificacio els programes estaran ordenats per ordre de preu si el criteri principal es el preu i sino en l'ordre en que l'usuari els ha introduit */
                rnd = new Random();
                mesclarProgrames(llistaProgrames, rnd);
            }


            /* Numero de programes que a priori volem que constin a la nostra planificacio */
            int n = llistaProgrames.size();


            /* Definim cada planificacio */
            while (nemis < n && numCriterisAfluixats <= 5) {//!dataEmissio.after(dataFiEmissio)) {


                for (int i = 0; i < llistaProgrames.size() && !preuSobrepassat; i++) {

                    ok = false;

                    if (!pertanyPlanificacio(llistaProgrames.get(i))) {

                        dia = dataEmissio.get(Calendar.DAY_OF_MONTH);
                        mes = (dataEmissio.get(Calendar.MONTH) + 1);
                        any = dataEmissio.get(Calendar.YEAR);

                        Date dat = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
                        Calendar copiaDataEmissio = Calendar.getInstance();
                        copiaDataEmissio.setTime(dat);

                        if (((llistaProgrames.get(i).getClass()).getName().charAt(llistaProgrames.get(i).getClass().getName().length() - 1)) == 'D') {
                            /* Si es tracta d'un programa de format DIRECTE setejarem la data a la del programa */

                            directe = true;
                            dia = ((Directe) (llistaProgrames.get(i))).getIniciEmissio().get(Calendar.DAY_OF_MONTH);
                            mes = (((Directe) (llistaProgrames.get(i))).getIniciEmissio().get(Calendar.MONTH) + 1);
                            any = ((Directe) (llistaProgrames.get(i))).getIniciEmissio().get(Calendar.YEAR);

                            dat = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
                            dataEmissio = Calendar.getInstance();
                            dataEmissio.setTime(dat);

                        } else {
                            directe = false;
                        }

                        if (criteris[4][1] == 0) {
                            /* Criteri Dates Planificacio invalidat, per tant augmentem la dataFiEmissio */

                            dia = superDataFi.get(Calendar.DAY_OF_MONTH);
                            mes = (superDataFi.get(Calendar.MONTH) + 1);
                            any = superDataFi.get(Calendar.YEAR);

                            formatCalendar = new SimpleDateFormat("dd-MM-yyyy");
                            dat = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
                            dataFiEmissio = Calendar.getInstance();
                            dataFiEmissio.setTime(dat);

                            dataFiEmissio.add(Calendar.DAY_OF_MONTH, +1);
                        }

                        while (!dataEmissio.after(dataFiEmissio) && !preuSobrepassat && !ok) {


                            if (criteris[1][1] == 0) {
                                /* Franges Preferides deshabilitades */
                                crearFrangesNoProhibidesNiPreferides(fPreferides, fProhibides);
                            }

                            if (criteris[2][1] == 0) {
                                /* Franges Prohibides deshabilitades */
                                /* Agafem totes les franges dels repositoris, les prohibides tambe i les ordenam per preu */
                                llistaFranges.clear();
                                listFranAux.clear();

                                for (int j = 0; j < repositoriFranges.size(); j++) {
                                    listFranAux.add(repositoriFranges.get(j));
                                }
                                llistaFranges = ordenarFrangesPreu(listFranAux);
                            }


                            for (int j = 0; j < llistaFranges.size() && !preuSobrepassat && !ok; j++) {

                                valor = factible(llistaProgrames.get(i), llistaFranges.get(j), dataEmissio);

                                if (valor[0].equalsIgnoreCase("1")) {

                                    /* calendarInici es la hora d'inici de la emissio, ens la retorna la funcio factible dins valor[0] */
                                    SimpleDateFormat formatCalendar2 = new SimpleDateFormat("H:mm");
                                    Date dateIni = formatCalendar2.parse(valor[1]);
                                    Calendar calendarInici = Calendar.getInstance();
                                    calendarInici.setTime(dateIni);

                                    /* Cercam la franja associada a la nova emissio per poder definir un preu d'emissio */
                                    FranjaHoraria franja = cercarFranja(llistaFranges.get(j).getHoraInici(), repositoriFranges);

                                    Emissio emi = new Emissio(dataEmissio, false, false, llistaProgrames.get(i), franja, calendarInici, calendarFi);


                                    if (criteris[0][1] == 1) {
                                        /* Si tenim la opcio del preu maxim deshabilitada no farem la comparacio i afegirem la emissio directament, altrament mirarem de no sobrepassar el limit */
                                        if ((preuTotal + emi.getPreuEmissio()) <= preuMax) {

                                            if (min == null) {
                                                min = (Calendar) dataEmissio.clone();
                                                max = (Calendar) dataEmissio.clone();
                                            } else if (dataEmissio.before(min)) {
                                                min = (Calendar) dataEmissio.clone();
                                            }

                                            if (dataEmissio.after(max)) {
                                                max = (Calendar) dataEmissio.clone();
                                            }
                                            plani.addEmissioPlanificacio(emi);
                                            nemis++;
                                            numEmisDia++;
                                            preuTotal += emi.getPreuEmissio();
                                            ok = true;
                                        } else {

                                            preuSobrepassat = true;
                                        }
                                    } else {

                                        if (min == null) {
                                            min = (Calendar) dataEmissio.clone();
                                            max = (Calendar) dataEmissio.clone();
                                        } else if (dataEmissio.before(min)) {
                                            min = (Calendar) dataEmissio.clone();
                                        }
                                        if (dataEmissio.after(max)) {
                                            max = (Calendar) dataEmissio.clone();
                                        }
                                        plani.addEmissioPlanificacio(emi);
                                        nemis++;
                                        numEmisDia++;
                                        preuTotal += emi.getPreuEmissio();
                                        ok = true;
                                        preuSobrepassat = false;
                                    }


                                } else if (directe) {
                                    n--;
                                }
                            }

                            if (numEmisDia >= maxEmisDia) {
                                dataEmissio.add(Calendar.DAY_OF_MONTH, +1);
                                numEmisDia = 0;
                                if (dataEmissio.after(dataFiEmissio)) {
                                    dataFiEmissio.add(Calendar.DAY_OF_MONTH, +1);
                                }
                            }
                        }

                        if (directe) {
                            /* Si s'havia canviat la data d'emissio perque es tractava d'un programa de format directe, ara la reestablim */

                            dia = copiaDataEmissio.get(Calendar.DAY_OF_MONTH);
                            mes = (copiaDataEmissio.get(Calendar.MONTH) + 1);
                            any = copiaDataEmissio.get(Calendar.YEAR);

                            dat = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
                            dataEmissio = Calendar.getInstance();
                            dataEmissio.setTime(dat);
                        }
                    }
                }


                if (criteris[3][1] == 0) {
                    /* Si el criteri "programes" esta desabilitat decrementem el numero de programes a buscar */
                    n--;
                }

                if (nemis < llistaProgrames.size()) {
                    /* Si hem recorregut tots els programes i no hem trobat les emissions que tindria que tenir
                    afluixam els criteris per veure si podem trobar alguna emissio que s'aproximi el maxim
                    possible a les nostres preferencies */
                    afluixarCriteris();
                    planNova = false;
                    preuSobrepassat = false;
                } else {
                    planNova = true;
                }

            }


            /* COMMPROVAM QUE NO EXISTEIX UNA PLANIFICACIO IGUAL */
            total++;

            preuTotal = 0;
            nemis = 0;
            preuSobrepassat = false;
            numCriterisAfluixats = 0;
            if (plani.getLlistaEmissions().size() == 0) {
                llistaPlanificacionsGenerades.add(plani);

            } else {

                igual = false;

                for (int i = 0; i < llistaPlanificacionsGenerades.size() && !igual; i++) {
                    for (int k = 0; k < plani.getLlistaEmissions().size() && !igual; k++) {
                        for (int j = 0; j < llistaPlanificacionsGenerades.get(i).getLlistaEmissions().size() && !igual; j++) {

                            if (k == j && llistaPlanificacionsGenerades.get(i).getLlistaEmissions().get(j).getIdentificador().equalsIgnoreCase(plani.getLlistaEmissions().get(j).getIdentificador()) && sonIguals(llistaPlanificacionsGenerades.get(i).getLlistaEmissions().get(j).getDataReal(), plani.getLlistaEmissions().get(j).getDataReal()) && sonHoresIguals(((Emissio) llistaPlanificacionsGenerades.get(i).getLlistaEmissions().get(j)).getHoraInici(), ((Emissio) plani.getLlistaEmissions().get(j)).getHoraInici()) && sonHoresIguals(((Emissio) llistaPlanificacionsGenerades.get(i).getLlistaEmissions().get(j)).getHoraFi(), ((Emissio) plani.getLlistaEmissions().get(j)).getHoraFi())) {
                                if (countadorIguals == 2) {
                                    in.add(Calendar.DAY_OF_MONTH, +1);
                                    countadorIguals = -1;
                                }
                                countadorIguals++;
                                igual = true;

                            }
                        }
                    }
                }

                if (!igual) {

                    plani.setDataInici(min);
                    plani.setDataFi(max);
                    llistaPlanificacionsGenerades.add(plani);

                }
            }

        /* Fi comprovacio igualtat */

        }
        return llistaPlanificacionsGenerades;
    }

    private void afluixarCriteris() {
        int pos = 0;
        boolean trobat = false;

        for (int j = 0; j <
                5 && !trobat; j++) {
            /* Cercar el primer element que estigui habilitat */
            if (criteris[j][1] == 1) {
                trobat = true;
                pos =
                        j;
            }

        }
        for (int i = 1; i <
                5; i++) {
            if (criteris[i][1] == 1 && criteris[i][0] > criteris[pos][0]) {
                /*Seleccionam l'element amb una prioritat menor*/
                /* Maxima prioritat =1, minima prioritat = 5 */
                pos = i;
            }

        }
        /* Desabilitam el criteri que era menys prioritari */
        criteris[pos][1] = 0;
        numCriterisAfluixats++;

    }

    private FranjaHoraria cercarFranja(Calendar horaInici, LinkedList<FranjaHoraria> llistaFran) throws ParseException {
        /* Cerca dins el repositori de franges la franja que conte horaInici. Aixi sabem a qunia franja pertany una emissio. */

        FranjaHoraria fra = llistaFran.get(0);

        for (int i = 0; i <
                llistaFran.size(); i++) {
            if (!horaInici.before(llistaFran.get(i).getHoraInici()) && horaInici.before(llistaFran.get(i).getHoraFi())) {
                return llistaFran.get(i);
            } else {
                if (llistaFran.get(i).getHoraInici().after(fra.getHoraInici())) {
                    fra = llistaFran.get(i);
                }

            }

        }
        return fra;
    /* Si no ha trobat la franja es perque la franja esta definida per una horaInici abans de 00:00 i un horaFi despres de 00:00,
    per tant, retornam la darrera franja */
    }

    private void crearFrangesNoProhibidesNiPreferides(LinkedList<FranjaHoraria> fPreferides, LinkedList<FranjaHoraria> fProhibides) throws ParseException {
        /* Crea les franges que no son ni prohibides ni preferides i les guarda a llistaFranges */

        listFranAux = fusionarFranges(fPreferides, fProhibides);
        llistaFranges.clear();

        SimpleDateFormat formatCalendar2 = new SimpleDateFormat("H:mm");
        Date dateIni = formatCalendar2.parse("00:00");
        Calendar ini = Calendar.getInstance();
        ini.setTime(dateIni);

        Calendar fi = Calendar.getInstance();
        Calendar zero = Calendar.getInstance();
        fi.setTime(dateIni);
        zero.setTime(dateIni);


        if (listFranAux.size() == 0) {

            /*  AQUESTA PRIMERA FRANJA REPRESENTA EL "PRIME TIME"
             * 
             *  Per decidir quin es el PRIME TIME hem fet la mitja:
             * 
             *  Normalment acaba sobre les 00:00 o 01:00.
             *      
             *      En canvi, les hores d'inici varien més:
             * 
             *      La Sexta:   20:30 
             *      Cuatro:     22:00
             *      Tele5:      22:00  
             * 
             *  D'AQUESTA MANERA, NOSALTRES PRENEM L'INTERVAL 21:00 - 00:00 com a PRIME TIME
             * 
             */

            dateIni = formatCalendar2.parse("21:00");
            ini =
                    Calendar.getInstance();
            ini.setTime(dateIni);

            Date dateFi = formatCalendar2.parse("23:59");
            fi =
                    (Calendar) ini.clone();
            // Calendar.getInstance();
            fi.setTime(dateFi);

            FranjaHoraria franja = new FranjaHoraria(ini, fi, (float) 0.10);
            llistaFranges.add(franja);

            /* 2 FRANGES COBREIXEN LES 24 HORES DEL DIA */

            dateIni =
                    formatCalendar2.parse("00:00");
            ini =
                    Calendar.getInstance();
            ini.setTime(dateIni);

            dateFi =
                    formatCalendar2.parse("20:59");
            fi =
                    (Calendar) ini.clone();
            // Calendar.getInstance();
            fi.setTime(dateFi);
            franja =
                    new FranjaHoraria(ini, fi, (float) 0.10);
            llistaFranges.add(franja);

        } else if (!(listFranAux.size() == 1 && sonHoresIguals(listFranAux.get(0).getHoraInici(), listFranAux.get(0).getHoraFi()))) {

            boolean afegir = false, primeraV = true, cavalga = false;

            for (int i = 0; i <
                    listFranAux.size(); i++) {

                /* La primera franja comença a les 00:00    */
                if (sonHoresIguals(listFranAux.get(i).getHoraInici(), zero)) {
                    ini = listFranAux.get(i).getHoraFi();
                } else if (primeraV && listFranAux.get(listFranAux.size() - 1).getHoraFi().before(listFranAux.get(listFranAux.size() - 1).getHoraInici())) {
                    /* Hi ha cavalgament */
                    ini = listFranAux.get(listFranAux.size() - 1).getHoraFi();
                    fi =
                            listFranAux.get(i).getHoraInici();
                    cavalga =
                            true;
                }

                if (i + 1 < listFranAux.size() && !cavalga) {
                    /* Si hi ha una altra franja i no cavalgament */
                    if (!sonHoresIguals(listFranAux.get(0).getHoraInici(), zero)) {
                        fi = listFranAux.get(i).getHoraInici();
                    } else { /* horaInici primera franja == 00:00 */
                        fi = listFranAux.get(i + 1).getHoraInici();
                    }

                /* Si no hi ha cap més franja i no hi ha cavalgament */
                } else if (!cavalga) {
                    if (!sonHoresIguals(listFranAux.get(i).getHoraFi(), zero) && !sonHoresIguals(listFranAux.get(i).getHoraInici(), zero) && !sonHoresIguals(ini, listFranAux.get(i).getHoraFi())) {
                        fi = listFranAux.get(i).getHoraInici();
                        afegir =
                                true;
                    } else {
                        fi.setTime(dateIni);
                    /* fi = 00:00 */

                    }

                }

                FranjaHoraria franja = new FranjaHoraria(ini, fi, (float) 0.00);
                llistaFranges.add(franja);

                if (afegir) {

                    franja = new FranjaHoraria(listFranAux.get(i).getHoraFi(), zero, (float) 0.00);
                    llistaFranges.add(franja);
                    afegir =
                            false;
                }

                if (i + 1 < listFranAux.size()) {

                    if (!sonHoresIguals(listFranAux.get(0).getHoraInici(), zero)) {
                        ini = listFranAux.get(i).getHoraFi();
                    } else { /* horaInici primera franja == 00:00 */
                        ini = listFranAux.get(i + 1).getHoraFi();
                    }

                }
                if (cavalga) {
                    ini = listFranAux.get(i).getHoraFi();
                    if (i + 1 < listFranAux.size()) {
                        fi = listFranAux.get(i + 1).getHoraInici();
                    }

                }
                primeraV = false;
            }

        }
    }

    private int duracio(Programa prog) {
        int duracio;    /* en minuts */

        if (((prog.getClass()).getName().charAt(prog.getClass().getName().length() - 1)) == 'D') {
            /* ES DIRECTE */
            duracio = ((Directe) prog).getDuracio();

        } else if (((prog.getClass()).getName().charAt(prog.getClass().getName().length() - 1)) == 'N') {
            /* ES NORMAL */
            duracio = ((Normal) prog).getDuracio();
        } else {
            /* Simplemenet per afergir un poc de pseudo alietorietat afegirem un random per determinat la
            duracio dels programes de format continu, perquè l'usuari sense intervenir pugui obtenir 
            planificacions diferents. Per tant ho farem un poc mes "aleatori"  */

            Random rnd = new Random();
            int x = (int) (rnd.nextDouble() * 123456.0);
            x %=
                    7;

            switch (x) {
                case 1:
                    duracio = 15;
                    break;
                case 2:
                    duracio = 45;
                    break;
                case 3:
                    duracio = 60;
                    break;
                case 4:
                    duracio = 75;
                    break;
                case 5:
                    duracio = 90;
                    break;
                case 6:
                    duracio = 120;
                    break;
                default:
                    duracio = 15;
                    break;
                }
        /* Suposem que una emissio de menys de 15 minuts ni de mes de 120 minuts no s'ha de generar de forma AUTOMATICA */

        }

        return duracio;
    }

    private String[] factible(Programa prog, FranjaHoraria franja, Calendar dataEmissio) throws ParseException {
        /* Es pot emetre el Programa prog dins la FranjaHoraria franja dia dataEmissio sense que es solapi amb una altra Emissio?
         * si es aixi ens encarregarem d'inicialitzar les hores de l'emissio: calendarInici, calendarFi mitjançant la funcio "solapa"   */

        String valor[] = new String[2];

        boolean ok = false;
        if (prog.getDataCaducitat().after(dataEmissio)) {

            valor = solapa(prog, franja, dataEmissio);

            if (!valor[0].equalsIgnoreCase("1")) {
                /* SI NO ES SOLAPA  */

                ok = true;
            }

        }
        if (ok) {
            valor[0] = "" + 1;
        } else {
            valor[0] = "" + 0;
        }

        /* TAMBE PASSAR LA DATA INICI A VALOR[1] */
        return valor;
    }

    private LinkedList<FranjaHoraria> fusionarFranges(LinkedList<FranjaHoraria> prefes, LinkedList<FranjaHoraria> prohibs) throws ParseException {

        LinkedList<FranjaHoraria> llista = new LinkedList<FranjaHoraria>();
        FranjaHoraria un = null, dos = null;
        FranjaHoraria franja = null;

        Calendar inici = null, fi = null;
        boolean p = false, f = false;

        for (int j = 0; j <
                prefes.size(); j++) {
            listFranAux.add(prefes.get(j));
            f =
                    true;
        }

        for (int j = 0; j <
                prohibs.size(); j++) {
            listFranAux.add(prohibs.get(j));
            p =
                    true;
        }

        if (!f || !p) {
            /* Si no hi ha cap franja preferida ni prohibida, o be si nomes en tenim de preferides o de prohibides */

            llista = ordenarFrangesHora(listFranAux);
            return llista;
        }

        llistaFranges = ordenarFrangesHora(listFranAux);

        boolean esSolapen = false;

        while (llistaFranges.size() > 1) {
            /* MENTRE COM A MINIM HI HAGI 2 FRANGES */

            un = llistaFranges.get(0);
            dos =
                    llistaFranges.get(1);

            /* SI ES SOLAPEN    */

            /*Com ordenarFrangesHora ens garanteix que l'hora d'inici de la primera franja (un) es igual o anterior 
            a la hora d'inici de la segona franja(dos) */
            if (!dos.getHoraInici().after(un.getHoraFi())) {
                esSolapen = true;
                inici =
                        un.getHoraInici();
                if (un.getHoraFi().after(dos.getHoraFi())) {
                    fi = un.getHoraFi();
                } else {
                    fi = dos.getHoraFi();
                }

            } else {   /* NO HI HA SOLAPACIO   */

                esSolapen = false;
                inici =
                        un.getHoraInici();
                fi =
                        un.getHoraFi();

            }

            franja = new FranjaHoraria(inici, fi, (float) 0);

            if (esSolapen) {
                llistaFranges.remove(0);
                llistaFranges.remove(0);
                llistaFranges.addFirst(franja);

            } else {
                llista.add(franja);
                llistaFranges.remove(0);

            }

        }

        if (llistaFranges.size() == 1) {
            franja = new FranjaHoraria(llistaFranges.get(0).getHoraInici(), llistaFranges.get(0).getHoraFi(), (float) 0);
            llista.add(franja);
            llistaFranges.clear();
        }

        return llista;
    }

    private void inicialitzarCriteris(int[] prioritats) {
        /* Activam tots els criteris, i => activat */
        for (int k = 0; k <
                5; k++) {
            criteris[k][1] = 1;
        }

    }

    private LinkedList<FranjaHoraria> ordenarFrangesHora(LinkedList<FranjaHoraria> franges) {

        LinkedList<FranjaHoraria> llista = new LinkedList<FranjaHoraria>();

        while (0 < franges.size()) {
            int p = posMinHora(franges, 0, franges.size() - 1);
            llista.add(franges.get(p));
            franges.remove(p);
        }

        return llista;
    }

    private LinkedList<FranjaHoraria> ordenarFrangesPreu(LinkedList<FranjaHoraria> franges) {

        LinkedList<FranjaHoraria> llista = new LinkedList<FranjaHoraria>();

        while (0 < franges.size()) {
            int p = posMinFrang(franges, 0, franges.size() - 1);
            llista.add(franges.get(p));
            franges.remove(p);
        }

        return llista;
    }

    private LinkedList<Programa> ordenarProgramesPreu(LinkedList<Programa> llistaP) {

        LinkedList<Programa> llista = new LinkedList<Programa>();

        while (0 < llistaP.size()) {
            int p = posMinProg(llistaP, 0, llistaP.size() - 1);
            llista.add(llistaP.get(p));
            llistaP.remove(p);
        }

        return llista;
    }

    private boolean pertanyPlanificacio(Programa prog) {
        if (plani != null) {
            for (int k = 0; k <
                    plani.getLlistaEmissions().size(); k++) {
                if (((Emissio) plani.getLlistaEmissions().get(k)).getPrograma().getNom().equalsIgnoreCase(prog.getNom())) {
                    return true;
                }

            }
        }
        return false;
    }

    private int posMinHora(LinkedList<FranjaHoraria> listFran, int e, int d) {

        int p = e;

        for (int j = e + 1; j <=
                d; j++) {
            if (listFran.get(j).getHoraInici().before(listFran.get(p).getHoraInici())) {
                p = j;
            }

        }
        return p;
    }

    private int posMinProg(LinkedList<Programa> llistP, int e, int d) {

        int p = e;

        for (int j = e + 1; j <=
                d; j++) {
            if (llistP.get(j).getPreuBase() < llistP.get(p).getPreuBase()) {
                p = j;
            }

        }
        return p;
    }

    private int posMinFrang(LinkedList<FranjaHoraria> llistaF, int e, int d) {

        int p = e;

        for (int j = e + 1; j <=
                d; j++) {
            if (llistaF.get(j).getTaxa() < llistaF.get(p).getTaxa()) {
                p = j;
            }

        }
        return p;
    }

    private void mesclarProgrames(LinkedList<Programa> llistaProgrames, Random rnd) {

        if (llistaProgrames.size() > 1) {

            int random = (int) (rnd.nextDouble() * 123456.0);
            random %=
                    llistaProgrames.size();
            for (int i = 0; i <=
                    random; i++) {
                if ((i + random) % 2 == 0) {
                    /* Si random es un numero parell*/
                    llistaProgrames.addLast(llistaProgrames.get(random));
                    llistaProgrames.remove(random);
                }

                llistaProgrames.addFirst(llistaProgrames.get(random));
                llistaProgrames.remove(random + 1);

            }

        }
    }

    /**
     * Agafa dos calendars i comprova si son iguals amb Hora i minuts
     * @param hora1 Data del calendar 1 que volem comparar amb el calendar 2
     * @param hora2 Data del calendar 2 que volem comparar amb el calendar 1
     * @return Cert si son iguals, fals altrament.
     */
    private boolean sonHoresIguals(Calendar hora1, Calendar hora2) {
        int h1 = hora1.get(Calendar.YEAR);
        int m1 = hora1.get(Calendar.MONTH);
        int h2 = hora2.get(Calendar.MONTH);
        int m2 = hora2.get(Calendar.DAY_OF_MONTH);

        return (h1 == h2 && m1 == m2);

    }

    /**
     * Agafa dos calendars i comprova si son iguals amb Any, Mes i Dia unicament
     * @param data1 Data del calendari 1 que volem comparar amb el calendari 2
     * @param data2 Data del calendari 2 que volem comparar amb el calendari 1
     * @return Cert si son iguals, fals altrament.
     */
    private boolean sonIguals(Calendar data1, Calendar data2) {
        int any1 = data1.get(Calendar.YEAR);
        int mes1 = data1.get(Calendar.MONTH);
        int dia1 = data1.get(Calendar.DAY_OF_MONTH);
        int any2 = data2.get(Calendar.YEAR);
        int mes2 = data2.get(Calendar.MONTH);
        int dia2 = data2.get(Calendar.DAY_OF_MONTH);

        return (any1 == any2 && dia1 == dia2 && mes1 == mes2);

    }

    private String[] solapa(Programa prog, FranjaHoraria franja, Calendar dataEmissio) throws ParseException {
        /*  TRUE si es solapa amb altres emissions de la mateixa Planificacio    */
        /* Inicialitzarem les hores: calendarInici i calendarFi, que fan referencia a l'emissio en cas que no es solapi */

        String valor[] = new String[2];
        /* calendarInici representa la data d'inici de la emissio */
        Calendar calendarInici = Calendar.getInstance();

        SimpleDateFormat formatCalendar2 = new SimpleDateFormat("H:mm");
        int horaIni = 0, minutIni = 0, horaFi = 0, minutFi = 0;
        boolean solapa = false;
        boolean fi = false;
        Date hora;

        boolean directo = false;

        int dura = duracio(prog);
        System.out.println("DATA EMISSIO = " + dataEmissio.get(Calendar.DAY_OF_MONTH));
        if (plani.getLlistaEmissions().size() == 0) {

            if (((prog.getClass()).getName().charAt(prog.getClass().getName().length() - 1)) == 'D') {
                /* SI ES DIRECTE */
                directo = true;

                calendarInici =
                        ((Directe) prog).getIniciEmissio();
                horaIni =
                        calendarInici.get(Calendar.HOUR_OF_DAY);
                minutIni =
                        calendarInici.get(Calendar.MINUTE);
                /**/
                System.out.println("SOLAPA -> DIRECTE == 0 calendarInici = " + horaIni + ":" + minutIni);
                /**/
                horaFi =
                        ((minutIni + dura) / 60) + horaIni;
                minutFi =
                        (dura + minutIni) % 60;
                /**/
                System.out.println("SOLAPA -> DIRECTE == 0 calendarFi = " + horaFi + ":" + minutFi);
                /**/

                if (minutFi < 10) {
                    hora = formatCalendar2.parse("" + horaFi + ":0" + minutFi);
                } else {
                    hora = formatCalendar2.parse("" + horaFi + ":" + minutFi);
                }

                calendarFi = Calendar.getInstance();
                calendarFi.setTime(hora);

            } else {

                calendarInici = franja.getHoraInici();

                horaIni =
                        calendarInici.get(Calendar.HOUR_OF_DAY);
                minutIni =
                        calendarInici.get(Calendar.MINUTE);
                /**/
                System.out.println("SOLAPA -> NO DIRECTE == 0 calendarInici = " + horaIni + ":" + minutIni);
                /**/
                horaFi =
                        ((minutIni + dura) / 60) + horaIni;
                minutFi =
                        (dura + minutIni) % 60;
                /**/
                System.out.println("SOLAPA -> NO DIRECTE == 0 calendarFi = " + horaFi + ":" + minutFi);
                /**/

                if (minutFi < 10) {
                    hora = formatCalendar2.parse("" + horaFi + ":0" + minutFi);
                } else {
                    hora = formatCalendar2.parse("" + horaFi + ":" + minutFi);
                }

                calendarFi = Calendar.getInstance();
                calendarFi.setTime(hora);

            }

            if (calendarInici.before(franja.getHoraInici()) || calendarFi.after(franja.getHoraFi())) {
                /* NO ES FACTIBLE    */
                solapa = true;
            }

        } else {    /*  plani.getLlistaEmissions().size() > 1   */
            for (int j = 0; j <
                    plani.getLlistaEmissions().size() && !solapa && !directo; j++) {

                calendarInici = franja.getHoraInici();
                
                if (sonIguals((plani.getLlistaEmissions().get(j)).getDataReal(), dataEmissio)) {

                    do {

                        if (((prog.getClass()).getName().charAt(prog.getClass().getName().length() - 1)) == 'D') {
                            /* SI ES DIRECTE */
                            directo = true;
                            calendarInici =
                                    ((Directe) prog).getIniciEmissio();


                            horaIni =
                                    calendarInici.get(Calendar.HOUR_OF_DAY);
                            minutIni =
                                    calendarInici.get(Calendar.MINUTE);
                            /**/
                            System.out.println("SOLAPA -> DIRECTE > 0 calendarInici = " + horaIni + ":" + minutIni);
                            /**/
                            horaFi =
                                    ((minutIni + dura) / 60) + horaIni;
                            minutFi =
                                    (dura + minutIni) % 60;
                            /**/
                            System.out.println("SOLAPA -> DIRECTE > 0 calendarFi = " + horaFi + ":" + minutFi);
                            /**/

                            if (minutFi < 10) {
                                hora = formatCalendar2.parse("" + horaFi + ":0" + minutFi);
                            } else {
                                hora = formatCalendar2.parse("" + horaFi + ":" + minutFi);
                            }

                            calendarFi = Calendar.getInstance();
                            calendarFi.setTime(hora);

                        } else {

                            horaIni =
                                    calendarInici.get(Calendar.HOUR_OF_DAY);
                            minutIni =
                                    calendarInici.get(Calendar.MINUTE);

                            /**/
                            System.out.println("SOLAPA -> NO DIRECTE > 0 calendarInici = " + horaIni + ":" + minutIni);
                            /**/

                            horaFi =
                                    ((minutIni + dura) / 60) + horaIni;
                            minutFi =
                                    (dura + minutIni) % 60;

                            /**/
                            System.out.println("SOLAPA -> NO DIRECTE > 0 calendarFi = " + horaFi + ":" + minutFi);
                            /**/

                            if (minutFi < 10) {
                                hora = formatCalendar2.parse("" + horaFi + ":0" + minutFi);
                            } else {
                                hora = formatCalendar2.parse("" + horaFi + ":" + minutFi);
                            }

                            calendarFi = Calendar.getInstance();
                            calendarFi.setTime(hora);

                            calendarInici.add(Calendar.MINUTE, +1);

                        }
                        /* calendarFi es una variable global que representa la hora fi de la emissio */

                        if ((!calendarFi.after(franja.getHoraFi()) && !calendarInici.before(franja.getHoraInici())) && (!calendarFi.after(((Emissio) plani.getLlistaEmissions().get(j)).getHoraInici()) || !calendarInici.before(((Emissio) plani.getLlistaEmissions().get(j)).getHoraFi()))) {

                            fi = true;
                        }

                    } while (!fi && !directo && calendarFi.before(franja.getHoraFi()));

                }

            }
        }

        if (solapa) {
            System.out.println("SOLAPAAAAA =>>>> TRUE!!!!!!!!!!!");
            valor[0] = "" + 1;
        } else {
            System.out.println("SOLAPAAAAA =>>>> FALSE!!!!!!!!!!!");
            valor[0] = "" + 0;
        }

        valor[1] = "" + horaIni + ":" + minutIni;

        return valor;
    }
}
