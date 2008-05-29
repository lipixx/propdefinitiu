/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Calendar;

/**
 *
 * @author rael
 */
public class ControladorFactura {

    Factura FGENERAL = new Factura();
    private Cliente clientActual;
    private LinkedList<ServeiPendent> llistaServeisP;
    private ArrayList<Planificacio> llistaPlan;
    private ArrayList<Factura> llistaFactures;


    public ControladorFactura(Cliente clientAct)
    {
        clientActual = clientAct;
        llistaFactures = clientAct.getListFact();
        llistaPlan = clientAct.getLlistaPlan();
        setLlServeisPendents();
    }

    public Object[][] getListaFacturas() 
    {
        Object[][] factList = new Object[llistaFactures.size()][2];
        for (int i=0; i<llistaFactures.size(); i++)
        {
            factList[i][1] = llistaFactures.get(i).getTotal();
            factList[i][0] = ""+llistaFactures.get(i).getDataFacturacio().get(Calendar.DAY_OF_MONTH) + "/" +llistaFactures.get(i).getDataFacturacio().get(Calendar.MONTH) + "/" + llistaFactures.get(i).getDataFacturacio().get(Calendar.YEAR);
        }
        return factList;
    }

    public String[][] getListaServeisP()
    {
        String[][] servList = new String[1][llistaServeisP.size()];
        for (int i = 0; i<llistaServeisP.size(); i++)
        {
            servList[1][i] = llistaServeisP.get(i).getIdentificador();
        }
        return servList;
    }
    /**
     * Posa a la llista de serveis pendents, tots els serveis del client
     * que no estan facturats.
     * @pre El client actual te serveis pendents de facturar.
     * @post La llistaServeisP conte tots els serveis pendents de facturar del client
     * actual.
     * @return cert si s'ha pogut, fals si la llista de planificacions era buida.
     */
    public boolean setLlServeisPendents()
    {
        llistaServeisP = new LinkedList<ServeiPendent>();
        llistaPlan = clientActual.getLlistaPlan();
        
        if (llistaPlan == null) return false;
        int mida = llistaPlan.size();
        
        for (int i=0; i<mida; i++)
        {
            LinkedList<ServeiPendent> llistaServeisAux = llistaPlan.get(i).getLlistaServeis();
            
            if (llistaServeisAux != null)
            {
            for (int k=0; k<llistaServeisAux.size(); k++)
            {
                if (llistaServeisAux.get(k).getFacturat())
                llistaServeisP.add(llistaServeisAux.get(k));
            }
            }
        }
        return true;  
    }
    
    /**
     * Inicializa la lista de facturas, obteniendolas
     * del cliente actual.
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
     * Crea una factura nova per string d'identificadors
     * 
     * @return Cierto si se ha creado la factura correctamente
     * @throws java.lang.Exception
     * @pre    Cert.
     * @post   Retorna cert si s'ha creat correctament.
     */
    public boolean facturaNova(String[] identificadors) 
    {
        Factura F = new Factura();
        
        for (int k=0; k<identificadors.length;k++)
        {
        for (int i = 0; i<llistaServeisP.size(); i++)
        {
            ServeiPendent concepte = llistaServeisP.get(i);
            if ((concepte.getIdentificador()).equalsIgnoreCase(identificadors[k]))
            {
                    F.addConcepte(concepte);
                    concepte.setFacturat(true);
            }
        }
        }
        clientActual.setUltimaFactura(Calendar.getInstance());
        return true;
    }
    
    /**Factura nova per planificacio*/
   public boolean facturaNova(Calendar dataInici, Calendar dataFi) 
    {
        Factura F = new Factura();
        for (int i = 0; i<llistaPlan.size(); i++)
        {
            Planificacio p = llistaPlan.get(i);
            if (p==null) return false;
            
            if (p.getDataFi().equals(dataInici)&&p.getDataInici().equals(dataFi))
            {
                LinkedList<ServeiPendent> sp = p.getLlistaServeis();
                if (sp == null) return false;
                
                for (int k=0; k<sp.size();k++)
                {
                    F.addConcepte(sp.get(k));
                    sp.get(k).setFacturat(true);

                }
            }
        }
        clientActual.setUltimaFactura(Calendar.getInstance());
        return true;
    }

    /**
     * Selecciona un conjunt de serveis segons un preu maxim.
     * 
     * @param inPreuMax 
     *           Precio máximo del conjunto de servicios seleccionado.
     * @return Cierto si se ha creado la factura correctamente
     * @throws java.lang.Exception
     * @pre    Cert.
     * @post   Retorna la llista de emissions que sumen com a máxim 
     * inPreuMax.
     */
    public Object[][] autofacturaPreumax(double inPreuMax) 
    {
        double preu = 0;
        int i = 0;

        LinkedList<Integer> llistaAutofacturats = new LinkedList();


        while ((preu <= inPreuMax) && (i < llistaServeisP.size())) 
        {
            if ((llistaServeisP.get(i).getPreu() + preu) <= inPreuMax) 
            {
                llistaAutofacturats.add(i);
                preu += llistaServeisP.get(i).getPreu();
            }
            i++;
        }

        int nConceptes = llistaAutofacturats.size(); 
        Object[][] Conceptes = new Object[nConceptes][4];

        for (i = 0; i < nConceptes; i++) 
        {
            Conceptes[i][0] = llistaAutofacturats.get(i);
            Conceptes[i][1] = llistaServeisP.get(llistaAutofacturats.get(i)).getIdentificador();
            Conceptes[i][2] = llistaServeisP.get(llistaAutofacturats.get(i)).getDataReal().get(Calendar.DAY_OF_MONTH) + "/" +  llistaServeisP.get(llistaAutofacturats.get(i)).getDataReal().get(Calendar.MONTH) + "/" +  llistaServeisP.get(llistaAutofacturats.get(i)).getDataReal().get(Calendar.YEAR);
            Conceptes[i][3] = llistaServeisP.get(llistaAutofacturats.get(i)).getPreu();
        }

        return Conceptes;

    }


    /**
     * Selecciona un conjunt de emisions segons una planificacio
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
       
        LinkedList<Integer> llistaIndexsServeisP;

        llistaIndexsServeisP = llistaPendentsPeriode(inPI, inPF);

        Object[][] Conceptes = new Object[llistaIndexsServeisP.size()][4];
        int i = 0;

        for (i = 0; i < llistaIndexsServeisP.size(); i++) {
            Conceptes[i][0] = llistaIndexsServeisP.get(i);
            Conceptes[i][1] = llistaServeisP.get(llistaIndexsServeisP.get(i)).getIdentificador();
            Conceptes[i][2] = llistaServeisP.get(llistaIndexsServeisP.get(i)).getDataReal().get(Calendar.DAY_OF_MONTH) + "/" +  llistaServeisP.get(llistaIndexsServeisP.get(i)).getDataReal().get(Calendar.MONTH) + "/" +  llistaServeisP.get(llistaIndexsServeisP.get(i)).getDataReal().get(Calendar.YEAR);
            Conceptes[i][3] = llistaServeisP.get(llistaIndexsServeisP.get(i)).getPreu();
        }

        return Conceptes;
    }

    /**
     * Comprueba si hay alguna emision pendiente por cobrar en el
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
    public boolean autofacturaComprovacioPeriode() throws Exception
    {

        Calendar dUltimaFactura = clientActual.getUltimaFactura();
        
        //LinkedList<Integer> llistaAutoFacturats;

        Calendar dFP = dUltimaFactura;
        Calendar dIP = dUltimaFactura;
        Calendar Ahora = Calendar.getInstance();
        
        //0: MES |  1: SETMANA | 2:DIA
        int periode = clientActual.getIntervaloFactura();
        
        if (periode == 0) {
            dFP.add(Calendar.MONTH, 1);
        } else if (periode == 1) {
            dFP.add(Calendar.DATE, 7);
        } else if (periode == 2) {
            dFP.add(Calendar.DATE, 1);
        }

        if (dFP.before(Ahora) || dFP.equals(Ahora)) 
        {
            //Aqui tenim la llista d'indexs de llistaServeisP que s'han de facturar
            //llistaAutoFacturats = llistaPendentsPeriode(dIP, dFP);
            
            //if (llistaAutoFacturats.size() != 0)
            if (llistaServeisP.size() != 0)
            {
               facturaTot();
               return true;
            }
        } 
            return false;
    }

    private void facturaTot() 
    {
        for (int i=0; i<llistaServeisP.size(); i++)
        {
            FGENERAL.addConcepte(llistaServeisP.get(i));
            llistaServeisP.get(i).setFacturat(true);
        }
    }


    /**
     * Selecciona el conjunt de emisions pendientes de pagar en un periode
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

        while (i < llistaServeisP.size()) 
        {
            dataEmissio = llistaServeisP.get(i).getDataReal();
            if (!(dataEmissio.before(inPI)) && (dataEmissio.before(inPF))) {

                llista.add(i);
            }

            i++;
        }

        return llista;
    }

}