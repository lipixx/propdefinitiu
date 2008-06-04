package domini;

import dades.RepositoriFranges;

/**
 *
 * @author lipi
 */



public class ControladorDomini 
{
    
    /**Variables globals i classes que te el CD*/
    private ControladorProgrames ControlProg;
    private ControladorCliente  ControlClient;
    //Falten
    private ControladorPlanificacio ControlPlan;
    private ControladorFactura ControlFactures;
    //Fi falten
    private RepositoriFranges RepoFranges;
    private Cliente clientActual;
    
    public ControladorDomini()
    {
        /**Inicialitzacions*/
        try 
        {
            clientActual = null;
            RepoFranges = new RepositoriFranges();
            ControlProg = new ControladorProgrames(RepoFranges);
            ControlClient = new ControladorCliente();
            
            //Falten
            ControlPlan = new ControladorPlanificacio(RepoFranges,ControlProg);
            ControlFactures = new ControladorFactura();
            //*Fi falten
            
        } catch (Exception ex) 
        {
           System.out.println("Error carregant els controladors");           
        }
        
       
    }

    public boolean clientSetted() {
        if (clientActual == null) return false;
        return true;
    }
    
    public ControladorFactura getCFactura()
    {
        return ControlFactures;
    }
    
    public ControladorProgrames getCProgs()
    {
        return ControlProg;
    }
    
    public ControladorCliente getCClient()
    {
        return ControlClient;
    }
    
    public ControladorPlanificacio getCPlan()
    {
        return ControlPlan;
    }

    public void guardaClientActual() 
    {
        //Guarda el clientActual al repositori de clients
        if(clientActual != null)
        ControlClient.modificarCliente(clientActual);
    }
    
    public String[] setClientActual(String id) throws Exception
    {
        String[] informacio = new String[2];
        
        Cliente aux = new Cliente();
        if (ControlClient.obtenerCliente(id, aux)){
            clientActual = aux;
        
        /**Aqui avisem als controladors que resetejin el seu clientActual
        *En el cas de les factures, automaticament el controlador comprova
        *si el client passat te factures pendents, i ho factura. Retorna
        *cert si en tenia, fals altrament-> ens podria retornar l'string de factures...
        */
        String autoFactures = "No hi havia factures pendents.";
        ControlPlan.setClient(clientActual);
        if (ControlFactures.setClient(clientActual)) autoFactures = "S'han realitzat facturacions!";
        
        informacio[1] = autoFactures;
        
        String faux = "No";
        if (clientActual.getAutoFact()) faux = "Si";
        
        informacio[0] = "DNI: "+clientActual.getId()+"\nNom: "+clientActual.getNombre()+"\nCognoms: "
                +clientActual.getApellidos()+"\nAutoFactura activada: "+faux;
        
        return informacio;
        }
        return null;
    }
    
}
