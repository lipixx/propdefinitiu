/*
 * vistaFacturacion.java
 *
 * @author Rael Garcia Arnés    47808932M
 */

package vistes;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  rael
 */
public class VistaFacturacio extends javax.swing.JPanel {


    private static String[] CabeceraListaFacturas = {"Data Facturació", "Import"};
    private static String[] CabeceraListaDesglose = {"Nom Programa", "Data Emissió", "Preu"};
    

    /*
     * Creates new form vistaExploradorFactures 
     */
    
    public VistaFacturacio() {
        initComponents();
    }

    public void aviso(String inmsg) {
        JOptionPane.showMessageDialog(null, (Object) inmsg);
    }

    /**
     * 
     * @param index 
     */
    
    public void seleccionarFactura(int index){
        lsFacturas.setRowSelectionInterval(index, 0);
    }
    
    public void setListaFacturas(Object[][] inDatos) {
        ((DefaultTableModel) (lsFacturas.getModel())).setDataVector(inDatos, CabeceraListaFacturas);
        lsFacturas.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    public void setListaDesglose(Object[][] inDatos) {
        ((DefaultTableModel) (lsDesglose.getModel())).setDataVector(inDatos, CabeceraListaDesglose);
        lsDesglose.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void setFechaFactura(String inFechaFactura){
        this.etDataFactura.setText(inFechaFactura);
    }
    
    public void setTotal(String inTotal){
        this.tbTotal.setText(inTotal);
    }
         
    
    
    public void setAction(ListSelectionListener lista, ActionListener accion) {
        lsFacturas.getSelectionModel().addListSelectionListener(lista);
        bNovaFactura.addActionListener(accion);
    }
        
    
    public int getIndexFactura(){
        
       if (lsFacturas.getSelectedRowCount() == 1) {
            return lsFacturas.getSelectedRow();
        }
        else if  (lsFacturas.getRowCount()>0){
            return 0; 
        }
       
        return -1;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        lsFacturas = new javax.swing.JTable();
        etTotal = new javax.swing.JLabel();
        tbTotal = new javax.swing.JTextField();
        etTitolFactura1 = new javax.swing.JLabel();
        bSync = new javax.swing.JButton();
        bNovaFactura = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        lsDesglose = new javax.swing.JTable();
        etDataFactura = new javax.swing.JLabel();

        lsFacturas.setBackground(new java.awt.Color(254, 254, 254));
        lsFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Data Factura", "Import"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lsFacturas.setGridColor(new java.awt.Color(254, 254, 254));
        lsFacturas.setInheritsPopupMenu(true);
        lsFacturas.setOpaque(false);
        jScrollPane2.setViewportView(lsFacturas);

        etTotal.setFont(new java.awt.Font("Lucida Grande 16 12 12", 0, 12));
        etTotal.setText("Import Total"); // NOI18N

        tbTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        etTitolFactura1.setFont(new java.awt.Font("Lucida Grande 18 18 18", 1, 18));
        etTitolFactura1.setText("Factura"); // NOI18N

        bSync.setText("Sincronizar"); // NOI18N

        bNovaFactura.setText("Nova Factura"); // NOI18N

        lsDesglose.setBackground(new java.awt.Color(254, 254, 254));
        lsDesglose.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nom Programa", "Data Emissió", "Preu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lsDesglose.setGridColor(new java.awt.Color(254, 254, 254));
        jScrollPane3.setViewportView(lsDesglose);

        etDataFactura.setText("----");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(594, Short.MAX_VALUE)
                .addComponent(etDataFactura)
                .addGap(389, 389, 389))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(79, 79, 79)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(etTotal)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(21, 21, 21))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(etTitolFactura1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bSync)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bNovaFactura))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(etDataFactura)
                .addContainerGap(500, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(etTitolFactura1)
                                .addComponent(bNovaFactura)
                                .addComponent(bSync))
                            .addGap(4, 4, 4)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(etTotal)
                                .addComponent(tbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(6, 6, 6))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
                    .addGap(16, 16, 16)))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bNovaFactura;
    public javax.swing.JButton bSync;
    private javax.swing.JLabel etDataFactura;
    private javax.swing.JLabel etTitolFactura1;
    private javax.swing.JLabel etTotal;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable lsDesglose;
    private javax.swing.JTable lsFacturas;
    private javax.swing.JTextField tbTotal;
    // End of variables declaration//GEN-END:variables
    
}
