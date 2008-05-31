/*
 * NewJFrame.java
 *
 * Created on 22 / maig / 2008, 10:34
 */

package vistes;

import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  lipi
 */
public class VistaPrincipal extends javax.swing.JFrame {
      
    VistaGProgrames vGProgs;
    VistaFranges vFranges;
    VistaPlanificacio vPlan;
    VistaFacturacio vFact;
    
    private String[] cabecerasTabla = {"DNI", "Nom", "Cognoms"};
    
    /** Creates new form NewJFrame */
    public VistaPrincipal(VistaGProgrames nVistesGP, VistaFranges nVistaFranges, VistaPlanificacio nVistaPlan, VistaFacturacio nVistaFact) 
    {
        /**Totes les pestanyes que hi haura*/
        vGProgs = nVistesGP;
        vFranges = nVistaFranges;
        vPlan = nVistaPlan;
        vFact = nVistaFact;
        try {
            initComponents();
        } catch (Exception ex) {
            System.out.println("Err al carregar bbdd");
        }
    }
    
    public void setActions(ActionListener actions[], ListSelectionListener LSL)
    {
        saveAll.addActionListener(actions[0]);
        importaProgs.addActionListener(actions[1]);
        esborraRepoProgs.addActionListener(actions[2]);
        exportaProgs.addActionListener(actions[3]);
        gClients.addActionListener(actions[4]);
        buttonGuardarSortir.addActionListener(actions[5]);
        expFranges.addActionListener(actions[6]);
        impFranges.addActionListener(actions[7]);
        esborraFrangesDisc.addActionListener(actions[8]);
        buttonActLlista.addActionListener(actions[9]);
        menuSortir.addActionListener(actions[10]);
        llistaClients.getSelectionModel().addListSelectionListener(LSL);
    }

    public String getSelectedID() 
    {
       if (llistaClients.getSelectedRowCount() == 1) {
            return (String) llistaClients.getModel().getValueAt(llistaClients.getSelectedRow(), 0);
        } else {
            return null;
        }
    }

    public void setFitxa(String fitxa) 
    {
        dadesClSeleccionat.setText(fitxa);
    }
    
    public void setFacturesPendents(String factPendents)
    {
        dadesFacturaPendent.setText(factPendents);
    }

    void setLlistaClients(Object[][] novaLlista) 
    {
        ((DefaultTableModel) (llistaClients.getModel())).setDataVector(novaLlista,cabecerasTabla);    
    }
        
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        gClients = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        llistaClients = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        dadesClSeleccionat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        dadesFacturaPendent = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        buttonGuardarSortir = new javax.swing.JButton();
        buttonActLlista = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        saveAll = new javax.swing.JMenuItem();
        clearAll = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        importaProgs = new javax.swing.JMenuItem();
        exportaProgs = new javax.swing.JMenuItem();
        esborraRepoProgs = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        expFranges = new javax.swing.JMenuItem();
        impFranges = new javax.swing.JMenuItem();
        esborraFrangesDisc = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuSortir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        gClients.setText("Gestionar Clients");

        llistaClients.setAutoCreateRowSorter(true);
        llistaClients.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null}
            },
            new String [] {
                "DNI", "Nom", "Cognoms"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(llistaClients);
        llistaClients.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel1.setText("Factures pendents:");

        jLabel2.setText("Sel·lecció del Client a administrar:");

        dadesClSeleccionat.setColumns(20);
        dadesClSeleccionat.setEditable(false);
        dadesClSeleccionat.setRows(5);
        jScrollPane3.setViewportView(dadesClSeleccionat);

        dadesFacturaPendent.setColumns(20);
        dadesFacturaPendent.setEditable(false);
        dadesFacturaPendent.setRows(5);
        jScrollPane2.setViewportView(dadesFacturaPendent);

        jLabel3.setText("Client sel·leccionat actualment:");

        buttonGuardarSortir.setText("Guardar i sortir");

        buttonActLlista.setText("Actualitza llista");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonActLlista)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonGuardarSortir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gClients, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                        .addGap(51, 51, 51))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(117, 117, 117)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(gClients, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(buttonGuardarSortir, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonActLlista)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Menu Principal - Clients", jPanel1);

        jMenu1.setText("Fitxer");

        saveAll.setText("Guardar tot");
        jMenu1.add(saveAll);

        clearAll.setText("Esborrar tot");
        jMenu1.add(clearAll);

        jMenu3.setText("Programes");

        importaProgs.setText("Importar Programes");
        jMenu3.add(importaProgs);

        exportaProgs.setText("Exportar Programes");
        jMenu3.add(exportaProgs);

        esborraRepoProgs.setText("Esborrar Programes de disc");
        jMenu3.add(esborraRepoProgs);

        jMenu1.add(jMenu3);

        jMenu4.setText("Franges");

        expFranges.setText("Exportar Franges");
        jMenu4.add(expFranges);

        impFranges.setText("Importar Franges");
        jMenu4.add(impFranges);

        esborraFrangesDisc.setText("Esborrar Franges de disc");
        jMenu4.add(esborraFrangesDisc);

        jMenu1.add(jMenu4);

        jMenu5.setText("Planificació");
        jMenu5.setEnabled(false);
        jMenu1.add(jMenu5);

        menuSortir.setText("Sortir");
        jMenu1.add(menuSortir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ajuda");

        jMenuItem7.setText("Manual d'usuari");
        jMenu2.add(jMenuItem7);

        jMenuItem8.setText("Ampliacions futures");
        jMenu2.add(jMenuItem8);

        jMenuItem6.setText("Sobre el programa");
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.add("Programes",vGProgs);
        jTabbedPane1.add("Franges Horaries",vFranges);
        jTabbedPane1.add("Planificador", vPlan);
        jTabbedPane1.add("Facturacio", vFact);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonActLlista;
    private javax.swing.JButton buttonGuardarSortir;
    private javax.swing.JMenuItem clearAll;
    private javax.swing.JTextArea dadesClSeleccionat;
    private javax.swing.JTextArea dadesFacturaPendent;
    private javax.swing.JMenuItem esborraFrangesDisc;
    private javax.swing.JMenuItem esborraRepoProgs;
    private javax.swing.JMenuItem expFranges;
    private javax.swing.JMenuItem exportaProgs;
    private javax.swing.JButton gClients;
    private javax.swing.JMenuItem impFranges;
    private javax.swing.JMenuItem importaProgs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable llistaClients;
    private javax.swing.JMenuItem menuSortir;
    private javax.swing.JMenuItem saveAll;
    // End of variables declaration//GEN-END:variables
    
}
