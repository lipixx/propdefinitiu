/**
 * La classe kVistaFranges es el controlador de la vista de franges. S'encarrega
 * de interactuar amb la capa de domini i la vista.
 * 
 * @author  Felip Moll 41743858P
 * @version 1.0, 6 Juny 2008 
 * 
 */
package vistes;

import domini.ControladorProgrames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class kVistaFranges {

    private ControladorProgrames cProgs;
    private VistaFranges vFranges;
    public kVistaFranges(ControladorProgrames NcProgs)
    {
        cProgs = NcProgs;
        initVFranges();
    }

    
    public VistaFranges getVistaFranges()
    {
        return vFranges;
    }

    /**Reset de la finestra*/
    public void resetFranges()
    {
         String franges[][] = cProgs.getFranges();
         vFranges.setLlistaFranges(franges);
    }

    /**Inicialitzacio de la finestra*/
    private void initVFranges() 
    {
         vFranges = new VistaFranges();
         String franges[][] = cProgs.getFranges();
         vFranges.setLlistaFranges(franges);
         
         ActionListener accions;
         
         accions = (new ActionListener() {

            
            public void actionPerformed(ActionEvent arg0) 
            {
                String franges[][] = vFranges.getListaFranges();
                if (franges != null)
                cProgs.setFranges(franges.length, franges);
                else System.out.println("CAP FRANJA!!");
            }
        });
        
        vFranges.setActions(accions);

    }
    
}
