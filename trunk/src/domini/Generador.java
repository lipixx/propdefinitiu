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
 * Un Generador s'identifica per una llista de Programes progamesOrdenats, i un programa millor.
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

    public LinkedList<Planificacio> generar(LinkedList<Programa> llistaProgs, float preuMax, int[] prioritats, LinkedList<FranjaHoraria> fPreferides, LinkedList<FranjaHoraria> fProhibides, Calendar dataIni, Calendar dataFi, int nPlanis, LinkedList<FranjaHoraria> llistaFranT, int separat) throws ParseException {
    
        
        int total = 0;
        int countadorIguals = 0;
        int maxIteracions = 500;
        int separ = separat;
        boolean ok = true;
        String valor[] = new String[2];
        int maxEmisDia = 1;
        int numEmisDia = 0;
        boolean planNova = true;
        boolean igual = false;
        Calendar in = dataIni;
        Calendar fi = dataFi;

        for (int k = 0; k < 5; k++) {
            /* INICIALITZAR CRITERIS AMB ELS VALORS PASSAT PER PARAMETRE A PRIORITATS */
            criteris[k][0] = prioritats[k];
        }

        /* Inicialitzar llista de programes i franges d'acord amb els criteris especificats */
        System.out.println("Size llistaProgs =" + llistaProgs.size());
        for (int i = 0; i < llistaProgs.size(); i++) {
            llistaProgrames.add(llistaProgs.get(i));
        }
        System.out.println("Size llistaProgrames =" + llistaProgrames.size());

        for (int j = 0; j < llistaFranT.size(); j++) {
            repositoriFranges.add(llistaFranT.get(j));
        }

        if (criteris[0][0] == 1) {
            System.out.println("CRITERI == PREU");
            /* El preu es el criteris mes prioritari*/
            System.out.println("Size fPreferides =" + fPreferides.size());
            for (int i = 0; i < fPreferides.size(); i++) {
                listFranAux.add(fPreferides.get(i));
            }
            if (listFranAux.size() == 0) {
                System.out.println("Size listFranAux == 0 -> crear FRANGES NO PROHIB NI PREF");
                crearFrangesNoProhibidesNiPreferides(fPreferides, fProhibides);
                System.out.println("Size llistaFranges No Prohib Ni Pref =" + llistaFranges.size());
                for (int k = 0; k < llistaFranges.size(); k++) {
                    listFranAux.add(llistaFranges.get(k));
                    System.out.println("FRANGA: inici=" + llistaFranges.get(k).getHoraInici().get(Calendar.HOUR_OF_DAY) + ":" + llistaFranges.get(k).getHoraFi().get(Calendar.MINUTE) + " fi=" + llistaFranges.get(k).getHoraFi().get(Calendar.HOUR_OF_DAY) + ":" + llistaFranges.get(k).getHoraFi().get(Calendar.MINUTE));
                }
            }
            System.out.println("Size listFranAux =" + listFranAux.size());
            llistaFranges = ordenarFrangesPreu(listFranAux);
            System.out.println("Size llistaFranges =" + llistaFranges.size());

            for (int k = 0; k < llistaFranges.size(); k++) {
                System.out.println("FRANGA: inici=" + llistaFranges.get(k).getHoraInici().get(Calendar.HOUR_OF_DAY) + ":" + llistaFranges.get(k).getHoraFi().get(Calendar.MINUTE) + " fi=" + llistaFranges.get(k).getHoraFi().get(Calendar.HOUR_OF_DAY) + ":" + llistaFranges.get(k).getHoraFi().get(Calendar.MINUTE));
            }

        } else if (criteris[1][0] == 1 || criteris[2][0] == 1) {
            System.out.println("CRITERI == FRANGES");
            /* Les franges preferides i prohibides son els criteris mes prioritaris*/
            System.out.println("Size fPreferides =" + fPreferides.size());
            for (int i = 0; i < fPreferides.size(); i++) {
                listFranAux.add(fPreferides.get(i));
            }
            System.out.println("Size listFranAux =" + listFranAux.size());
            if (listFranAux.size() == 0) {
                System.out.println("Size listFranAux == 0 -> crear FRANGES NO PROHIB NI PREF");
                crearFrangesNoProhibidesNiPreferides(fPreferides, fProhibides);
                System.out.println("Size llistaFranges No Prohib Ni Pref =" + llistaFranges.size());
                for (int k = 0; k < llistaFranges.size(); k++) {
                    System.out.println("FRANGA: inici=" + llistaFranges.get(k).getHoraInici().get(Calendar.HOUR_OF_DAY) + ":" + llistaFranges.get(k).getHoraFi().get(Calendar.MINUTE) + " fi=" + llistaFranges.get(k).getHoraFi().get(Calendar.HOUR_OF_DAY) + ":" + llistaFranges.get(k).getHoraFi().get(Calendar.MINUTE));
                }
            }
            System.out.println("Size llistaProgrames =" + llistaProgrames.size());
            listProgsAux = seleccionarDirectes(llistaProgrames);
            System.out.println("Size listProgsAux =" + listProgsAux.size());
            if (listProgsAux.size() == 0) {
                System.out.println("llistaProgrames == 0");
                for (int i = 0; i < llistaProgs.size(); i++) {
                    llistaProgrames.add(llistaProgs.get(i));
                }
                System.out.println("llistaProgrames =" + llistaProgrames.size());
            }

        } else {
            System.out.println("CRITERI != PREU && != FRANGES");
            System.out.println("Size fPreferides =" + fPreferides.size());
            for (int i = 0; i < fPreferides.size(); i++) {
                listFranAux.add(fPreferides.get(i));
            }
            System.out.println("Size listFranAux =" + listFranAux.size());
            if (listFranAux.size() == 0) {
                System.out.println("Size listFranAux == 0 -> crear FRANGES NO PROHIB NI PREF");
                crearFrangesNoProhibidesNiPreferides(fPreferides, fProhibides);
                System.out.println("Size llistaFranges No Prohib Ni Pref =" + llistaFranges.size());
                for (int k = 0; k < llistaFranges.size(); k++) {
                    System.out.println("FRANGA: inici=" + llistaFranges.get(k).getHoraInici().get(Calendar.HOUR_OF_DAY) + ":" + llistaFranges.get(k).getHoraFi().get(Calendar.MINUTE) + " fi=" + llistaFranges.get(k).getHoraFi().get(Calendar.HOUR_OF_DAY) + ":" + llistaFranges.get(k).getHoraFi().get(Calendar.MINUTE));
                }
            }
        }
        /* Fi inicialitzacio de programes i franges */

        if (separ == 3) {
            Random rnd = new Random();
            maxEmisDia = (int) (rnd.nextDouble() * 123456.0);
            maxEmisDia %= 5;
            maxEmisDia++;
        }

        System.out.println("ABANS WHILE 1");

        while (nPlanis > llistaPlanificacionsGenerades.size() && total < maxIteracions) {
            System.out.println("Nombre planificacions generades = " + llistaPlanificacionsGenerades.size());
            inicialitzarCriteris(prioritats);

            /* Ordenam els programes de forma aleatoria */
            Random rnd = new Random();
            mesclarProgrames(llistaProgrames, rnd);
            System.out.println("Llista de programes MESCLATS");
            for (int i = 0; i < llistaProgrames.size(); i++) {
                System.out.println("Nom del programa: " + llistaProgrames.get(i).getNom());
            }

            if (!igual) {
                in = dataIni;
            }
            int dia = in.get(Calendar.DAY_OF_MONTH);
            int mes = in.get(Calendar.MONTH);
            int any = in.get(Calendar.YEAR);

            SimpleDateFormat formatCalendar = new SimpleDateFormat("dd-MM-yyyy");
            Date dat = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
            Calendar dataEmissio = Calendar.getInstance();
            dataEmissio.setTime(dat);
            /**/
            System.out.println("dataEmissio = " + dia + "-" + mes + "-" + any);
            /**/
            fi = dataFi;
            dia = fi.get(Calendar.DAY_OF_MONTH);
            mes = fi.get(Calendar.MONTH);
            any = fi.get(Calendar.YEAR);
            /**/
            System.out.println("dataFiEmissio =" + dia + "-" + mes + "-" + any);
            /**/
            dat = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
            Calendar dataFiEmissio = Calendar.getInstance();
            dataFiEmissio.setTime(dat);

            int n = 0;

            System.out.println("ABANS WHILE 2");
            System.out.println("numCriterisAfluixats =" + numCriterisAfluixats);
            System.out.println("nemis =" + nemis);
            while (nemis < (llistaProgrames.size() + n) && numCriterisAfluixats <= 5) {
                System.out.println("numCriterisAfluixats =" + numCriterisAfluixats);
                System.out.println("nemis =" + nemis);
                System.out.println("n =" + n);
                System.out.println("planNova =" + planNova);
                if (planNova) {
                    System.out.println("new planificacio!");
                    plani = new Planificacio(dataIni, dataFi);
                }

                System.out.println("ABANS FOR 1");
                System.out.println("preuSobrepassat =" + preuSobrepassat);
                for (int i = 0; i < llistaProgrames.size() && !preuSobrepassat; i++) {
                    System.out.println("i =" + i);
                    ok = false;
                    System.out.println("nom prog = " + llistaProgrames.get(i).getNom());
                    if (!pertanyPlanificacio(llistaProgrames.get(i))) {

                        System.out.println(" DIA EMISSIO = " + dataEmissio.get(Calendar.DAY_OF_MONTH));
                        if (((llistaProgrames.get(i).getClass()).getName().charAt(llistaProgrames.get(i).getClass().getName().length() - 1)) == 'D' && ((Directe) (llistaProgrames.get(i))).getIniciEmissio().equals(dataEmissio) || ((llistaProgrames.get(i).getClass()).getName().charAt(llistaProgrames.get(i).getClass().getName().length() - 1)) != 'D') {
                            System.out.println("DIRECTE I MATEIX DIA O NO DIRECTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                            System.out.println("criteris[4][1] =" + criteris[4][1]);
                            if (criteris[4][1] == 0) {
                                /* Criteri Dates Planificacio invalidat, per tant augmentem la dataFiEmissio */
                                System.out.println("criteris[4][1] ==0");
                                dia = dataEmissio.get(Calendar.DAY_OF_MONTH);
                                mes = dataEmissio.get(Calendar.MONTH);
                                any = dataEmissio.get(Calendar.YEAR);
                                /**/
                                System.out.println("dataEmissio = " + dia + "-" + mes + "-" + any);
                                /**/
                                formatCalendar = new SimpleDateFormat("dd-MM-yyyy");
                                dat = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
                                dataFiEmissio = Calendar.getInstance();
                                dataFiEmissio.setTime(dat);
                                /**/
                                dia = dataFiEmissio.get(Calendar.DAY_OF_MONTH);
                                mes = dataFiEmissio.get(Calendar.MONTH);
                                any = dataFiEmissio.get(Calendar.YEAR);
                                /**/

                                dataFiEmissio.add(Calendar.DAY_OF_MONTH, +1);
                                /**/
                                System.out.println("dataFiEmissio = " + dia + "-" + mes + "-" + any);
                                /**/
                                /*ini*/

                                System.out.println("criteris[4][1] ==0");
                                dia = dataEmissio.get(Calendar.DAY_OF_MONTH);
                                mes = dataEmissio.get(Calendar.MONTH);
                                any = dataEmissio.get(Calendar.YEAR);

                                System.out.println("dataEmissio = " + dia + "-" + mes + "-" + any);

                                dia = dataFiEmissio.get(Calendar.DAY_OF_MONTH);
                                mes = dataFiEmissio.get(Calendar.MONTH);
                                any = dataFiEmissio.get(Calendar.YEAR);

                                System.out.println("dataFiEmissio = " + dia + "-" + mes + "-" + any);

                            /*fi*/
                            }
                            System.out.println("ABANS WHILE 3");
                            System.out.println("preuSobrepassat =" + preuSobrepassat);
                            System.out.println("ok =" + ok);

                            dia = dataEmissio.get(Calendar.DAY_OF_MONTH);
                            mes = dataEmissio.get(Calendar.MONTH);
                            any = dataEmissio.get(Calendar.YEAR);
                            System.out.println("DATA EMISSIO: " + dia + "-" + mes + "-" + any);

                            dia = dataFiEmissio.get(Calendar.DAY_OF_MONTH);
                            mes = dataFiEmissio.get(Calendar.MONTH);
                            any = dataFiEmissio.get(Calendar.YEAR);
                            System.out.println("DATA FI EMISSIO: " + dia + "-" + mes + "-" + any);

                            while (!dataEmissio.after(dataFiEmissio) && !preuSobrepassat && !ok) {

                                System.out.println(" SI QUE PASSA DE WHILE 3!!");

                                numEmisDia = 0;
                                System.out.println("criteris[4][1] =" + criteris[1][1]);
                                if (criteris[1][1] == 0) {
                                    System.out.println("criteris[1][1] ==0");
                                    crearFrangesNoProhibidesNiPreferides(fPreferides, fProhibides);
                                    System.out.println("CREAR NO PROHIB NI PREFES -> size = " + llistaFranges.size());
                                }
                                System.out.println("criteris[2][1] =" + criteris[2][1]);
                                if (criteris[2][1] == 0) {
                                    System.out.println("criteris[2][1] ==0");
                                    llistaFranges.clear();
                                    listFranAux.clear();
                                    System.out.println("Size repositoriFranges =" + repositoriFranges.size());
                                    for (int j = 0; j < repositoriFranges.size(); j++) {
                                        listFranAux.add(repositoriFranges.get(j));
                                        System.out.println("IniFranga : " + repositoriFranges.get(j).getHoraInici().get(Calendar.HOUR_OF_DAY) + ":" + repositoriFranges.get(j).getHoraInici().get(Calendar.MINUTE) + " FINS HORA FI" + repositoriFranges.get(j).getHoraFi().get(Calendar.HOUR_OF_DAY) + ":" + repositoriFranges.get(j).getHoraFi().get(Calendar.MINUTE));
                                    }
                                    llistaFranges = ordenarFrangesPreu(listFranAux);
                                    System.out.println("Size lrepositoriFranges =" + repositoriFranges.size());
                                }

                                System.out.println("ABANS FOR 2");
                                System.out.println("preuSobrepassat =" + preuSobrepassat);
                                System.out.println("ok =" + ok);
                                for (int j = 0; j < llistaFranges.size() && !preuSobrepassat && !ok; j++) {

                                    valor = factible(llistaProgrames.get(i), llistaFranges.get(j), dataEmissio);
                                    System.out.println("factible -> valor[0]=" + valor[0]);
                                    if (valor[0].equals("1")) {
                                        /**/
                                        System.out.println("ES FACTIBLE!!");
                                        /**/
                                        SimpleDateFormat formatCalendar2 = new SimpleDateFormat("H:mm");
                                        Date dateIni = formatCalendar2.parse(valor[1]);
                                        Calendar calendarInici = Calendar.getInstance();
                                        calendarInici.setTime(dateIni);

                                        dia = llistaFranges.get(j).getHoraInici().get(Calendar.HOUR_OF_DAY);
                                        mes = llistaFranges.get(j).getHoraInici().get(Calendar.MINUTE);
                                        System.out.println("llistaFranges.get(j) -> inici = " + dia + ":" + mes);

                                        FranjaHoraria franja = cercarFranja(llistaFranges.get(j).getHoraInici(), repositoriFranges);
                                        System.out.println("FRANJA: hIni " + franja.getHoraInici().get(Calendar.HOUR_OF_DAY) + " hFi " + franja.getHoraFi().get(Calendar.MINUTE));
                                        Emissio emi = new Emissio(dataEmissio, false, false, llistaProgrames.get(i), franja, calendarInici, calendarFi);

                                        /**/
                                        System.out.println("preu emissio= " + emi.getPreuEmissio() + " preu total amb emissio=" + (preuTotal + emi.getPreuEmissio()));
                                        /**/
                                        if (criteris[0][1] == 1) {
                                            if ((preuTotal + emi.getPreuEmissio()) <= preuMax) {

                                                System.out.println("NEW EMISSIO! amb preuMax");
                                                plani.getLlistaEmissions().add(emi);
                                                nemis++;
                                                numEmisDia++;
                                                preuTotal += emi.getPreuEmissio();
                                                ok = true;
                                            } else {
                                                System.out.println("PREU SOBREPASSAT");
                                                preuSobrepassat = true;
                                            }
                                        } else {

                                            System.out.println("NEW EMISSIO! sense preuMax");

                                            plani.getLlistaEmissions().add(emi);
                                            nemis++;
                                            numEmisDia++;
                                            preuTotal += emi.getPreuEmissio();
                                            ok = true;
                                            preuSobrepassat = false;
                                        }

                                        switch (separ) {
                                            case 1:
                                                System.out.println("separ ==1 -> day++");
                                                dataEmissio.add(Calendar.DAY_OF_MONTH, +1);
                                                if (dataEmissio.after(dataFi)) {
                                                    dataEmissio.add(Calendar.DAY_OF_MONTH, -1);
                                                }
                                                break;
                                            case 3:
                                                System.out.println("Separ == 3");
                                                if (numEmisDia >= maxEmisDia) {
                                                    System.out.println("Separ == 3 -> dia++");
                                                    dataEmissio.add(Calendar.DAY_OF_MONTH, +1);
                                                    if (dataEmissio.after(dataFi)) {
                                                        dataFiEmissio.add(Calendar.DAY_OF_MONTH, +1);
                                                    }
                                                }
                                                break;
                                            default:
                                                System.out.println("separ == 2 -> day =day");
                                                break;
                                        }
                                    }
                                }

                                boolean preu;
                                if (preuSobrepassat && criteris[0][1] == 0) {
                                    preu = true;
                                } else {
                                    preu = false;
                                }

                                if (!ok && separ == 2 && preu) {
                                    System.out.println("!ok && separ ->  day++");
                                    dataEmissio.add(Calendar.DAY_OF_MONTH, +1);
                                    if (dataEmissio.after(dataFi)) {
                                        dataFiEmissio.add(Calendar.DAY_OF_MONTH, +1);
                                    } else {
                                        numEmisDia = 0;
                                    }
                                }
                            }
                        } else {
                            dataEmissio.add(Calendar.DAY_OF_MONTH, +1);
                            if (dataEmissio.after(dataFi)) {
                                dataFiEmissio.add(Calendar.DAY_OF_MONTH, +1);
                            }
                        }
                    } // aki
                }

                if (criteris[3][1] == 0) {
                    /* Si el criteri "programes" esta desabilitat decrementem el numero de programes a buscar */
                    n--;
                }

                if (nemis < llistaProgrames.size()) {
                    System.out.println("ALFUIXAM ELS CRITERIS!!!!!!!!!!!!!!!!!!!!!!! OJOOOOOOO");
                    afluixarCriteris();
                    planNova = false;
                    preuSobrepassat = false;
                }

            }

            /* COMMPROVAM QUE NO EXISTEIX UNA PLANIFICACIO IGUAL */

            total++;
            System.out.println("total++");
            //if (planNova) {

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

                            if (k == j && ((Emissio) llistaPlanificacionsGenerades.get(i).getLlistaEmissions().get(j)).getPrograma().getNom().compareTo(((Emissio) plani.getLlistaEmissions().get(j)).getPrograma().getNom()) == 0 && ((Emissio) llistaPlanificacionsGenerades.get(i).getLlistaEmissions().get(j)).getDataEmissio().equals(((Emissio) plani.getLlistaEmissions().get(j)).getDataEmissio()) && ((Emissio) llistaPlanificacionsGenerades.get(i).getLlistaEmissions().get(j)).getHoraInici().equals(((Emissio) plani.getLlistaEmissions().get(j)).getHoraInici()) && ((Emissio) llistaPlanificacionsGenerades.get(i).getLlistaEmissions().get(j)).getHoraFi().equals(((Emissio) plani.getLlistaEmissions().get(j)).getHoraFi())) {
                                if (countadorIguals == 2) {
                                    in.add(Calendar.DAY_OF_MONTH, +1);
                                    countadorIguals = -1;
                                }
                                countadorIguals++;
                                igual = true;
                                System.out.println(" PLANIS IGUALS!!");

                            }
                        }
                    }
                }

                if (!igual) {
                    llistaPlanificacionsGenerades.add(plani);
                    System.out.println("PLANIFICACIO AGREGADA A LLISTA DE GENERADES!");
                }
            }
            // }

            /* Fi comprovacio igualtat */

            if (total == (maxIteracions / 3) || total == (2 * maxIteracions / 3)) {
                switch (separ) {
                    case 1:
                        separ = 2;
                        break;
                    case 2:
                        separ = 3;
                        break;
                    case 3:
                        separ = 1;
                        break;
                    default:
                        break;
                    }
            }

        }
        System.out.println("total = " + total);
        System.out.println("");

        return llistaPlanificacionsGenerades;
    }

    private void afluixarCriteris() {
        int pos = 0;
        for (int i = 1; i <
                5; i++) {
            if (criteris[i][0] > criteris[i - 1][0] && criteris[i][1] != 0) {
                pos = i;
            }

        }
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
        System.out.println("FRANJA = NULL!! > ARA RETORN ES DARRER ELEMENT!!");

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

            Date dateFi = formatCalendar2.parse("00:00");
            fi =
                    Calendar.getInstance();
            fi.setTime(dateFi);

            FranjaHoraria franja = new FranjaHoraria(ini, fi, (float) 0.00);
            llistaFranges.add(franja);

            /* 2 FRANGES COBREIXEN LES 24 HORES DEL DIA */
            franja =
                    new FranjaHoraria(fi, ini, (float) 0.00);
            llistaFranges.add(franja);

        } else if (!(listFranAux.size() == 1 && listFranAux.get(0).getHoraInici().equals(listFranAux.get(0).getHoraFi()))) {

            boolean afegir = false, primeraV = true, cavalga = false;

            for (int i = 0; i <
                    listFranAux.size(); i++) {

                /* La primera franja comença a les 00:00    */
                if (listFranAux.get(i).getHoraInici().equals(zero)) {
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
                    if (!listFranAux.get(0).getHoraInici().equals(zero)) {
                        fi = listFranAux.get(i).getHoraInici();
                    } else { /* horaInici primera franja == 00:00 */
                        fi = listFranAux.get(i + 1).getHoraInici();
                    }

                /* Si no hi ha cap més franja i no hi ha cavalgament */
                } else if (!cavalga) {
                    if (!listFranAux.get(i).getHoraFi().equals(zero) && !listFranAux.get(i).getHoraInici().equals(zero) && !ini.equals(listFranAux.get(i).getHoraFi())) {
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

                    if (!listFranAux.get(0).getHoraInici().equals(zero)) {
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
            x %= 7;

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

            if (!valor[1].equals("1")) {
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
                if (((Emissio) plani.getLlistaEmissions().get(k)).getPrograma().getNom().compareTo(prog.getNom()) == 0) {
                    System.out.println("SI pertany a la planificacio");
                    return true;
                }

            }
        }
        System.out.println("NO pertany a la planificacio");
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

    private LinkedList<Programa> seleccionarDirectes(LinkedList<Programa> llistaP) {

        LinkedList<Programa> llista = new LinkedList<Programa>();

        for (int i = 0; i <
                llistaP.size(); i++) {

            if (((llistaP.get(i).getClass()).getName().charAt(llistaP.get(i).getClass().getName().length() - 1)) == 'D') {

                llista.add(llistaP.get(i));
            }

        }
        return llista;
    }

    private LinkedList<Programa> seleccionarNoDirectes(LinkedList<Programa> llistaP) {

        LinkedList<Programa> llista = new LinkedList<Programa>();

        for (int i = 0; i <
                llistaP.size(); i++) {

            if (!(((llistaP.get(i).getClass()).getName().charAt(llistaP.get(i).getClass().getName().length() - 1)) == 'D')) {

                llista.add(llistaP.get(i));
            }

        }
        return llista;
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

    private String[] solapa(Programa prog, FranjaHoraria franja, Calendar dataEmissio) throws ParseException {
        /*  TRUE si es solapa amb altres emissions de la mateixa Planificacio    */
        /* Inicialitzarem les hores: calendarInici i calendarFi, que fan referencia a l'emissio en cas que no es solapi */

        String valor[] = new String[2];
        Calendar calendarInici = null;

        SimpleDateFormat formatCalendar2 = new SimpleDateFormat("H:mm");
        int horaIni = 0, minutIni = 0, horaFi = 0, minutFi = 0;
        boolean solapa = false;
        boolean directe = false;
        boolean fi = false;
        Date hora;

        int dura = duracio(prog);

        if (plani.getLlistaEmissions().size() == 0) {

            if (((prog.getClass()).getName().charAt(prog.getClass().getName().length() - 1)) == 'D') {
                /* SI ES DIRECTE */
                directe = true;

                System.out.println("PROG DIRECTE ==> " + prog.getNom());
                System.out.println("Inici Emissio" + ((Directe) prog).getIniciEmissio().get(Calendar.DAY_OF_MONTH));

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

                calendarInici = franja.getHoraInici();

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

            }

            if (calendarInici.before(franja.getHoraInici()) || calendarFi.after(franja.getHoraFi())) {
                /* NO ES FACTIBLE    */
                solapa = true;
            }

        } else {    /*  plani.getLlistaEmissions().size() > 1   */
            for (int j = 0; j <
                    plani.getLlistaEmissions().size() && !solapa; j++) {


                if (((Emissio) plani.getLlistaEmissions().get(j)).getDataEmissio().equals(dataEmissio)) {

                    do {

                        if (((prog.getClass()).getName().charAt(prog.getClass().getName().length() - 1)) == 'D') {
                            /* SI ES DIRECTE */
                            directe = true;
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


                            calendarInici = franja.getHoraInici();

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

                            calendarInici.add(Calendar.MINUTE, +1);

                        }

                        if ((!calendarFi.after(franja.getHoraFi()) && !calendarInici.before(franja.getHoraInici())) && (!calendarFi.after(((Emissio) plani.getLlistaEmissions().get(j)).getHoraInici()) || !calendarInici.before(((Emissio) plani.getLlistaEmissions().get(j)).getHoraFi()))) {

                            fi = true;
                        }

                    } while (!fi && !directe && !calendarFi.after(franja.getHoraFi()));

                }

            }
        }

        if (solapa) {
            valor[0] = "" + 1;
        } else {
            valor[0] = "" + 0;
        }

        //if (minutIni < 10) {
        //  valor[1] = "" + horaIni + ":0" + minutIni;
        //} else {
        valor[1] = "" + horaIni + ":" + minutIni;
        System.out.println("SOLAPA -> horaInici = " + horaIni + ":" + minutIni);
        //}
        return valor;
    }
}
