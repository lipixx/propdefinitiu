/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes;

import java.text.ParseException;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 *
 * @author lipi
 */
public class Verificador extends InputVerifier {

    public boolean verify(JComponent input) 
    {
        if (input instanceof JFormattedTextField) 
        {
            JFormattedTextField ftf = (JFormattedTextField) input;
            JFormattedTextField.AbstractFormatter formatter =
                    ftf.getFormatter();
            if (formatter != null) {
                String texte = ftf.getText();
                try {
                    formatter.stringToValue(texte);
                  //JOptionPane.showMessageDialog(null,"Format de la data correcte.");
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
