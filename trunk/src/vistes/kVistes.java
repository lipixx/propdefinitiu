/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes;

import dades.GestorDiscException;
import domini.ControladorCliente;
import domini.ControladorDomini;
import domini.ControladorFactura;
import domini.ControladorPlanificacio;
import domini.ControladorProgrames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author lipi
 */


public class kVistes {

    /** Tindra:
     * - Controladors de domini
     * - Controladors de vistes
     * - VistaPrincipal
     */
    ControladorDomini CD;
    ControladorProgrames CPG;
    ControladorCliente CCliente;
    ControladorVistasCliente kvCliente;
    kVistaGProgrames kvGProgs;
    kVistaFranges kvFranges;
    
    //Falten
    ControladorPlanificacio CPlani;
    ControladorFactura CFactura;
    
    kVistaPlanificacio kvPlan;
    kVistaFacturacio kvFact;
    //Fi Falten
    
    /**Les dues pestanyes que te incloses*/
    VistaPrincipal vPrincipal;
    
    /**Necessari per generar la llista de clients*/
    public Object[][] listaClientes;

    public kVistes() throws Exception {

        /**Inits dels Controladors i Vistes*/
        CD = new ControladorDomini();
        CPG = CD.getCProgs();
        CCliente = CD.getCClient();
        CPlani = CD.getCPlan();
        CFactura = CD.getCFactura();
        
        /**Controladors de les vistes (han de tenir els controladors de domini que necessitin*/
        kvGProgs = new kVistaGProgrames(CPG);
        kvFranges = new kVistaFranges(CPG);
        kvCliente = new ControladorVistasCliente(CCliente);
        kvPlan = new kVistaPlanificacio(CPlani);
        kvFact = new kVistaFacturacio(CFactura);

        /**Vista principal: Pestanyes que te la vista principal, a mes de les seves*/
        vPrincipal = new VistaPrincipal(kvGProgs.getVista(), kvFranges.getVistaFranges(), kvPlan.getVistaPlan(), kvFact.getVistaFact());
        initListaCliente();
        initVistaPrincipal();
    }

    /**Mostra la pantalla gran del programa*/
    public void mostraVPrincipal() {
        //Dimension tamPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        vPrincipal.setTitle("PropTV 1.0b");
        vPrincipal.setLocation(150, 40);
        vPrincipal.setVisible(true);
    }

    public void initVistaPrincipal() {
        /**Nous escoltadors d'events*/
        ActionListener accions[] = new ActionListener[10];


        /**Init de nous listeners*/
        /**Menu: Guardar tot: Guarda tots els repositoris a disc*/
        accions[0] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    //Guardem el client a MP i a disc:
                    CD.guardaClientActual();
                    CCliente.actualizarClientes();

                    if (CPG.saveGclientsAll()) {
                        JOptionPane.showMessageDialog(null, "S'ha guardat tot al disc!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error.");
                    }
                } catch (GestorDiscException ex) {
                    System.out.println("Warning! - Pot ser que no hi haguessin dades!");
                }
            }
        });

        /**Menu: Importa repositori de programes*/
        accions[1] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    //Treure un dialeg per seleccionar fitxers de tipus .db
                    //El nom obtingut sera rutaFitxer
                    String rutaFitxer = (String) JOptionPane.showInputDialog(
                            null,
                            "Introdueix el nom del fitxer a carregar:\n",
                            "Importa Repositori de Programes",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            "RepositoriProgramesEXPIMP.db");

                    CPG.importarLlistaProgrames(rutaFitxer);
                    kvGProgs.actualitzaVistaGProgrames();

                    JOptionPane.showMessageDialog(null, "S'ha importat: " + rutaFitxer + " !");
                } catch (GestorDiscException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        /*Menu: Neteja repositori de programes*/
        accions[2] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                CPG.netejaLlistaProgrames();
                JOptionPane.showMessageDialog(null, "Esborrat de disc correctament!");
            }
        });

        /*Menu: Exporta repositori de programes*/
        accions[3] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    String rutaFitxer = (String) JOptionPane.showInputDialog(
                            null,
                            "Introdueix el nom del fitxer:\n",
                            "Exporta el Repositori de Programes",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            "RepositoriProgramesEXPIMP.db");

                    CPG.exportarLlProgrames(rutaFitxer);
                    JOptionPane.showMessageDialog(null, "S'ha guardat el repositori de programes" +
                            "al fitxer " + rutaFitxer);

                } catch (GestorDiscException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        /*Boto: Engega la Gestio de Clients*/
        accions[4] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                kvCliente.gestionaCliente();
            }
        });

        /*Boto: Guarda i surt*/
        accions[5] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    CD.guardaClientActual();
                    CCliente.actualizarClientes();
                    CPG.saveGclientsAll();
                    System.exit(0);
                } catch (GestorDiscException ex) {
                    System.out.println("Warning! guardant tot - pot ser que no existisin dades.");
                }
            }
        });

        /*Menu: expFranges*/
        accions[6] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String rutaFitxer = (String) JOptionPane.showInputDialog(
                        null,
                        "Introdueix el nom del fitxer:\n",
                        "Exporta el Repositori de Franges Horaries",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "RepositoriFrangesEXPIMP.db");

                CPG.exportaFranges(rutaFitxer);
                JOptionPane.showMessageDialog(null, "S'ha guardat el repositori de franges" +
                        "al fitxer " + rutaFitxer);
            }
        });

        /*Menu: impFranges*/
        accions[7] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String rutaFitxer = (String) JOptionPane.showInputDialog(
                        null,
                        "Introdueix el nom del fitxer:\n",
                        "Importa el Repositori de Franges Horaries",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "RepositoriFrangesEXPIMP.db");

                CPG.impFranges(rutaFitxer);
                kvFranges.resetFranges();
                JOptionPane.showMessageDialog(null, "S'ha carregat el repositori de franges" +
                        "del fitxer " + rutaFitxer);
            }
        });

        /*Menu: Esborra franges disc*/
        accions[8] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                CPG.esborraFranges();
                kvFranges.resetFranges();
                JOptionPane.showMessageDialog(null, "S'han esborrat les franges en memoria");
            }
        });
        
                /*Act llista clients MenuPrincipal:*/
        accions[9] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                 //Despres de tornar refresquem la llista nostra
                initListaCliente();
                vPrincipal.setLlistaClients(listaClientes);
            }
        });

        /*Seleccio del client actual*/
        ListSelectionListener LSL = new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                //Guardar client Actual?

                String selectedID = vPrincipal.getSelectedID();
                if (selectedID != null) {
                    if (CD.clientSetted())
                    {
                   /** Object[] options = {"Si", "No"};
                    int n = JOptionPane.showOptionDialog(null,
                            "Vols guardar totes les modificacions del client actual seleccionat?",
                            "Guardar les modificacions",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    if (n == 0) {
                        CD.guardaClientActual();
                    }*/
                        CD.guardaClientActual();
                    }
                    String informacio[] = CD.setClientActual(selectedID);

                    if (informacio != null) {
                        /*Ja tenim setejat el nou client a CD i als respectius controladors
                        de domini, per tant ara nomes falta actualitzar les vistes que
                        faixin servir el clientActual de CD*/

//          kVistaFactura.actualitzaVista() 
//          kVistaPlanificacio.actualizaVista();

                        /*Setejem la fitxa i les fact pendents de la finestra principal*/
                        vPrincipal.setFitxa(informacio[0]);
                        vPrincipal.setFacturesPendents(informacio[1]);
                    }

                }
            }
        };

        vPrincipal.setActions(accions, LSL);
        vPrincipal.setLlistaClients(listaClientes);
    }

    private void initListaCliente() {
        Object[] IDListAux = CCliente.listarId();
        listaClientes = new Object[IDListAux.length][3];
        Object[] auxData;
        for (int i = 0; i < IDListAux.length; i++) {
            auxData = CCliente.consultaCliente((String) IDListAux[i]);
            listaClientes[i][0] = auxData[0];
            listaClientes[i][1] = auxData[1];
            listaClientes[i][2] = auxData[2];
        }
    }
}
