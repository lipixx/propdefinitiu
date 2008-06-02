/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.Calendar;

/**
 *
 * @author Josep Marti
 */
public class tuplaEmissio {

    public Calendar dataEmissio;
    public Calendar horaInici;
    public Calendar horaFi;
    public boolean emes;
    public boolean facturat;
    public float preuEmissio;
    public String nomPrograma;

    public tuplaEmissio() {
        dataEmissio=null;
        horaInici=null;
        horaFi=null;
        nomPrograma = "";
        emes = false;
        facturat = false;   
    }
}
