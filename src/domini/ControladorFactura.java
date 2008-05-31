
package domini;

import java.util.LinkedList;
import java.util.Calendar;

/**
 *
 * @author Rael Garcia Arnés    47808932M
 */
public class ControladorFactura {

    private Factura factura;
    private String compte;
    private LinkedList<Emissio> llistaConceptes;
    private LinkedList<Integer> llistaConceptesActuals;
    private LinkedList<Factura> llistaFactures;
    private Cliente clientActual;
    
    public ControladorFactura() {
        this.llistaConceptes = new LinkedList();
        this.llistaConceptesActuals = new LinkedList();
        this.llistaFactures = new LinkedList();
    }

    public boolean setClient(Cliente nouClient)
    {
        clientActual = nouClient;
        if (clientActual != null)
        {
           setListaConceptos();
           setListaFactures();
           // retorna true si realitzarAutoFacturacio a partir de la data d'avui es fa
        }
        return true;
    }

    
    /**
     * Inicializa la lista de facturas de un cliente
     * 
     * 
     * @param inLlistaFactures
     *          Lista de facturas
     */
    public void setListaFactures()
    {

        llistaFactures = clientActual.getListFact();
    }
    
    
   /**
     * Inicializa la lista de facturas de un cliente
     * 
     * 
     * @param inLlistaFactures
     *          Lista de facturas
     */
    public void getListaFactures(LinkedList<Factura> inLlistaFactures) {

        llistaFactures = inLlistaFactures;
    }

    
    /**
     * Inicializa el controlador de facturas con una lista de 
     * emisiones pendientes
     * 
     * @param inllistaConceptes
     *          Lista de conceptos pendetes por facturar
     */
    public void setListaConceptos() {
         //Agafem la llista de planificacions del client
            LinkedList<Planificacio> plansClient = clientActual.getLlistaPlan();
            
            //Per tota planificacio:
            for (int i=0; i<plansClient.size(); i++)
            {
                //Agafa la llista de serveis pendents (emissions)
               LinkedList<ServeiPendent> aux;
               aux = plansClient.get(i).getLlistaEmissions();
               
               //En coloca els ServeisPendents (emissions) a la llista privada de conceptes
               for (int j=0; i<aux.size();i++)
               {
                  llistaConceptes.add((Emissio)aux.get(j));
               }
            }
    }

    /**
     * Obtiene la lista de emisiones pendientes
     * 
     * @return 
     * La lista de emisiones pendientes del usuario
     */
    public Object[][] getListaConceptos() {
        Object[][] Conceptes = new Object[llistaConceptes.size()][5];
        int i = 0;

        for (i = 0; i < llistaConceptes.size(); i++) {

            Conceptes[i][0] = i;
            Conceptes[i][1] = llistaConceptes.get(i).getPrograma().getNom();
            Conceptes[i][2] = "Continu";
            Conceptes[i][3] = llistaConceptes.get(i).getDataEmissio().get(Calendar.DAY_OF_MONTH) + "/" + llistaConceptes.get(i).getDataEmissio().get(Calendar.MONTH) + "/" + llistaConceptes.get(i).getDataEmissio().get(Calendar.YEAR);
            Conceptes[i][4] = llistaConceptes.get(i).getPreuEmissio();

        }

        return Conceptes;
    }

    /**
     * Obtiene la lista de emisiones pendientes para facturar
     * 
     * @return 
     * La lista de emisiones para factura del usuario
     */
    public Object[][] getListaConceptosActuals() {
        Object[][] Conceptes = new Object[llistaConceptesActuals.size()][4];
        int i = 0;

        for (i = 0; i < llistaConceptesActuals.size(); i++) {
            Conceptes[i][0] = llistaConceptesActuals.get(i);
            Conceptes[i][1] = llistaConceptes.get(llistaConceptesActuals.get(i)).getPrograma().getNom();
            Conceptes[i][2] = llistaConceptes.get(llistaConceptesActuals.get(i)).getDataEmissio().get(Calendar.DAY_OF_MONTH) + "/" + llistaConceptes.get(llistaConceptesActuals.get(i)).getDataEmissio().get(Calendar.MONTH) + "/" + llistaConceptes.get(llistaConceptesActuals.get(i)).getDataEmissio().get(Calendar.YEAR);
            Conceptes[i][3] = llistaConceptes.get(llistaConceptesActuals.get(i)).getPreuEmissio();
        }
        return Conceptes;
    }

    /**
     * Obtiene la lista de facturas
     * 
     * @return 
     * La lista de facturas del usuario
     * @throws Exception 
     */
    public Object[][] getListaFacturas() throws Exception {
        Object[][] Factures = new Object[llistaFactures.size()][3];
        int i = 0;

        for (i = 0; i < llistaFactures.size(); i++) {
            Factures[i][0] = llistaFactures.get(i).getDataFacturacio().get(Calendar.DAY_OF_MONTH) + "/" + llistaFactures.get(i).getDataFacturacio().get(Calendar.MONTH) + "/" + llistaFactures.get(i).getDataFacturacio().get(Calendar.YEAR);
            Factures[i][1] = llistaFactures.get(i).getTotal();
            Factures[i][2] = llistaFactures.get(i).getLlistaConceptos().toArray();
        }
        return Factures;
    }

    /**
     * Obtiene la lista de facturas
     * 
     * @param index 
     * @return
     * La lista de facturas del usuario
     * @throws Exception 
     */
    public Object[][] getListaEmisionsFactura(int index) throws Exception {
        LinkedList<ServeiPendent> lsEmissions = new LinkedList();

        lsEmissions = llistaFactures.get(index).getLlistaConceptos();

        Object[][] Emissions = new Object[lsEmissions.size()][3];
        int i = 0;

        for (i = 0; i < lsEmissions.size(); i++) {
            Emissions[i][0] = lsEmissions.get(i).getIdentificador();
            Emissions[i][1] = lsEmissions.get(i).getDataReal().get(Calendar.DAY_OF_MONTH) + "/" + lsEmissions.get(i).getDataReal().get(Calendar.MONTH) + "/" + lsEmissions.get(i).getDataReal().get(Calendar.YEAR);
            Emissions[i][2] = lsEmissions.get(i).getPreu();
        }
        return Emissions;
    }

    /**
     * Crea una factura nova
     * 
     * 
     * @param inCompte 
     *           Codi del compte o visa on volem fer el recarrec
     * @return Cierto si se ha creado la factura correctamente
     * @throws java.lang.Exception
     * @pre    Cert.
     * @post   Retorna la factura creada.
     */
    public Factura facturaNova() throws Exception {

        int i = 0;

        try {
            this.factura = new Factura();

            for (i = 0; i < llistaConceptesActuals.size(); i++) {
                llistaConceptes.get(llistaConceptesActuals.get(i)).setEmes(true);
                while (!(this.factura.addConcepte(llistaConceptes.get(llistaConceptesActuals.get(i))))){}
                llistaConceptes.remove(llistaConceptesActuals.get(i));
            }

            this.factura.cobrarFacturar(compte);
            llistaFactures.add(factura);
            clientActual.addFactura(factura);
            
            return factura;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Selecciona un conjunt de emisions segons un preu máxim
     * 
     * @param inPreuMax 
     *           Precio máximo del conjunto de servicios seleccioando
     * @return Cierto si se ha creado la factura correctamente
     * @throws java.lang.Exception
     * @pre    Cert.
     * @post   Retorna la llista de emissions que sumen com a máxim 
     * inPreuMax.
     */
    public Object[][] autofacturaPreumax(double inPreuMax) throws Exception {

        LinkedList<Emissio> llista;


        double _preu = 0;
        int i = 0;

        LinkedList<Integer> llistaAutofacturats = new LinkedList();

        llista = this.llistaPendents();

        while ((_preu < inPreuMax) && (i < llista.size())) {
            if ((llista.get(i).getPreuEmissio() + _preu) <= inPreuMax) {
                llistaAutofacturats.add(i);
                _preu += llista.get(i).getPreuEmissio();
            }
            i++;
        }


        Object[][] Conceptes = new Object[llistaAutofacturats.size()][4];

        for (i = 0; i < llistaAutofacturats.size(); i++) {
            Conceptes[i][0] = llistaAutofacturats.get(i);
            Conceptes[i][1] = llistaConceptes.get(llistaAutofacturats.get(i)).getPrograma().getNom();
            Conceptes[i][2] = llistaConceptes.get(llistaAutofacturats.get(i)).getDataEmissio().get(Calendar.DAY_OF_MONTH) + "/" +  llistaConceptes.get(llistaAutofacturats.get(i)).getDataEmissio().get(Calendar.MONTH) + "/" +  llistaConceptes.get(llistaAutofacturats.get(i)).getDataEmissio().get(Calendar.YEAR);
            Conceptes[i][3] = llistaConceptes.get(llistaAutofacturats.get(i)).getPreuEmissio();
        }

        llistaConceptesActuals.clear();
        llistaConceptesActuals = llistaAutofacturats;

        return Conceptes;

    }

    /**
     * Afegim un concepte a la llista
     * 
    
     * @param id 
     * @throws java.lang.Exception
     * @pre    Cert.
     * @post   La llista conte l'objecte Concepte.
     */
    public void addConcepteFactura(int id) throws Exception {
        if (id < llistaConceptes.size()) {
            llistaConceptesActuals.add(id);
        }
    }

    /**
     * Treiem un concepte de la llista
     * 
    
     * @param id 
     * @throws java.lang.Exception
     * @pre    Cert.
     * @post   La llista ya no conte l'objecte Concepte.
     */
    public void removeConcepteFactura(int id) throws Exception {
        llistaConceptesActuals.remove(id);
    }

    /**
     * Selecciona un conjunt de emisions segons un preu máxim
     * 
    
     * @throws java.lang.Exception
     * @pre    Cert.
     * @post   La llista ya no conte l'objecte Concepte.
     */
    public void clearConceptesFactura() throws Exception {
        llistaConceptesActuals.clear();
    }

    /**
     * Selecciona un conjunt de emisions segons un preu máxim
     * 
    
     * @param inPI 
     *          Fecha de inicio del periodo que queremos facturar
     * @param inPF 
     *          Fecha de fin del periodo que queremos facturar
     * @return Cierto si se ha creado la factura correctamente
     * @throws java.lang.Exception
     * @pre    Cert.
     * @post   Retorna la llista de emissions pendets per pagar d'un periode concret.
     */
    public Object[][] autofacturaPeriode(Calendar inPI, Calendar inPF) throws Exception {
        LinkedList<Integer> llistaAutofacturats;

        llistaAutofacturats =this.llistaPendentsPeriode(inPI, inPF);

        Object[][] Conceptes = new Object[llistaAutofacturats.size()][4];
        int i = 0;

        for (i = 0; i < llistaAutofacturats.size(); i++) {
            Conceptes[i][0] = llistaAutofacturats.get(i);
            Conceptes[i][1] = llistaConceptes.get(llistaAutofacturats.get(i)).getPrograma().getNom();
            Conceptes[i][2] = llistaConceptes.get(llistaAutofacturats.get(i)).getDataEmissio().get(Calendar.DAY_OF_MONTH) + "/" +  llistaConceptes.get(llistaAutofacturats.get(i)).getDataEmissio().get(Calendar.MONTH) + "/" +  llistaConceptes.get(llistaAutofacturats.get(i)).getDataEmissio().get(Calendar.YEAR);
            Conceptes[i][3] = llistaConceptes.get(llistaAutofacturats.get(i)).getPreuEmissio();
        }

        llistaConceptesActuals.clear();
        llistaConceptesActuals = llistaAutofacturats;

        return Conceptes;
    }

    /**
     * Comprueva si hay alguna emision pendiente por cobrar en el
     * periodo de autofacturación del cliente.
     * 
     * @param periode 
     *          Tipo de perioodo de la autofacturacion
     * @param dUltimafactura 
     *          Fecha de la última factura.
     * @return Cierto si se ha creado la factura correctamente
     * @throws java.lang.Exception
     * @pre    Cert.
     * @post   Retorna cert si hi ha alguna factura pendent al periode indicat
     */
    public boolean autofacturaComprovacioPeriode(String periode, Calendar dUltimafactura) throws Exception {

        LinkedList<Integer> llistaAutofacturats;

        Calendar dFP = dUltimafactura;
        Calendar dIP = dUltimafactura;
        Calendar Ahora = Calendar.getInstance();

        if (periode.equalsIgnoreCase("MENSUAL")) {
            dFP.add(Calendar.MONTH, 1);
        } else if (periode.equalsIgnoreCase("SETMANAL")) {
            dFP.add(Calendar.DATE, 7);
        } else if (periode.equalsIgnoreCase("DIARI")) {
            dFP.add(Calendar.DATE, 1);
        }

        if (dFP.before(Ahora)) {
            llistaAutofacturats = this.llistaPendentsPeriode(dIP, dFP);

            llistaConceptesActuals.clear();
            llistaConceptesActuals = llistaAutofacturats;

            return (llistaAutofacturats.size() != 0);
        } else {
            return false;
        }

    }

    /**
     * Selecciona el conjunt de emisions pendientes de pagar
     * 
     * @return Cierto si se ha creado la factura correctamente
     * @throws java.lang.Exception
     * @pre    Cert.
     * @post   Retorna la llista de Conceptes pendentes per facturar
     */
    private LinkedList<Emissio> llistaPendents() throws Exception {
        int i = 0;
        LinkedList<Emissio> _llista = new LinkedList();

        while (i < llistaConceptes.size()) {
            if (!llistaConceptes.get(i).getFacturat()) {
                _llista.add(llistaConceptes.get(i));
            }

            i++;
        }

        return _llista;
    }

    /**
     * Selecciona el conjunt de emisions pendientes de pagar
     * 
    
     * @param inPI 
     *          Fecha de inicio del periodo que queremos facturar
     * @param inPF 
     *          Fecha de fin del periodo que queremos facturar 
     * @return Cierto si se ha creado la factura correctamente
     * @throws java.lang.ExceptionR
     * @pre    Cert.
     * @post  Retorna la llista de Conceptes pendentes per facturar
     *          d'un periode concret.
     */
    private LinkedList<Integer> llistaPendentsPeriode(Calendar inPI, Calendar inPF) throws Exception {
        int i = 0;
        LinkedList<Integer> llista = new LinkedList();
        Calendar dataEmissio = Calendar.getInstance();

        while (i < llistaConceptes.size()) {
            dataEmissio = llistaConceptes.get(i).getDataEmissio();
            if (!(llistaConceptes.get(i).getFacturat()) && (dataEmissio.before(inPI)) && (dataEmissio.before(inPF))) {

                llista.add(i);
            }

            i++;
        }

        return llista;
    }
}