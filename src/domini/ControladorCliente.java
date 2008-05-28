package domini;

import dades.Controlador;
import dades.GestorDiscException;
import java.util.Calendar;
import java.util.LinkedList;

/** Controlador de la capa de dominio de Cliente
/**
 * @author: Sandra Crespo Calvo
 * @Dni: 46984873J
 * @Grupo: 
 */
public class ControladorCliente {

    private Controlador<String, Cliente> c;

    /*
     * Constructora de ControladorDominioCliente 
     * * {Pre:-}
     */
    public ControladorCliente() throws GestorDiscException {
        Cliente cx = new Cliente();
        String clase = cx.getClass().getName();
        c = new Controlador<String, Cliente>(clase,"ListaClientes.txt");
    }
    /* {Post: se ha inicializado Controlador para String y Cliente}*/

    /*
     * Se da de alta un nuevo cliente con los parametros dados.
     * {Pre: id, nombre, apellidos, fechaNacimiento, facturacion, intFact.
    ID es el identificador por el cual podremos identificar al Cliente}*/
    public boolean altaCliente(String id, String nombre, String apellidos, Calendar fechaNacimiento, boolean facturacion, int intFact) {

        Cliente cx = new Cliente(id, nombre, apellidos, fechaNacimiento, facturacion, intFact);
        return c.afegirElement(cx);
    }
    /* {Post: devuelve false cuando queremos dar de alta a un cliente que ya ha 
     *        sido dado de alta.
     *        devuelve true cuando se ha dado de alta un cliente de manera 
     *        satisfactoria}*/

    /*
     * Se modifican los datos de un  Cliente con los parametros dados.
     * {Pre: ID, nombre, apellidos, fechaNacimiento, facturado,intFact su formato es el correcto.
     *       id es el identificador por el cual podremos identificar al Cliente}
     */
    public boolean modificarCliente(String id, String nombre, String apellidos, Calendar fechaNacimiento, boolean facturado, int intFact) {
        Cliente caux = new Cliente();
        if (c.obteElement(id, caux)) {
            caux.setNombre(nombre);
            caux.setApellidos(apellidos);
            caux.setFechaNacimiento(fechaNacimiento);
            caux.setAutoFact(facturado);
            caux.setIntervaloFactura(intFact);
            return c.modificaElement(caux);
        }
        return false;
    }
    /* {Post: -devuelve false cuando queremos modificar los datos de un cliente, 
     *        que no ha sido dado de alta.
     *        -devuelve true cuando se han modificado los datos un cliente de 
     *        manera satisfactoria}*/

    
    /*
     * Se da de baja un Cliente dado su ID que lo identifica
     * {Pre: id es el identificador por el cual podremos identificar al Cliente}
     */
    public boolean bajaCliente(String id) {
        Cliente cx = new Cliente();
        if (c.obteElement(id, cx)) {
            return c.esborraElement(cx);
        } else {
            return false;
        }
    }
    /*{Post:-devuelve false cuando el cliente que se quiere dar de baja no existe
     *      -devuelve true cuando el ciente se ha dado de baja de manera 
     *      satisfactoria}
     */

    /*
     * Se consulta los datos de un cliente, dando su id que lo indentifica
     * {Pre: id es el identificador por el cual podremos identificar al Cliente}
     */
    public Object[] consultaCliente(String id) {
        Cliente cx = new Cliente();
        Object[] cDev = new Object[7];
        if (c.obteElement(id, cx)) {
            cDev[0] = cx.getId();
            cDev[1] = cx.getNombre();
            cDev[2] = cx.getApellidos();
            cDev[3] = cx.getFechaNacimiento();
            cDev[4] = cx.getAutoFact();
            cDev[5] = cx.getIntervaloFactura();
            cDev[6] = cx.getUltimaFactura();

        } else {
            cDev = null;
        }
        return cDev;
    }
    /*{Post: cDev contine los datos del Cliente que se identifica por el ID introducido
     *       - si cDev esta vacia, indica que el cliente que se quieren consultar los datos
     *         no esta dado de alta
     *       - si cDev no esta vacia indica que el Cliente esta dado de alta y devuelve los parametros}*/

    /*
     * Se listan los datos de todos los clientes dados de alta.
     * {Pre: -}
     */
    public Object[] listarClientes() {
        int i = 0;
        Object[] listClientes;
        LinkedList<Cliente> clientes = c.listaObject();
        if (clientes != null) {
            listClientes = new Object[clientes.size()];
            for (Cliente cx : clientes) {
                listClientes[i] = consultaCliente(cx.getId());
                i++;
            }
            return listClientes;
        } else {
            listClientes = null;
        }
        return listClientes;
    }
    /*{Post: listClientes contine los datos de todos los Clientes dados de alta
     *       - si listClientes esta vacia, indica que no hay clientes dados de alta.
     *       - si listClientes no esta vacia contiene los datos de todos los clientes}*/


    /*
     * Se listan los identificadores de todos los clientes dados de alta.
     * {Pre: -}
     */
    public Object[] listarId() {
        int i = 0;
        Object[] listId;
        LinkedList<Cliente> clientes = c.listaObject();
        if (clientes != null) {
            listId = new Object[clientes.size()];
            for (Cliente cx : clientes) {
                listId[i] = cx.getId();
                i++;
            }
        } else {
            listId = null;
        }
        return listId;
    }
    /*{Post: listId contine los Id de todos los Clientes dados de alta
     *       - si listId esta vacia, indica que no hay clientes dados de alta.
     *       - si listId no esta vacia contiene los Id de todos los clientes dados de alta}*/


    /*
    Actualiza la lista de Clientes clientes en Disco. Si no existe lo crea de nuevo, 
     * {Pre:-}*/
    public void actualizarClientes() throws GestorDiscException {
        c.actualizar("ListaClientes.txt");
    }
    /*{Post:se ha guardado la lista clientes en Disco}*/
}

