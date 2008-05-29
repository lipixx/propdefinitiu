/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vistes;

import domini.ControladorProgrames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author lipi
 */


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
                cProgs.setFranges(franges.length, franges);
            }
        });
        
        vFranges.setActions(accions);

    }
    
}
