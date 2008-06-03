/*
 * VistaPlanificacio2.java
 *
 * Created on 30 de mayo de 2008, 3:00
 */
package vistes;

import domini.tuplaEmissio;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  superman
 */
public class VistaGenerada extends javax.swing.JDialog {

    private String[] headGraella = {"Hora", "Dilluns", "Dimarts", "Dimecres",
        "Dijous", "Divendres", "Dissabte", "Diumenge"
    };

    /** Creates new form VistaPlanificacio2 */
    public VistaGenerada(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    String getPlanSelected() {
        String pla = (String) llistaPlanificacionsV.getSelectedValue();
        return pla;
    }

    String getGraellaSelected() {
        return (String) graella.getValueAt(graella.getSelectedColumn(), graella.getSelectedRow());

    }

    void setSetmana(String setmana) {
        botoSetmana.setText(setmana);
    }

    void setPreu(String preu) {
        preuTotalV.setText(preu);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        graella = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        llistaPlanificacionsV = new javax.swing.JList();
        botoContractar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        preuTotalV = new javax.swing.JTextField();
        butoCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        butoAnular = new javax.swing.JButton();
        butoSetmanaAnterior = new javax.swing.JButton();
        butoSetmanaSeguent = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        botoSetmana = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        graella.setModel(new javax.swing.table.DefaultTableModel(
            new Object [144][8],
            new String [] {
                "Hora","Dilluns", "Dimarts", "Dimecres", "Dijous","Divendres", "Dissabte", "Diumenge"
            }
        ));
        graella.setColumnSelectionAllowed(true);
        graella.setInheritsPopupMenu(true);
        graella.setRowSelectionAllowed(false);
        graella.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(graella);
        graella.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jScrollPane2.setViewportView(llistaPlanificacionsV);

        botoContractar.setText("CONTRACTAR");

        jLabel3.setText("Preu Total:");

        preuTotalV.setEditable(false);

        butoCancelar.setText("Cancelar");
        butoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butoCancelarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel4.setText("Llista Planificacions");

        butoAnular.setText("Anul·lar Programa");

        butoSetmanaAnterior.setText(" << Setmana anterior");

        butoSetmanaSeguent.setText("Setmana següent >>");

        jLabel1.setText("€");

        botoSetmana.setEditable(false);
        botoSetmana.setAlignmentX(1.0F);
        botoSetmana.setAlignmentY(1.0F);
        jScrollPane3.setViewportView(botoSetmana);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(butoSetmanaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(butoSetmanaSeguent)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(butoCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                        .addComponent(botoContractar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel4)))
                .addGap(28, 28, 28))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(preuTotalV, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                .addComponent(butoAnular)
                .addGap(414, 414, 414))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addComponent(butoSetmanaAnterior))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(butoSetmanaSeguent))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoContractar)
                            .addComponent(butoCancelar)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(butoAnular)
                    .addComponent(preuTotalV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(86, 86, 86))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1044, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 572, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void butoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butoCancelarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_butoCancelarActionPerformed

    public void setLlistaPlans(String[] llistaPlanificacions) {
        llistaPlanificacionsV.setListData(llistaPlanificacions);
    }

    public void pintarGraella(String[][] grill) {

        for (int i = 0; i < 144; i++) {
            for (int j = 0; j < 8; j++) {
                graella.setValueAt(grill[i][j], i, j);
            }
        }
    }

    public void setGraella() {

        String[][] graellaAuxiliar = new String[144][8];
        graella.setModel(new javax.swing.table.DefaultTableModel(
                graellaAuxiliar,
                new String[]{
            "Hora", "Dilluns", "Dimarts", "Dimecres", "Dijous", "Divendres", "Dissabte", "Diumenge"
        }));

    }

    public void setActions(ActionListener actions[], ListSelectionListener lPlanificacions) {

        llistaPlanificacionsV.getSelectionModel().addListSelectionListener(lPlanificacions);
        butoSetmanaAnterior.addActionListener(actions[0]);
        butoSetmanaSeguent.addActionListener(actions[1]);
        botoContractar.addActionListener(actions[2]);
        butoAnular.addActionListener(actions[3]);

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botoContractar;
    private javax.swing.JTextPane botoSetmana;
    private javax.swing.JButton butoAnular;
    private javax.swing.JButton butoCancelar;
    private javax.swing.JButton butoSetmanaAnterior;
    private javax.swing.JButton butoSetmanaSeguent;
    private javax.swing.JTable graella;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList llistaPlanificacionsV;
    private javax.swing.JTextField preuTotalV;
    // End of variables declaration//GEN-END:variables
    /*void pintarGraella(String[][] novaGraella) {
    ((DefaultTableModel) (graella.getModel())).setDataVector(novaGraella, headGraella);
    }*/
    /* void pintarGraella(tuplaEmissio[] tEmissio) throws ParseException {
    Calendar rightNow = Calendar.getInstance();
    Calendar inici = Calendar.getInstance();
    Calendar fi = Calendar.getInstance();
    int ara = rightNow.get(Calendar.DAY_OF_WEEK);
    // SUNDAY=0, MONDAY=1, TUESDAY=2, WEDNESDAY=3, THURSDAY=4, FRIDAY=5 and SATURDAY=6 
    int sumar = 0;
    if (ara == 0) {
    sumar = 6;
    } else {
    sumar = ara - 1;
    }
    inici.add(Calendar.DAY_OF_MONTH, -(sumar));
    int dia = inici.get(Calendar.DAY_OF_MONTH);
    int mes = inici.get(Calendar.MONTH);
    int any = inici.get(Calendar.YEAR);
    SimpleDateFormat formatCalendar = new SimpleDateFormat("dd-MM-yyyy");
    Date dateInici = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
    inici = Calendar.getInstance();
    inici.setTime(dateInici);
    Date dateFi = formatCalendar.parse("" + dia + "-" + mes + "-" + any);
    fi = Calendar.getInstance();
    fi.setTime(dateFi);
    fi.add(Calendar.DAY_OF_MONTH, +7);
    }*/
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                VistaGenerada dialog = new VistaGenerada(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
}