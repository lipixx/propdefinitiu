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
    private ControladorPlanificacio ControlPlan;
    private RepositoriFranges RepoFranges;
    private Cliente clientActual;
    
    public ControladorDomini()
    {
        /**Inicialitzacions*/
        try 
        {
            RepoFranges = new RepositoriFranges();
            ControlProg = new ControladorProgrames(RepoFranges);
            ControlClient = new ControladorCliente();
            ControlPlan = new ControladorPlanificacio();
        
        } catch (Exception ex) 
        {
           System.out.println("Error carregant els controladors");           
        }
        
       
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
    
    public void setClientActual(String id, boolean guardar)
    {
        if (guardar)
        ControlClient.modificarCliente(clientActual);
        
        Cliente aux = new Cliente();
        if (ControlClient.obtenerCliente(id, aux))
            clientActual = aux;        
    }
    
}
