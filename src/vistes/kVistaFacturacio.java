/**
 * 
 * @author Rael Garcia Arnés    47808932M
 */
package vistes;

import domini.ControladorFactura;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author rael
 */
public class kVistaFacturacio {

    VistaNovaFactura vnf;
    VistaFacturacio vf;
    ControladorFactura cf;
    //ControladorDomini cd;
    Object[] datos;

    /**
     * Constructora de Vista Facturacion
     * 
     */
    public kVistaFacturacio(ControladorFactura CFactura) {


        try {
            cf = CFactura;
            inicializarVistaExploradorFactura();
            inicializarVistaNuevaFactura();
        } catch (Exception e) {
            if (vnf.isVisible()) {
                vnf.aviso("No se pudieron cargar los datos del disco:\n" + e.getMessage());
            } else if (vf.isVisible()) {
                vf.aviso("No se pudieron cargar los datos del disco:\n" + e.getMessage());
            }
        }
    }

    //Retorna la vista principal de la facturacio
    public VistaFacturacio getVistaFact() {
        return vf;
    }

    /**
     * Inicializa el controlador de la vista de factura
     * 
     * @param inCF
     * 
     * Lista de conceptos pendientes de facturar
     * 
     */
    public void inicializarControladorVistaFactura(ControladorFactura inCF) {

        cf = inCF;

    }

    /**
     * La ventana vistaFacturacion se inicializa con la lista
     * de facturas del cliente actual y tambien se inicializan los eventos
     * 
     * @throws Exception 
     */
    public void inicializarVistaExploradorFactura() throws Exception {
        vf = new VistaFacturacio();
        inicializarListaFacturas();

        ListSelectionListener lista;
        ActionListener accion;

        accion = new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    abrirNuevaFactura();
                } catch (Exception ex) {
                    Logger.getLogger(kVistaFacturacio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };



        lista = (ListSelectionListener) java.beans.EventHandler.create(ListSelectionListener.class, this, "verDesglose");

        vf.setAction(lista, accion);
    }

    public void actualitzarVFacturacio() throws Exception {
        inicializarListaFacturas();
    }

    /**
     * La ventana vistaExploradorFacturas se pone visible según el booleano que
     * se le pasa por parametro.
     * 
     * @param b
     *          Visibilidad de la ventana
     */
    public void mostrarExploradorFacturas(boolean b) throws Exception {
        vf.setVisible(b);
        if (cf.getListaFacturas().length != 0) {
            vf.seleccionarFactura(0);
        }
    }

    /**
     * Carga la lista conceptos de la factura seleccionada
     * 
     * @throws Exception 
     */
    public void verDesglose() throws Exception {
        int i;
        i = vf.getIndexFactura();

        if (i >= 0) {
            vf.setFechaFactura((String) cf.getListaFacturas()[i][0]);
            vf.setListaDesglose(cf.getListaEmisionsFactura(i));
            vf.setTotal(String.valueOf(cf.getListaFacturas()[i][1]));
        } else {
            vf.setFechaFactura("");
            vf.setListaDesglose(null);
            vf.setTotal("");
        }

    }

    /**
     * Muestra la ventana de creación de una nueva factura
     * 
     * @throws Exception 
     */
    public void abrirNuevaFactura() throws Exception {
        inicializarVistaNuevaFactura();
        mostrarNuevaFactura(true);
    }

    /**
     * Cierra la ventana de ExploradorFacturas
     * @param estado 
     */
    public void finalizaFacturacion(int estado) {
        vf.setVisible(false);
        vf = null;
    }

    /**
     * Carga la lista de facturas del cliente actual
     * 
     * @throws Exception 
     */
    public void inicializarListaFacturas() throws Exception {

        vf.setListaFacturas(cf.getListaFacturas());
    }

    /*
    Vista Nueva Factura 
    /** 
     * Inicializa la ventana vistaNovaFactura
     * 
     */
    public void inicializarVistaNuevaFactura() {
        vnf = new VistaNovaFactura();
        cf.setListaConceptos();
        vnf.setListaPendientes(cf.getListaConceptos());

        ActionListener acciones[] = new ActionListener[3];
        WindowListener ventana;
        acciones[0] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "autofacturaPreu");
        acciones[1] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "autofacturaPeriode");
        acciones[2] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "cobrarFactura");
        ventana = (WindowListener) java.beans.EventHandler.create(WindowListener.class, this, "finalizaNuevaFactura", "NewState", "windowClosing");
        vnf.setAction(acciones, ventana);
    }

    /**
     * La ventana vistaNovaFactura se pone visible según el booleano que
     * se le pasa por parametro.
     * 
     * @param b
     *          Visibilidad de la ventana
     */
    public void mostrarNuevaFactura(boolean b) {
        vnf.setVisible(b);
    }

    /**
     * AutoFactura Preu
     * @throws java.lang.Exception
     */
    public void autofacturaPreu() throws Exception {
        String p = "";
        boolean b = true;
        int i = 0;

        p = vnf.getAutofacturaPreuMax();
        char[] c = new char[p.length()];
        c = p.toCharArray();

        for (i = 0; i < p.length(); i++) {
            if (!(Character.isDigit(c[i]))) {
                b = false;
                i = p.length();
            }
        }

        if (b) {
            vnf.setListaSeleccionados(cf.autofacturaPreumax(Float.parseFloat(p)));
        } else {
            vnf.aviso("El formato del precio no es correcto.");
        }
    }

    /**
     * AutoFactura Data
     * @throws java.lang.Exception
     */
    public void autofacturaPeriode() throws Exception 
    {
        vnf.setListaSeleccionados(cf.autofacturaPeriode(vnf.getAutofacturaPeriodeInici(), vnf.getAutofacturaPeriodeFi()));
    }

    /**
     * Cobrar la factura
     * @throws java.lang.Exception
     */
    public void cobrarFactura() throws Exception {
        int[] LlistaParaFacturar = vnf.getListaParaFacturar();
        int i;

        if (LlistaParaFacturar.length != 0) {
            cf.clearConceptesFactura();

            for (i = 0; i < LlistaParaFacturar.length; i++) {
                cf.addConcepteFactura(LlistaParaFacturar[i]);
            }

            cf.facturaNova();
            vf.bSync.doClick();
        }

        finalizaNuevaFactura(0);
    }

    /**
     * Cierra la ventana de NuevaFactura
     * @param estado 
     */
    public void finalizaNuevaFactura(int estado) {
        vnf.setVisible(false);
        vnf = null;
    }
    /**
     * 
     * 
     * Explorador de facturas
     * 
     * 
     * @throws Exception 
     */
}
