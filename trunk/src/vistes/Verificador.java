/**
 * La classe Verificador ens servira per assignar a un JFormattedTextField, un verificador
 * de dades. Aquest verificador s'encarrega de mirar si el que s'ha introduit a la caixa
 * de texte es correcte, avisant si no ho es.
 * 
 * @author  Felip Moll 41743858P
 * @version 1.0, 6 Juny 2008 
 * 
 * Versio Final
 */
package vistes;

import java.text.ParseException;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;


public class Verificador extends InputVerifier {

    public boolean verify(JComponent input) 
    {
        /**Comprovem si l'input es de tipus JFormattedTextField*/
        if (input instanceof JFormattedTextField) 
        {
            //Assignem un formatejador
            JFormattedTextField ftf = (JFormattedTextField) input;
            JFormattedTextField.AbstractFormatter formatter = ftf.getFormatter();
            if (formatter != null) {
                //Agafem el text de la caixa, i comprovem
                String texte = ftf.getText();
                try {
                    formatter.stringToValue(texte);
                    return true;
                } catch (ParseException pe) {
                    JOptionPane.showMessageDialog(null,"Format de la data incorrecte!");
                    ftf.setText("");
                    ftf.requestFocus();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        return verify(input);
    }
}
