/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes;

import domini.ControladorPlanificacio;
import domini.ControladorProgrames;
import domini.tuplaCriteris;
import domini.tuplaEmissio;
import domini.tuplaPrograma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Josep Marti
 */
public class kVistaPlanificacio {

    private ControladorPlanificacio CPlani;
    private VistaPlanificacio vPlani;
    private VistaGenerada vGen;
    private VistaCriteris vCriteris;
    private VistaSelectProgrames vSprog;
    private ControladorProgrames CPG;
    private String[] llistaPlanificacions;
    private String[][] graella;
    tuplaCriteris nousCriteris;
    tuplaEmissio tEmissio[];
    private Calendar iniciSetmana,  fiSetmana;
    private tuplaPrograma dadesPrograma;
    SimpleDateFormat formatCalendar;
    private Vector<String> programesSeleccionats;
    private String[] llistaProgrames;
    private String[] llistaFiltres;

    public kVistaPlanificacio(ControladorProgrames controladorProg, ControladorPlanificacio controladorPlani) {

        System.out.println("INIT");
        CPlani = controladorPlani;
        CPG = controladorProg;


        llistaPlanificacions = null;
        programesSeleccionats = null;
        graella = null;
        formatCalendar = new SimpleDateFormat("dd/MM/yyyy");

        vCriteris = new VistaCriteris(new javax.swing.JFrame(), true);
        vSprog = new VistaSelectProgrames(new javax.swing.JFrame(), true);
        vGen = new VistaGenerada(new javax.swing.JFrame(), true);

        initSetmana();
        initVistaPlanificacio();
        initVistaCriteris();
        initVistaSelectProg();
        initVistaGenerat();

    }

    public VistaPlanificacio getVistaPlan() {
        return vPlani;
    }

    public void setLlistaPlani() {
        llistaPlanificacions = CPlani.getLlistaPlanificacions();
        vPlani.setLlistaPlans(llistaPlanificacions);
    }

    public void initVistaPlanificacio() {

        vPlani = new VistaPlanificacio();

        ActionListener actions[] = new ActionListener[4];

        ListSelectionListener selPlan;

        selPlan = (new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                seleccionatPlanificacio();
            }
        });

        actions[0] = (new ActionListener() {
            /* Nova planificacio */

            public void actionPerformed(ActionEvent e) {
                System.out.println("HOSTIA PUTA JODER!!");
                vCriteris.setLocationRelativeTo(vPlani);
                vCriteris.setTitle("Definir criteris planificacio");
                vCriteris.setVisible(true);
            }
        });


        actions[1] = (new ActionListener() {
            /* Setmana Anterior */

            public void actionPerformed(ActionEvent e) {
                retrocedirSetmana();
            }
        });

        actions[2] = (new ActionListener() {
            /* Setmana Seguent */

            public void actionPerformed(ActionEvent e) {
                avancarSetmana();
            }
        });

        actions[3] = (new ActionListener() {
            /* Anular */

            public void actionPerformed(ActionEvent e) {

                String nomPrograma = vPlani.getGraellaSelected();
                if (nomPrograma != null) {
                    try {
                        String idPlanificacio = vPlani.getPlanSelected();
                        /* idPLanificacio son 2 calendars dd/mm/yyyy - dd/mm/yyyy */

                        String sIni = idPlanificacio.substring(0, 10);
                        String sFi = idPlanificacio.substring(13, 23);

                        Date dateInici = formatCalendar.parse(sIni);
                        Date dateFi = formatCalendar.parse(sFi);
                        Calendar dIni = Calendar.getInstance();
                        Calendar dFi = Calendar.getInstance();
                        dIni.setTime(dateInici);
                        dFi.setTime(dateFi);

                        CPlani.anularEmissio(nomPrograma, dIni, dFi, false); /* true implica que es TEMPORAL */
                    } catch (ParseException ex) {
                        System.out.println("Error: L 140");
                        Logger.getLogger(kVistaPlanificacio.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

        vPlani.setActions(actions, selPlan);
    }

    private void generarGraella() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void initVistaSelectProg() {

        ListSelectionListener selFiltre, selPrograma;
        ActionListener accions[] = new ActionListener[7];


        /**Setejem les dues llistes que tenim, la de programes i la de filtres
        buidem tambe la fitxa*/
        actLlistaFiltres(0);
        actLlistaProgrames("tots", "");
        vSprog.setLlistaProgrames(llistaProgrames);
        vSprog.setLlistaFiltres(llistaFiltres);
        vSprog.clearFitxa();

        /**Init de nous listeners*/
        /** EventHandle.create(Qui fa sa crida, Classe a sa que es cerca sa funcio, nom de sa funcio)*/
        accions[0] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                setLlistaFiltre();
            }
        });

        accions[1] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                setLlistaFiltre();
            }
        });

        accions[2] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                setLlistaFiltre();
            }
        });

        accions[3] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                setLlistaFiltre();
            }
        });


        accions[4] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                programesSeleccionats = vSprog.getLlistaSeleccionats();
                // funcio ControladorProgrames.llistarProgramesNom(String[] nom) -> LinkedList<Programa>
                llistaPlanificacions = CPlani.gene(programesSeleccionats, nousCriteris);
                vSprog.setVisible(false);
                vGen.setLocationRelativeTo(vSprog);
                vGen.setTitle("Definir criteris planificacio");
                vGen.setVisible(true);
            }
        });


        selFiltre = (new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                actualitzaLlProgrames();
            }
        });

        selPrograma = (new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                seleccionatPrograma();
            }
        });

        vSprog.setListeners(accions, selFiltre, selPrograma);
    }

    private void initSetmana() {
        try {

            Calendar inici = Calendar.getInstance();
            Calendar fi = (Calendar) inici.clone();
            int ara = inici.get(Calendar.DAY_OF_WEEK);
            /* SUNDAY=0, MONDAY=1, TUESDAY=2, WEDNESDAY=3, THURSDAY=4, FRIDAY=5 and SATURDAY=6 */

            int sumar = 0;
            if (ara == 0) {
                sumar = 6;
            } else {
                sumar = ara - 1;
            }

            inici.add(Calendar.DAY_OF_MONTH, -(sumar));
            int dia = inici.get(Calendar.DAY_OF_MONTH);
            int mes = inici.get(Calendar.MONTH);
            int any = inici.get(Calendar.YEAR);

            Date dateInici = formatCalendar.parse("" + dia + "/" + mes + "/" + any);
            inici = Calendar.getInstance();
            inici.setTime(dateInici);
            Date dateFi = formatCalendar.parse("" + dia + "/" + mes + "/" + any);
            fi = Calendar.getInstance();
            fi.setTime(dateFi);
            fi.add(Calendar.DAY_OF_MONTH, +7);
        } catch (ParseException ex) {
            System.out.println("Error: L 257");
            Logger.getLogger(kVistaPlanificacio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initVistaCriteris() {

        ActionListener actions = (new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    nousCriteris = vCriteris.getCriteris();
                    vCriteris.setVisible(false);
                    vSprog.setVisible(true);
                } catch (ParseException ex) {
                    System.out.println("Error: L 273");
                    Logger.getLogger(kVistaPlanificacio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        vCriteris.setActions(actions);
    }

    private void initVistaGenerat() {


        ActionListener actions[] = new ActionListener[4];

        ListSelectionListener selPlan;

        selPlan = (new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                seleccionatPlanGen();
            }
        });

        actions[0] = (new ActionListener() {
            /* Setmana Anterior */

            public void actionPerformed(ActionEvent e) {
                retrocedirSetmana();
                if (vGen.getPlanSelected() != null) {
                    seleccionatPlanGen();
                }
            }
        });

        actions[1] = (new ActionListener() {
            /* Setmana Seguent */

            public void actionPerformed(ActionEvent e) {
                avancarSetmana();
                if (vGen.getPlanSelected() != null) {
                    seleccionatPlanGen();
                }
            }
        });

        actions[2] = (new ActionListener() {
            /* Anular programa */

            public void actionPerformed(ActionEvent e) {
                String nomPrograma = vGen.getGraellaSelected();
                if (nomPrograma != null) {
                    try {
                        String idPlanificacio = vGen.getPlanSelected();
                        //Transformar idPlanificacio a Calendari....
                        // idPLanificacio son 2 calendars dd/mm/yyyy - dd/mm/yyyy
                        String sIni = idPlanificacio.substring(0, 10);
                        String sFi = idPlanificacio.substring(13, 23);

                        Date dateInici = formatCalendar.parse(sIni);
                        Date dateFi = formatCalendar.parse(sFi);
                        Calendar dIni = Calendar.getInstance();
                        Calendar dFi = Calendar.getInstance();
                        dIni.setTime(dateInici);
                        dFi.setTime(dateFi);

                        CPlani.anularEmissio(nomPrograma, dIni, dFi, true); // true implica que es TEMPORAL
                    } catch (ParseException ex) {
                        System.out.println("Error: L 339");
                        Logger.getLogger(kVistaPlanificacio.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

        actions[3] = (new ActionListener() {
            /* Contractar */

            public void actionPerformed(ActionEvent e) {

                String idPlanificacio = vGen.getPlanSelected();

                if (idPlanificacio != null) {
                    try {
                        // idPLanificacio son 2 calendars dd/mm/yyyy - dd/mm/yyyy
                        // idPLanificacio son 2 calendars dd/mm/yyyy - dd/mm/yyyy
                        String sIni = idPlanificacio.substring(0, 10);
                        String sFi = idPlanificacio.substring(13, 23);

                        Date dateInici = formatCalendar.parse(sIni);
                        Date dateFi = formatCalendar.parse(sFi);
                        Calendar dIni = Calendar.getInstance();
                        Calendar dFi = Calendar.getInstance();
                        dIni.setTime(dateInici);
                        dFi.setTime(dateFi);

                        CPlani.contractar(dIni, dFi);
                    // cerca sa planificacio de sa llista temporal i fa un client.addPlanificacio(plani);
                    } catch (ParseException ex) {
                        System.out.println("Error: L 372");
                        Logger.getLogger(kVistaPlanificacio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                // cerca sa planificacio de sa llista temporal i fa un client.addPlanificacio(plani);              

                }
            }
        });

        vGen.setActions(actions, selPlan);
    }

    private void seleccionatPlanGen() {
// agafa sa planificacio seleccionada de sa vista gen anat dominitPlanificacio i cercar sa planificacio temporal
        // pillant sa seva data ini + fi de manera que retorna una tupla d'emissions
        // idPlanficacio = vPlanGen.getPlanSelected();
        // vectorTuplaEmissions = cPlani.getEmissions(idPlanificacio,temporal);
        // initSetmana();
        // generarGraella();
        // vPlanGen.pintarGraella(graella);
        // generar graella amb akesta tupla demissions (segons la setmana indicada en globals)

        String idPlanificacio = vGen.getPlanSelected();
        initSetmana();
        generarGraella();
        vGen.pintarGraella(graella);

    }

    private void seleccionatPlanificacio() {
        //mateix codi que seleccionat plangen pero canviar sa vista

        String idPlanificacio = vPlani.getPlanSelected();
        initSetmana();
        generarGraella();
        vPlani.pintarGraella(graella);

    }

    private void retrocedirSetmana() {

        iniciSetmana.add(Calendar.DAY_OF_MONTH, -7);
        fiSetmana.add(Calendar.DAY_OF_MONTH, -7);

    }

    private void avancarSetmana() {
        iniciSetmana.add(Calendar.DAY_OF_MONTH, +7);
        fiSetmana.add(Calendar.DAY_OF_MONTH, +7);
    }

    /** A Partir d'aqui  part de Controlador de vistes de Programes*/
    public void setLlistaFiltre() {
        vSprog.clearFitxa();
        actLlistaFiltres(vSprog.getFClickedInt());
        vSprog.setLlistaFiltres(llistaFiltres);
    }

    public void seleccionatPrograma() {
        //Agafem nom del programa seleccionat
        String nomP = vSprog.getProgramaSelected();

        //Agafem fitxa del programa seleccionat
        //i actualizem la fitxa
      
        tuplaPrograma dadesP = CPG.veureFitxa(nomP.toLowerCase());
        if (dadesP != null) {
            String fitxa = "Nom: " + dadesP.nom + "\nPreu: " + dadesP.preu + "\nFormat: " + dadesP.format + "\nCategoria: " + dadesP.categoria + "\nDescripcio: " + dadesP.descripcio + "\nData Caducitat: " + dadesP.dataCad.get(Calendar.DATE) + "/" + dadesP.dataCad.get(Calendar.MONTH) +
                    "/" + dadesP.dataCad.get(Calendar.YEAR);

            if (dadesP.format == 2 || dadesP.format == 0) {
                fitxa += "\nDuracio: " + dadesP.duracio;
            }

            if (dadesP.format == 2) {
                fitxa = fitxa + "\nData Inici Emissio:" + dadesP.iniciEmissio.get(Calendar.DATE) + "/" + dadesP.iniciEmissio.get(Calendar.MONTH) +
                        "/" + dadesP.iniciEmissio.get(Calendar.YEAR) + " a les " + dadesP.iniciEmissio.get(Calendar.HOUR_OF_DAY) + ":" + dadesP.iniciEmissio.get(Calendar.MINUTE);
            }


            if (dadesP.tematiques != null) {
                fitxa = fitxa + "\nTemes: ";
                for (int i = 0; i < dadesP.tematiques.length; i++) {
                    if (i == 5) {
                        fitxa = fitxa + "\n";
                    }
                    fitxa = fitxa + dadesP.tematiques[i] + " ";
                }
            }

            vSprog.setCuadreFitxa(fitxa);
        }
    }

    public void actualitzaLlProgrames() {
        //System.out.println("Actualitz ll progs!!");
        //Boto pitjat
        String tipusFiltre = vSprog.getFClickedStr();


        //Agafa valor des filtre
        String valorFiltre;

        if (tipusFiltre.equalsIgnoreCase("tematica") == true || tipusFiltre.equalsIgnoreCase("nom") == true) {
            valorFiltre = vSprog.getFiltreSelected(true).toLowerCase();
        } else {
            valorFiltre = vSprog.getFiltreSelected(false);
        }


        if (valorFiltre.equals("-2") || valorFiltre.equals("-1") || valorFiltre.equals("tots")) {
            tipusFiltre = "tots";
        }
        llistaProgrames = CPG.getllistaFiltrada(tipusFiltre, valorFiltre);

        //Actualitza llista
        if (llistaProgrames == null) {
            llistaProgrames = new String[1];
            llistaProgrames[0] = "";
        }

        vSprog.setLlistaProgrames(llistaProgrames);
    }

    /**
     * Obte una llista filtrada del controlador de domini de programes,
     * i l'assigna a la llistaProgrames, variable global d'aquest controlador.
     * @param  valor Es el valor que agafa el filtre, que sera '"0".."2" si es
     * un format, "0".."10" si es una categoria, i un String qualsevol si
     * Tematica o Nom. Valdra null si volem Tots els programes.
     * @param    tipusFiltre pren com a valor Format, Categoria, 
     *          Tematica, Nom o Tots, en forma d'string.
     * @pre -
     * @post S'ha assignat la llista filtrada del CPG a la global d'aquesta classe.
     */
    public void actLlistaProgrames(String tipusFiltre, String valor) {
        llistaProgrames = CPG.getllistaFiltrada(tipusFiltre, null);
    }

    private void actLlistaFiltres(int i) {
        switch (i) {
            case 0: //Llista de formats
                llistaFiltres = CPG.getLFormat();
                break;
            case 1: //Llista de categories
                llistaFiltres = CPG.getLCategories();
                break;
            case 2:  //Nom, introduit al popup
                //S'ha de fer el popup i agafar el valor
                Object[] possibilities = null;
                String s = (String) JOptionPane.showInputDialog(
                        null,
                        "Introdueix el nom del programa que cerques:\n",
                        "Cerca per Nom",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        "Tots");

                //If a string was returned:
                if ((s != null) && (s.length() > 0)) {
                    llistaFiltres = new String[1];
                    llistaFiltres[0] = s;
                    //  vGProgs.selectCercaNom();
                    return;
                }
                //If you're here, the return value was null/empty.
                llistaFiltres = new String[0];
                break;
            case 3: //Llista de Tematiques disponibles
                llistaFiltres = CPG.getLTematiques();
                break;
            default:
                break;
        }

    }
}
