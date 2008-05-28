/*
 * AfegirPrograma.java
 *
 * Created on 26 / maig / 2008, 22:55
 */
package vistes;

import domini.tuplaPrograma;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author  lipi
 */
public class vAfegirPrograma extends javax.swing.JDialog {

    tuplaPrograma nouPrograma;

    /** Creates new form AfegirPrograma */
    public vAfegirPrograma(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setActions(ActionListener actionListener) {
        dacord.addActionListener(actionListener);
    }

    public tuplaPrograma getTupla() {
        try {
            SimpleDateFormat formatCalendar = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatHora = new SimpleDateFormat("k:mm");
            nouPrograma = new tuplaPrograma();

            nouPrograma.nom = nom.getText();
            if (nouPrograma.nom == null || nouPrograma.nom.equals("") || nouPrograma.nom.equals(" ")) {
                JOptionPane.showMessageDialog(null, "Has de omplir totes les dades.");
                return null;
            }
            String aux3 = "";
            String aux = preu.getText().replace('.', ' ');
            for (int x=0; x < aux.length(); x++) {
                if (aux.charAt(x) != ' ') {
                    aux3 += aux.charAt(x);
                }
            }
            if ((nouPrograma.preu = (float) Double.parseDouble(aux3.replace(',', '.'))) < 0) {
                nouPrograma.preu = 0;
            }

            nouPrograma.descripcio = descripcio.getText();

            /**Data Caducitat*/
            Date date = formatCalendar.parse(dataC.getText());
            nouPrograma.dataCad = Calendar.getInstance();
            nouPrograma.dataCad.setTime(date);

            /**Data Inici - Nomes Directe*/
            if (llistaFormats.getSelectedIndex() == 2) {
                Date date1 = formatCalendar.parse(dIniEmissio.getText());
                Date hora1 = formatHora.parse(hora.getText());
                Calendar aux2 = Calendar.getInstance();
                aux2.setTime(hora1);

                nouPrograma.iniciEmissio = Calendar.getInstance();
                nouPrograma.iniciEmissio.setTime(date);
                nouPrograma.iniciEmissio.set(Calendar.HOUR_OF_DAY, aux2.get(Calendar.HOUR_OF_DAY));
                nouPrograma.iniciEmissio.set(Calendar.MINUTE, aux2.get(Calendar.MINUTE));

                if (nouPrograma.iniciEmissio.before(nouPrograma.dataCad)) {
                    JOptionPane.showMessageDialog(null, "La data de caducitat era anterior a la data d'inici d'emissio.");
                    return null;
                }

                if ((nouPrograma.duracio = ((Integer) duracio.getValue()).intValue()) < 0) {
                    JOptionPane.showMessageDialog(null, "La duracio no pot ser negativa.");
                }

                /*Categories per format Directe*/
                switch (llistaCategories.getSelectedIndex()) {
                    case 0:
                        //Altres
                        nouPrograma.categoria = 0;
                        break;
                    case 1:
                        //Concurs
                        nouPrograma.categoria = 2;
                        break;
                    case 2:
                        //Esport
                        nouPrograma.categoria = 4;
                        break;
                    case 3:
                        //Noticies
                        nouPrograma.categoria = 7;
                        break;
                    case 4:
                        //Tertulia
                        nouPrograma.categoria = 10;
                        break;
                }
            }

            /**Format Normal*/
            if (llistaFormats.getSelectedIndex() == 0) {
                if ((nouPrograma.duracio = ((Integer) duracio.getValue()).intValue()) < 0) {
                    JOptionPane.showMessageDialog(null, "La duracio no pot ser negativa.");
                }

                switch (llistaCategories.getSelectedIndex()) {
                    case 0:
                        //Altres
                        nouPrograma.categoria = 0;
                        break;
                    case 1:
                        //Adults
                        nouPrograma.categoria = 1;
                        break;
                    case 2:
                        //Concurs
                        nouPrograma.categoria = 2;
                        break;
                    case 3:
                        //Documental
                        nouPrograma.categoria = 3;
                        break;
                    case 4:
                        //Esport
                        nouPrograma.categoria = 4;
                        break;
                    case 5:
                        //Infantil
                        nouPrograma.categoria = 5;
                        break;
                    case 6:
                        //Musica
                        nouPrograma.categoria = 6;
                        break;
                    case 7:
                        //Pelicula
                        nouPrograma.categoria = 8;
                        break;
                    case 8:
                        //Serie
                        nouPrograma.categoria = 9;
                        break;
                    case 9:
                        //Tertulia
                        nouPrograma.categoria = 10;
                        break;
                }
            }

            /**Format Continu*/
            if (llistaFormats.getSelectedIndex() == 1) {

                switch (llistaCategories.getSelectedIndex()) {
                    case 0:
                        //Altres
                        nouPrograma.categoria = 0;
                        break;
                    case 1:
                        //Concurs
                        nouPrograma.categoria = 2;
                        break;
                    case 2:
                        //Esports
                        nouPrograma.categoria = 4;
                        break;

                    case 3:
                        //Musica
                        nouPrograma.categoria = 6;
                        break;
                    case 4:
                        //Noticies
                        nouPrograma.categoria = 7;
                        break;
                    default:
                        break;
                    }
            }

            /*Format*/
            nouPrograma.format = llistaFormats.getSelectedIndex();

            /*Tematiques*/
            StringTokenizer temaSueltu = new StringTokenizer(tematiques.getText());
            nouPrograma.tematiques = new String[temaSueltu.countTokens()+1];
            int i = 0;
            while (temaSueltu.hasMoreTokens() && i < nouPrograma.tematiques.length-1) {
                nouPrograma.tematiques[i] = temaSueltu.nextToken();
                i++;
            }
            nouPrograma.tematiques[nouPrograma.tematiques.length-1] = "General";
            clearComponents();
            return nouPrograma;

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error Intern - vAfegirPrograma.getTupla: L27. ->" + ex.getMessage());
            initComponents();
            return null;
        }
    }

    public void setDadesMod(tuplaPrograma dadesP) 
    {
        if (dadesP != null){
        nom.setText(dadesP.nom);
        nom.setEditable(false);
        llistaCategories.setEnabled(false);
        llistaFormats.setEnabled(false);
        
        descripcio.setText(dadesP.descripcio);
        preu.setValue(dadesP.preu);  
        
        int dia = dadesP.dataCad.get(Calendar.DAY_OF_MONTH);
        int mes = dadesP.dataCad.get(Calendar.MONTH);
        int any = dadesP.dataCad.get(Calendar.YEAR);
        dataC.setText(dia+"/"+mes+"/"+any);
        
        if (dadesP.format == 2 || dadesP.format == 0)
                    duracio.setValue(dadesP.duracio);
        
        if (dadesP.format == 2)
        {
        dia = dadesP.iniciEmissio.get(Calendar.DAY_OF_MONTH);
        mes = dadesP.iniciEmissio.get(Calendar.MONTH);
        any = dadesP.iniciEmissio.get(Calendar.YEAR);
        int horax = dadesP.iniciEmissio.get(Calendar.HOUR_OF_DAY);
        int minuts = dadesP.iniciEmissio.get(Calendar.MINUTE);
        
        dIniEmissio.setText(dia+"/"+mes+"/"+any);
        hora.setText(horax+":"+minuts);
        }
        
        llistaFormats.setSelectedIndex(dadesP.format);
        
        switch (dadesP.format)
        {
                //Normal
            case 0:
                switch (dadesP.categoria) {
                    case 0:
                        //Altres
                        llistaCategories.setSelectedIndex(0);
                        break;
                    case 1:
                        //Adults
                        llistaCategories.setSelectedIndex(1);
                        break;
                    case 2:
                        //Concurs
                        llistaCategories.setSelectedIndex(2);
                        break;
                    case 3:
                        //Documental
                        llistaCategories.setSelectedIndex(3);
                        break;
                    case 4:
                        //Esport
                        llistaCategories.setSelectedIndex(4);
                        break;
                    case 5:
                        //Infantil
                        llistaCategories.setSelectedIndex(5);
                        break;
                    case 6:
                        //Musica
                        llistaCategories.setSelectedIndex(6);
                        break;
                    case 8:
                        //Pelicula
                        llistaCategories.setSelectedIndex(7);
                        break;
                    case 9:
                        //Serie
                        llistaCategories.setSelectedIndex(8);
                        break;
                    case 10:
                        //Tertulia
                        llistaCategories.setSelectedIndex(9);
                        break;
                }
                break;
                //Continu
            case 1:
                switch (dadesP.categoria) {
                    case 0:
                        //Altres
                        llistaCategories.setSelectedIndex(0);
                        break;
                    case 2:
                        //Concurs
                        llistaCategories.setSelectedIndex(1);
                        break;
                    case 4:
                        //Esports
                        llistaCategories.setSelectedIndex(2);
                        break;

                    case 6:
                        //Musica
                        llistaCategories.setSelectedIndex(3);
                        break;
                    case 7:
                        //Noticies
                        llistaCategories.setSelectedIndex(4);
                        break;
                    default:
                        break;
                    }
                break;
                
                //Directe
            case 2:
                switch (dadesP.categoria)
                {
                    case 0:
                        //Altres
                        llistaCategories.setSelectedIndex(0);
                        break;
                    case 2:
                        //Concurs
                        llistaCategories.setSelectedIndex(1);
                        break;
                    case 4:
                        //Esport
                        llistaCategories.setSelectedIndex(2);
                        break;
                    case 7:
                        //Noticies
                        llistaCategories.setSelectedIndex(3);
                        break;
                    case 10:
                        //Tertulia
                        llistaCategories.setSelectedIndex(4);
                    break;
                    default: break;
                }
                break;        
            default: break;
        }
        
        String tematiquesOld = new String();
        for (int i=0; i<dadesP.tematiques.length; i++)
            tematiquesOld = tematiquesOld + dadesP.tematiques[i] +" ";
        
        tematiques.setText(tematiquesOld);
        
    }
    }

    private void clearComponents() {
        nom.setText("");
        descripcio.setText("");
        preu.setText("0.0");
        duracio.setValue(0);
        dataC.setValue(new Date());
        dIniEmissio.setValue(new Date());
        hora.setValue(new Date());
        llistaCategories.setSelectedIndex(0);
        llistaFormats.setSelectedIndex(0);
        tematiques.setText("");
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nom = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descripcio = new javax.swing.JTextArea();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatter df = new DateFormatter(format);
        dataC = new javax.swing.JFormattedTextField(df);
        llistaCategories = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        llistaFormats = new javax.swing.JComboBox();
        tematiques = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        duracio = new javax.swing.JSpinner();
        dacord = new javax.swing.JButton();
        cancela = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        dIniEmissio = new javax.swing.JFormattedTextField(df);
        NumberFormat formatPreu = NumberFormat.getNumberInstance();
        formatPreu.setMinimumFractionDigits(2);
        NumberFormatter nf = new NumberFormatter(formatPreu);
        preu = new javax.swing.JFormattedTextField(nf);
        jLabel5 = new javax.swing.JLabel();
        DateFormat formatH = new SimpleDateFormat("k:mm");
        DateFormatter hf = new DateFormatter(formatH);
        hora = new javax.swing.JFormattedTextField(hf);
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 24));
        jLabel1.setText("Nou Programa");

        jLabel2.setText("Nom:");

        jLabel3.setText("Preu:");

        jLabel4.setText("Descripció:");

        jLabel6.setText("Data Caducitat:");

        jLabel7.setText("Propietats generals:");

        descripcio.setColumns(20);
        descripcio.setRows(5);
        jScrollPane1.setViewportView(descripcio);

        dataC.setValue(new Date());
        dataC.setInputVerifier(new Verificador());

        llistaCategories.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Altres", "Adults", "Concurs", "Documental", "Esport", "Infantil", "Música", "Pel·licula", "Serie", "Tertulia" }));

        jLabel8.setText("Categoria:");

        jLabel9.setText("Format:");

        llistaFormats.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Continu", "Directe" }));
        llistaFormats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                llistaFormatsActionPerformed(evt);
            }
        });

        jLabel10.setText("Tematiques (separa amb espais):");

        jLabel11.setText("Duracio (min):");

        duracio.setModel(new javax.swing.SpinnerNumberModel(0, 0, 500, 5));

        dacord.setText("D'acord");

        cancela.setText("Cancel·la");
        cancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelaActionPerformed(evt);
            }
        });

        jLabel12.setText("Data Inici Emissio:");

        dIniEmissio.setValue(new Date());
        dIniEmissio.setInputVerifier(new Verificador());
        dIniEmissio.setEnabled(false);

        preu.setText(null);
        preu.setValue(new Double(0));

        jLabel5.setText("€");

        hora.setValue(new Date());
        hora.setText("00:00");
        hora.setEnabled(false);

        jLabel13.setText("h");

        jLabel14.setText("a les");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel10)
                                                .addComponent(jLabel4)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel3))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addComponent(preu, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jLabel5))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(dataC, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                                            .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                            .addGap(167, 167, 167))
                                        .addComponent(tematiques, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel12)
                                                .addComponent(jLabel11))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(duracio, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(dIniEmissio, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                                    .addComponent(jLabel14)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel13))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addComponent(cancela, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(34, 34, 34)
                                                    .addComponent(dacord, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addGap(53, 53, 53)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(llistaFormats, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel9))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel8)
                                                .addComponent(llistaCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(61, 61, 61)))
                                    .addComponent(jLabel2)))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(dataC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(preu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(llistaFormats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(llistaCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tematiques, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(duracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(dIniEmissio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dacord)
                    .addComponent(cancela))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void cancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelaActionPerformed
        this.setVisible(false);        // TODO add your handling code here:
}//GEN-LAST:event_cancelaActionPerformed

    private void llistaFormatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_llistaFormatsActionPerformed

        switch (llistaFormats.getSelectedIndex()) {
            //Normal
            case 0:
                duracio.setEnabled(true);
                dIniEmissio.setEnabled(false);
                hora.setEnabled(false);
                llistaCategories.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Altres", "Adults", "Concurs", "Documental", "Esport", "Infantil", "Música", "Pel·licula", "Serie", "Tertulia"}));
                break;
            //Continu
            case 1:
                duracio.setEnabled(false);
                dIniEmissio.setEnabled(false);
                hora.setEnabled(false);
                llistaCategories.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Altres", "Concurs", "Esport", "Música", "Noticies"}));
                break;
            //Directe
            case 2:
                duracio.setEnabled(true);
                dIniEmissio.setEnabled(true);
                hora.setEnabled(true);
                llistaCategories.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Altres", "Concurs", "Esport", "Noticies", "Tertulia"}));
                break;
            default:
                break;
        }
    }//GEN-LAST:event_llistaFormatsActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancela;
    private javax.swing.JFormattedTextField dIniEmissio;
    private javax.swing.JButton dacord;
    private javax.swing.JFormattedTextField dataC;
    private javax.swing.JTextArea descripcio;
    private javax.swing.JSpinner duracio;
    private javax.swing.JFormattedTextField hora;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox llistaCategories;
    private javax.swing.JComboBox llistaFormats;
    private javax.swing.JTextField nom;
    private javax.swing.JFormattedTextField preu;
    private javax.swing.JTextField tematiques;
    // End of variables declaration//GEN-END:variables
}
