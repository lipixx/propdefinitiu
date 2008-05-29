/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes;

import dades.GestorDiscException;
import domini.ControladorCliente;
import domini.ControladorDomini;
import domini.ControladorProgrames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

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
    
    VistaPrincipal vPrincipal;
   
    public kVistes() throws Exception {

        /**Inits dels Controladors i Vistes*/
        CD = new ControladorDomini();
        CPG = CD.getCProgs();
        CCliente = CD.getCClient();
        
        /**Controladors de les vistes (han de tenir els controladors de domini que necessitin*/
        kvGProgs = new kVistaGProgrames(CPG);
        kvFranges = new kVistaFranges(CPG);
        kvCliente = new ControladorVistasCliente(CCliente);
  
        /**Vista principal (ha de tenir totes les vistes*/
        vPrincipal = new VistaPrincipal(kvGProgs.getVista(),kvFranges.getVistaFranges());
        initVistaPrincipal();

    }

    public void mostraVPrincipal() {
        //Dimension tamPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        vPrincipal.setTitle("PropTV 1.0b");
        vPrincipal.setLocation(150, 40);
        vPrincipal.setVisible(true);
    }

    public void initVistaPrincipal() {
        /**Nous escoltadors d'events*/
        ActionListener accions[] = new ActionListener[5];


        /**Init de nous listeners*/
        /** EventHandle.create(Qui fa sa crida, Classe a sa que es cerca sa funcio, nom de sa funcio)*/
        /**Guardar tot: Guarda tots els repositoris a disc*/
        accions[0] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                   if (CPG.saveGclientsAll())
                    JOptionPane.showMessageDialog(null, "Fet!");
                   else
                    JOptionPane.showMessageDialog(null, "Error.");
                
            }
        });

        /**Importa repositori de programes*/
        accions[1] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    //Treure un dialeg per seleccionar fitxers de tipus .db
                    //El nom obtingut sera rutaFitxer
                    String rutaFitxer = "RepositoriProgramesEXPIMP.db";
                    CPG.importarLlistaProgrames(rutaFitxer);
                    JOptionPane.showMessageDialog(null, "Fet!");
                } catch (GestorDiscException ex) {
                    JOptionPane.showMessageDialog(null, "Error." + ex.getMessage());
                }
            }
        });

        /*Neteja repositori de programes*/
        accions[2] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                CPG.netejaLlistaProgrames();
                JOptionPane.showMessageDialog(null, "Fet!");
            }
        });

        /*Exporta repositori de programes*/
        accions[3] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    //Treure un dialeg per seleccionar fitxers de tipus .db
                    //El nom obtingut sera rutaFitxer
                    String rutaFitxer = "RepositoriProgramesEXPIMP.db";
                    CPG.exportarLlProgrames(rutaFitxer);
                    JOptionPane.showMessageDialog(null, "Fet!");
                } catch (GestorDiscException ex) {
                    JOptionPane.showMessageDialog(null, "Error." + ex.getMessage());
                }
            }
        });
        
                /*Engega la Gestio de Clients*/
        accions[4] = (new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                  kvCliente.gestionaCliente();
            }
        });

        /** Falten:
         *  Eliminar BBDD completa
         *  Tot lo de franges
         *  tot lo de planificacio
         */
        vPrincipal.setActions(accions);
    }
}
