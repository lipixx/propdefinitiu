/*
 * VistaCriteris.java
 *
 * Created on 29 de mayo de 2008, 16:51
 */
package vistes;

import domini.tuplaCriteris;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.text.DateFormatter;

/**
 *
 * @author  superman
 */
public class VistaCriteris extends javax.swing.JDialog {

    private tuplaCriteris criteris;
    private int index;
    private String[] vec = {" --- ", "Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"};
    private int[] op = {-1, -1, -1, -1, -1};

    /** Creates new form VistaCriteris */
    public VistaCriteris(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        criteris = new tuplaCriteris();
        index = 0;
    }

    public tuplaCriteris getCriteris() throws ParseException {

        DateFormat formatHora = new SimpleDateFormat("H:mm");
        DateFormat formatCalendar = new SimpleDateFormat("dd/MM/yyyy");


        String preu = botoPreuMax.getText();
        if (preu == null || preu.equals("") || preu.equals(" ")) {
            JOptionPane.showMessageDialog(null, "Preu Maxim no pot estar buit.");
            return null;
        } else {
            criteris.preuMaxim = Float.parseFloat(preu);
            if (criteris.preuMaxim <= 0) {
                JOptionPane.showMessageDialog(null, "El format de les dades és incorrecte. Preu màxim > 0");
                return null;
            }
        }


        Date hora = formatHora.parse("00:00");


        if (botoActivarPre1.isSelected()) {

            hora = formatHora.parse(botoPreIni1.getText());
            criteris.pre1Ini = Calendar.getInstance();
            criteris.pre1Ini.setTime(hora);
            hora = formatHora.parse(botoPreFi1.getText());
            criteris.pre1Fi = Calendar.getInstance();
            criteris.pre1Fi.setTime(hora);
        }
        if (botoActivarPre2.isSelected()) {

            hora = formatHora.parse(botoPreIni2.getText());
            criteris.pre2Ini = Calendar.getInstance();
            criteris.pre2Ini.setTime(hora);
            hora = formatHora.parse(botoPreFi2.getText());
            criteris.pre2Fi = Calendar.getInstance();
            criteris.pre2Fi.setTime(hora);
        }
        if (botoActivarPre3.isSelected()) {

            hora = formatHora.parse(botoPreIni3.getText());
            criteris.pre3Ini = Calendar.getInstance();
            criteris.pre3Ini.setTime(hora);
            hora = formatHora.parse(botoPreFi3.getText());
            criteris.pre3Fi = Calendar.getInstance();
            criteris.pre3Fi.setTime(hora);
        }
        if (botoActivarPre4.isSelected()) {

            hora = formatHora.parse(botoPreIni4.getText());
            criteris.pre4Ini = Calendar.getInstance();
            criteris.pre4Ini.setTime(hora);
            hora = formatHora.parse(botoPreFi4.getText());
            criteris.pre4Fi = Calendar.getInstance();
            criteris.pre4Fi.setTime(hora);
        }
        if (botoActivarProh1.isSelected()) {

            hora = formatHora.parse(botoProhIni1.getText());
            criteris.proh1Ini = Calendar.getInstance();
            criteris.proh1Ini.setTime(hora);
            hora = formatHora.parse(botoProhFi1.getText());
            criteris.proh1Fi = Calendar.getInstance();
            criteris.proh1Fi.setTime(hora);
        }
        if (botoActivarProh2.isSelected()) {

            hora = formatHora.parse(botoProhIni2.getText());
            criteris.proh2Ini = Calendar.getInstance();
            criteris.proh2Ini.setTime(hora);
            hora = formatHora.parse(botoProhFi2.getText());
            criteris.proh2Fi = Calendar.getInstance();
            criteris.proh2Fi.setTime(hora);
        }
        if (botoActivarProh3.isSelected()) {

            hora = formatHora.parse(botoProhIni3.getText());
            criteris.proh3Ini = Calendar.getInstance();
            criteris.proh3Ini.setTime(hora);
            hora = formatHora.parse(botoProhFi3.getText());
            criteris.proh3Fi = Calendar.getInstance();
            criteris.proh3Fi.setTime(hora);
        }
        if (botoActivarProh4.isSelected()) {

            hora = formatHora.parse(botoProhIni4.getText());
            criteris.proh4Ini = Calendar.getInstance();
            criteris.proh4Ini.setTime(hora);
            hora = formatHora.parse(botoProhFi4.getText());
            criteris.proh4Fi = Calendar.getInstance();
            criteris.proh4Fi.setTime(hora);
        }

        criteris.autoGen = botoActivarAutoGen.isSelected();
        if (criteris.autoGen) {
            criteris.autoGen = true;
            criteris.adults = botoAAdults.isSelected();
            criteris.concurs = botoAConcursos.isSelected();
            criteris.documental = botoADocumentals.isSelected();
            criteris.esport = botoAEsports.isSelected();
            criteris.infantil = botoAInfantil.isSelected();
            criteris.musica = botoAMusica.isSelected();
            criteris.noticies = botoANoticies.isSelected();
            criteris.pelicula = botoAPelicules.isSelected();
            criteris.series = botoASeries.isSelected();
            criteris.tertulies = botoATertulies.isSelected();
        }

        if (botoDInici == null || botoDFi == null) {
            JOptionPane.showMessageDialog(null, "El periode de la planificacio ha d'estar definit");
            return null;
        } else {


            hora = formatCalendar.parse("dd/MM/yyyy");

            hora = formatCalendar.parse(botoDInici.getText());
            criteris.dataIni = Calendar.getInstance();
            criteris.dataIni.setTime(hora);

            hora = formatCalendar.parse(botoDFi.getText());
            criteris.dataFi = Calendar.getInstance();
            criteris.dataFi.setTime(hora);

        }
                    
      criteris.nombrePlanis=(Integer)botoNombrePlanis.getValue();

        return criteris;
    }

    private String[] elementsNoElegits() {

        String[] aux = new String[4];
        boolean trobat = false;
        int j = 0, cont = 0;
        for (int i = 0; i < 5; i++) {
            trobat = false;
            j = 0;
            while (j < 5 && !trobat) {
                if (i == op[j]) {
                    trobat = true;
                }
            }
            if (!trobat) {
                aux[cont] = vec[i];
                cont++;
            }
        }
        return aux;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        botoPreuMax = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        botoPrimerCriteri = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        botoSegonCriteri = new javax.swing.JComboBox();
        botoTercerCriteri = new javax.swing.JComboBox();
        botoQuartCriteri = new javax.swing.JComboBox();
        botoCinqueCriteri = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        DateFormat formatHora = new SimpleDateFormat("H:mm");
        DateFormatter df = new DateFormatter(formatHora);
        botoPreIni1 = new javax.swing.JFormattedTextField(df);
        botoPreFi1 = new javax.swing.JFormattedTextField(df);
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        botoActivarPre1 = new javax.swing.JCheckBox();
        botoPreIni2 = new javax.swing.JFormattedTextField(df);
        botoPreIni3 = new javax.swing.JFormattedTextField(df);
        botoPreIni4 = new javax.swing.JFormattedTextField(df);
        botoPreFi2 = new javax.swing.JFormattedTextField(df);
        botoPreFi3 = new javax.swing.JFormattedTextField(df);
        botoPreFi4 = new javax.swing.JFormattedTextField(df);
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        botoActivarPre3 = new javax.swing.JCheckBox();
        botoActivarPre4 = new javax.swing.JCheckBox();
        botoActivarPre2 = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        botoProhIni4 = new javax.swing.JFormattedTextField(df);
        jLabel23 = new javax.swing.JLabel();
        botoProhIni3 = new javax.swing.JFormattedTextField(df);
        jLabel24 = new javax.swing.JLabel();
        botoProhIni2 = new javax.swing.JFormattedTextField(df);
        jLabel25 = new javax.swing.JLabel();
        botoProhIni1 = new javax.swing.JFormattedTextField(df);
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        botoProhFi1 = new javax.swing.JFormattedTextField(df);
        botoProhFi2 = new javax.swing.JFormattedTextField(df);
        botoProhFi3 = new javax.swing.JFormattedTextField(df);
        botoProhFi4 = new javax.swing.JFormattedTextField(df);
        jLabel29 = new javax.swing.JLabel();
        botoActivarProh1 = new javax.swing.JCheckBox();
        botoActivarProh2 = new javax.swing.JCheckBox();
        botoActivarProh3 = new javax.swing.JCheckBox();
        botoActivarProh4 = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        botoASeries = new javax.swing.JCheckBox();
        botoAConcursos = new javax.swing.JCheckBox();
        botoANoticies = new javax.swing.JCheckBox();
        botoAInfantil = new javax.swing.JCheckBox();
        botoAPelicules = new javax.swing.JCheckBox();
        botoADocumentals = new javax.swing.JCheckBox();
        botoAEsports = new javax.swing.JCheckBox();
        botoAMusica = new javax.swing.JCheckBox();
        botoATertulies = new javax.swing.JCheckBox();
        botoAAdults = new javax.swing.JCheckBox();
        jLabel32 = new javax.swing.JLabel();
        botoActivarAutoGen = new javax.swing.JCheckBox();
        botoAcceptar = new javax.swing.JButton();
        botoCancelar = new javax.swing.JButton();
        botoReset = new javax.swing.JButton();
        DateFormat formatCalendar = new SimpleDateFormat("H:mm");
        DateFormatter dfc = new DateFormatter(formatCalendar);
        botoDInici = new javax.swing.JFormattedTextField(dfc);
        botoDFi = new javax.swing.JFormattedTextField(dfc);
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        botoNombrePlanis = new javax.swing.JSpinner();
        jLabel36 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel1.setText("Preu Maxim:");

        jLabel2.setText("€");

        jLabel3.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel3.setText("Prioritat dels criteris:");

        botoPrimerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio" }));
        botoPrimerCriteri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoPrimerCriteriActionPerformed(evt);
            }
        });

        jLabel4.setText("Primer:");

        jLabel5.setText("Segon:");

        jLabel6.setText("Tercer:");

        jLabel7.setText("Quart:");

        jLabel8.setText("Cinquè:");

        botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio" }));
        botoSegonCriteri.setSelectedIndex(1);
        botoSegonCriteri.setEnabled(false);
        botoSegonCriteri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoSegonCriteriActionPerformed(evt);
            }
        });

        botoTercerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio" }));
        botoTercerCriteri.setSelectedIndex(2);
        botoTercerCriteri.setEnabled(false);
        botoTercerCriteri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoTercerCriteriActionPerformed(evt);
            }
        });

        botoQuartCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio" }));
        botoQuartCriteri.setSelectedIndex(3);
        botoQuartCriteri.setEnabled(false);
        botoQuartCriteri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoQuartCriteriActionPerformed(evt);
            }
        });

        botoCinqueCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio" }));
        botoCinqueCriteri.setSelectedIndex(4);
        botoCinqueCriteri.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel9.setText("Franges Preferides:");

        botoPreIni1.setText("HH:mm");
        botoPreIni1.setInputVerifier(new Verificador());

        botoPreFi1.setText("HH:mm");
        botoPreFi1.setInputVerifier(new Verificador());

        jLabel10.setText("Hora Inici:");

        jLabel11.setText("Hora fi:");

        jLabel12.setText("Activar");

        botoPreIni2.setText("HH:mm");
        botoPreIni2.setInputVerifier(new Verificador());

        botoPreIni3.setText("HH:mm");
        botoPreIni3.setInputVerifier(new Verificador());

        botoPreIni4.setText("HH:mm");
        botoPreIni4.setInputVerifier(new Verificador());

        botoPreFi2.setText("HH:mm");
        botoPreFi2.setInputVerifier(new Verificador());

        botoPreFi3.setText("HH:mm");
        botoPreFi3.setInputVerifier(new Verificador());

        botoPreFi4.setText("HH:mm");
        botoPreFi4.setInputVerifier(new Verificador());

        jLabel13.setText("Hora fi:");

        jLabel14.setText("Hora fi:");

        jLabel15.setText("Hora fi:");

        jLabel16.setText("Hora Inici:");

        jLabel17.setText("Hora Inici:");

        jLabel18.setText("Hora Inici:");

        jLabel19.setText("Hora Inici:");

        jLabel20.setText("Hora Inici:");

        jLabel21.setText("Hora Inici:");

        jLabel22.setText("Hora Inici:");

        botoProhIni4.setText("HH:mm");
        botoProhIni4.setInputVerifier(new Verificador());

        jLabel23.setText("Hora fi:");

        botoProhIni3.setText("HH:mm");
        botoProhIni3.setInputVerifier(new Verificador());

        jLabel24.setText("Hora fi:");

        botoProhIni2.setText("HH:mm");
        botoProhIni2.setInputVerifier(new Verificador());

        jLabel25.setText("Hora fi:");

        botoProhIni1.setText("HH:mm");
        botoProhIni1.setInputVerifier(new Verificador());

        jLabel26.setText("Hora fi:");

        jLabel27.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel27.setText("Franges Prohibides:");

        jLabel28.setText("Hora fi:");

        botoProhFi1.setText("HH:mm");
        botoProhFi1.setInputVerifier(new Verificador());

        botoProhFi2.setText("HH:mm");
        botoProhFi2.setInputVerifier(new Verificador());

        botoProhFi3.setText("HH:mm");
        botoProhFi3.setInputVerifier(new Verificador());

        botoProhFi4.setText("HH:mm");
        botoProhFi4.setInputVerifier(new Verificador());

        jLabel29.setText("Activar");

        jLabel30.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 12));
        jLabel30.setText("AUTOGENERACIÓ");

        jLabel31.setText("Filtratge per tipus:");

        botoASeries.setText("Sèries");

        botoAConcursos.setText("Concursos");

        botoANoticies.setText("Notícies");

        botoAInfantil.setText("Infantil");

        botoAPelicules.setText("Pel·lícules");

        botoADocumentals.setText("Documentals");

        botoAEsports.setText("Esports");

        botoAMusica.setText("Música");

        botoATertulies.setText("Tertúlies");

        botoAAdults.setText("Adults");

        jLabel32.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel32.setText("Activar Auto Generació ?");

        botoAcceptar.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 12));
        botoAcceptar.setText("ACCPETAR");

        botoCancelar.setText("CANCELAR");
        botoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoCancelarActionPerformed(evt);
            }
        });

        botoReset.setText("Reset prioritats");
        botoReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoResetActionPerformed(evt);
            }
        });

        Calendar cal = Calendar.getInstance();
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH);
        int any = cal.get(Calendar.YEAR);
        botoDInici.setText(""+dia+"/"+mes+"/"+any);
        //botoDInici.setValue("dd/MM/yyyy");
        botoDInici.setInputVerifier(new Verificador());

        cal = Calendar.getInstance();
        dia = cal.get(Calendar.DAY_OF_MONTH);
        mes = cal.get(Calendar.MONTH);
        any = cal.get(Calendar.YEAR);
        botoDFi.setText(""+dia+"/"+mes+"/"+any);
        //botoDFi.setValue(new Date());
        botoDFi.setInputVerifier(new Verificador());

        jLabel33.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel33.setText("Inici Període planificació");

        jLabel34.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel34.setText("Fi Període planificació");

        botoNombrePlanis.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));

        jLabel36.setText("Nombre màxim planificacions:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel33)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(botoDInici, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(botoPreuMax, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)))
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel34)
                                            .addComponent(jLabel36))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(botoNombrePlanis, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(botoDFi, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(botoQuartCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGap(26, 26, 26)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(botoTercerCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(botoSegonCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(botoPrimerCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(botoCinqueCriteri, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botoReset)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botoActivarAutoGen))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botoAConcursos)
                            .addComponent(botoASeries)
                            .addComponent(botoANoticies)
                            .addComponent(botoAInfantil)
                            .addComponent(jLabel31)
                            .addComponent(botoAPelicules))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botoAAdults)
                            .addComponent(botoAEsports)
                            .addComponent(botoADocumentals)
                            .addComponent(botoAMusica)
                            .addComponent(botoATertulies)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel30)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botoPreIni4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoPreFi4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botoPreIni3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoPreFi3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botoPreIni2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botoPreFi2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botoPreIni1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoPreFi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(botoCancelar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(36, 36, 36)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(botoActivarPre1)
                    .addComponent(botoActivarPre3)
                    .addComponent(botoActivarPre4)
                    .addComponent(botoActivarPre2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botoProhIni2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botoProhIni3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botoProhFi2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoActivarProh2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botoProhFi3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoActivarProh3))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botoProhIni1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoProhFi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel27)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(botoActivarProh1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botoProhIni4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botoAcceptar)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botoProhFi4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoActivarProh4)))))
                .addContainerGap(138, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addGap(47, 47, 47))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(botoPreuMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel36)
                                    .addComponent(botoNombrePlanis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(botoPrimerCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botoReset))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoSegonCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoTercerCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoQuartCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(2, 2, 2)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botoADocumentals)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoAPelicules)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoASeries)
                            .addComponent(botoAEsports))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoAConcursos)
                            .addComponent(botoAMusica))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoANoticies)
                            .addComponent(botoATertulies))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoAInfantil)
                            .addComponent(botoAAdults))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botoActivarAutoGen)
                            .addComponent(jLabel32))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botoCinqueCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel29))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel22)
                                    .addComponent(botoProhIni1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botoProhFi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(botoActivarProh1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botoProhIni2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botoProhIni3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botoProhIni4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel28)
                                    .addComponent(botoProhFi4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(botoProhFi2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botoActivarProh2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(botoProhFi3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botoActivarProh3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoActivarProh4))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(botoPreFi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)
                                .addComponent(botoPreIni1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(botoActivarPre1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botoPreIni2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botoPreIni3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botoPreIni4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botoPreFi2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botoPreFi3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botoPreFi4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoActivarPre2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoActivarPre3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoActivarPre4)))))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botoAcceptar)
                    .addComponent(botoCancelar))
                .addGap(51, 51, 51))
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(botoDInici, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(475, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(botoDFi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(475, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void botoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoCancelarActionPerformed
        this.setVisible(false);
}//GEN-LAST:event_botoCancelarActionPerformed

    private void botoPrimerCriteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoPrimerCriteriActionPerformed
        criteris.primer = botoPrimerCriteri.getSelectedIndex();
        op[index] = criteris.primer;
        index++;
        switch (criteris.primer) {
            case 0:
                botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"}));
                break;
            case 1:
                botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Preu", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"}));
                break;
            case 2:
                botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Preu", "Franja Preferida", "Programes Seleccionats", "Periode Planificacio"}));
                break;
            case 3:
                botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Preu", "Franja Preferida", "Franja Prohibida", "Periode Planificacio"}));
                break;
            case 4:
                botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats"}));
                break;
            default:
                System.out.println("Error, aquesta opcio no es correcte");
                break;
        }
        botoSegonCriteri.setSelectedIndex(0);
        botoSegonCriteri.setEnabled(true);
            
    }//GEN-LAST:event_botoPrimerCriteriActionPerformed

    private void botoSegonCriteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoSegonCriteriActionPerformed



        String[] aux = elementsNoElegits();

        criteris.segon = botoSegonCriteri.getSelectedIndex();
        op[index] = criteris.segon;
        index++;

        botoTercerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{aux[0], aux[1], aux[2]}));

        botoTercerCriteri.setSelectedIndex(0);
        botoTercerCriteri.setEnabled(true);

        
    }//GEN-LAST:event_botoSegonCriteriActionPerformed

    private void botoTercerCriteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoTercerCriteriActionPerformed

        String[] aux = elementsNoElegits();

        criteris.tercer = botoTercerCriteri.getSelectedIndex();
        op[index] = criteris.tercer;
        index++;

        botoQuartCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{aux[0], aux[1]}));

        botoQuartCriteri.setSelectedIndex(0);
        botoQuartCriteri.setEnabled(true);
        
    }//GEN-LAST:event_botoTercerCriteriActionPerformed

    private void botoQuartCriteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoQuartCriteriActionPerformed

        String[] aux = elementsNoElegits();

        criteris.quart = botoQuartCriteri.getSelectedIndex();
        op[index] = criteris.quart;
        index++;

        botoCinqueCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{aux[0]}));

        botoCinqueCriteri.setSelectedIndex(0);
        botoCinqueCriteri.setEnabled(true);
        
        
    }//GEN-LAST:event_botoQuartCriteriActionPerformed

    private void botoResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoResetActionPerformed

        botoPrimerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"}));
        botoPrimerCriteri.setSelectedIndex(0);
        botoPrimerCriteri.setEnabled(true);

        botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"}));
        botoSegonCriteri.setSelectedIndex(1);
        botoSegonCriteri.setEnabled(false);

        botoTercerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"}));
        botoTercerCriteri.setSelectedIndex(2);
        botoTercerCriteri.setEnabled(false);

        botoQuartCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"}));
        botoQuartCriteri.setSelectedIndex(3);
        botoQuartCriteri.setEnabled(false);

        botoCinqueCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"}));
        botoCinqueCriteri.setSelectedIndex(4);
        botoCinqueCriteri.setEnabled(false);
}//GEN-LAST:event_botoResetActionPerformed

    public void setActions(ActionListener actions) {

        botoAcceptar.addActionListener(actions);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox botoAAdults;
    private javax.swing.JCheckBox botoAConcursos;
    private javax.swing.JCheckBox botoADocumentals;
    private javax.swing.JCheckBox botoAEsports;
    private javax.swing.JCheckBox botoAInfantil;
    private javax.swing.JCheckBox botoAMusica;
    private javax.swing.JCheckBox botoANoticies;
    private javax.swing.JCheckBox botoAPelicules;
    private javax.swing.JCheckBox botoASeries;
    private javax.swing.JCheckBox botoATertulies;
    private javax.swing.JButton botoAcceptar;
    private javax.swing.JCheckBox botoActivarAutoGen;
    private javax.swing.JCheckBox botoActivarPre1;
    private javax.swing.JCheckBox botoActivarPre2;
    private javax.swing.JCheckBox botoActivarPre3;
    private javax.swing.JCheckBox botoActivarPre4;
    private javax.swing.JCheckBox botoActivarProh1;
    private javax.swing.JCheckBox botoActivarProh2;
    private javax.swing.JCheckBox botoActivarProh3;
    private javax.swing.JCheckBox botoActivarProh4;
    private javax.swing.JButton botoCancelar;
    private javax.swing.JComboBox botoCinqueCriteri;
    private javax.swing.JFormattedTextField botoDFi;
    private javax.swing.JFormattedTextField botoDInici;
    private javax.swing.JSpinner botoNombrePlanis;
    private javax.swing.JFormattedTextField botoPreFi1;
    private javax.swing.JFormattedTextField botoPreFi2;
    private javax.swing.JFormattedTextField botoPreFi3;
    private javax.swing.JFormattedTextField botoPreFi4;
    private javax.swing.JFormattedTextField botoPreIni1;
    private javax.swing.JFormattedTextField botoPreIni2;
    private javax.swing.JFormattedTextField botoPreIni3;
    private javax.swing.JFormattedTextField botoPreIni4;
    private javax.swing.JTextField botoPreuMax;
    private javax.swing.JComboBox botoPrimerCriteri;
    private javax.swing.JFormattedTextField botoProhFi1;
    private javax.swing.JFormattedTextField botoProhFi2;
    private javax.swing.JFormattedTextField botoProhFi3;
    private javax.swing.JFormattedTextField botoProhFi4;
    private javax.swing.JFormattedTextField botoProhIni1;
    private javax.swing.JFormattedTextField botoProhIni2;
    private javax.swing.JFormattedTextField botoProhIni3;
    private javax.swing.JFormattedTextField botoProhIni4;
    private javax.swing.JComboBox botoQuartCriteri;
    private javax.swing.JButton botoReset;
    private javax.swing.JComboBox botoSegonCriteri;
    private javax.swing.JComboBox botoTercerCriteri;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
