/**
 * 
 * @author Rael Garcia Arnés    47808932M
 */
package vistes;

import domini.ControladorFactura;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    Object[][] listaPendientes;
    Object[][] listaFacturas;

    
   static SimpleDateFormat d = new SimpleDateFormat("dd/mm/yyyy");
    /**
     * Constructora de Vista Facturacion
     * 
     */
    public kVistaFacturacio(ControladorFactura CFactura) {

        listaPendientes = null;

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
                mostrarNuevaFactura(true);
            }
        };
        
        lista = (ListSelectionListener) java.beans.EventHandler.create(ListSelectionListener.class, this, "verDesglose");
        
        vf.setAction(lista, accion);
    }

    /**
     * La ventana vistaExploradorFacturas se pone visible según el booleano que
     * se le pasa por parametro.
     * 
     * @param b
     *          Visibilidad de la ventana
     */
    public void mostrarExploradorFacturas(boolean b) {
        vf.setVisible(b);
        if (listaFacturas.length != 0) {
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

        vf.setFechaFactura((String) listaFacturas[i][0]);
        vf.setListaDesglose(cf.getListaEmisionsFactura(i));
        vf.setTotal(String.valueOf(listaFacturas[i][1]));

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

        listaFacturas = cf.getListaFacturas();
        vf.setListaFacturas(listaFacturas);

    }

    /*
    
    
    Vista Nueva Factura 
    
    
     */
    /**
     * Carga la lista de emisiones pendientes del cliente actual
     * 
     */
    public void inicializarListaPendientes() {
        listaPendientes = cf.getListaConceptos();
    }

    /** 
     * Inicializa la ventana vistaNovaFactura
     * 
     */
    public void inicializarVistaNuevaFactura() {
        vnf = new VistaNovaFactura();
        inicializarListaPendientes();
        vnf.setListaPendientes(listaPendientes);

        ActionListener acciones[] = new ActionListener[3];
        WindowListener ventana;
        acciones[0] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "autofacturaPreu");
        acciones[1] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "autofacturaPeriode");
        acciones[2] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "cobrarFactura");
        ventana = (WindowListener) java.beans.EventHandler.create(WindowListener.class, this, "finalizaNuevaFactura", "NewState", "windowClosing");
        vnf.setAction(acciones, ventana);
        vnf.setListaPendientes(listaPendientes);
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

    public void autofacturaPeriode() throws Exception {
        Calendar cali = Calendar.getInstance();
        Calendar calf = Calendar.getInstance();
        
        cali = vnf.getAutofacturaPeriodeInici();
        calf = vnf.getAutofacturaPeriodeFi();
      
        
        vnf.setListaSeleccionados(cf.autofacturaPeriode(cali, calf));
    }

    /**
     * Cobrar la factura
     * @throws java.lang.Exception
     */
    public void cobrarFactura() throws Exception {
        int[] LlistaParaFacturar = vnf.getListaParaFacturar();
        int i;
        
        cf.clearConceptesFactura();
        
        for(i=0;i<LlistaParaFacturar.length;i++){
            cf.addConcepteFactura(LlistaParaFacturar[i]);
        }
        
        cf.facturaNova();
        finalizaNuevaFactura(0);
        vf.bSync.doClick();
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