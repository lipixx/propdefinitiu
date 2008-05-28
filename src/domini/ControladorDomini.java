package domini;

/**
 *
 * @author lipi
 */



public class ControladorDomini 
{
    
    /**Variables globals i classes que te el CD*/
    private ControladorProgrames ControlProg;
    
    
    public ControladorDomini()
    {
        /**Inicialitzacions*/
        try 
        {
            ControlProg = new ControladorProgrames();
       
        
        } catch (Exception ex) 
        {
           System.out.println("Error carregant els controladors");           
        }
        
       
    }
    
    public ControladorProgrames getCProgs()
    {
        return ControlProg;
    }
    
    
}
