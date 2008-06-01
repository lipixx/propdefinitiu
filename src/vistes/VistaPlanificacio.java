/*
 * kVistaPlanificacio.java
 *
 * Created on 28 de mayo de 2008, 17:45
 */
package vistes;

import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author  Josep Marti
 */
public class VistaPlanificacio extends javax.swing.JPanel {

    /** Creates new form kVistaPlanificacio */
    public VistaPlanificacio() {
        initComponents();
    }

    public void setLlistaPlans(String[] llistaPlanificacions) {
        /* Col·loca la llistaPlanificacions dins la JList llistaPlanificacionsV */
        llistaPlanificacionsV.setListData(llistaPlanificacions);
    }

    String getGraellaSelected() {
        /* Retorna un String que identifica una Emissio */
        return (String) graella.getValueAt(graella.getSelectedColumn(), graella.getSelectedRow());
    }

    String getPlanSelected() {

        String pla = (String) llistaPlanificacionsV.getSelectedValue();
        return pla;
    }

    public void setGraella() {
        /* ?¿ */
        String[][] graellaAuxiliar = new String[144][7];
        graella.setModel(new javax.swing.table.DefaultTableModel(
                graellaAuxiliar,
                new String[]{
            "Dilluns", "Dimarts", "Dimecres", "Dijous", "Divendres", "Dissabte", "Diumenge"
        }));

    }

    public void setActions(ActionListener actions[], ListSelectionListener lPlanificacions) {

        llistaPlanificacionsV.getSelectionModel().addListSelectionListener(lPlanificacions);
        botoNovaPlanificacio.addActionListener(actions[0]);
        botoSetmanaAnterior.addActionListener(actions[1]);
        botoSetmanaSeguent.addActionListener(actions[2]);
        botoAnular.addActionListener(actions[3]);

    }

    void pintarGraella(String[][] grill) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 144; j++) {
                graella.setValueAt(grill[i][j], i, j);
            }
        }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        graella = new javax.swing.JTable();
        botoNovaPlanificacio = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        llistaPlanificacionsV = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        preuTotalV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        botoAnular = new javax.swing.JButton();
        botoSetmanaAnterior = new javax.swing.JButton();
        botoSetmanaSeguent = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        botoSetmana = new javax.swing.JTextPane();

        graella.setModel(new javax.swing.table.DefaultTableModel(
            new Object [144][8],
            new String [] {
                "Hora","Dilluns", "Dimarts", "Dimecres", "Dijous","Divendres", "Dissabte", "Diumenge"
            }
        ));
        graella.setCellSelectionEnabled(true);
        graella.setInheritsPopupMenu(true);
        jScrollPane1.setViewportView(graella);

        botoNovaPlanificacio.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 12));
        botoNovaPlanificacio.setText("CREAR NOVA PLANIFICACIO");

        jScrollPane2.setViewportView(llistaPlanificacionsV);

        jLabel3.setText("Preu Total:");

        preuTotalV.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 10));
        jLabel4.setText("Llista Planificacions");

        botoAnular.setText("Anul·lar Programa");

        botoSetmanaAnterior.setText(" << Setmana anterior");

        botoSetmanaSeguent.setText("Setmana següent >>");

        jLabel1.setText("€");

        botoSetmana.setEditable(false);
        botoSetmana.setAlignmentX(1.0F);
        botoSetmana.setAlignmentY(1.0F);
        jScrollPane3.setViewportView(botoSetmana);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(botoSetmanaAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(botoSetmanaSeguent)
                        .addGap(91, 91, 91)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(botoNovaPlanificacio))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel4)))
                .addGap(28, 28, 28))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(preuTotalV, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                .addComponent(botoAnular)
                .addGap(414, 414, 414))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(botoSetmanaAnterior)
                    .addComponent(botoSetmanaSeguent)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(12, 12, 12)
                        .addComponent(botoNovaPlanificacio)
                        .addGap(31, 31, 31))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(botoAnular)
                    .addComponent(preuTotalV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(86, 86, 86))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botoAnular;
    private javax.swing.JButton botoNovaPlanificacio;
    private javax.swing.JTextPane botoSetmana;
    private javax.swing.JButton botoSetmanaAnterior;
    private javax.swing.JButton botoSetmanaSeguent;
    private javax.swing.JTable graella;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList llistaPlanificacionsV;
    private javax.swing.JTextField preuTotalV;
    // End of variables declaration//GEN-END:variables
}
