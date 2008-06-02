/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domini;

/**
 * Clase que se encarga del sistema de facturacion.
 * 
 * @author rael
 */


import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;

public class Factura implements Serializable {

    public enum enumFormaPagament { 

        TARJA, COMPTE;
    }
    /** Identificador intern de la factura */
    private int idFactura;
    /** Preu total de la factura */
    private double total;
    /** Data de emisió de la factura */
    private Calendar dataFacturacio;
    /** Llista de conceptes de la factura */
    private LinkedList<ServeiPendent> llistaConceptos;
   
    
    /** Codi del compte de facturació, sigui tarja o compte corrent */
    private String compte;
    /** Forma de pagament, tarja o compte corrent */
    private enumFormaPagament formaPagament;
  
    
    public Factura()
    {
        idFactura = 0;
        total = 0;
        dataFacturacio = Calendar.getInstance();
        llistaConceptos = new LinkedList<ServeiPendent>();
        
        compte = "";
        formaPagament = formaPagament.COMPTE; 
    }
    
    public Factura(int nIdFactura)
    {
        idFactura = nIdFactura;
        total = 0;
        dataFacturacio = Calendar.getInstance();
        llistaConceptos = new LinkedList<ServeiPendent>();
        
        compte = "";
        formaPagament = formaPagament.COMPTE;  
    }
    

    /**
     * Calcula el preu total de una factura a partir dels seus serveis.
     * 
     * @return Preu total acumulat de la factura.
     * @throws java.lang.Exception 
     * @pre    -
     * @post   Retorna el preu total acumulat de la factura.
     */
    public double getTotal() {
        return total;
    }
    
    /**
     * Consultora de l'atribut idfactura.
     * 
     * @return id de la factura
     * @throws - 
     * @pre    -
     * @post   Retorna l'identificador de la factura
     */
    public int getId() {
        return idFactura;
    }

    /**
     * Consultora de l'atribut dataFacturacio.
     * 
     * @return La data de la facturacio.
     * @throws java.lang.Exception 
     * @pre    -
     * @post   -
     * 
     */
    public Calendar getDataFacturacio() {
       return dataFacturacio;  
    }

    /**
     * Llista tots els conceptes de la factura.
     * 
     * @return La llista de conceptes que conte la factura.
     * @throws java.lang.Exception 
     * @pre    -
     * @post   -
     */
    public LinkedList<ServeiPendent> getLlistaConceptos() {
        return llistaConceptos;
    }

    /**
     * Elimina tots els conceptes de la factura.
     * 
     * @return Cert.
     * @throws java.lang.Exception 
     * @pre    -
     * @post   La factura no conte cap concepte y total val 0.
     */
    public boolean vaciarFactura() {
        total = 0;
        dataFacturacio = Calendar.getInstance();
        llistaConceptos.clear();
        return true;
    }
    
    public void setTotal(double nTotal)
    {
        total = nTotal;
    }
    public void setIdFactura(int id)
    {
        idFactura = id;
    }
    public void setDataFacturacio(Calendar nData)
    {
        dataFacturacio = (Calendar) nData.clone();
    }

    /**
     * Afegeix un concepte a la llista de conceptes de la factura.
     * i actualitza el preu total d'aquesta.
     * 
     * @param  inConcepto
     *          Servei que volem afegir a la factura.
     * @return Cert.
     * @throws java.lang.Exception 
     * @pre    -
     * @post   La factura conte el nou concepte.
     */
    public boolean addConcepte(ServeiPendent inConcepto)
    {
        if (llistaConceptos.add(inConcepto))
        {
        total += inConcepto.getPreu();
        return true;
        }
        return false;
    }

    /**
     * Cerca i treu un concepte a la llista de conceptes d'una factura i
     * actualitza el preu total de la factura.
     * 
     * @param  inConcepto
     *          Servei que volem treure de la factura.
     *  
     * @return Cert.
     * @throws java.lang.Exception 
     * @pre    -
     * @post   La factura ja no conte el nou concepte.
     */
    public boolean removeConcepte(ServeiPendent inConcepto)
    {
        boolean r =  llistaConceptos.remove(inConcepto);
        if (r) total -= inConcepto.getPreu();
        return r;
    }

    /**
     * Marca una factura com cobrada.
     * @param inCompte Codi del compte on es cobrara la factura
     * @return Cert
     * @throws java.lang.Exception 
     * @pre    La factura no estava cobrada.
     * @post   Tots els serveis pendents de la factura s'han marcat com facturats.
     */
      public boolean cobrarFacturar(String inCompte) 
      {
        Calendar ahora = Calendar.getInstance();

        compte = inCompte;
        for (int i = 0; i<llistaConceptos.size(); i++)
        {
            llistaConceptos.get(i).setFacturat(true);
        }

        dataFacturacio = ahora;

        return true;
    }
      
    public boolean cobrarFacturar() 
      {

        for (int i = 0; i<llistaConceptos.size(); i++)
        {
            llistaConceptos.get(i).setFacturat(true);
        }

        dataFacturacio = Calendar.getInstance();

        return true;
    }
}