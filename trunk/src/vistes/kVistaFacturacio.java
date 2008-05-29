package vistes;

import domini.ControladorFactura;
import java.util.LinkedList;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author rael
 */
public class kVistaFacturacio 
{
    VistaNovaFactura vnf;
    VistaFacturacio vf;
    ControladorFactura cf;
    Object[] datos;
    Object[][] listaPendientes;
    Object[][] listaFacturas;
    static SimpleDateFormat d = new SimpleDateFormat("dd/mm/yyyy");

    /**
     * Constructora la Vista Facturació
     * 
     * @Pre Cert
     * @Post S'inicialitza el controlador de factures.
     */
    public kVistaFacturacio(ControladorFactura cFact) {

        listaPendientes = null;
        listaFacturas = null;
            cf = cFact;
            inicializarVistaFacturacio();
    }

    /**
     * Inicializa el controlador de la vista de factura
     * 
     * @param listaConceptos 
     * 
     *  Llista de conceptes pendents de facturar.
     * 
     * @param inLlistaFacturas 
     * 
     *  Llista de factures.
     * 
     * @pre Cert
     * @post La vista factura conté les llistes de factures 
     * i d'emissions pendents de pagament.
     */
    public void inicializarControladorVistaFactura() 
    {
        cf.setLlServeisPendents();
    }

    /**
     * La finestra vistaFacturació s'inicialitza amb la llista de
     * factures que hem inicialitzat y també s'inicialitzen els 
     * esdeveniments.
     * 
     * @throws Exception 
     * @pre     Cert
     * @post    
     */
    public void inicializarVistaFacturacio() throws Exception {
        vf = new VistaFacturacio();
       
        inicializarListaFacturas();

        ListSelectionListener lista;
        ActionListener acciones[] = new ActionListener[1];
        acciones[0] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "mostrarNuevaFactura");

        lista = (ListSelectionListener) java.beans.EventHandler.create(ListSelectionListener.class, this, "verDesglose");

        vf.setAction(lista, acciones);
    }

    /**
     * La ventana vistaExploradorFacturas se pone visible según el booleano que
     * se le pasa por parametro.
     * 
     * @param b
     *          Visibilidad de la ventana
     *
     * @pre
     *      La vistaFacturacio esta inicialitzada. 
     * @post
     *      Es mostra la vistaFacturacio.
     */
    public void mostrarFacturacio(boolean b) 
    {    
        if (listaFacturas.length != 0) {
            vf.seleccionarFactura(0);
        }
        vf.setVisible(b);
    }

    /**
     * Obté les dades de una factura concreta.
     * 
     * @throws Exception 
     */
    public void verDesglose() throws Exception {
        int i;
        i = vf.getIndexFactura();

        vf.setFechaFactura((String) listaFacturas[i][0]);
        //Agafa la llista d'emissions -> cf.getListaEmisionsFactura(i)??¿
        vf.setListaDesglose(cf.getListaFacturas());
        vf.setTotal(String.valueOf(listaFacturas[i][1]));

    }

    /**
     * Mostra la finestra de Nova Factura
     * 
     * @throws Exception 
     */
    public void mostrarNuevaFactura()
    {
        inicializarVistaNovaFactura();
        vnf.setVisible(true);
    }

    /**
     * Obté la llista de factures del controlador de factures
     * 
     * @throws Exception 
     */
    public void inicializarListaFacturas() 
    {
        listaFacturas = cf.getListaFacturas();
        vf.setListaFacturas(listaFacturas);
    }

    /**
     * Carga la lista de emisiones pendientes del cliente actual
     * @pre Cert
     * @post listaPendientes conté la llista de conceptes del controlador
     * de factures.
     */
    public void inicializarListaPendientes() {
        listaPendientes = cf.getListaServeisP();
    }

    /** 
     * La finestra vistaNovaFactura s'inicialitza amb la llista de
     * factures que hem inicialitzat i també s'inicialitzen els 
     * esdeveniments.
     * 
     * @pre     Cert
     * @post    S'inicialitzen els esdeveniments i les llistes de dades.
     */
    public void inicializarVistaNovaFactura() {
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
     * Obté la llista de conceptes que sumats costen com a máxim
     * l'introduit a la vista del controlador de factures.
     * 
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
     * Obté la llista de conceptes que van ser emessos entre
     * les dates introduides a la vista.
     * 
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
     * Crea una factura de la llista de conceptes seleccionats 
     * i tanca la finestra de Nova Factura.
     * 
     * @throws java.lang.Exception
     */
    public void cobrarFactura()
    {
        int[] LlistaParaFacturar = vnf.getListaParaFacturar();
        int i;

     //   cf.clearConceptesFactura();

        for (i = 0; i < LlistaParaFacturar.length; i++) {
       //     cf.addConcepteFactura(LlistaParaFacturar[i]);
        }

      //  cf.facturaNova();
        finalizaNovaFactura(0);
    }

    /**
     * Tanca la finestra de NovaFactura
     * 
     * @param estado 
     */
    public void finalizaNovaFactura(int estado) {
        vnf.setVisible(false);
        vnf = null;
    }
}
