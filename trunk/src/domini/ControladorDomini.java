/**
 * La classe ControladorDomini relaciona tots els controladors d'aquesta capa
 * amb els de la capa de dades i la de vista.
 * 
 * @author  Felip Moll
 * @version 1.0 6 Juny 2008 
 * 
 * Versió final.
 */

package domini;

import dades.RepositoriFranges;

public class ControladorDomini {

    /**Variables globals i Controladors que te el CD*/
    private ControladorProgrames ControlProg;
    private ControladorCliente ControlClient;
    private ControladorPlanificacio ControlPlan;
    private ControladorFactura ControlFactures;
    private RepositoriFranges RepoFranges;
    private Cliente clientActual;

    public ControladorDomini() {
        /**Inicialitzacions*/
        try {
            clientActual = null;
            RepoFranges = new RepositoriFranges();
            ControlProg = new ControladorProgrames(RepoFranges);
            ControlClient = new ControladorCliente();
            ControlPlan = new ControladorPlanificacio(RepoFranges, ControlProg);
            ControlFactures = new ControladorFactura();

        } catch (Exception ex) {
            System.out.println("Error carregant els controladors");
        }
    }

    /**
     * Comprova si el client actual ha estat seleccionat durant el transcurs
     * del programa.
     * @return Fals si encara no s'ha triat cap client, cert altrament.
     */
    public boolean clientSetted() {
        if (clientActual == null) {
            return false;
        }
        return true;
    }

    /* Les seguents operacions serveixen per passar els controladors de la
     *  capa de domini als corresponents controladors d'altres capes que els
     *  necessitin.
     */
    /**
     * Obtenir el Controlador de Facturacio
     * @return El controlador de facturacio actual
     */
    public ControladorFactura getCFactura() {
        return ControlFactures;
    }

    /**
     * Obtenir el Controlador de Programes
     * @return El controlador de programes actual
     */
    public ControladorProgrames getCProgs() {
        return ControlProg;
    }

    /**
     * Obtenir el Controlador de Client
     * @return El controlador de Client actual
     */
    public ControladorCliente getCClient() {
        return ControlClient;
    }

    /**
     * Obtenir el Controlador de Planificacio
     * @return El controlador de Planificacio actual
     */
    public ControladorPlanificacio getCPlan() {
        return ControlPlan;
    }

    /**
     * Aquesta operacio guarda el clientActual al repositori de clients.
     * Serveix per quan s'han fet canvis sobre ell i s'ha de guardar a disc.
     * @pre El clientActual esta incialitzat
     * @post S'ha guardat el clientActual al repositori de clients de disc
     */
    public void guardaClientActual() {
        if (clientActual != null) {
            ControlClient.modificarCliente(clientActual);
        }
    }

    /**
     * Aquesta operacio es cridada quan es selecciona a la vista principal
     * un nou client. Realitza les operacions necessaries de guardat/recuperacio
     * i avisa als controladors corresponents
     * @param id L'identificador del nou client triat
     * @return Un string de dues posicions tals que:
     *      informacio[0] Conte les dades generals del client
     *      informacio[1] Conte informacio sobre les autofacturacions realitzades
     * @pre -
     * @post Tots els controladors han estat avisats de que s'ha canviat el clientActual
     * i si aquest estava inicialitzat, s'ha guardat a disc.
     * @throws java.lang.Exception
     */
    public String[] setClientActual(String id) throws Exception {
        String[] informacio = new String[2];

        Cliente aux = new Cliente();
        if (ControlClient.obtenerCliente(id, aux)) {
            clientActual = aux;

            /**Aqui avisem als controladors que resetejin el seu clientActual
             *En el cas de les factures, automaticament el controlador comprova
             *si el client passat te factures pendents, i ho factura. Retorna
             *cert si en tenia, fals altrament-> Per mes informacio queda per implementar
             * el que ens retorni un resum del que s'ha facturat.
             */
            String autoFactures = "No hi havia factures pendents.";
            ControlPlan.setClient(clientActual);
            if (ControlFactures.setClient(clientActual)) {
                autoFactures = "S'han realitzat facturacions!";
            }

            informacio[1] = autoFactures;

            String faux = "No";
            if (clientActual.getAutoFact()) {
                faux = "Si";
            }

            informacio[0] = "DNI: " + clientActual.getId() + "\nNom: " + clientActual.getNombre() + "\nCognoms: " + clientActual.getApellidos() + "\nAutoFactura activada: " + faux;

            return informacio;
        }
        return null;
    }
}
