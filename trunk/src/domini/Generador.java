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
    private Convertir conv;
    boolean preuSobrepassat = false;
    private Planificacio plani;
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
        conv = new Convertir();
    }

    @SuppressWarnings("empty-statement")
    public LinkedList<Planificacio> generar(LinkedList<Programa> llistaProgs, float preuMax, int[] prioritats, LinkedList<FranjaHoraria> fPreferides, LinkedList<FranjaHoraria> fProhibides, Calendar superDataIni, Calendar superDataFi, int nPlanis, LinkedList<FranjaHoraria> llistaFranT) throws ParseException {

        int total = 0;
        int comptadorIguals = 0;
        int maxIteracions = 500;
        boolean ok = true;
        String valor[] = new String[2];
        int maxEmisDia = 1;
        int numEmisDia = 0;
        boolean igual = false;
        Calendar in = (Calendar) superDataIni.clone();
        Calendar fi = (Calendar) superDataFi.clone();
        //int dia, mes, any;
        Calendar dataEmissio = Calendar.getInstance(), dataFiEmissio = Calendar.getInstance();
        Calendar min = null, max = null;
        //SimpleDateFormat formatCalendar = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatCalendar2 = new SimpleDateFormat("H:mm");
        Calendar copiaDataEmissio = Calendar.getInstance();

        for (int k = 0; k < 5; k++) {
            /* INICIALITZAR CRITERIS AMB ELS VALORS PASSATS PER PARAMETRE A PRIORITATS */
            /* Maxima prioritat =1, minima prioritat = 5 */
            criteris[k][0] = prioritats[k];
        }

        /* Inicialitzar llista de programes i franges d'acord amb els criteris esecificats */

        llistaProgrames.clear();
        llistaFranges.clear();
        llistaPlanificacionsGenerades.clear();
        repositoriFranges.clear();
        listFranAux.clear();
        listProgsAux.clear();

        for (int i = 0; i < llistaProgs.size(); i++) {
            llistaProgrames.add(llistaProgs.get(i));
        }

        boolean[] vectorProgrames = new boolean[llistaProgs.size()];

        for (int j = 0; j < llistaFranT.size(); j++) {
            repositoriFranges.add(llistaFranT.get(j));
        }

        for (int kaka = 0; kaka < fProhibides.size(); kaka++) {
            System.out.println("prohibida =" + fProhibides.get(kaka).getHoraInici().get(Calendar.HOUR_OF_DAY) + ":" + fProhibides.get(kaka).getHoraInici().get(Calendar.MINUTE));
            System.out.println("prohibida =" + fProhibides.get(kaka).getHoraFi().get(Calendar.HOUR_OF_DAY) + ":" + fProhibides.get(kaka).getHoraFi().get(Calendar.MINUTE));
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

            if (criteris[3][0] < criteris[1][0] || criteris[3][0] < criteris[2][0]) {
                /* Primer volem els programes de format directe */
                llistaProgrames = primerDirectes(llistaProgrames);
                for (int po = 0; po < llistaProgrames.size(); po++) {
                    System.out.println("prog = " + llistaProgrames.get(po).getNom());
                }
            }

        } else {

            for (int i = 0; i < fPreferides.size(); i++) {
                llistaFranges.add(fPreferides.get(i));
            }

            if (llistaFranges.size() == 0) {

                crearFrangesNoProhibidesNiPreferides(fPreferides, fProhibides);
            }

            for (int zona = 0; zona < llistaFranges.size(); zona++) {
                System.out.println("a generador");
                conv.debugCalendar(llistaFranges.get(zona).getHoraInici(), zona);
                conv.debugCalendar(llistaFranges.get(zona).getHoraFi(), zona);
            }

            if (criteris[3][0] < criteris[1][0] || criteris[3][0] < criteris[2][0]) {
                /* Primer volem els programes de format directe */
                llistaProgrames = primerDirectes(llistaProgrames);
                for (int po = 0; po < llistaProgrames.size(); po++) {
                    System.out.println("prog = " + llistaProgrames.get(po).getNom());
                }

            }
        }
        /* Fi inicialitzacio de programes i franges */


        /* Comencem a generar les planificacions */
        while (nPlanis > llistaPlanificacionsGenerades.size() && total < maxIteracions) {

            inicialitzarCriteris(prioritats);

            plani = new Planificacio(in, fi);

            /* dataEmissio actualment correspon a la data d'inici de la planificacio */
            dataEmissio = (Calendar) in.clone();
            dataEmissio.set(Calendar.HOUR_OF_DAY, 0);
            dataEmissio.set(Calendar.HOUR_OF_DAY, 0);

            dataFiEmissio = (Calendar) fi.clone();



            if (llistaPlanificacionsGenerades.size() > 0 || total >= 2) {
                /* Ordenam els programes de forma aleatoria */
                /* En la primera planificacio els programes estaran ordenats per ordre de preu si el criteri principal es el preu i sino en l'ordre en que l'usuari els ha introduit */
                /* En les planificacions posteriors, els programes s'ordenaran en ordre aleatori per generar una major varietat en les planificacions */
                Random rnd = new Random();
                mesclarProgrames(llistaProgrames, rnd);

            }

            /* Aquest vector ens servira per desactivar un programa de tipus directe quan el criteri de programes seleccionants
            es menys prioritari que les franges preferides i el programa s'emet dintre una altra franja diferent a la preferida */
            for (int mom = 0; mom < vectorProgrames.length; mom++) {
                vectorProgrames[mom] = true;
            }

            /* Numero de programes que a priori volem que constin a la nostra planificacio */
            /* Posteriorment aquest valor es pot decrementar en funcio dels criteris que l'usuari hagi elegit */
            int n = llistaProgrames.size();


            /* Definim cada planificacio */
            /* Mentres no obtinguem una planificacio amb n emissions o els criteris ja no es puguin afluixar mes 
            intentarem crear una planificacio el mes optima possible d'acord amb els criteris de l'usuari */
            while (nemis < n && numCriterisAfluixats <= 5) {

                /* Per a cada programa, mentre el preu no s'hagi sobrepassat intentarem associar-lo a una emissio */
                for (int i = 0; i < llistaProgrames.size() && !preuSobrepassat; i++) {

                    ok = false;

                    if (!pertanyPlanificacio(llistaProgrames.get(i)) && vectorProgrames[i]) {

                        if (((llistaProgrames.get(i).getClass()).getName().charAt(llistaProgrames.get(i).getClass().getName().length() - 1)) == 'D') {
                            /* Si es tracta d'un programa de format DIRECTE setejarem la dataEmissio a la del programa (i despres recuperarem la actual) */

                            copiaDataEmissio = (Calendar) dataEmissio.clone();
                            /* Copia per restaurar el valor de la dataEmissio al valor actual una volta s'hagi intentat crear una emissio d'un programa de format directe,
                            ja que te una data determinada, i nomes pot ser aquella */

                            directe = true;

                            dataEmissio = ((Directe) (llistaProgrames.get(i))).getIniciEmissio();

                        } else {
                            directe = false;
                        }

                        if (criteris[4][1] == 0) {
                            /* Criteri Dates Planificacio invalidat, per tant augmentem la dataFiEmissio */

                            dataFiEmissio = (Calendar) fi.clone();

                            dataFiEmissio.add(Calendar.DAY_OF_MONTH, +1);
                        }

                        while (!dataEmissio.after(dataFiEmissio) && !preuSobrepassat && !ok) {

                            if (llistaPlanificacionsGenerades.size() < 3 || total < 4) {

                                int numDies = superDataFi.get(Calendar.DAY_OF_MONTH) - superDataIni.get(Calendar.DAY_OF_MONTH);
                                numDies++;
                                maxEmisDia = llistaProgrames.size() / numDies;
                                maxEmisDia++;
                            } else {

                                /*  maxEmisDia sempre entre 1 i 4 emissions per dia */
                                Random rnd = new Random();
                                maxEmisDia = (int) (rnd.nextDouble() * 123456.0);
                                maxEmisDia %= 4;
                                maxEmisDia++;
                            }


                            if (criteris[1][1] == 0) {
                                /* Franges Preferides deshabilitades */
                                crearFrangesNoProhibidesNiPreferides(null, fProhibides);
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


                            if (total % 3 == 1 && llistaFranges.size() > 0) {
                                /* Quan les franges sempre siguin les mateixes i es generin les mateixes planificacions reordenarem les franges de nou*/
                                Random rnd = new Random();
                                mesclarFranges(llistaFranges, rnd);
                            }

                            /* Mentre el programa no s'hagi associat a una emissio (ok=true) o s'hagi sobrepassat el preu, mirarem si un programa es pot associar a alguna franja */
                            for (int j = 0; j < llistaFranges.size() && !ok; j++) {

                                if (directe) {

                                    Calendar horaInici = ((Directe) llistaProgrames.get(i)).getIniciEmissio();
                                    int dura = ((Directe) llistaProgrames.get(i)).getDuracio();
                                    int horaIni = horaInici.get(Calendar.HOUR_OF_DAY);
                                    int minutIni = horaInici.get(Calendar.MINUTE);
                                    int horaFinal = ((minutIni + dura) / 60) + horaIni;
                                    int minutFinal = (dura + minutIni) % 60;

                                    System.out.println("horaInici =" + horaIni + ":" + minutIni);
                                    System.out.println("horaFinal =" + horaFinal + ":" + minutFinal);
                                    Date hora;
                                    if (minutFinal < 10) {
                                        hora = formatCalendar2.parse("" + horaFinal + ":0" + minutFinal);
                                    } else {
                                        hora = formatCalendar2.parse("" + horaFinal + ":" + minutFinal);
                                    }

                                    Calendar horaFi = (Calendar) horaInici.clone();
                                    horaFi.setTime(hora);

                                    boolean burroCargado = false;
                                    for (int burro = 0; burro < llistaFranges.size() && !burroCargado; burro++) {
                                        /* Intentam trobar la franja que pertany a la emisio en directe */
                                        System.out.println("Inici franja =" + llistaFranges.get(burro).getHoraInici().get(Calendar.HOUR_OF_DAY) + ":" + llistaFranges.get(burro).getHoraInici().get(Calendar.MINUTE));
                                        System.out.println("Fi franja =" + llistaFranges.get(burro).getHoraFi().get(Calendar.HOUR_OF_DAY) + ":" + llistaFranges.get(burro).getHoraFi().get(Calendar.MINUTE));
                                        if (conv.horaMajor(horaInici, llistaFranges.get(burro).getHoraInici()) && conv.horaMajor(llistaFranges.get(burro).getHoraFi(), horaFi)) {
                                            /* Hem trobat la franja que pertany a la hora d'emissio*/
                                            burroCargado = true;
                                            valor = factible(llistaProgrames.get(i), llistaFranges.get(burro), dataEmissio);
                                        }
                                    }
                                    if (!burroCargado && ((criteris[3][0] < criteris[1][0] && fPreferides.size() > 0) || (criteris[3][0] < criteris[2][0] && fProhibides.size() >= 0))) {
                                        /* Si no hi ha cap franja que coincideix amb l'hora d'emissio del programa en directe
                                        i els programes seleccionats tenen major preferencia que les franges horaries preferides o prohibides */
                                        System.out.println("horaInici =" + horaInici.get(Calendar.HOUR_OF_DAY) + ":" + horaInici.get(Calendar.MINUTE));
                                        System.out.println("horaFinal =" + horaFi.get(Calendar.HOUR_OF_DAY) + ":" + horaFi.get(Calendar.MINUTE));
                                        FranjaHoraria franja = new FranjaHoraria(horaInici, horaFi, (float) 0.01);
                                        valor = factible(llistaProgrames.get(i), franja, dataEmissio);
                                    } else if (!burroCargado) {
                                        /* La franja es mes important que els programes seleccionats, per tant deshabilitem el programa */
                                        vectorProgrames[i] = false;
                                        n--;
                                        valor[0] = "" + 0;
                                    }
                                } else {
                                    /* Miram si un programa determinat es pot associar a una franja determinada en la dataEmissio especificada */
                                    valor = factible(llistaProgrames.get(i), llistaFranges.get(j), dataEmissio);
                                }

                                if (valor[0].equalsIgnoreCase("1")) {

                                    /* calendarInici es la hora d'inici de la emissio, ens la retorna la funcio factible dins valor[0] */
                                    Date dateIni = formatCalendar2.parse(valor[1]);
                                    Calendar calendarInici = Calendar.getInstance();
                                    calendarInici.setTime(dateIni);

                                    /* calendarInici es la hora d'inici de la emissio, ens la retorna la funcio factible dins valor[0] */
                                    Date dateFi = formatCalendar2.parse(valor[2]);
                                    Calendar calendarFi = Calendar.getInstance();
                                    calendarFi.setTime(dateFi);

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

                                            afluixarCriteris();
                                            if (criteris[0][1] == 0) {
                                                plani.addEmissioPlanificacio(emi);
                                                nemis++;
                                                numEmisDia++;
                                                preuTotal += emi.getPreuEmissio();
                                            } else {
                                                preuSobrepassat = true;
                                            }
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
                                    ok = true;
                                } else if (!directe && conv.comparacioData(dataEmissio, dataFiEmissio) == -11) {
                                    dataEmissio.add(Calendar.DAY_OF_MONTH, +1);
                                }
                            }
                            if (maxEmisDia <= numEmisDia) {
                                numEmisDia = 0;
                                dataEmissio.add(Calendar.DAY_OF_MONTH, +1);
                            }
                        }

                        if (dataEmissio.after(dataFiEmissio) && criteris[4][1] == 0 && !directe && !ok) {
                            dataFiEmissio.add(Calendar.DAY_OF_MONTH, +1);
                        }
                    }

                    if (directe) {
                        /* Si s'havia canviat la data d'emissio perque es tractava d'un programa de format directe, ara la reestablim */

                        dataEmissio = (Calendar) copiaDataEmissio.clone();
                    }

                }

                if (nemis < n) {
                    /* Si hem recorregut tots els programes i no hem trobat les emissions que tindria que tenir
                    afluixam els criteris per veure si podem trobar alguna emissio que s'aproximi el maxim
                    possible a les nostres preferencies */
                    afluixarCriteris();
                    preuSobrepassat = false;
                }
            }


            /* COMMPROVAM QUE NO EXISTEIX UNA PLANIFICACIO IGUAL */
            total++;

            preuTotal =
                    0;
            nemis =
                    0;
            preuSobrepassat = false;
            numCriterisAfluixats = 0;
            if (llistaPlanificacionsGenerades.size() == 0 && plani.getLlistaEmissions().size() > 0) {
                plani.setDataInici(min);
                plani.setDataFi(max);
                llistaPlanificacionsGenerades.add(plani);

            } else if (plani.getLlistaEmissions().size() > 0) {

                igual = false;

                for (int p = 0; p < llistaPlanificacionsGenerades.size() && !igual; p++) {
                    for (int k = 0; k < plani.getLlistaEmissions().size() && !igual; k++) {
                        if (llistaPlanificacionsGenerades.get(p).getLlistaEmissions().size() == plani.getLlistaEmissions().size()) {
                            for (int j = 0; j < llistaPlanificacionsGenerades.get(p).getLlistaEmissions().size() && !igual; j++) {
                                if (((Emissio) llistaPlanificacionsGenerades.get(p).getLlistaEmissions().get(j)).getPrograma().getNom().equalsIgnoreCase(((Emissio) plani.getLlistaEmissions().get(j)).getPrograma().getNom()) && sonIguals(((Emissio) llistaPlanificacionsGenerades.get(p).getLlistaEmissions().get(j)).getDataEmissio(), ((Emissio) plani.getLlistaEmissions().get(j)).getDataEmissio()) && sonHoresIguals(((Emissio) llistaPlanificacionsGenerades.get(p).getLlistaEmissions().get(j)).getHoraInici(), ((Emissio) plani.getLlistaEmissions().get(j)).getHoraInici()) && sonHoresIguals(((Emissio) llistaPlanificacionsGenerades.get(p).getLlistaEmissions().get(j)).getHoraFi(), ((Emissio) plani.getLlistaEmissions().get(j)).getHoraFi())) {
                                    if (comptadorIguals == 3) {
                                        comptadorIguals = -1;
                                        in.add(Calendar.DAY_OF_MONTH, +1);
                                        if (in.after(dataFiEmissio)) {
                                            fi.add(Calendar.DAY_OF_MONTH, +3);
                                        }
                                    }
                                    comptadorIguals++;
                                    igual =
                                            true;
                                }
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

            if (total % 10 == 0) {
                in = (Calendar) superDataIni.clone();
                fi =
                        (Calendar) superDataFi.clone();
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
        /* Cerca dins el repositori de franges (llistaFran) la franja que conte horaInici. Aixi sabem a qunia franja pertany una emissio. */

        FranjaHoraria fra = llistaFran.get(0);

        for (int i = 0; i <
                llistaFran.size(); i++) {
            if (!horaInici.before(llistaFran.get(i).getHoraInici()) && horaInici.before(llistaFran.get(i).getHoraFi())) {
                return llistaFran.get(i);
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

        SimpleDateFormat formatCalendar2 = new SimpleDateFormat("HH:mm");
        Date dateIni = formatCalendar2.parse("00:00");
        Calendar ini = Calendar.getInstance();
        ini.setTime(dateIni);

        Date dateFin = formatCalendar2.parse("23:59");
        Calendar fin = Calendar.getInstance();
        fin.setTime(dateFin);

        Calendar fi = (Calendar) fin.clone();
        Calendar zero = (Calendar) ini.clone();
        zero.setTime(dateIni);


        if (listFranAux.size() == 0) {
            /* Si no te cap franja */

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
             *  D'AQUESTA MANERA, NOSALTRES PRENEM L'INTERVAL 20:30 - 23:59 com a PRIME TIME
             * horaInici =20:0
            horaFinal =21:30
             */

            dateIni = formatCalendar2.parse("20:30");
            ini = Calendar.getInstance();
            ini.setTime(dateIni);

            Date dateFi = formatCalendar2.parse("23:59");
            fi = (Calendar) ini.clone();

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
                    formatCalendar2.parse("20:29");
            fi =
                    (Calendar) ini.clone();

            fi.setTime(dateFi);
            franja =
                    new FranjaHoraria(ini, fi, (float) 0.10);
            llistaFranges.add(franja);

        } else { /* ARA: si tenim alguna franja */
            //else if (!(listFranAux.size() == 1 && sonHoresIguals(listFranAux.get(0).getHoraInici(), listFranAux.get(0).getHoraFi()))) {
        /* Si te mes d'una franja i ses hores d'inici i de fi son diferents */
            boolean afegir = false, afegida = false, end = false;

            for (int i = 0; i <
                    listFranAux.size() && !end; i++) {

                if (sonHoresIguals(listFranAux.get(0).getHoraInici(), zero) && sonHoresIguals(listFranAux.get(0).getHoraFi(), fin)) {
                    /* Si la unio de les dues llistes de franges retornen una unica franja que abarca tot el dia retornarem la mateixa franja */
                    llistaFranges.add(listFranAux.get(0));
                    end = true;

                } else if (sonHoresIguals(listFranAux.get(i).getHoraInici(), zero)) {
                    /* Si la primera franja comenca a les 00:00, setejarem la hora d'inici a la fi de la primera franja */
                    conv.debugCalendar(listFranAux.get(i).getHoraFi(), i);
                    ini = listFranAux.get(i).getHoraFi();
                    ini.add(Calendar.MINUTE, +1);
                    conv.debugCalendar(ini, i);
                //if (i == (listFranAux.size() - 1)) {
                        /* Si es sa darrera franja afegirem una que abarcara desde el fi d'aquesta franja fins 23:59 */
                //  afegir = true;
                //}

                } else if (sonHoresIguals(listFranAux.get(i).getHoraFi(), fin)) {
                    /* Si la ultima franja acaba a les 23:59, la seva hora de fi sera la hora d'inici d'aquesta franja */
                    //  if (sonHoresIguals(listFranAux.get(0).getHoraInici(), zero) || listFranAux.size() == 1) {
                    fi = listFranAux.get(i).getHoraInici();
                    fi.add(Calendar.MINUTE, -1);
                //} else {
                // end = true;
                //}
                } else {
                    /* Altrament, si la franja i no comenca ni a les 00:00 ni acaba a les 23:59 */

                    if (sonHoresIguals(listFranAux.get(0).getHoraInici(), zero)) {
                        if (i == (listFranAux.size() - 1)) {
                            /* Si es sa darrera franja afegirem una que abarcara desde el fi d'aquesta franja fins 23:59 */
                            fi = (Calendar) fin.clone();
                        }
                    } else {
                        if (i == (listFranAux.size() - 1)) {
                            /* Si es sa darrera franja afegirem una que abarcara desde el fi d'aquesta franja fins 23:59 */
                            afegir = true;
                        } else {
                            /* aixo es inutil XD*/
                            fi = (Calendar) fin.clone();
                        }
                        fi = listFranAux.get(i).getHoraInici();
                        fi.add(Calendar.MINUTE, -1);
                        conv.debugCalendar(fi, i);
                    }
                }

                if (i + 1 < listFranAux.size()) {
                    /* Si hi ha una altra franja */
                    if (sonHoresIguals(listFranAux.get(0).getHoraInici(), zero)) {
                        /* horaInici primera franja == 00:00 */
                        fi = listFranAux.get(i + 1).getHoraInici();
                        fi.add(Calendar.MINUTE, -1);
                        conv.debugCalendar(fi, i);
                    }
                }


                FranjaHoraria franja;
                if (!end) {
                    franja = new FranjaHoraria(ini, fi, (float) 0.00);
                    llistaFranges.add(franja);
                }
                if (afegir) {
                    ini = listFranAux.get(i).getHoraFi();
                    ini.add(Calendar.MINUTE, +1);
                    franja = new FranjaHoraria(ini, fin, (float) 0.00);
                    llistaFranges.add(franja);
                    afegir = false;
                }
                if (i + 1 < listFranAux.size()) {

                    if (sonHoresIguals(listFranAux.get(i + 1).getHoraFi(), fin) && sonHoresIguals(listFranAux.get(0).getHoraInici(), zero)) {
                        end = true;
                    } else if (sonHoresIguals(listFranAux.get(0).getHoraInici(), zero)) {
                        /* horaInici primera franja == 00:00 */
                        ini = listFranAux.get(i + 1).getHoraFi();
                        ini.add(Calendar.MINUTE, +1);
                    } else {
                        ini = listFranAux.get(i).getHoraFi();
                        ini.add(Calendar.MINUTE, +1);
                    }

                }
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
            /* ES CONTINU */
            /* Simplemenet per afergir un poc de pseudo alietorietat afegirem un random per determinat la
            duracio dels programes de format continu, perquè l'usuari sense intervenir pugui obtenir 
            planificacions diferents. Per tant ho farem un poc mes "aleatori"  */

            Random rnd = new Random();
            int x = (int) (rnd.nextDouble() * 123456.0);
            x %=
                    13;

            switch (x) {
                case 1:
                    duracio = 10;
                    break;
                case 2:
                    duracio = 20;
                    break;
                case 3:
                    duracio = 30;
                    break;
                case 4:
                    duracio = 40;
                    break;
                case 5:
                    duracio = 50;
                    break;
                case 6:
                    duracio = 60;
                    break;
                case 7:
                    duracio = 70;
                    break;
                case 8:
                    duracio = 80;
                    break;
                case 9:
                    duracio = 90;
                    break;
                case 10:
                    duracio = 100;
                    break;
                case 11:
                    duracio = 110;
                    break;
                case 12:
                    duracio = 120;
                    break;
                default:
                    duracio = 130;
                    break;
                }
        /* Suposem que una emissio de menys de 10 minuts ni de mes de 130 minuts no s'ha de generar de forma AUTOMATICA */

        }

        return duracio;
    }

    private String[] factible(Programa prog, FranjaHoraria franja, Calendar dataEmissio) throws ParseException {
        /* Es pot emetre el Programa prog dins la FranjaHoraria franja dia dataEmissio sense que es solapi amb una altra Emissio?
         * si es aixi ens encarregarem d'inicialitzar les hores de l'emissio: calendarInici, calendarFi mitjançant la funcio "solapa"   */

        /* Utilitzarem aquest vector per guardar els valors que ens retorna la funcio solapa, 
        valor[0] -> 1 si es solapa, i per tant no es factible, valor[1] que conte la hora d'inici de la emissio a generar, 
        i finalment, valor[2] conte la hora de fi la nostra nov emissio*/

        String valor[] = new String[3];

        boolean factible = false;
        System.out.println("Nom prog =" + prog.getNom());
        System.out.println("Caducitat =" + prog.getDataCaducitat().get(Calendar.DAY_OF_MONTH) + "-" + prog.getDataCaducitat().get(Calendar.MONTH) + "-" + prog.getDataCaducitat().get(Calendar.YEAR));
        System.out.println("dataEmissio =" + dataEmissio.get(Calendar.DAY_OF_MONTH) + "-" + dataEmissio.get(Calendar.MONTH) + "-" + dataEmissio.get(Calendar.YEAR));
        if (conv.comparacioData(prog.getDataCaducitat(), dataEmissio) == 1 || conv.sonIgualsData(prog.getDataCaducitat(), dataEmissio)) {
            /* Entenem que un programa es pot emetre el mateix dia que caduca */
            valor = solapa(prog, franja, dataEmissio);

            if (!valor[0].equalsIgnoreCase("1")) {
                /* SI NO ES SOLAPA I PER TANT ES FACTIBLE */

                factible = true;
            }

        }
        if (factible) {
            valor[0] = "" + 1;
        } else {
            valor[0] = "" + 0;
        }

        /* TAMBE PASSEM LA DATA INICI DINS VALOR[1] I LA DATA FI DINS VALOR[2]*/
        return valor;
    }

    private LinkedList<FranjaHoraria> fusionarFranges(LinkedList<FranjaHoraria> prefes, LinkedList<FranjaHoraria> prohibs) throws ParseException {

        LinkedList<FranjaHoraria> llista = new LinkedList<FranjaHoraria>();
        FranjaHoraria un = null, dos = null;
        FranjaHoraria franja = null;

        Calendar inici = Calendar.getInstance(), fi = Calendar.getInstance();
        boolean p = false, f = false;

        if (prefes != null) {
            for (int j = 0; j <
                    prefes.size(); j++) {
                listFranAux.add(prefes.get(j));
                f =
                        true;
            }

        }

        if (prohibs != null) {
            for (int j = 0; j <
                    prohibs.size(); j++) {
                listFranAux.add(prohibs.get(j));
                p =
                        true;
            }

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

    private void mesclarFranges(LinkedList<FranjaHoraria> llistaFranges, Random rnd) {

        if (llistaFranges.size() > 1) {

            int random = (int) (rnd.nextDouble() * 123456.0);
            random %=
                    llistaFranges.size();
            for (int i = 0; i <=
                    random; i++) {
                if ((i + random) % 2 == 0) {
                    /* Si random es un numero parell*/
                    llistaFranges.addLast(llistaFranges.get(random));
                    llistaFranges.remove(random);
                }

                llistaFranges.addFirst(llistaFranges.get(random));
                llistaFranges.remove(random + 1);

            }

        }
    }

    private long numDies(Calendar cerdito, Calendar patito) {
        /* Calcula el nombre de dies que hi ha entre les dues dates*/
        /* {Pre: cerdito <= patito } */

        long diesCerdito = cerdito.getTimeInMillis();
        long diesPatito = cerdito.getTimeInMillis();

        long perrito = diesPatito - diesCerdito;

        perrito /=
                1000;/* Passem de milisegons a segons */
        perrito /=
                60; /* Passem de segons a minuts */
        perrito /=
                60; /* Passem de minuts a hores */
        perrito /=
                14; /* Passem de hores a dies */


        return perrito;
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

    private LinkedList<Emissio> ordenarEmissionsHora(LinkedList<Emissio> llistaE) {

        LinkedList<Emissio> llista = new LinkedList<Emissio>();

        while (0 < llistaE.size()) {
            int e = posMinEmi(llistaE, 0, llistaE.size() - 1);
            llista.add(llistaE.get(e));
            llistaE.remove(e);
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

    private LinkedList<Programa> primerDirectes(LinkedList<Programa> llistaP) {
        /* Filtram els programes de manera que primer quedin els directes i despres els no directes */
        LinkedList<Programa> llista = new LinkedList<Programa>();

        for (int i = 0; i <
                llistaP.size(); i++) {

            if (((llistaP.get(i).getClass()).getName().charAt(llistaP.get(i).getClass().getName().length() - 1)) == 'D') {

                llista.add(llistaP.get(i));
            }

        }
        boolean hies = false;
        for (int i = 0; i <
                llistaP.size(); i++) {
            hies = false;
            for (int j = 0; j <
                    llista.size() && !hies; j++) {
                if (llista.get(j).getNom().compareToIgnoreCase(llistaP.get(i).getNom()) == 0) {
                    hies = true;
                }

            }
            if (!hies) {
                llista.add(llistaP.get(i));
            }

        }
        return llista;
    }

    private int posMinEmi(LinkedList<Emissio> list, int e, int d) {

        int p = e;

        for (int j = e + 1; j <=
                d; j++) {
            if (list.get(j).getHoraInici().before(list.get(p).getHoraInici())) {
                p = j;
            }

        }
        return p;
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
        int h1 = hora1.get(Calendar.HOUR_OF_DAY);
        int m1 = hora1.get(Calendar.MINUTE);
        int h2 = hora2.get(Calendar.HOUR_OF_DAY);
        int m2 = hora2.get(Calendar.MINUTE);

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

        String valor[] = new String[3];

        /* calendarInici representa la hora d'inici de la emissio */
        Calendar calendarInici = Calendar.getInstance();
        Calendar calendarFi = (Calendar) calendarInici.clone();

        SimpleDateFormat formatCalendar2 = new SimpleDateFormat("HH:mm");
        int horaIni = 0, minutIni = 0, horaFi = 0, minutFi = 0;
        boolean solapa = false;
        boolean ok = false;
        Date hora;

        boolean directo = false;

        int dura = duracio(prog);

        /* Llista auxiliar on guardarem les possibles emissions que es poden solapar amb la nova emissio */
        LinkedList<Emissio> llista = new LinkedList<Emissio>();
        LinkedList<Emissio> list = new LinkedList<Emissio>();

        for (int i = 0; i <
                plani.getLlistaEmissions().size(); i++) {

            /* Perque una emissio es solapi amb una altra aquest dues han de emitir-se el mateix dia */
            if (sonIguals((plani.getLlistaEmissions().get(i)).getDataReal(), dataEmissio)) {

                list.add((Emissio) plani.getLlistaServeis().get(i));
            }

        }


        Date dateIni = formatCalendar2.parse("00:00");
        Calendar zero = Calendar.getInstance();
        zero.setTime(dateIni);

        llista = ordenarEmissionsHora(list);
        int n = llista.size();

        calendarInici =
                franja.getHoraInici();

        do {
            solapa = false;

            if (((prog.getClass()).getName().charAt(prog.getClass().getName().length() - 1)) == 'D') {
                /* SI ES DIRECTE */
                directo = true;
                calendarInici =
                        ((Directe) prog).getIniciEmissio();


                horaIni =
                        calendarInici.get(Calendar.HOUR_OF_DAY);
                minutIni =
                        calendarInici.get(Calendar.MINUTE);

                horaFi =
                        ((minutIni + dura) / 60) + horaIni;
                minutFi =
                        (dura + minutIni) % 60;

                if (minutFi < 10) {
                    hora = formatCalendar2.parse("" + horaFi + ":0" + minutFi);
                } else {
                    hora = formatCalendar2.parse("" + horaFi + ":" + minutFi);
                }

                calendarFi = Calendar.getInstance();
                calendarFi.setTime(hora);

            } else {
                /* EL PROGRAMA NO ES DE FORMAT DIRECTE */

                /* Partim d'una hora d'inici, que en aquest cas el l'inici de la franja on volem assignar la nova emissio,
                a partir d'aqui anirem incrementant aquesta hora per tal d'aconseguir que no es solapi amb altres emissions */


                /* Descomposem les hores i els minuts de calendarInici per definir 
                un hora de fi per la nostra emissio */
                horaIni =
                        calendarInici.get(Calendar.HOUR_OF_DAY);
                minutIni =
                        calendarInici.get(Calendar.MINUTE);


                horaFi =
                        ((minutIni + dura) / 60) + horaIni;
                minutFi =
                        (dura + minutIni) % 60;


                if (minutFi < 10) {
                    hora = formatCalendar2.parse("" + horaFi + ":0" + minutFi);
                } else {
                    hora = formatCalendar2.parse("" + horaFi + ":" + minutFi);
                }

                /* Setejam calendarFi a la hora de fi per poder fer comparacions amb mes facilitat */
                calendarFi = Calendar.getInstance();
                calendarFi.setTime(hora);
            }

            /* calendarFi es una variable global que representa la hora fi de la emissio */ /* Miram que la nova emissio no es solapi amb alguna de les emisisons creades anteriorment */

            if (n != 0) {

                /* Si l'interval d'hores de la nova emissio esta entre la franja associada */
                if (!conv.horaMajor(calendarFi, franja.getHoraFi()) && !conv.horaMajor(franja.getHoraInici(), calendarInici)) {
                    //if (!calendarFi.after(franja.getHoraFi()) && !calendarInici.before(franja.getHoraInici())) {


                    if (sonHoresIguals(calendarFi, zero) && !conv.horaMajor(llista.get(llista.size() - 1).getHoraFi(), calendarInici) && !(sonHoresIguals(llista.get(llista.size() - 1).getHoraFi(), zero))) {
                        ok = true;
                    } else if (!sonHoresIguals(calendarFi, zero)) {
                        for (int j = 0; j <
                                n && !ok; j++) {

                            if (!sonHoresIguals(calendarInici, llista.get(j).getHoraInici()) || !sonHoresIguals(calendarFi, llista.get(j).getHoraFi())) {
                                if (j == (n - 1) && j == 0) {/* Nomes hi ha una emissio, miram si el nostre interval pot anar abans o despres */
                                    if (!conv.horaMajor(calendarFi, llista.get(j).getHoraInici()) || (!conv.horaMajor(llista.get(j).getHoraFi(), calendarInici) && !sonHoresIguals(llista.get(j).getHoraFi(), zero))) {
                                        //if (!calendarFi.after(llista.get(j).getHoraInici()) || !calendarInici.before(llista.get(j).getHoraFi())) {
                                        ok = true;
                                    } else {
                                        solapa = true;
                                    }

                                } else if (j == (n - 1) && j != 0) { /* Entre dues emissios o be hem arribat a sa darrera emissio */
                                    if ((!conv.horaMajor(llista.get(j).getHoraFi(), calendarInici) && !sonHoresIguals(llista.get(j).getHoraFi(), zero)) || (!conv.horaMajor(calendarFi, llista.get(j).getHoraInici()) && !conv.horaMajor(llista.get(j - 1).getHoraFi(), calendarInici))) {
                                        //if ((!calendarInici.before(llista.get(j).getHoraFi())) || (!calendarFi.after(llista.get(j).getHoraInici()) && !calendarInici.before(llista.get(j - 1).getHoraFi()))) {

                                        ok = true;/* Hem trobat un interval de hores optim per a assignar la nostra nova emissio */
                                    } else {
                                        solapa = true;
                                    }

                                } else if (j != (n - 1) && j == 0) { /* Es la primera emissio (hi ha mes de una emissio) */ /* OJOOOOOOO AMB EL 00:00*/
                                    if ((!conv.horaMajor(calendarFi, llista.get(j).getHoraInici()) && conv.horaMajor(calendarFi, calendarInici)) || (!conv.horaMajor(llista.get(j).getHoraFi(), calendarInici) && !conv.horaMajor(calendarFi, llista.get(j + 1).getHoraInici()) && !sonHoresIguals(llista.get(j).getHoraFi(), zero))) {
                                        //if (!calendarFi.after(llista.get(j).getHoraInici()) || (!calendarInici.before(llista.get(j).getHoraFi()) && !calendarFi.after(llista.get(j + 1).getHoraInici()))) {
                                        ok = true;
                                    } else {
                                        solapa = true;
                                    }

                                } else { /* Entre dues emissions */
                                    if ((!conv.horaMajor(llista.get(j).getHoraFi(), calendarInici) && !conv.horaMajor(calendarFi, llista.get(j + 1).getHoraInici()) && !sonHoresIguals(llista.get(j).getHoraFi(), zero)) || (!conv.horaMajor(calendarFi, llista.get(j).getHoraInici()) && !conv.horaMajor(llista.get(j - 1).getHoraFi(), calendarInici))) {
                                        //   if ((!calendarInici.before(llista.get(j).getHoraFi()) && !calendarFi.after(llista.get(j + 1).getHoraInici())) || (!calendarFi.after(llista.get(j).getHoraInici()) && !calendarInici.before(llista.get(j - 1).getHoraFi()))) {
                                        ok = true;
                                    } else {
                                        solapa = true;
                                    }

                                }
                            }
                        }
                    } else {
                        solapa = true;
                    }

                }

                if (!ok) {

                    /* Si no hem trobat una hora optima per assignar la nostra nova emisiso
                    anirem augmentant els minuts de la hora de fi (i en consecuencia tambe 
                    la hora de fi) per seguir intentar trobar una hora optima */
                    calendarInici.add(Calendar.MINUTE, +1);
                }

            } else {
                ok = true;
            }

        } while (!ok && !directo && !calendarFi.after(franja.getHoraFi()));

// SOLAPA || !OK
        if (solapa && !ok) {
            /* SOLAPA = TRUE */
            valor[0] = "" + 1;
        } else {
            /* ok && !solapa */
            /* SOLAPA = FALSE */
            valor[0] = "" + 0;
        }

        /**/
        System.out.println("HORA INICI:" + horaIni + ":" + minutIni);

        System.out.println("HORA FI:" + horaFi + ":" + minutFi);
        /**/

        valor[1] = "" + horaIni + ":" + minutIni;
        valor[2] = "" + horaFi + ":" + minutFi;

        return valor;
    }
}
