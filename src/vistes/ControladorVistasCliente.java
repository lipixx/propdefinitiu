/*
 * ControladorVistasCliente.java
 *
 * Created on 24 de abril de 2008, 10:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package vistes;

import domini.ControladorCliente;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.beans.EventHandler;
import java.util.Calendar;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Francisco Javier Rojas (e6464692)
 */
public class ControladorVistasCliente {

    protected ControladorCliente CDC;
    protected VistaGestionaCliente VGC;
    protected EventHandler EVTH;
    protected Object[][] listaClientes;

    /** Creates a new instance of ControladorVistasCliente */
    public ControladorVistasCliente(ControladorCliente contrClient) {
        boolean error = false;
        listaClientes = null;
        try {
            CDC = contrClient;
        } catch (Exception e) {
            VistaGestionaCliente.alerta("No se pudieron cargar los datos del disco:\n" + e.getMessage());
            error = true;
        }
        if (!error) {
            actListaCliente();
            initVistasCliente();
        }
    }

    public void gestionaCliente() {
        VGC.setTitle("Gestió Clients - PropTV 1.0b");
        VGC.setLocation(400, 70);
        VGC.setVisible(true);
    }

    public void consultaCliente(int index) {
        String auxID = VGC.getIDFilaSeleccionada();
        if (auxID != null) {
            Object[] auxData = CDC.consultaCliente(auxID);
            //<null> -> no existe
            //[0]->ID(String)
            //[1]->Nombre(String)
            //[2]->Apellido(String)
            //[3]->FNacimiento(Calendar)
            //[4]->AutoFact(Boolean)
            //[5]->IntervalFact(Integer)
            //[6]->UltFact(Calendar)
            VGC.setDatosConsulta((String) auxData[0], (String) auxData[1], (String) auxData[2], (Calendar) auxData[3], (Boolean) auxData[4], (Integer) auxData[5]);
        }
    }

    public void altaCliente() {
        Object[] aux = VGC.getDatosAgregar();
        if (aux != null){
        CDC.altaCliente((String)aux[0],(String) aux[1],(String) aux[2],(Calendar) aux[3],(Boolean) aux[4],(Integer) aux[5]);
        actListaCliente();
        VGC.clearDatosConsulta();
        VGC.setListado(this.listaClientes);
        }
    }

    public void bajaCliente() {
        if (VistaGestionaCliente.confirmacion("¿Está seguro que desea borrar los datos del cliente?")) {
            CDC.bajaCliente(VGC.getIDFilaSeleccionada());
            actListaCliente();
            VGC.clearDatosConsulta();
            VGC.setListado(this.listaClientes);
        }
    }

    public void modificaCliente() {
        Object[] aux = VGC.getDatosModificar();
        CDC.modificarCliente(VGC.getIDFilaSeleccionada(),(String) aux[0],(String) aux[1],(Calendar) aux[2],(Boolean) aux[3],(Integer) aux[4]);
        actListaCliente();
        VGC.clearDatosConsulta();
        VGC.setListado(this.listaClientes);

    }
    /*
    public void cargaCliente() {
    try {
    CDC = new ControladorDominioCliente();
    } catch (Exception e) {
    VistaGestionaCliente.alerta("No se pudieron cargar los datos del disco");
    }
    actListaCliente();
    VGC.clearDatosConsulta();
    VGC.actListado(this.listaClientes);
    }
     */

    public void guardaCliente() {
        try {
            CDC.actualizarClientes();
        } catch (Exception e) {
            VistaGestionaCliente.alerta("No se pudieron guardar los datos del disco");
        }
        actListaCliente();
    }

    public void finalizaGestion(int status) {
        finalizaGestion();
    }

    public void finalizaGestion() {
        boolean error = false;
        switch (VistaGestionaCliente.pregunta("¿Desea guardar antes de salir?")) {
            case -1:
                break;
            case 1:
                try {
                    CDC.actualizarClientes();
                } catch (Exception e) {
                    VistaGestionaCliente.alerta("No se pudieron guardar los datos del disco:\n" + e.getMessage());
                    error = true;
                }
                if (!error) {
                    VGC.setVisible(false);
                    VGC.dispose();
                }
                break;
            case 0:
                VGC.setVisible(false);
                VGC.dispose();
            default:
                break;
        }
    }

    protected void initVistasCliente() {
        VGC = new VistaGestionaCliente();
        ActionListener a[] = new ActionListener[5];
        ListSelectionListener LSL;
        WindowListener WL;
        a[0] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "modificaCliente");
        a[1] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "altaCliente");
        a[2] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "bajaCliente");
        //a[3] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "cargaCliente");
        a[3] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "guardaCliente");
        a[4] = (ActionListener) java.beans.EventHandler.create(ActionListener.class, this, "finalizaGestion");
        LSL = (ListSelectionListener) java.beans.EventHandler.create(ListSelectionListener.class, this, "consultaCliente", "FirstIndex", "valueChanged");
        WL = (WindowListener) java.beans.EventHandler.create(WindowListener.class, this, "finalizaGestion", "NewState", "windowClosing");
        VGC.setAction(a, LSL, WL);
        VGC.setListado(listaClientes);
        VGC.clearDatosConsulta();

    }

    protected void actListaCliente() {
        Object[] IDListAux = CDC.listarId();
        listaClientes = new Object[IDListAux.length][3];
        Object[] auxData;
        for (int i = 0; i < IDListAux.length; i++) {
            auxData = CDC.consultaCliente((String) IDListAux[i]);
            listaClientes[i][0] = auxData[0];
            listaClientes[i][1] = auxData[1];
            listaClientes[i][2] = auxData[2];
        }
    }
}
