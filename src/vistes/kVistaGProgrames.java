/**
 * La classe kVistaGProgrames ens proporciona el controlador per les vistes
 * d'afegir programa, modificar programa i de gestio de programes.
 * 
 * @author  Felip Moll 41743858P
 * @version 1.0, 6 Juny 2008 
 * 
 */
package vistes;

import dades.GestorDiscException;
import domini.ControladorProgrames;
import domini.tuplaPrograma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class kVistaGProgrames {

    private ControladorProgrames CPG;
    private VistaGProgrames vGProgs;
    private vAfegirPrograma vADDP,  vMODP;
    private tuplaPrograma dadesPrograma;
    private String[] llistaProgrames;
    private String[] llistaFiltres;
    private SimpleDateFormat formatCalendar;

    public kVistaGProgrames(ControladorProgrames cpgComu) {
        /**Controlador de programes que li pasa el kVistes*/
        CPG = cpgComu;

        /*Incicialitzacio d'atributs privats*/
        llistaProgrames = null;
        llistaFiltres = null;
        dadesPrograma = null;
        formatCalendar = new SimpleDateFormat("dd-MM-yyyy");

        vADDP = new vAfegirPrograma(new javax.swing.JFrame(), true);
        vMODP = new vAfegirPrograma(new javax.swing.JFrame(), true);
        /*Init de vista*/
        initVistaGProgrames();
        initVistaADDP();
        initVistaMODP();
    }

    public VistaGProgrames getVista() {
        return vGProgs;
    }

    public void actualitzaVistaGProgrames() {
        actLlistaFiltres(0);
        actLlistaProgrames("tots", "");
        vGProgs.setLlistaProgrames(llistaProgrames);
        vGProgs.setLlistaFiltres(llistaFiltres);
        vGProgs.clearFitxa();
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
    private void actLlistaProgrames(String tipusFiltre, String valor) {
        llistaProgrames = CPG.getllistaFiltrada(tipusFiltre, null);
    }

    /**
     * Obte una llista de filtres segons el parametre especificat.
     * @param i Val 0 per obtenir una llista de Formats, 1 de Categories,
     * 2 de Tematiques, 3 de nom.
     * @pre -
     * @post S'ha assignat la llistaFiltres una nova llista d'acord amb
     * la opcio seleccionada.
     */
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

    /**Inicialitza una nova vista del gestor de programes*/
    private void initVistaGProgrames() {
        /**Nova vista i nous escoltadors d'events*/
        vGProgs = new VistaGProgrames();
        ListSelectionListener selFiltre, selPrograma;
        ActionListener accions[] = new ActionListener[8];


        /**Setejem les dues llistes que tenim, la de programes i la de filtres
        buidem tambe la fitxa*/
        actLlistaFiltres(0);
        actLlistaProgrames("tots", "");
        vGProgs.setLlistaProgrames(llistaProgrames);
        vGProgs.setLlistaFiltres(llistaFiltres);
        vGProgs.clearFitxa();

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
                try {
                    afegirPrograma();
                    actualitzaLlProgrames();
                    setLlistaFiltre();
                } catch (ParseException ex) {
                    System.out.println("Error in afegirPrograma: " + ex.getMessage());
                }
            }
        });

        accions[5] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                modificarPrograma();
            }
        });
        accions[6] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                eliminarPrograma();
            }
        });
        accions[7] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    guardarTot();
                } catch (GestorDiscException ex) {
                    System.out.println("Error in afegirPrograma: " + ex.getMessage());
                }
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

        vGProgs.setListeners(accions, selFiltre, selPrograma);
    }

    /**Inicialitza una nova vista de la finestra d'afegir programa*/
    private void initVistaADDP() {
        vADDP.setActions(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                addProgramaDeForm();
            }
        });
    }

    /**Inicialitza una nova vista de la finestra de modificar programa*/
    private void initVistaMODP() {
        vMODP.setActions(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                modProgramaDeForm();
            }
        });
    }

    /**Funcions dels action listeners i list listeners*/
    /**
     * Funcio que s'activa quan pitjem el boto d'eliminar programa. Li dona l'ordre
     * al controlador de programes que l'elimini.
     * @pre El nom del programa esta inicialitzat, havent-lo seleccionat a la llista
     * de la vista.
     * @post S'ha eliminat el programa amb el nom que hi ha seleccionat a la vista.
     */
    private void eliminarPrograma() {
        String nomP = vGProgs.getProgramaSelected();
        if (nomP != null || !nomP.equals("")) {
            if (!CPG.eliminaPrograma(nomP.toLowerCase())) {
                System.out.println("Error in eliminarPrograma - kVistaGProgrames L:237");
            } else {
                actualitzaLlProgrames();
            }
        }
        vGProgs.clearFitxa();
    }

    /**
     * Funcio que s'activa quan pitjem el boto de modificar programa. Li dona l'ordre
     * a la finestra de modificar programa perque apareixi.
     * @pre El nom del programa esta inicialitzat, havent-lo seleccionat a la llista
     * de la vista.
     * @post Ha sortit la nova finestra.
     */
    private void modificarPrograma() {
        String nomP = vGProgs.getProgramaSelected();
        tuplaPrograma dadesP = CPG.veureFitxa(nomP.toLowerCase());


        if (dadesP != null) {
            vMODP.setDadesMod(dadesP);
            vMODP.setLocationRelativeTo(vGProgs);
            vMODP.setTitle("Modificar programa");
            vMODP.setVisible(true);
        }
    }

    /**
     * Funcio que s'activa quan pitjem el boto d'acceptacio de que hem acabat
     * de definir un nou programa.
     * @pre El nom del programa esta inicialitzat, aixi com les seves dades.
     * @post S'ha afegit el programa amb les dades passades des de les vistes.
     */
    private void addProgramaDeForm() {
        tuplaPrograma nou = vADDP.getTupla();

        if (nou == null) {
            System.out.println("Dades inexistents o incompletes!");
        } else if (!CPG.afegirPrograma(nou)) {
            JOptionPane.showMessageDialog(null, "El programa existeix.");
        } else {
            System.out.println("El programa ha estat afegit!");
        }
        vADDP.setVisible(false);
    }

    /**
     * Funcio que s'activa quan pitjem el boto d'acceptacio de que hem acabat
     * de modificar un programa.
     * @pre El nom del programa esta inicialitzat, aixi com les seves dades.
     * @post S'ha modificat el programa amb les dades passades des de les vistes.
     */
    private void modProgramaDeForm() {
        tuplaPrograma mod = vMODP.getTupla();

        vMODP.setVisible(false);

        if (mod == null || !CPG.modificarPrograma(mod)) {
            JOptionPane.showMessageDialog(null, "El programa no s'ha modificat.");
        } else {
            System.out.println("El programa ha estat modificat!");
        }

        actualitzaLlProgrames();
        vGProgs.clearFitxa();
    }

    /**Aquesta funcio s'activa quan pitjem els botons de filtres a la vista
     * de seleccio de programes.
     * @pre -
     * @post S'han esborrat les dades de la fitxa del programa de la vista i s'ha
     * actualitzat la llista de filtres amb els nous filtres corresponents al boto pitjat.
     */
    private void setLlistaFiltre() {
        vGProgs.clearFitxa();
        actLlistaFiltres(vGProgs.getFClickedInt());
        vGProgs.setLlistaFiltres(llistaFiltres);
    }

    /**Aquesta funcio s'activa quan pitjem sobre un filtre a la llista de filtres.
     * @pre -
     * @post S'ha actualitzat la llista de programes, posant-hi els que corresponen a
     * la llista filtrada per el tipus de filtre pitjat als botons, i per el valor
     * seleccionat a la llista de filtres.
     */
    private void actualitzaLlProgrames() {
        //System.out.println("Actualitz ll progs!!");
        //Boto pitjat
        String tipusFiltre = vGProgs.getFClickedStr();


        //Agafa valor des filtre
        String valorFiltre;

        if (tipusFiltre.equalsIgnoreCase("tematica") == true || tipusFiltre.equalsIgnoreCase("nom") == true) {
            valorFiltre = vGProgs.getFiltreSelected(true).toLowerCase();
        } else {
            valorFiltre = vGProgs.getFiltreSelected(false);
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

        vGProgs.setLlistaProgrames(llistaProgrames);
    }

    /** Aquesta funcio es crida quan es selecciona un programa de la llista de programes
     *  i serveix per actualitzar la fitxa.
     *  @pre El programa seleccionat existeix al repositori de programes
     *  @post S'ha setejat la fitxa de la vista
     */
    private void seleccionatPrograma() {
        //Agafem nom del programa seleccionat
        String nomP = vGProgs.getProgramaSelected();

        //Agafem fitxa del programa seleccionat
        //i actualizem la fitxa
        ////// FALTA INICIEMISSIO i DATA CADUCITATTTTTTTTT
        tuplaPrograma dadesP = CPG.veureFitxa(nomP.toLowerCase());
        if (dadesP != null) {
            String fitxa = "Nom: " + dadesP.nom + "\nPreu: " + dadesP.preu + "\nFormat: " + dadesP.format + "\nCategoria: " + dadesP.categoria + "\nDescripcio: " + dadesP.descripcio + "\nData Caducitat: " + dadesP.dataCad.get(Calendar.DATE) + "/" + (dadesP.dataCad.get(Calendar.MONTH) + 1) +
                    "/" + dadesP.dataCad.get(Calendar.YEAR);

            if (dadesP.format == 2 || dadesP.format == 0) {
                fitxa += "\nDuracio: " + dadesP.duracio;
            }

            if (dadesP.format == 2) {
                fitxa = fitxa + "\nData Inici Emissio:" + dadesP.iniciEmissio.get(Calendar.DATE) + "/" + (dadesP.iniciEmissio.get(Calendar.MONTH) + 1) +
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

            vGProgs.setCuadreFitxa(fitxa);
        }
    }

    /** Boto de "Afegir Programa", fa sortir la finestra*/
    private void afegirPrograma() throws ParseException {
        vADDP.setLocationRelativeTo(vGProgs);
        vADDP.setTitle("Afegir nou Programa");
        vADDP.setVisible(true);
    }

    /** Boto de "Guardar tot", guarda a disc totes les dades que fan referencia
     * al gestor de programes.
     * @pre -
     * @post S'ha guardat a disc amb els noms RepositoriTemes.db, RepositoriFranges.db
     * RepositoriProgrames.db, les corresponents bases de dades.
     * @throws dades.GestorDiscException
     */
    private void guardarTot() throws GestorDiscException 
    {
        CPG.saveGclientsAll();
    }
}
