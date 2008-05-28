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
    vAfegirPrograma vADDP, vMODP;
    private tuplaPrograma dadesPrograma;
    private String[] llistaProgrames;
    private String[] llistaFiltres;
    SimpleDateFormat formatCalendar;

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

    public void initVistaGProgrames() {
        /**Nova vista i nous escoltadors d'events*/
        vGProgs = new VistaGProgrames();
        ListSelectionListener selFiltre, selPrograma;
        ActionListener accions[] = new ActionListener[9];


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
        accions[8] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                sortir();
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
        /**
        accions[0] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "setLlistaFiltre");
        accions[1] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "setLlistaFiltre");
        accions[2] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "setLlistaFiltre");
        accions[3] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "setLlistaFiltre");
        accions[4] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "afegirPrograma");
        accions[5] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "eliminarPrograma");
        accions[6] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "modificarPrograma");
        accions[7] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "guardarTot");
        accions[8] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "sortir");
        selFiltre = (ListSelectionListener) java.beans.EventHandler.create(ListSelectionListener.class, this, "actualitzaLlProgrames");
        selPrograma = (ListSelectionListener) java.beans.EventHandler.create(ListSelectionListener.class, this, "seleccionatPrograma");
         */
        vGProgs.setListeners(accions, selFiltre, selPrograma);
    }

    private void initVistaADDP() {
        //  vADDP.setActions((ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "addProgramaDeForm"));
        vADDP.setActions(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                addProgramaDeForm();
            }
        });
    }

    private void initVistaMODP() {
        vMODP.setActions(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                modProgramaDeForm();
            }
        });
    }

    /** Funcions dels action listeners i list listeners*/
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

    private void modificarPrograma() {
        String nomP = vGProgs.getProgramaSelected();
        tuplaPrograma dadesP = CPG.veureFitxa(nomP.toLowerCase());


        if (dadesP != null) {
            vMODP.setDadesMod(dadesP);
            vMODP.setVisible(true);
        }
    }

    private void sortir() {
        System.out.println("sortir-programa");
    }

    public void addProgramaDeForm() {
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

    public void modProgramaDeForm() {
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

    public void setLlistaFiltre() {
        vGProgs.clearFitxa();
        actLlistaFiltres(vGProgs.getFClickedInt());
        vGProgs.setLlistaFiltres(llistaFiltres);
    }

    public void actualitzaLlProgrames() {
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

    public void seleccionatPrograma() {
        //Agafem nom del programa seleccionat
        String nomP = vGProgs.getProgramaSelected();

        //Agafem fitxa del programa seleccionat
        //i actualizem la fitxa
        ////// FALTA INICIEMISSIO i DATA CADUCITATTTTTTTTT
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

            vGProgs.setCuadreFitxa(fitxa);
        }
    }

    public void afegirPrograma() throws ParseException {
        // vADDP = new AfegirPrograma(new javax.swing.JFrame(), true);
        // initVistaADDP();
        vADDP.setLocationRelativeTo(vGProgs);
        vADDP.setTitle("Afegir nou Programa");
        vADDP.setVisible(true);
    }

    public void guardarTot() throws GestorDiscException {
        System.out.println("All saved!!!");
        CPG.saveGclientsAll();
    }
}
