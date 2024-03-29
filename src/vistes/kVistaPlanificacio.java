/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes;

import domini.ControladorPlanificacio;
import domini.ControladorProgrames;
import domini.Convertir;
import domini.tuplaCriteris;
import domini.tuplaEmissio;
import domini.tuplaPrograma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
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
    private VistaVeureResumP vResum;
    private ControladorProgrames CPG;
    private String[] llistaPlanificacions;
    private String[][] graella;
    tuplaCriteris nousCriteris;
    tuplaEmissio tEmissio[];
    private Calendar iniciSetmana,  fiSetmana;
    private Vector<String> programesSeleccionats;
    private String[] llistaProgrames;
    private String[] llistaFiltres;
    Convertir Conv;

    public kVistaPlanificacio(ControladorProgrames controladorProg, ControladorPlanificacio controladorPlani) throws ParseException {

        Conv = new Convertir();
        CPlani = controladorPlani;
        CPG = controladorProg;


        llistaPlanificacions = null;
        programesSeleccionats = null;
        graella = null;

        vCriteris = new VistaCriteris(new javax.swing.JFrame(), true);
        vSprog = new VistaSelectProgrames(new javax.swing.JFrame(), true);
        vGen = new VistaGenerada(new javax.swing.JFrame(), true);
        vResum = new VistaVeureResumP(new javax.swing.JFrame(), true);
        initSetmana();
        initVistaPlanificacio();
        initGraella();
        initVistaCriteris();
        initVistaGenerat();
        initVistaSelectProg();
    }

    public VistaPlanificacio getVistaPlan() {
        return vPlani;
    }

    public void actualitzaVista(boolean temporal) throws ParseException {
        llistaPlanificacions = CPlani.getLlistaPlanificacions(temporal);
        if (temporal) {
            vGen.setLlistaPlans(llistaPlanificacions);
            vGen.setGraella();
        } else {
            vPlani.setLlistaPlans(llistaPlanificacions);
            vPlani.setGraella();
        }
        iniDataClient();
    }

    public void initVistaPlanificacio() {

        //Fem un init de la setmana actual
        vPlani = new VistaPlanificacio();

        vPlani.setSetmana(Conv.setmana(iniciSetmana, fiSetmana));
        ActionListener actions[] = new ActionListener[5];

        ListSelectionListener selPlan;

        selPlan = (new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    seleccionatPlanificacio();
                } catch (ParseException ex) {
                    System.out.println("Err selecting plan " + ex.getMessage());
                }
            }
        });

        actions[0] = (new ActionListener() {
            /* Nova planificacio */

            public void actionPerformed(ActionEvent e) {
                if (CPlani.getClient() != null && CPG.getNProgs() != 0 && CPG.getNFranges() != 0) {
                    iniDataClient();
                    vCriteris.setLocationRelativeTo(vPlani);
                    vCriteris.setTitle("Definir criteris planificacio");
                    vCriteris.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Comprova que: \n - Has seleccionat un client" +
                            "\n - Hi ha algun programa al repositori \n - Ha alguna franja definida");
                }
            }
        });


        actions[1] = (new ActionListener() {
            /* Setmana Anterior */

            public void actionPerformed(ActionEvent e) {
                retrocedirSetmana(false);
            }
        });

        actions[2] = (new ActionListener() {
            /* Setmana Seguent */

            public void actionPerformed(ActionEvent e) {
                avancarSetmana(false);
            }
        });

        actions[3] = (new ActionListener() {
            /* Anular programa */

            public void actionPerformed(ActionEvent e) {

                String nomPrograma = vPlani.getGraellaSelected();
                int planSelected = vPlani.getIndexsSelected();
                try {
                    if (nomPrograma != null && nomPrograma.compareToIgnoreCase("") != 0 && nomPrograma.compareToIgnoreCase("-") != 0) {

                        String idPlanificacio = vPlani.getPlanSelected();
                        /* idPLanificacio son 2 calendars dd/mm/yyyy - dd/mm/yyyy */

                        Calendar[] idPlani = Conv.idPlanificacio(idPlanificacio);

                        if (CPlani.anularEmissio(nomPrograma, idPlani[0], idPlani[1], -9, false) /* true implica que es TEMPORAL */) {
                            vPlani.setPreu(0);
                            actualitzaVista(false);
                            if (planSelected != -1) {
                                vPlani.setSelectPlan(planSelected);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Aquesta emissio ja ha estat emesa o facturada.");
                        }

                    }
                } catch (ParseException ex) {
                    System.out.println("Error: L 140");
                }
            }
        });

        actions[4] = (new ActionListener() {
            /* Veure Resum */

            public void actionPerformed(ActionEvent e) {
                veureResum(false);
            }
        });

        vPlani.setActions(actions, selPlan);
    }

    public void veureResum(boolean temporal) {
        String idPlanificacio;
        int indexP = -9;
        if (temporal) {
            idPlanificacio = vGen.getPlanSelected();
            vResum.setLocationRelativeTo(vGen);
            indexP = vGen.getIndexPlanSelected();
        } else {
            idPlanificacio = vPlani.getPlanSelected();
            vResum.setLocationRelativeTo(vPlani);
        }

        if (idPlanificacio != null) {
            vResum.setTitle("Resum de la planificacio " + idPlanificacio);
            vResum.setResum(CPlani.genResum(idPlanificacio, indexP-1, temporal), idPlanificacio);
            vResum.setVisible(true);
        }
    }

    public void iniDataClient() {
        if (CPlani.getNumPlanisClient() > 0) {
            String data = CPlani.getDataClient((CPlani.getNumPlanisClient() - 1));
            vCriteris.setDataUltimaPlani(data);
        } else {
            vCriteris.setDataUltimaPlani("");
        }
    }

    private void initGraella() throws ParseException {
        if (CPlani.getNumPlanisClient() > 0) {
            generarGraella(false, -9);
            vPlani.pintarGraella(graella);
        }

    }

    private void initVistaSelectProg() {

        ListSelectionListener selFiltre, selPrograma, selSel;
        ActionListener accions[] = new ActionListener[5];


        /**Setejem les dues llistes que tenim, la de programes i la de filtres
        buidem tambe la fitxa*/
        actLlistaFiltres(0);
        actLlistaProgrames("tots", "");
        vSprog.setLlistaProgrames(llistaProgrames);
        vSprog.setLlistaFiltres(llistaFiltres);
        vSprog.clearFitxa();

        /**Init de nous listeners*/
        /**Button: Format*/
        accions[0] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                setLlistaFiltre();
            }
        });
        /**Button: Categoria*/
        accions[1] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                setLlistaFiltre();
            }
        });
        /**Button: Nom*/
        accions[2] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                setLlistaFiltre();
            }
        });
        /**Button: Tematica*/
        accions[3] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                setLlistaFiltre();
            }
        });

        /**Button: Generar*/
        accions[4] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                clicatBotoGenerar();
            }
        });


        selFiltre = (new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                actualitzaLlProgrames();
            }
        });

        selPrograma = (new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                seleccionatPrograma(false);
            }
        });

        selSel = (new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                seleccionatPrograma(true);
            }
        });

        vSprog.setListeners(accions, selFiltre, selPrograma, selSel);
    }

    private void initVistaCriteris() {

        ActionListener actions = (new ActionListener() {

            //Boto d'acceptar criteris
            public void actionPerformed(ActionEvent arg0) {
                try {
                    clicatAcceptCriteris();
                } catch (ParseException ex) {
                    Logger.getLogger(kVistaPlanificacio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        vCriteris.setActions(actions);
    }

    private void clicatBotoCancelGenerada() throws ParseException {
        vGen.setPreu(0);
        vGen.setVisible(false);
        actualitzaVista(false);
    }

    /**Aquesta funcio es crida quan contractem una nova planificacio a la finestra
     * de VistaGenerat.
     */
    private void clicatBotoContractar() {
        initSetmana();
        vPlani.setSetmana(Conv.setmana(iniciSetmana, fiSetmana));

        String idPlanificacio = vGen.getPlanSelected();

        if (idPlanificacio != null) {
            try {
                Calendar[] idPlanif = Conv.idPlanificacio(idPlanificacio);
                CPlani.contractar(idPlanif[0], idPlanif[1], vGen.getIndexPlanSelected());
                // cerca sa planificacio de sa llista temporal i fa un client.addPlanificacio(plani);
                vGen.setPreu(0);
                vGen.setVisible(false);
                actualitzaVista(true);
                actualitzaVista(false);

            } catch (ParseException ex) {
                System.out.println("Error: L 372");
            }
        }
    }

    /**Funcio que es realitza quan s'ha clicat el boto de Generar de la pantalla
     * de VistaSeleccioProgrames
     */
    private void clicatBotoGenerar() {
        try {

            programesSeleccionats = vSprog.getLlistaSeleccionats();
            if (programesSeleccionats.size() != 0) {
                llistaPlanificacions = CPlani.gene(programesSeleccionats, nousCriteris);

                /**Netejem la pantalla per posteriors usos*/
                vSprog.neteja();
                actLlistaFiltres(0);
                actLlistaProgrames("tots", "");
                vSprog.setLlistaProgrames(llistaProgrames);
                vSprog.setLlistaFiltres(llistaFiltres);
                vSprog.clearFitxa();
                vSprog.setVisible(false);

                /*Fem visible el resum de la planificacio*/
                String setmana = Conv.obteSetmana(nousCriteris.dataIni);
                vGen.setSetmana(setmana);
                vGen.setLocationRelativeTo(vSprog);
                vGen.setTitle("Planificacio Generada! - Resum");

                actualitzaVista(true);
                vGen.setLlistaPlans(llistaPlanificacions);
                vGen.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Has de seleccionar algun programa." +
                        "\n (no has seleccionat 'Autogeneracio')");
            }
        } catch (ParseException ex) {
            System.out.println("DEBUG: Error seleccionant programes: " + ex.getMessage());
        }
    }

    /**
     * Aquesta funcio realitza l'accio que es portara a terme quan s'hagi clicat
     * el boto "D'acord" a la finestra de definicio de criteris. Si s'ha seleccionat
     * com a criteri l'autogeneracio, es generaran planificacions i es pasara 
     * a la pantalla de veure les planificacions generades.
     * Altrament es pasara a la pantalla de seleccio de programes.
     */
    private void clicatAcceptCriteris() throws ParseException {

        nousCriteris = vCriteris.getCriteris();
        if (nousCriteris != null) {
            try {

                if (nousCriteris.autoGen == false) {

                    vSprog.setLocationRelativeTo(vCriteris);
                    vSprog.setTitle("Seleccionar programes");

                    actLlistaProgrames("tots", "");
                    filtraPeriode();


                    if (llistaProgrames.length == 0) {
                        JOptionPane.showMessageDialog(null, "Atenció, no hi ha cap programa en el període especificat." +
                                "\n Llista Buida.");
                    } else {
                        vSprog.setLlistaProgrames(llistaProgrames);
                        vCriteris.setVisible(false);
                        vSprog.setVisible(true);
                    }

                } else if (nousCriteris.autoGen == true) {
                    String setmana = Conv.setmana(iniciSetmana, fiSetmana);
                    vGen.setSetmana(setmana);
                    vGen.setLocationRelativeTo(vSprog);
                    vGen.setTitle("Planificacio Generada! - Resum");

                    //Si s'ha definit "AutoGeneracio" hi ha que fer la generacio a partir
                    // de tots els programes possibles.

                    llistaProgrames = CPG.getllistaFiltrada("tots", "");
                    filtraPeriode();

                    programesSeleccionats = new Vector<String>();
                    for (int i = 0; i < llistaProgrames.length; i++) {
                        programesSeleccionats.add(llistaProgrames[i]);
                    }

                    llistaPlanificacions = CPlani.gene(programesSeleccionats, nousCriteris);
                    vGen.setLlistaPlans(llistaPlanificacions);
                    vCriteris.setVisible(false);
                    vGen.setVisible(true);

                }

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Format incorrecte");
            }

        }
    }

    private void initVistaGenerat() {

        ActionListener actions[] = new ActionListener[6];
        vGen.setSetmana(Conv.setmana(iniciSetmana, fiSetmana));

        ListSelectionListener selPlan;

        selPlan =
                (new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    seleccionatPlanGen();
                } catch (ParseException ex) {
                    System.out.println("Error L418 kVistaPlanif: " + ex.getMessage());
                }
            }
        });

        actions[0] = (new ActionListener() {
            /* Setmana Anterior */

            public void actionPerformed(ActionEvent e) {
                retrocedirSetmana(true);
            }
        });

        actions[1] = (new ActionListener() {
            /* Setmana Seguent */

            public void actionPerformed(ActionEvent e) {
                avancarSetmana(true);
            }
        });

        actions[2] = (new ActionListener() {
            /* Anular programa */

            public void actionPerformed(ActionEvent e) {
                String nomPrograma = vGen.getGraellaSelected();
                int planSelected = vGen.getIndexsSelected();
                try {
                    if (nomPrograma != null && nomPrograma.compareToIgnoreCase("") != 0 && nomPrograma.compareToIgnoreCase("-") != 0) {

                        String idPlanificacio = vGen.getPlanSelected();
                        int indexPlan = vGen.getIndexPlanSelected();
                        /* idPLanificacio son 2 calendars dd/mm/yyyy - dd/mm/yyyy */
                        Calendar[] idPlani = Conv.idPlanificacio(idPlanificacio);

                        if (CPlani.anularEmissio(nomPrograma, idPlani[0], idPlani[1],indexPlan, true) /* true implica que es TEMPORAL */) {
                            actualitzaVista(true);
                            vGen.setPreu(0);
                            vGen.setSelectPlan(planSelected);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error anulant l'emissio.");
                        }

                    }
                } catch (ParseException ex) {
                    System.out.println("Error: L 140");
                }
            }
        });

        actions[3] = (new ActionListener() {
            /* Contractar */

            public void actionPerformed(ActionEvent e) {

                clicatBotoContractar();
            }
        });

        actions[4] = (new ActionListener() {

            /*Veure resum de la planificacio seleccionada*/
            public void actionPerformed(ActionEvent e) {
                veureResum(true);
            }
        });

        /**Button: Cancelar Generada*/
        actions[5] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    clicatBotoCancelGenerada();
                } catch (ParseException ex) {
                    Logger.getLogger(kVistaPlanificacio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        vGen.setActions(actions, selPlan);
    }

    private void initSetmana() {
        String setmana = Conv.obteSetmana(Calendar.getInstance());
        Calendar[] aux = Conv.idPlanificacio(setmana);
        iniciSetmana = aux[0];
        fiSetmana = aux[1];
    }

    private void seleccionatPlanGen() throws ParseException {
// agafa sa planificacio seleccionada de sa vista gen anat dominitPlanificacio i cercar sa planificacio temporal
        // pillant sa seva data ini + fi de manera que retorna una tupla d'emissions
        // idPlanficacio = vPlanGen.getPlanSelected();
        // vectorTuplaEmissions = cPlani.getEmissions(idPlanificacio,temporal);
        // initSetmana();
        // generarGraella();
        // vPlanGen.pintarGraella(graella);
        // generar graella amb akesta tupla demissions (segons la setmana indicada en globals)
        String planSelectedID = vGen.getPlanSelected();
        int indexPlanSelected = vGen.getIndexPlanSelected();

        if (planSelectedID != null || indexPlanSelected > 0) {

            Calendar idPlan[] = Conv.idPlanificacio(planSelectedID);
            String setmana = Conv.obteSetmana(idPlan[0]);
            idPlan = Conv.idPlanificacio(setmana);
            iniciSetmana = idPlan[0];
            fiSetmana = idPlan[1];

            vGen.setSetmana(setmana);

            //Aixo vol dir que cercara ses planificacions des client i no ses
            //generades per s'algoritme
            boolean temporal = true;

            //Seteja sa Graella amb ses noves tEmissio[]


            generarGraella(temporal, indexPlanSelected);

            if (graella != null)//Pintar-la
            {
                vGen.pintarGraella(graella);
            }
            vGen.setPreu(CPlani.getPreuPlan(planSelectedID, indexPlanSelected, true));
        }

    }

    private void avancarPlanificacio(boolean temporal) throws ParseException {
        String planSelectedID;
        int indexPlanSelected = -9;
        if (temporal) {
            indexPlanSelected = vGen.getIndexPlanSelected();
            planSelectedID = vGen.getPlanSelected();
        } else {
            planSelectedID = vPlani.getPlanSelected();
        }
        if (planSelectedID != null) {
            generarGraella(temporal, indexPlanSelected);
            if (temporal) {
                vGen.pintarGraella(graella);
            } else {
                vPlani.pintarGraella(graella);
            }
        }
    }

    private void retrocedirPlanificacio(boolean temporal) throws ParseException {
        String planSelectedID;
        int indexPlanSelected = -9;
        if (temporal) {
            indexPlanSelected = vGen.getIndexPlanSelected();
            planSelectedID = vGen.getPlanSelected();
        } else {
            planSelectedID = vPlani.getPlanSelected();
        }
        if (planSelectedID != null) {
            generarGraella(temporal, indexPlanSelected);
            if (temporal) {
                vGen.pintarGraella(graella);
            } else {
                vPlani.pintarGraella(graella);
            }
        }
    }

    private void seleccionatPlanificacio() throws ParseException {

        String planSelectedID = vPlani.getPlanSelected();
        if (planSelectedID != null) {
            initSetmana();

            //Aixo vol dir que cercara ses planificacions des client i no ses
            //generades per s'algoritme
            boolean temporal = false;

            Calendar idPlan[] = Conv.idPlanificacio(planSelectedID);
            String setmana = Conv.obteSetmana(idPlan[0]);
            idPlan = Conv.idPlanificacio(setmana);
            iniciSetmana = idPlan[0];
            fiSetmana = idPlan[1];
            vPlani.setSetmana(setmana);

            //Seteja sa Graella amb ses noves tEmissio[]
            generarGraella(temporal, -9);

            if (graella != null)//Pintar-la
            {
                vPlani.pintarGraella(graella);
            }

            vPlani.setPreu(CPlani.getPreuPlan(planSelectedID, false));
        }
    }

    public void seleccionatPrograma(boolean llistaSeleccio) {
        //Agafem nom del programa seleccionat de la llistaSeleccio o de la llistaProgrames:
        String nomP;
        if (llistaSeleccio) {
            nomP = vSprog.getSeleccioSelected();
        } else {
            nomP = vSprog.getProgramaSelected();
        }

        //Agafem fitxa del programa seleccionat
        //i actualizem la fitxa

        tuplaPrograma dadesP = CPG.veureFitxa(nomP.toLowerCase());
        if (dadesP != null) {
            String fitxa = "Nom: " + dadesP.nom + "\nPreu: " + dadesP.preu + "\nFormat: " + dadesP.format + "\nCategoria: " + dadesP.categoria + "\nDescripcio: " + dadesP.descripcio + "\nData Caducitat: " + Conv.dateToStr(dadesP.dataCad);

            if (dadesP.format == 2 || dadesP.format == 0) {
                fitxa += "\nDuracio: " + dadesP.duracio;
            }

            if (dadesP.format == 2) {
                fitxa = fitxa + "\nData Inici Emissio:" + Conv.dateToStr(dadesP.iniciEmissio) + " a les " + Conv.getHora(dadesP.iniciEmissio);
            }

            if (dadesP.tematiques != null) {
                fitxa = fitxa + "\nTemes: ";
                for (int i = 0; i <
                        dadesP.tematiques.length; i++) {
                    if (i == 5) {
                        fitxa = fitxa + "\n";
                    }

                    fitxa = fitxa + dadesP.tematiques[i] + " ";
                }

            }

            vSprog.setCuadreFitxa(fitxa);
        }

    }

    private void retrocedirSetmana(boolean generada) {
        try {
            Conv.sumaDies(iniciSetmana, -7);
            Conv.sumaDies(fiSetmana, -7);

            if (!generada) {
                vPlani.setSetmana(Conv.setmana(iniciSetmana, fiSetmana));
            } else {
                vGen.setSetmana(Conv.setmana(iniciSetmana, fiSetmana));
            }
            retrocedirPlanificacio(generada);

        } catch (ParseException ex) {
            Logger.getLogger(kVistaPlanificacio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void avancarSetmana(boolean generada) {
        try {
            Conv.sumaDies(iniciSetmana, +7);
            Conv.sumaDies(fiSetmana, +7);

            if (!generada) {
                vPlani.setSetmana(Conv.setmana(iniciSetmana, fiSetmana));
            } else {
                vGen.setSetmana(Conv.setmana(iniciSetmana, fiSetmana));
            }
            avancarPlanificacio(generada);

        } catch (ParseException ex) {
            Logger.getLogger(kVistaPlanificacio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Filtra una llista de noms de programes, donat un periode, segons si son
     * de format normal, continu o directe.
     * 
     * @pre La llistaNoms implicita no es buida
     * @post A llistaNoms se li eliminen aquells que no es troben dins el periode
     */
    private void filtraPeriode() {
        int mida = llistaProgrames.length;
        tuplaPrograma aux;
        String[] patata = new String[mida];

        if (mida > 0 && nousCriteris != null) {
            int nPatates = 0;
            for (int i = 0; i < mida; i++) {

                aux = CPG.veureFitxa(llistaProgrames[i].toLowerCase());
                if (aux != null) {
                    switch (aux.format) {
                        case 0: //Normal

                            if (Conv.comparacioData(aux.dataCad, nousCriteris.dataIni) >= 0) {
                                patata[nPatates] = llistaProgrames[i];
                                nPatates++;
                            }
                            break;
                        case 1: //Continu
                            if (Conv.comparacioData(aux.dataCad, nousCriteris.dataIni) >= 0) {
                                patata[nPatates] = llistaProgrames[i];
                                nPatates++;
                            }
                            break;
                        case 2: //Directe
                            if ((Conv.comparacioData(aux.iniciEmissio, nousCriteris.dataIni) >= 0) && (Conv.comparacioData(aux.iniciEmissio, nousCriteris.dataFi) < 0) && (Conv.comparacioData(aux.dataCad, nousCriteris.dataIni) == 1)) {
                                patata[nPatates] = llistaProgrames[i];
                                nPatates++;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            llistaProgrames = new String[nPatates];
            for (int k = 0; k < nPatates; k++) {
                llistaProgrames[k] = patata[k];
            }
        }
    }

    private void generarGraella(boolean temporal, int indexPlan) throws ParseException {
        String inici = Conv.dateToStr(iniciSetmana);
        String fi = Conv.dateToStr(fiSetmana);

        if (temporal) {
            graella = CPlani.genSet(inici, fi, vGen.getPlanSelected(), indexPlan, temporal);
        } else {
            graella = CPlani.genSet(inici, fi, vPlani.getPlanSelected(), -1, temporal);
        }
    }

    /** A Partir d'aqui  part de Controlador de vistes de Programes*/
    public void setLlistaFiltre() {
        vSprog.clearFitxa();
        actLlistaFiltres(vSprog.getFClickedInt());
        vSprog.setLlistaFiltres(llistaFiltres);
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
        if (llistaProgrames == null || llistaProgrames.length == 0) {
            llistaProgrames = new String[1];
            llistaProgrames[0] = "";
        }
        if (llistaProgrames[0] != null) {
            filtraPeriode();
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
        filtraPeriode();
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
