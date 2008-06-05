/*
 * vistaNovaFactura.java
 * @author Rael Garcia Arnés    47808932M
 */
package vistes;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.Calendar;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import domini.Convertir;
import java.util.Date;
/**
 *
 * @author  rael
 */
@SuppressWarnings("unchecked")
public class VistaNovaFactura extends javax.swing.JFrame {

    private Convertir Conv;
    private static String[] CabeceraListaPendientes = {"ID", "Nom Programa", "Preu Base", "Data Emissió", "Preu"};
    private static String[] CabeceraListaParaFacturar = {"ID", "Nom Programa", "Data Emissió", "Preu"};
    Object[][] listaParaFacturar;
    LinkedList<Integer> llistaSeleccionats;
    int i = 0;

    /** Creates new form vistaNovaFactura */
    public VistaNovaFactura() {
        initComponents();
        llistaSeleccionats = new LinkedList<Integer>();
        Conv = new Convertir();
    }

    public void aviso(String inmsg) {
        JOptionPane.showMessageDialog(null, (Object) inmsg);
    }

// Consultoras
    public String getAutofacturaPreuMax() {
        return tbAutofacturaPreu.getText();
    }

    public Calendar getAutofacturaPeriodeInici() 
    {
        return Conv.dateToCalendar((Date) spPeriodoInicio.getValue());
    }

    public Calendar getAutofacturaPeriodeFi() 
    { 
        return Conv.dateToCalendar((Date) spPeriodoFin.getValue());
    }

// Cosas de la vista
    public void setAction(ActionListener[] acciones, WindowListener ventana) {
        bAutofacturaPreu.addActionListener(acciones[0]);
        bAutofacturaPeriode.addActionListener(acciones[1]);
        bFacturar.addActionListener(acciones[2]);
    }

// Lista Pendientes //
    public void setListaPendientes(Object[][] inDatos) {

        ((DefaultTableModel) (lsProgramesPendents.getModel())).setDataVector(inDatos, CabeceraListaPendientes);
        lsProgramesPendents.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaParaFacturar = new Object[lsProgramesPendents.getRowCount()][4];
    }

    public String[] getSeleccionadoListaPendientes() {
        String[] e = new String[4];
        
        e[0] = String.valueOf(-1);
        
        if (lsProgramesPendents.getSelectedRowCount() == 1) {
            e[0] = String.valueOf(lsProgramesPendents.getModel().getValueAt(lsProgramesPendents.getSelectedRow(), 0));
            e[1] = (String) lsProgramesPendents.getModel().getValueAt(lsProgramesPendents.getSelectedRow(), 1);
            e[2] = (String) lsProgramesPendents.getModel().getValueAt(lsProgramesPendents.getSelectedRow(), 3);
            e[3] = String.valueOf(lsProgramesPendents.getModel().getValueAt(lsProgramesPendents.getSelectedRow(), 4));
        }

        return e;
    }

    public int[] getListaParaFacturar() {
        int[] e = new int[llistaSeleccionats.size()];

        for (i = 0; i < llistaSeleccionats.size(); i++) {
            e[i] = llistaSeleccionats.get(i);
        }

        return e;
    }

    /**
     * 
     * Lista Seleccionados
     * @param inDatos 
     */
    public void setListaSeleccionados(Object[][] inDatos) {
        
        for(i=0;i<inDatos.length;i++){
            llistaSeleccionats.add(Integer.parseInt((String.valueOf(inDatos[i][0]))));
            listaParaFacturar[i][0] = inDatos[i][0];
            listaParaFacturar[i][1] = inDatos[i][1];
            listaParaFacturar[i][2] = inDatos[i][2];
            listaParaFacturar[i][3] = inDatos[i][3];    
            tbTotal.setText(String.valueOf(Double.parseDouble(tbTotal.getText()) + Double.parseDouble(String.valueOf(inDatos[i][3])))); 
        }
        
        actualizarListaSeleccionados();
    }

    public void actualizarListaSeleccionados() {
        ((DefaultTableModel) (lsProgramesSeleccionats.getModel())).setDataVector(listaParaFacturar, CabeceraListaParaFacturar);
        lsProgramesSeleccionats.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        lsProgramesPendents = new javax.swing.JTable();
        spPeriodoInicio = new javax.swing.JSpinner();
        tbAutofacturaPreu = new javax.swing.JTextField();
        bAutofacturaPreu = new javax.swing.JButton();
        spPeriodoFin = new javax.swing.JSpinner();
        bAutofacturaPeriode = new javax.swing.JButton();
        bAfegirSeleccio = new javax.swing.JButton();
        etTitolSeleccioAutomatica = new javax.swing.JLabel();
        etPagarPeriode = new javax.swing.JLabel();
        etPagarMaxim = new javax.swing.JLabel();
        etTitolSeleccionManual = new javax.swing.JLabel();
        etTotal = new javax.swing.JLabel();
        tbTotal = new javax.swing.JTextField();
        bFacturar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lsProgramesSeleccionats = new javax.swing.JTable();
        bNetejarFactura = new javax.swing.JButton();

        lsProgramesPendents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nom Programa", "Preu Base", "Data Emissió", "Preu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lsProgramesPendents.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        lsProgramesPendents.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(lsProgramesPendents);

        spPeriodoInicio.setModel(new javax.swing.SpinnerDateModel());

        tbAutofacturaPreu.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tbAutofacturaPreu.setText("0"); // NOI18N

        bAutofacturaPreu.setFont(new java.awt.Font("Lucida Grande 13", 0, 12));
        bAutofacturaPreu.setText("AutoFactura"); // NOI18N

        spPeriodoFin.setModel(new javax.swing.SpinnerDateModel());

        bAutofacturaPeriode.setFont(new java.awt.Font("Lucida Grande 13", 0, 12));
        bAutofacturaPeriode.setText("AutoFactura"); // NOI18N

        bAfegirSeleccio.setText("Afegir a factura"); // NOI18N
        bAfegirSeleccio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAfegirSeleccioMouseClicked(evt);
            }
        });

        etTitolSeleccioAutomatica.setFont(new java.awt.Font("Lucida Grande 18", 1, 14));
        etTitolSeleccioAutomatica.setText("Selecció Automática"); // NOI18N

        etPagarPeriode.setText("Període"); // NOI18N

        etPagarMaxim.setText("Màxim a pagar"); // NOI18N

        etTitolSeleccionManual.setFont(new java.awt.Font("Lucida Grande", 1, 14));
        etTitolSeleccionManual.setText("Selecció Manual"); // NOI18N

        etTotal.setFont(new java.awt.Font("Lucida Grande 16", 0, 12));
        etTotal.setText("Total a pagar"); // NOI18N

        tbTotal.setEditable(false);
        tbTotal.setFont(new java.awt.Font("Lucida Grande 16 12", 0, 12));
        tbTotal.setText("0"); // NOI18N

        bFacturar.setText("Facturar"); // NOI18N

        lsProgramesSeleccionats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nom Programa", "Data Emissió", "Preu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lsProgramesSeleccionats.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(lsProgramesSeleccionats);

        bNetejarFactura.setText("Buidar factura"); // NOI18N
        bNetejarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNetejarFacturaActionPerformed(evt);
            }
        });
        bNetejarFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bNetejarFacturaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(spPeriodoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(tbAutofacturaPreu)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bAutofacturaPreu))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(spPeriodoFin, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bAutofacturaPeriode))))
                            .addComponent(bAfegirSeleccio)
                            .addComponent(etTitolSeleccioAutomatica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(etPagarPeriode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(72, 72, 72))
                                .addComponent(etPagarMaxim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(etTitolSeleccionManual))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(etTotal)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                            .addComponent(bFacturar))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(243, 243, 243)
                                    .addComponent(bNetejarFactura)))
                            .addGap(13, 13, 13)))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 526, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(etTitolSeleccioAutomatica)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(39, 39, 39)
                                    .addComponent(bAutofacturaPeriode))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(bAutofacturaPreu)
                                            .addComponent(tbAutofacturaPreu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(etPagarMaxim))
                                    .addGap(10, 10, 10)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(etPagarPeriode)
                                        .addComponent(spPeriodoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(spPeriodoFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(etTitolSeleccionManual)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(bNetejarFactura)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bAfegirSeleccio)
                        .addComponent(tbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(etTotal)
                        .addComponent(bFacturar))
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bAfegirSeleccioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAfegirSeleccioMouseClicked
        // TODO add your handling code here:
        
        String e[] = new String[4];
        e = getSeleccionadoListaPendientes();
        
        if(!(e[0].equals("-1")) && !(llistaSeleccionats.contains(Integer.parseInt(e[0])))) {
            llistaSeleccionats.add(Integer.parseInt(e[0]));
            
            listaParaFacturar[i][0] = e[0];
            listaParaFacturar[i][1] = e[1];
            listaParaFacturar[i][2] = e[2];
            listaParaFacturar[i][3] = e[3];
            i = i + 1;
            
            actualizarListaSeleccionados();
            tbTotal.setText(String.valueOf(Double.parseDouble(tbTotal.getText()) + Double.parseDouble(e[3])));
        }
    }//GEN-LAST:event_bAfegirSeleccioMouseClicked

    private void bNetejarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNetejarFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bNetejarFacturaActionPerformed

    private void bNetejarFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bNetejarFacturaMouseClicked
        // TODO add your handling code here:
        llistaSeleccionats.clear();
        listaParaFacturar = new Object[lsProgramesPendents.getRowCount()][4];
        i = 0;
        this.tbTotal.setText("0");
        actualizarListaSeleccionados();
    }//GEN-LAST:event_bNetejarFacturaMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaNovaFactura().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAfegirSeleccio;
    private javax.swing.JButton bAutofacturaPeriode;
    private javax.swing.JButton bAutofacturaPreu;
    private javax.swing.JButton bFacturar;
    private javax.swing.JButton bNetejarFactura;
    private javax.swing.JLabel etPagarMaxim;
    private javax.swing.JLabel etPagarPeriode;
    private javax.swing.JLabel etTitolSeleccioAutomatica;
    private javax.swing.JLabel etTitolSeleccionManual;
    private javax.swing.JLabel etTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable lsProgramesPendents;
    private javax.swing.JTable lsProgramesSeleccionats;
    private javax.swing.JSpinner spPeriodoFin;
    private javax.swing.JSpinner spPeriodoInicio;
    private javax.swing.JTextField tbAutofacturaPreu;
    private javax.swing.JTextField tbTotal;
    // End of variables declaration//GEN-END:variables
    
}
