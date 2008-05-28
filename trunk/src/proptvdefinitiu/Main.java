/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proptvdefinitiu;

import vistes.kVistes;

/**
 *
 * @author lipi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run(){
                try {
                    kVistes vistaPrincipal = new kVistes();
                    if (vistaPrincipal != null) {
                        vistaPrincipal.mostraVPrincipal();
                    }
                } catch (Exception ex) {
                    System.out.println("MAIN.java => Exception!!" +ex.getMessage());
                }
            }
        });
    }
}