/**
 * La classe VistaCriteris correspon a la pantalla en le que es seleccionen els
 * criteris per generar una nova planificacio. Aquesta classe reunira tots aquests
 * criteris i els retornara en forma de tuplaCriteris.
 * 
 * @author  Felip Moll 41743858P
 * @author  Josep Marti 41743212Y
 * @version 1.0, 6 Juny 2008 
 * 
 * Versio Final
 */
package vistes;

import domini.Convertir;
import domini.tuplaCriteris;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;

public class VistaCriteris extends javax.swing.JDialog {

    private tuplaCriteris criteris;
    private Calendar iniPeriodePlani,  fiPeriodePlani;
    int comptador;
    SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
    SimpleDateFormat formatCalendar = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatNombre = NumberFormat.getNumberInstance();
    public Convertir Conv;

    //Per les caixes de text
    DateFormatter formatadorCalendar = new DateFormatter(formatCalendar);
    DateFormatter formatadorHora = new DateFormatter(formatHora);
    NumberFormatter formatadorNombre;

    public VistaCriteris(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        formatNombre.setMinimumFractionDigits(2);
        formatadorNombre = new NumberFormatter(formatNombre);
        Conv = new Convertir();

        initComponents();
        criteris = new tuplaCriteris();
        iniPeriodePlani = Calendar.getInstance();
        fiPeriodePlani = Calendar.getInstance();
        comptador = 0;
    }

    /** Aquesta funcio es cridada quan el kVistaPlanificacio obre aquesta finestra.
     *  Li dona la data de la ultima planificacio de l'usuari, i aquesta s'ha de setejar
     *  a la caixa corresponent iniPeriodePlani. Tambe es seteja 7 dies mes tart fiPeriodePlani.
     * @param dataIni Data de inici de la ultima planificacio
     */
    public void setDataUltimaPlani(String dataIniUp) {
        if (!dataIniUp.equalsIgnoreCase("")) {
            iniPeriodePlani = Conv.strToCalendar(dataIniUp);
        } else {
            iniPeriodePlani = Calendar.getInstance();
        }

        fiPeriodePlani = (Calendar) iniPeriodePlani.clone();
        Conv.sumaDies(fiPeriodePlani, 7);
        botoDInici.setText(Conv.dateToStr(iniPeriodePlani));
        botoDFi.setText(Conv.dateToStr(fiPeriodePlani));
    }

    /**
     * Aquesta funcio es crida quan es clica el boto d'acceptacio. Recull tots els criteris
     * que s'han especificat a les vistes.
     * @return Una tupla amb els criteris especificats.
     * @throws java.text.ParseException
     */
    public tuplaCriteris getCriteris() throws ParseException {

        criteris = new tuplaCriteris();
        boolean correcte = true;

        /**Agafem el preu*/
        criteris.preuMaxim = (float) Integer.parseInt(casellaPreuMax.getText());

        if (criteris.preuMaxim < 0) {
            criteris.preuMaxim = 0;
            JOptionPane.showMessageDialog(null, "Preu negatiu, s'agafa com a 0");
        }

        /**Agafem el nombre de planificacions que es volen*/
        criteris.nombrePlanis = (Integer) botoNombrePlanis.getValue();

        /**Agafem les franges*/
        if (!agafaHores()) {
            return null;
        }

        /**Agafem els filtres i l'autogeneracio*/
        criteris.autoGen = botoActivarAutoGen.isSelected();
        if (criteris.autoGen) {
            criteris.filtres[0] = botoAAltres.isSelected();
            criteris.filtres[1] = botoAAdults.isSelected();
            criteris.filtres[2] = botoAConcursos.isSelected();
            criteris.filtres[3] = botoADocumentals.isSelected();
            criteris.filtres[4] = botoAEsports.isSelected();
            criteris.filtres[5] = botoAInfantil.isSelected();
            criteris.filtres[6] = botoAMusica.isSelected();
            criteris.filtres[7] = botoANoticies.isSelected();
            criteris.filtres[8] = botoAPelicules.isSelected();
            criteris.filtres[9] = botoASeries.isSelected();
            criteris.filtres[10] = botoATertulies.isSelected();
        }

        /**Agafem la data de la planificacio*/
        if (botoDInici == null || botoDFi == null) {
            JOptionPane.showMessageDialog(null, "El periode de la planificacio ha d'estar definit");
            return null;
        } else {
            Calendar avui;
            criteris.dataIni = Conv.strToCalendar(botoDInici.getText());
            criteris.dataFi = Conv.strToCalendar(botoDFi.getText());

            avui = Calendar.getInstance();

            //iniPlani ha de ser superior a avui i a la data de la ultima planificacio de lusuari
            // fiPlani superior a iniPlani
            //|| criteris.dataIni.after(criteris.dataFi)
            if (Conv.comparacioData(criteris.dataIni, avui) == -1 || Conv.comparacioData(criteris.dataIni, iniPeriodePlani) == -1) {
                JOptionPane.showMessageDialog(null, "El periode inicial de la planificacio no pot ser anterior al dia d'avui \n ni a la data de la ultima planificacio.");
                return null;
            }
        }

        if (!setPrioritats()) {
            JOptionPane.showMessageDialog(null, "Has de setejar o tots o cap criteri");
            return null;
        }

        //   reordenaCriteris();
        if (correcte) {
            return criteris;
        }
        return null;
    }

    /**
     * Aquesta funcio agafa l'apartat de les franges prohibides i preferides.
     * @return Retorna cert si tot es coherent. Fals altrament.
     */
    private boolean agafaHores() throws ParseException {
        Date hora = formatHora.parse("00:00");

        /**Per cada casella comprovem si esta seleccionada, i si ho esta passem
         * a obtenir-ne les dades.
         */
        if (botoActivarPre1.isSelected()) {

            criteris.pre1Ini = Conv.strToCalendar(botoPreIni1.getText());
            criteris.pre1Fi = Conv.strToCalendar(botoPreFi1.getText());

            if (!Conv.horaMajor(criteris.pre1Fi, criteris.pre1Ini)) {
                JOptionPane.showMessageDialog(null, "La hora de fi ha de ser posterior a la hora d'inici.");
                return false;
            }
        }

        if (botoActivarPre2.isSelected()) {

            criteris.pre2Ini = Conv.strToCalendar(botoPreIni2.getText());
            criteris.pre2Fi = Conv.strToCalendar(botoPreFi2.getText());

            if (!Conv.horaMajor(criteris.pre2Fi, criteris.pre2Ini)) {
                JOptionPane.showMessageDialog(null, "La hora de fi ha de ser posterior a la hora d'inici.");
                return false;
            }
        }
        if (botoActivarPre3.isSelected()) {

            criteris.pre3Ini = Conv.strToCalendar(botoPreIni3.getText());
            criteris.pre3Fi = Conv.strToCalendar(botoPreFi3.getText());

            if (!Conv.horaMajor(criteris.pre3Fi, criteris.pre3Ini)) {
                JOptionPane.showMessageDialog(null, "La hora de fi ha de ser posterior a la hora d'inici");
                return false;
            }
        }
        if (botoActivarPre4.isSelected()) {
            criteris.pre4Ini = Conv.strToCalendar(botoPreIni4.getText());
            criteris.pre4Fi = Conv.strToCalendar(botoPreFi4.getText());

            if (!Conv.horaMajor(criteris.pre4Fi, criteris.pre4Ini)) {
                JOptionPane.showMessageDialog(null, "La hora de fi ha de ser posterior a la hora d'inici");
                return false;
            }
        }
        if (botoActivarProh1.isSelected()) {

            criteris.proh1Ini = Conv.strToCalendar(botoProhIni1.getText());
            criteris.proh1Fi = Conv.strToCalendar(botoProhFi1.getText());

            if (!Conv.horaMajor(criteris.proh1Fi, criteris.proh1Ini)) {
                JOptionPane.showMessageDialog(null, "La hora de fi ha de ser posterior a la hora d'inici");
                return false;
            }
        }
        if (botoActivarProh2.isSelected()) {

            criteris.proh2Ini = Conv.strToCalendar(botoProhIni2.getText());
            criteris.proh2Fi = Conv.strToCalendar(botoProhFi2.getText());

            if (!Conv.horaMajor(criteris.proh2Fi, criteris.proh2Ini)) {
                JOptionPane.showMessageDialog(null, "La hora de fi ha de ser posterior a la hora d'inici");
                return false;
            }
        }

        if (botoActivarProh3.isSelected()) {

            criteris.proh3Ini = Conv.strToCalendar(botoProhIni3.getText());
            criteris.proh3Fi = Conv.strToCalendar(botoProhFi3.getText());

            if (!Conv.horaMajor(criteris.proh3Fi, criteris.proh3Ini)) {
                JOptionPane.showMessageDialog(null, "La hora de fi ha de ser posterior a la hora d'inici");
                return false;
            }
        }
        if (botoActivarProh4.isSelected()) {

            criteris.proh4Ini = Conv.strToCalendar(botoProhIni4.getText());
            criteris.proh4Fi = Conv.strToCalendar(botoProhFi4.getText());

            if (!Conv.horaMajor(criteris.proh4Fi, criteris.proh4Ini)) {
                JOptionPane.showMessageDialog(null, "La hora de fi ha de ser posterior a la hora d'inici");
                return false;
            }
        }
        return true;
    }

    private boolean setPrioritats() {
        criteris.prioritats[0] = getIndexCriteri((String) botoPrimerCriteri.getSelectedItem());
        criteris.prioritats[1] = getIndexCriteri((String) botoSegonCriteri.getSelectedItem());
        criteris.prioritats[2] = getIndexCriteri((String) botoTercerCriteri.getSelectedItem());
        criteris.prioritats[3] = getIndexCriteri((String) botoQuartCriteri.getSelectedItem());
        criteris.prioritats[4] = getIndexCriteri((String) botoCinqueCriteri.getSelectedItem());

        boolean correcte = true;

        if (criteris.prioritats[0] == 0) {
            criteris.prioritats[0] = 1;
            criteris.prioritats[1] = 2;
            criteris.prioritats[2] = 3;
            criteris.prioritats[3] = 4;
            criteris.prioritats[4] = 5;
        } else {
            for (int i = 1; i < 5; i++) {
                if (criteris.prioritats[i] == 0) {
                    correcte = false;
                }

            }
        }
        return correcte;
    }

    private int getIndexCriteri(String criteri) {
        if (criteri.equalsIgnoreCase("Preu")) {
            return 1;
        }
        if (criteri.equalsIgnoreCase("Franja Preferida")) {
            return 2;
        }
        if (criteri.equalsIgnoreCase("Franja Prohibida")) {
            return 3;
        }
        if (criteri.equalsIgnoreCase("Programes Seleccionats")) {
            return 4;
        }
        if (criteri.equalsIgnoreCase("Periode Planificacio")) {
            return 5;
        }
        return 0;
    }

    private String[] agafarModel(ComboBoxModel model) {
        int mida = model.getSize();
        String nouModel[] = new String[mida];

        for (int i = 0; i < mida; i++) {
            nouModel[i] = (String) model.getElementAt(i);
        }
        return nouModel;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
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
        botoPreIni1 = new javax.swing.JFormattedTextField(formatadorHora);
        botoPreFi1 = new javax.swing.JFormattedTextField(formatadorHora);
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        botoActivarPre1 = new javax.swing.JCheckBox();
        botoPreIni2 = new javax.swing.JFormattedTextField(formatadorHora);
        botoPreIni3 = new javax.swing.JFormattedTextField(formatadorHora);
        botoPreIni4 = new javax.swing.JFormattedTextField(formatadorHora);
        botoPreFi2 = new javax.swing.JFormattedTextField(formatadorHora);
        botoPreFi3 = new javax.swing.JFormattedTextField(formatadorHora);
        botoPreFi4 = new javax.swing.JFormattedTextField(formatadorHora);
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
        botoProhIni4 = new javax.swing.JFormattedTextField(formatadorHora);
        jLabel23 = new javax.swing.JLabel();
        botoProhIni3 = new javax.swing.JFormattedTextField(formatadorHora);
        jLabel24 = new javax.swing.JLabel();
        botoProhIni2 = new javax.swing.JFormattedTextField(formatadorHora);
        jLabel25 = new javax.swing.JLabel();
        botoProhIni1 = new javax.swing.JFormattedTextField(formatadorHora);
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        botoProhFi1 = new javax.swing.JFormattedTextField(formatadorHora);
        botoProhFi2 = new javax.swing.JFormattedTextField(formatadorHora);
        botoProhFi3 = new javax.swing.JFormattedTextField(formatadorHora);
        botoProhFi4 = new javax.swing.JFormattedTextField(formatadorHora);
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
        botoDInici = new javax.swing.JFormattedTextField(formatadorCalendar);
        botoDFi = new javax.swing.JFormattedTextField(formatadorCalendar);
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        botoNombrePlanis = new javax.swing.JSpinner();
        jLabel36 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        casellaPreuMax = new javax.swing.JFormattedTextField();
        botoAAltres = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel1.setText("Preu Maxim:");

        jLabel2.setText("€");

        jLabel3.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel3.setText("Prioritat dels criteris:");

        botoPrimerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ---- ", "Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio" }));
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

        botoPreIni1.setText("00:00");
        botoPreIni1.setEnabled(false);
        botoPreIni1.setInputVerifier(new Verificador());

        botoPreFi1.setText("23:59");
        botoPreFi1.setEnabled(false);
        botoPreFi1.setInputVerifier(new Verificador());

        jLabel10.setText("Hora Inici:");

        jLabel11.setText("Hora fi:");

        jLabel12.setText("Activar");

        botoActivarPre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoActivarPre1ActionPerformed(evt);
            }
        });

        botoPreIni2.setText("00:00");
        botoPreIni2.setEnabled(false);
        botoPreIni2.setInputVerifier(new Verificador());

        botoPreIni3.setText("00:00");
        botoPreIni3.setEnabled(false);
        botoPreIni3.setInputVerifier(new Verificador());

        botoPreIni4.setText("00:00");
        botoPreIni4.setEnabled(false);
        botoPreIni4.setInputVerifier(new Verificador());

        botoPreFi2.setText("23:59");
        botoPreFi2.setEnabled(false);
        botoPreFi2.setInputVerifier(new Verificador());

        botoPreFi3.setText("23:59");
        botoPreFi3.setEnabled(false);
        botoPreFi3.setInputVerifier(new Verificador());

        botoPreFi4.setText("23:59");
        botoPreFi4.setEnabled(false);
        botoPreFi4.setInputVerifier(new Verificador());

        jLabel13.setText("Hora fi:");

        jLabel14.setText("Hora fi:");

        jLabel15.setText("Hora fi:");

        jLabel16.setText("Hora Inici:");

        jLabel17.setText("Hora Inici:");

        jLabel18.setText("Hora Inici:");

        botoActivarPre3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoActivarPre3ActionPerformed(evt);
            }
        });

        botoActivarPre4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoActivarPre4ActionPerformed(evt);
            }
        });

        botoActivarPre2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoActivarPre2ActionPerformed(evt);
            }
        });

        jLabel19.setText("Hora Inici:");

        jLabel20.setText("Hora Inici:");

        jLabel21.setText("Hora Inici:");

        jLabel22.setText("Hora Inici:");

        botoProhIni4.setText("00:00");
        botoProhIni4.setEnabled(false);
        botoProhIni4.setInputVerifier(new Verificador());

        jLabel23.setText("Hora fi:");

        botoProhIni3.setText("00:00");
        botoProhIni3.setEnabled(false);
        botoProhIni3.setInputVerifier(new Verificador());

        jLabel24.setText("Hora fi:");

        botoProhIni2.setText("00:00");
        botoProhIni2.setEnabled(false);
        botoProhIni2.setInputVerifier(new Verificador());

        jLabel25.setText("Hora fi:");

        botoProhIni1.setText("00:00");
        botoProhIni1.setEnabled(false);
        botoProhIni1.setInputVerifier(new Verificador());

        jLabel26.setText("Hora fi:");

        jLabel27.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel27.setText("Franges Prohibides:");

        jLabel28.setText("Hora fi:");

        botoProhFi1.setText("23:59");
        botoProhFi1.setEnabled(false);
        botoProhFi1.setInputVerifier(new Verificador());

        botoProhFi2.setText("23:59");
        botoProhFi2.setEnabled(false);
        botoProhFi2.setInputVerifier(new Verificador());

        botoProhFi3.setText("23:59");
        botoProhFi3.setEnabled(false);
        botoProhFi3.setInputVerifier(new Verificador());

        botoProhFi4.setText("23:59");
        botoProhFi4.setEnabled(false);
        botoProhFi4.setInputVerifier(new Verificador());

        jLabel29.setText("Activar");

        botoActivarProh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoActivarProh1ActionPerformed(evt);
            }
        });

        botoActivarProh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoActivarProh2ActionPerformed(evt);
            }
        });

        botoActivarProh3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoActivarProh3ActionPerformed(evt);
            }
        });

        botoActivarProh4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoActivarProh4ActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 12));
        jLabel30.setText("AUTOGENERACIÓ");

        jLabel31.setText("Filtratge per tipus:");

        botoASeries.setText("Sèries");

        botoAConcursos.setText("Concursos");

        botoANoticies.setText("Notícies");

        botoAInfantil.setText("Infantil");

        botoAPelicules.setText("Pel·licula");

        botoADocumentals.setText("Documentals");

        botoAEsports.setText("Esports");

        botoAMusica.setText("Música");

        botoATertulies.setText("Tertúlies");

        botoAAdults.setText("Adults");

        jLabel32.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel32.setText("Activar Auto Generació ?");

        botoAcceptar.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 12));
        botoAcceptar.setText("Accepta");

        botoCancelar.setText("Cancel·la");
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

        Calendar avui = Calendar.getInstance();
        botoDInici.setValue(new Date());
        botoDInici.setInputVerifier(new Verificador());

        avui = Calendar.getInstance();
        botoDFi.setValue(new Date());
        botoDFi.setInputVerifier(new Verificador());

        jLabel33.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel33.setText("Inici Període planificació");

        jLabel34.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel34.setText("Fi Període planificació");

        botoNombrePlanis.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));

        jLabel36.setText("Nombre màxim planificacions:");

        casellaPreuMax.setText("250");

        botoAAltres.setText("Altres");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(83, 83, 83)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(82, 82, 82)
                                            .addComponent(casellaPreuMax, 0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(7, 7, 7)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(botoDInici)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                .addComponent(jLabel33))))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                                            .addComponent(jLabel36)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(botoNombrePlanis, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(99, 99, 99)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(botoDFi, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel34))))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(65, 65, 65)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(botoTercerCriteri, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(botoSegonCriteri, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botoQuartCriteri, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botoCinqueCriteri, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botoPrimerCriteri, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(botoReset))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(botoCancelar)
                        .addGap(44, 44, 44)
                        .addComponent(botoAcceptar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31)
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
                                            .addComponent(botoAAltres))
                                        .addGap(35, 35, 35)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(botoAAdults)
                                            .addComponent(botoAEsports)
                                            .addComponent(botoAMusica)
                                            .addComponent(botoATertulies)
                                            .addComponent(botoADocumentals)))
                                    .addComponent(botoAPelicules, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel30))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(45, Short.MAX_VALUE))
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
                                .addComponent(botoPreFi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(36, 36, 36)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(botoActivarPre2)
                    .addComponent(botoActivarPre1)
                    .addComponent(botoActivarPre3)
                    .addComponent(botoActivarPre4))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botoActivarProh2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botoProhFi3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                            .addComponent(botoActivarProh1)
                            .addComponent(jLabel29)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botoProhIni4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botoProhFi4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botoActivarProh4)))
                .addContainerGap(275, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(botoReset)
                            .addComponent(botoPrimerCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(botoCinqueCriteri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoADocumentals)
                            .addComponent(botoAAltres))
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
                        .addGap(7, 7, 7)
                        .addComponent(botoAPelicules)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(botoActivarAutoGen))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26)
                                .addComponent(jLabel22)
                                .addComponent(botoProhIni1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(botoProhFi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                    .addComponent(botoProhFi4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel28))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(botoProhFi2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botoActivarProh2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botoActivarPre3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botoActivarPre4)))))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botoAcceptar)
                    .addComponent(botoCancelar))
                .addGap(51, 51, 51))
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botoDFi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botoDInici, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(casellaPreuMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botoNombrePlanis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(508, 508, 508))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**Action listeners*/
    public void setActions(ActionListener actions) {
        botoAcceptar.addActionListener(actions);
    }

    /**Funcions dels botons*/
    /**Boto Cancelar*/
    private void botoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoCancelarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_botoCancelarActionPerformed

    /**Botons del comboBox dels criteris*/
    private void botoPrimerCriteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoPrimerCriteriActionPerformed

        switch (botoPrimerCriteri.getSelectedIndex()) {
            case 0:
                botoSegonCriteri.setEnabled(false);
                botoTercerCriteri.setEnabled(false);
                botoQuartCriteri.setEnabled(false);
                botoCinqueCriteri.setEnabled(false);
                break;
            case 1:
                botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"----", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"}));
                break;
            case 2:
                botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"----", "Preu", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"}));
                break;
            case 3:
                botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"----", "Preu", "Franja Preferida", "Programes Seleccionats", "Periode Planificacio"}));
                break;
            case 4:
                botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"----", "Preu", "Franja Preferida", "Franja Prohibida", "Periode Planificacio"}));
                break;
            case 5:
                botoSegonCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"----", "Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats"}));
                break;
            default:
                break;
        }
        botoSegonCriteri.setSelectedIndex(0);
        botoSegonCriteri.setEnabled(true);
        botoTercerCriteri.setEnabled(false);
        botoQuartCriteri.setEnabled(false);
        botoCinqueCriteri.setEnabled(false);
    }//GEN-LAST:event_botoPrimerCriteriActionPerformed

    private void botoSegonCriteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoSegonCriteriActionPerformed

        String[] valorsCombo = agafarModel(botoSegonCriteri.getModel());

        switch (botoSegonCriteri.getSelectedIndex()) {
            case 0:
                botoTercerCriteri.setEnabled(false);
                botoQuartCriteri.setEnabled(false);
                botoCinqueCriteri.setEnabled(false);
                break;
            case 1:
                botoTercerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{valorsCombo[0], valorsCombo[2], valorsCombo[3], valorsCombo[4]}));
                break;
            case 2:
                botoTercerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{valorsCombo[0], valorsCombo[1], valorsCombo[3], valorsCombo[4]}));
                break;
            case 3:
                botoTercerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{valorsCombo[0], valorsCombo[1], valorsCombo[2], valorsCombo[4]}));
                break;
            case 4:
                botoTercerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{valorsCombo[0], valorsCombo[1], valorsCombo[2], valorsCombo[3]}));
                break;

        }
        botoTercerCriteri.setSelectedIndex(0);
        botoTercerCriteri.setEnabled(true);
        botoQuartCriteri.setEnabled(false);
        botoCinqueCriteri.setEnabled(false);

    }//GEN-LAST:event_botoSegonCriteriActionPerformed
    private void botoTercerCriteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoTercerCriteriActionPerformed

        String[] valorsCombo = agafarModel(botoTercerCriteri.getModel());

        switch (botoTercerCriteri.getSelectedIndex()) {
            case 0:
                botoQuartCriteri.setEnabled(false);
                botoCinqueCriteri.setEnabled(false);
                break;
            case 1:
                botoQuartCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{valorsCombo[0], valorsCombo[2], valorsCombo[3]}));
                break;
            case 2:
                botoQuartCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{valorsCombo[0], valorsCombo[1], valorsCombo[3]}));
                break;
            case 3:
                botoQuartCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{valorsCombo[0], valorsCombo[2], valorsCombo[1]}));
                break;
        }
        botoQuartCriteri.setSelectedIndex(0);
        botoQuartCriteri.setEnabled(true);
        botoCinqueCriteri.setEnabled(false);
    }//GEN-LAST:event_botoTercerCriteriActionPerformed
    private void botoQuartCriteriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoQuartCriteriActionPerformed

        String[] valorsCombo = agafarModel(botoQuartCriteri.getModel());

        switch (botoQuartCriteri.getSelectedIndex()) {
            case 0:
                botoCinqueCriteri.setEnabled(false);
                break;
            case 1:
                botoCinqueCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{valorsCombo[0], valorsCombo[2]}));
                break;
            case 2:
                botoCinqueCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{valorsCombo[0], valorsCombo[1]}));
                break;
        }
        botoCinqueCriteri.setSelectedIndex(0);
        botoCinqueCriteri.setEnabled(true);
    }//GEN-LAST:event_botoQuartCriteriActionPerformed

    /**Reset criteris*/
    private void botoResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoResetActionPerformed

        botoPrimerCriteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{" ---- ", "Preu", "Franja Preferida", "Franja Prohibida", "Programes Seleccionats", "Periode Planificacio"}));
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

    private void botoActivarPre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoActivarPre1ActionPerformed
        botoPreIni1.setEnabled(!botoPreIni1.isEnabled());
        botoPreFi1.setEnabled(!botoPreFi1.isEnabled());
    }//GEN-LAST:event_botoActivarPre1ActionPerformed

    private void botoActivarPre2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoActivarPre2ActionPerformed
        botoPreIni2.setEnabled(!botoPreIni2.isEnabled());
        botoPreFi2.setEnabled(!botoPreFi2.isEnabled());
    }//GEN-LAST:event_botoActivarPre2ActionPerformed

    private void botoActivarPre3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoActivarPre3ActionPerformed
        botoPreIni3.setEnabled(!botoPreIni3.isEnabled());
        botoPreFi3.setEnabled(!botoPreFi3.isEnabled());
    }//GEN-LAST:event_botoActivarPre3ActionPerformed

    private void botoActivarPre4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoActivarPre4ActionPerformed
        botoPreIni4.setEnabled(!botoPreIni4.isEnabled());
        botoPreFi4.setEnabled(!botoPreFi4.isEnabled());
    }//GEN-LAST:event_botoActivarPre4ActionPerformed

    private void botoActivarProh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoActivarProh1ActionPerformed
        botoProhIni1.setEnabled(!botoProhIni1.isEnabled());
        botoProhFi1.setEnabled(!botoProhFi1.isEnabled());
    }//GEN-LAST:event_botoActivarProh1ActionPerformed

    private void botoActivarProh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoActivarProh2ActionPerformed
        botoProhIni2.setEnabled(!botoProhIni2.isEnabled());
        botoProhFi2.setEnabled(!botoProhFi2.isEnabled());
    }//GEN-LAST:event_botoActivarProh2ActionPerformed

    private void botoActivarProh3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoActivarProh3ActionPerformed
        botoProhIni3.setEnabled(!botoProhIni3.isEnabled());
        botoProhFi3.setEnabled(!botoProhFi3.isEnabled());
    }//GEN-LAST:event_botoActivarProh3ActionPerformed

    private void botoActivarProh4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoActivarProh4ActionPerformed
        botoProhIni4.setEnabled(!botoProhIni4.isEnabled());
        botoProhFi4.setEnabled(!botoProhFi4.isEnabled());
    }//GEN-LAST:event_botoActivarProh4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox botoAAdults;
    private javax.swing.JCheckBox botoAAltres;
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
    private javax.swing.JFormattedTextField casellaPreuMax;
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
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
