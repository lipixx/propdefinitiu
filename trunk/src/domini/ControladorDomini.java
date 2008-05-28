package domini;

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
    
    public ControladorDomini()
    {
        /**Inicialitzacions*/
        try 
        {
            ControlProg = new ControladorProgrames();
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
    
}
